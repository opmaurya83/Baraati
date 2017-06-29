
package com.nectarbits.baraati.Models.UserEvent;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserEventModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("UnReadNotification")
    @Expose
    private String UnReadNotification;
    @SerializedName("mychecklist")
    @Expose
    private List<Mychecklist> mychecklist = new ArrayList<Mychecklist>();
    @SerializedName("AssignChecklist")
    @Expose
    private List<AssignChecklist> assignChecklist = new ArrayList<AssignChecklist>();

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
     *     The mychecklist
     */
    public List<Mychecklist> getMychecklist() {
        return mychecklist;
    }

    /**
     * 
     * @param mychecklist
     *     The mychecklist
     */
    public void setMychecklist(List<Mychecklist> mychecklist) {
        this.mychecklist = mychecklist;
    }

    /**
     * 
     * @return
     *     The assignChecklist
     */
    public List<AssignChecklist> getAssignChecklist() {
        return assignChecklist;
    }

    /**
     * 
     * @param assignChecklist
     *     The AssignChecklist
     */
    public void setAssignChecklist(List<AssignChecklist> assignChecklist) {
        this.assignChecklist = assignChecklist;
    }

    public String getUnReadNotification() {
        return UnReadNotification;
    }

    public void setUnReadNotification(String unReadNotification) {
        UnReadNotification = unReadNotification;
    }
}
