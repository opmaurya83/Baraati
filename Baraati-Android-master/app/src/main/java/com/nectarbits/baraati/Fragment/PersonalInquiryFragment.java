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
import com.nectarbits.baraati.Interface.OnInquiryListeners;
import com.nectarbits.baraati.Interface.OnPersonalInquiryLoadedListener;
import com.nectarbits.baraati.Models.InquiryModel.List.AssignInqiry;
import com.nectarbits.baraati.Models.InquiryModel.List.MyInqiry;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.generalHelper.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PersonalInquiryFragment extends Fragment implements OnPersonalInquiryLoadedListener {

    private static final String TAG = PersonalInquiryFragment.class.getSimpleName();
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

    public PersonalInquiryFragment() {
        // Required empty public constructor
    }


    public static PersonalInquiryFragment newInstance(String param1, String param2) {
        PersonalInquiryFragment fragment = new PersonalInquiryFragment();
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
        View view=inflater.inflate(R.layout.fragment_personal_inquiry, container, false);
        ButterKnife.bind(this,view);
        empty_text.setText(getString(R.string.str_no_inquiry));
        mContext=getActivity();
        setRecyclerView();

        return view;


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
    public void onPersonalInquiryLoadedListener(List<MyInqiry> subCategories) {
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
        adapter = new InquiryListAdapter(mContext,list, true, new InquiryListAdapter.OnInquiryClickListener() {
            @Override
            public void onMessageClick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(((MyInqiry)list.get(position)).getMessage())
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

            }
        });
        rc_inquiry.setAdapter(adapter);
    }
}
