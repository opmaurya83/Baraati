package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nectarbits.baraati.Adapters.SubCategoryAdapter;
import com.nectarbits.baraati.Interface.OnSubCategorySelect;
import com.nectarbits.baraati.Models.SubCategory.SubCaregoryDetail;
import com.nectarbits.baraati.Models.SubCategory.SubCategoryModel;
import com.nectarbits.baraati.Singletone.AddCheckListSigletone;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.orhanobut.hawk.Hawk;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nectarbits on 19/08/16.
 */
public class SubCategoryActivity extends AppCompatActivity{

    private static final String TAG = SubCategoryActivity.class.getSimpleName() ;
    private Context mContext;
    private ArrayList<String> arrayList;
    @Bind(R.id.txtTitle)
    TextView txtTitle;
    @Bind(R.id.recyclerViewSubCategory)
    RecyclerView recyclerViewSubCategory;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    String categoryID;
    ProgressDialog progressDialog;
    SubCategoryModel subCategoryModel;
    SubCategoryAdapter adapter;
    List<String> selected_subcat = new ArrayList<String>();
    String selected_ids="";
     List<SubCaregoryDetail> list_final=new ArrayList<SubCaregoryDetail>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        ButterKnife.bind(this);
        mContext = this;

         categoryID=getIntent().getExtras().getString(AppUtils.ARG_CATEGORY_ID);

        progressDialog = new ProgressDialog(mContext);

        recyclerViewSubCategory.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewSubCategory.setItemAnimator(new DefaultItemAnimator());
        setRecyclerViewSubCategory();
        if(Hawk.contains(AppUtils.OFFLINE_SUBCATEGORY_LIST+categoryID)){
            progressDialog.setOfflineDataAvailable(true);
            list_final.clear();
            list_final.addAll((List<SubCaregoryDetail>)Hawk.get(AppUtils.OFFLINE_SUBCATEGORY_LIST+categoryID));
            adapter.notifyDataSetChanged();
        }
        if (GH.isInternetOn(mContext))
        LoadSubCategory();
        txtTitle.setText(AddCheckListSigletone.getInstance().getCategoryName());
    implementToolbar();

    }

    /**
     * set ToolBar
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_next, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_next){
            if(selected_ids.length()>0)
            {
                AddCheckListSigletone.getInstance().setSubcategoryID(selected_ids);
                startActivityForResult(new Intent(mContext,EventActivty.class),AppUtils.REQUEST_Event);

            }else{
                Toast.makeText(SubCategoryActivity.this,getString(R.string.str_please_select_wedding_type), Toast.LENGTH_SHORT).show();
            }
            //startActivity(new Intent(mContext,CategoryListActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }

    private void LoadSubCategory(){
        if(!progressDialog.isShowing() && !progressDialog.getOfflineDataAvailable())
            progressDialog.show();
        Call<SubCategoryModel> call = RetrofitBuilder.getInstance().getRetrofit().GetSubCategory(RequestBodyBuilder.getInstanse().getRequestSubcategory(categoryID));
        call.enqueue(new Callback<SubCategoryModel>() {
            @Override
            public void onResponse(Call<SubCategoryModel> call, Response<SubCategoryModel> response) {
                subCategoryModel=response.body();
                if(subCategoryModel.getStatus().equalsIgnoreCase(AppUtils.Success)){

                    List<SubCaregoryDetail> list= subCategoryModel.getSubCaregoryDetail();
                    list_final.clear();
                    for (int i = 0; i < list.size(); i++) {
                        Log.e(TAG, "onResponse: "+list.get(i).getSubcategoryId() +" "+TextUtils.join(",",AddCheckListSigletone.getInstance().getSelected_subCat()));
                        //if(!AddCheckListSigletone.getInstance().getSelected_subCat().contains(list.get(i).getSubcategoryId())){
                            Log.e(TAG, "onResponse: true");
                            list_final.add(list.get(i));

//                        }
                    }
                    Hawk.put(AppUtils.OFFLINE_SUBCATEGORY_LIST+categoryID,list_final);
                    adapter.notifyDataSetChanged();;

                }else{

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<SubCategoryModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

    private void setRecyclerViewSubCategory(){
        adapter=new SubCategoryAdapter(mContext, list_final, new OnSubCategorySelect() {

            @Override
            public void OnSubCategoryChecked(int position, Boolean aBoolean) {

                if(aBoolean) {
                    selected_subcat.add(list_final.get(position).getSubcategoryId());
                }else {
                    selected_subcat.remove(list_final.get(position).getSubcategoryId());
                }

                selected_ids = TextUtils.join(",", selected_subcat);
                Log.e("TAG", "OnSubCategoryChecked: "+selected_ids);

            }
        });

        recyclerViewSubCategory.setAdapter(adapter);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==AppUtils.REQUEST_Event){
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
