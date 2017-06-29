package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.nectarbits.baraati.Interface.OnCalenderEventSelect;
import com.nectarbits.baraati.Interface.OnSubCategorySelect;
import com.nectarbits.baraati.Models.SubCategory.SubCaregoryDetail;
import com.nectarbits.baraati.Models.UserEvent.EventType;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by nectarbits on 19/08/16.
 */
public class UserCalendarEventsAdapter extends RecyclerView.Adapter<UserCalendarEventsAdapter.MyHolder> {

    private Context mContext;
    private List<Event> mArrayList;
    private OnCalenderEventSelect onCalenderEventSelect;
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    public UserCalendarEventsAdapter(Context context, List<Event> arrayList,OnCalenderEventSelect onCalenderEventSelect){
        mContext = context;
        mArrayList = arrayList;
        this.onCalenderEventSelect=onCalenderEventSelect;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_calender_event_item,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        EventType eventType=(EventType)mArrayList.get(position).getData();
            holder.txtUserEvent.setText(eventType.getEventType());
            holder.txtDate.setText(fmt.format(new Date(mArrayList.get(position).getTimeInMillis())));
            holder.tableRow.setTag(position);
            holder.tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=(int)v.getTag();
                    onCalenderEventSelect.onCalenderEventSelect(position);
                }
            });

        if(eventType.getIsDone().equalsIgnoreCase("1")){
            holder.txt_completion_status.setText(String.format(mContext.getString(R.string.completed_by),eventType.getIsDoneName()));
        }else{
            holder.txt_completion_status.setText("");
        }


    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView txtUserEvent,txtDate,txt_completion_status;
        TableRow tableRow;
        public MyHolder(View itemView) {
            super(itemView);

            txtUserEvent = (TextViewTitle) itemView.findViewById(R.id.txtUserEvent);
            txtDate = (TextViewDescription) itemView.findViewById(R.id.txtDate);
            txt_completion_status = (TextViewDescription) itemView.findViewById(R.id.txt_completion_status);
            tableRow = (TableRow) itemView.findViewById(R.id.tblRow);


        }
    }
}
