
package com.nectarbits.baraati.Models.Resposibility.Add;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddResponsibilityModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sharedeventDetail")
    @Expose
    private List<SharedeventDetail> sharedeventDetail = new ArrayList<SharedeventDetail>();
    @SerializedName("msg")
    @Expose
    private String msg;

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
     *     The sharedeventDetail
     */
    public List<SharedeventDetail> getSharedeventDetail() {
        return sharedeventDetail;
    }

    /**
     * 
     * @param sharedeventDetail
     *     The sharedeventDetail
     */
    public void setSharedeventDetail(List<SharedeventDetail> sharedeventDetail) {
        this.sharedeventDetail = sharedeventDetail;
    }

    /**
     * 
     * @return
     *     The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 
     * @param msg
     *     The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
