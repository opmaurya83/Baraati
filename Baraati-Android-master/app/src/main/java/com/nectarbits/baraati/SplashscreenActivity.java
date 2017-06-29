package com.nectarbits.baraati;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;

import com.nectarbits.baraati.Adapters.CartListAdapter;
import com.nectarbits.baraati.Chat.App;
import com.nectarbits.baraati.Chat.ui.activities.authorization.BaseAuthActivity;
import com.nectarbits.baraati.Chat.ui.activities.authorization.LandingActivity;
import com.nectarbits.baraati.Chat.ui.activities.authorization.SplashActivity;
import com.nectarbits.baraati.Chat.utils.helpers.LoginHelper;
import com.nectarbits.baraati.Chat.utils.listeners.ExistingQbSessionListener;
import com.nectarbits.baraati.Interface.OnAlertDialogClicked;
import com.nectarbits.baraati.Interface.OnCartClick;
import com.nectarbits.baraati.Models.CartList.CartListModel;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.Utils.ContactService;
import com.nectarbits.baraati.generalHelper.AlertsUtils;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.SP;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.orhanobut.hawk.Hawk;
import com.quickblox.q_municate_core.models.AppSession;
import com.vistrav.ask.Ask;
import com.vistrav.ask.annotations.AskGranted;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashscreenActivity extends BaseAuthActivity implements ExistingQbSessionListener {

    private static final String TAG = SplashscreenActivity.class.getSimpleName();
    private static int SPLASH_TIME_OUT = 2000;
    private Context context;

    @Override
    protected int getContentResId() {
        return R.layout.activity_splashscreen;
    }

    @Override
    public void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splashscreen);
        context=this;



        if (isNetworkAvailable()) {
            Log.d(TAG, "onCreate checkStartExistSession()");
            LoginHelper loginHelper = new LoginHelper(this, this);
            loginHelper.checkStartExistSession();

        } else if (LoginHelper.isCorrectOldAppSession()) {
            Log.d(TAG, "onCreate startMainActivity()");
            //startMainActivity();
        }/* else {

        }*/
        startLandingActivity();
        if (SP.getPreferences(context, SP.LOGIN_STATUS).equalsIgnoreCase(SP.FLAG_YES)) {
            if (GH.isInternetOn(context))
          RequestFoCartList();
        }



    }



    @Override
    protected void onResume() {
        super.onResume();
       /* if (isLoggedInToServer()) {
            startMainActivity(true);
        }*/
    }

    @Override
    public void onStartSessionSuccess() {
        appSharedHelper.saveSavedRememberMe(true);
        /*startMainActivity(true);*/
    }

    @Override
    public void onStartSessionFail() {
       /* startLandingActivity();*/
    }

    @Override
    public void checkShowingConnectionError() {
        // nothing. Toolbar is missing.
    }


    private void startLandingActivity() {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                Log.e(TAG, "run: "+SP.getPreferences(context,AppUtils.INTRO)+" "+SP.getPreferences(context, SP.LOGIN_STATUS));
                if(!SP.getPreferences(context,AppUtils.INTRO).equalsIgnoreCase("true") && !SP.getPreferences(context, SP.LOGIN_STATUS).equalsIgnoreCase(SP.FLAG_YES)){
                    SP.savePreferences(context,AppUtils.INTRO,"true");
                    Intent i = new Intent(context, Intro_Activity.class);
                    startActivity(i);
                    finish();
                }
                else if (SP.getPreferences(context, SP.LOGIN_STATUS).equalsIgnoreCase(SP.FLAG_YES)) {
                    if(!isLoggedInToServer() && !AppSession.getSession().isSessionExist())
                    {
                        login(UserDetails.getInstance(context).getUser_emailid(),UserDetails.getInstance(context).getUser_emailid());
                    }else{
                        Log.e(TAG, "run: logged in to quickblox"+ AppSession.getSession().isSessionExist());
                    }
                    startActivity(new Intent(context, MainActivity.class));
                    finish();
                } else {

                    Intent i = new Intent(SplashscreenActivity.this, LoginSignUpActivity.class);
                    startActivity(i);
                    finish();


                }
            }
        }, SPLASH_TIME_OUT);
    }

    private void RequestFoCartList() {

        Call<CartListModel> call = RetrofitBuilder.getInstance().getRetrofit().GetCartList(RequestBodyBuilder.getInstanse().Request_CartList(UserDetails.getInstance(context).getUser_id()));

        call.enqueue(new Callback<CartListModel>() {
            @Override
            public void onResponse(Call<CartListModel> call, Response<CartListModel> response) {
                CartListModel   cartListModel=response.body();
                if(cartListModel.getStatus().equalsIgnoreCase(AppUtils.Success)){
                    Log.e(TAG, "onResponse: cart size::"+cartListModel.getCartlistdata().size());
                    Hawk.put(AppUtils.CART_ITEMS,cartListModel.getCartlistdata().size());
                }else {
                    Log.e(TAG, "onResponse: "+response.body().getStatus());
                    Hawk.put(AppUtils.CART_ITEMS,0);
                }

            }

            @Override
            public void onFailure(Call<CartListModel> call, Throwable t) {
                t.printStackTrace();

            }
        });
    }


}