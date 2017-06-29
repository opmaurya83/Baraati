
package com.nectarbits.baraati.Models.Vendor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class VenderInfo {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vendorDetail")
    @Expose
    private List<VendorDetailInfo> vendorDetail = new ArrayList<VendorDetailInfo>();

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
    public List<VendorDetailInfo> getVendorDetail() {
        return vendorDetail;
    }

    /**
     * 
     * @param vendorDetail
     *     The vendorDetail
     */
    public void setVendorDetail(List<VendorDetailInfo> vendorDetail) {
        this.vendorDetail = vendorDetail;
    }

}
