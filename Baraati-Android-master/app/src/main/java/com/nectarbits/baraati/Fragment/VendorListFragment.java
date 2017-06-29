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

import com.nectarbits.baraati.Adapters.VendorListAdapter;
import com.nectarbits.baraati.Interface.OnVendorDataLoaded;
import com.nectarbits.baraati.Interface.OnVendorFragmentClicked;
import com.nectarbits.baraati.Interface.OnVendorSelect;
import com.nectarbits.baraati.Models.VendorBazaar.VendorBazaarModel;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.VenderDetailActivity;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.orhanobut.hawk.Hawk;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link VendorListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VendorListFragment extends Fragment implements OnVendorDataLoaded {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_USER_EVENTTYPE_EVENTID = "ARG_USER_EVENTTYPE_EVENTID";
    private static final String TAG = VendorListFragment.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mFor_USER_ID,mUserEventtypeEventID;

    private OnVendorFragmentClicked mListener;
    @Bind(R.id.rcVendorlist)
    RecyclerView rcVendorlist;
    @Bind(R.id.empty_text)
    TextViewTitle empty_text;
    VendorListAdapter vendorListAdapter;
    Context mContext;

    public VendorListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VendorListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VendorListFragment newInstance(String param1, String param2,String user_eventtype_event_id) {
        VendorListFragment fragment = new VendorListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_USER_EVENTTYPE_EVENTID, user_eventtype_event_id);
        fragment.setArguments(args);
        return fragment;
    }

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
        mContext=getActivity();
        View view=inflater.inflate(R.layout.fragment_vendor_list2, container, false);
        ButterKnife.bind(this,view);
        if(Hawk.contains(AppUtils.OFFLINE_VENDORPRODUCT_LIST+mParam1)){
            setData((VendorBazaarModel) Hawk.get(AppUtils.OFFLINE_VENDORPRODUCT_LIST+mParam1));
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.OnVendorClicked();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnVendorFragmentClicked) {
            mListener = (OnVendorFragmentClicked) context;
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
      setData(vendorList);
    }

    private void setData(final VendorBazaarModel vendorList) {
        empty_text.setText(getString(R.string.str_no_vendor_found));
        empty_text.setVisibility(View.GONE);
        rcVendorlist.setVisibility(View.VISIBLE);
        if (vendorList.getStatus().equalsIgnoreCase(AppUtils.Success)) {
            Log.e(TAG, "OnDataLoaded: "+vendorList.getVendorDetail().size());
            vendorListAdapter = new VendorListAdapter(mContext, vendorList.getVendorDetail(), new OnVendorSelect() {
                @Override
                public void onVendorSelectFromList(int position, String value) {

                    Intent i = new Intent(mContext, VenderDetailActivity.class);
                    i.putExtra(AppUtils.ARG_VENDOR_ID,vendorList.getVendorDetail().get(position).getVendorId());
                    i.putExtra(AppUtils.ARG_FOR_USER_ID,mFor_USER_ID);
                    i.putExtra(AppUtils.ARG_USER_EVENTTYPE_EVENTID,mUserEventtypeEventID);

                    i.putExtra(AppUtils.ARG_VENDRO_COMPANY_NAME,vendorList.getVendorDetail().get(position).getCompany_Name());
                    i.putExtra(AppUtils.ARG_VENDRO_SPECIALITY,vendorList.getVendorDetail().get(position).getSpecialist());

                    startActivity(i);

                }

            });
            rcVendorlist.setLayoutManager(new LinearLayoutManager(mContext));
            rcVendorlist.setItemAnimator(new DefaultItemAnimator());
            rcVendorlist.setAdapter(vendorListAdapter);
            if(vendorList.getVendorDetail().size()==0)
            {
                empty_text.setVisibility(View.VISIBLE);
                rcVendorlist.setVisibility(View.GONE);
            }
        }
        else{
            empty_text.setVisibility(View.VISIBLE);
            rcVendorlist.setVisibility(View.GONE);
        }
    }
}
