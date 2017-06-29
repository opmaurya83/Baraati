package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.nectarbits.baraati.Models.Login.LoginModel;
import com.nectarbits.baraati.Models.Register.OTPModel;
import com.nectarbits.baraati.Singletone.LoginResponseSingletone;
import com.nectarbits.baraati.Singletone.RegisterSignletone;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.L;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.generalHelper.SP;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPActivity extends AppCompatActivity {

    @Bind(R.id.txtCode)
    EditText txtCode;

    Context mContext;
    ProgressDialog progressDialog;
    @OnClick(R.id.btnResend)
    void onResend(){
        if (GH.isInternetOn(mContext))
        sendOTP();
    }

    @OnClick(R.id.btnVerify)
    void onVerify(){

       /* if(isValidToReset())
        {*/
            LoginResponseSingletone.getInstance().getLoginResponse().getUserDetail().setContact(LoginResponseSingletone.getInstance().getPhonenumber());
            saveResponseToPreference(LoginResponseSingletone.getInstance().getLoginResponse());
            setResult(RESULT_OK);
            finish();
        /*}*/
        /*if(txtCode.getText().toString().equalsIgnoreCase(LoginResponseSingletone.getInstance().getOtp())){

           *//* startActivity(new Intent(mContext, MainActivity.class));
            finish();*//*
        }else{
            Toast.makeText(mContext,getString(R.string.str_opt_does_not_match), Toast.LENGTH_SHORT).show();
        }
*/
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mContext = this;
        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(mContext);
    }

    private boolean isValidToReset(){
        if (!GH.isValidString(txtCode.getText().toString().trim())){
            L.showToast(mContext,getResources().getString(R.string.str_code_must));
            return false;
        } else if (!LoginResponseSingletone.getInstance().getOtp().equals(txtCode.getText().toString().trim())){
            L.showToast(mContext,getResources().getString(R.string.str_reset_entered_code_is_invalid));
            return false;
        } else {
            return true;
        }

    }

    private void saveResponseToPreference(LoginModel responseInfo) {
        SP.savePreferences(mContext,SP.USER_USERID,responseInfo.getUserDetail().getUserId());
        SP.savePreferences(mContext,SP.USER_EMAIL,""+responseInfo.getUserDetail().getEmail());
        SP.savePreferences(mContext,SP.USER_NAME,""+responseInfo.getUserDetail().getFirstName());
        SP.savePreferences(mContext,SP.USER_ADDRESS_1,""+responseInfo.getUserDetail().getAddressLine1());
        SP.savePreferences(mContext,SP.USER_ADDRESS_2,""+responseInfo.getUserDetail().getAddressline2());
        SP.savePreferences(mContext,SP.USER_COUNTRY,""+responseInfo.getUserDetail().getCountry());
        SP.savePreferences(mContext,SP.USER_PHONE,""+responseInfo.getUserDetail().getContact());
        SP.savePreferences(mContext,SP.USER_STATE,""+responseInfo.getUserDetail().getState());
        SP.savePreferences(mContext,SP.USER_CITY,""+responseInfo.getUserDetail().getCity());
        SP.savePreferences(mContext,SP.USER_WEDDING_DATE,""+responseInfo.getUserDetail().getWeddingdate());
        SP.savePreferences(mContext,SP.USER_ZIP,""+responseInfo.getUserDetail().getZipcode());
        SP.savePreferences(mContext,SP.USER_GENDER,""+responseInfo.getUserDetail().getGendar());
        SP.savePreferences(mContext,SP.LOGIN_STATUS,SP.FLAG_YES);
    }


    private void sendOTP(){


        if (!progressDialog.isShowing())
            progressDialog.show();
        JSONObject jsonObject= RequestBodyBuilder.getInstanse().Request_OPT(LoginResponseSingletone.getInstance().getOtp());


        Call<OTPModel> call = RetrofitBuilder.getInstance().getRetrofit().SendOTP(jsonObject);

        call.enqueue(new Callback<OTPModel>() {
            @Override
            public void onResponse(Call<OTPModel> call, Response<OTPModel> response) {
                OTPModel    registerResponseInfo = response.body();
                if (registerResponseInfo.getStatus().equals("Success")) {




                    LoginResponseSingletone.getInstance().setOtp(registerResponseInfo.getOtp());


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
