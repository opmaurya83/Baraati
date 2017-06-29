
package com.nectarbits.baraati.Models.AddtoCart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddtoCartModel {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("information")
    @Expose
    private String information;
    @SerializedName("status")
    @Expose
    private String status;

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

    /**
     * 
     * @return
     *     The information
     */
    public String getInformation() {
        return information;
    }

    /**
     * 
     * @param information
     *     The information
     */
    public void setInformation(String information) {
        this.information = information;
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
