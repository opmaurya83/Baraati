package com.nectarbits.baraati;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.nectarbits.baraati.Adapters.NotificationsAdapter;
import com.nectarbits.baraati.Models.Notifications.List.Detail;
import com.nectarbits.baraati.Models.Notifications.List.NotificationModel;
import com.nectarbits.baraati.Models.Notifications.Read.NotificationReadModel;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    @Bind(R.id.rv_notifications)
    RecyclerView rv_notifications;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private Context mContext;
    ProgressDialog progressDialog;
    NotificationsAdapter adapter;
    List<Detail> list_final=new ArrayList<Detail>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        mContext = this;
        implementToolbar();

        progressDialog = new ProgressDialog(mContext);

        rv_notifications.setLayoutManager(new LinearLayoutManager(mContext));
        rv_notifications.setItemAnimator(new DefaultItemAnimator());
        setRecyclerViewSubCategory();
        if (GH.isInternetOn(mContext))
        LoadNotifications();
        GH.UpdateQuickBloxID(mContext);
    }

    /**
     * set ToolBar
     */
    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.notifications));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void setRecyclerViewSubCategory(){
        adapter=new NotificationsAdapter(mContext, list_final, new NotificationsAdapter.OnNotificationClickListener() {


            @Override
            public void onNotificationClick(int position) {
                Detail detail=list_final.get(position);
              /*  if(detail.getIsread().equalsIgnoreCase("0")) {

                    ReadNotifications(detail);
                }else{
*/
                    if(!detail.getType().equalsIgnoreCase(AppUtils.Canceled)) {
                        Intent intent = new Intent(mContext, EventDateResponsibilityActivity.class);
                        intent.putExtra(AppUtils.ARG_EVENTTYPE_ID, detail.getUserEventTypeEventsId());
                        intent.putExtra(AppUtils.ARG_EVENTTYPE_NAME, detail.getEventName());
                        intent.putExtra(AppUtils.ARG_DATE_FROM, detail.getStartDate());
                        intent.putExtra(AppUtils.ARG_DATE_TO, detail.getEndDate());
                        intent.putExtra(AppUtils.ARG_FOR_USER_ID, detail.getFromUserId());
                        intent.putExtra(AppUtils.ARG_NOTIFICATION_TYPE, detail.getType());
                        intent.putExtra(AppUtils.ARG_EVENT_STATUS, detail.getEventStatus());
                        intent.putExtra(AppUtils.ARG_NOTIFICATION_ID, detail.getNotificationId());
                        intent.putExtra(AppUtils.ARG_NOTIFICATION_IS_READ, detail.getIsread());
                        intent.putExtra(AppUtils.ARG_NOTIFICATION_DATE, detail.getNotificationDate());

                        intent.putExtra(AppUtils.ARG_NOTIFICATION_USERNAME, detail.getUserName());
                        intent.putExtra(AppUtils.ARG_NOTIFICATION_MAINEVENT, detail.getMainEventname());


                        startActivityForResult(intent, AppUtils.REQUEST_READ_NOTIFICATION);
                    }else {
                        Toast.makeText(mContext,detail.getMessage(), Toast.LENGTH_SHORT).show();
                    }
           /*     }*/
            }
        });

        rv_notifications.setAdapter(adapter);
    }

    private void LoadNotifications(){
        if(!progressDialog.isShowing() )
            progressDialog.show();
        Call<NotificationModel> call = RetrofitBuilder.getInstance().getRetrofit().GetNotifications(RequestBodyBuilder.getInstanse().Request_GetNotifications(UserDetails.getInstance(mContext).getUser_id()));
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                NotificationModel subCategoryModel=response.body();
                if(subCategoryModel.getStatus().equalsIgnoreCase(AppUtils.Success)){

                    list_final.clear();
                    list_final.addAll(subCategoryModel.getDetail());

                    adapter.notifyDataSetChanged();;

                }else{

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==AppUtils.REQUEST_READ_NOTIFICATION && resultCode==RESULT_OK){
            LoadNotifications();
        }
    }


    /*  private void ReadNotifications(final Detail detail){
        if(!progressDialog.isShowing() )
            progressDialog.show();
        Call<NotificationReadModel> call = RetrofitBuilder.getInstance().getRetrofit().ReadNotifications(RequestBodyBuilder.getInstanse().Request_ReadNotifications(UserDetails.getInstance(mContext).getUser_id(),detail.getNotificationId()));
        call.enqueue(new Callback<NotificationReadModel>() {
            @Override
            public void onResponse(Call<NotificationReadModel> call, Response<NotificationReadModel> response) {
                NotificationReadModel subCategoryModel=response.body();
                if(subCategoryModel.getStatus().equalsIgnoreCase(AppUtils.Success)){

                    if(!detail.getType().equalsIgnoreCase(AppUtils.Canceled)) {
                        Intent intent = new Intent(mContext, EventDateResponsibilityActivity.class);
                        intent.putExtra(AppUtils.ARG_EVENTTYPE_ID, detail.getUserEventTypeEventsId());
                        intent.putExtra(AppUtils.ARG_EVENTTYPE_NAME, detail.getEventName());
                        intent.putExtra(AppUtils.ARG_DATE_FROM, detail.getStartDate());
                        intent.putExtra(AppUtils.ARG_DATE_TO, detail.getEndDate());
                        intent.putExtra(AppUtils.ARG_FOR_USER_ID, detail.getFromUserId());
                        intent.putExtra(AppUtils.ARG_NOTIFICATION_TYPE, detail.getType());
                        intent.putExtra(AppUtils.ARG_EVENT_STATUS, detail.getEventStatus());
                        intent.putExtra(AppUtils.ARG_NOTIFICATION_DATE, detail.getNotificationDate());
                        startActivityForResult(intent, AppUtils.REQUEST_UPDATE_USEREVENT);
                    }else {
                        Toast.makeText(mContext,detail.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }else{

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<NotificationReadModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }*/
}
