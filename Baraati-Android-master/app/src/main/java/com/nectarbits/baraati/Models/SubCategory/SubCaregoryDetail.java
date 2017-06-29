
package com.nectarbits.baraati.Models.SubCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCaregoryDetail {

    @SerializedName("categoryName")
    @Expose
    private String categoryName;
    @SerializedName("subcategoryName")
    @Expose
    private String subcategoryName;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("subcategoryId")
    @Expose
    private String subcategoryId;

    @SerializedName("subcategoryicon")
    @Expose
    private String subcategoryicon;
    @SerializedName("subcategorylabel")
    @Expose
    private String subcategorylabel;

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
     *     The subcategoryName
     */
    public String getSubcategoryName() {
        return subcategoryName;
    }

    /**
     * 
     * @param subcategoryName
     *     The subcategoryName
     */
    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
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
     *     The subcategoryId
     */
    public String getSubcategoryId() {
        return subcategoryId;
    }

    /**
     * 
     * @param subcategoryId
     *     The subcategoryId
     */
    public void setSubcategoryId(String subcategoryId) {
        this.subcategoryId = subcategoryId;
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
