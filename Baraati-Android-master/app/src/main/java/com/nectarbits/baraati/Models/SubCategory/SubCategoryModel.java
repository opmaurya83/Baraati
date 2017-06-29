
package com.nectarbits.baraati.Models.SubCategory;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubCategoryModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("subCaregoryDetail")
    @Expose
    private List<SubCaregoryDetail> subCaregoryDetail = new ArrayList<SubCaregoryDetail>();

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The subCaregoryDetail
     */
    public List<SubCaregoryDetail> getSubCaregoryDetail() {
        return subCaregoryDetail;
    }

    /**
     * 
     * @param subCaregoryDetail
     *     The subCaregoryDetail
     */
    public void setSubCaregoryDetail(List<SubCaregoryDetail> subCaregoryDetail) {
        this.subCaregoryDetail = subCaregoryDetail;
    }

}
