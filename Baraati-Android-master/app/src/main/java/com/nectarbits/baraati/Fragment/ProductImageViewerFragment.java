package com.nectarbits.baraati.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.nectarbits.baraati.Models.ProductDetail.Moreimage;
import com.nectarbits.baraati.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by nectarbits on 15/03/16.
 */
@SuppressLint("ValidFragment")
public class ProductImageViewerFragment extends Fragment {
    private Context mContext;
    private List<Moreimage> mArrayList;
    private String mPosition;
    PhotoViewAttacher mAttacher;
    public ProductImageViewerFragment(Context context, List<Moreimage> arrayList, String position){
        mContext=context;
        mArrayList=arrayList;
        mPosition=position;
    }

    private ImageView imgViewer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_image_viewer,container,false);

        imgViewer=(ImageView)v.findViewById(R.id.imgViewer);
        mAttacher = new PhotoViewAttacher(imgViewer);
        mAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);
        if (mArrayList.get(Integer.parseInt(mPosition)).getMainimage() != null){
            Picasso.with(mContext)
                    .load(mArrayList.get(Integer.parseInt(mPosition)).getMainimage() )
                    .placeholder(R.drawable.icon_image_place_holder)
                    .error(R.drawable.icon_image_place_holder)
                    .into(imgViewer, new Callback() {
                        @Override
                        public void onSuccess() {
                            mAttacher.update();
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }  else {
            Picasso.with(mContext)
                    .load(R.drawable.icon_image_place_holder)
                    .into(imgViewer, new Callback() {
                        @Override
                        public void onSuccess() {
                            mAttacher.update();
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }



        return v;

    }
}
