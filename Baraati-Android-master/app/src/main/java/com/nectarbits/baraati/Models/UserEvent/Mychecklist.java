
package com.nectarbits.baraati.Models.UserEvent;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mychecklist {

    @SerializedName("SubCategory")
    @Expose
    private List<SubCategory> subCategory = new ArrayList<SubCategory>();

    /**
     * 
     * @return
     *     The subCategory
     */
    public List<SubCategory> getSubCategory() {
        return subCategory;
    }

    /**
     * 
     * @param subCategory
     *     The SubCategory
     */
    public void setSubCategory(List<SubCategory> subCategory) {
        this.subCategory = subCategory;
    }

}
