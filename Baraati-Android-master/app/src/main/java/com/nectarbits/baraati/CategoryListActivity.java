package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nectarbits.baraati.Adapters.MainCategoryAdapter;
import com.nectarbits.baraati.Interface.OnMainCategorySelect;
import com.nectarbits.baraati.Models.Login.LoginModel;
import com.nectarbits.baraati.Models.MainCategory.CaregoryDetail;
import com.nectarbits.baraati.Models.MainCategory.MainCategoryModel;
import com.nectarbits.baraati.Singletone.AddCheckListSigletone;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.L;
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

public class CategoryListActivity extends AppCompatActivity  {

    @Bind(R.id.recyclerViewMainCategory)
    RecyclerView recyclerView;
    private Context mContext;
    MainCategoryModel mainCategoryModel;
    ProgressDialog progressDialog;
    MainCategoryAdapter adapter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    List<CaregoryDetail> caregoryDetailList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        ButterKnife.bind(this);
        mContext=this;
        progressDialog = new ProgressDialog(mContext);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setRecyclerView();
        if(Hawk.contains(AppUtils.OFFLINE_CATEGORY_LIST)){
            caregoryDetailList.addAll((List<CaregoryDetail>) Hawk.get(AppUtils.OFFLINE_CATEGORY_LIST));
            adapter.notifyDataSetChanged();
        }

        if (GH.isInternetOn(mContext))
        LoadMainCategory();
        implementToolbar();
        GH.UpdateQuickBloxID(mContext);

    }

    /**
     * Set Toolbar
     */
    private void implementToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(getString(R.string.str_choose_wedding));
    }

    /**
     * Load Category
     */
    private void LoadMainCategory(){
        if(!progressDialog.isShowing() && !Hawk.contains(AppUtils.OFFLINE_CATEGORY_LIST))
            progressDialog.show();
        Call<MainCategoryModel> call = RetrofitBuilder.getInstance().getRetrofit().GetMainCategory();
        call.enqueue(new Callback<MainCategoryModel>() {
            @Override
            public void onResponse(Call<MainCategoryModel> call, Response<MainCategoryModel> response) {
                mainCategoryModel=response.body();
                if(mainCategoryModel.getStatus().equalsIgnoreCase(AppUtils.Success)){
                    caregoryDetailList.clear();
                    caregoryDetailList.addAll(mainCategoryModel.getCaregoryDetail());
                    Hawk.put(AppUtils.OFFLINE_CATEGORY_LIST,mainCategoryModel.getCaregoryDetail());
                    adapter.notifyDataSetChanged();


                }else{

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<MainCategoryModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

private void setRecyclerView(){
    adapter=new MainCategoryAdapter(mContext, caregoryDetailList, new OnMainCategorySelect() {
        @Override
        public void OnMainCategorySelected(int position) {
            AddCheckListSigletone.getInstance().reset();
            AddCheckListSigletone.getInstance().setCategoryID(caregoryDetailList.get(position).getCategoryId());
            AddCheckListSigletone.getInstance().setCategoryName(caregoryDetailList.get(position).getCategoryName());
            Intent intent=new Intent(mContext,SubCategoryActivity.class);
            intent.putExtra(AppUtils.ARG_CATEGORY_ID,caregoryDetailList.get(position).getCategoryId());
            startActivityForResult(intent,AppUtils.REQUEST_SUBCATEGORY);
        }
    });

    recyclerView.setAdapter(adapter);
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==AppUtils.REQUEST_SUBCATEGORY){
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
