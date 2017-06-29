
package com.nectarbits.baraati.Models.Vendor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class VendorList {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vendorDetail")
    @Expose
    private List<VendorDetail> vendorDetail = new ArrayList<VendorDetail>();

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
     *     The vendorDetail
     */
    public List<VendorDetail> getVendorDetail() {
        return vendorDetail;
    }

    /**
     * 
     * @param vendorDetail
     *     The vendorDetail
     */
    public void setVendorDetail(List<VendorDetail> vendorDetail) {
        this.vendorDetail = vendorDetail;
    }

}
