package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.hedgehog.ratingbar.RatingBar;
import com.nectarbits.baraati.Interface.OnRatingClick;
import com.nectarbits.baraati.Models.RattingList.RatingDetail;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by root on 17/6/16.
 */
public class RatingListAdapter extends RecyclerView.Adapter<RatingListAdapter.MyViewHolder> {

    private Context mContext;

    private List<RatingDetail> ratingDetails;

    private OnRatingClick onRatingClick;



    public RatingListAdapter(Context context, List<RatingDetail> quotationdetailsList, OnRatingClick onRatingClick) {
        mContext = context;
        this.ratingDetails = quotationdetailsList;
        Log.e("TAG", "QuotationListAdapter: "+quotationdetailsList.size());
        this.onRatingClick=onRatingClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_review, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Log.e("TAG", "onBindViewHolder: "+position);
        final RatingDetail ratingDetail = ratingDetails.get(position);
        holder.txtName.setText(ratingDetail.getUser());
        holder.txtReview.setText(ratingDetail.getComment());
        holder.txtDate.setText(ratingDetail.getDate());
        holder.ratingbar.setStar(Float.parseFloat(ratingDetail.getRating()));
        /*holder.txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.txtReview.isExpanded()){
                    holder.txtMore.setText(mContext.getString(R.string.str_more));
                    holder.txtReview.collapse();
                }else{
                    holder.txtMore.setText(mContext.getString(R.string.str_less));
                    holder.txtReview.expand();
                }
            }
        });*/
        holder.llReview.setTag(position);
        holder.llReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRatingClick.onRating((int)v.getTag());
            }
        });

    }

    @Override
    public int getItemCount() {
        return ratingDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.llReview)
        LinearLayout llReview;
        @Bind(R.id.txtName)
        TextViewTitle txtName;
        @Bind(R.id.txtDate)
        TextViewDescription txtDate;
        @Bind(R.id.ratingbar)
        RatingBar ratingbar;
        @Bind(R.id.txtComment)
        TextViewDescription txtReview;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }
    }
}