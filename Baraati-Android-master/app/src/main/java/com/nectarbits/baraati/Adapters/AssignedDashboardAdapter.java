package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.nectarbits.baraati.Interface.OnUserCheckListSelect;
import com.nectarbits.baraati.Models.UserEvent.SubCategory;
import com.nectarbits.baraati.Models.UserEvent.SubCategory_;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 * Created by nectarbits on 19/08/16.
 */
public class AssignedDashboardAdapter extends RecyclerView.Adapter<AssignedDashboardAdapter.MyHolder> {

    private Context mContext;
    private List<SubCategory_> mArrayList;
    private OnUserCheckListSelect onUserCheckListSelect;
    AssignedDashboardEventTypeAdapter dashboardEventTypeAdapter;

    public AssignedDashboardAdapter(Context context, List<SubCategory_> arrayList, OnUserCheckListSelect onSubCategorySelect){
        mContext = context;
        mArrayList = arrayList;
        this.onUserCheckListSelect =onSubCategorySelect;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_category_subcategory_item,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
      holder.txtCategory.setText(mArrayList.get(position).getCategory());
      holder.txtSubcategory.setText(mArrayList.get(position).getSubcategory());
        dashboardEventTypeAdapter=new AssignedDashboardEventTypeAdapter(mContext,mArrayList.get(position).getEvents(),onUserCheckListSelect);
        holder.rcEvent_EventTypes.setLayoutManager(new LinearLayoutManager(mContext));
        holder.rcEvent_EventTypes.setItemAnimator(new DefaultItemAnimator());
        holder.rcEvent_EventTypes.setAdapter(dashboardEventTypeAdapter);
        holder.btnDelete.setTag(position);
        holder.btnDelete.setVisibility(View.GONE);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
            onUserCheckListSelect.OnUserSubcategoryDeleteSelect(position);
            }
        });

        holder.btnPlus.setTag(position);
        holder.btnPlus.setVisibility(View.GONE);
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                onUserCheckListSelect.OnUserSubcategoryAddSelect(position);
            }
        });
        if(!TextUtils.isEmpty(mArrayList.get(position).getSubcategoryicon())){
            Picasso.with(mContext)
                    .load(mArrayList.get(position).getSubcategoryicon())
                    .into(holder.img);
        }
        //dashboardEventTypeAdapter=new DashboardEventTypeAdapter()
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.txtCategory)
        TextViewDescription txtCategory;
        @Bind(R.id.txtSubcategory)
        TextViewTitle txtSubcategory;
        @Bind(R.id.btnPlus)
        ImageView btnPlus;
        @Bind(R.id.btnDelete)
        ImageView btnDelete;
        @Bind(R.id.rcEvent_EventTypes)
        RecyclerView rcEvent_EventTypes;
        @Bind(R.id.img)
        CircularImageView img;
        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
