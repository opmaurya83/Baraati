
package com.nectarbits.baraati.Models.Event;

import java.util.ArrayList;
import java.util.List;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailResult implements ParentListItem{

    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Subcategory")
    @Expose
    private String subcategory;
    @SerializedName("subcategoryicon")
    @Expose
    private String subcategoryicon;
    @SerializedName("subcategorylabel")
    @Expose
    private String subcategorylabel;
    @SerializedName("EventList")
    @Expose
    private List<EventList> eventList = new ArrayList<EventList>();

    /**
     * 
     * @return
     *     The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The Category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The subcategory
     */
    public String getSubcategory() {
        return subcategory;
    }

    /**
     * 
     * @param subcategory
     *     The Subcategory
     */
    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    /**
     * 
     * @return
     *     The eventList
     */
    public List<EventList> getEventList() {
        return eventList;
    }

    /**
     * 
     * @param eventList
     *     The EventList
     */
    public void setEventList(List<EventList> eventList) {
        this.eventList = eventList;
    }

    @Override
    public List<?> getChildItemList() {
        return eventList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return true;
    }

    public String getSubcategoryicon() {
        return subcategoryicon;
    }

    public void setSubcategoryicon(String subcategoryicon) {
        this.subcategoryicon = subcategoryicon;
    }

    public String getSubcategorylabel() {
        return subcategorylabel;
    }

    public void setSubcategorylabel(String subcategorylabel) {
        this.subcategorylabel = subcategorylabel;
    }
}
