package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nectarbits.baraati.Adapters.CartListAdapter;
import com.nectarbits.baraati.Adapters.RatingListAdapter;
import com.nectarbits.baraati.Adapters.VendorListAdapter;
import com.nectarbits.baraati.Interface.OnAlertDialogClicked;
import com.nectarbits.baraati.Interface.OnCartClick;
import com.nectarbits.baraati.Interface.OnRatingClick;
import com.nectarbits.baraati.Interface.OnVendorSelect;
import com.nectarbits.baraati.Models.CartList.CartListModel;
import com.nectarbits.baraati.Models.CartList.Cartlistdatum;
import com.nectarbits.baraati.Models.RattingList.ProductRatingListModel;
import com.nectarbits.baraati.Models.VendorBazaar.VendorBazaarModel;
import com.nectarbits.baraati.Singletone.CartProcessUtil;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AlertsUtils;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.generalHelper.SingletonUtils;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.orhanobut.hawk.Hawk;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    @Bind(R.id.rcCart)
    RecyclerView rcCart;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.empty_text)
    TextViewTitle empty_text;
    @Bind(R.id.ttvContinue)
    TextViewTitle ttvContinue;

    @Bind(R.id.ttvPrice)
    TextViewTitle ttvPrice;
    @Bind(R.id.ttvTotalPrice)
    TextViewTitle ttvTotalPrice;
    @Bind(R.id.cvPayableDetail)
    CardView cvPayableDetail;

    @Bind(R.id.cvPaymentDetail)
    CardView cvPaymentDetail;

    @Bind(R.id.ll_main)
    LinearLayout ll_main;


    CartListAdapter adapter;
    Context mContext;
    ProgressDialog progressDialog;
    CartListModel     cartListModel;

    private List<Cartlistdatum> cartlistdata=new ArrayList<>();

    /************Click**************/
    @OnClick(R.id.ttvContinue)
    void onContinue(){
        if(cartlistdata.size()>0) {
            CartProcessUtil.getInstance().setCartListModel(cartListModel);
            Intent intent = new Intent(mContext, AddressSelectActivity.class);
            startActivityForResult(intent,AppUtils.REQUEST_ORDER);
        }else {
            Toast.makeText(CartActivity.this, getString(R.string.str_cart_must_not_be_empty), Toast.LENGTH_SHORT).show();
        }
    }


    /************Click**************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        implementToolbar();
        if (GH.isInternetOn(mContext))
        RequestFoCartList();
        //setData(new VendorBazaarModel());
    }

    /**
     * set ToolBar
     */
    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.str_cart));
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
     * set Data to view
     * @param vendorList
     */
  /*  private void setData(final VendorBazaarModel vendorList) {
        empty_text.setText(getString(R.string.str_your_cart_is_empty));
        *//*if (vendorList.getStatus().equalsIgnoreCase(AppUtils.Success)) {*//*

            adapter = new CartListAdapter(mContext, vendorList.getVendorDetail(), new OnVendorSelect() {
                @Override
                public void onVendorSelectFromList(int position, String value) {

                    Intent i = new Intent(mContext, VenderDetailActivity.class);
                    i.putExtra(AppUtils.ARG_VENDOR_ID,vendorList.getVendorDetail().get(position).getVendorId());
                    startActivity(i);

                }

            });
            rcCart.setLayoutManager(new LinearLayoutManager(mContext));
        rcCart.setItemAnimator(new DefaultItemAnimator());
        rcCart.setAdapter(adapter);

    }*/


    /**
     * Get Cart List
     */
    private void RequestFoCartList() {
        if (!progressDialog.isShowing())
            progressDialog.show();
        ll_main.setVisibility(View.GONE);
        rcCart.setVisibility(View.VISIBLE);
        empty_text.setVisibility(View.GONE);
        empty_text.setText(getString(R.string.str_your_cart_is_empty));
        Call<CartListModel> call = RetrofitBuilder.getInstance().getRetrofit().GetCartList(RequestBodyBuilder.getInstanse().Request_CartList(UserDetails.getInstance(mContext).getUser_id()));

        call.enqueue(new Callback<CartListModel>() {
            @Override
            public void onResponse(Call<CartListModel> call, Response<CartListModel> response) {
                     cartListModel=response.body();
                
                if(cartListModel.getStatus().equalsIgnoreCase(AppUtils.Success)){
                    cvPayableDetail.setVisibility(View.VISIBLE);
                    cvPaymentDetail.setVisibility(View.VISIBLE);
                    cartlistdata=cartListModel.getCartlistdata();
                    ttvPrice.setText(mContext.getString(R.string.rs)+ GH.getFormatedAmount(""+cartListModel.getGrandsum()));
                    ttvTotalPrice.setText(mContext.getString(R.string.rs)+GH.getFormatedAmount(""+cartListModel.getGrandsum()));
                    Hawk.put(AppUtils.CART_ITEMS,cartListModel.getCartlistdata().size());
                    adapter = new CartListAdapter(mContext, cartListModel.getCartlistdata(), new OnCartClick() {
                        @Override
                        public void onProductClick(int position) {
                            Intent i = new Intent(mContext, ProductDetailActivity.class);
                            i.putExtra(AppUtils.ARG_PRODUCT_ID,cartlistdata.get(position).getProductId());
                            i.putExtra(AppUtils.ARG_PRODUCT_NAME,cartlistdata.get(position).getName());
                            startActivity(i);
                        }

                        @Override
                        public void onDeleteClick(final int position) {

                            AlertsUtils.getInstance().showYesNoAlert(mContext, getString(R.string.str_are_you_sure_remove_product), R.string.str_yes_caps, R.string.str_no_caps, new OnAlertDialogClicked() {
                                @Override
                                public void onPositiveClicked() {
                                    if (GH.isInternetOn(mContext))
                                    RequestFoDeleteCart(position);
                                }

                                @Override
                                public void onNagativeClicked() {

                                }
                            });


                        }

                        @Override
                        public void onPlusClick(int position) {
                            if (GH.isInternetOn(mContext))
                            RequestFoUpdate(position,true);
                        }

                        @Override
                        public void onMinusClick(int position) {
                            if(Integer.parseInt(cartlistdata.get(position).getQuantity())>1) {
                                if (GH.isInternetOn(mContext))
                                RequestFoUpdate(position, false);
                            }
                        }
                    });
                    rcCart.setLayoutManager(new LinearLayoutManager(mContext));
                    rcCart.setItemAnimator(new DefaultItemAnimator());
                    rcCart.setAdapter(adapter);

                    if(cartlistdata.size()==0){
                        rcCart.setVisibility(View.GONE);
                        empty_text.setVisibility(View.VISIBLE);
                        cvPayableDetail.setVisibility(View.GONE);
                        cvPaymentDetail.setVisibility(View.GONE);
                        ttvContinue.setVisibility(View.GONE);
                    }

                }else {
                    Hawk.put(AppUtils.CART_ITEMS,0);
                    cartlistdata.clear();
                    rcCart.setVisibility(View.GONE);
                    empty_text.setVisibility(View.VISIBLE);
                    ttvPrice.setText("0");
                    ttvTotalPrice.setText("0");
                    cvPayableDetail.setVisibility(View.GONE);
                    cvPaymentDetail.setVisibility(View.GONE);
                    ttvContinue.setVisibility(View.GONE);
                }
                ll_main.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CartListModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                ll_main.setVisibility(View.VISIBLE);
            }
        });
    }


    /**
     * Update Cart List
     */
    private void RequestFoUpdate(int position,Boolean isplus) {
        if (!progressDialog.isShowing())
            progressDialog.show();

     int quontity=Integer.parseInt(cartlistdata.get(position).getQuantity());
        if(isplus){
            quontity++;
        }else {
            quontity--;
        }
    JSONObject jsonObject=RequestBodyBuilder.getInstanse().Request_AddtoCart_UpdateCart(UserDetails.getInstance(mContext).getUser_id(),cartlistdata.get(position).getProductId(),""+quontity,cartlistdata.get(position).getPrice());

        rcCart.setVisibility(View.VISIBLE);
        empty_text.setVisibility(View.GONE);
        empty_text.setText(getString(R.string.str_your_cart_is_empty));
        Call<CartListModel> call = RetrofitBuilder.getInstance().getRetrofit().UpdateCart(jsonObject);

        call.enqueue(new Callback<CartListModel>() {
            @Override
            public void onResponse(Call<CartListModel> call, Response<CartListModel> response) {
                  cartListModel=response.body();
                if(cartListModel.getStatus().equalsIgnoreCase(AppUtils.Success)){
                    cartlistdata.clear();
                    cartlistdata.addAll(cartListModel.getCartlistdata());
                    ttvPrice.setText(mContext.getString(R.string.rs)+GH.getFormatedAmount(""+cartListModel.getGrandsum()));
                    ttvTotalPrice.setText(mContext.getString(R.string.rs)+GH.getFormatedAmount(""+cartListModel.getGrandsum()));
                    adapter.notifyDataSetChanged();

                    if(cartlistdata.size()==0){
                        rcCart.setVisibility(View.GONE);
                        empty_text.setVisibility(View.VISIBLE);

                    }
                }else {
                    cartlistdata.clear();
                    rcCart.setVisibility(View.GONE);
                    empty_text.setVisibility(View.VISIBLE);
                    ttvPrice.setText("0");
                    ttvTotalPrice.setText("0");
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CartListModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }


    /**
     * Delete Cart Item
     */
    private void RequestFoDeleteCart(int position) {
        if (!progressDialog.isShowing())
            progressDialog.show();


        rcCart.setVisibility(View.VISIBLE);
        empty_text.setVisibility(View.GONE);
        empty_text.setText(getString(R.string.str_your_cart_is_empty));
        Call<CartListModel> call = RetrofitBuilder.getInstance().getRetrofit().DeleteCartItem(RequestBodyBuilder.getInstanse().Request_Remove_Product_From_Cart(UserDetails.getInstance(mContext).getUser_id(),cartlistdata.get(position).getProductId()));

        call.enqueue(new Callback<CartListModel>() {
            @Override
            public void onResponse(Call<CartListModel> call, Response<CartListModel> response) {
                CartListModel     cartListModel=response.body();
                if(cartListModel.getStatus().equalsIgnoreCase(AppUtils.Success)){
                    cvPayableDetail.setVisibility(View.VISIBLE);
                    cvPaymentDetail.setVisibility(View.VISIBLE);

                    cartlistdata.clear();
                    cartlistdata.addAll(cartListModel.getCartlistdata());
                    ttvPrice.setText(mContext.getString(R.string.rs)+cartListModel.getGrandsum());
                    ttvTotalPrice.setText(mContext.getString(R.string.rs)+cartListModel.getGrandsum());
                    adapter.notifyDataSetChanged();
                    Hawk.put(AppUtils.CART_ITEMS,cartListModel.getCartlistdata().size());
                    if(cartlistdata.size()==0){
                        rcCart.setVisibility(View.GONE);
                        empty_text.setVisibility(View.VISIBLE);
                    }


                }else {
                    Hawk.put(AppUtils.CART_ITEMS,0);
                    cartlistdata.clear();
                    rcCart.setVisibility(View.GONE);
                    empty_text.setVisibility(View.VISIBLE);
                    ttvPrice.setText("0");
                    ttvTotalPrice.setText("0");
                    cvPayableDetail.setVisibility(View.GONE);
                    cvPaymentDetail.setVisibility(View.GONE);
                    ttvContinue.setVisibility(View.GONE);
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CartListModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==AppUtils.REQUEST_ORDER){
                Intent intent=new Intent(mContext,PaymentSucessActivity.class);
                startActivity(intent);
                finish();

            }
        }
    }
}
