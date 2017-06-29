package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.nectarbits.baraati.Models.ForgotPassword.ForgotPasswordModel;
import com.nectarbits.baraati.Models.Register.RegisterModel;
import com.nectarbits.baraati.Models.ResetPassword.ResetPasswordModel;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.L;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {

    private Context mContext;

    @Bind(R.id.txtCode)
    EditText txtCode;
    @Bind(R.id.txtPassword)
    EditText txtPassword;

    @OnClick(R.id.btnResend) void ResendPassword(){
        if (GH.isInternetOn(mContext))
        onForgotPasswordSendRequest();
    }

    @OnClick(R.id.btnReSet) void ResetPassword(){
        if (isValidToReset()) {
            if (GH.isInternetOn(mContext))
            onResetPassword();
        }
    }
    String code;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mContext = this;

        ButterKnife.bind(this);

        progressDialog = new ProgressDialog(mContext);
        code=getIntent().getStringExtra("resetCode");
    }


    @OnClick(R.id.btnBack)
    void OnBack(){
        finish();
    }


    private boolean isValidToReset(){
        if (!GH.isValidString(txtCode.getText().toString().trim())){
            L.showToast(mContext,getResources().getString(R.string.str_code_must));
            return false;
        } else if (!code.equals(txtCode.getText().toString().trim())){
            L.showToast(mContext,getResources().getString(R.string.str_reset_entered_code_is_invalid));
            return false;
        }
        else if (!GH.isValidString(txtPassword.getText().toString().trim())){
            L.showToast(mContext,getResources().getString(R.string.str_password_must));
            return false;
        }else if (!GH.isValidPassword(txtPassword.getText().toString().trim())){
            L.showToast(mContext,getResources().getString(R.string.str_password_should_atleast_6_character));
            return false;
        }
        else {
            return true;
        }
    }

    private void onResetPassword() {
        if(!progressDialog.isShowing())
            progressDialog.show();

        Call<ResetPasswordModel> call = RetrofitBuilder.getInstance().getRetrofit().ResetPassword(RequestBodyBuilder.getInstanse().getRequestResetPasswordResponse(
                getIntent().getStringExtra("userEmail"),txtPassword.getText().toString().trim()));

        call.enqueue(new Callback<ResetPasswordModel>() {
            @Override
            public void onResponse(Call<ResetPasswordModel> call, Response<ResetPasswordModel> response) {
                ResetPasswordModel resetPasswordResponseInfo = response.body();
                if (resetPasswordResponseInfo.getStatus().equals("Success")) {
                    L.showToast(mContext,resetPasswordResponseInfo.getMsg());
                    finish();
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ResetPasswordModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }


    private void onForgotPasswordSendRequest() {
        if(!progressDialog.isShowing())
            progressDialog.show();
        L.showError("Called");

        Call<ForgotPasswordModel> call = RetrofitBuilder.getInstance().getRetrofit().ForgotPassword(RequestBodyBuilder.getInstanse().getRequestForgotPasswordResponse(
                getIntent().getStringExtra("userEmail")));

        call.enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                ForgotPasswordModel forgotPasswordResponseInfo = response.body();
                if (forgotPasswordResponseInfo.getStatus().equals("Success")) {
                    code=forgotPasswordResponseInfo.getCode();
                    L.showToast(mContext,forgotPasswordResponseInfo.getMsg());
                } else {
                    L.showToast(mContext,forgotPasswordResponseInfo.getMsg());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }
}
