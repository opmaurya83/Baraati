package com.nectarbits.baraati;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.internal.zzng;
import com.nectarbits.baraati.Adapters.SampleImageListAdapter;
import com.nectarbits.baraati.Adapters.VendorBazaarEventTypeAdapter;
import com.nectarbits.baraati.Adapters.VendorListAdapter;
import com.nectarbits.baraati.Fragment.BazaarFragment;
import com.nectarbits.baraati.Fragment.VendorListFragment;
import com.nectarbits.baraati.Interface.OnBazaarDataLoaded;
import com.nectarbits.baraati.Interface.OnBazaarFragmentClicked;
import com.nectarbits.baraati.Interface.OnVendorBazaarEventSelect;
import com.nectarbits.baraati.Interface.OnVendorDataLoaded;
import com.nectarbits.baraati.Interface.OnVendorFragmentClicked;
import com.nectarbits.baraati.Models.UserEvent.EventType;
import com.nectarbits.baraati.Models.Vendor.VendorList;
import com.nectarbits.baraati.Models.VendorBazaar.VendorBazaarModel;
import com.nectarbits.baraati.Singletone.EventTypesSingletone;
import com.nectarbits.baraati.Singletone.FilterController;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.orhanobut.hawk.Hawk;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorBazaarActivity extends AppCompatActivity implements OnVendorFragmentClicked,OnBazaarFragmentClicked {

    private static final String TAG = VendorBazaarActivity.class.getSimpleName();
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    @Bind(R.id.toolbar)
            Toolbar toolbar;
    Context mContext;
    ProgressDialog progressDialog;
    VendorBazaarModel vendorList;
    VendorListAdapter vendorListAdapter;
    OnVendorDataLoaded onVendorDataLoaded;
    OnBazaarDataLoaded onBazaarDataLoaded;
    @Bind(R.id.spinner_nav)
    Spinner spinner_nav;
    VendorBazaarEventTypeAdapter vendorBazaarEventTypeAdapter;
    String event_type_id="0";
    String filters_product="";
    String filters_vendor="";
    String pricerangefilter="";
    String for_user_id="0",userEventtypeEventID="0";
    Boolean is_filter=false;
    Boolean isAssigned=false;
    public List<EventType> eventTypeList=new ArrayList<>();
    FloatingActionButton fab_sort;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_bazaar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);

        event_type_id=getIntent().getExtras().getString(AppUtils.ARG_EVENTTYPE_ID);
        for_user_id=getIntent().getExtras().getString(AppUtils.ARG_FOR_USER_ID);
        userEventtypeEventID=getIntent().getExtras().getString(AppUtils.ARG_USER_EVENTTYPE_EVENTID);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        isAssigned=getIntent().getExtras().getBoolean(AppUtils.ARG_IS_ASSIGNED,false);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    fab.setVisibility(View.VISIBLE);
                    fab_sort.setVisibility(View.VISIBLE);
                }else{
                    fab.setVisibility(View.VISIBLE);
                    fab_sort.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (GH.isInternetOn(mContext))
        LoadVendorList(RequestBodyBuilder.getInstanse().getRequestVendorList(event_type_id));

            if(isAssigned){
                eventTypeList=EventTypesSingletone.getInstance().getEventTypeList_assigned();
            }else {
                eventTypeList=EventTypesSingletone.getInstance().getEventTypeList();
            }

        vendorBazaarEventTypeAdapter=new VendorBazaarEventTypeAdapter(mContext, eventTypeList, new OnVendorBazaarEventSelect() {
            @Override
            public void onvendorBazaarEventTypeSelected(int position) {
                Log.e(TAG, "onvendorBazaarEventTypeSelected: "+position );
            }
        });

        spinner_nav.setAdapter(vendorBazaarEventTypeAdapter);

        for (int i = 0; i < eventTypeList.size(); i++) {
            if(eventTypeList.get(i).getEventTypeId().equalsIgnoreCase(event_type_id)) {
                spinner_nav.setSelection(i);
            }
        }

        spinner_nav.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(isAssigned) {
                    event_type_id = EventTypesSingletone.getInstance().getEventTypeList_assigned().get(i).getEventTypeId();
                }else{
                    event_type_id = EventTypesSingletone.getInstance().getEventTypeList().get(i).getEventTypeId();
                }
                if (GH.isInternetOn(mContext))
                LoadVendorList(RequestBodyBuilder.getInstanse().getRequestVendorList(event_type_id));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        implementToolbar();
        if(Hawk.contains(event_type_id)){

           // onVendorDataLoaded.OnDataLoaded((VendorBazaarModel)Hawk.get(event_type_id));
            //onBazaarDataLoaded.OnDataLoaded((VendorBazaarModel)Hawk.get(event_type_id));
        }

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(VendorBazaarActivity.this, "Filters", Toast.LENGTH_SHORT).show();
                if(vendorList!=null) {
                    if(vendorList.getStatus().equalsIgnoreCase(AppUtils.Success)) {
                        if(mViewPager.getCurrentItem()==1) {
                            startActivityForResult(new Intent(mContext, FilterActivity.class)
                                            .putExtra(AppUtils.ARG_MIN_PRICE_RANGE, vendorList.getProductRangePrice().get(0).getMinprice())
                                            .putExtra(AppUtils.ARG_MAX_PRICE_RANGE, vendorList.getProductRangePrice().get(0).getMaxprice())
                                            .putExtra(AppUtils.ARG_IS_PRODUCT, true)
                                    , AppUtils.REQUEST_FILTER);
                        }else{
                            startActivityForResult(new Intent(mContext, FilterActivity.class)
                                            .putExtra(AppUtils.ARG_MIN_PRICE_RANGE, vendorList.getProductRangePrice().get(0).getMinprice())
                                            .putExtra(AppUtils.ARG_MAX_PRICE_RANGE, vendorList.getProductRangePrice().get(0).getMaxprice())
                                            .putExtra(AppUtils.ARG_IS_PRODUCT,false)
                                    , AppUtils.REQUEST_FILTER);
                        }
                    }
                }
            }
        });

        fab_sort = (FloatingActionButton) findViewById(R.id.fabSort);
        fab_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnSort();
            }
        });


        fab.setVisibility(View.VISIBLE);
        fab_sort.setVisibility(View.VISIBLE);
    }


    private void OnSort() {
     /*   if (!llSortProducts.isSelected()) {
            llSortProducts.setSelected(true);
        }
        if (llFilterProducts.isSelected()) {
            llFilterProducts.setSelected(false);
        }*/
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_sorting);
        dialog.setCanceledOnTouchOutside(true);

        TextView txtHighToLow = (TextView) dialog.findViewById(R.id.txtHighToLow);
        TextView txtLowToHigh = (TextView) dialog.findViewById(R.id.txtLowToHigh);
        TextView txtNameAtoZ = (TextView) dialog.findViewById(R.id.txtNameAtoZ);
        TextView txtNameZtoA = (TextView) dialog.findViewById(R.id.txtNameZtoA);

        txtHighToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(mViewPager.getCurrentItem()==1) {
                  JSONObject jsonObject= RequestBodyBuilder.getInstanse().getRequestVendorList(event_type_id, "price", "desc", "", "",filters_vendor,filters_product,pricerangefilter);
                    if (GH.isInternetOn(mContext))
                    LoadVendorList(jsonObject);
                }else{
                  JSONObject jsonObject=  RequestBodyBuilder.getInstanse().getRequestVendorList(event_type_id, "", "","price", "desc",filters_vendor,filters_product,pricerangefilter);
                    if (GH.isInternetOn(mContext))
                    LoadVendorList(jsonObject);
                }
               /* if (getIntent().getStringExtra(AppUtils.ARG_SEARCH_PRODUCT) != null) {
                    onSearchProductListing(categoryId, "0", "2");
                } else {
                    onProductSorting(RequestBodyBuilder.getInstanse().getRequestBuilderForPriceSorting(categoryId, "2", "0"));
                }*/
            }
        });
        txtLowToHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(mViewPager.getCurrentItem()==1) {
                   JSONObject jsonObject= RequestBodyBuilder.getInstanse().getRequestVendorList(event_type_id, "price", "asc", "", "",filters_vendor,filters_product,pricerangefilter);
                    if (GH.isInternetOn(mContext))
                    LoadVendorList(jsonObject);
                }else{
                    JSONObject jsonObject=RequestBodyBuilder.getInstanse().getRequestVendorList(event_type_id, "", "","price", "asc",filters_vendor,filters_product,pricerangefilter);
                    if (GH.isInternetOn(mContext))
                    LoadVendorList(jsonObject);
                }
              /*  if (getIntent().getStringExtra(AppUtils.ARG_SEARCH_PRODUCT) != null) {
                    onSearchProductListing(categoryId, "0", "1");
                } else {
                    onProductSorting(RequestBodyBuilder.getInstanse().getRequestBuilderForPriceSorting(categoryId, "1", "0"));
                }*/
            }
        });
        txtNameAtoZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(mViewPager.getCurrentItem()==1) {
                    JSONObject jsonObject=RequestBodyBuilder.getInstanse().getRequestVendorList(event_type_id, "name", "asc", "", "",filters_vendor,filters_product,pricerangefilter);
                    if (GH.isInternetOn(mContext))
                    LoadVendorList(jsonObject);
                }else{
                    JSONObject jsonObject=RequestBodyBuilder.getInstanse().getRequestVendorList(event_type_id, "", "", "name", "asc",filters_vendor,filters_product,pricerangefilter);
                    if (GH.isInternetOn(mContext))
                    LoadVendorList(jsonObject);
                }
                    /*  if (getIntent().getStringExtra(AppUtils.ARG_SEARCH_PRODUCT) != null) {
                    onSearchProductListing(categoryId, "1", "0");
                } else {
                    onProductSorting(RequestBodyBuilder.getInstanse().getRequestBuilderForPriceSorting(categoryId, "0", "1"));
                }*/
            }
        });
        txtNameZtoA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                /*if (getIntent().getStringExtra(AppUtils.ARG_SEARCH_PRODUCT) != null) {
                    onSearchProductListing(categoryId, "2", "0");
                } else {
                    onProductSorting(RequestBodyBuilder.getInstanse().getRequestBuilderForPriceSorting(categoryId, "0", "2"));

                }*/

                if(mViewPager.getCurrentItem()==1) {
                    JSONObject jsonObject=RequestBodyBuilder.getInstanse().getRequestVendorList(event_type_id, "name", "desc", "", "",filters_vendor,filters_product,pricerangefilter);
                    if (GH.isInternetOn(mContext))
                    LoadVendorList(jsonObject);
                }else{
                    JSONObject jsonObject=RequestBodyBuilder.getInstanse().getRequestVendorList(event_type_id, "", "", "name", "desc",filters_vendor,filters_product,pricerangefilter);
                    if (GH.isInternetOn(mContext))
                    LoadVendorList(jsonObject);
                }
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
              //  llSortProducts.setSelected(false);
            }
        });
        dialog.show();
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
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);



        MenuItem itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        if(Hawk.contains(AppUtils.CART_ITEMS)) {
            GH.setBadgeCount(this, icon,""+Hawk.get(AppUtils.CART_ITEMS));
        }else {
            GH.setBadgeCount(this, icon,"0");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_cart:
                Intent intent=new Intent(mContext,CartActivity.class);
                startActivity(intent);

                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnBazaarClicked() {

    }

    @Override
    public void OnVendorClicked() {

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0) {
                Fragment fragment = VendorListFragment.newInstance(event_type_id, for_user_id,userEventtypeEventID);
                onVendorDataLoaded =(OnVendorDataLoaded)fragment;
                return fragment;
            }
            else {
                Fragment fragment = BazaarFragment.newInstance(event_type_id, for_user_id,userEventtypeEventID);
                onBazaarDataLoaded =(OnBazaarDataLoaded) fragment;
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
                    return getString(R.string.str_vendor_list);
                case 1:
                    return getString(R.string.str_bazaar);

            }
            return null;
        }
    }

    /**
     * Load Vendor and Products data
     */
    private void LoadVendorList(JSONObject jsonObject) {

        Log.e(TAG, "LoadVendorList: "+jsonObject.toString() );

        if(!progressDialog.isShowing() && !Hawk.contains(AppUtils.OFFLINE_VENDORPRODUCT_LIST+event_type_id)) {
            progressDialog.show();
        }
        else if (is_filter){
            progressDialog.show();
        }

        Call<VendorBazaarModel> call = RetrofitBuilder.getInstance().getRetrofit().GetVendorBazaarList(jsonObject);

        call.enqueue(new Callback<VendorBazaarModel>() {
            @Override
            public void onResponse(Call<VendorBazaarModel> call, Response<VendorBazaarModel> response) {
                vendorList = response.body();
                if(vendorList.getStatus().equalsIgnoreCase(AppUtils.Success)) {
                    Hawk.put(AppUtils.OFFLINE_VENDORPRODUCT_LIST+event_type_id, vendorList);
                    FilterController.getInstance().setProductAllAttributes(vendorList.getProductAllAttribute());
                    FilterController.getInstance().setVendorAllAttributes(vendorList.getVendorAllAttribute());
                }
                try {
                    onVendorDataLoaded.OnDataLoaded(vendorList);
                    onBazaarDataLoaded.OnDataLoaded(vendorList);
                }catch (Exception ex){

                }


                is_filter=false;

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<VendorBazaarModel> call, Throwable t) {
                is_filter=false;
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().invalidateOptionsMenu();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RESULT_OK==resultCode){
            if(requestCode==AppUtils.REQUEST_FILTER){
                Boolean aBoolean=data.getBooleanExtra(AppUtils.ARG_IS_PRODUCT,true);


                 pricerangefilter = data.getStringExtra(AppUtils.ARG_RETURN_PRICE_RANGE);


                JSONObject jsonObject=new JSONObject();
                if(aBoolean) {
                    filters_product = data.getStringExtra(AppUtils.ARG_RETURN_ATTRIBUTES_LIST);
                    jsonObject = RequestBodyBuilder.getInstanse().getRequestVendorList(event_type_id, "", "", "", "", filters_vendor, filters_product, pricerangefilter);
                }else{
                    filters_vendor = data.getStringExtra(AppUtils.ARG_RETURN_ATTRIBUTES_LIST);
                    jsonObject = RequestBodyBuilder.getInstanse().getRequestVendorList(event_type_id, "", "", "","", filters_vendor, filters_product, pricerangefilter);
                }
                is_filter=true;
                if (GH.isInternetOn(mContext))
                LoadVendorList(jsonObject);
            }
        }
    }
}
