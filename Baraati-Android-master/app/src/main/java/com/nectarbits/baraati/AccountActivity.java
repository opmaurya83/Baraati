package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.SP;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountActivity extends AppCompatActivity {

    @Bind(R.id.ll_profile)
    LinearLayout llProfile;
    @Bind(R.id.ll_aboutus)
    LinearLayout llAboutus;
    @Bind(R.id.ll_contactus)
    LinearLayout llContactus;
    @Bind(R.id.ll_privacypolicy)
    LinearLayout llPrivacypolicy;
    @Bind(R.id.ll_termsofuse)
    LinearLayout llTermsofuse;
    Context mContext;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ll_logout)
    LinearLayout llLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        mContext = this;
        implementToolbar();

    }

    /**
     * set ToolBar
     */
    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.account));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @OnClick({R.id.ll_profile, R.id.ll_aboutus, R.id.ll_contactus, R.id.ll_privacypolicy, R.id.ll_termsofuse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_profile:
                Intent intent = new Intent(mContext, ProfileDetailActivity.class);
                intent.putExtra(AppUtils.ARG_IS_EDIT, false);
                startActivityForResult(intent, AppUtils.REQUEST_UPDATE_PROFILE);
                break;
            case R.id.ll_aboutus:
                startActivity(new Intent(mContext, AboutUsActivity.class));
                break;
            case R.id.ll_contactus:
             /*   startActivity(new Intent(mContext, ContactUsActivity.class));*/
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("vnd.android.cursor.item/email");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"email@baraati-inc.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Contact Us");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(emailIntent, "Send mail using..."));

                break;
            case R.id.ll_privacypolicy:
                startActivity(new Intent(mContext, PrivacyPolicyActivity.class));
                break;
            case R.id.ll_termsofuse:
                startActivity(new Intent(mContext, TermsOfUseActivity.class));
                break;
        }
    }

    @OnClick(R.id.ll_logout)
    public void onViewClicked() {
        if (SP.getPreferences(mContext, SP.LOGIN_STATUS).equalsIgnoreCase(SP.FLAG_YES)) {
            if (GH.isInternetOn(mContext)) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
