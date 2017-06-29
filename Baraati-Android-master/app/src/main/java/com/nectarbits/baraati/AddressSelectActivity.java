package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nectarbits.baraati.Adapters.AddressAdapter;
import com.nectarbits.baraati.Adapters.CartListAdapter;
import com.nectarbits.baraati.Interface.OnAddressClick;
import com.nectarbits.baraati.Interface.OnAlertDialogClicked;
import com.nectarbits.baraati.Interface.OnCartClick;
import com.nectarbits.baraati.Models.AddDates.AddDateModel;
import com.nectarbits.baraati.Models.Address.AddressModel;
import com.nectarbits.baraati.Models.Address.ShippingAddress;
import com.nectarbits.baraati.Models.CartList.CartListModel;
import com.nectarbits.baraati.Models.CountryModel.CityInfo;
import com.nectarbits.baraati.Models.CountryModel.CountryInfo;
import com.nectarbits.baraati.Models.CountryModel.StateInfo;
import com.nectarbits.baraati.Singletone.CartProcessUtil;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AlertsUtils;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.DBHelper;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressSelectActivity extends AppCompatActivity {

    @Bind(R.id.rvAddress)
    RecyclerView rvAddress;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.empty_text)
    TextViewTitle empty_text;

    Context mContext;
    ProgressDialog progressDialog;
    List<ShippingAddress> shippingAddresses=new ArrayList<>();

    private ArrayList<CountryInfo> arrayListCountry;
    private ArrayList<CityInfo> cityInfoArrayList;
    private ArrayList<StateInfo> stateInfoArrayList=new ArrayList<>();
    private DBHelper dbHelper;
    StateInfo stateInfo=new StateInfo();
   /* CityInfo cityInfo=new CityInfo();*/

    AddressAdapter addressAdapter;
    Boolean is_selected=false;
    @OnClick(R.id.ttvContinue)
    void onContinue(){
       /* if(is_selected) {
            Intent intent = new Intent(mContext, SelectPaymentActivity.class);
            startActivityForResult(intent, AppUtils.REQUEST_ORDER);
        }else{
            Toast.makeText(mContext, getString(R.string.str_please_select_address), Toast.LENGTH_SHORT).show();
        }*/

        Boolean aBoolean=false;
        for (int i = 0; i < shippingAddresses.size(); i++) {
            if(shippingAddresses.get(i).getSelected()==true){
                Intent intent = new Intent(mContext, SelectPaymentActivity.class);
                startActivityForResult(intent, AppUtils.REQUEST_ORDER);
                aBoolean=true;
            }
        }
        if(!aBoolean) {
            Toast.makeText(mContext, getString(R.string.str_please_select_address), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_select);

        ButterKnife.bind(this);
        dbHelper = new DBHelper(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        loadDefauldAddress();
        implementToolbar();
        if (GH.isInternetOn(mContext))
        RequestFoAddress();
    }

    void loadDefauldAddress(){
        shippingAddresses.clear();
        if(UserDetails.getInstance(mContext).getUser_address1().length()>0) {
            ShippingAddress shippingAddress = new ShippingAddress();
            shippingAddress.setName(UserDetails.getInstance(mContext).getUser_name());
            shippingAddress.setAddressline1(UserDetails.getInstance(mContext).getUser_address1());
            shippingAddress.setAddressline2(UserDetails.getInstance(mContext).getUser_address2());
            shippingAddress.setCountry("India");
            shippingAddress.setSelected(true);
            shippingAddress.setZipcode(UserDetails.getInstance(mContext).getUser_zip());
            shippingAddress.setPhone(UserDetails.getInstance(mContext).getUser_phone());
            if (UserDetails.getInstance(mContext).getUser_state().length() > 0) {
                getStateFromDB();
                for (int i = 0; i < stateInfoArrayList.size(); i++) {
                    if (stateInfoArrayList.get(i).getId() == (Integer.parseInt(UserDetails.getInstance(mContext).getUser_state()))) {
                        shippingAddress.setState(stateInfoArrayList.get(i).getName());
                        stateInfo.setId(stateInfoArrayList.get(i).getId());
                        stateInfo.setName(stateInfoArrayList.get(i).getName());
                        stateInfo.setCountry_id(stateInfoArrayList.get(i).getCountry_id());
                    }
                }

            }

            if (UserDetails.getInstance(mContext).getUser_city().length() > 0) {
                getCityNameFromCountryId();
                for (int i = 0; i < cityInfoArrayList.size(); i++) {
                    if (cityInfoArrayList.get(i).getId() == (Integer.parseInt(UserDetails.getInstance(mContext).getUser_city()))) {
                        shippingAddress.setCity(cityInfoArrayList.get(i).getCityName());
                    }
                }

            }
            shippingAddresses.add(shippingAddress);
        }
        addressAdapter=new AddressAdapter(mContext, shippingAddresses, new OnAddressClick() {
            @Override
            public void onAddressSelectClick(int position,Boolean aBoolean) {
                CartProcessUtil.getInstance().setSetectedAddress(position);
                for (int i = 0; i < shippingAddresses.size(); i++) {
                    if(position==i){
                        shippingAddresses.get(i).setSelected(true);
                    }else {
                        shippingAddresses.get(i).setSelected(false);
                    }
                    is_selected=true;
                }
                try{
                    Handler handler = new Handler();

                    final Runnable r = new Runnable() {
                        public void run() {
                            addressAdapter.notifyDataSetChanged();
                        }
                    };

                    handler.post(r);

                }catch (Exception e){
                    e.printStackTrace();
                }



            }

            @Override
            public void onAddressChangeClick(int position) {
                CartProcessUtil.getInstance().ResetAddress();
                CartProcessUtil.getInstance().setShippingAddress(shippingAddresses.get(position));
                Intent intent=new Intent(mContext,UpdateAddress.class);
                startActivityForResult(intent,AppUtils.REQUEST_ADD_ADDRESS);
            }
        });



        rvAddress.setLayoutManager(new LinearLayoutManager(mContext));
        rvAddress.setItemAnimator(new DefaultItemAnimator());
        rvAddress.setAdapter(addressAdapter);

    }
    /**
     * set ToolBar
     */
    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.str_select_shipping_address));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(shippingAddresses.size()<=1)
        getMenuInflater().inflate(R.menu.menu_address, menu);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_next:
                if(shippingAddresses.size()<=1){
                    Intent intent=new Intent(mContext,UpdateAddress.class);
                    startActivityForResult(intent,AppUtils.REQUEST_ADD_ADDRESS);
                }
                break;
        }


        return super.onOptionsItemSelected(item);
    }
    private void RequestFoAddress() {
        if (!progressDialog.isShowing())
            progressDialog.show();

        empty_text.setVisibility(View.GONE);
        empty_text.setText(getString(R.string.str_no_address_available));
        rvAddress.setVisibility(View.VISIBLE);

        Call<AddressModel> call = RetrofitBuilder.getInstance().getRetrofit().GetShippingAddress(RequestBodyBuilder.getInstanse().Request_ShippingAddress(UserDetails.getInstance(mContext).getUser_id()));

        call.enqueue(new Callback<AddressModel>() {
            @Override
            public void onResponse(Call<AddressModel> call, Response<AddressModel> response) {
                AddressModel     cartListModel=response.body();
                if(cartListModel.getStatus().equalsIgnoreCase(AppUtils.Success)){
                    ShippingAddress shippingAddress=cartListModel.getShippingAddress().get(0);

                    if(shippingAddress.getState().length()>0) {
                        for (int i = 0; i < stateInfoArrayList.size(); i++) {
                            if(stateInfoArrayList.get(i).getId()==Integer.parseInt(shippingAddress.getState())){
                                shippingAddress.setState(stateInfoArrayList.get(i).getName());
                                stateInfo.setId(stateInfoArrayList.get(i).getId());
                                stateInfo.setName(stateInfoArrayList.get(i).getName());
                                stateInfo.setCountry_id(stateInfoArrayList.get(i).getCountry_id());
                                break;
                            }
                        }

                    }

                    if(shippingAddress.getCity().length()>0) {
                        getCityNameFromCountryId();
                        for (int i = 0; i < cityInfoArrayList.size(); i++) {
                            if(cityInfoArrayList.get(i).getId()==(Integer.parseInt(shippingAddress.getCity()))){
                                shippingAddress.setCity(cityInfoArrayList.get(i).getCityName());
                                break;
                            }
                        }

                    }
                    shippingAddress.setChangeable(true);
                    shippingAddress.setSelected(false);
                    shippingAddresses.add(shippingAddress);
                    addressAdapter.notifyDataSetChanged();
                    if(shippingAddresses.size()==0){
                        rvAddress.setVisibility(View.GONE);
                        empty_text.setVisibility(View.VISIBLE);
                    }

                }else {
                    if(shippingAddresses.size()==0){
                        rvAddress.setVisibility(View.GONE);
                        empty_text.setVisibility(View.VISIBLE);
                    }
                }

                supportInvalidateOptionsMenu();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AddressModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                rvAddress.setVisibility(View.GONE);
                empty_text.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getStateFromDB() {

        String selectQuery = "select * from states  where country_id=101 order by name";
        Cursor cursor = dbHelper.executeSelectQuery(selectQuery);
        stateInfoArrayList = new ArrayList<StateInfo>();
        if(cursor != null && cursor.moveToFirst()){
            for (int i=0;i<cursor.getCount();i++){
                StateInfo countryInfo = new StateInfo();

                countryInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
                countryInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
                countryInfo.setCountry_id(cursor.getInt(cursor.getColumnIndex("country_id")));
                stateInfoArrayList.add(countryInfo);
                cursor.moveToNext();
            }
        }
    }

    private void getCityNameFromCountryId() {

        String selectCity = "select * from cities where state_id="+stateInfo.getId()+" order by name";
        Cursor cursor = dbHelper.executeSelectQuery(selectCity);
        cityInfoArrayList = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst()){
            for(int i=0;i<cursor.getCount();i++){
                CityInfo cityInfo = new CityInfo();
                cityInfo.setCityName(cursor.getString(cursor.getColumnIndex("name")));
                cityInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
                cityInfoArrayList.add(cityInfo);
                cursor.moveToNext();
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==AppUtils.REQUEST_ADD_ADDRESS){
                loadDefauldAddress();
                if (GH.isInternetOn(mContext))
                RequestFoAddress();
            }else if(requestCode==AppUtils.REQUEST_ORDER){
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
