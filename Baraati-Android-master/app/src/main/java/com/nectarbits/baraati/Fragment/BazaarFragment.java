package com.nectarbits.baraati.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nectarbits.baraati.Adapters.ProductListAdapter;
import com.nectarbits.baraati.Interface.OnBazaarDataLoaded;
import com.nectarbits.baraati.Interface.OnBazaarFragmentClicked;
import com.nectarbits.baraati.Interface.OnProductSelect;
import com.nectarbits.baraati.Models.VendorBazaar.VendorBazaarModel;
import com.nectarbits.baraati.ProductDetailActivity;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.orhanobut.hawk.Hawk;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link BazaarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BazaarFragment extends Fragment implements OnBazaarDataLoaded {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_USER_EVENTTYPE_EVENTID = "ARG_USER_EVENTTYPE_EVENTID";
    private static final String TAG = BazaarFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mFor_USER_ID,mUserEventtypeEventID;

    @Bind(R.id.rcProductList)
    RecyclerView rcProductList;
    @Bind(R.id.empty_text)
    TextViewTitle empty_text;
    private OnBazaarFragmentClicked mListener;
    ProductListAdapter productListAdapter;
    Context mContext;
    public BazaarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BazaarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BazaarFragment newInstance(String param1, String param2,String user_eventtype_event_id) {
        BazaarFragment fragment = new BazaarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_USER_EVENTTYPE_EVENTID, user_eventtype_event_id);
        fragment.setArguments(args);
        return fragment;
    }
;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mFor_USER_ID = getArguments().getString(ARG_PARAM2);
            mUserEventtypeEventID= getArguments().getString(ARG_USER_EVENTTYPE_EVENTID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_bazaar, container, false);
        ButterKnife.bind(this,view);
        if(Hawk.contains(AppUtils.OFFLINE_VENDORPRODUCT_LIST+mParam1)){
            setData((VendorBazaarModel) Hawk.get(AppUtils.OFFLINE_VENDORPRODUCT_LIST+mParam1));

        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.OnBazaarClicked();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBazaarFragmentClicked) {
            mListener = (OnBazaarFragmentClicked) context;
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


    @Override
    public void OnDataLoaded(final VendorBazaarModel vendorList) {
        Log.e(TAG, "OnDataLoaded: ");
       setData(vendorList);
    }

    private void setData(final VendorBazaarModel vendorList){
        empty_text.setText(getString(R.string.str_no_product_found));
        rcProductList.setVisibility(View.VISIBLE);
        empty_text.setVisibility(View.GONE);
        if (vendorList.getStatus().equalsIgnoreCase(AppUtils.Success)) {
            Log.e(TAG, "OnDataLoaded: "+vendorList.getVendorDetail().size());

            productListAdapter = new ProductListAdapter(getActivity(), vendorList.getProductDetail(), new OnProductSelect() {
                @Override
                public void onProductSelect(int position) {
                    Intent i = new Intent(getActivity(), ProductDetailActivity.class);
                    i.putExtra(AppUtils.ARG_PRODUCT_ID,vendorList.getProductDetail().get(position).getProductId());
                    i.putExtra(AppUtils.ARG_PRODUCT_NAME,vendorList.getProductDetail().get(position).getProduct());
                    i.putExtra(AppUtils.ARG_FOR_USER_ID,mFor_USER_ID);
                    i.putExtra(AppUtils.ARG_USER_EVENTTYPE_EVENTID,mUserEventtypeEventID);
                    startActivity(i);
                }
            });
            rcProductList.setLayoutManager(new LinearLayoutManager(getActivity()));
            rcProductList.setItemAnimator(new DefaultItemAnimator());
            rcProductList.setAdapter(productListAdapter);
            if(vendorList.getProductDetail().size()==0){
                rcProductList.setVisibility(View.GONE);
                empty_text.setVisibility(View.VISIBLE);
            }
        }else {
            rcProductList.setVisibility(View.GONE);
            empty_text.setVisibility(View.VISIBLE);
        }
    }

}
