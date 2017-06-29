package com.nectarbits.baraati.Adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.nectarbits.baraati.Interface.OnAddressClick;
import com.nectarbits.baraati.Interface.OnCartClick;
import com.nectarbits.baraati.Models.Address.ShippingAddress;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nectarbits on 23/08/16.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyHolder> {

    private Context mContext;
    private List<ShippingAddress> cartlistdata;
    private OnAddressClick onAddressClick;

    public AddressAdapter(Context context, List<ShippingAddress> arrayVendorLists , OnAddressClick onCartClick){
        mContext = context;
        cartlistdata = arrayVendorLists;
        onAddressClick = onCartClick;
    }
    @Override
    public AddressAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_address,parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressAdapter.MyHolder holder, int position) {


        holder.ttvname.setText(cartlistdata.get(position).getName());
        holder.ttvAddress.setText(cartlistdata.get(position).getAddressline1()+cartlistdata.get(position).getAddressline2());
        holder.ttCity.setText(""+cartlistdata.get(position).getCity());


        holder.ttState.setText(cartlistdata.get(position).getState());
        holder.ttPhone.setText(cartlistdata.get(position).getPhone());
        holder.ttPin.setText(cartlistdata.get(position).getZipcode());
        if(cartlistdata.get(position).getSelected()){
            holder.ivSelect.setSelected(true);
        }else{
            holder.ivSelect.setSelected(false);
        }

        holder.ttvChangeAddress.setTag(position);
        if(cartlistdata.get(position).getChangeable()){
            holder.ttvChangeAddress.setVisibility(View.VISIBLE);
        }else {
            holder.ttvChangeAddress.setVisibility(View.GONE);
        }
        holder.ttvChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                onAddressClick.onAddressChangeClick(position);
            }
        });
        holder.ivSelect.setTag(position);
        holder.ivSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int position=(int)buttonView.getTag();
              /*  if(isChecked){*/
                    onAddressClick.onAddressSelectClick(position,isChecked);
                /*}*/
            }
        });

        if(cartlistdata.get(position).getSelected()){
            holder.ivSelect.setChecked(true);
        }else {
            holder.ivSelect.setChecked(false);
        }
      /*  holder.ivDelete.setTag(position);
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  int position=(int)v.getTag();
                onAddressClick.onDeleteClick(position);
            }
        });
        holder.ttvPlus.setTag(position);
        holder.ttvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                onAddressClick.onPlusClick(position);
            }
        });

        holder.ttvminus.setTag(position);
        holder.ttvminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();

                    onAddressClick.onMinusClick(position);

            }
        });*/

      /*  holder.llProduct.setTag(position);
        holder.llProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                onAddressClick.onAddressSelectClick(position);
            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return cartlistdata.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.llProduct)
        LinearLayout llProduct;
        @Bind(R.id.ttvname)
        TextViewTitle ttvname;
        @Bind(R.id.ttvAddress)
        TextViewDescription ttvAddress;
        @Bind(R.id.ttCity)
        TextViewDescription ttCity;
        @Bind(R.id.ttState)
        TextViewDescription ttState;
        @Bind(R.id.ttPin)
        TextViewDescription ttPin;
        @Bind(R.id.ttPhone)
        TextViewDescription ttPhone;
        @Bind(R.id.ttvChangeAddress)
        TextViewTitle ttvChangeAddress;
        @Nullable
        @Bind(R.id.ivSelect)
        CheckBox ivSelect;


        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }

    }


    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }
}
