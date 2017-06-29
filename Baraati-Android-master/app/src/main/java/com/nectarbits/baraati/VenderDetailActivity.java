package com.nectarbits.baraati;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.nectarbits.baraati.Adapters.SampleImageListAdapter;
import com.nectarbits.baraati.Interface.OnSampleImageClick;
import com.nectarbits.baraati.Models.InquiryModel.Send.InquiryModel;
import com.nectarbits.baraati.Models.VendorDetail.Moreimage;
import com.nectarbits.baraati.Models.VendorDetail.VendorDetailModel;
import com.nectarbits.baraati.Singletone.GeneralSingletone;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.ExpandableTextViewDescription;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.GridSpacingItemDecoration;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.orhanobut.hawk.Hawk;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nectarbits on 23/08/16.
 */
public class VenderDetailActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener,BaseSliderView.OnSliderClickListener{

    private static final String TAG = VenderDetailActivity.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.slider)
    SliderLayout mSlider;
    @Bind(R.id.txtVendorName)
    TextViewTitle txtVendorName;
    @Bind(R.id.txtProductName)
    TextViewTitle txtProductName;
    @Bind(R.id.txtInquiry)
    TextViewDescription txtInquiry;
    @Bind(R.id.txtTotalReviews)
    TextViewDescription txtTotalReviews;
    @Bind(R.id.txtAverageRatting)
    TextViewTitle txtAverageRatting;
    @Bind(R.id.ttvSpecification)
    TextViewTitle ttvSpecification;
    @Bind(R.id.llAttributes)
    LinearLayout llAttributes;
    @Bind(R.id.txtDescriptioTitle)
    TextViewTitle txtDescriptioTitle;
    @Bind(R.id.txtDecription)
    ExpandableTextViewDescription txtDecription;
    @Bind(R.id.rcSampleImages)
    RecyclerView rcSampleImages;
    @Bind(R.id.custom_indicator)
    PagerIndicator pagerIndicator;
    @Bind(R.id.nsvVendor)
    NestedScrollView nsvVendor;
    @Bind(R.id.tvdNoSpecification)
    TextViewDescription tvdNoSpecification;
    @Bind(R.id.cv_more_images)
    CardView cv_more_images;
    private Context mContext;
    ProgressDialog progressDialog;
    SampleImageListAdapter adapter;
    String vendor_id="0";
    ExpandableLinearLayout llAttributesExapandable;
    String mFor_USER_ID="0",mUserEventTypeEventID="0";
    String vendor_company="",vendor_speciality="";
    @OnClick(R.id.txtInquiry)
    void onSendInquiry(){

        LayoutInflater li = LayoutInflater.from(mContext);
        View promptsView = li.inflate(R.layout.layout_inquiry, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                mContext);

        alertDialogBuilder.setTitle(getString(R.string.str_submit_inquiry));
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.evdMessage);
        final EditText txtBudget = (EditText) promptsView
                .findViewById(R.id.evdBudget);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.str_send),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                               if(userInput.getText().toString().length()<=0){
                                   Toast.makeText(VenderDetailActivity.this, getString(R.string.str_inquiry_message_must_not), Toast.LENGTH_SHORT).show();
                               }else if(TextUtils.isEmpty(txtBudget.getText().toString())){
                                   Toast.makeText(VenderDetailActivity.this, getString(R.string.str_budget_must_not), Toast.LENGTH_SHORT).show();
                               }else{
                                   if (GH.isInternetOn(mContext))
                                   sendInquiry(userInput.getText().toString(),txtBudget.getText().toString());
                                   dialog.dismiss();
                               }
                            }

                        })
                .setNegativeButton(getString(R.string.str_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
        

    }
    @OnClick(R.id.txtDescriptioTitle)
    public void OnDescriptionExpand(){
        if(txtDecription.isExpanded()){
            txtDecription.collapse();
            txtDescriptioTitle.setSelected(false);
        }else{
            txtDecription.expand();
            txtDescriptioTitle.setSelected(true);
        }
    }

    @OnClick(R.id.ttvSpecification)
    public void OnSpecification(){
        if(!ttvSpecification.isSelected()){
            ttvSpecification.setSelected(true);
            llAttributesExapandable.collapse();
        }else{

            ttvSpecification.setSelected(false);
            llAttributesExapandable.expand();
        }
    }

    @OnClick(R.id.llShowReview)
    void onClickReadWriteReview(){
        Intent intent=new Intent(this,ReviewActivity.class);
        intent.putExtra(AppUtils.ARG_PRODUCT_ID,"123");
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_detail);

        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        vendor_id=getIntent().getExtras().getString(AppUtils.ARG_VENDOR_ID);
        vendor_company=getIntent().getExtras().getString(AppUtils.ARG_VENDRO_COMPANY_NAME);
        vendor_speciality=getIntent().getExtras().getString(AppUtils.ARG_VENDRO_SPECIALITY);
        mFor_USER_ID=getIntent().getExtras().getString(AppUtils.ARG_FOR_USER_ID);
        mUserEventTypeEventID=getIntent().getExtras().getString(AppUtils.ARG_USER_EVENTTYPE_EVENTID);
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setCustomIndicator(pagerIndicator);
       // mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(VenderDetailActivity.this);
        mSlider.removeAllSliders();
        mSlider.stopAutoCycle();

        implementToolbar();

        if(Hawk.contains(AppUtils.OFFLINE_VENDOR_DETAIL+vendor_id)){
            setDataToView((VendorDetailModel)Hawk.get(AppUtils.OFFLINE_VENDOR_DETAIL+vendor_id));
        }
        if (GH.isInternetOn(mContext))
        LoadVendorDetail();
        if(vendor_speciality.length()>0) {
            txtProductName.setText(vendor_company);
        }

    }

    /**
     * Initialize toolbar
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);



        MenuItem itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable  icon = (LayerDrawable) itemCart.getIcon();
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.e(TAG, "onPageScrolled: "+position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        //mSlider.stopAutoCycle();
    //    mSlider.removeAllSliders();
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  mSlider.startAutoCycle();
    }

    /**
     * Initialize recycler view for GridLayout
     */
    private void initializeRecyclerView(List<Moreimage> list) {
        GridLayoutManager gridLayoutManager;
        if(list.size()>4) {
            gridLayoutManager = new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
        }else {
            gridLayoutManager = new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
        }

        int spanCount =3; // 3 columns

        boolean includeEdge = false;
        rcSampleImages.addItemDecoration(new GridSpacingItemDecoration(spanCount, 0, includeEdge));

        rcSampleImages.setHasFixedSize(true);
        rcSampleImages.setLayoutManager(gridLayoutManager);
        if(list.size()>0){
            cv_more_images.setVisibility(View.VISIBLE);
        }else{
            cv_more_images.setVisibility(View.GONE);
        }

    }

    /**
     * Load Vendor detail
     */
    private void sendInquiry(String message,String budget) {

        if(!progressDialog.isShowing())
            progressDialog.show();

        Call<InquiryModel> call = RetrofitBuilder.getInstance().getRetrofit().SendVendroInquiry(RequestBodyBuilder.getInstanse().getRequestForSendInquiry(UserDetails.getInstance(mContext).getUser_emailid(),message,budget,mFor_USER_ID,UserDetails.getInstance(mContext).getUser_id(),mUserEventTypeEventID,vendor_id));

        call.enqueue(new Callback<InquiryModel>() {
            @Override
            public void onResponse(Call<InquiryModel> call, Response<InquiryModel> response) {
                final InquiryModel inquiryModel=response.body();
                if(inquiryModel.getStatus().equalsIgnoreCase(AppUtils.Success)){
                    Toast.makeText(mContext,getString(R.string.str_sent_inquiry_success),Toast.LENGTH_SHORT).show();
                }else{

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<InquiryModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    /**
     * Load Vendor detail
     */
    private void LoadVendorDetail() {

        if(!progressDialog.isShowing() && !Hawk.contains(AppUtils.OFFLINE_VENDOR_DETAIL+vendor_id)) {
            progressDialog.show();
            nsvVendor.setVisibility(View.GONE);
        }

        Call<VendorDetailModel> call = RetrofitBuilder.getInstance().getRetrofit().GetVendorDetail(RequestBodyBuilder.getInstanse().getRequestVendorDetail(vendor_id));

        call.enqueue(new Callback<VendorDetailModel>() {
            @Override
            public void onResponse(Call<VendorDetailModel> call, Response<VendorDetailModel> response) {
                final VendorDetailModel venderInfo=response.body();
                if(venderInfo.getStatus().equalsIgnoreCase(AppUtils.Success)){
                    Hawk.put(AppUtils.OFFLINE_VENDOR_DETAIL+vendor_id,venderInfo);
                    setDataToView(venderInfo);

                }else{

                }
                progressDialog.dismiss();
                nsvVendor.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<VendorDetailModel> call, Throwable t) {
                progressDialog.dismiss();
                nsvVendor.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    /**
     * Set Data to views
     * @param vendorDetail
     */
    public void setDataToView(final VendorDetailModel vendorDetail){
        if(vendor_company.length()>0) {
            toolbar.setTitle(vendor_company);
        }else {
            toolbar.setTitle(vendorDetail.getVendorDetail().get(0).getName());
        }
        txtVendorName.setText(vendorDetail.getVendorDetail().get(0).getName());
        txtTotalReviews.setText(String.format(getString(R.string.str_customer_reviews_with_values),"127"));

        txtAverageRatting.setText("2.5");
        txtDecription.setText(Html.fromHtml(vendorDetail.getVendorDetail().get(0).getLongDescription()));


        adapter=new SampleImageListAdapter(mContext,vendorDetail.getVendorDetail().get(0).getMoreimage(), new OnSampleImageClick() {

            @Override
            public void onSampleImageClick(int position) {
                GeneralSingletone.getInstance().setMoreimageList(vendorDetail.getVendorDetail().get(0).getMoreimage());
                Intent intent=new Intent(mContext,ImageViewerActivity.class);
                intent.putExtra(AppUtils.ARG_MORE_POSITION,position);
                startActivity(intent);
            }
        });
        initializeRecyclerView(vendorDetail.getVendorDetail().get(0).getMoreimage());
        rcSampleImages.setAdapter(adapter);

        llAttributesExapandable=new ExpandableLinearLayout(VenderDetailActivity.this);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llAttributesExapandable.setLayoutParams(layoutParams);
        llAttributesExapandable.setOrientation(LinearLayout.VERTICAL);

        if(vendorDetail.getVendorAllAttribute().size()>0) {
            tvdNoSpecification.setVisibility(View.GONE);
            for (int i = 0; i <vendorDetail.getVendorAllAttribute().size(); i++) {
                View child = getLayoutInflater().inflate(R.layout.layout_attribute_item, null);
                TextView txtAttributeName = (TextView) child.findViewById(R.id.txtAttributeName);
                TextView txtAttributeValue = (TextView) child.findViewById(R.id.txtAttributeValue);
                txtAttributeName.setText(vendorDetail.getVendorAllAttribute().get(i).getAttributeName());
                txtAttributeValue.setText(vendorDetail.getVendorAllAttribute().get(i).getValue());

                llAttributesExapandable.addView(child);
            }
        }else{
             tvdNoSpecification.setVisibility(View.VISIBLE);
            llAttributesExapandable.addView(new View(VenderDetailActivity.this));
        }
        llAttributesExapandable.initLayout(true);
        llAttributes.addView(llAttributesExapandable);
        ttvSpecification.setSelected(false);

        for (int i = 0; i < vendorDetail.getVendorDetail().get(0).getBannermainimage().size(); i++) {

            TextSliderView textSliderView = new TextSliderView(mContext);

            textSliderView
                    .image(vendorDetail.getVendorDetail().get(0).getBannermainimage().get(i).getBannermainimage())
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(VenderDetailActivity.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putInt("position", i);
            Log.e(TAG, "setDataToView: slider added::"+i);
            mSlider.addSlider(textSliderView);

        }



        nsvVendor.fullScroll(ScrollView.FOCUS_UP);
    }
}
