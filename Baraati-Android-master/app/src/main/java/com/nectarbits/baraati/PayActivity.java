package com.nectarbits.baraati;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.nectarbits.baraati.Models.AddtoCart.AddtoCartModel;
import com.nectarbits.baraati.Models.OrderPlace.OrderPlaceModel;
import com.nectarbits.baraati.Singletone.CartProcessUtil;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ttvMM)
    TextViewTitle ttvMonth;
    @Bind(R.id.tvtGrandTotal)
    TextViewTitle tvtGrandTotal;
    @Bind(R.id.ttvYYYY)
    TextViewTitle ttvYYYY;
    Context mContext;
    ProgressDialog progressDialog;
    final CharSequence[] items_month = { "01", "02" ,"03","04","05","06","07","08","09","10","11","12" };
    final CharSequence[] items_year = { "2016", "2017" ,"2018","2019","2020","2021","2022","2023","2024","2025","2026","2027" };
    /************Click********************/
    @OnClick(R.id.ttvMM)
    void onMM(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.str_epiry_month));

        builder.setSingleChoiceItems(items_month, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        ttvMonth.setText(items_month[item]);


                    }
                });
        builder.setPositiveButton(getString(R.string.str_done), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(true);
     /*   builder.setPositiveButton(btnText[0], listener);
        if (btnText.length != 1) {
            builder.setNegativeButton(btnText[1], listener);
        }*/
        builder.show();
    }
    @OnClick(R.id.ttvYYYY)
    void onYYYY(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.str_epiry_year));

        builder.setSingleChoiceItems(items_year, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        ttvYYYY.setText(items_year[item]);


                    }
                });
        builder.setPositiveButton(getString(R.string.str_done), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(true);
        builder.show();
    }

    @OnClick(R.id.ttvPay)
    void onPay(){

      //  PlaceOrder();
        Intent intent=new Intent(mContext,PaymentActivity.class);
        startActivityForResult(intent,AppUtils.REQUEST_PAYMENT);
    }
    /************Click********************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        implementToolbar();
        tvtGrandTotal.setText(getString(R.string.rs)+GH.getFormatedAmount(""+CartProcessUtil.getInstance().getCartListModel().getGrandsum()));
    }

    /**
     * set ToolBar
     */
    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.str_pay));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private void PlaceOrder() {

        if(!progressDialog.isShowing())
            progressDialog.show();

        Call<OrderPlaceModel> call = RetrofitBuilder.getInstance().getRetrofit().PlaceOrder(RequestBodyBuilder.getInstanse().Request_PlaceOrder(UserDetails.getInstance(mContext).getUser_id(),""+CartProcessUtil.getInstance().getSetectedAddress(),CartProcessUtil.getInstance().getCartListModel().getCartlistdata()));

        call.enqueue(new Callback<OrderPlaceModel>() {
            @Override
            public void onResponse(Call<OrderPlaceModel> call, Response<OrderPlaceModel> response) {
                OrderPlaceModel detailModel=response.body();
                if(detailModel.getStatus().equalsIgnoreCase(AppUtils.Success)){
                    setResult(RESULT_OK);
                    finish();

                }else{
                    Toast.makeText(PayActivity.this, detailModel.getMsg(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<OrderPlaceModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==AppUtils.REQUEST_PAYMENT && resultCode==RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }else if(requestCode==AppUtils.REQUEST_PAYMENT && resultCode==RESULT_CANCELED){
            Toast.makeText(PayActivity.this, "You have canceled order payment...", Toast.LENGTH_SHORT).show();
        }

    }
}
