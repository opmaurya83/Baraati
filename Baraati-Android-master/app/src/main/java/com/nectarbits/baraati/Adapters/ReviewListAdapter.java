package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hedgehog.ratingbar.RatingBar;
import com.nectarbits.baraati.Interface.OnReviewSelect;
import com.nectarbits.baraati.Models.Vendor.VendorDetail;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nectarbits on 23/08/16.
 */
public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.MyHolder> {

    private Context mContext;
    private List<VendorDetail> myAVendorLists;
    private OnReviewSelect onReviewSelect;

    public ReviewListAdapter(Context context, List<VendorDetail> arrayVendorLists , OnReviewSelect onReviewSelect){
        mContext = context;
        myAVendorLists = arrayVendorLists;
        this.onReviewSelect = onReviewSelect;
    }
    @Override
    public ReviewListAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_review,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewListAdapter.MyHolder holder, int position) {

        holder.llReview.setTag(position);
        holder.llReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                onReviewSelect.onReviewSelect(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return /*myAVendorLists.size()*/20;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
         @Bind(R.id.llReview)
        LinearLayout llReview;
        @Bind(R.id.txtName)
        TextViewTitle txtName;
        @Bind(R.id.txtDate)
        TextViewDescription txtDate;
        @Bind(R.id.txtComment)
        TextViewDescription txtComment;
        @Bind(R.id.ratingbar)
        RatingBar ratingBar;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }
    }
}
