package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.nectarbits.baraati.Models.Register.OTPModel;
import com.nectarbits.baraati.Models.Register.UserCheck.UserCheckModel;
import com.nectarbits.baraati.Singletone.LoginResponseSingletone;
import com.nectarbits.baraati.Singletone.RegisterSignletone;
import com.nectarbits.baraati.generalHelper.AlertsUtils;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.L;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileVerificationActivity extends AppCompatActivity {

    @Bind(R.id.txtPhoneNumber)
    EditText txtPhoneNumber;

    @OnClick(R.id.btnNext)
    void onNext(){

        if (!GH.isValidMobile(txtPhoneNumber.getText().toString().trim())) {
            L.showToast(mContext, getString(R.string.str_phone_must));
        }else  if (!GH.isValidMobile(txtPhoneNumber.getText().toString().trim())) {
            L.showToast(mContext, getString(R.string.str_phone_must_range));
        }
        else {
            if (GH.isInternetOn(mContext))
            onUserCheck();

        }

    }
    Context mContext;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_verification);
        mContext = this;
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(mContext);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==AppUtils.REQUEST_LOGIN)
        {
            setResult(RESULT_OK);
            finish();
        }
    }


    private void onUserCheck(){


        if (!progressDialog.isShowing())
            progressDialog.show();
        JSONObject jsonObject= RequestBodyBuilder.getInstanse().Request_UserExistance("","91"+txtPhoneNumber.getText().toString());


        Call<UserCheckModel> call = RetrofitBuilder.getInstance().getRetrofit().CheckUserExistance(jsonObject);

        call.enqueue(new Callback<UserCheckModel>() {
            @Override
            public void onResponse(Call<UserCheckModel> call, Response<UserCheckModel> response) {
                UserCheckModel    registerResponseInfo = response.body();
                if (registerResponseInfo.getStatus().equals("Success")) {

                    String msg="";
                    int i=0;
                   /* if(registerResponseInfo.getEmailstatus().equalsIgnoreCase("1"))
                    {
                        i=1;
                        msg=getString(R.string.mailid_already_register_with_baraati);
                    }*/
                    if(registerResponseInfo.getPhonestatus().equalsIgnoreCase("1"))
                    {
                        i++;
                        if(i>0)
                            msg=msg+"\n";

                        msg=msg+getString(R.string.phonenuymber_already_register_with_baraati);
                    }



                    if(i==0) {
                        LoginResponseSingletone.getInstance().setPhonenumber("91"+txtPhoneNumber.getText().toString().trim());
                        LoginResponseSingletone.getInstance().setOtp(/*registerResponseInfo.getOtp()*/"");
                        Intent intent=new Intent(mContext,OTPActivity.class);
                        startActivityForResult(intent, AppUtils.REQUEST_LOGIN);

                      //  sendOTP();
                    }else{
                        AlertsUtils.getInstance().showOKAlert(mContext,msg);
                    }

                } else {

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<UserCheckModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }


    private void sendOTP(){


        if (!progressDialog.isShowing())
            progressDialog.show();
        JSONObject jsonObject= RequestBodyBuilder.getInstanse().Request_OPT("91"+txtPhoneNumber.getText().toString());


        Call<OTPModel> call = RetrofitBuilder.getInstance().getRetrofit().SendOTP(jsonObject);

        call.enqueue(new Callback<OTPModel>() {
            @Override
            public void onResponse(Call<OTPModel> call, Response<OTPModel> response) {
                OTPModel    registerResponseInfo = response.body();
                if (registerResponseInfo.getStatus().equals("Success")) {



                    LoginResponseSingletone.getInstance().setPhonenumber("91"+txtPhoneNumber.getText().toString().trim());
                    LoginResponseSingletone.getInstance().setOtp(registerResponseInfo.getOtp());
                    Intent intent=new Intent(mContext,OTPActivity.class);
                    startActivityForResult(intent, AppUtils.REQUEST_LOGIN);


                } else {

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<OTPModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }
}
