
package com.nectarbits.baraati.Models.MainCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaregoryDetail {

    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("categorylabel")
    @Expose
    private String categorylabel;
    @SerializedName("categoryicon")
    @Expose
    private String categoryIcon;


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
     *     The categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * @param categoryName
     *     The categoryName
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    public String getCategorylabel() {
        return categorylabel;
    }

    public void setCategorylabel(String categorylabel) {
        this.categorylabel = categorylabel;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }
}
