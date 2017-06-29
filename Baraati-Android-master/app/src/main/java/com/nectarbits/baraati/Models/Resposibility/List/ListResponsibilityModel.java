
package com.nectarbits.baraati.Models.Resposibility.List;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nectarbits.baraati.Models.Resposibility.Add.SharedeventDetail;

public class ListResponsibilityModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("responsableDetail")
    @Expose
    private List<SharedeventDetail> responsableDetail = new ArrayList<SharedeventDetail>();

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
     *     The responsableDetail
     */
    public List<SharedeventDetail> getResponsableDetail() {
        return responsableDetail;
    }

    /**
     * 
     * @param responsableDetail
     *     The responsableDetail
     */
    public void setResponsableDetail(List<SharedeventDetail> responsableDetail) {
        this.responsableDetail = responsableDetail;
    }

}
