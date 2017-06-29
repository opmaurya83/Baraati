package com.nectarbits.baraati.Singletone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 20/8/16.
 */
public class AddCheckListSigletone {
    public String categoryID="";
    public String categoryName="";
    public String subcategoryID="";
    public String eventsID="";
    public String eventTypeID="";
    public List<String> selected_event=new ArrayList<>();
    public List<String> selected_eventTypes=new ArrayList<>();
    public List<String> selected_subCat=new ArrayList<>();
    private static AddCheckListSigletone ourInstance = new AddCheckListSigletone();

    public static AddCheckListSigletone getInstance() {
        return ourInstance;
    }

    private AddCheckListSigletone() {
    }

    public void reset()
    {
        categoryID="";
        categoryName="";
        subcategoryID="";
        eventsID="";
        eventTypeID="";
        selected_event.clear();
        //selected_eventTypes.clear();
        //selected_subCat.clear();
    }
    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getSubcategoryID() {
        return subcategoryID;
    }

    public void setSubcategoryID(String subcategoryID) {
        this.subcategoryID = subcategoryID;
    }

    public String getEventsID() {
        return eventsID;
    }

    public void setEventsID(String eventsID) {
        this.eventsID = eventsID;
    }

    public String getEventTypeID() {
        return eventTypeID;
    }

    public void setEventTypeID(String eventTypeID) {
        this.eventTypeID = eventTypeID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<String> getSelected_event() {
        return selected_event;
    }

    public void setSelected_event(List<String> selected_event) {
        this.selected_event = selected_event;
    }

    public List<String> getSelected_eventTypes() {
        return selected_eventTypes;
    }

    public void setSelected_eventTypes(List<String> selected_eventTypes) {
        this.selected_eventTypes = selected_eventTypes;
    }

    public List<String> getSelected_subCat() {
        return selected_subCat;
    }

    public void setSelected_subCat(List<String> selected_subCat) {
        this.selected_subCat = selected_subCat;
    }
}
