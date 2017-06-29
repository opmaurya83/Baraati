package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nectarbits.baraati.Adapters.OrderListAdapter;
import com.nectarbits.baraati.Fragment.AssignedInquiryFragment;
import com.nectarbits.baraati.Fragment.AssignedOrderFragment;
import com.nectarbits.baraati.Fragment.PersonalInquiryFragment;
import com.nectarbits.baraati.Fragment.PersonalOrderFragment;
import com.nectarbits.baraati.Interface.OnAssignedInquiryListener;
import com.nectarbits.baraati.Interface.OnAssignedOrderListener;
import com.nectarbits.baraati.Interface.OnInquiryListeners;
import com.nectarbits.baraati.Interface.OnOrderListeners;
import com.nectarbits.baraati.Interface.OnOrderProductClick;
import com.nectarbits.baraati.Interface.OnPersonalInquiryLoadedListener;
import com.nectarbits.baraati.Interface.OnPersonalOrderLoadedListener;
import com.nectarbits.baraati.Models.InquiryModel.List.InquiryListModel;

import com.nectarbits.baraati.Models.OrderList.List.OrderListModel;
import com.nectarbits.baraati.Singletone.UserDetails;
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

public class OrderListActivity extends AppCompatActivity implements OnOrderListeners {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private static final String TAG = OrderListActivity.class.getSimpleName() ;
    private Context mContext;
    ProgressDialog progressDialog;


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private OnPersonalOrderLoadedListener onPersonalInquiryLoadedListener;
    private OnAssignedOrderListener onAssignedInquiryListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_list);

        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        if (GH.isInternetOn(mContext))
        RequestFoGetOrderList();

        implementToolbar();
        GH.UpdateQuickBloxID(mContext);
    }

    /**
     * set ToolBar
     */
    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.str_my_orders));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }




    @Override
    public void onPersonalOrder() {

    }

    @Override
    public void onAssignedOrder() {

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0) {
                Fragment fragment = PersonalOrderFragment.newInstance("", "");
                onPersonalInquiryLoadedListener =(OnPersonalOrderLoadedListener) fragment;
                return fragment;
            }
            else {
                Fragment fragment = AssignedOrderFragment.newInstance("", "");
                onAssignedInquiryListener =(OnAssignedOrderListener) fragment;
                return fragment;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.myorders);
                case 1:
                    return getString(R.string.sharedorders);

            }
            return null;
        }
    }

   /* private void LoadInquiries(){
        if(!progressDialog.isShowing() && !progressDialog.getOfflineDataAvailable())
            progressDialog.show();
        Call<InquiryListModel> call = RetrofitBuilder.getInstance().getRetrofit().GetInquiryList(RequestBodyBuilder.getInstanse().Request_InquiryList(UserDetails.getInstance(mContext).getUser_id()));
        call.enqueue(new Callback<InquiryListModel>() {
            @Override
            public void onResponse(Call<InquiryListModel> call, Response<InquiryListModel> response) {
                InquiryListModel  subCategoryModel=response.body();
                if(subCategoryModel.getStatus().equalsIgnoreCase(AppUtils.Success)){

                    onPersonalInquiryLoadedListener.onPersonalInquiryLoadedListener(subCategoryModel.getMyInqiry());
                    onAssignedInquiryListener.onAssignedInquiryListener(subCategoryModel.getAssignInqiry());


                }else{

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<InquiryListModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

*/
    /**
     * Get Order List
     */
    private void RequestFoGetOrderList() {
        if (!progressDialog.isShowing())
            progressDialog.show();



        Call<OrderListModel> call = RetrofitBuilder.getInstance().getRetrofit().GetOrderList(RequestBodyBuilder.getInstanse().Request_GetOrderList(UserDetails.getInstance(mContext).getUser_id()));

        call.enqueue(new Callback<OrderListModel>() {
            @Override
            public void onResponse(Call<OrderListModel> call, Response<OrderListModel> response) {
                OrderListModel    cartListModel=response.body();
                if(cartListModel.getStatus().equalsIgnoreCase(AppUtils.Success)){

                    onPersonalInquiryLoadedListener.onPersonalOrderLoadedListener(cartListModel.getOwnOrder());
                    onAssignedInquiryListener.onAssignedOrderListener(cartListModel.getAssigneeOrder());

                }else {



                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<OrderListModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }
}
