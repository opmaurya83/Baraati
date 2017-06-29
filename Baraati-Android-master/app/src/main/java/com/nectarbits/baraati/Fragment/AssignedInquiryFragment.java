package com.nectarbits.baraati.Fragment;

import android.content.Context;
import android.content.DialogInterface;
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
import com.nectarbits.baraati.Interface.OnAssignedInquiryListener;
import com.nectarbits.baraati.Interface.OnInquiryListeners;
import com.nectarbits.baraati.Models.InquiryModel.List.AssignInqiry;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.generalHelper.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AssignedInquiryFragment extends Fragment implements OnAssignedInquiryListener {

    private static final String TAG = AssignedInquiryFragment.class.getSimpleName();
    @Bind(R.id.rc_inquiry)
    RecyclerView rc_inquiry;
    @Bind(R.id.empty_text)
    TextView empty_text;
    Context mContext;
    List<Object> list=new ArrayList<Object>();
    ProgressDialog progressDialog;


    InquiryListAdapter adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnInquiryListeners mListener;

    public AssignedInquiryFragment() {
        // Required empty public constructor
    }


    public static AssignedInquiryFragment newInstance(String param1, String param2) {
        AssignedInquiryFragment fragment = new AssignedInquiryFragment();
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
        View view=inflater.inflate(R.layout.fragment_assigned_inquiry, container, false);
        ButterKnife.bind(this,view);
        mContext=getActivity();
        empty_text.setText(getString(R.string.str_no_assigned_inquiry));
        setRecyclerView();

        return view;


    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAssignedInquairy();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnInquiryListeners) {
            mListener = (OnInquiryListeners) context;
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
    public void onAssignedInquiryListener(List<AssignInqiry> subCategories) {
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

    void setRecyclerView(){
        Log.e(TAG, "setRecyclerView: "+list.size());
        rc_inquiry.setLayoutManager(new LinearLayoutManager(mContext));
        rc_inquiry.setItemAnimator(new DefaultItemAnimator());
        adapter = new InquiryListAdapter(mContext,list, false, new InquiryListAdapter.OnInquiryClickListener() {
            @Override
            public void onMessageClick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(((AssignInqiry)list.get(position)).getMessage())
                        .setTitle(getString(R.string.inquiry_message))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //do things
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }

            @Override
            public void onInquiryClick(AssignInqiry myInqiry) {
               /* Intent i = new Intent(mContext, VenderDetailActivity.class);
                i.putExtra(AppUtils.ARG_VENDOR_ID,myInqiry.getVendorId());
                i.putExtra(AppUtils.ARG_FOR_USER_ID,"0");
                i.putExtra(AppUtils.ARG_USER_EVENTTYPE_EVENTID,mUserEventtypeEventID);
                startActivity(i);
*/
            }
        });
        rc_inquiry.setAdapter(adapter);
    }

}
