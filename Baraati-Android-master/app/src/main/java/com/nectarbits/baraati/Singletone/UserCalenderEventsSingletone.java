package com.nectarbits.baraati.Singletone;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 31/8/16.
 */
public class UserCalenderEventsSingletone {
    public List<Event> getUserEventlist() {
        return UserEventlist;
    }

    public void setUserEventlist(List<Event> userEventlist) {
        UserEventlist = userEventlist;
    }

    List<Event> UserEventlist=new ArrayList<>();
    private static UserCalenderEventsSingletone ourInstance = new UserCalenderEventsSingletone();

    public static UserCalenderEventsSingletone getInstance() {
        return ourInstance;
    }

    private UserCalenderEventsSingletone() {
    }
}
