
package com.nectarbits.baraati.Models.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OTPModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("otp")
    @Expose
    private String otp;

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
     *     The otp
     */
    public String getOtp() {
        return otp;
    }

    /**
     * 
     * @param otp
     *     The otp
     */
    public void setOtp(String otp) {
        this.otp = otp;
    }


}
