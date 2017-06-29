
package com.nectarbits.baraati.Models.Notifications.List;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Detail")
    @Expose
    private List<Detail> detail = new ArrayList<Detail>();

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
     *     The detail
     */
    public List<Detail> getDetail() {
        return detail;
    }

    /**
     * 
     * @param detail
     *     The Detail
     */
    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

}
