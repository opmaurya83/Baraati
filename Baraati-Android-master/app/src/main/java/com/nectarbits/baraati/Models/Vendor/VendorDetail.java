
package com.nectarbits.baraati.Models.Vendor;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class VendorDetail {

    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("vendorName")
    @Expose
    private String vendorName;
    @SerializedName("vendorId")
    @Expose
    private String vendorId;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("Product_Description")
    @Expose
    private String productDescription;

    /**
     * 
     * @return
     *     The productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * 
     * @param productId
     *     The productId
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * 
     * @return
     *     The vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * 
     * @param vendorName
     *     The vendorName
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * 
     * @return
     *     The vendorId
     */
    public String getVendorId() {
        return vendorId;
    }

    /**
     * 
     * @param vendorId
     *     The vendorId
     */
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * 
     * @return
     *     The pname
     */
    public String getPname() {
        return pname;
    }

    /**
     * 
     * @param pname
     *     The pname
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * 
     * @return
     *     The productDescription
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * 
     * @param productDescription
     *     The Product_Description
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

}
