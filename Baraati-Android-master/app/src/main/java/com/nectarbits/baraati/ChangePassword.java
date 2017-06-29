package com.nectarbits.baraati;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nectarbits.baraati.Models.ChangePasswordModel.ChangePasswordModel;
import com.nectarbits.baraati.Models.ResetPassword.ResetPasswordModel;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.EditTextTitle;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.L;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    @Bind(R.id.evOldPassword)
    EditTextTitle evOldPassword;

    @Bind(R.id.evNewPassword)
    EditTextTitle evNewPassword;

    @Bind(R.id.evConfirmPassword)
    EditTextTitle evConfirmPassword;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private Context mContext;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ButterKnife.bind(this);
        mContext=this;
        progressDialog = new ProgressDialog(mContext);

        implementToolbar();
    }

    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.str_change_password));
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
                if (evOldPassword.getText().toString().trim().equals("")) {
                    L.showToast(mContext, getString(R.string.str_old_pass_must_not));
                }else if(evNewPassword.getText().toString().trim().equals("")) {
                    L.showToast(mContext, getString(R.string.str_new_pass_must_not));
                }else if(evConfirmPassword.getText().toString().trim().equals("")) {
                    L.showToast(mContext, getString(R.string.str_conf_pass_must_not));
                }
                else if(evNewPassword.getText().toString().trim().length()<6 || evNewPassword.getText().toString().trim().length()>15) {
                    L.showToast(mContext, getString(R.string.str_new_password_should_atleast_6_character));
                }
                else if(evConfirmPassword.getText().toString().trim().length()<6 || evConfirmPassword.getText().toString().trim().length()>15) {
                    L.showToast(mContext, getString(R.string.str_conf_password_should_atleast_6_character));
                }
                else if(!evNewPassword.getText().toString().trim().equals(evConfirmPassword.getText().toString().trim())) {
                    L.showToast(mContext, getString(R.string.str__new_confirm_pass_must_not));
                }else {
                    if (GH.isInternetOn(mContext))
                    onChangePassowrd();
                }
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void onChangePassowrd() {
        if(!progressDialog.isShowing())
            progressDialog.show();

        Call<ChangePasswordModel> call = RetrofitBuilder.getInstance().getRetrofit().ChangePassword(RequestBodyBuilder.getInstanse().getRequest_ChangePasswordResponse(
                UserDetails.getInstance(mContext).getUser_emailid(),evOldPassword.getText().toString(),evConfirmPassword.getText().toString()));

        call.enqueue(new Callback<ChangePasswordModel>() {
            @Override
            public void onResponse(Call<ChangePasswordModel> call, Response<ChangePasswordModel> response) {
                ChangePasswordModel changePasswordModel = response.body();
                if (changePasswordModel.getStatus().equals("Success")) {
                    L.showToast(mContext,getString(R.string.str_password_changed_success));
                    finish();
                }else{
                    L.showToast(mContext,changePasswordModel.getMsg());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

}
