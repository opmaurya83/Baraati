package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.graphics.RadialGradient;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.nectarbits.baraati.Singletone.CartProcessUtil;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SelectPaymentActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.rbCashOnDelivery)
    CheckBox rbCashOnDelivery;
    @Bind(R.id.rbCrediCard)
     CheckBox rbCrediCard;
    @Bind(R.id.rbDebitCard)
    CheckBox rbDebitCard;
    @Bind(R.id.rbEMI)
    CheckBox rbEMI;
    @Bind(R.id.rbNetBanking)
    CheckBox rbNetBanking;

    @Bind(R.id.ttvPrice)
    TextViewTitle ttvPrice;
    @Bind(R.id.ttvTotalPrice)
    TextViewTitle ttvTotalPrice;

    Context mContext;
    ProgressDialog progressDialog;
    Boolean is_select_payment=false;
    /**************Click*************/
    @OnClick(R.id.ttvPlaceOrder)
    void placeOrder(){
        if(is_select_payment) {
            Intent intent = new Intent(mContext, PayActivity.class);
            startActivityForResult(intent, AppUtils.REQUEST_ORDER);
        }else{
            Toast.makeText(mContext, getString(R.string.str_please_select_payment_method), Toast.LENGTH_SHORT).show();
        }
    }

    /*@OnCheckedChanged(R.id.rbNetBanking)
    void OnCheckedrbNetBanking(View buttonView){
        if(((CheckBox)buttonView).isChecked())
            setChangeChecks(0);
    }
    @OnCheckedChanged(R.id.rbCrediCard)
    void OnCheckedrbCrediCard(View buttonView){
        if(((CheckBox)buttonView).isChecked())
        setChangeChecks(1);
    }
    @OnCheckedChanged(R.id.rbEMI)
    void OnCheckedrbEMI(View buttonView){
        if(((CheckBox)buttonView).isChecked())
        setChangeChecks(2);
    }
    @OnCheckedChanged(R.id.rbDebitCard)
    void OnCheckedrbDebitCard(View buttonView){
        if(((CheckBox)buttonView).isChecked())
        setChangeChecks(3);
    }
    @OnCheckedChanged(R.id.rbCashOnDelivery)
    void OnCheckedrbCashOnDelivery(View buttonView){
        if(((CheckBox)buttonView).isChecked())
        setChangeChecks(4);
    }*/
    /**************Click*************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_payment);
        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        implementToolbar();

        rbNetBanking.setOnCheckedChangeListener(this);
        rbCrediCard.setOnCheckedChangeListener(this);
        rbEMI.setOnCheckedChangeListener(this);
        rbDebitCard.setOnCheckedChangeListener(this);
        rbCashOnDelivery.setOnCheckedChangeListener(this);


        if(CartProcessUtil.getInstance().getCartListModel()!=null){
            ttvPrice.setText(mContext.getString(R.string.rs)+GH.getFormatedAmount(""+CartProcessUtil.getInstance().getCartListModel().getGrandsum()));
            ttvTotalPrice.setText(mContext.getString(R.string.rs)+GH.getFormatedAmount(""+CartProcessUtil.getInstance().getCartListModel().getGrandsum()));
        }
    }

    /**
     * set ToolBar
     */
    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.str_select_payment));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    void setChangeChecks(int index){
        switch (index){
            case 0:
                is_select_payment=true;
                rbNetBanking.setChecked(true);
                rbCrediCard.setChecked(false);
                rbEMI.setChecked(false);
                rbDebitCard.setChecked(false);
                rbCashOnDelivery.setChecked(false);
                break;
            case 1:
                is_select_payment=true;
                rbNetBanking.setChecked(false);
                rbCrediCard.setChecked(true);
                rbEMI.setChecked(false);
                rbDebitCard.setChecked(false);
                rbCashOnDelivery.setChecked(false);
                break;
            case 2:
                is_select_payment=true;
                rbNetBanking.setChecked(false);
                rbCrediCard.setChecked(false);
                rbEMI.setChecked(true);
                rbDebitCard.setChecked(false);
                rbCashOnDelivery.setChecked(false);
                break;
            case 3:
                is_select_payment=true;
                rbNetBanking.setChecked(false);
                rbCrediCard.setChecked(false);
                rbEMI.setChecked(false);
                rbDebitCard.setChecked(true);
                rbCashOnDelivery.setChecked(false);
                break;
            case 4:
                is_select_payment=true;
                rbNetBanking.setChecked(false);
                rbCrediCard.setChecked(false);
                rbEMI.setChecked(false);
                rbDebitCard.setChecked(false);
                rbCashOnDelivery.setChecked(true);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.rbNetBanking:
                if(isChecked)
                    setChangeChecks(0);
                break;
            case R.id.rbCrediCard:
                if(isChecked)
                    setChangeChecks(1);
                break;
            case R.id.rbEMI:
                if(isChecked)
                    setChangeChecks(2);
                break;
            case R.id.rbDebitCard:
                if(isChecked)
                    setChangeChecks(3);
                break;
            case R.id.rbCashOnDelivery:
                if(isChecked)
                    setChangeChecks(4);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==AppUtils.REQUEST_ORDER){
                setResult(RESULT_OK);
                finish();

            }
        }
    }
}
