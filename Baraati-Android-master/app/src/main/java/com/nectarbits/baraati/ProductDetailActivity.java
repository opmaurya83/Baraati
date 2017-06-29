package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.nectarbits.baraati.Adapters.SampleImageListAdapter;
import com.nectarbits.baraati.Adapters.SampleProductImageListAdapter;
import com.nectarbits.baraati.Chat.ui.activities.main.*;
import com.nectarbits.baraati.Interface.OnSampleImageClick;
import com.nectarbits.baraati.Models.AddtoCart.AddtoCartModel;
import com.nectarbits.baraati.Models.Login.UserDetail;
import com.nectarbits.baraati.Models.ProductDetail.ProductDetailModel;

import com.nectarbits.baraati.Models.Resposibility.Add.SharedeventDetail;
import com.nectarbits.baraati.Models.VendorDetail.Moreimage;
import com.nectarbits.baraati.Singletone.GeneralSingletone;
import com.nectarbits.baraati.Singletone.ShareProductUtils;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.ExpandableTextViewDescription;
import com.nectarbits.baraati.View.MyTextSliderView;
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


public class ProductDetailActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener,BaseSliderView.OnSliderClickListener {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private static final String TAG = ProductDetailActivity.class.getSimpleName();
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

    @Bind(R.id.txtDescriptioTitle)
    TextViewTitle txtDescriptioTitle;
    @Bind(R.id.ttvSpecification)
    TextViewTitle ttvSpecification;
    @Bind(R.id.txtDecription)
    ExpandableTextViewDescription txtDecription;
    @Bind(R.id.txtPrice)
    TextViewTitle txtPrice;
    @Bind(R.id.txtSpecialPrice)
    TextViewTitle txtSpecialPrice;
    @Bind(R.id.llAddToCart)
    LinearLayout llAddToCart;
    @Bind(R.id.custom_indicator)
    PagerIndicator pagerIndicator;
    @Bind(R.id.llAttributes)
    LinearLayout llAttributes;
    @Bind(R.id.tvdNoSpecification)
    TextViewDescription tvdNoSpecification;
    @Bind(R.id.rcSampleImages)
    RecyclerView rcSampleImages;
    @Bind(R.id.nsv_main)
    NestedScrollView nsv_main;
    @Bind(R.id.cv_more_images)
    CardView cv_more_images;
    private Context mContext;
    ProgressDialog progressDialog;
    String product_id,product_name;
    LayerDrawable icon;
    ExpandableLinearLayout llAttributesExapandable;
    ProductDetailModel detailModel;
    String mFor_USER_ID="0",mUserEventTypeEventID="";
    SampleProductImageListAdapter adapter;



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
    @OnClick(R.id.llShowReview)
    void onClickReadWriteReview(){
        Intent intent=new Intent(this,ReviewActivity.class);
        intent.putExtra(AppUtils.ARG_PRODUCT_ID,product_id);
        startActivityForResult(intent,AppUtils.REQUEST_WRITE_REVIEW);

    }
    @OnClick(R.id.llAddToCart)
    void onAddToCart(){
       /* Toast.makeText(ProductDetailActivity.this,getString(R.string.str_product_added_to_cart_successfully), Toast.LENGTH_SHORT).show();
        GH.setBadgeCount(this, icon, "10");*/
        if(!detailModel.getIscart().equalsIgnoreCase("False")){
            Toast.makeText(mContext,getString(R.string.product_already_in_your_cart), Toast.LENGTH_SHORT).show();
        }else{
            if (GH.isInternetOn(mContext))
            AddtoCart();
        }

    }

