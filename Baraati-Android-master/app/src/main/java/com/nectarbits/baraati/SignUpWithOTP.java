package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.data.DataHolder;
import com.nectarbits.baraati.Chat.ui.activities.authorization.BaseAuthActivity;
import com.nectarbits.baraati.Chat.ui.activities.authorization.SignUpActivity;
import com.nectarbits.baraati.Chat.utils.helpers.GoogleAnalyticsHelper;
import com.nectarbits.baraati.Models.Register.OTPModel;
import com.nectarbits.baraati.Models.Register.RegisterModel;
import com.nectarbits.baraati.Singletone.RegisterSignletone;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.L;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.generalHelper.SP;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.quickblox.q_municate_core.core.command.Command;
import com.quickblox.q_municate_core.models.AppSession;
import com.quickblox.q_municate_core.qb.commands.QBUpdateUserCommand;
import com.quickblox.q_municate_core.qb.commands.rest.QBSignUpCommand;
import com.quickblox.q_municate_core.service.QBServiceConsts;
import com.quickblox.q_municate_db.managers.DataManager;
import com.quickblox.users.model.QBUser;

import org.json.JSONObject;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpWithOTP extends BaseAuthActivity {

    private Context mContext;

    @Bind(R.id.txtCode)
    EditText txtCode;
    private QBUser qbUser;
    RegisterModel registerResponseInfo=null;
    @OnClick(R.id.btnResend) void ResendPassword(){
      //  onForgotPasswordSendRequest();
        if (GH.isInternetOn(mContext))
        sendOTP();
    }

    @OnClick(R.id.btnReSet) void ResetPassword(){
       /* if (isValidToReset()) {*/
        if (GH.isInternetOn(mContext))
            onRegisterUser();
       /* }*/

    }
    String code;
    ProgressDialog progressDialog;
    public static String TAG=SignUpWithOTP.class.getSimpleName();
    private SignUpSuccessAction signUpSuccessAction;
    private UpdateUserSuccessAction updateUserSuccessAction;
    @Override
    protected int getContentResId() {
        return R.layout.activity_sign_up_with_otp;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_sign_up_with_otp);

        mContext = this;
        ButterKnife.bind(this);
        qbUser = new QBUser();
        progressDialog = new ProgressDialog(mContext);

    }


    @OnClick(R.id.btnBack)
    void OnBack(){
        finish();
    }


    private boolean isValidToReset(){
        if (!GH.isValidString(txtCode.getText().toString().trim())){
            L.showToast(mContext,getResources().getString(R.string.str_code_must));
            return false;
        } else if (!RegisterSignletone.getInstance().getCode().equals(txtCode.getText().toString().trim())){
            L.showToast(mContext,getResources().getString(R.string.str_reset_entered_code_is_invalid));
            return false;
        } else {
            return true;
        }

    }

    private void onRegisterUser(){

        Log.e(TAG, "onRegisterUser: name::"+RegisterSignletone.getInstance().getName() );
        Log.e(TAG, "onRegisterUser: email::"+RegisterSignletone.getInstance().getEmailid());
        Log.e(TAG, "onRegisterUser: password::"+RegisterSignletone.getInstance().getPassword());
        Log.e(TAG, "onRegisterUser: phone::"+RegisterSignletone.getInstance().getPhonenumber());
        if (!progressDialog.isShowing())
            progressDialog.show();
        JSONObject jsonObject=RequestBodyBuilder.getInstanse().get_Request_For_Register(
                RegisterSignletone.getInstance().getPassword(), RegisterSignletone.getInstance().getPhonenumber(),RegisterSignletone.getInstance().getName(),
                "", RegisterSignletone.getInstance().getEmailid());
        L.showError("Called::++"+jsonObject);

        Call<RegisterModel> call = RetrofitBuilder.getInstance().getRetrofit().Register(jsonObject);

        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                registerResponseInfo = response.body();
                if (registerResponseInfo.getStatus().equals("Success")) {
                    saveResponseToPreference();

                    qbUser.setFullName(RegisterSignletone.getInstance().getName());
                    qbUser.setEmail(RegisterSignletone.getInstance().getEmailid());
                    qbUser.setPassword(RegisterSignletone.getInstance().getEmailid());
                    qbUser.setExternalId(registerResponseInfo.getUserId());
                    qbUser.setPhone(RegisterSignletone.getInstance().getPhonenumber());
                    appSharedHelper.saveUsersImportInitialized(false);
                    DataManager.getInstance().clearAllTables();
                    AppSession.getSession().closeAndClear();
                    QBSignUpCommand.start(SignUpWithOTP.this, qbUser, null);

                    setResult(RESULT_OK);
                    finish();

                } else {
                    L.showToast(mContext, registerResponseInfo.getMsg());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }








    private void saveResponseToPreference() {
        SP.savePreferences(mContext,SP.USER_NAME, RegisterSignletone.getInstance().getName());
        SP.savePreferences(mContext,SP.USER_USERID, registerResponseInfo.getUserId());
        SP.savePreferences(mContext,SP.USER_EMAIL, RegisterSignletone.getInstance().getEmailid());
        SP.savePreferences(mContext,SP.USER_PHONE, RegisterSignletone.getInstance().getPhonenumber());


        SP.savePreferences(mContext,SP.LOGIN_STATUS,SP.FLAG_YES);
    }

    protected void performUpdateUserSuccessAction(Bundle bundle) {
        QBUser user = (QBUser) bundle.getSerializable(QBServiceConsts.EXTRA_USER);
        appSharedHelper.saveFirstAuth(true);
        appSharedHelper.saveSavedRememberMe(true);



    }

    private void performSignUpSuccessAction(Bundle bundle) {
        File image = (File) bundle.getSerializable(QBServiceConsts.EXTRA_FILE);
        QBUser user = (QBUser) bundle.getSerializable(QBServiceConsts.EXTRA_USER);
        QBUpdateUserCommand.start(SignUpWithOTP.this, user, image);


    }

    private class SignUpSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) throws Exception {
            appSharedHelper.saveUsersImportInitialized(false);
            performSignUpSuccessAction(bundle);
        }
    }

    private class UpdateUserSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) throws Exception {
            performUpdateUserSuccessAction(bundle);
        }
    }


    private void addActions() {
        addAction(QBServiceConsts.SIGNUP_SUCCESS_ACTION, signUpSuccessAction);
        addAction(QBServiceConsts.UPDATE_USER_SUCCESS_ACTION, updateUserSuccessAction);

        updateBroadcastActionList();
    }

    private void removeActions() {
        removeAction(QBServiceConsts.SIGNUP_SUCCESS_ACTION);
        removeAction(QBServiceConsts.UPDATE_USER_SUCCESS_ACTION);

        updateBroadcastActionList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addActions();
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeActions();
    }

    private void sendOTP(){


        if (!progressDialog.isShowing())
            progressDialog.show();
        JSONObject jsonObject= RequestBodyBuilder.getInstanse().Request_OPT(RegisterSignletone.getInstance().getPhonenumber());


        Call<OTPModel> call = RetrofitBuilder.getInstance().getRetrofit().SendOTP(jsonObject);

        call.enqueue(new Callback<OTPModel>() {
            @Override
            public void onResponse(Call<OTPModel> call, Response<OTPModel> response) {
                OTPModel    registerResponseInfo = response.body();
                if (registerResponseInfo.getStatus().equals("Success")) {



                    RegisterSignletone.getInstance().setCode(registerResponseInfo.getOtp());



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
