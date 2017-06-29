package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nectarbits.baraati.R;

import java.util.ArrayList;

/**
 * Created by nectarbits on 23/08/16.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

    private Context mContext;
    private ArrayList<String> mArrayList;

    public ProductAdapter(Context context,ArrayList<String> arrayList){
        mContext = context;
        mArrayList = arrayList;
    }
    @Override
    public ProductAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_vendor_things_list_item,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.MyHolder holder, int position) {
            holder.txtProductName.setText(mArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }



    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView txtProductName;
        public MyHolder(View itemView) {
            super(itemView);

            txtProductName = (TextView)itemView.findViewById(R.id.txtProductName);
        }
    }
}
