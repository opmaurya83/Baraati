package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nectarbits.baraati.Interface.OnCartClick;
import com.nectarbits.baraati.Interface.OnOrderProductClick;
import com.nectarbits.baraati.Models.OrderList.List.AssigneeOrder;
import com.nectarbits.baraati.Models.OrderList.List.OwnOrder;
import com.nectarbits.baraati.Models.OrderList.ProductDetail;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nectarbits on 23/08/16.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyHolder> {

    private Context mContext;
    private List<Object> quotationdetailList;
    private OnOrderProductClick mOnCartClick;
    private Boolean isPesonla=false;
    public OrderListAdapter(Context context, List<Object> arrayVendorLists , OnOrderProductClick onCartClick,Boolean aBoolean){
        mContext = context;
        quotationdetailList = arrayVendorLists;
        mOnCartClick = onCartClick;
        this.isPesonla=aBoolean;
    }
    @Override
    public OrderListAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_order_item,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderListAdapter.MyHolder holder, int position) {

       if(isPesonla){
           setPersonalOrder(holder,position);
       }else{
           setAssigned(holder,position);
       }
    }


    private void setPersonalOrder(OrderListAdapter.MyHolder holder, int position){
        OwnOrder ownOrder=(OwnOrder)quotationdetailList.get(position);
        holder.ll_order_by.setVisibility(View.VISIBLE);
        if(!TextUtils.isEmpty(ownOrder.getOrderBy()))
        {
            holder.txt_order_by.setText(ownOrder.getOrderBy());
        }else {
            holder.txt_order_by.setText(mContext.getString(R.string.me));
        }
        holder.txtTitle.setText(ownOrder.getProduct());
        holder.txtDesc.setText(ownOrder.getTwolinedescription());
        holder.ttvOrderDate.setText(ownOrder.getOrderDate());
        holder.ttvOrderNumber.setText("(ORD_"+ownOrder.getOrderId()+")");
        Picasso.with(mContext)
                .load(ownOrder.getImage())
                .into(holder.imgProductImage);



        holder.llProduct.setTag(position);
        holder.llProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                mOnCartClick.onOrderProductClick(position);
            }
        });

    }

    private void setAssigned(OrderListAdapter.MyHolder holder, int position){

        AssigneeOrder ownOrder=(AssigneeOrder)quotationdetailList.get(position);
        holder.ll_order_by.setVisibility(View.GONE);
        holder.txtTitle.setText(ownOrder.getProduct());
        holder.txtDesc.setText(ownOrder.getTwolinedescription());
        holder.ttvOrderDate.setText(ownOrder.getOrderDate());
        holder.ttvOrderNumber.setText("(ORD_"+ownOrder.getOrderId()+")");
        Picasso.with(mContext)
                .load(ownOrder.getImage())
                .into(holder.imgProductImage);



        holder.llProduct.setTag(position);
        holder.llProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                mOnCartClick.onOrderProductClick(position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return quotationdetailList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.llProduct)
        LinearLayout llProduct;
        @Bind(R.id.txtTitle)
        TextViewTitle txtTitle;
        @Bind(R.id.txtDesc)
        TextViewDescription txtDesc;
        @Bind(R.id.ttvOrderDate)
        TextViewTitle ttvOrderDate;
        @Bind(R.id.ttvOrderNumber)
        TextViewTitle ttvOrderNumber;
        @Bind(R.id.ttvStatus)
        TextViewDescription ttvStatus;
        @Bind(R.id.ttvDate)
        TextViewDescription ttvDate;
        @Bind(R.id.imgProductImage)
        ImageView imgProductImage;
        @Bind(R.id.ll_order_by)
        LinearLayout ll_order_by;
        @Bind(R.id.txt_order_by)
        TextViewTitle txt_order_by;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }

    }
}
