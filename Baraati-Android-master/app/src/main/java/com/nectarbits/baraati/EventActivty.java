package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.nectarbits.baraati.Adapters.EventAdapter;
import com.nectarbits.baraati.Interface.OnEventSelect;
import com.nectarbits.baraati.Models.Event.DetailResult;
import com.nectarbits.baraati.Models.Event.EventList;
import com.nectarbits.baraati.Models.Event.EventModel;
import com.nectarbits.baraati.Singletone.AddCheckListSigletone;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.orhanobut.hawk.Hawk;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivty extends AppCompatActivity {
    private static final String TAG = EventActivty.class.getSimpleName();
    private Context mContext;
    @Bind(R.id.recyclerViewSubCategory)
    RecyclerView recyclerViewSubCategory;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    ProgressDialog progressDialog;
    EventAdapter adapter;
    EventModel eventTypeModel;
    List<String> selected_subcat = new ArrayList<String>();
    String selected_ids="";
    List<DetailResult> list_final=new ArrayList<DetailResult>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_activty);
        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        recyclerViewSubCategory.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewSubCategory.setItemAnimator(new DefaultItemAnimator());
        setRecyclerViewSubCategory();
        if(Hawk.contains(AppUtils.OFFLINE_EVENT_LIST +AddCheckListSigletone.getInstance().getSubcategoryID())){
            progressDialog.setOfflineDataAvailable(true);
            list_final.clear();
            list_final.addAll((List<DetailResult>)Hawk.get(AppUtils.OFFLINE_EVENT_LIST +AddCheckListSigletone.getInstance().getSubcategoryID()));
            setRecyclerViewSubCategory();

        }
        implementToolbar();
        if (GH.isInternetOn(mContext))
        LoadEvents();

    }

    /**
     * Set ToolBar
     */
    private void implementToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(getString(R.string.str_choose_event));

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
        getMenuInflater().inflate(R.menu.menu_next, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_next){
            AddCheckListSigletone.getInstance().setEventsID(selected_ids);
            if(selected_ids.length()>0)
            {
                AddCheckListSigletone.getInstance().setEventsID(selected_ids);
                startActivityForResult(new Intent(mContext,EventTypeActivity.class),AppUtils.REQUEST_EventType);

            }else{
                Toast.makeText(EventActivty.this,getString(R.string.str_please_select_wedding_type), Toast.LENGTH_SHORT).show();
            }
            //startActivity(new Intent(mContext,CategoryListActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }
    private void LoadEvents(){
        if(!progressDialog.isShowing() && !progressDialog.getOfflineDataAvailable())
            progressDialog.show();
        JSONObject jsonObject=RequestBodyBuilder.getInstanse().getRequestEvents(AddCheckListSigletone.getInstance().getCategoryID(),AddCheckListSigletone.getInstance().getSubcategoryID());
        Log.e(TAG, "LoadEvents: "+jsonObject.toString());

        Call<EventModel> call = RetrofitBuilder.getInstance().getRetrofit().GetEvents(RequestBodyBuilder.getInstanse().getRequestEvents(AddCheckListSigletone.getInstance().getCategoryID(),AddCheckListSigletone.getInstance().getSubcategoryID()));
        call.enqueue(new Callback<EventModel>() {
            @Override
            public void onResponse(Call<EventModel> call, Response<EventModel> response) {
                eventTypeModel =response.body();
                if(eventTypeModel.getStatus().equalsIgnoreCase(AppUtils.Success)){

                     List<DetailResult> list=eventTypeModel.getDetailResult();
                    list_final.clear();
                    for (int i = 0; i < list.size() ; i++) {
                         List<EventList> event_list=new ArrayList<EventList>();
                        for (int j = 0; j < list.get(i).getEventList().size(); j++) {

                            if(!AddCheckListSigletone.getInstance().getSelected_event().contains(list.get(i).getEventList().get(j).getEventId())) {
                                event_list.add(list.get(i).getEventList().get(j));
                          }
                        }
                        list.get(i).setEventList(event_list);
                        list_final.add(list.get(i));
                    }

                    Hawk.put(AppUtils.OFFLINE_EVENT_LIST +AddCheckListSigletone.getInstance().getSubcategoryID(),list_final);
                    setRecyclerViewSubCategory();


                }else{

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<EventModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==AppUtils.REQUEST_EventType ){
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    /**
     * Set RecyclerView
     */
    void setRecyclerViewSubCategory(){

        adapter=new EventAdapter(mContext, list_final, new OnEventSelect() {
            @Override
            public void OnEventSelect(EventList eventType, Boolean aBoolean) {
                if (eventType != null) {
                   /* if (aBoolean ) {
                        if(!selected_subcat.contains(eventType.getEventCategoryId()))
                        selected_subcat.add(eventType.getEventCategoryId());

                    } else {
                        selected_subcat.remove(eventType.getEventCategoryId());
                    }*/

                    selected_subcat.clear();
                    for (int i = 0; i < list_final.size(); i++) {
                        for (int j = 0; j < list_final.get(i).getEventList().size(); j++) {
                            if(list_final.get(i).getEventList().get(j).getSelected()) {
                              //  Log.e(TAG, "OnEventSelect: "+list_final.get(i).getEventList().get(j).getEventCategoryId());
                                selected_subcat.add(list_final.get(i).getEventList().get(j).getEventCategoryId());
                            }
                        }

                    }
                    selected_ids = TextUtils.join(",", selected_subcat);
                }
            }
        });
        recyclerViewSubCategory.setAdapter(adapter);

    }
}
