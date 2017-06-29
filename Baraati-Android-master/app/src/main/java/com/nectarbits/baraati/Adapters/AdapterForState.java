package com.nectarbits.baraati.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nectarbits.baraati.Interface.OnStateCitySelect;
import com.nectarbits.baraati.Models.CountryModel.StateInfo;
import com.nectarbits.baraati.R;

import java.util.ArrayList;



/**
 * Created by nectarbits-06 on 16/4/16.
 */
public class AdapterForState extends RecyclerView.Adapter<AdapterForState.MyViewHolder> {

    private ArrayList<StateInfo> arrayListCountryInfo;
    OnStateCitySelect onStateCitySelect;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.countryName);

        }
    }

    public AdapterForState(ArrayList<StateInfo> countryInfos, OnStateCitySelect onStateCitySelect){
        this.arrayListCountryInfo = countryInfos;
        this.onStateCitySelect=onStateCitySelect;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        StateInfo countryInfo = arrayListCountryInfo.get(position);
        holder.title.setText(countryInfo.getName());
        holder.title.setTag(position);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                onStateCitySelect.onselected(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayListCountryInfo.size();
    }


}
