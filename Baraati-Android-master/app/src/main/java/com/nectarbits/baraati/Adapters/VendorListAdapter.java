package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nectarbits.baraati.Interface.OnVendorSelect;

import com.nectarbits.baraati.Models.VendorBazaar.VendorDetail;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nectarbits on 23/08/16.
 */
public class VendorListAdapter extends RecyclerView.Adapter<VendorListAdapter.MyHolder> {

    private Context mContext;
    private List<VendorDetail> vendorDetailList;
    private OnVendorSelect mOnVendorSelect;

    public VendorListAdapter(Context context, List<VendorDetail> arrayVendorLists , OnVendorSelect onVendorSelect){
        mContext = context;
        vendorDetailList = arrayVendorLists;
        mOnVendorSelect = onVendorSelect;
    }
    @Override
    public VendorListAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_vendor_things_list_item,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(VendorListAdapter.MyHolder holder, int position) {
        //    holder.txtVendorName.setText(vendorDetailList.get(position).getVendorName());
        if(vendorDetailList.get(position).getSpecialist().length()>0)
            holder.txtProductName.setText(vendorDetailList.get(position).getSpecialist().toUpperCase());

        holder.txtProductName.setAllCaps(true);
        if(vendorDetailList.get(position).getMainimage().size()>0) {
            Picasso.with(mContext)
                    .load(vendorDetailList.get(position).getMainimage().get(0).getMainimage())
                    .into(holder.imgVendor);
        }
        if(vendorDetailList.get(position).getCompany_Name().length()>0)
        {
            holder.txtVendorName.setText("By "+vendorDetailList.get(position).getCompany_Name());
        }else {
            holder.txtVendorName.setText("By Kapoor & sons");
        }
            //holder.txtProductName.setText(vendorDetailList.get(position).getPname());
            holder.viewDetail.setTag(position);

            holder.viewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnVendorSelect.onVendorSelectFromList((int)v.getTag(), AppUtils.KEY_PASS_DETAIL_VIEW);

                }
            });


    }

    @Override
    public int getItemCount() {
        return vendorDetailList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView txtProductName;
        private TextView txtVendorName;
        private LinearLayout linMain;
        private View viewDetail;
        private ImageView imgVendor;


        public MyHolder(View itemView) {
            super(itemView);

            txtProductName = (TextView)itemView.findViewById(R.id.txtProductName);
            txtVendorName = (TextView)itemView.findViewById(R.id.txtVendorName);
            linMain = (LinearLayout)itemView.findViewById(R.id.linMain);
            viewDetail = (View)itemView.findViewById(R.id.viewDetail);
            imgVendor=(ImageView)itemView.findViewById(R.id.imgVendor);
        }
    }
}
