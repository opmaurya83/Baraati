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
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.nectarbits.baraati.Adapters.UserCalendarEventsAdapter;
import com.nectarbits.baraati.Interface.OnCalenderEventSelect;
import com.nectarbits.baraati.Models.UserEvent.EventType;
import com.nectarbits.baraati.Singletone.UserCalenderEventsSingletone;
import com.nectarbits.baraati.generalHelper.AppUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CalendarActivity extends AppCompatActivity {

    @Bind(R.id.compactcalendar_view)
    CompactCalendarView compactCalendarView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.txtMonthYear)
    TextView txtMonthYear;
    @Bind(R.id.rcUserEvents)
    RecyclerView rcUserEvents;
    Context mContext;
    SimpleDateFormat fmt = new SimpleDateFormat("MMM-yyyy");
    UserCalendarEventsAdapter adapter;
    SimpleDateFormat fmt_1 = new SimpleDateFormat("yyyy-MM-dd");
    List<Event> arrayList=new ArrayList<>();
    Date date;
    Boolean isDateChanges=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        mContext=this;
        ButterKnife.bind(this);
        implementToolbar();
        date=new Date();

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            if (bundle.containsKey(AppUtils.ARG_DATE_FROM) && bundle.containsKey(AppUtils.ARG_DATE_FROM)) {
                if (getIntent().getExtras().getString(AppUtils.ARG_DATE_FROM).toString().length() > 0 && !getIntent().getExtras().getString(AppUtils.ARG_DATE_FROM).toString().equalsIgnoreCase("0000-00-00")) {
                    try {
                        date.setTime(fmt.parse(getIntent().getExtras().getString(AppUtils.ARG_DATE_FROM).toString()).getTime());
                    } catch (Exception ex) {

                    }


                }
            }
        }

       /* if(getIntent().getExtras().getString(AppUtils.ARG_DATE_TO).toString().length()>0 && !getIntent().getExtras().getString(AppUtils.ARG_DATE_TO).toString().equalsIgnoreCase("0000-00-00"))
        {
            txtTo.setText(getIntent().getExtras().getString(AppUtils.ARG_DATE_TO));
        }*/

        txtMonthYear.setText(fmt.format(date));

        compactCalendarView.addEvents(UserCalenderEventsSingletone.getInstance().getUserEventlist());

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                arrayList=compactCalendarView.getEvents(dateClicked);
                adapter=new UserCalendarEventsAdapter(mContext, arrayList, new OnCalenderEventSelect() {
                    @Override
                    public void onCalenderEventSelect(int position) {
                      /*  Intent intent = new Intent(mContext, EventDateActivity.class);
                        intent.putExtra(AppUtils.ARG_EVENTTYPE_ID,((EventType)arrayList.get(position).getData()).getUserEventTypeEventsId());
                        intent.putExtra(AppUtils.ARG_EVENTTYPE_NAME,((EventType)arrayList.get(position).getData()).getEventType());
                        intent.putExtra(AppUtils.ARG_DATE_FROM, ((EventType)arrayList.get(position).getData()).getStartDate());
                        intent.putExtra(AppUtils.ARG_DATE_TO, ((EventType)arrayList.get(position).getData()).getEndDate());
                        startActivityForResult(intent, AppUtils.REQUEST_UPDATE_USEREVENT);*/
                    }
                });
                rcUserEvents.setLayoutManager(new LinearLayoutManager(mContext));
                rcUserEvents.setItemAnimator(new DefaultItemAnimator());
                rcUserEvents.setAdapter(adapter);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                txtMonthYear.setText(fmt.format(firstDayOfNewMonth));
            }
        });

        adapter=new UserCalendarEventsAdapter(mContext, arrayList, new OnCalenderEventSelect() {
            @Override
            public void onCalenderEventSelect(int position) {

            /*    Intent intent = new Intent(mContext, EventDateActivity.class);
                intent.putExtra(AppUtils.ARG_EVENTTYPE_ID,((EventType)arrayList.get(position).getData()).getUserEventTypeEventsId());
                intent.putExtra(AppUtils.ARG_EVENTTYPE_NAME,((EventType)arrayList.get(position).getData()).getEventType());
                intent.putExtra(AppUtils.ARG_DATE_FROM, ((EventType)arrayList.get(position).getData()).getStartDate());
                intent.putExtra(AppUtils.ARG_DATE_TO, ((EventType)arrayList.get(position).getData()).getEndDate());
                startActivityForResult(intent, AppUtils.REQUEST_UPDATE_USEREVENT);*/
            }
        });
        arrayList=compactCalendarView.getEvents(date);
        rcUserEvents.setLayoutManager(new LinearLayoutManager(mContext));
        rcUserEvents.setItemAnimator(new DefaultItemAnimator());
        rcUserEvents.setAdapter(adapter);
    }
    private void implementToolbar() {
        toolbar.setTitle(getString(R.string.str_events_calender));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.selector_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDateChanges) {
                    setResult(RESULT_OK);
                }
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode== AppUtils.REQUEST_UPDATE_USEREVENT){
                isDateChanges=true;
                compactCalendarView.removeAllEvents();
                compactCalendarView.addEvents(UserCalenderEventsSingletone.getInstance().getUserEventlist());
                arrayList=compactCalendarView.getEvents(date);

                adapter.notifyDataSetChanged();
            }
        }
    }
}
