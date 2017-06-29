
package com.nectarbits.baraati.Models.EventType;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EventList implements ParentListItem {

    @SerializedName("Event")
    @Expose
    private String event;
    @SerializedName("eventId")
    @Expose
    private String eventId;
    @SerializedName("event_categoryId")
    @Expose
    private String eventCategoryId;
    @SerializedName("EventTypes")
    @Expose
    private List<EventType> eventTypes = new ArrayList<EventType>();


    @SerializedName("Subcategory")
    @Expose
    private String Subcategory;
    @SerializedName("eventicon")
    @Expose
    private String eventicon;
    @SerializedName("eventlabel")
    @Expose
    private String eventlabel;

    /**
     * 
     * @return
     *     The event
     */
    public String getEvent() {
        return event;
    }

    /**
     * 
     * @param event
     *     The Event
     */
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * 
     * @return
     *     The eventId
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * 
     * @param eventId
     *     The eventId
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * 
     * @return
     *     The eventCategoryId
     */
    public String getEventCategoryId() {
        return eventCategoryId;
    }

    /**
     * 
     * @param eventCategoryId
     *     The event_categoryId
     */
    public void setEventCategoryId(String eventCategoryId) {
        this.eventCategoryId = eventCategoryId;
    }

    /**
     * 
     * @return
     *     The eventTypes
     */
    public List<EventType> getEventTypes() {
        return eventTypes;
    }

    /**
     * 
     * @param eventTypes
     *     The EventTypes
     */
    public void setEventTypes(List<EventType> eventTypes) {
        this.eventTypes = eventTypes;
    }

    @Override
    public List<?> getChildItemList() {
        Log.e("TAG", "getChildItemList: "+  this.eventTypes.size());
        return  this.eventTypes;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return true;
    }

    public String getSubcategory() {
        return Subcategory;
    }

    public void setSubcategory(String subcategory) {
        Subcategory = subcategory;
    }

    public String getEventicon() {
        return eventicon;
    }

    public void setEventicon(String eventicon) {
        this.eventicon = eventicon;
    }

    public String getEventlabel() {
        return eventlabel;
    }

    public void setEventlabel(String eventlabel) {
        this.eventlabel = eventlabel;
    }
}
