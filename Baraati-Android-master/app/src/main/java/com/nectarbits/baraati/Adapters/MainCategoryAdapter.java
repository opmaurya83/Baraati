package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.nectarbits.baraati.Models.MainCategory.CaregoryDetail;
import com.nectarbits.baraati.Interface.OnMainCategorySelect;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nectarbits on 19/08/16.
 */
public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.MyHolder> {

    private Context mContext;
    private List<CaregoryDetail> mArrayList;
    private OnMainCategorySelect onMainCategorySelect;
    public MainCategoryAdapter(Context context, List<CaregoryDetail> arrayList, OnMainCategorySelect onMainCategorySelect){
        mContext = context;
        mArrayList = arrayList;
        this.onMainCategorySelect=onMainCategorySelect;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.category_list_item,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
            holder.textViewMainCategory.setText(mArrayList.get(position).getCategoryName());
            holder.textViewDescription.setText(mArrayList.get(position).getCategorylabel());
            if(!TextUtils.isEmpty(mArrayList.get(position).getCategoryIcon())){
                Picasso.with(mContext)
                        .load(mArrayList.get(position).getCategoryIcon())
                        .into(holder.imgCategory);
            }
            holder.tblRow.setTag(position);
            holder.tblRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // tblRow.setSelected(true);
                onMainCategorySelect.OnMainCategorySelected((int)v.getTag());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        private TextViewTitle textViewMainCategory;
        private TextViewDescription textViewDescription;
        private TableRow tblRow;
        private CircularImageView imgCategory;

        public MyHolder(View itemView) {
            super(itemView);

            textViewMainCategory = (TextViewTitle)itemView.findViewById(R.id.textViewMainCategory);
            textViewDescription = (TextViewDescription)itemView.findViewById(R.id.txtDesc);
            imgCategory = (CircularImageView)itemView.findViewById(R.id.imgCategory);
            tblRow = (TableRow)itemView.findViewById(R.id.tblRow);


        }
    }
}
