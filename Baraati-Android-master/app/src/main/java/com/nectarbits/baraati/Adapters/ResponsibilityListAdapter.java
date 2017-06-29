package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.nectarbits.baraati.Interface.OnSubCategorySelect;
import com.nectarbits.baraati.Models.Resposibility.Add.SharedeventDetail;
import com.nectarbits.baraati.Models.SubCategory.SubCaregoryDetail;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.tr4android.recyclerviewslideitem.SwipeAdapter;
import com.tr4android.recyclerviewslideitem.SwipeConfiguration;

import java.util.List;

/**
 * Created by nectarbits on 19/08/16.
 */
public class ResponsibilityListAdapter extends SwipeAdapter {

    private Context mContext;
    private List<SharedeventDetail> mArrayList;

    private  OnMemberClick onMemberClick;
    public ResponsibilityListAdapter(Context context, List<SharedeventDetail> arrayList, OnMemberClick onSubCategorySelect){
        mContext = context;
        mArrayList = arrayList;
        this.onMemberClick=onSubCategorySelect;
    }

   /* @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sub_category_list_item,parent,false);

        return new MyHolder(view);
    }
*/
    @Override
    public RecyclerView.ViewHolder onCreateSwipeViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_responsible_member,viewGroup,true);

        return new MyHolder(view);
    }

    @Override
    public void onBindSwipeViewHolder(RecyclerView.ViewHolder ViewHolder, int position) {
        MyHolder holder=(MyHolder)ViewHolder;
        holder.tvt_member.setText(mArrayList.get(position).getName());
        holder.tvt_member.setTag(position);

        holder.checkBox.setTag(position);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position=(int)buttonView.getTag();
                if(isChecked){
                    onMemberClick.onMemberClick(position);
                }else{
                    onMemberClick.onMemberClick(position);
                }
            }
        });

        if(mArrayList.get(position).getInvitationStatus().equalsIgnoreCase(AppUtils.Pending)){
            //holder.iv_status.setImageResource(R.drawable.icon_share_yellow);
           /* ColorStateList csl = AppCompatResources.getColorStateList(mContext, R.color.orange);
            Drawable drawable = DrawableCompat.wrap( holder.iv_status.getDrawable());
            DrawableCompat.setTintList(drawable, csl);
            holder.iv_status.setImageDrawable(drawable);*/

            holder.tvt_status.setText(mContext.getString(R.string.str_sent));
            holder.tvt_status.setTextColor(ContextCompat.getColor(mContext,R.color.orange));

        }else if(mArrayList.get(position).getInvitationStatus().equalsIgnoreCase(AppUtils.Accepted)){
            //older.iv_status.setImageResource(R.drawable.icon_share_green);
          /*  ColorStateList csl = AppCompatResources.getColorStateList(mContext, R.color.green);
            Drawable drawable = DrawableCompat.wrap( holder.iv_status.getDrawable());
            DrawableCompat.setTintList(drawable, csl);
            holder.iv_status.setImageDrawable(drawable);*/

            holder.tvt_status.setText(mContext.getString(R.string.accepted));
            holder.tvt_status.setTextColor(ContextCompat.getColor(mContext,R.color.green));
        }else {
           // holder.iv_status.setImageResource(R.drawable.icon_share_red);
           /* ColorStateList csl = AppCompatResources.getColorStateList(mContext, R.color.red);
            Drawable drawable = DrawableCompat.wrap( holder.iv_status.getDrawable());
            DrawableCompat.setTintList(drawable, csl);
            holder.iv_status.setImageDrawable(drawable);*/

            holder.tvt_status.setText(mContext.getString(R.string.reject));
            holder.tvt_status.setTextColor(ContextCompat.getColor(mContext,R.color.red));
        }
    }



    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    @Override
    public SwipeConfiguration onCreateSwipeConfiguration(Context context, int i) {
        return new SwipeConfiguration.Builder(context)
                .setLeftBackgroundColorResource(R.color.colorPrimary)
                .setRightBackgroundColorResource(R.color.colorPrimary)
                //  .setDrawableResource(R.drawable.ic_delete_white_24dp)
                //  .setRightDrawableResource(R.drawable.ic_done_white_24dp)
                .setCallbackEnabled(true)
                .setLeftCallbackEnabled(true)
                .setLeftUndoable(false)
                .setLeftUndoDescription(R.string.action_undo)
                .setDescriptionTextColorResource(android.R.color.white)
                .setLeftSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.NORMAL_SWIPE)
                .setRightSwipeBehaviour(SwipeConfiguration.SwipeBehaviour.NO_SWIPE)
                .build();
    }

    @Override
    public void onSwipe(int position, int direction) {
        if (direction == SWIPE_LEFT) {
           // mDataset.remove(position);
           // notifyItemRemoved(position);
           onMemberClick.onDelete(position);
           /* Toast toast = Toast.makeText(mContext, "Deleted item at position " + position, Toast.LENGTH_SHORT);
            toast.show();*/
        } else {
            Toast toast = Toast.makeText(mContext, "Marked item as read at position " + position, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextViewTitle tvt_member,tvt_status;
        private TableRow tblRow;
        private CheckBox checkBox;
        private CircularImageView civ_member_image;
        private ImageView iv_status;
        public MyHolder(View itemView) {
            super(itemView);

            tvt_member = (TextViewTitle)itemView.findViewById(R.id.tvt_member);
            checkBox = (CheckBox)itemView.findViewById(R.id.checkbox);
            iv_status=(ImageView)itemView.findViewById(R.id.iv_status);
            tvt_status = (TextViewTitle)itemView.findViewById(R.id.tvt_status);
         /*   tblRow = (TableRow)itemView.findViewById(R.id.tblRow);

            tblRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tblRow.setSelected(true);
                }
            });*/
        }
    }


    public interface OnMemberClick {

         void onMemberClick(int position);
         void onDelete(int position);

    }


}
