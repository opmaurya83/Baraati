package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nectarbits.baraati.Adapters.AdapterForCityList;
import com.nectarbits.baraati.Adapters.AdapterForState;
import com.nectarbits.baraati.Adapters.OrderListAdapter;
import com.nectarbits.baraati.Chat.App;
import com.nectarbits.baraati.Interface.OnOrderProductClick;
import com.nectarbits.baraati.Models.CountryModel.CityInfo;
import com.nectarbits.baraati.Models.CountryModel.CountryInfo;
import com.nectarbits.baraati.Models.CountryModel.StateInfo;
import com.nectarbits.baraati.Models.OrderDetail.OrderDetailModel;
import com.nectarbits.baraati.Models.OrderList.OrderListModel;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.DBHelper;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Bind(R.id.tvtOrderDate)
    TextViewTitle tvtOrderDate;
    @Bind(R.id.tvtOrderNumber)
    TextViewTitle tvtOrderNumber;
    @Bind(R.id.tvtOrderTotal)
    TextViewTitle tvtOrderTotal;

    @Bind(R.id.tvdDeliveryDate)
    TextViewDescription tvdDeliveryDate;
    @Bind(R.id.tvtTitle)
    TextViewTitle tvtTitle;
    @Bind(R.id.tvdDesciption)
    TextViewDescription tvdDesciption;
    @Bind(R.id.tvtPrice)
    TextViewTitle tvtPrice;
    @Bind(R.id.tvtQty)
    TextViewTitle tvtQty;
    @Bind(R.id.imgProductImage)
    ImageView imgProductImage;
    @Bind(R.id.tvtOrderPaymentMethod)
    TextViewTitle tvtOrderPaymentMethod;
    @Bind(R.id.tvtaddName)
    TextViewTitle tvtaddName;
    @Bind(R.id.tvtadd)
    TextViewTitle tvtadd;
    @Bind(R.id.tvtState)
    TextViewTitle tvtState;
    @Bind(R.id.tvtCity)
    TextViewTitle tvtCity;
    @Bind(R.id.tvtZip)
    TextViewTitle tvtZip;
    @Bind(R.id.tvtPhone)
    TextViewTitle tvtPhone;
    @Bind(R.id.ll_main)
    LinearLayout ll_main;
    Context mContext;
    ProgressDialog progressDialog;

    String order_id;
    String product_id;


    private ArrayList<CityInfo> cityInfoArrayList;
    private ArrayList<StateInfo> stateInfoArrayList;



    StateInfo stateInfo=new StateInfo();
    CityInfo cityInfo=new CityInfo();

    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        product_id= getIntent().getExtras().getString(AppUtils.ARG_PRODUCT_ID);
        order_id= getIntent().getExtras().getString(AppUtils.ARG_ORDER_ID);
        dbHelper = new DBHelper(this);
        implementToolbar();
        if (GH.isInternetOn(mContext))
        RequestFoGetOrderDetail();
    }

    /**
     * set ToolBar
     */
    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.str_order_detail));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    /**
     * Get Cart List
     */
    private void RequestFoGetOrderDetail() {
        if (!progressDialog.isShowing())
            progressDialog.show();
        ll_main.setVisibility(View.GONE);



        Call<OrderDetailModel> call = RetrofitBuilder.getInstance().getRetrofit().GetOrderDetail(RequestBodyBuilder.getInstanse().Request_GetOrderDetail(UserDetails.getInstance(mContext).getUser_id(),order_id,product_id));

        call.enqueue(new Callback<OrderDetailModel>() {
            @Override
            public void onResponse(Call<OrderDetailModel> call, Response<OrderDetailModel> response) {
                OrderDetailModel  cartListModel=response.body();
                if(cartListModel.getStatus().equalsIgnoreCase(AppUtils.Success)){

                        tvtOrderDate.setText(cartListModel.getOrderDetail().get(0).getOrderdate());
                        tvtOrderNumber.setText("ORD_"+cartListModel.getOrderDetail().get(0).getOrderId());
                        tvtOrderTotal.setText(mContext.getString(R.string.rs)+GH.getFormatedAmount(""+cartListModel.getGrandtotal()));

                        tvtTitle.setText(cartListModel.getOrderDetail().get(0).getProduct());
                        tvdDesciption.setText(cartListModel.getOrderDetail().get(0).getTwolinedescription());
                        tvtPrice.setText(mContext.getString(R.string.rs)+GH.getFormatedAmount(cartListModel.getOrderDetail().get(0).getPrice()));
                        tvtQty.setText(cartListModel.getOrderDetail().get(0).getQuantity()+" "+getString(R.string.str_order_qty));
                    Picasso.with(mContext)
                            .load(cartListModel.getOrderDetail().get(0).getImage())
                            .into(imgProductImage);
                        tvtaddName.setText(cartListModel.getShippingDetail().get(0).getName());
                        tvtadd.setText(cartListModel.getShippingDetail().get(0).getAddressline1()+", "+cartListModel.getShippingDetail().get(0).getAddressline2());
                        tvtZip.setText("-"+cartListModel.getShippingDetail().get(0).getZipcode());
                        tvtPhone.setText("-"+cartListModel.getShippingDetail().get(0).getPhone());
                    if(cartListModel.getShippingDetail().get(0).getState().length()>0) {
                            getStateFromDB();
                        for (int i = 0; i < stateInfoArrayList.size(); i++) {
                            try{
                                if(stateInfoArrayList.get(i).getId()==Integer.parseInt(cartListModel.getShippingDetail().get(0).getState())){

                                    stateInfo.setId(stateInfoArrayList.get(i).getId());
                                    stateInfo.setName(stateInfoArrayList.get(i).getName());
                                    stateInfo.setCountry_id(stateInfoArrayList.get(i).getCountry_id());
                                    tvtState.setText(stateInfoArrayList.get(i).getName());
                                }}catch (Exception e){

                            }
                        }

                    }

                    if(cartListModel.getShippingDetail().get(0).getCity().length()>0) {
                        //tvCity.setText(shippingAddress.getCity());
                        getCityNameFromCountryId();
                        for (int i = 0; i < cityInfoArrayList.size(); i++) {
                            try {
                                if (cityInfoArrayList.get(i).getId()==Integer.parseInt(cartListModel.getShippingDetail().get(0).getCity())) {
                                    tvtCity.setText(cityInfoArrayList.get(i).getCityName());
                                    cityInfo=cityInfoArrayList.get(i);
                                    tvtCity.setText(cityInfoArrayList.get(i).getCityName());
                                    break;
                                }
                            }catch (Exception ex){

                            }
                        }

                    }

                }else {


                }
                ll_main.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<OrderDetailModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                ll_main.setVisibility(View.VISIBLE);
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
}
