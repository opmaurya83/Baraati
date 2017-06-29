package com.nectarbits.baraati;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nectarbits.baraati.R;
import com.nectarbits.baraati.generalHelper.ProgressDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nectarbits on 23/08/16.
 */
public class TermsOfUseActivity extends AppCompatActivity {

    @Bind(R.id.webviewTermsofuse)
    WebView webviewTermsofuse;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    ProgressDialog progressDialog;
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use);

        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);

        implementToolbar();
        webviewTermsofuse.loadUrl(getString(R.string.str_url_terms_of_use));

        progressDialog.show();
        webviewTermsofuse.setWebViewClient(new WebViewClient());
    }

    private void implementToolbar() {

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back_button);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class WebViewClient extends android.webkit.WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            // TODO Auto-generated method stub

            super.onPageFinished(view, url);

            progressDialog.dismiss();
        }
    }
}
