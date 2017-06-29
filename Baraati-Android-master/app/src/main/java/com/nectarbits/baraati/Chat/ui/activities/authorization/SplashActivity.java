package com.nectarbits.baraati.Chat.ui.activities.authorization;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.nectarbits.baraati.R;
import com.nectarbits.baraati.Chat.utils.helpers.LoginHelper;
import com.nectarbits.baraati.Chat.utils.listeners.ExistingQbSessionListener;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseAuthActivity implements ExistingQbSessionListener {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final int DELAY_FOR_OPENING_LANDING_ACTIVITY = 1000;

    public static void start(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        if (isNetworkAvailable()) {
            Log.d(TAG, "onCreate checkStartExistSession()");
            LoginHelper loginHelper = new LoginHelper(this, this);
            loginHelper.checkStartExistSession();
        } else if (LoginHelper.isCorrectOldAppSession()) {
            Log.d(TAG, "onCreate startMainActivity()");
            startMainActivity();
        } else {
            startLandingActivity();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isLoggedInToServer()) {
            startMainActivity(true);
        }
    }

    @Override
    public void onStartSessionSuccess() {
        appSharedHelper.saveSavedRememberMe(true);
        startMainActivity(true);
    }

    @Override
    public void onStartSessionFail() {
        startLandingActivity();
    }

    @Override
    public void checkShowingConnectionError() {
        // nothing. Toolbar is missing.
    }

    private void startLandingActivity() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                LandingActivity.start(SplashActivity.this);
                finish();
            }
        }, DELAY_FOR_OPENING_LANDING_ACTIVITY);
    }
}