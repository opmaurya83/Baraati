package com.nectarbits.baraati.Chat.ui.adapters.base;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nectarbits.baraati.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nectarbits.baraati.Chat.utils.image.ImageLoaderUtils;
import com.quickblox.q_municate_core.models.AppSession;
import com.quickblox.users.model.QBUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected LayoutInflater layoutInflater;
    protected Context context;
    protected List<T> objectsList;
    protected Resources resources;

    protected QBUser currentUser;

    public BaseListAdapter(Context context, List<T> objectsList) {
        this.context = context;
        this.objectsList = objectsList;
        this.layoutInflater = LayoutInflater.from(context);
        resources = context.getResources();
        currentUser = AppSession.getSession().getUser();
    }

    @Override
    public int getCount() {
        return objectsList.size();
    }

    @Override
    public T getItem(int position) {
        return objectsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setNewData(List<T> newData) {
        objectsList = newData;
        notifyDataSetChanged();
    }

    protected void displayAvatarImage(String uri, ImageView imageView) {
        Log.e("TAG", "displayAvatarImage: "+uri);
        try {
            Picasso.with(context)
                    .load(uri)
                    .placeholder(R.drawable.placeholder_user)
                    .into(imageView);
        }catch (Exception ex){

        }
        //ImageLoader.getInstance().displayImage(uri, imageView, ImageLoaderUtils.UIL_USER_AVATAR_DISPLAY_OPTIONS);
    }

    protected void displayGroupPhotoImage(String uri, ImageView imageView) {
        //ImageLoader.getInstance().displayImage(uri, imageView, ImageLoaderUtils.UIL_GROUP_AVATAR_DISPLAY_OPTIONS);
        try {
            Picasso.with(context)
                    .load(uri)
                    .placeholder(R.drawable.placeholder_group)
                    .into(imageView);
        }catch (Exception ex){

        }
    }
}