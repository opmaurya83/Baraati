
package com.nectarbits.baraati.Models.UserEvent;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategory_ {

    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("Adminuser")
    @Expose
    private String adminuser;
    @SerializedName("AdminuserId")
    @Expose
    private String adminuserId;
    @SerializedName("TaskStatus")
    @Expose
    private String taskStatus;
    @SerializedName("Subcategory")
    @Expose
    private String subcategory;
    @SerializedName("subcategory_id")
    @Expose
    private String subcategoryId;
    @SerializedName("usersubCategoryId")
    @Expose
    private String usersubCategoryId;
    @SerializedName("Events")
    @Expose
    private List<Event> events = new ArrayList<Event>();


    @SerializedName("subcategorylabel")
    @Expose
    private String subcategorylabel;
    @SerializedName("subcategoryicon")
    @Expose
    private String subcategoryicon;


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
     *     The categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * 
     * @param categoryId
     *     The categoryId
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 
     * @return
     *     The adminuser
     */
    public String getAdminuser() {
        return adminuser;
    }

    /**
     * 
     * @param adminuser
     *     The Adminuser
     */
    public void setAdminuser(String adminuser) {
        this.adminuser = adminuser;
    }

    /**
     * 
     * @return
     *     The adminuserId
     */
    public String getAdminuserId() {
        return adminuserId;
    }

    /**
     * 
     * @param adminuserId
     *     The AdminuserId
     */
    public void setAdminuserId(String adminuserId) {
        this.adminuserId = adminuserId;
    }

    /**
     * 
     * @return
     *     The taskStatus
     */
    public String getTaskStatus() {
        return taskStatus;
    }

    /**
     * 
     * @param taskStatus
     *     The TaskStatus
     */
    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
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
     *     The subcategoryId
     */
    public String getSubcategoryId() {
        return subcategoryId;
    }

    /**
     * 
     * @param subcategoryId
     *     The subcategory_id
     */
    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
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
     *     The events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * 
     * @param events
     *     The Events
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getSubcategorylabel() {
        return subcategorylabel;
    }

    public void setSubcategorylabel(String subcategorylabel) {
        this.subcategorylabel = subcategorylabel;
    }

    public String getSubcategoryicon() {
        return subcategoryicon;
    }

    public void setSubcategoryicon(String subcategoryicon) {
        this.subcategoryicon = subcategoryicon;
    }
}
