package com.nectarbits.baraati.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nectarbits.baraati.Adapters.InquiryListAdapter;
import com.nectarbits.baraati.Adapters.OrderListAdapter;
import com.nectarbits.baraati.Interface.OnAssignedInquiryListener;
import com.nectarbits.baraati.Interface.OnAssignedOrderListener;
import com.nectarbits.baraati.Interface.OnInquiryListeners;
import com.nectarbits.baraati.Interface.OnOrderListeners;
import com.nectarbits.baraati.Interface.OnOrderProductClick;
import com.nectarbits.baraati.Models.InquiryModel.List.AssignInqiry;
import com.nectarbits.baraati.Models.OrderList.List.AssigneeOrder;
import com.nectarbits.baraati.Models.OrderList.List.OwnOrder;
import com.nectarbits.baraati.OrderDetailActivity;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AssignedOrderFragment extends Fragment implements OnAssignedOrderListener {

    private static final String TAG = AssignedOrderFragment.class.getSimpleName();
    @Bind(R.id.rc_inquiry)
    RecyclerView rc_inquiry;
    @Bind(R.id.empty_text)
    TextView empty_text;
    Context mContext;
    List<Object> list=new ArrayList<Object>();
    ProgressDialog progressDialog;


    OrderListAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnOrderListeners mListener;

    public AssignedOrderFragment() {
        // Required empty public constructor
    }


    public static AssignedOrderFragment newInstance(String param1, String param2) {
        AssignedOrderFragment fragment = new AssignedOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_assigned_orders, container, false);
        ButterKnife.bind(this,view);
        empty_text.setText(getString(R.string.str_no_assigned_order));
        mContext=getActivity();
        setRecyclerView();

        return view;


    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAssignedOrder();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOrderListeners) {
            mListener = (OnOrderListeners) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    void setRecyclerView(){
        Log.e(TAG, "setRecyclerView: "+list.size());
        rc_inquiry.setLayoutManager(new LinearLayoutManager(mContext));
        rc_inquiry.setItemAnimator(new DefaultItemAnimator());
        adapter = new OrderListAdapter(mContext, list, new OnOrderProductClick() {
            @Override
            public void onOrderProductClick(int position) {
                AssigneeOrder ownOrder=(AssigneeOrder)list.get(position);
                Intent intent=new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra(AppUtils.ARG_ORDER_ID,ownOrder.getOrderId());
                intent.putExtra(AppUtils.ARG_PRODUCT_ID,ownOrder.getProductId());
                startActivity(intent);
            }
        },false);
        rc_inquiry.setAdapter(adapter);
    }

    @Override
    public void onAssignedOrderListener(List<AssigneeOrder> subCategories) {
        list.clear();
        list.addAll(subCategories);
        if(list.size()==0){
            empty_text.setVisibility(View.VISIBLE);
            rc_inquiry.setVisibility(View.GONE);
        }else{
            empty_text.setVisibility(View.GONE);
            rc_inquiry.setVisibility(View.VISIBLE);
        }


        adapter.notifyDataSetChanged();
    }
}