    @OnClick(R.id.iv_share)
    void onShare(){
        if(detailModel!=null) {
            ShareProductUtils.getInstance().setProduct_id(product_id);
            ShareProductUtils.getInstance().setProduct_name(product_name);
            ShareProductUtils.getInstance().setImage_ulr(detailModel.getProductDetail().get(0).getMainimage().get(0).getMainimage());
            ShareProductUtils.getInstance().setmUserEventTypeEventID(mUserEventTypeEventID);
            ShareProductUtils.getInstance().setmFor_USER_ID(mFor_USER_ID);
            com.nectarbits.baraati.Chat.ui.activities.main.MainActivity.start(mContext);
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        ButterKnife.bind(this);
        mContext=this;
        progressDialog = new ProgressDialog(mContext);
        product_id=getIntent().getExtras().getString(AppUtils.ARG_PRODUCT_ID);
        product_name=getIntent().getExtras().getString(AppUtils.ARG_PRODUCT_NAME);
        mFor_USER_ID=getIntent().getExtras().getString(AppUtils.ARG_FOR_USER_ID);
        mUserEventTypeEventID=getIntent().getExtras().getString(AppUtils.ARG_USER_EVENTTYPE_EVENTID);
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setCustomIndicator(pagerIndicator);
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(ProductDetailActivity.this);
        implementToolbar();
        if(Hawk.contains(AppUtils.OFFLINE_PRODUCT_DETAIL+product_id))
        {
            detailModel=(ProductDetailModel)Hawk.get(AppUtils.OFFLINE_PRODUCT_DETAIL+product_id);
            setDataToView(detailModel);
        }
        if (GH.isInternetOn(mContext))
        LoadProductDetail();

        Log.e(TAG, "onCreate: "+product_id+" mFor_USER_ID::+"+mFor_USER_ID);
        //int i=10/0;
    }


    private void implementToolbar() {
        toolbar.setTitle(product_name);
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
        getMenuInflater().inflate(R.menu.menu_cart_product, menu);



        MenuItem itemCart = menu.findItem(R.id.action_cart);
        icon = (LayerDrawable) itemCart.getIcon();
        if(Hawk.contains(AppUtils.CART_ITEMS)) {
            GH.setBadgeCount(this, icon,""+Hawk.get(AppUtils.CART_ITEMS));
        }else {
            GH.setBadgeCount(this, icon,"0");
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){

            case R.id.action_cart:
                intent=new Intent(mContext,CartActivity.class);
                startActivity(intent);

                break;
            case R.id.action_compare:
                intent=new Intent(mContext,CompareActivity.class);
                intent.putExtra(AppUtils.ARG_PRODUCT_NAME,product_name);
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

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSlider.stopAutoCycle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSlider.startAutoCycle();
        getSupportActionBar().invalidateOptionsMenu();
    }

    /**
     * Load Product data
     */
    private void LoadProductDetail() {
        Log.e(TAG, "onCreate: "+product_id);
        if(!progressDialog.isShowing()&& !Hawk.contains(AppUtils.OFFLINE_PRODUCT_DETAIL+product_id)) {
            progressDialog.show();
            nsv_main.setVisibility(View.GONE);
        }
       Call<ProductDetailModel> call = RetrofitBuilder.getInstance().getRetrofit().GetProductDetail(RequestBodyBuilder.getInstanse().getRequestForProductDetail(product_id, UserDetails.getInstance(mContext).getUser_id()));

        call.enqueue(new Callback<ProductDetailModel>() {
            @Override
            public void onResponse(Call<ProductDetailModel> call, Response<ProductDetailModel> response) {
                detailModel=response.body();

                if(detailModel.getStatus().equalsIgnoreCase(AppUtils.Success)){
                    Hawk.put(AppUtils.OFFLINE_PRODUCT_DETAIL+product_id,detailModel);
                setDataToView(detailModel);


                }else{

                }
                nsv_main.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ProductDetailModel> call, Throwable t) {
                progressDialog.dismiss();
                nsv_main.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setDataToView(final ProductDetailModel productDetail){
        txtVendorName.setText(productDetail.getProductDetail().get(0).getName());
        txtTotalReviews.setText(String.format(getString(R.string.str_customer_reviews_with_values),productDetail.getTotaluserRated()));
        txtAverageRatting.setText(productDetail.getAverageRating());
        txtDecription.setText(Html.fromHtml(productDetail.getProductDetail().get(0).getLongDescription()));
        txtPrice.setText(mContext.getString(R.string.rs)+GH.getFormatedAmount(productDetail.getProductDetail().get(0).getPrice()));

        adapter=new SampleProductImageListAdapter(mContext,productDetail.getProductDetail().get(0).getMoreimage(), new OnSampleImageClick() {

            @Override
            public void onSampleImageClick(int position) {
                GeneralSingletone.getInstance().setMoreimageList_product(productDetail.getProductDetail().get(0).getMoreimage());
                Intent intent=new Intent(mContext,ProductImageViewerActivity.class);
                intent.putExtra(AppUtils.ARG_MORE_POSITION,position);
                startActivity(intent);
            }
        });
        initializeRecyclerView(productDetail.getProductDetail().get(0).getMoreimage());
        rcSampleImages.setAdapter(adapter);

        llAttributesExapandable=new ExpandableLinearLayout(ProductDetailActivity.this);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llAttributesExapandable.setLayoutParams(layoutParams);
        llAttributesExapandable.setOrientation(LinearLayout.VERTICAL);

        if(productDetail.getProductAllAttribute().size()>0) {
            tvdNoSpecification.setVisibility(View.GONE);
            for (int i = 0; i <productDetail.getProductAllAttribute().size(); i++) {
                View child = getLayoutInflater().inflate(R.layout.layout_attribute_item, null);
                TextView txtAttributeName = (TextView) child.findViewById(R.id.txtAttributeName);
                TextView txtAttributeValue = (TextView) child.findViewById(R.id.txtAttributeValue);
                txtAttributeName.setText(productDetail.getProductAllAttribute().get(i).getAttributeName());
                txtAttributeValue.setText(productDetail.getProductAllAttribute().get(i).getAtrribute());

                llAttributesExapandable.addView(child);
            }
        }else{
            tvdNoSpecification.setVisibility(View.VISIBLE);
            llAttributesExapandable.addView(new View(ProductDetailActivity.this));
        }
        llAttributesExapandable.initLayout(true);
        llAttributes.addView(llAttributesExapandable);
        ttvSpecification.setSelected(false);

        for (int i = 0; i < productDetail.getProductDetail().get(0).getBannermainimage().size(); i++) {

            MyTextSliderView textSliderView = new MyTextSliderView(mContext);

            textSliderView
                    .image(productDetail.getProductDetail().get(0).getBannermainimage().get(i).getBannermainimage())
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(ProductDetailActivity.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putInt("position", i);

            mSlider.addSlider(textSliderView);

        }
        if(productDetail.getProductDetail().get(0).getBannermainimage().size()==1){
            mSlider.stopAutoCycle();
            mSlider.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });

        }
        if(!productDetail.getIscart().equalsIgnoreCase("False")){
            txtInquiry.setTextColor(ContextCompat.getColor(mContext,R.color.toolbar_shadow));
        }else{
            txtInquiry.setTextColor(ContextCompat.getColor(mContext,R.color.white));
        }

    }

    /**
     * Initialize recycler view for GridLayout
     */
    private void initializeRecyclerView(List<com.nectarbits.baraati.Models.ProductDetail.Moreimage> list) {
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
    @Override
    public void onSliderClick(BaseSliderView slider) {

    }




    private void AddtoCart() {

        if(!progressDialog.isShowing())
            progressDialog.show();

        Call<AddtoCartModel> call = RetrofitBuilder.getInstance().getRetrofit().AddtoCart(RequestBodyBuilder.getInstanse().Request_AddtoCart_UpdateCart(UserDetails.getInstance(mContext).getUser_id(),product_id,"1",detailModel.getProductDetail().get(0).getPrice(),mFor_USER_ID,mUserEventTypeEventID));

        call.enqueue(new Callback<AddtoCartModel>() {
            @Override
            public void onResponse(Call<AddtoCartModel> call, Response<AddtoCartModel> response) {
                AddtoCartModel addtoCartModel=response.body();
                if(addtoCartModel.getStatus().equalsIgnoreCase(AppUtils.Success)){
                    detailModel.setIscart("True");
                    Toast.makeText(ProductDetailActivity.this, getString(R.string.str_product_added_to_cart_successfully), Toast.LENGTH_SHORT).show();
                    if(Hawk.contains(AppUtils.CART_ITEMS)){
                        Hawk.put(AppUtils.CART_ITEMS,((int)Hawk.get(AppUtils.CART_ITEMS)+1));
                    }else{
                        Hawk.put(AppUtils.CART_ITEMS,1);
                    }
                    txtInquiry.setTextColor(ContextCompat.getColor(mContext,R.color.toolbar_shadow));

                    getSupportActionBar().invalidateOptionsMenu();

                }else{
                    Toast.makeText(ProductDetailActivity.this, addtoCartModel.getMsg(), Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<AddtoCartModel> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RESULT_OK==resultCode){
            if(resultCode==AppUtils.REQUEST_WRITE_REVIEW){
                LoadProductDetail();
            }
        }
    }
}
