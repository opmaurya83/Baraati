package com.nectarbits.baraati;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.nectarbits.baraati.Fragment.AssignedInquiryFragment;
import com.nectarbits.baraati.Fragment.PersonalInquiryFragment;
import com.nectarbits.baraati.Interface.OnAssignedInquiryListener;
import com.nectarbits.baraati.Interface.OnInquiryListeners;
import com.nectarbits.baraati.Interface.OnPersonalInquiryLoadedListener;
import com.nectarbits.baraati.Models.InquiryModel.List.InquiryListModel;
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

public class InquiryListActivity extends AppCompatActivity implements OnInquiryListeners {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private static final String TAG = InquiryListActivity.class.getSimpleName() ;
    private Context mContext;
    ProgressDialog progressDialog;


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private OnPersonalInquiryLoadedListener onPersonalInquiryLoadedListener;
    private OnAssignedInquiryListener onAssignedInquiryListener;
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
        LoadInquiries();

        implementToolbar();
        GH.UpdateQuickBloxID(mContext);
    }

    /**
     * set ToolBar
     */
    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.inquiries));
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
    public void onPersonalInquairy() {

    }

    @Override
    public void onAssignedInquairy() {

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
                Fragment fragment = PersonalInquiryFragment.newInstance("", "");
                onPersonalInquiryLoadedListener =(OnPersonalInquiryLoadedListener) fragment;
                return fragment;
            }
            else {
                Fragment fragment = AssignedInquiryFragment.newInstance("", "");
                onAssignedInquiryListener =(OnAssignedInquiryListener) fragment;
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
                    return getString(R.string.myindquiries);
                case 1:
                    return getString(R.string.sharedinquiries);

            }
            return null;
        }
    }

    private void LoadInquiries(){
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

}
