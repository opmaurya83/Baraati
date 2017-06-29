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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nectarbits.baraati.Adapters.AssignedDashboardAdapter;
import com.nectarbits.baraati.EventActivty;
import com.nectarbits.baraati.EventDateResponsibilityActivity;
import com.nectarbits.baraati.EventTypeActivity;
import com.nectarbits.baraati.Interface.OnAlertDialogClicked;
import com.nectarbits.baraati.Interface.OnAssignedCheckListLoadedListener;
import com.nectarbits.baraati.Interface.OnDashBoardListeners;
import com.nectarbits.baraati.Interface.OnUserCheckListSelect;
import com.nectarbits.baraati.Models.DeleteUserEvent.DeleteUserEventModel;
import com.nectarbits.baraati.Models.UserEvent.Complete.CompleteEventModel;
import com.nectarbits.baraati.Models.UserEvent.Event;
import com.nectarbits.baraati.Models.UserEvent.EventType;
import com.nectarbits.baraati.Models.UserEvent.SubCategory_;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.Singletone.AddCheckListSigletone;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.VendorBazaarActivity;
import com.nectarbits.baraati.generalHelper.AlertsUtils;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignedCheckListFragment extends Fragment implements OnAssignedCheckListLoadedListener {

    private static final String TAG = AssignedCheckListFragment.class.getSimpleName();
    @Bind(R.id.recyclerViewMainCategory)
    RecyclerView recyclerView;
    @Bind(R.id.empty_text)
    TextView empty_text;
    Context mContext;
    List<SubCategory_> list=new ArrayList<>();
    AssignedDashboardAdapter adapter;
    ProgressDialog progressDialog;
    Boolean isUpdate=false;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnDashBoardListeners mListener;

    public AssignedCheckListFragment() {
        // Required empty public constructor
    }


    public static AssignedCheckListFragment newInstance(String param1, String param2) {
        AssignedCheckListFragment fragment = new AssignedCheckListFragment();
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
        View view=inflater.inflate(R.layout.fragment_assigned_check_list, container, false);
        ButterKnife.bind(this,view);
        mContext=getActivity();
        progressDialog = new ProgressDialog(mContext);
        empty_text.setText(getString(R.string.str_no_assigned_checklsit));
        setRecyclerView();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAssignedCheckList();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDashBoardListeners) {
            mListener = (OnDashBoardListeners) context;
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
    public void onAssignedCheckListLoaed(List<SubCategory_> subCategories) {
        list.clear();
        list.addAll(subCategories);
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getEvents().size(); j++) {
                for (int k = 0; k < list.get(i).getEvents().get(j).getEventType().size(); k++) {
                    list.get(i).getEvents().get(j).getEventType().get(k).setAdminuserId(list.get(i).getAdminuserId());
                }
            }
        }


        if(list.size()==0){
            empty_text.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            empty_text.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }


        adapter.notifyDataSetChanged();
    }

    void setRecyclerView(){
        Log.e(TAG, "setRecyclerView: "+list.size());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new AssignedDashboardAdapter(mContext, list, new OnUserCheckListSelect() {
            @Override
            public void onEventTypeSelect(int position) {

            }

            @Override
            public void OnUserSubcategoryAddSelect(int potition) {
                AddCheckListSigletone.getInstance().reset();
                AddCheckListSigletone.getInstance().setCategoryID(list.get(potition).getCategoryId());
                AddCheckListSigletone.getInstance().setSubcategoryID(list.get(potition).getSubcategoryId());
                List<String> selected_events = new ArrayList<String>();
                for (int i = 0; i < list.get(potition).getEvents().size(); i++) {
                    selected_events.add(list.get(potition).getEvents().get(i).getEventId());
                }
                AddCheckListSigletone.getInstance().setSelected_event(selected_events);
                Intent intent = new Intent(mContext, EventActivty.class);
                intent.putExtra(AppUtils.ARG_CATEGORY_ID, list.get(potition).getCategoryId());
                startActivityForResult(intent, AppUtils.REQUEST_UPDATE_USEREVENT);
            }

            @Override
            public void OnUserSubcategoryDeleteSelect(final int potition) {
                AlertsUtils.getInstance().showYesNoAlert(mContext, String.format(getString(R.string.str_are_you_sure_you_want_to_remove), list.get(potition).getSubcategory(), list.get(potition).getSubcategory()), R.string.str_yes_caps, R.string.str_no_caps, new OnAlertDialogClicked() {
                    @Override
                    public void onPositiveClicked() {
                        if (GH.isInternetOn(mContext))
                          DeleteUserEvents(list.get(potition).getUsersubCategoryId(), "0", "0");
                    }

                    @Override
                    public void onNagativeClicked() {

                    }
                });

            }

            @Override
            public void OnUserEventDeleteSelect(final Event event) {
                AlertsUtils.getInstance().showYesNoAlert(mContext, String.format(getString(R.string.str_are_you_sure_you_want_to_remove), event.getEvent(),event.getEvent()), R.string.str_yes_caps, R.string.str_no_caps, new OnAlertDialogClicked() {
                    @Override
                    public void onPositiveClicked() {
                        if (GH.isInternetOn(mContext))
                          DeleteUserEvents("0", event.getUserEventId(), "0");
                    }

                    @Override
                    public void onNagativeClicked() {

                    }
                });

            }

            @Override
            public void OnUserEventAddSelect(Event event) {
                AddCheckListSigletone.getInstance().reset();
                List<String> selected_events = new ArrayList<String>();
                for (int i = 0; i < event.getEventType().size(); i++) {
                    selected_events.add(event.getEventType().get(i).getEventTypeEventId());
                }
                Log.e(TAG, "onResponse: Selected eventtypes::" + TextUtils.join(",", selected_events));

                AddCheckListSigletone.getInstance().setSelected_eventTypes(selected_events);


                AddCheckListSigletone.getInstance().setCategoryID(event.getCategoryID());
                AddCheckListSigletone.getInstance().setSubcategoryID(event.getSub_categoryID());
                AddCheckListSigletone.getInstance().setEventsID(event.getEventCategoryId());
                Intent intent = new Intent(mContext, EventTypeActivity.class);
                startActivityForResult(intent, AppUtils.REQUEST_UPDATE_USEREVENT);
            }

            @Override
            public void OnUserEventTypeDeleteSelect(final EventType eventType) {

                AlertsUtils.getInstance().showYesNoAlert(mContext, String.format(getString(R.string.str_are_you_sure_you_want_to_remove), eventType.getEventType(), eventType.getEventType()), R.string.str_yes_caps, R.string.str_no_caps, new OnAlertDialogClicked() {
                    @Override
                    public void onPositiveClicked() {
                        if (GH.isInternetOn(mContext))
                         DeleteUserEvents("0", "0", eventType.getUserEventTypeEventsId());
                    }

                    @Override
                    public void onNagativeClicked() {

                    }
                });
            }

            @Override
            public void OnUserEventTypeCalenderSelect(EventType eventType) {
                String categoryID="",subcategory="";
                for (int i = 0; i <list.size(); i++) {
                    for (int j = 0; j < list.get(i).getEvents().size(); j++) {
                        for (int k = 0; k < list.get(i).getEvents().get(j).getEventType().size(); k++) {
                            if(eventType.getUserEventTypeEventsId().equalsIgnoreCase(list.get(i).getEvents().get(j).getEventType().get(k).getUserEventTypeEventsId())){
                                categoryID=list.get(i).getCategoryId();
                                subcategory=list.get(i).getSubcategoryId();
                            }
                        }
                    }
                }

                if(!eventType.getStartDate().toString().equalsIgnoreCase("0000-00-00") && !eventType.getEndDate().toString().equalsIgnoreCase("0000-00-00"))
                {
                    Intent intent = new Intent(mContext, EventDateResponsibilityActivity.class);
                    intent.putExtra(AppUtils.ARG_EVENTTYPE_ID, eventType.getUserEventTypeEventsId());
                    intent.putExtra(AppUtils.ARG_EVENTTYPE_NAME, eventType.getEventType());
                    intent.putExtra(AppUtils.ARG_DATE_FROM, eventType.getStartDate());
                    intent.putExtra(AppUtils.ARG_DATE_TO, eventType.getEndDate());
                    /*intent.putExtra(AppUtils.ARG_CATEGORY_ID, categoryID);*/
                    if(eventType.getIsDone().equalsIgnoreCase("0")) {
                        intent.putExtra(AppUtils.ARG_EVENT_STATUS, AppUtils.Incomplete);
                    }else{
                        intent.putExtra(AppUtils.ARG_EVENT_STATUS, AppUtils.Completed);
                    }
                    intent.putExtra(AppUtils.ARG_NOTIFICATION_TYPE, "123");

                    intent.putExtra(AppUtils.ARG_FOR_USER_ID,eventType.getAdminuserId());
                    /*intent.putExtra(AppUtils.ARG_SUBCATEGORY_ID,subcategory);*/

                    startActivityForResult(intent, AppUtils.REQUEST_UPDATE_USEREVENT);
                }else{
                    Intent intent = new Intent(mContext, EventDateResponsibilityActivity.class);
                    intent.putExtra(AppUtils.ARG_EVENTTYPE_ID, eventType.getUserEventTypeEventsId());
                    intent.putExtra(AppUtils.ARG_EVENTTYPE_NAME, eventType.getEventType());
                    intent.putExtra(AppUtils.ARG_DATE_FROM, eventType.getStartDate());
                    intent.putExtra(AppUtils.ARG_DATE_TO, eventType.getEndDate());
                   /* intent.putExtra(AppUtils.ARG_CATEGORY_ID, categoryID);
                    intent.putExtra(AppUtils.ARG_SUBCATEGORY_ID,subcategory);*/
                    if(eventType.getIsDone().equalsIgnoreCase("0")) {
                        intent.putExtra(AppUtils.ARG_EVENT_STATUS, AppUtils.Incomplete);
                    }else{
                        intent.putExtra(AppUtils.ARG_EVENT_STATUS, AppUtils.Completed);
                    }
                    intent.putExtra(AppUtils.ARG_NOTIFICATION_TYPE, "123");
                    intent.putExtra(AppUtils.ARG_FOR_USER_ID,eventType.getAdminuserId());
                    startActivityForResult(intent, AppUtils.REQUEST_UPDATE_USEREVENT);
                }

              /*  Intent intent = new Intent(mContext, EventDateResponsibilityActivity.class);
                intent.putExtra(AppUtils.ARG_EVENTTYPE_ID, detail.getUserEventTypeEventsId());
                intent.putExtra(AppUtils.ARG_EVENTTYPE_NAME, detail.getEventName());
                intent.putExtra(AppUtils.ARG_DATE_FROM, detail.getStartDate());
                intent.putExtra(AppUtils.ARG_DATE_TO, detail.getEndDate());
                intent.putExtra(AppUtils.ARG_FOR_USER_ID, detail.getFromUserId());
                intent.putExtra(AppUtils.ARG_NOTIFICATION_TYPE, detail.getType());
                intent.putExtra(AppUtils.ARG_EVENT_STATUS, detail.getEventStatus());
                intent.putExtra(AppUtils.ARG_NOTIFICATION_DATE, detail.getNotificationDate());
                startActivityForResult(intent, AppUtils.REQUEST_UPDATE_USEREVENT);*/

            }

            @Override
            public void OnUserEventTypeCartSelect(EventType eventType) {


                Intent intent = new Intent(mContext, VendorBazaarActivity.class);
                intent.putExtra(AppUtils.ARG_EVENTTYPE_ID, eventType.getEventTypeId());
                intent.putExtra(AppUtils.ARG_EVENTTYPE_NAME, eventType.getEventType());
                intent.putExtra(AppUtils.ARG_FOR_USER_ID,eventType.getAdminuserId());
                intent.putExtra(AppUtils.ARG_IS_ASSIGNED,true);
                intent.putExtra(AppUtils.ARG_USER_EVENTTYPE_EVENTID, eventType.getUserEventTypeEventsId());
                startActivity(intent);
            }

            @Override
            public void onCompleteEvent(Boolean aBoolean, final EventType eventType) {
                if(!aBoolean){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage(String.format(getString(R.string.do_you_want_to_complete_event),eventType.getEventType()))
                            .setTitle(getString(R.string.complete_event))
                            .setCancelable(false)
                            .setNegativeButton(getString(R.string.str_no_caps), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton(getString(R.string.str_yes_caps), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (GH.isInternetOn(mContext))
                                    completeEvent(eventType.getUserEventTypeEventsId());
                                    dialog.dismiss();

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage(String.format(getString(R.string.already_completed_event),eventType.getEventType()))
                            .setTitle(getString(R.string.complete_event))
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void DeleteUserEvents(String usersubCategoryId, String userEventId, String userEventTypeEventsId) {
        if (!progressDialog.isShowing())
            progressDialog.show();
        JSONObject jsonObject = RequestBodyBuilder.getInstanse().getRequestDeleteUserEventResponse(UserDetails.getInstance(mContext).getUser_id(), usersubCategoryId, userEventId, userEventTypeEventsId);
        Log.e(TAG, "DeleteUserEvents: " + jsonObject.toString());
        Call<DeleteUserEventModel> call = RetrofitBuilder.getInstance().getRetrofit().DeleteUserEvent(jsonObject);
        call.enqueue(new Callback<DeleteUserEventModel>() {
            @Override
            public void onResponse(Call<DeleteUserEventModel> call, Response<DeleteUserEventModel> response) {
                DeleteUserEventModel mainCategoryModel = response.body();
                if (mainCategoryModel.getStatus().equalsIgnoreCase(AppUtils.Success)) {
                    isUpdate=true;
                    mListener.onRefreshCheckList();
                } else {

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<DeleteUserEventModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }


    /**
     * Complete User Event
     * @param userEventTypeEventsId
     */
    private void completeEvent(String userEventTypeEventsId) {
        if (!progressDialog.isShowing())
            progressDialog.show();
        JSONObject jsonObject = RequestBodyBuilder.getInstanse().Request_CompleteEvent(UserDetails.getInstance(mContext).getUser_id(),userEventTypeEventsId);

        Call<CompleteEventModel> call = RetrofitBuilder.getInstance().getRetrofit().CompletEvent(jsonObject);
        call.enqueue(new Callback<CompleteEventModel>() {
            @Override
            public void onResponse(Call<CompleteEventModel> call, Response<CompleteEventModel> response) {
                CompleteEventModel mainCategoryModel = response.body();
                if (mainCategoryModel.getStatus().equalsIgnoreCase(AppUtils.Success)) {
                    mListener.onRefreshCheckList();
                } else {

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<CompleteEventModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }
}
