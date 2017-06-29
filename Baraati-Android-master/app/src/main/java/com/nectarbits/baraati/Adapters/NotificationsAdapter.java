package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.nectarbits.baraati.Models.Notifications.List.Detail;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nectarbits on 19/08/16.
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.MyHolder> {

    private Context mContext;
    private List<Detail> mArrayList;
    private OnNotificationClickListener onMainCategorySelect;
    public NotificationsAdapter(Context context, List<Detail> arrayList, OnNotificationClickListener onMainCategorySelect){
        mContext = context;
        mArrayList = arrayList;
        this.onMainCategorySelect=onMainCategorySelect;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_notification,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        holder.tvt_notification_date.setText(mArrayList.get(position).getNotificationDate());
        if(!TextUtils.isEmpty(mArrayList.get(position).getProfilePicture())) {
            Picasso.with(mContext)
                    .load(mArrayList.get(position).getProfilePicture())
                    .placeholder(R.drawable.icon_dem)
                    .into(holder.iv_UserImage);
        }
        if(mArrayList.get(position).getIsread().equalsIgnoreCase("0")){
            holder.tvt_notification.setTypeface(null, Typeface.BOLD);
            holder.cv_main.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.white));
        }else{
            holder.tvt_notification.setTypeface(null, Typeface.NORMAL);
            holder.cv_main.setCardBackgroundColor(ContextCompat.getColor(mContext,R.color.color_read_notofication));
        }

        //holder.tvt_notification.setText( Html.fromHtml("RGB colors are <font color='#FF0000'>RED</font>, <font color='#00FF00'>GREEN</font> and <font color='#0000FF'>BLUE</font>"));
        holder.tvt_notification.setText( Html.fromHtml(mArrayList.get(position).getMessage()));
       /* if(mArrayList.get(position).getType().equalsIgnoreCase(AppUtils.Assign)){
            SpannableString str1= new SpannableString(mArrayList.get(position).getUserName());
            str1.setSpan(new ForegroundColorSpan(Color.RED), 0, str1.length(), 0);
            builder.append(str1);

            SpannableString str2= new SpannableString(" has assigned you responsibilty of ");
            str2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, str2.length(), 0);
            builder.append(str2);

            SpannableString str3= new SpannableString(mArrayList.get(position).getEventName());
            str3.setSpan(new ForegroundColorSpan(Color.RED), 0, str3.length(), 0);
            builder.append(str3);

            holder.tvt_notification.setText( builder, TextView.BufferType.SPANNABLE);
        }else if(mArrayList.get(position).getType().equalsIgnoreCase(AppUtils.Accepted)){
            SpannableString str1= new SpannableString(mArrayList.get(position).getUserName());
            str1.setSpan(new ForegroundColorSpan(Color.RED), 0, str1.length(), 0);
            builder.append(str1);

            SpannableString str2= new SpannableString(" has assigned you responsibilty of ");
            str2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, str2.length(), 0);
            builder.append(str2);

            SpannableString str3= new SpannableString(mArrayList.get(position).getEventName());
            str3.setSpan(new ForegroundColorSpan(Color.RED), 0, str3.length(), 0);
            builder.append(str3);

            holder.tvt_notification.setText( builder, TextView.BufferType.SPANNABLE);
        }*/

        holder.tblRow.setTag(position);
        holder.tblRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // tblRow.setSelected(true);
                onMainCategorySelect.onNotificationClick((int)v.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        private TextViewTitle tvt_notification;
        private CircularImageView iv_UserImage;
        private TextViewDescription tvt_notification_date;
        private TableRow tblRow;
        private CardView cv_main;

        public MyHolder(View itemView) {
            super(itemView);

            tvt_notification = (TextViewTitle)itemView.findViewById(R.id.tvt_notification);
            iv_UserImage = (CircularImageView) itemView.findViewById(R.id.iv_userImage);
            tvt_notification_date = (TextViewDescription)itemView.findViewById(R.id.tvt_notification_date);
            tblRow = (TableRow)itemView.findViewById(R.id.tblRow);
            cv_main = (CardView) itemView.findViewById(R.id.cv_main);


        }
    }

    public interface OnNotificationClickListener {

        void onNotificationClick(int position);
    }
}
