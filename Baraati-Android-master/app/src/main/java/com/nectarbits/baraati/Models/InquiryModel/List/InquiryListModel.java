
package com.nectarbits.baraati.Models.InquiryModel.List;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InquiryListModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("myInqiry")
    @Expose
    private List<MyInqiry> myInqiry = new ArrayList<MyInqiry>();
    @SerializedName("assignInqiry")
    @Expose
    private List<AssignInqiry> assignInqiry = new ArrayList<AssignInqiry>();

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
     *     The myInqiry
     */
    public List<MyInqiry> getMyInqiry() {
        return myInqiry;
    }

    /**
     * 
     * @param myInqiry
     *     The myInqiry
     */
    public void setMyInqiry(List<MyInqiry> myInqiry) {
        this.myInqiry = myInqiry;
    }

    /**
     * 
     * @return
     *     The assignInqiry
     */
    public List<AssignInqiry> getAssignInqiry() {
        return assignInqiry;
    }

    /**
     * 
     * @param assignInqiry
     *     The assignInqiry
     */
    public void setAssignInqiry(List<AssignInqiry> assignInqiry) {
        this.assignInqiry = assignInqiry;
    }

}
