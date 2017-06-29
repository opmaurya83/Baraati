package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nectarbits.baraati.Interface.OnProductSelect;

import com.nectarbits.baraati.Models.Compare.ProductDetail;
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
public class ProductCompareListAdapter extends RecyclerView.Adapter<ProductCompareListAdapter.MyHolder> {

    private Context mContext;
    private List<ProductDetail> productDetailList;
    private OnProductSelect onProductSelect;

    public ProductCompareListAdapter(Context context, List<ProductDetail> arrayVendorLists , OnProductSelect onProductSelect){
        mContext = context;
        productDetailList = arrayVendorLists;
        this.onProductSelect = onProductSelect;
    }
    @Override
    public ProductCompareListAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_product_list,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductCompareListAdapter.MyHolder holder, int position) {

        holder.txtTitle.setText(productDetailList.get(position).getProduct());
        holder.txtDesc.setText(productDetailList.get(position).getTwolinedescription());
       holder.txtPrice.setText(mContext.getString(R.string.rs)+productDetailList.get(position).getPrice());
        if(productDetailList.get(position).getMainimage().size()>0) {
            Picasso.with(mContext)
                    .load(productDetailList.get(position).getMainimage().get(0).getThumimage())
                    .into(holder.imgProductImage);
        }
        holder.llProduct.setTag(position);
        holder.llProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                onProductSelect.onProductSelect(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return productDetailList.size();
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
        @Bind(R.id.txtRatting)
        TextViewTitle txtRatting;
        @Bind(R.id.imgProductImage)
        ImageView imgProductImage;


        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }
    }
}
