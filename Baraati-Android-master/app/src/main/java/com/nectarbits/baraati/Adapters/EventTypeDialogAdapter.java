package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.nectarbits.baraati.Interface.OnEventTypeDialogClick;
import com.nectarbits.baraati.Models.EventType.EventType;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewTitle;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nectarbits on 19/08/16.
 */
public class EventTypeDialogAdapter extends RecyclerView.Adapter<EventTypeDialogAdapter.MyHolder> {

    private static final String TAG = EventTypeDialogAdapter.class.getSimpleName();
    private Context mContext;
    private List<EventType> mArrayList;
    private OnEventTypeDialogClick onSubCategorySelect;
    private int screenWidth;
    public EventTypeDialogAdapter(Context context, List<EventType> arrayList, OnEventTypeDialogClick onSubCategorySelect){
        mContext = context;
        mArrayList = arrayList;
        this.onSubCategorySelect=onSubCategorySelect;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_event_type_dailog,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, int position) {
        holder.checkboxTextview.setText(mArrayList.get(position).getEventType());
        holder.checkboxTextview.setTag(position);
        if(!TextUtils.isEmpty(mArrayList.get(position).getEventtypeicon())){
            Picasso.with(mContext)
                    .load(mArrayList.get(position).getEventtypeicon())
                    .into(holder.img);
        }

        holder.checkBox.setTag(position);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position=(int)buttonView.getTag();
                /*if(isChecked){*/
                    onSubCategorySelect.onEventTypeDialogSelectClick(position,isChecked);
                /*}else{
                    onSubCategorySelect.onEventTypeDialogSelectClick(position);
                }*/
            }
        });
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(/*screenWidth / 3*/ViewGroup.LayoutParams.MATCH_PARENT,/*screenWidth / 3*/ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.tblRow.setLayoutParams(layoutParams);
        if(mArrayList.get(position).getSelect()){
            holder.checkBox.setChecked(true);
        }else{
            holder.checkBox.setChecked(false);
        }

        if((position+1)%3!=0){
            Log.e(TAG, "onBindViewHolder: False "+((position+1)%3));
            holder.viewBorder.setVisibility(View.VISIBLE);
        }else{
            holder.viewBorder.setVisibility(View.GONE);
            Log.e(TAG, "onBindViewHolder: True "+((position+1)%3));
        }
        holder.tblRow.setTag(position);
        holder.tblRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=(int)view.getTag();
                if(mArrayList.get(position).getSelect()){
                    holder.checkBox.setChecked(false);

                }else{
                    holder.checkBox.setChecked(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextViewTitle checkboxTextview;

        private CheckBox checkBox;
        private CircularImageView img;
        private LinearLayout tblRow;
        private View viewBorder;
        public MyHolder(View itemView) {
            super(itemView);

            checkboxTextview = (TextViewTitle)itemView.findViewById(R.id.checkboxTextview);
            img = (CircularImageView)itemView.findViewById(R.id.img);
            checkBox = (CheckBox)itemView.findViewById(R.id.checkbox);
            tblRow = (LinearLayout)itemView.findViewById(R.id.tblRow);
            viewBorder = (View) itemView.findViewById(R.id.vleftDivider);
        }
    }
}
