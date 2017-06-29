package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;


import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.IndicatorController;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.nectarbits.baraati.IntroFragment.Intro_fragment_1;
import com.nectarbits.baraati.IntroFragment.Intro_fragment_2;
import com.nectarbits.baraati.IntroFragment.Intro_fragment_3;
import com.nectarbits.baraati.IntroFragment.Intro_fragment_4;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.SP;


public class Intro_Activity extends AppIntro2 {

    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_intro_);


        setIndicatorColor(Color.parseColor("#FFFFFF"),Color.parseColor("#FFFFFF"));


        mContext=this;
        addSlide(new Intro_fragment_1());
        addSlide(new Intro_fragment_2());
        addSlide(new Intro_fragment_3());
        addSlide(new Intro_fragment_4());



        // OPTIONAL METHODS
        // Override bar/separator color.
        showStatusBar(false);
        setNavBarColor(R.color.colorPrimary);



        showSkipButton(false);
        setOffScreenPageLimit(4);
       // setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
       // showSkipButton(true);
        //setProgressButtonEnabled(true);


        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
       /* setVibrate(true);
        setVibrateIntensity(30);*/


    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
       // SP.savePreferences(mContext,AppUtils.INTRO,"true");
        Intent i = new Intent(Intro_Activity.this, LoginSignUpActivity.class);
        startActivity(i);

        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        //SP.savePreferences(mContext,AppUtils.INTRO,"true");
        SP.savePreferences(this,AppUtils.INTRO,"true");
        Intent i = new Intent(Intro_Activity.this, LoginSignUpActivity.class);
        startActivity(i);
               finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
       // setIndicatorColor(R.color.colorPrimary,R.color.white);
    }


}
