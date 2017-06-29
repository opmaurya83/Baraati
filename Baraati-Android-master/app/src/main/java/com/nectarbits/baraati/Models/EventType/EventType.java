
package com.nectarbits.baraati.Models.EventType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventType {

    @SerializedName("eventTypeEvent_id")
    @Expose
    private String eventTypeEventId;
    @SerializedName("eventType")
    @Expose
    private String eventType;

    @SerializedName("CategoryId")
    @Expose
    private String CategoryId;

    @SerializedName("SubcategoryId")
    @Expose
    private String SubcategoryId;

    @SerializedName("eventId")
    @Expose
    private String eventId;
    @SerializedName("eventtypelabel")
    @Expose
    private String eventtypelabel;
    @SerializedName("eventtypeicon")
    @Expose
    private String eventtypeicon;

    private String event_categoryId;
    Boolean isSelect=false;
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
     *     The eventTypeEvent_id
     */
    public void setEventTypeEventId(String eventTypeEventId) {
        this.eventTypeEventId = eventTypeEventId;
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

    public Boolean getSelect() {
        return isSelect;
    }

    public void setSelect(Boolean select) {
        isSelect = select;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getSubcategoryId() {
        return SubcategoryId;
    }

    public void setSubcategoryId(String subcategoryId) {
        SubcategoryId = subcategoryId;
    }

    public String getEvent_categoryId() {
        return event_categoryId;
    }

    public void setEvent_categoryId(String event_categoryId) {
        this.event_categoryId = event_categoryId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventtypelabel() {
        return eventtypelabel;
    }

    public void setEventtypelabel(String eventtypelabel) {
        this.eventtypelabel = eventtypelabel;
    }

    public String getEventtypeicon() {
        return eventtypeicon;
    }

    public void setEventtypeicon(String eventtypeicon) {
        this.eventtypeicon = eventtypeicon;
    }
}
