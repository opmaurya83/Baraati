
package com.nectarbits.baraati.Models.Register.UserCheck;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserCheckModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("emailMessage")
    @Expose
    private String emailMessage;
    @SerializedName("emailstatus")
    @Expose
    private String emailstatus;
    @SerializedName("phoneMessage")
    @Expose
    private String phoneMessage;
    @SerializedName("phonestatus")
    @Expose
    private String phonestatus;

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
     *     The emailMessage
     */
    public String getEmailMessage() {
        return emailMessage;
    }

    /**
     * 
     * @param emailMessage
     *     The emailMessage
     */
    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }

    /**
     * 
     * @return
     *     The emailstatus
     */
    public String getEmailstatus() {
        return emailstatus;
    }

    /**
     * 
     * @param emailstatus
     *     The emailstatus
     */
    public void setEmailstatus(String emailstatus) {
        this.emailstatus = emailstatus;
    }

    /**
     * 
     * @return
     *     The phoneMessage
     */
    public String getPhoneMessage() {
        return phoneMessage;
    }

    /**
     * 
     * @param phoneMessage
     *     The phoneMessage
     */
    public void setPhoneMessage(String phoneMessage) {
        this.phoneMessage = phoneMessage;
    }

    /**
     * 
     * @return
     *     The phonestatus
     */
    public String getPhonestatus() {
        return phonestatus;
    }

    /**
     * 
     * @param phonestatus
     *     The phonestatus
     */
    public void setPhonestatus(String phonestatus) {
        this.phonestatus = phonestatus;
    }

}
