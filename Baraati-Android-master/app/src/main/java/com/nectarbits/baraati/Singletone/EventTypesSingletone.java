package com.nectarbits.baraati.Singletone;

import com.nectarbits.baraati.Models.UserEvent.EventType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/9/16.
 */
public class EventTypesSingletone {

    public List<EventType> getEventTypeList() {
        return eventTypeList;
    }

    public void setEventTypeList(List<EventType> eventTypeList) {
        this.eventTypeList = eventTypeList;
    }

    public List<EventType> eventTypeList=new ArrayList<>();

    public List<EventType> eventTypeList_assigned=new ArrayList<>();

    private static EventTypesSingletone ourInstance = new EventTypesSingletone();

    public static EventTypesSingletone getInstance() {
        return ourInstance;
    }

    private EventTypesSingletone() {
    }

    public List<EventType> getEventTypeList_assigned() {
        return eventTypeList_assigned;
    }

    public void setEventTypeList_assigned(List<EventType> eventTypeList_assigned) {
        this.eventTypeList_assigned = eventTypeList_assigned;
    }

    public static EventTypesSingletone getOurInstance() {
        return ourInstance;
    }

    public static void setOurInstance(EventTypesSingletone ourInstance) {
        EventTypesSingletone.ourInstance = ourInstance;
    }
}
