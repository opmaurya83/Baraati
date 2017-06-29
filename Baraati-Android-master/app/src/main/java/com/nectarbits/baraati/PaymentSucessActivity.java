package com.nectarbits.baraati;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nectarbits.baraati.generalHelper.ProgressDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentSucessActivity extends AppCompatActivity {

    Context mContext;
    ProgressDialog progressDialog;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.ttvDone)
    void onDone(){
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_sucess);
        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        implementToolbar();
    }
    /**
     * set ToolBar
     */
    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.str_Order_places));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
