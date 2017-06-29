package com.nectarbits.baraati;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.nectarbits.baraati.generalHelper.ProgressDialog;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BlogActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.webview)
    WebView webView;

    private Context mContext;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        ButterKnife.bind(this);
        mContext = this;
        implementToolbar();
        progressDialog = new ProgressDialog(mContext);

       // webView.loadUrl(getString(R.string.str_url_about_us));
        webView.loadUrl("https://in.pinterest.com/NectarBits/photo-montage-app-ios567/");
        progressDialog.show();
        webView.setWebViewClient(new BlogActivity.WebViewClient());

    }

    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.str_pint));
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
