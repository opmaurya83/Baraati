package com.nectarbits.baraati;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.nectarbits.baraati.Singletone.CartProcessUtil;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.generalHelper.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG = PaymentActivity.class.getSimpleName();
    @Bind(R.id.webview)
    WebView webView;

    private Context mContext;
    ProgressDialog progressDialog;
    String url_1="";
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ButterKnife.bind(this);
        mContext=this;
        progressDialog = new ProgressDialog(mContext);


        JIFace iface=new JIFace();
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(iface, "Android");
        webView.getSettings().setAllowContentAccess(true);
        try{
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        }catch (Exception ex){

        }

        JSONObject jsonObject = new JSONObject();
        try {
        jsonObject.put("address", CartProcessUtil.getInstance().getSetectedAddress());
        jsonObject.put("userid", UserDetails.getInstance(mContext).getUser_id());
        }catch (Exception ex){

        }
        webView.loadUrl("http://iappbits.in/barati/webservice/paynow?pay_Request="+jsonObject.toString());
        progressDialog.show();
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                Log.e(TAG, "shouldOverrideUrlLoading: "+request.getUrl().getPath());

                return super.shouldOverrideUrlLoading(view, request);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                // TODO Auto-generated method stub

                Log.e(TAG, "shouldOverrideUrlLoading: "+url);

                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                // TODO Auto-generated method stub

                super.onPageFinished(view, url);

                Log.e(TAG, "onPageFinished: "+url);


                if(url.contains("paymentSuccess")) {

                    url_1=url;
                    setResult(RESULT_OK);
                    finish();



                }else if(url.contains("cancelPayment")){
                    setResult(RESULT_CANCELED);
                    finish();
                }


                progressDialog.dismiss();
            }
        });



    }

    class JIFace {
        public void print(String data) {
            data =""+data+"";
            System.out.println(data);
            //DO the stuff
        }
    }

   /* AsyncTask asyncTask=new AsyncTask() {
        @Override
        protected String doInBackground(Object[] objects) {
            URL aUrl = null;
            try {
                aUrl = new URL(url_1);
                URLConnection conn = aUrl.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();

                BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr;
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                Log.e(TAG, "doInBackground: "+responseStrBuilder.toString());
                JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

                Log.e(TAG, "shouldOverrideUrlLoading: "+jsonObject.toString());
                // Use inputstream code to read the json..

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    };*/
}
