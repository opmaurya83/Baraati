package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.nectarbits.baraati.Models.ForgotPassword.ForgotPasswordModel;
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

public class ForgetPassword extends AppCompatActivity {


    private Context mContext;

    @Bind(R.id.txtEmailAddress)
    EditText etEmail;

    @OnClick(R.id.btnSend)
    void ForgotPassword() {
        if (!GH.isValidString(etEmail.getText().toString().trim())) {
            L.showToast(mContext,getResources().getString(R.string.str_email_must));
        }
        else if (!GH.isValidEmail(etEmail.getText().toString().trim())) {
            L.showToast(mContext,getResources().getString(R.string.str_enter_valid_email));

        } else {
            if (GH.isInternetOn(mContext))
            onForgotPasswordSendRequest();
        }
    }

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mContext = this;

        ButterKnife.bind(this);

        progressDialog=new ProgressDialog(mContext);
    }

    @OnClick(R.id.btnBack)
    void OnBack(){
        finish();
    }


    private void onForgotPasswordSendRequest() {
        if(!progressDialog.isShowing())
            progressDialog.show();
        L.showError("Called");

        Call<ForgotPasswordModel> call = RetrofitBuilder.getInstance().getRetrofit().ForgotPassword(RequestBodyBuilder.getInstanse().getRequestForgotPasswordResponse(
                etEmail.getText().toString().trim()));

        call.enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                ForgotPasswordModel forgotPasswordResponseInfo = response.body();
                if (forgotPasswordResponseInfo.getStatus().equals("Success")) {
                    L.showError(forgotPasswordResponseInfo.getCode());
                    startActivity(new Intent(mContext, ResetPassword.class).putExtra("userEmail",etEmail.getText().toString().trim()).putExtra("resetCode",forgotPasswordResponseInfo.getCode()));
                    finish();
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
