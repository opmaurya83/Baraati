package com.nectarbits.baraati;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.nectarbits.baraati.Adapters.AdapterForCityList;
import com.nectarbits.baraati.Adapters.AdapterForState;
import com.nectarbits.baraati.Interface.OnStateCitySelect;
import com.nectarbits.baraati.Models.AddUpdateAddress.AddUpdateAddressModel;
import com.nectarbits.baraati.Models.Address.AddressModel;
import com.nectarbits.baraati.Models.Address.ShippingAddress;
import com.nectarbits.baraati.Models.CountryModel.CityInfo;
import com.nectarbits.baraati.Models.CountryModel.CountryInfo;
import com.nectarbits.baraati.Models.CountryModel.StateInfo;
import com.nectarbits.baraati.Singletone.CartProcessUtil;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.EditTextTitle;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.DBHelper;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAddress extends AppCompatActivity {

    private static final String TAG = UpdateAddress.class.getSimpleName() ;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.evName)
    EditTextTitle evName;

    @Bind(R.id.evPhone)
    EditTextTitle evPhone;
    @Bind(R.id.evAddress)
    EditTextTitle evAddress;
    @Bind(R.id.evAddress2)
    EditTextTitle evAddress2;
    @Bind(R.id.evPinCode)
    EditTextTitle evPinCode;

    @Bind(R.id.tvCountry)
    TextViewTitle tvCount;

    @Bind(R.id.tvState)
    TextViewTitle tvState;

    @Bind(R.id.tvCity)
    TextViewTitle tvCity;
    private ArrayList<CountryInfo> arrayListCountry;
    private ArrayList<CityInfo> cityInfoArrayList;
    private ArrayList<StateInfo> stateInfoArrayList;
    private AdapterForState adapterForState;
    private AdapterForCityList adapterForCityList;

    ArrayList<CityInfo> cityInfoslistTemp=new ArrayList<>();
    ArrayList<StateInfo> stateInfosListTemp =new ArrayList<>();

    StateInfo stateInfo=new StateInfo();
    CityInfo cityInfo=new CityInfo();

    Dialog dialog;
    RecyclerView recyclerView;
    private DBHelper dbHelper;
    private Context mContext;
    ProgressDialog progressDialog;
    AlertDialog alert= null;
    ShippingAddress shippingAddress;
    @OnClick(R.id.tvState)
    void OnState(){
        openState();

    }


    @OnClick(R.id.tvCity)
    void OnCity(){
        if(stateInfo.getId()>0) {
            openCity();
        }else{
            Toast.makeText(UpdateAddress.this, getString(R.string.str_please_select_state_first), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);

        ButterKnife.bind(this);
        mContext=this;
        progressDialog = new ProgressDialog(mContext);

        dbHelper = new DBHelper(this);
        implementToolbar();
        getCountryFromDB();
        getStateFromDB();

        tvCount.setText(arrayListCountry.get(0).getName());

        if(CartProcessUtil.getInstance().getShippingAddress()!=null){
            shippingAddress=CartProcessUtil.getInstance().getShippingAddress();
            evName.setText(shippingAddress.getName());




            if(shippingAddress.getPhone().length()>0) {
                evPhone.setText(shippingAddress.getPhone());
            }



            if(shippingAddress.getAddressline1().length()>0) {
                evAddress.setText(shippingAddress.getAddressline1());
            }

            if(shippingAddress.getAddressline2().length()>0) {
                evAddress2.setText(shippingAddress.getAddressline2());
            }


            if(shippingAddress.getZipcode().length()>0) {
                evPinCode.setText(shippingAddress.getZipcode());
            }

            if(shippingAddress.getState().length()>0) {
                tvState.setText(shippingAddress.getState());
                for (int i = 0; i < stateInfoArrayList.size(); i++) {
                    try{
                    if(stateInfoArrayList.get(i).getName().equalsIgnoreCase(shippingAddress.getState())){
                       // tvState.setText(stateInfoArrayList.get(i).getName());
                        stateInfo.setId(stateInfoArrayList.get(i).getId());
                        stateInfo.setName(stateInfoArrayList.get(i).getName());
                        stateInfo.setCountry_id(stateInfoArrayList.get(i).getCountry_id());
                        tvState.setText(stateInfoArrayList.get(i).getName());
                    }}catch (Exception e){

                    }
                }

            }

            if(shippingAddress.getCity().length()>0) {
                tvCity.setText(shippingAddress.getCity());
              getCityNameFromCountryId();
                for (int i = 0; i < cityInfoArrayList.size(); i++) {
                    try {
                        if (cityInfoArrayList.get(i).getCityName().equalsIgnoreCase(shippingAddress.getCity())) {
                            tvCity.setText(cityInfoArrayList.get(i).getCityName());
                            cityInfo=cityInfoArrayList.get(i);
                            tvCity.setText(cityInfoArrayList.get(i).getCityName());
                            break;
                        }
                    }catch (Exception ex){

                    }
                }

            }
        }
    }

    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.str_shipping_address));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done, menu);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_done:
                if(evName.getText().length()==0){
                    Toast.makeText(UpdateAddress.this, getString(R.string.str_name_must), Toast.LENGTH_SHORT).show();
                }else if(evPhone.getText().toString().length()==0){
                    Toast.makeText(UpdateAddress.this, getString(R.string.str_name_must), Toast.LENGTH_SHORT).show();
                }else if(evPhone.getText().toString().length()<10 && evPhone.getText().toString().length()>12){
                    Toast.makeText(UpdateAddress.this, getString(R.string.str_phone_must_range), Toast.LENGTH_SHORT).show();
                }else if(evAddress.getText().toString().length()==0){
                    Toast.makeText(UpdateAddress.this, getString(R.string.str_address1_must), Toast.LENGTH_SHORT).show();
                }else if(stateInfo.getId()==0){
                    Toast.makeText(UpdateAddress.this, getString(R.string.str_please_select_state_first), Toast.LENGTH_SHORT).show();
                }else if(cityInfo.getId()==0){
                    Toast.makeText(UpdateAddress.this, getString(R.string.str_city_must), Toast.LENGTH_SHORT).show();
                }else if(evPinCode.getText().toString().length()==0){
                    Toast.makeText(UpdateAddress.this, getString(R.string.str_pin_must), Toast.LENGTH_SHORT).show();
                }else if(evPinCode.getText().toString().length()!=6){
                    Toast.makeText(UpdateAddress.this, getString(R.string.str_pin_must_range), Toast.LENGTH_SHORT).show();
                }else {
                    if (GH.isInternetOn(mContext))
                    RequestFoAddress();
                }
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void openState() {

        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_list_item_for_country);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        EditText editText = (EditText)dialog.findViewById(R.id.edttxtCountry);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerViewForCountry);
        editText.addTextChangedListener(textWatcher_State);
        getCountryFromDB();

        adapterForState = new AdapterForState(stateInfoArrayList, onStateCitySelect_state);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterForState);
        dialog.show();
    }




    OnStateCitySelect onStateCitySelect_state=new OnStateCitySelect() {
        @Override
        public void onselected(int position) {
            dialog.dismiss();
            if(stateInfosListTemp.size()==0) {
                stateInfo.setId( stateInfoArrayList.get(position).getId());
                stateInfo.setName(stateInfoArrayList.get(position).getName());
                stateInfo.setCountry_id(stateInfoArrayList.get(position).getCountry_id());
                tvState.setText(stateInfoArrayList.get(position).getName());
            }else{
                stateInfo.setId( stateInfosListTemp.get(position).getId());
                stateInfo.setName(stateInfosListTemp.get(position).getName());
                stateInfo.setCountry_id(stateInfosListTemp.get(position).getCountry_id());
                tvState.setText(stateInfosListTemp.get(position).getName());
            }
        }
    };
    TextWatcher textWatcher_State=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s != null && s.length() > 0){
                //  arrayListCountry =getFilteredCountries(s.toString().toLowerCase().trim());
                Log.e(TAG, "afterTextChanged: > 0");
                stateInfosListTemp=getFilteredStates(s.toString().toLowerCase().trim());
                AdapterForState adapterForCountry1 = new AdapterForState(stateInfosListTemp,onStateCitySelect_state);
                recyclerView.setAdapter(adapterForCountry1);
                adapterForCountry1.notifyDataSetChanged();

            }else {
                Log.e(TAG, "afterTextChanged: < 0");
                stateInfosListTemp.clear();
                getCountryFromDB();
                AdapterForState adapterForCountry1 = new AdapterForState(stateInfoArrayList,onStateCitySelect_state);

                recyclerView.setAdapter(adapterForCountry1);
                adapterForCountry1.notifyDataSetChanged();
            }
        }

    };

    private ArrayList<StateInfo> getFilteredStates(String trim) {
        ArrayList<StateInfo> arrayList1 = new ArrayList<StateInfo>();

        for(int i = 0; i< stateInfoArrayList.size(); i++){
            if(stateInfoArrayList.get(i).getName().toLowerCase().contains(trim)){
                arrayList1.add(stateInfoArrayList.get(i));
            }
        }

        return arrayList1;
    }

    private void getCountryFromDB() {

        String selectQuery = "select * from countries  where id=101 order by name";
        Cursor cursor = dbHelper.executeSelectQuery(selectQuery);
        arrayListCountry = new ArrayList<CountryInfo>();
        if(cursor != null && cursor.moveToFirst()){
            for (int i=0;i<cursor.getCount();i++){
                CountryInfo countryInfo = new CountryInfo();
                countryInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
                countryInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
                countryInfo.setShortName(cursor.getString(cursor.getColumnIndex("sortname")));
                arrayListCountry.add(countryInfo);
                cursor.moveToNext();
            }
        }
    }


    private void openCity() {
        getCityNameFromCountryId();
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.spinner_list_item_for_country);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        EditText editText = (EditText)dialog.findViewById(R.id.edttxtCountry);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerViewForCountry);
        editText.addTextChangedListener(textWatcher_City);
        getCountryFromDB();

        adapterForCityList = new AdapterForCityList(cityInfoArrayList, onStateCitySelect_city);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterForCityList);
        dialog.show();
    }

    OnStateCitySelect onStateCitySelect_city=new OnStateCitySelect() {
        @Override
        public void onselected(int position) {
            dialog.dismiss();
            if(cityInfoslistTemp.size()==0) {
                cityInfo.setId( cityInfoArrayList.get(position).getId());

                cityInfo.setCityName(cityInfoArrayList.get(position).getCityName());

                tvCity.setText(cityInfoArrayList.get(position).getCityName());
            }else{
                cityInfo.setId( cityInfoslistTemp.get(position).getId());

                cityInfo.setCityName(cityInfoslistTemp.get(position).getCityName());

                tvCity.setText(cityInfoslistTemp.get(position).getCityName());
            }
        }
    };


    TextWatcher textWatcher_City=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s != null && s.length() > 0){
                //  arrayListCountry =getFilteredCountries(s.toString().toLowerCase().trim());
                cityInfoslistTemp=getFilteredCity(s.toString().toLowerCase().trim());
                AdapterForCityList adapterForCountry1 = new AdapterForCityList(cityInfoslistTemp,onStateCitySelect_city);
                recyclerView.setAdapter(adapterForCountry1);
                adapterForCountry1.notifyDataSetChanged();

            }else {
                cityInfoslistTemp.clear();
                getCityNameFromCountryId();
                AdapterForCityList adapterForCountry1 = new AdapterForCityList(cityInfoArrayList,onStateCitySelect_state);

                recyclerView.setAdapter(adapterForCountry1);
                adapterForCountry1.notifyDataSetChanged();
            }
        }

    };

    private ArrayList<CityInfo> getFilteredCity(String trim) {
        ArrayList<CityInfo> arrayList1 = new ArrayList<CityInfo>();

        for(int i = 0; i< cityInfoArrayList.size(); i++){
            if(cityInfoArrayList.get(i).getCityName().toLowerCase().contains(trim)){
                arrayList1.add(cityInfoArrayList.get(i));
            }
        }

        return arrayList1;
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

    private void RequestFoAddress() {
        if (!progressDialog.isShowing())
            progressDialog.show();

        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("userid",UserDetails.getInstance(mContext).getUser_id());
            jsonObject.put("country","101");
            jsonObject.put("state",stateInfo.getId());
            jsonObject.put("city",cityInfo.getId());
            jsonObject.put("addressline1",evAddress.getText().toString());
            jsonObject.put("addressline2",evAddress2.getText().toString());
            jsonObject.put("zipcode",evPinCode.getText());
            jsonObject.put("phone",evPhone.getText());
            jsonObject.put("name",evName.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<AddUpdateAddressModel> call = RetrofitBuilder.getInstance().getRetrofit().AddUpdateAddress(jsonObject);

        call.enqueue(new Callback<AddUpdateAddressModel>() {
            @Override
            public void onResponse(Call<AddUpdateAddressModel> call, Response<AddUpdateAddressModel> response) {
                AddUpdateAddressModel     cartListModel=response.body();
                if(cartListModel.getStatus().equalsIgnoreCase(AppUtils.Success)){
                    setResult(RESULT_OK);
                    finish();

                }else {

                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AddUpdateAddressModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }
}
