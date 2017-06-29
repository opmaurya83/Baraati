package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nectarbits.baraati.Interface.OnSampleImageClick;

import com.nectarbits.baraati.Models.ProductDetail.Moreimage;
import com.nectarbits.baraati.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nectarbits on 23/08/16.
 */
public class SampleProductImageListAdapter extends RecyclerView.Adapter<SampleProductImageListAdapter.MyHolder> {

    private Context mContext;
    private List<Moreimage> moreimageList;
    private OnSampleImageClick onSampleImageClick;

    public SampleProductImageListAdapter(Context context, List<Moreimage> arrayVendorLists , OnSampleImageClick onSampleImageClick){
        mContext = context;
        moreimageList = arrayVendorLists;
        this.onSampleImageClick = onSampleImageClick;
    }
    @Override
    public SampleProductImageListAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_sampleimage,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(SampleProductImageListAdapter.MyHolder holder, final int position) {

        Picasso.with(mContext)
                .load(moreimageList.get(position).getThumimage())
                .into(holder.imgSampleImage);
        holder.imgSampleImage.setTag(position);
        holder.imgSampleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                onSampleImageClick.onSampleImageClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return moreimageList.size();

    }

    public class MyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imgsampleimage)
        ImageView imgSampleImage;



        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }
    }

}
