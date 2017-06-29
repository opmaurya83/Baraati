package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nectarbits.baraati.Interface.OnProductSelect;
import com.nectarbits.baraati.Models.InquiryModel.List.AssignInqiry;
import com.nectarbits.baraati.Models.InquiryModel.List.MyInqiry;
import com.nectarbits.baraati.Models.VendorBazaar.ProductDetail;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.GH;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nectarbits on 23/08/16.
 */
public class InquiryListAdapter extends RecyclerView.Adapter<InquiryListAdapter.MyHolder> {

    private Context mContext;
    private List<Object> inquiryList;
    private OnInquiryClickListener onInquiryClickListener;
    private Boolean isPesonla=false;
    public InquiryListAdapter(Context context, List<Object> arrayVendorLists ,Boolean isPesonla, OnInquiryClickListener onInquiryClickListener){
        mContext = context;
        inquiryList = arrayVendorLists;
        this.isPesonla=isPesonla;
        this.onInquiryClickListener = onInquiryClickListener;
    }
    @Override
    public InquiryListAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_inquiry_list,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(InquiryListAdapter.MyHolder holder, int position) {

                if(isPesonla){
                    setPersonalInquiry(holder,position);
                }else{
                    setAssignedInquiry(holder,position);
                }



    }

    private void setPersonalInquiry(InquiryListAdapter.MyHolder holder, int position){

        MyInqiry myInqiry=(MyInqiry) inquiryList.get(position);

        holder.ll_Inquiry_by.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(myInqiry.getInquiryby())) {
            holder.txt_inquiry_by.setText(myInqiry.getInquiryby());
        }else {
            holder.txt_inquiry_by.setText(mContext.getString(R.string.me));
        }
        holder.txtTitle.setText(myInqiry.getVendorName());
        holder.txtDesc.setText(myInqiry.getTwolinedescription());
        holder.txtPrice.setText(mContext.getString(R.string.rs)+ GH.getFormatedAmount(myInqiry.getBudget()));
            try {
                Picasso.with(mContext)
                        .load(myInqiry.getImage())
                        .into(holder.imgProductImage);
            }catch (Exception ex){

            }

        holder.iv_message.setTag(position);
        holder.iv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                onInquiryClickListener.onMessageClick(position);
            }
        });
    }

    private void setAssignedInquiry(InquiryListAdapter.MyHolder holder, int position){
        AssignInqiry myInqiry=(AssignInqiry) inquiryList.get(position);
        holder.ll_Inquiry_by.setVisibility(View.GONE);
        holder.txtTitle.setText(myInqiry.getVendorName());
        holder.txtDesc.setText(myInqiry.getTwolinedescription());
        holder.txtPrice.setText(mContext.getString(R.string.rs)+GH.getFormatedAmount(myInqiry.getBudget()));

        try {
            Picasso.with(mContext)
                    .load(myInqiry.getImage())
                    .into(holder.imgProductImage);
        }catch (Exception ex){

        }


        holder.iv_message.setTag(position);
        holder.iv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                onInquiryClickListener.onMessageClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return inquiryList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
         @Bind(R.id.llProduct)
        LinearLayout llProduct;
        @Bind(R.id.txtTitle)
        TextViewTitle txtTitle;
        @Bind(R.id.txtDesc)
        TextViewDescription txtDesc;
        @Bind(R.id.txtPrice)
        TextViewTitle txtPrice;
        @Bind(R.id.imgProductImage)
        ImageView imgProductImage;
        @Bind(R.id.iv_message)
        ImageView iv_message;

        @Bind(R.id.ll_Inquiry_by)
        LinearLayout ll_Inquiry_by;
        @Bind(R.id.txt_inquiry_by)
        TextViewTitle txt_inquiry_by;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }
    }

    public interface OnInquiryClickListener {

        void onMessageClick(int position);
        void onInquiryClick( AssignInqiry myInqiry);


    }

}
