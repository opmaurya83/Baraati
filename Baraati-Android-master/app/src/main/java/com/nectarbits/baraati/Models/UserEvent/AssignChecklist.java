
package com.nectarbits.baraati.Models.UserEvent;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignChecklist {

    @SerializedName("SubCategory")
    @Expose
    private List<SubCategory_> subCategory = new ArrayList<SubCategory_>();

    /**
     * 
     * @return
     *     The subCategory
     */
    public List<SubCategory_> getSubCategory() {
        return subCategory;
    }

    /**
     * 
     * @param subCategory
     *     The SubCategory
     */
    public void setSubCategory(List<SubCategory_> subCategory) {
        this.subCategory = subCategory;
    }

}
