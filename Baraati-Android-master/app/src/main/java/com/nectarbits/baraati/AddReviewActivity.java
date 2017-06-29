package com.nectarbits.baraati;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hedgehog.ratingbar.RatingBar;
import com.nectarbits.baraati.Models.AddDates.AddDateModel;
import com.nectarbits.baraati.Models.WriteReview.WriteReviewModel;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.EditTextTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.L;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.generalHelper.SingletonUtils;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddReviewActivity extends AppCompatActivity implements RatingBar.OnRatingChangeListener {

    @Bind(R.id.evNickName)
    EditTextTitle evNickName;
    @Bind(R.id.evComment)
    EditTextTitle evComment;
    @Bind(R.id.ratingbar)
    RatingBar ratingBar;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private Context mContext;
    ProgressDialog progressDialog;
    String mProductId="";
    Boolean ISUPDATE=false;
    int ratting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        ButterKnife.bind(this);
        mContext=this;
        progressDialog = new ProgressDialog(mContext);

        mProductId = getIntent().getStringExtra(AppUtils.ARG_PRODUCT_ID);
        ratingBar.setOnRatingChangeListener(this);
        Bundle bundle=getIntent().getExtras();
        if(bundle.containsKey(AppUtils.ARG_ISUPDATE_REVIEW)){
            if(bundle.getString(AppUtils.ARG_ISUPDATE_REVIEW).equalsIgnoreCase(AppUtils.TRUE))
            {
                ISUPDATE=true;
                evNickName.setText(SingletonUtils.getInstance().getRatingDetail().getUser());
                evComment.setText(SingletonUtils.getInstance().getRatingDetail().getComment());
                ratingBar.setStar(Float.parseFloat(SingletonUtils.getInstance().getRatingDetail().getRating()));
                ratting=Integer.parseInt(SingletonUtils.getInstance().getRatingDetail().getRating());
            }else{
                ISUPDATE=false;
            }
        }

        implementToolbar();
    }

    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.str_writereview));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done, menu);




        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_done:
                if (evNickName.getText().toString().length() <= 0) {
                    Toast.makeText(mContext, getString(R.string.str_nick_name_must_not_be_empty), Toast.LENGTH_SHORT).show();
                } else if (ratting <= 0) {
                    Toast.makeText(mContext, getString(R.string.str_must_rate), Toast.LENGTH_SHORT).show();
                } else if (evComment.getText().length() <= 0) {
                    Toast.makeText(mContext, getString(R.string.str_comment_must_not_be_empty), Toast.LENGTH_SHORT).show();
                } else if (evComment.getText().length() <= 50) {
                    Toast.makeText(mContext, getString(R.string.str_comment_must_lenght), Toast.LENGTH_SHORT).show();
                } else {
                    if(!ISUPDATE) {
                        if (GH.isInternetOn(mContext))
                        submitReview();
                    }else{
                        if (GH.isInternetOn(mContext))
                        updateReview();
                    }
                }
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRatingChange(float RatingCount) {
        ratting = (int) RatingCount;
    }


    /**
     * SubmitReview
     */
    private void submitReview() {
        if (!progressDialog.isShowing())
            progressDialog.show();

        Call<WriteReviewModel> call = RetrofitBuilder.getInstance().getRetrofit().WriteReview(RequestBodyBuilder.getInstanse().getRequestBuilderForSubmitReview(UserDetails.getInstance(mContext).getUser_id(), mProductId, ratting, evComment.getText().toString(), evNickName.getText().toString()));

        call.enqueue(new Callback<WriteReviewModel>() {
            @Override
            public void onResponse(Call<WriteReviewModel> call, Response<WriteReviewModel> response) {

                if (response.body().getStatus().equalsIgnoreCase(AppUtils.Success)) {
                    setResult(RESULT_OK);
                    finish();

                } else {
                    Toast.makeText(AddReviewActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<WriteReviewModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }




    /**
     * UpdateReview
     */
    private void updateReview() {
        if (!progressDialog.isShowing())
            progressDialog.show();

        Call<WriteReviewModel> call = RetrofitBuilder.getInstance().getRetrofit().UpdateReview(RequestBodyBuilder.getInstanse().getRequestBuilderForEditReview(UserDetails.getInstance(mContext).getUser_id(), mProductId, ratting, evComment.getText().toString(), evNickName.getText().toString(),SingletonUtils.getInstance().getRatingDetail().getProductReviewId()));

        call.enqueue(new Callback<WriteReviewModel>() {
            @Override
            public void onResponse(Call<WriteReviewModel> call, Response<WriteReviewModel> response) {

                if (response.body().getStatus().equalsIgnoreCase(AppUtils.Success)) {
                    setResult(RESULT_OK);
                    finish();

                } else {
                    Toast.makeText(AddReviewActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<WriteReviewModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }
}
