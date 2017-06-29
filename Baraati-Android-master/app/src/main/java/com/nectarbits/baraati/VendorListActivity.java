package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nectarbits.baraati.Adapters.VendorListAdapter;
import com.nectarbits.baraati.Interface.OnVendorSelect;
import com.nectarbits.baraati.Models.Vendor.VendorList;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nectarbits on 23/08/16.
 */
public class VendorListActivity extends AppCompatActivity {

    @Bind(R.id.recyclerviewBuyThings)
    RecyclerView recyclerviewBuyThings;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    Context mContext;
    ProgressDialog progressDialog;
    VendorList vendorList;
    VendorListAdapter vendorListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_things);

        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);

        implementToolbar();
        if (GH.isInternetOn(mContext))
        LoadVendorList();

        recyclerviewBuyThings.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerviewBuyThings.setItemAnimator(new DefaultItemAnimator());
    }

    private void LoadVendorList() {

        if(!progressDialog.isShowing())
            progressDialog.show();

        Call<VendorList> call = RetrofitBuilder.getInstance().getRetrofit().GetVendorList(RequestBodyBuilder.getInstanse().getRequestVendorList("3"));

        call.enqueue(new Callback<VendorList>() {
            @Override
            public void onResponse(Call<VendorList> call, Response<VendorList> response) {
                vendorList = response.body();

                if(vendorList.getStatus().equalsIgnoreCase(AppUtils.Success)){
                  /*  vendorListAdapter = new VendorListAdapter(mContext, vendorList.getVendorDetail(), new OnVendorSelect() {
                        @Override
                        public void onVendorSelectFromList(int position, String value) {
                            if(value.equalsIgnoreCase(AppUtils.KEY_PASS_DETAIL_VIEW)){
                                Intent i = new Intent(mContext,VenderDetailActivity.class);
                                i.putExtra(AppUtils.KEY_FOR_PASS_VENDERID,vendorList.getVendorDetail().get(position).getVendorId());
                                i.putExtra(AppUtils.KEY_FOR_PASS_VENDERNAME,vendorList.getVendorDetail().get(position).getVendorName());
                                i.putExtra(AppUtils.KEY_FOR_PASS_PRODUCTNAME,vendorList.getVendorDetail().get(position).getPname());
                                startActivity(i);
                            }else if(value.equalsIgnoreCase(AppUtils.KEY_PASS_VIEW_LIST)){
                                Intent i = new Intent(mContext,ProductTypeActivity.class);
                                i.putExtra(AppUtils.KEY_FOR_PASS_VENDERID,vendorList.getVendorDetail().get(position).getVendorId());
                                i.putExtra(AppUtils.KEY_FOR_PASS_VENDERNAME,vendorList.getVendorDetail().get(position).getVendorName());
                                i.putExtra(AppUtils.KEY_FOR_PASS_PRODUCTNAME,vendorList.getVendorDetail().get(position).getPname());
                                startActivity(i);
                            }
                        }

                    });*/

                    recyclerviewBuyThings.setAdapter(vendorListAdapter);
                }else{

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<VendorList> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void implementToolbar() {
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
