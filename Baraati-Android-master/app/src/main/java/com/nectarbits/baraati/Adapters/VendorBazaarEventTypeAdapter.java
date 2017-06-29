package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SpinnerAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.nectarbits.baraati.Interface.OnSubCategorySelect;
import com.nectarbits.baraati.Interface.OnVendorBazaarEventSelect;
import com.nectarbits.baraati.Models.SubCategory.SubCaregoryDetail;
import com.nectarbits.baraati.Models.UserEvent.EventType;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewTitle;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nectarbits on 19/08/16.
 */
public class VendorBazaarEventTypeAdapter implements SpinnerAdapter {

    private Context mContext;
    private List<EventType> mArrayList;
    private OnVendorBazaarEventSelect onSubCategorySelect;
    LayoutInflater inflater;
    public VendorBazaarEventTypeAdapter(Context context, List<EventType> arrayList, OnVendorBazaarEventSelect onSubCategorySelect){
        mContext = context;
        mArrayList = arrayList;
        this.onSubCategorySelect=onSubCategorySelect;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

   /* @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_row,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
          holder.checkboxTextview.setText(mArrayList.get(position).getEventType());
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }*/

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }




    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(R.layout.spinner_row, parent, false);

        TextView tvCategory = (TextView) row.findViewById(R.id.tvCategory);

        tvCategory.setText(mArrayList.get(position).getEventType());

        return row;
    }

}
