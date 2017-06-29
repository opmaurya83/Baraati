
package com.nectarbits.baraati.Models.VendorDetail;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorDetailModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vendorDetail")
    @Expose
    private List<VendorDetail> vendorDetail = new ArrayList<VendorDetail>();
    @SerializedName("vendor_allAttribute")
    @Expose
    private List<VendorAllAttribute> vendorAllAttribute = new ArrayList<VendorAllAttribute>();

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

    /**
     * 
     * @return
     *     The vendorAllAttribute
     */
    public List<VendorAllAttribute> getVendorAllAttribute() {
        return vendorAllAttribute;
    }

    /**
     * 
     * @param vendorAllAttribute
     *     The vendor_allAttribute
     */
    public void setVendorAllAttribute(List<VendorAllAttribute> vendorAllAttribute) {
        this.vendorAllAttribute = vendorAllAttribute;
    }

}
