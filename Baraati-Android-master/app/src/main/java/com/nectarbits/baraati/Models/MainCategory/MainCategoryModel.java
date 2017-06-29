
package com.nectarbits.baraati.Models.MainCategory;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainCategoryModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("CaregoryDetail")
    @Expose
    private List<CaregoryDetail> caregoryDetail = new ArrayList<CaregoryDetail>();

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
     *     The caregoryDetail
     */
    public List<CaregoryDetail> getCaregoryDetail() {
        return caregoryDetail;
    }

    /**
     * 
     * @param caregoryDetail
     *     The CaregoryDetail
     */
    public void setCaregoryDetail(List<CaregoryDetail> caregoryDetail) {
        this.caregoryDetail = caregoryDetail;
    }

}
