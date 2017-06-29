
package com.nectarbits.baraati.Models.VendorBazaar;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorBazaarModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vendorDetail")
    @Expose
    private List<VendorDetail> vendorDetail = new ArrayList<VendorDetail>();
    @SerializedName("vendor_allAttribute")
    @Expose
    private List<VendorAllAttribute> vendorAllAttribute = new ArrayList<VendorAllAttribute>();
    @SerializedName("productDetail")
    @Expose
    private List<ProductDetail> productDetail = new ArrayList<ProductDetail>();
    @SerializedName("product_allAttribute")
    @Expose
    private List<ProductAllAttribute> productAllAttribute = new ArrayList<ProductAllAttribute>();
    @SerializedName("product_rangePrice")
    @Expose
    private List<ProductRangePrice> productRangePrice = new ArrayList<ProductRangePrice>();

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

    /**
     * 
     * @return
     *     The productDetail
     */
    public List<ProductDetail> getProductDetail() {
        return productDetail;
    }

    /**
     * 
     * @param productDetail
     *     The productDetail
     */
    public void setProductDetail(List<ProductDetail> productDetail) {
        this.productDetail = productDetail;
    }

    /**
     * 
     * @return
     *     The productAllAttribute
     */
    public List<ProductAllAttribute> getProductAllAttribute() {
        return productAllAttribute;
    }

    /**
     * 
     * @param productAllAttribute
     *     The product_allAttribute
     */
    public void setProductAllAttribute(List<ProductAllAttribute> productAllAttribute) {
        this.productAllAttribute = productAllAttribute;
    }

    /**
     * 
     * @return
     *     The productRangePrice
     */
    public List<ProductRangePrice> getProductRangePrice() {
        return productRangePrice;
    }

    /**
     * 
     * @param productRangePrice
     *     The product_rangePrice
     */
    public void setProductRangePrice(List<ProductRangePrice> productRangePrice) {
        this.productRangePrice = productRangePrice;
    }

}
