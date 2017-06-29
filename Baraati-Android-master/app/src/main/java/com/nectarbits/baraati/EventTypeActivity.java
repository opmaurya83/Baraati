package com.nectarbits.baraati;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.BoolRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.internal.zzng;
import com.nectarbits.baraati.Adapters.EventTypeAdapter;
import com.nectarbits.baraati.Adapters.EventTypeDialogAdapter;
import com.nectarbits.baraati.Interface.OnEventTypeDialogClick;
import com.nectarbits.baraati.Interface.OnEventTypeSelect;
import com.nectarbits.baraati.Models.AddCheckList.AddCheckListModel;
import com.nectarbits.baraati.Models.EventType.EventList;
import com.nectarbits.baraati.Models.EventType.EventTypeModel;
import com.nectarbits.baraati.Models.EventType.EventType;
import com.nectarbits.baraati.Singletone.AddCheckListSigletone;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.GridSpacingItemDecoration;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.orhanobut.hawk.Hawk;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventTypeActivity extends AppCompatActivity {
    private static final String TAG = EventTypeActivity.class.getSimpleName();
    private Context mContext;
    @Bind(R.id.recyclerViewSubCategory)
    RecyclerView recyclerViewSubCategory;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    ProgressDialog progressDialog;
    EventTypeAdapter adapter;
    EventTypeModel eventTypeModel;
    List<EventType> selected_subcat = new ArrayList<EventType>();
    String selected_ids;
    List<EventList> list=new ArrayList<EventList>();
    List<EventList> list_temp=new ArrayList<EventList>();
    EventTypeDialogAdapter dialogAdapter;
    private List<EventType> mArrayList=new ArrayList<>();
    EventTypeModel typeModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        recyclerViewSubCategory.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewSubCategory.setItemAnimator(new DefaultItemAnimator());
        setRecycler();
        if(Hawk.contains(AppUtils.OFFLINE_EVENTTYPE_LIST+AddCheckListSigletone.getInstance().getEventsID()))
        {
            progressDialog.setOfflineDataAvailable(true);
            list.clear();
            list.addAll((List<EventList>)Hawk.get(AppUtils.OFFLINE_EVENTTYPE_LIST+AddCheckListSigletone.getInstance().getEventsID()));
            setRecycler();
        }
        implementToolbar();
        if (GH.isInternetOn(mContext))
        LoadEvents();
    }

    private void implementToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(getString(R.string.str_things_you_need));

    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        adapter.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_done){
            // AddCheckListSigletone.getInstance().setEventsID(selected_ids);
            if(selected_subcat.size()>0)
            {
                if (GH.isInternetOn(mContext))
                AddCheckList();
                //AddCheckListSigletone.getInstance().setSubcategoryID(selected_ids);
                // startActivity(new Intent(mContext,EventTypeActivity.class));

            }else{
                Toast.makeText(mContext,getString(R.string.str_please_select_event_type), Toast.LENGTH_SHORT).show();
            }
            //startActivity(new Intent(mContext,CategoryListActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }
    private void LoadEvents(){
        JSONObject jsonObject=RequestBodyBuilder.getInstanse().getRequestEventTypes(AddCheckListSigletone.getInstance().getCategoryID(),AddCheckListSigletone.getInstance().getEventsID());
        Log.e("TAG", "LoadEvents: "+jsonObject.toString());
        if(!progressDialog.isShowing() && !progressDialog.getOfflineDataAvailable())
            progressDialog.show();
        Call<EventTypeModel> call = RetrofitBuilder.getInstance().getRetrofit().GetEventTypes(jsonObject);
        call.enqueue(new Callback<EventTypeModel>() {
            @Override
            public void onResponse(Call<EventTypeModel> call, Response<EventTypeModel> response) {
                eventTypeModel =response.body();
                typeModel=response.body();
                if(eventTypeModel.getStatus().equalsIgnoreCase(AppUtils.Success)){

                    list.clear();
                    list_temp.clear();

                  /*  for (int i = 0; i <typeModel.getDetailResult().size() ; i++) {
                        //list.add(eventTypeModel.getDetailResult().get(0).getEventList());
                        for (int j = 0; j < typeModel.getDetailResult().get(i).getEventList().size(); j++) {

                            EventList eventLists= typeModel.getDetailResult().get(i).getEventList().get(j);
                            List<EventType> list_eventtype=new ArrayList<EventType>();
                            for (int k = 0; k <eventLists.getEventTypes().size() ; k++) {
                                typeModel.getDetailResult().get(i).getEventList().get(j).getEventTypes().get(k).setEvent_categoryId(eventLists.getEventCategoryId());
                                Log.e("TAG", "onResponse: "+eventLists.getEventTypes().get(k).getEventTypeEventId());
                                if(!AddCheckListSigletone.getInstance().getSelected_eventTypes().contains(eventLists.getEventTypes().get(k).getEventTypeEventId())){
                                    list_eventtype.add(eventLists.getEventTypes().get(k));
                                    Log.e("TAG", "onResponse: true");
                                }
                            }

                            typeModel.getDetailResult().get(i).getEventList().get(j).setEventTypes(list_eventtype);
                            list_temp.add(typeModel.getDetailResult().get(i).getEventList().get(j));


                        }
                    }*/



                    for (int i = 0; i <eventTypeModel.getDetailResult().size() ; i++) {
                        List<EventList> eventLists_new=new ArrayList<EventList>();
                        //list.add(eventTypeModel.getDetailResult().get(0).getEventList());
                        for (int j = 0; j < eventTypeModel.getDetailResult().get(i).getEventList().size(); j++) {
                            EventList eventList=new EventList();
                            EventList eventLists= eventTypeModel.getDetailResult().get(i).getEventList().get(j);
                            List<EventType> list_eventtype=new ArrayList<EventType>();
                            for (int k = 0; k <eventLists.getEventTypes().size() ; k++) {
                                eventTypeModel.getDetailResult().get(i).getEventList().get(j).getEventTypes().get(k).setEvent_categoryId(eventLists.getEventCategoryId());
                                Log.e("TAG", "onResponse: "+eventLists.getEventTypes().get(k).getEventTypeEventId() +" "+TextUtils.join(",",AddCheckListSigletone.getInstance().getSelected_eventTypes()));
                                if(!AddCheckListSigletone.getInstance().getSelected_eventTypes().contains(eventLists.getEventTypes().get(k).getEventTypeEventId())){
                                    list_eventtype.add(eventLists.getEventTypes().get(k));
                                    Log.e("TAG", "onResponse: true");
                                }

                            }
                            eventList.setEvent(eventLists.getEvent());
                            eventList.setEventCategoryId(eventLists.getEventCategoryId());
                            eventList.setEventId(eventLists.getEventId());
                            eventList.setSubcategory(eventLists.getSubcategory());


                            eventList.setEventTypes(list_eventtype);
                            eventTypeModel.getDetailResult().get(i).getEventList().get(j).setEventTypes(new ArrayList<EventType>());
                            list.add(eventTypeModel.getDetailResult().get(i).getEventList().get(j));
                            list_temp.add(eventList);
                        }
                    }




                    Hawk.put(AppUtils.OFFLINE_EVENTTYPE_LIST+AddCheckListSigletone.getInstance().getEventsID(),list);
                    setRecycler();


                }else{

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<EventTypeModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

    private void AddCheckList(){
        JSONObject jsonObject=RequestBodyBuilder.getInstanse().getRequestAddCheckList(UserDetails.getInstance(mContext).getUser_id(),selected_subcat);
        Log.e("TAG", "AddEvent: "+jsonObject);
        if(!progressDialog.isShowing())
            progressDialog.show();
        Call<AddCheckListModel> call = RetrofitBuilder.getInstance().getRetrofit().AddCheckList(jsonObject);
        call.enqueue(new Callback<AddCheckListModel>() {
            @Override
            public void onResponse(Call<AddCheckListModel> call, Response<AddCheckListModel> response) {
                AddCheckListModel     addCheckListModel =response.body();
                if(eventTypeModel.getStatus().equalsIgnoreCase(AppUtils.Success)){
                    setResult(RESULT_OK);
                    finish();
                    Toast.makeText(EventTypeActivity.this,getString(R.string.str_CheckList_added_successfully), Toast.LENGTH_SHORT).show();

                }else{

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<AddCheckListModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

    void setRecycler(){
      /*  for (int i = 0; i < list.size(); i++) {
            list.get(i).setEventTypes(new ArrayList<EventType>());
        }*/
        for (int i = 0; i < list.size(); i++) {
            Log.e(TAG, "setRecycler: "+i);
        }
        Log.e(TAG, "setRecycler List Size: "+list.size());
        adapter=new EventTypeAdapter(mContext, list, new OnEventTypeSelect() {
            @Override
            public void OnEventSelect(EventType eventType, Boolean aBoolean) {

                if(aBoolean) {
                    selected_subcat.add(eventType);
                }else {
                    selected_subcat.remove(eventType);
                }


                selected_ids = TextUtils.join(",", selected_subcat);
                Log.e("TAG", "OnSubCategoryChecked: "+selected_ids);

            }

            @Override
            public void OnEventSelectParent(EventList position) {
                // Log.e(TAG, "OnEventSelectParent: "+position.getEventId());
                mArrayList.clear();

                for (int i = 0; i < list_temp.size(); i++) {
                    Log.e(TAG, "OnEventSelectParent: "+list_temp.get(i).getEventId()+" "+list_temp.get(i).getEventCategoryId()+
                            "@@@@@ "+position.getEventId()+" "+position.getEventCategoryId());
                    if(position.getEventId().equalsIgnoreCase(list_temp.get(i).getEventId()) && position.getEventCategoryId().equalsIgnoreCase(list_temp.get(i).getEventCategoryId())){
                        //  Log.e(TAG, "OnEventSelectParent eventytype size: "+list_temp.get(i).getEventTypes().size());
                        mArrayList.addAll(list_temp.get(i).getEventTypes());
                    }
                }

                DFragment dFragment = new DFragment(mContext);
                dFragment.setTitle(getString(R.string.str_choose_event_types));
                dFragment.show();
               // dFragment.show(getSupportFragmentManager(),getString(R.string.str_choose_event_types));
            }
        });
        recyclerViewSubCategory.setAdapter(adapter);
    }


    class DFragment extends Dialog {
        public DFragment(Context context) {
           //super(context);
            super(context,R.style.WhiteProgressDialog);
        }


     /*   public DFragment newInstance(int dialogNo, String title, String msg)
        {
            DFragment fragment = new DFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);

            return fragment;
        }*/

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialogfragment);
            setTitle(getString(R.string.str_choose_event_types));
            TextViewTitle ttvDone=(TextViewTitle) findViewById(R.id.btnDone);
            RecyclerView rc=(RecyclerView)findViewById(R.id.rcEventType);
            TextViewTitle empty_text=(TextViewTitle) findViewById(R.id.empty_text);
            GridLayoutManager gridLayoutManager;
            if(list.size()>4) {
                gridLayoutManager = new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
            }else {
                gridLayoutManager = new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
            }

            int spanCount =3; // 3 columns

            boolean includeEdge = false;
            rc.addItemDecoration(new GridSpacingItemDecoration(spanCount, 0, includeEdge));

            rc.setHasFixedSize(true);
            rc.setLayoutManager(gridLayoutManager);
            empty_text.setText(getString(R.string.str_no_event_types_available));
            if(mArrayList.size()<=0){
                empty_text.setVisibility(View.VISIBLE);
                rc.setVisibility(View.GONE);
            }

            dialogAdapter=new EventTypeDialogAdapter(mContext, mArrayList, new OnEventTypeDialogClick() {
                @Override
                public void onEventTypeDialogSelectClick(int position, Boolean aBoolean) {
                    mArrayList.get(position).setSelect(aBoolean);
                    //Log.e(TAG, "onEventTypeDialogSelectClick: "+mArrayList.get(position).getEventId());

                    if(aBoolean) {
                        selected_subcat.add(mArrayList.get(position));
                    }else {
                        selected_subcat.remove(mArrayList.get(position));
                    }





                    for (int l = 0; l < list_temp.size(); l++) {

                        if(list_temp.get(l).getEventId().equalsIgnoreCase(mArrayList.get(position).getEventId()) && list_temp.get(l).getEventCategoryId().equalsIgnoreCase(mArrayList.get(position).getEvent_categoryId())){
                            for (int m = 0; m < list_temp.get(l).getEventTypes().size(); m++) {
                                if(list_temp.get(l).getEventTypes().get(m).getEventTypeEventId().equalsIgnoreCase(mArrayList.get(position).getEventTypeEventId()) ){
                                    list_temp.get(l).getEventTypes().get(m).setSelect(mArrayList.get(position).getSelect());
                                }
                            }
                        }else {
                            break;
                        }
                    }
                    for (int i = 0; i < list.size(); i++) {

                        if(list.get(i).getEventId().equalsIgnoreCase(mArrayList.get(position).getEventId()) && list.get(i).getEventCategoryId().equalsIgnoreCase(mArrayList.get(position).getEvent_categoryId())){

                            list.get(i).setEventTypes(new ArrayList<EventType>());
                            for (int j = 0; j < mArrayList.size(); j++) {
                                if(mArrayList.get(j).getSelect()){
                                    list.get(i).getEventTypes().add(mArrayList.get(j));
                                }
                            }
                        }

                    }

                }
            });

            rc.setAdapter(dialogAdapter);

            ttvDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    setRecycler();
                }
            });


        }
/*
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.dialogfragment, container,
                    false);
            RecyclerView rc=(RecyclerView)rootView.findViewById(R.id.rcEventType);
            TextViewTitle empty_text=(TextViewTitle) rootView.findViewById(R.id.empty_text);
            GridLayoutManager gridLayoutManager;
            if(list.size()>4) {
                gridLayoutManager = new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
            }else {
                gridLayoutManager = new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
            }

            int spanCount =3; // 3 columns

            boolean includeEdge = false;
            rc.addItemDecoration(new GridSpacingItemDecoration(spanCount, 0, includeEdge));

            rc.setHasFixedSize(true);
            rc.setLayoutManager(gridLayoutManager);
            empty_text.setText(getString(R.string.str_no_event_types_available));
            if(mArrayList.size()<=0){
                empty_text.setVisibility(View.VISIBLE);
                rc.setVisibility(View.GONE);
            }

            dialogAdapter=new EventTypeDialogAdapter(mContext, mArrayList, new OnEventTypeDialogClick() {
                @Override
                public void onEventTypeDialogSelectClick(int position, Boolean aBoolean) {
                    mArrayList.get(position).setSelect(aBoolean);
                    //Log.e(TAG, "onEventTypeDialogSelectClick: "+mArrayList.get(position).getEventId());

                    if(aBoolean) {
                        selected_subcat.add(mArrayList.get(position));
                    }else {
                        selected_subcat.remove(mArrayList.get(position));
                    }





                    for (int l = 0; l < list_temp.size(); l++) {

                        if(list_temp.get(l).getEventId().equalsIgnoreCase(mArrayList.get(position).getEventId()) && list_temp.get(l).getEventCategoryId().equalsIgnoreCase(mArrayList.get(position).getEvent_categoryId())){
                            for (int m = 0; m < list_temp.get(l).getEventTypes().size(); m++) {
                                if(list_temp.get(l).getEventTypes().get(m).getEventTypeEventId().equalsIgnoreCase(mArrayList.get(position).getEventTypeEventId()) ){
                                    list_temp.get(l).getEventTypes().get(m).setSelect(mArrayList.get(position).getSelect());
                                }
                            }
                        }else {
                            break;
                        }
                    }
                    for (int i = 0; i < list.size(); i++) {

                        if(list.get(i).getEventId().equalsIgnoreCase(mArrayList.get(position).getEventId()) && list.get(i).getEventCategoryId().equalsIgnoreCase(mArrayList.get(position).getEvent_categoryId())){

                            list.get(i).setEventTypes(new ArrayList<EventType>());
                            for (int j = 0; j < mArrayList.size(); j++) {
                                if(mArrayList.get(j).getSelect()){
                                    list.get(i).getEventTypes().add(mArrayList.get(j));
                                }
                            }
                        }

                    }
                    setRecycler();
                }
            });

            rc.setAdapter(dialogAdapter);

            getDialog().setTitle("DialogFragment Tutorial");
            // Do something else
            return rootView;
        }
*/

     /*   @Override
        public void onStart() {
            super.onStart();

            Dialog dialog = getDialog();
            if (dialog != null) {
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }*/
    }


}