
package com.nectarbits.baraati.Models.UserEvent;

import java.util.ArrayList;
import java.util.List;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event implements ParentListItem{

    @SerializedName("userEventId")
    @Expose
    private String userEventId;
    @SerializedName("eventId")
    @Expose
    private String eventId;
    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("eventCategoryId")
    @Expose
    private String eventCategoryId;
    @SerializedName("userEventCategoryId")
    @Expose
    private String userEventCategoryId;
    @SerializedName("EventType")
    @Expose
    private List<EventType> eventType = new ArrayList<EventType>();

    @SerializedName("eventlabel")
    @Expose
    private String eventlabel;

    @SerializedName("eventicon")
    @Expose
    private String eventicon;

    String CategoryID,Sub_categoryID;

    /**
     * 
     * @return
     *     The userEventId
     */
    public String getUserEventId() {
        return userEventId;
    }

    /**
     * 
     * @param userEventId
     *     The userEventId
     */
    public void setUserEventId(String userEventId) {
        this.userEventId = userEventId;
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
     *     The event
     */
    public String getEvent() {
        return event;
    }

    /**
     * 
     * @param event
     *     The event
     */
    public void setEvent(String event) {
        this.event = event;
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
     *     The eventCategoryId
     */
    public void setEventCategoryId(String eventCategoryId) {
        this.eventCategoryId = eventCategoryId;
    }

    /**
     * 
     * @return
     *     The userEventCategoryId
     */
    public String getUserEventCategoryId() {
        return userEventCategoryId;
    }

    /**
     * 
     * @param userEventCategoryId
     *     The userEventCategoryId
     */
    public void setUserEventCategoryId(String userEventCategoryId) {
        this.userEventCategoryId = userEventCategoryId;
    }

    /**
     * 
     * @return
     *     The eventType
     */
    public List<EventType> getEventType() {
        return eventType;
    }

    /**
     * 
     * @param eventType
     *     The EventType
     */
    public void setEventType(List<EventType> eventType) {
        this.eventType = eventType;
    }

    public String getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }

    public String getSub_categoryID() {
        return Sub_categoryID;
    }

    public void setSub_categoryID(String sub_categoryID) {
        Sub_categoryID = sub_categoryID;
    }

    @Override
    public List<?> getChildItemList() {
        return eventType;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return true;
    }

    public String getEventlabel() {
        return eventlabel;
    }

    public void setEventlabel(String eventlabel) {
        this.eventlabel = eventlabel;
    }

    public String getEventicon() {
        return eventicon;
    }

    public void setEventicon(String eventicon) {
        this.eventicon = eventicon;
    }
}
