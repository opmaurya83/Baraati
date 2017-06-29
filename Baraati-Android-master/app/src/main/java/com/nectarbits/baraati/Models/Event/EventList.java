
package com.nectarbits.baraati.Models.Event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventList {

    @SerializedName("Event")
    @Expose
    private String event;
    @SerializedName("eventId")
    @Expose
    private String eventId;
    @SerializedName("event_categoryId")
    @Expose
    private String eventCategoryId;
    @SerializedName("eventlabel")
    @Expose
    private String eventlabel;
    @SerializedName("eventicon")
    @Expose
    private String eventicon;


    Boolean isSelected=false;
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

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
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
