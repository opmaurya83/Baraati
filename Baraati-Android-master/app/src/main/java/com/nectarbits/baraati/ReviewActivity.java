package com.nectarbits.baraati;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hedgehog.ratingbar.RatingBar;
import com.nectarbits.baraati.Adapters.RatingListAdapter;
import com.nectarbits.baraati.Adapters.ReviewListAdapter;
import com.nectarbits.baraati.Adapters.SampleImageListAdapter;
import com.nectarbits.baraati.Interface.OnRatingClick;
import com.nectarbits.baraati.Interface.OnReviewSelect;
import com.nectarbits.baraati.Interface.OnVendorSelect;
import com.nectarbits.baraati.Models.AddDates.AddDateModel;
import com.nectarbits.baraati.Models.RattingList.ProductRatingListModel;
import com.nectarbits.baraati.Models.Vendor.VenderInfo;
import com.nectarbits.baraati.Models.Vendor.VendorDetail;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.generalHelper.SingletonUtils;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {

    private static final String TAG = ReviewActivity.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.txtAverageRatting)
    TextViewDescription txtAverageRatting;
    @Bind(R.id.ratingbar)
    RatingBar ratingBar;

    @Bind(R.id.pb_1)
    ProgressBar pb_1;
    @Bind(R.id.pb_2)
    ProgressBar pb_2;
    @Bind(R.id.pb_3)
    ProgressBar pb_3;
    @Bind(R.id.pb_4)
    ProgressBar pb_4;
    @Bind(R.id.pb_5)
    ProgressBar pb_5;
    @Bind(R.id.pb_1_total)
    TextViewDescription pb_1_total;
    @Bind(R.id.pb_2_total)
    TextViewDescription pb_2_total;
    @Bind(R.id.pb_3_total)
    TextViewDescription pb_3_total;
    @Bind(R.id.pb_4_total)
    TextViewDescription pb_4_total;
    @Bind(R.id.pb_5_total)
    TextViewDescription pb_5_total;
    @Bind(R.id.llSortReview)
    LinearLayout llSortReview;
    @Bind(R.id.llWriteReview)
    LinearLayout llWriteReview;
    @Bind(R.id.rcReviews)
    RecyclerView rcReviews;
    @Bind(R.id.empty_text)
    TextViewTitle empty_text;
    @Bind(R.id.tvtreviewspn)
    TextViewTitle tvtreviewspn;
    Context mContext;
    ProgressDialog progressDialog;

    ReviewListAdapter adapter;
    ProductRatingListModel productRatingListModel;
    String mProductId;
    RatingListAdapter ratingListAdapter;

    @OnClick(R.id.llSortReview)
    void onSortReview(){
        OnSort();
    }

    @OnClick(R.id.llWriteReview)
    void onWriteReview(){
        if(productRatingListModel!=null) {

            if(productRatingListModel.getAlreadyRated().equalsIgnoreCase("1")){
                Toast.makeText(mContext, getString(R.string.str_already_reviewed), Toast.LENGTH_SHORT).show();
            }else if(productRatingListModel.getCanRate().equalsIgnoreCase("0")){
                Toast.makeText(mContext, getString(R.string.str_write_review_denied), Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(this, AddReviewActivity.class);
                intent.putExtra(AppUtils.ARG_PRODUCT_ID, mProductId);
                intent.putExtra(AppUtils.ARG_ISUPDATE_REVIEW, AppUtils.FALSE);
                startActivityForResult(intent, AppUtils.REQUEST_WRITE_REVIEW);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);
        mContext=this;
        progressDialog = new ProgressDialog(mContext);
        implementToolbar();
        //Get Intent
        mProductId = getIntent().getStringExtra(AppUtils.ARG_PRODUCT_ID);
        JSONObject jsonObject=RequestBodyBuilder.getInstanse().getRequestReviewListingResponse(mProductId,UserDetails.getInstance(mContext).getUser_id());
        if (GH.isInternetOn(mContext))
        RequestForRatingList(jsonObject);
        Log.e(TAG, "onCreate: "+jsonObject);



    }

    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.str_reviews));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        dialog.setContentView(R.layout.dialog_sorting_review);
        dialog.setCanceledOnTouchOutside(true);

        TextView txtRecentReview = (TextView) dialog.findViewById(R.id.txtHighToLow);
        TextView txtTopReview = (TextView) dialog.findViewById(R.id.txtLowToHigh);


        txtRecentReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                tvtreviewspn.setText(getString(R.string.str_recent_review));
                JSONObject jsonObject= RequestBodyBuilder.getInstanse().getRequest_Review_Sort_Response(mProductId,UserDetails.getInstance(mContext).getUser_id(),"0", "1");
                if (GH.isInternetOn(mContext))
                 RequestForRatingList(jsonObject);

            }
        });
        txtTopReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvtreviewspn.setText(getString(R.string.str_top_review));
                dialog.dismiss();
                JSONObject jsonObject= RequestBodyBuilder.getInstanse().getRequest_Review_Sort_Response(mProductId,UserDetails.getInstance(mContext).getUser_id(),"1", "0");
                if (GH.isInternetOn(mContext))
                RequestForRatingList(jsonObject);

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

    /**
     * Load QuotationList
     */
    private void RequestForRatingList(JSONObject jsonObject) {
        if (!progressDialog.isShowing())
            progressDialog.show();

        Log.e(TAG, "RequestForRatingList: "+jsonObject.toString());
        rcReviews.setVisibility(View.VISIBLE);
        empty_text.setVisibility(View.GONE);
        empty_text.setText(getString(R.string.str_no_review_found));
        Call<ProductRatingListModel> call = RetrofitBuilder.getInstance().getRetrofit().GetProductRatings(jsonObject);

        call.enqueue(new Callback<ProductRatingListModel>() {
            @Override
            public void onResponse(Call<ProductRatingListModel> call, Response<ProductRatingListModel> response) {
                productRatingListModel=response.body();
                if(productRatingListModel.getStatus().equalsIgnoreCase(AppUtils.Success)){

                    txtAverageRatting.setText(String.format(getString(R.string.str_out_of_5_star),productRatingListModel.getAverageRating()));
                    pb_1.setProgress((int)Float.parseFloat(productRatingListModel.getRating1()));
                    pb_2.setProgress((int)Float.parseFloat(productRatingListModel.getRating2()));
                    pb_3.setProgress((int)Float.parseFloat(productRatingListModel.getRating3()));
                    pb_4.setProgress((int)Float.parseFloat(productRatingListModel.getRating4()));
                    pb_5.setProgress((int)Float.parseFloat(productRatingListModel.getRating5()));

                    pb_1_total.setText(productRatingListModel.getUserwithrate1());
                    pb_2_total.setText(productRatingListModel.getUserwithrate2());
                    pb_3_total.setText(productRatingListModel.getUserwithrate3());
                    pb_4_total.setText(productRatingListModel.getUserwithrate4());
                    pb_5_total.setText(productRatingListModel.getUserwithrate5());

                    ratingBar.setStar(productRatingListModel.getAverageRating());
                    ratingListAdapter=new RatingListAdapter(mContext, productRatingListModel.getRatingDetail(), new OnRatingClick() {
                        @Override
                        public void onRating(int position) {
                            if(UserDetails.getInstance(mContext).getUser_id().equalsIgnoreCase(productRatingListModel.getRatingDetail().get(position).getCustomerId())) {
                                SingletonUtils.getInstance().setRatingDetail(productRatingListModel.getRatingDetail().get(position));
                                Intent intent = new Intent(mContext, AddReviewActivity.class);
                                intent.putExtra(AppUtils.ARG_PRODUCT_ID, mProductId);
                                intent.putExtra(AppUtils.ARG_ISUPDATE_REVIEW, AppUtils.TRUE);
                                startActivityForResult(intent, AppUtils.REQUEST_WRITE_REVIEW);
                            }
                        }
                    });
                    LinearLayoutManager layoutManager = new LinearLayoutManager(ReviewActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rcReviews.setLayoutManager(layoutManager);
                    rcReviews.setAdapter(ratingListAdapter);

                    if(ratingListAdapter.getItemCount()==0){
                        rcReviews.setVisibility(View.GONE);
                        empty_text.setVisibility(View.VISIBLE);
                    }

                }else {
                    rcReviews.setVisibility(View.GONE);
                    empty_text.setVisibility(View.VISIBLE);
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ProductRatingListModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==AppUtils.REQUEST_WRITE_REVIEW) {
            if (resultCode == RESULT_OK) {
                JSONObject jsonObject=RequestBodyBuilder.getInstanse().getRequestReviewListingResponse(mProductId,UserDetails.getInstance(mContext).getUser_id());
                if (GH.isInternetOn(mContext))
                RequestForRatingList(jsonObject);
                setResult(RESULT_OK);
            }
        }
    }
}
