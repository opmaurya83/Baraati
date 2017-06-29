
package com.nectarbits.baraati.Models.OrderList.List;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderListModel {

    @SerializedName("ownOrder")
    @Expose
    private List<OwnOrder> ownOrder = new ArrayList<OwnOrder>();
    @SerializedName("assigneeOrder")
    @Expose
    private List<AssigneeOrder> assigneeOrder = new ArrayList<AssigneeOrder>();
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * 
     * @return
     *     The ownOrder
     */
    public List<OwnOrder> getOwnOrder() {
        return ownOrder;
    }

    /**
     * 
     * @param ownOrder
     *     The ownOrder
     */
    public void setOwnOrder(List<OwnOrder> ownOrder) {
        this.ownOrder = ownOrder;
    }

    /**
     * 
     * @return
     *     The assigneeOrder
     */
    public List<AssigneeOrder> getAssigneeOrder() {
        return assigneeOrder;
    }

    /**
     * 
     * @param assigneeOrder
     *     The assigneeOrder
     */
    public void setAssigneeOrder(List<AssigneeOrder> assigneeOrder) {
        this.assigneeOrder = assigneeOrder;
    }

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

}
