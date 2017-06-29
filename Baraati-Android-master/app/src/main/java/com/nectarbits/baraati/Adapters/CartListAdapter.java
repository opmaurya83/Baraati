package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nectarbits.baraati.Interface.OnCartClick;
import com.nectarbits.baraati.Interface.OnVendorSelect;
import com.nectarbits.baraati.Models.CartList.Cartlistdatum;
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
public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyHolder> {

    private Context mContext;
    private List<Cartlistdatum> cartlistdata;
    private OnCartClick mOnCartClick;

    public CartListAdapter(Context context, List<Cartlistdatum> arrayVendorLists , OnCartClick onCartClick){
        mContext = context;
        cartlistdata = arrayVendorLists;
        mOnCartClick = onCartClick;
    }
    @Override
    public CartListAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_cart_product_list,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(CartListAdapter.MyHolder holder, int position) {

        holder.txtTitle.setText(cartlistdata.get(position).getName());
        holder.txtDesc.setText(cartlistdata.get(position).getTwolinedescription());
        holder.txtPrice.setText(mContext.getString(R.string.rs)+ GH.getFormatedAmount(""+cartlistdata.get(position).getProductTotal()));
        Picasso.with(mContext)
                .load(cartlistdata.get(position).getImage())
                .into(holder.imgProductImage);

        holder.ttvQuontity.setText(cartlistdata.get(position).getQuantity());
        holder.ttvQuontity.setTag(cartlistdata.get(position).getQuantity());
        holder.ttvQuontity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                TextViewTitle ttvQuontity=(TextViewTitle)view;

                if(ttvQuontity.getText().toString().length()==0){
                    ttvQuontity.setText((String)view.getTag());
                }
            }
        });
        holder.ivDelete.setTag(position);
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  int position=(int)v.getTag();
                mOnCartClick.onDeleteClick(position);
            }
        });
        holder.ttvPlus.setTag(position);
        holder.ttvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                mOnCartClick.onPlusClick(position);
            }
        });

        holder.ttvminus.setTag(position);
        holder.ttvminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();

                    mOnCartClick.onMinusClick(position);

            }
        });

        holder.llProduct.setTag(position);
        holder.llProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                mOnCartClick.onProductClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartlistdata.size();
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
        @Bind(R.id.ivDelete)
        ImageView ivDelete;
        @Bind(R.id.ttvQuontity)
        TextViewTitle ttvQuontity;
        @Bind(R.id.ttvPlus)
        TextViewTitle ttvPlus;
        @Bind(R.id.ttvminus)
        TextViewTitle ttvminus;
        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }

    }
}
