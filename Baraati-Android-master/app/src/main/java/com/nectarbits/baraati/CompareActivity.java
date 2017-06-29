package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.nectarbits.baraati.Adapters.ProductCompareListAdapter;
import com.nectarbits.baraati.Adapters.SubCategoryAdapter;
import com.nectarbits.baraati.Interface.OnProductSelect;
import com.nectarbits.baraati.Interface.OnSubCategorySelect;
import com.nectarbits.baraati.Models.Compare.ProductCompareModel;
import com.nectarbits.baraati.Models.Compare.ProductDetail;
import com.nectarbits.baraati.Models.SubCategory.SubCaregoryDetail;
import com.nectarbits.baraati.Models.SubCategory.SubCategoryModel;
import com.nectarbits.baraati.Singletone.AddCheckListSigletone;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompareActivity extends AppCompatActivity {

    @Bind(R.id.rcCompare)
    RecyclerView rcCompare;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private Context mContext;
    ProgressDialog progressDialog;
    String keyword="";
    ProductCompareModel productCompareModel;
    ProductCompareListAdapter adapter;
    List<ProductDetail> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        keyword=getIntent().getExtras().getString(AppUtils.ARG_PRODUCT_NAME);
        implementToolbar();
        if (GH.isInternetOn(mContext))
        LoadProducts();
    }
    /**
     * set ToolBar
     */
    private void implementToolbar() {
        toolbar.setTitle(keyword);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    /**
     * Load Product
     */

    private void LoadProducts(){
        if(!progressDialog.isShowing() && !progressDialog.getOfflineDataAvailable())
            progressDialog.show();
        Call<ProductCompareModel> call = RetrofitBuilder.getInstance().getRetrofit().CompareProduct(RequestBodyBuilder.getInstanse().Request_CompareProdcut(keyword));
        call.enqueue(new Callback<ProductCompareModel>() {
            @Override
            public void onResponse(Call<ProductCompareModel> call, Response<ProductCompareModel> response) {
                productCompareModel=response.body();
                if(productCompareModel.getStatus().equalsIgnoreCase(AppUtils.Success)){

                   list= productCompareModel.getProductDetail();

                    setRecyclerViewSubCategory();
                    adapter.notifyDataSetChanged();;

                }else{

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<ProductCompareModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

    /**
     * set Recycleview
     */
    private void setRecyclerViewSubCategory(){
        adapter=new ProductCompareListAdapter(mContext, list, new OnProductSelect() {
            @Override
            public void onProductSelect(int position) {
                Intent i = new Intent(mContext, ProductDetailActivity.class);
                i.putExtra(AppUtils.ARG_PRODUCT_ID,list.get(position).getProductId());
                i.putExtra(AppUtils.ARG_PRODUCT_NAME,list.get(position).getProduct());
                startActivity(i);
            }
        });
        rcCompare.setLayoutManager(new LinearLayoutManager(mContext));
        rcCompare.setItemAnimator(new DefaultItemAnimator());
        rcCompare.setAdapter(adapter);
    }
}
