package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableRow;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.nectarbits.baraati.Interface.OnSubCategorySelect;
import com.nectarbits.baraati.Models.SubCategory.SubCaregoryDetail;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nectarbits on 19/08/16.
 */
public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyHolder> {

    private Context mContext;
    private List<SubCaregoryDetail> mArrayList;
    private OnSubCategorySelect onSubCategorySelect;

    public SubCategoryAdapter(Context context,List<SubCaregoryDetail> arrayList, OnSubCategorySelect onSubCategorySelect){
        mContext = context;
        mArrayList = arrayList;
        this.onSubCategorySelect=onSubCategorySelect;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sub_category_list_item,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
            holder.checkboxTextview.setText(mArrayList.get(position).getSubcategoryName());
        holder.checkboxTextview.setTag(position);
        holder.textViewDescription.setText(mArrayList.get(position).getSubcategorylabel());
        if(!TextUtils.isEmpty(mArrayList.get(position).getSubcategoryicon())){
            Picasso.with(mContext)
                    .load(mArrayList.get(position).getSubcategoryicon())
                    .into(holder.imgSubcategory);
        }
      /*  holder.checkboxTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();

                if(((CheckedTextView) v).isSelected()){
                    ((CheckedTextView) v).setSelected(false);
                    onSubCategorySelect.OnSubCategoryChecked(position,false);
                }else{
                    ((CheckedTextView) v).setSelected(true);
                    onSubCategorySelect.OnSubCategoryChecked(position,true);
                }

            }
        });*/
        holder.checkBox.setTag(position);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position=(int)buttonView.getTag();
                if(isChecked){
                    onSubCategorySelect.OnSubCategoryChecked(position,true);
                }else{
                    onSubCategorySelect.OnSubCategoryChecked(position,false);
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
        private TableRow tblRow;
        private CheckBox checkBox;
        private TextViewDescription textViewDescription;
        private CircularImageView imgSubcategory;

        public MyHolder(View itemView) {
            super(itemView);

            checkboxTextview = (TextViewTitle)itemView.findViewById(R.id.checkboxTextview);
            textViewDescription = (TextViewDescription)itemView.findViewById(R.id.txtDesc);
            imgSubcategory = (CircularImageView)itemView.findViewById(R.id.imgSubcategory);
            checkBox = (CheckBox)itemView.findViewById(R.id.checkbox);
         /*   tblRow = (TableRow)itemView.findViewById(R.id.tblRow);

            tblRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tblRow.setSelected(true);
                }
            });*/
        }
    }
}
