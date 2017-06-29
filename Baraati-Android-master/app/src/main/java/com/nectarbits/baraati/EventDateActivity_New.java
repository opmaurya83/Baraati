package com.nectarbits.baraati;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.domain.Event;
import com.nectarbits.baraati.Adapters.ResponsibilityListAdapter;
import com.nectarbits.baraati.Chat.ui.activities.chats.ContactsActivity;
import com.nectarbits.baraati.Models.AddDates.AddDateModel;
import com.nectarbits.baraati.Models.Notifications.Read.NotificationReadModel;
import com.nectarbits.baraati.Models.Resposibility.Add.AddResponsibilityModel;
import com.nectarbits.baraati.Models.Resposibility.Add.SharedeventDetail;
import com.nectarbits.baraati.Models.Resposibility.Delete.DeleteResponsibilityModel;
import com.nectarbits.baraati.Models.Resposibility.List.ListResponsibilityModel;
import com.nectarbits.baraati.Models.UserEvent.EventType;
import com.nectarbits.baraati.Singletone.UserCalenderEventsSingletone;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.TextViewDescription;
import com.nectarbits.baraati.View.TextViewTitle;
import com.nectarbits.baraati.generalHelper.AlertsUtils;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.GH;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.generalHelper.SingletonUtils;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDateActivity_New extends AppCompatActivity {

    private static final String TAG = EventDateActivity_New.class.getSimpleName();
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.rv_responsible_members)
    RecyclerView rv_responsible_members;


    @Bind(R.id.empty_text)
    TextViewTitle empty_text;

    Context mContext;
    String userevent_id, eventname;
    @Bind(R.id.txtRemindmeBefore)
    TextViewDescription txtRemindmeBefore;
    Boolean ISFROMNOTIFICATION = false;
    final CharSequence[] items = {"Hour", "Day", "Week"};
    int selected_reminder = -1;
    String categoryID, subCategoryID, notification_id = "0", notification_is_read = "-1";
    List<SharedeventDetail> responsableDetailList = new ArrayList<>();
    List<String> sharecontact_list = new ArrayList<>();

    @Bind(R.id.ttv_start_date_empty)
    TextViewDescription ttvStartDateEmpty;
    @Bind(R.id.ttv_start_date)
    TextViewTitle ttvStartDate;
    @Bind(R.id.ttv_start_month)
    TextViewTitle ttvStartMonth;
    @Bind(R.id.ttv_start_year)
    TextViewTitle ttvStartYear;
    @Bind(R.id.ttv_end_date_empty)
    TextViewDescription ttvEndDateEmpty;
    @Bind(R.id.ttv_end_date)
    TextViewTitle ttvEndDate;
    @Bind(R.id.ttv_end_month)
    TextViewTitle ttvEndMonth;
    @Bind(R.id.ttv_end_year)
    TextViewTitle ttvEndYear;
    @Bind(R.id.llStartDate)
    LinearLayout llStartDate;
    @Bind(R.id.llEndDate)
    LinearLayout llEndDate;
    @Bind(R.id.tblRowDateRange)
    TableRow tblRowDateRange;
    @Bind(R.id.tblRowReminder)
    TableRow tblRowReminder;
    @Bind(R.id.btnplus)
    TextView btnplus;
    @Bind(R.id.tvt_plus)
    ImageView tvtPlus;
  /*  @Bind(R.id.btnDone)
    TextView btnDone;*/
  @Bind(R.id.tvd_complete_event)
  TextViewDescription tvd_complete_event;
    @OnClick(R.id.tblRowDateRange)
    void onDateClick() {

        if(isCalenderOpen==false) {
            isCalenderOpen=true;
            Calendar nextYear = Calendar.getInstance();
            nextYear.add(Calendar.YEAR, 100);

            Calendar lastYear = Calendar.getInstance();
            showCalendarInDialog(getString(R.string.str_set_dates), R.layout.layout_calendar);

            ArrayList<Date> dates = new ArrayList<Date>();
            if (getIntent().getExtras().getString(AppUtils.ARG_DATE_FROM).toString().length() > 0 && !getIntent().getExtras().getString(AppUtils.ARG_DATE_FROM).toString().equalsIgnoreCase("0000-00-00")) {
                try {
                    dates.add(fmt.parse(getIntent().getExtras().getString(AppUtils.ARG_DATE_FROM).toString()));
                    if (dates.get(0).before(lastYear.getTime())) {
                        lastYear.setTime(fmt.parse(getIntent().getExtras().getString(AppUtils.ARG_DATE_FROM).toString()));
                    }

                } catch (Exception ex) {

                }
            }

            if (getIntent().getExtras().getString(AppUtils.ARG_DATE_TO).toString().length() > 0 && !getIntent().getExtras().getString(AppUtils.ARG_DATE_TO).toString().equalsIgnoreCase("0000-00-00")) {
                try {
                    dates.add(fmt.parse(getIntent().getExtras().getString(AppUtils.ARG_DATE_TO).toString()));
                } catch (Exception ex) {

                }
            }


            dialogView.init(lastYear.getTime(), nextYear.getTime()) //
                    .inMode(CalendarPickerView.SelectionMode.RANGE)
                    .withSelectedDates(dates);
        }

    }


    @OnClick(R.id.tblRowReminder)
    void onSetReminderClick() {


        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Remind before");

        builder.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        selected_reminder = item;

                        txtRemindmeBefore.setText(getString(R.string.str_remind_me_before) + " " + items[item]);


                        if(list.size()>0) {
                            Date date = list.get(0);

                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            calendar.set(Calendar.HOUR_OF_DAY, 10);

                            EventType eventType = null;

                            /**
                             * Update calender event list
                             */
                            List<Event> eventList = new ArrayList<Event>();
                            List<Event> eventList_copy = new ArrayList<Event>();
                            eventList.addAll(UserCalenderEventsSingletone.getInstance().getUserEventlist());
                            for (int i = 0; i < eventList.size(); i++) {
                                if (((EventType) eventList.get(i).getData()).getUserEventTypeEventsId().equalsIgnoreCase(userevent_id)) {
                                    eventType = ((EventType) eventList.get(i).getData());
                                } else {
                                    eventList_copy.add(eventList.get(i));
                                }
                            }
                            UserCalenderEventsSingletone.getInstance().getUserEventlist().clear();
                            UserCalenderEventsSingletone.getInstance().setUserEventlist(eventList_copy);
                            List<Date> list_dates = GH.getDates(start_date, end_date);

                            if (eventType != null) {
                                eventType.setEndDate(start_date);
                                eventType.setStartDate(end_date);
                                for (int m = 0; m < list_dates.size(); m++) {

                                    UserCalenderEventsSingletone.getInstance().getUserEventlist().add(new Event(Color.WHITE, list_dates.get(m).getTime(), eventType));
                                }
                            }

                            if (selected_reminder == 0) {
                                calendar.set(Calendar.HOUR_OF_DAY, 9);
                            } else if (selected_reminder == 1) {
                                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
                            } else {
                                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 7);
                            }

                            Intent myIntent = new Intent(mContext, NotifyService.class);
                            myIntent.putExtra(AppUtils.ARG_REMINDER_TITLE, eventname);
                            myIntent.putExtra(AppUtils.ARG_REMINDER_FROM, start_date);
                            myIntent.putExtra(AppUtils.ARG_REMINDER_TO, end_date);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            PendingIntent pendingIntent = PendingIntent.getService(mContext, 0, myIntent, PendingIntent.FLAG_ONE_SHOT);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
                        }
                    }
                });
     /*   builder.setPositiveButton(btnText[0], listener);
        if (btnText.length != 1) {
            builder.setNegativeButton(btnText[1], listener);
        }*/
        builder.show();


    }

  /*  @OnClick(R.id.btnDone)
    void onDone() {

        //if (ttvStartDate.getText().toString().length() > 0 && ttvEndDate.getText().toString().length() > 0) {
        if(list.size()>0){
            setDates();
        } else {
            Toast.makeText(EventDateActivity_New.this, getString(R.string.str_please_set_dates), Toast.LENGTH_SHORT).show();
        }


    }*/

    @OnClick(R.id.tvt_plus)
    void AddMember() {
        SingletonUtils.getInstance().setStrings(sharecontact_list);
        ContactsActivity.start(EventDateActivity_New.this);
    }

    private AlertDialog theDialog;
    private CalendarPickerView dialogView;
    List<Date> list = new ArrayList<>();
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat fmt_1 = new SimpleDateFormat("yyyy-MMM-dd");
    ProgressDialog progressDialog;
    ResponsibilityListAdapter adapter;
    String start_date = "", end_date = "";
    Boolean isCalenderOpen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventdate);
        ButterKnife.bind(this);
        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        userevent_id = getIntent().getExtras().getString(AppUtils.ARG_EVENTTYPE_ID);
        eventname = getIntent().getExtras().getString(AppUtils.ARG_EVENTTYPE_NAME);
        categoryID = getIntent().getExtras().getString(AppUtils.ARG_CATEGORY_ID);
        subCategoryID = getIntent().getExtras().getString(AppUtils.ARG_SUBCATEGORY_ID);

        notification_id = getIntent().getExtras().getString(AppUtils.ARG_NOTIFICATION_ID, "0");
        notification_is_read = getIntent().getExtras().getString(AppUtils.ARG_NOTIFICATION_ID, "-1");

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
        empty_text.setText(getString(R.string.str_no_member_found));

        rv_responsible_members.setLayoutManager(new LinearLayoutManager(mContext));
        rv_responsible_members.setItemAnimator(new DefaultItemAnimator());

        if (GH.isInternetOn(mContext))
        ListResponsibility();

        setupListView();
        implementToolbar();


        if (!notification_id.equalsIgnoreCase("0") && notification_is_read.equalsIgnoreCase("0")) {
            if (GH.isInternetOn(mContext))
            ReadNotifications();
        }
        Log.e(TAG, "onCreate: " + userevent_id);

        if(getIntent().getExtras().getString(AppUtils.ARG_IS_DONE, "0").equalsIgnoreCase("1")){
            tvd_complete_event.setText(String.format(getString(R.string.user_completed_this_event),getIntent().getExtras().getString(AppUtils.ARG_IS_DONE_USER, "")));
        }else{
            tvd_complete_event.setVisibility(View.GONE);
        }
        GH.UpdateQuickBloxID(mContext);
    }

    private void implementToolbar() {

        toolbar.setTitle(getString(R.string.str_schedule) + " " + eventname);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_calendar) {

            startActivity(new Intent(mContext, CalendarActivity.class));

        }


        return super.onOptionsItemSelected(item);
    }

    private void showCalendarInDialog(String title, int layoutResId) {
        dialogView = (CalendarPickerView) getLayoutInflater().inflate(layoutResId, null, false);
        theDialog = new AlertDialog.Builder(this) //
                .setTitle(title)
                .setView(dialogView)
                .setNeutralButton(getString(R.string.str_schedule_event), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        list = dialogView.getSelectedDates();
                        Log.e(TAG, "onClick: selected datesize::" + list.size());
                        if (list.size() > 0) {
                            setVisibleDates(View.VISIBLE,View.GONE);
                            start_date = fmt.format(list.get(0));
                            setStartDate(start_date);
                            end_date = fmt.format(list.get(list.size() - 1));
                            setEndDate(end_date);
                            if (GH.isInternetOn(mContext))
                            setDates();
                        }

                    }
                })
                .setNegativeButton(getString(R.string.str_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                       /* list = dialogView.getSelectedDates();
                        Log.e(TAG, "onClick: selected datesize::" + list.size());
                        if (list.size() > 0) {
                            llStartDate.setVisibility(View.GONE);
                            ttvStartDateEmpty.setVisibility(View.VISIBLE);

                            llEndDate.setVisibility(View.GONE);
                            ttvEndDateEmpty.setVisibility(View.VISIBLE);

                        }*/
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

                    }
                })
                .create();
        theDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Log.d(TAG, "onShow: fix the dimens!");
                dialogView.fixDialogDimens();
            }
        });
        theDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isCalenderOpen=false;
                /*list = dialogView.getSelectedDates();
                Log.e(TAG, "onClick: selected datesize::" + list.size());
                if (list.size() > 0) {
                    setVisibleDates(View.VISIBLE,View.GONE);
                    start_date = fmt.format(list.get(0));
                    end_date = fmt.format(list.get(list.size() - 1));
                    setStartDate(start_date);
                    setEndDate(end_date);

                }*/
            }
        });
        theDialog.show();


        dialogView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                Log.e(TAG, "onClick: Dates" + fmt.format(date));

            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

    }

    private void setDates() {
        if (!progressDialog.isShowing())
            progressDialog.show();
        Log.e(TAG, "setDates: " + RequestBodyBuilder.getInstanse().getRequestAddEventDate(userevent_id, start_date, end_date));
        Call<AddDateModel> call = RetrofitBuilder.getInstance().getRetrofit().AddDates(RequestBodyBuilder.getInstanse().getRequestAddEventDate(userevent_id, start_date, end_date));
        call.enqueue(new Callback<AddDateModel>() {
            @Override
            public void onResponse(Call<AddDateModel> call, Response<AddDateModel> response) {
                AddDateModel subCategoryModel = response.body();
                if (subCategoryModel.getStatus().equalsIgnoreCase(AppUtils.Success)) {

                    setResult(RESULT_OK);
                 //   finish();

                } else {

                }
                if(theDialog!=null) {
                    theDialog.dismiss();
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<AddDateModel> call, Throwable t) {
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
            if (GH.isInternetOn(mContext))
            ShareResponsibility(selected_id);
        }
    }

    private void ListResponsibility() {
        if (!progressDialog.isShowing())
            progressDialog.show();
        Call<ListResponsibilityModel> call = RetrofitBuilder.getInstance().getRetrofit().ListResposnsibility(RequestBodyBuilder.getInstanse().Request_ListResposibility(UserDetails.getInstance(mContext).getUser_id(), userevent_id));
        call.enqueue(new Callback<ListResponsibilityModel>() {
            @Override
            public void onResponse(Call<ListResponsibilityModel> call, Response<ListResponsibilityModel> response) {
                ListResponsibilityModel subCategoryModel = response.body();
                if (subCategoryModel.getStatus().equalsIgnoreCase(AppUtils.Success)) {
                    Log.e(TAG, "onResponse: list successfully");
                    responsableDetailList.clear();
                    sharecontact_list.clear();
                    responsableDetailList.addAll(subCategoryModel.getResponsableDetail());
                    for (int i = 0; i < responsableDetailList.size(); i++) {
                        sharecontact_list.add(responsableDetailList.get(i).getUserId());
                    }


                    empty_list();
                    adapter.notifyDataSetChanged();

                } else {
                    sharecontact_list.clear();
                }
                progressDialog.dismiss();
                if (!notification_id.equalsIgnoreCase("0") && notification_is_read.equalsIgnoreCase("0")) {
                    if (GH.isInternetOn(mContext))
                    ReadNotifications();
                }
            }

            @Override
            public void onFailure(Call<ListResponsibilityModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
                sharecontact_list.clear();
                if (!notification_id.equalsIgnoreCase("0") && notification_is_read.equalsIgnoreCase("0")) {
                    if (GH.isInternetOn(mContext))
                    ReadNotifications();
                }
            }
        });
    }


    private void ShareResponsibility(String id) {
        if (!progressDialog.isShowing())
            progressDialog.show();
        Call<AddResponsibilityModel> call = RetrofitBuilder.getInstance().getRetrofit().ShareResposnsibility(RequestBodyBuilder.getInstanse().Request_ShareResposibility(UserDetails.getInstance(mContext).getUser_id(), id, userevent_id, categoryID, subCategoryID));
        call.enqueue(new Callback<AddResponsibilityModel>() {
            @Override
            public void onResponse(Call<AddResponsibilityModel> call, Response<AddResponsibilityModel> response) {
                AddResponsibilityModel subCategoryModel = response.body();
                if (subCategoryModel.getStatus().equalsIgnoreCase(AppUtils.Success)) {
                    Log.e(TAG, "onResponse: shared successfully");
                    responsableDetailList.clear();
                    responsableDetailList.addAll(subCategoryModel.getSharedeventDetail());
                    adapter.notifyDataSetChanged();
                    empty_list();
                    setResult(RESULT_OK);
                    sharecontact_list.clear();
                    for (int i = 0; i < responsableDetailList.size(); i++) {
                        sharecontact_list.add(responsableDetailList.get(i).getUserId());
                    }


                } else {

                    AlertsUtils.getInstance().showOKAlert(mContext, subCategoryModel.getMsg());
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<AddResponsibilityModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }


    private void RemoveResponsibility(final int position) {
        if (!progressDialog.isShowing())
            progressDialog.show();
        Call<DeleteResponsibilityModel> call = RetrofitBuilder.getInstance().getRetrofit().RemoveResposnsibility(RequestBodyBuilder.getInstanse().Request_DeleteResposibility(UserDetails.getInstance(mContext).getUser_id(), responsableDetailList.get(position).getUserId(), userevent_id));
        call.enqueue(new Callback<DeleteResponsibilityModel>() {
            @Override
            public void onResponse(Call<DeleteResponsibilityModel> call, Response<DeleteResponsibilityModel> response) {
                DeleteResponsibilityModel subCategoryModel = response.body();
                if (subCategoryModel.getStatus().equalsIgnoreCase(AppUtils.Success)) {
                    Log.e(TAG, "onResponse: removed successfully");
                    responsableDetailList.remove(position);
                    sharecontact_list.clear();
                    for (int i = 0; i < responsableDetailList.size(); i++) {
                        sharecontact_list.add(responsableDetailList.get(i).getUserId());
                    }

                    adapter.notifyDataSetChanged();
                    empty_list();
                    setResult(RESULT_OK);
                } else {
                    AlertsUtils.getInstance().showOKAlert(mContext, subCategoryModel.getMsg());
                    adapter.notifyDataSetChanged();
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<DeleteResponsibilityModel> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }


    void setupListView() {
        adapter = new ResponsibilityListAdapter(mContext, responsableDetailList, new ResponsibilityListAdapter.OnMemberClick() {
            @Override
            public void onMemberClick(int position) {

            }

            @Override
            public void onDelete(final int position) {

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
                builder.setMessage(String.format(getString(R.string.do_you_want_to_remove_responsibility), responsableDetailList.get(position).getName()))
                        .setTitle(getString(R.string.remove_responsibility))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.str_no_caps), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setPositiveButton(getString(R.string.str_yes_caps), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (GH.isInternetOn(mContext))
                                RemoveResponsibility(position);
                                dialog.dismiss();

                            }
                        });
                android.support.v7.app.AlertDialog alert = builder.create();
                alert.show();


            }
        });

        rv_responsible_members.setAdapter(adapter);
        empty_list();
    }

    void empty_list() {
        if (responsableDetailList.size() <= 0) {
            empty_text.setVisibility(View.VISIBLE);
            rv_responsible_members.setVisibility(View.GONE);
        } else {
            empty_text.setVisibility(View.GONE);
            rv_responsible_members.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e(TAG, "onKeyDown: " + keyCode);
            if (ISFROMNOTIFICATION) {
                Intent intent = new Intent(mContext, SplashscreenActivity.class);
                startActivity(intent);
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

    void setVisibleDates(int llVisible,int emptyText){
        llStartDate.setVisibility(llVisible);
        llEndDate.setVisibility(llVisible);
        ttvStartDateEmpty.setVisibility(emptyText);
        ttvEndDateEmpty.setVisibility(emptyText);
    }
}

