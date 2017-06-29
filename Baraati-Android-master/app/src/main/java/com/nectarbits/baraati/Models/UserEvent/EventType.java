
package com.nectarbits.baraati.Models.UserEvent;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventType {

    @SerializedName("eventTypeId")
    @Expose
    private String eventTypeId;
    @SerializedName("eventTypeEventId")
    @Expose
    private String eventTypeEventId;
    @SerializedName("userEventTypeEventsId")
    @Expose
    private String userEventTypeEventsId;
    @SerializedName("usersubCategoryId")
    @Expose
    private String usersubCategoryId;
    @SerializedName("userCategoryId")
    @Expose
    private String userCategoryId;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;

    @SerializedName("eventType")
    @Expose
    private String eventType;

    @SerializedName("isDone")
    @Expose
    private String isDone;

    @SerializedName("isDoneName")
    @Expose
    private String isDoneName;

    @SerializedName("shared")
    @Expose
    private String shared;

    @SerializedName("eventicon")
    @Expose
    private String eventicon;
    @SerializedName("eventlabel")
    @Expose
    private String eventlabel;

    @SerializedName("eventtypeicon")
    @Expose
    private String eventtypeicon;

    @SerializedName("eventtypelabel")
    @Expose
    private String eventtypelabel;


    Boolean Last=false;
    String AdminuserId="0";
    /**
     * 
     * @return
     *     The eventTypeId
     */
    public String getEventTypeId() {
        return eventTypeId;
    }

    /**
     * 
     * @param eventTypeId
     *     The eventTypeId
     */
    public void setEventTypeId(String eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    /**
     * 
     * @return
     *     The eventTypeEventId
     */
    public String getEventTypeEventId() {
        return eventTypeEventId;
    }

    /**
     * 
     * @param eventTypeEventId
     *     The eventTypeEventId
     */
    public void setEventTypeEventId(String eventTypeEventId) {
        this.eventTypeEventId = eventTypeEventId;
    }

    /**
     * 
     * @return
     *     The userEventTypeEventsId
     */
    public String getUserEventTypeEventsId() {
        return userEventTypeEventsId;
    }

    /**
     * 
     * @param userEventTypeEventsId
     *     The userEventTypeEventsId
     */
    public void setUserEventTypeEventsId(String userEventTypeEventsId) {
        this.userEventTypeEventsId = userEventTypeEventsId;
    }

    /**
     * 
     * @return
     *     The usersubCategoryId
     */
    public String getUsersubCategoryId() {
        return usersubCategoryId;
    }

    /**
     * 
     * @param usersubCategoryId
     *     The usersubCategoryId
     */
    public void setUsersubCategoryId(String usersubCategoryId) {
        this.usersubCategoryId = usersubCategoryId;
    }

    /**
     * 
     * @return
     *     The userCategoryId
     */
    public String getUserCategoryId() {
        return userCategoryId;
    }

    /**
     * 
     * @param userCategoryId
     *     The userCategoryId
     */
    public void setUserCategoryId(String userCategoryId) {
        this.userCategoryId = userCategoryId;
    }

    /**
     * 
     * @return
     *     The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 
     * @param startDate
     *     The startDate
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * @return
     *     The endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 
     * @param endDate
     *     The endDate
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 
     * @return
     *     The eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * 
     * @param eventType
     *     The eventType
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public Boolean getLast() {
        return Last;
    }

    public void setLast(Boolean last) {
        Last = last;
    }

    public String getAdminuserId() {
        return AdminuserId;
    }

    public void setAdminuserId(String adminuserId) {
        AdminuserId = adminuserId;
    }

    public String getIsDone() {
        return isDone;
    }

    public void setIsDone(String isDone) {
        this.isDone = isDone;
    }

    public String getShared() {
        return shared;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }

    public String getIsDoneName() {
        return isDoneName;
    }

    public void setIsDoneName(String isDoneName) {
        this.isDoneName = isDoneName;
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

    public String getEventtypeicon() {
        return eventtypeicon;
    }

    public void setEventtypeicon(String eventtypeicon) {
        this.eventtypeicon = eventtypeicon;
    }

    public String getEventtypelabel() {
        return eventtypelabel;
    }

    public void setEventtypelabel(String eventtypelabel) {
        this.eventtypelabel = eventtypelabel;
    }
}
