
package com.nectarbits.baraati.Models.EventType;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventTypeModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("DetailResult")
    @Expose
    private List<DetailResult> detailResult = new ArrayList<DetailResult>();

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
     *     The detailResult
     */
    public List<DetailResult> getDetailResult() {
        return detailResult;
    }

    /**
     * 
     * @param detailResult
     *     The DetailResult
     */
    public void setDetailResult(List<DetailResult> detailResult) {
        this.detailResult = detailResult;
    }

}
