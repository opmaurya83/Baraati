package com.nectarbits.baraati;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;

import com.nectarbits.baraati.Models.Notifications.Read.NotificationReadModel;
import com.nectarbits.baraati.Models.Notifications.Request.AcceptRejectModel;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.orhanobut.hawk.Hawk;
import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDateResponsibilityActivity extends AppCompatActivity {

    private static final String TAG = EventDateResponsibilityActivity.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Bind(R.id.cv_accept_reject_status)
    CardView cv_accept_reject_status;

    @Bind(R.id.tvt_accept_or_reject)
    TextViewTitle tvt_accept_or_reject;

    @Bind(R.id.cv_accept_reject)
    CardView cv_accept_reject;
    @Bind(R.id.ttv_start_date_empty)
    TextViewDescription ttvStartDateEmpty;
    @Bind(R.id.ttv_start_date)
    TextViewTitle ttvStartDate;
    @Bind(R.id.ttv_start_month)
    TextViewTitle ttvStartMonth;
    @Bind(R.id.ttv_start_year)
    TextViewTitle ttvStartYear;
    @Bind(R.id.llStartDate)
    LinearLayout llStartDate;
    @Bind(R.id.ttv_end_date_empty)
    TextViewDescription ttvEndDateEmpty;
    @Bind(R.id.ttv_end_date)
    TextViewTitle ttvEndDate;
    @Bind(R.id.ttv_end_month)
    TextViewTitle ttvEndMonth;
    @Bind(R.id.ttv_end_year)
    TextViewTitle ttvEndYear;
    @Bind(R.id.llEndDate)
    LinearLayout llEndDate;
    @Bind(R.id.tblRowDateRange)
    TableRow tblRowDateRange;

    @Bind(R.id.txt_user_label)
    TextViewDescription txt_user_label;

    @Bind(R.id.tv_instructions)
    TextViewDescription tv_instructions;

    @OnClick(R.id.ll_accept)
    void OnAccept() {
        if (GH.isInternetOn(mContext))
        Request(AppUtils.accept);
    }

    @OnClick(R.id.ll_reject)
    void OnReject() {
        if (GH.isInternetOn(mContext))
        Request(AppUtils.reject);
    }

    Context mContext;
    String userevent_id, eventname;
    final CharSequence[] items = {"Hour", "Day", "Week"};
    String mEventStatus, mType, from_user_id, notification_id = "0", notification_is_read = "-1";
    static Boolean IS_UPDATED = false;
    Boolean ISFROMNOTIFICATION = false;
    SimpleDateFormat fmt_1 = new SimpleDateFormat("yyyy-MMM-dd");

    String username="",main_event_name="";

/*    @OnClick(R.id.tblRowDateRange)
    void onDateClick(){

         Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 100);

        Calendar lastYear = Calendar.getInstance();
        showCalendarInDialog(getString(R.string.str_set_dates),R.layout.layout_calendar);

        ArrayList<Date> dates = new ArrayList<Date>();
        if(getIntent().getExtras().getString(AppUtils.ARG_DATE_FROM).toString().length()>0 && !getIntent().getExtras().getString(AppUtils.ARG_DATE_FROM).toString().equalsIgnoreCase("0000-00-00"))
        {
            try {
                dates.add(fmt.parse(getIntent().getExtras().getString(AppUtils.ARG_DATE_FROM).toString()));
                if(dates.get(0).before(lastYear.getTime())){
                    lastYear.setTime(fmt.parse(getIntent().getExtras().getString(AppUtils.ARG_DATE_FROM).toString()));
                }

            }catch (Exception ex){

            }
        }

        if(getIntent().getExtras().getString(AppUtils.ARG_DATE_TO).toString().length()>0 && !getIntent().getExtras().getString(AppUtils.ARG_DATE_TO).toString().equalsIgnoreCase("0000-00-00"))
        {
            try {
                dates.add(fmt.parse(getIntent().getExtras().getString(AppUtils.ARG_DATE_TO).toString()));
            }catch (Exception ex){

            }
        }



        dialogView.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDates(dates);


    }*/


    private AlertDialog theDialog;
    private CalendarPickerView dialogView;
    List<Date> list = new ArrayList<>();
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsibility_set_event_date);
        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        userevent_id = getIntent().getExtras().getString(AppUtils.ARG_EVENTTYPE_ID);
        eventname = getIntent().getExtras().getString(AppUtils.ARG_EVENTTYPE_NAME);
        mEventStatus = getIntent().getExtras().getString(AppUtils.ARG_EVENT_STATUS);
        mType = getIntent().getExtras().getString(AppUtils.ARG_NOTIFICATION_TYPE);
        from_user_id = getIntent().getExtras().getString(AppUtils.ARG_FOR_USER_ID);

        notification_id = getIntent().getExtras().getString(AppUtils.ARG_NOTIFICATION_ID, "0");
        notification_is_read = getIntent().getExtras().getString(AppUtils.ARG_NOTIFICATION_IS_READ, "-1");

        username = getIntent().getExtras().getString(AppUtils.ARG_NOTIFICATION_USERNAME, "");
        main_event_name = getIntent().getExtras().getString(AppUtils.ARG_NOTIFICATION_MAINEVENT, "");

        ISFROMNOTIFICATION = getIntent().getExtras().getBoolean(AppUtils.ARG_IS_FROM_NOTIFICATION, false);

        if (getIntent().getExtras().getString(AppUtils.ARG_DATE_FROM).toString().length() > 0 && !getIntent().getExtras().getString(AppUtils.ARG_DATE_FROM).toString().equalsIgnoreCase("0000-00-00")) {
            //txtFrom.setText(getIntent().getExtras().getString(AppUtils.ARG_DATE_FROM));
            setStartDate(getIntent().getExtras().getString(AppUtils.ARG_DATE_FROM));
            llStartDate.setVisibility(View.VISIBLE);
            ttvStartDateEmpty.setVisibility(View.GONE);
        } else {
            llStartDate.setVisibility(View.GONE);
            ttvStartDateEmpty.setVisibility(View.VISIBLE);
        }

        if (getIntent().getExtras().getString(AppUtils.ARG_DATE_TO).toString().length() > 0 && !getIntent().getExtras().getString(AppUtils.ARG_DATE_TO).toString().equalsIgnoreCase("0000-00-00")) {
            //txtTo.setText(getIntent().getExtras().getString(AppUtils.ARG_DATE_TO));
            setEndDate(getIntent().getExtras().getString(AppUtils.ARG_DATE_TO));
            llEndDate.setVisibility(View.VISIBLE);
            ttvEndDateEmpty.setVisibility(View.GONE);
        }else{
            llEndDate.setVisibility(View.GONE);
            ttvEndDateEmpty.setVisibility(View.VISIBLE);
        }

        IS_UPDATED = false;

        if (mType.equalsIgnoreCase(AppUtils.Accepted)) {
            cv_accept_reject.setVisibility(View.GONE);
            cv_accept_reject_status.setVisibility(View.VISIBLE);

            tvt_accept_or_reject.setText(R.string.accepted);

        } else if (mType.equalsIgnoreCase(AppUtils.Rejected)) {
            cv_accept_reject.setVisibility(View.GONE);
            cv_accept_reject_status.setVisibility(View.VISIBLE);

            tvt_accept_or_reject.setText(R.string.rejected);
            IS_UPDATED = true;

        } else if (mEventStatus.equalsIgnoreCase(AppUtils.Incomplete)) {
            cv_accept_reject.setVisibility(View.GONE);
            cv_accept_reject_status.setVisibility(View.VISIBLE);

            tvt_accept_or_reject.setText(R.string.incomplete);
        } else if (mType.equalsIgnoreCase(AppUtils.Completed)) {
            cv_accept_reject.setVisibility(View.GONE);
            cv_accept_reject_status.setVisibility(View.VISIBLE);

            tvt_accept_or_reject.setText(R.string.completed);
        } else if (mType.equalsIgnoreCase(AppUtils.Canceled)) {
            cv_accept_reject.setVisibility(View.GONE);
            cv_accept_reject_status.setVisibility(View.VISIBLE);
            tvt_accept_or_reject.setText(R.string.removed);
        }


        implementToolbar();

        if (!notification_id.equalsIgnoreCase("0") && notification_is_read.equalsIgnoreCase("0")) {
            if (GH.isInternetOn(mContext))
            ReadNotifications();
        }
        Log.e(TAG, "onCreate: " + userevent_id);

        txt_user_label.setText(String.format(getString(R.string.mahesh_mori_shared_responsibility_for_sangeet_dj),username,main_event_name,eventname));
        tv_instructions.setText(String.format(getString(R.string.now_you_can_help_to_manage_for_their_wedding_by_purchasing_product_suggest_best_vendor_for_the_particular_event),eventname));
        GH.UpdateQuickBloxID(mContext);
    }

    private void implementToolbar() {

        toolbar.setTitle(eventname);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ISFROMNOTIFICATION) {
                    Intent intent = new Intent(mContext, SplashscreenActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });


    }


    private void Request(final String descison) {
        if (!progressDialog.isShowing())
            progressDialog.show();
        Call<AcceptRejectModel> call = RetrofitBuilder.getInstance().getRetrofit().Aceept_Reject_Request(RequestBodyBuilder.getInstanse().Request_Accept_Reject(UserDetails.getInstance(mContext).getUser_id(), from_user_id, userevent_id, descison));
        call.enqueue(new Callback<AcceptRejectModel>() {
            @Override
            public void onResponse(Call<AcceptRejectModel> call, Response<AcceptRejectModel> response) {
                AcceptRejectModel subCategoryModel = response.body();
                if (subCategoryModel.getStatus().equalsIgnoreCase(AppUtils.Success)) {

                    cv_accept_reject.setVisibility(View.GONE);
                    cv_accept_reject_status.setVisibility(View.VISIBLE);
                    if (descison.equalsIgnoreCase(AppUtils.accept)) {
                        tvt_accept_or_reject.setText(R.string.accepted);
                    } else {
                        tvt_accept_or_reject.setText(R.string.rejected);
                    }
                    IS_UPDATED = true;

                } else {

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<AcceptRejectModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && requestCode == AppUtils.REQUEST_SELECT_MEMBER) {
            String selected_id = data.getStringExtra(AppUtils.ARG_SELECTED_MEMBER);
            Log.e(TAG, "onActivityResult: " + selected_id);

        }
    }

    private void ReadNotifications() {
        if (!progressDialog.isShowing())
            progressDialog.show();
        Call<NotificationReadModel> call = RetrofitBuilder.getInstance().getRetrofit().ReadNotifications(RequestBodyBuilder.getInstanse().Request_ReadNotifications(UserDetails.getInstance(mContext).getUser_id(), notification_id));
        call.enqueue(new Callback<NotificationReadModel>() {
            @Override
            public void onResponse(Call<NotificationReadModel> call, Response<NotificationReadModel> response) {
                NotificationReadModel subCategoryModel = response.body();
                if (subCategoryModel.getStatus().equalsIgnoreCase(AppUtils.Success)) {

                    String count=(String)Hawk.get(AppUtils.ARG_NOTIFICATION_COUNT);
                    if(count!=null){
                        if(!count.equalsIgnoreCase("0"))
                        {
                            int count_1=Integer.parseInt((String)Hawk.get(AppUtils.ARG_NOTIFICATION_COUNT));
                            count_1--;
                            Hawk.put(AppUtils.ARG_NOTIFICATION_COUNT,""+count_1);
                        }
                    }
                    setResult(RESULT_OK);

                } else {

                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<NotificationReadModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // do something
            if (ISFROMNOTIFICATION) {
                Intent intent = new Intent(mContext, SplashscreenActivity.class);
                startActivity(intent);
            }
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    void setStartDate(String startdate) {
        try {
            Date weddingdate = fmt.parse(startdate);
            String date = fmt_1.format(weddingdate);
            String month = date.split("-")[1];
            String year = date.split("-")[0];
            String day = date.split("-")[2];
            ttvStartDate.setText(day);
            ttvStartMonth.setText(month);
            ttvStartYear.setText(year);
        } catch (Exception ex) {

        }
    }


    void setEndDate(String endtdate) {
        try {
            Date weddingdate = fmt.parse(endtdate);
            String date = fmt_1.format(weddingdate);
            String month = date.split("-")[1];
            String year = date.split("-")[0];
            String day = date.split("-")[2];
            ttvEndDate.setText(day);
            ttvEndMonth.setText(month);
            ttvEndYear.setText(year);
        } catch (Exception ex) {

        }
    }

}
