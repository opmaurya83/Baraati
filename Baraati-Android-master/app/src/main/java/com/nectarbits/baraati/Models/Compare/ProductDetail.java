
package com.nectarbits.baraati.Models.Compare;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetail {

    @SerializedName("vendorName")
    @Expose
    private String vendorName;
    @SerializedName("productId")
    @Expose
    private String productId;

    @SerializedName("price")
    @Expose
    private String price;


    @SerializedName("twolinedescription")
    @Expose
    private String twolinedescription;
    @SerializedName("longDescription")
    @Expose
    private String longDescription;
    @SerializedName("Product")
    @Expose
    private String product;
    @SerializedName("bannermainimage")
    @Expose
    private List<Bannermainimage> bannermainimage = new ArrayList<Bannermainimage>();
    @SerializedName("mainimage")
    @Expose
    private List<Mainimage> mainimage = new ArrayList<Mainimage>();
    @SerializedName("moreimage")
    @Expose
    private List<Object> moreimage = new ArrayList<Object>();

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
     *     The twolinedescription
     */
    public String getTwolinedescription() {
        return twolinedescription;
    }

    /**
     * 
     * @param twolinedescription
     *     The twolinedescription
     */
    public void setTwolinedescription(String twolinedescription) {
        this.twolinedescription = twolinedescription;
    }

    /**
     * 
     * @return
     *     The longDescription
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * 
     * @param longDescription
     *     The longDescription
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    /**
     * 
     * @return
     *     The product
     */
    public String getProduct() {
        return product;
    }

    /**
     * 
     * @param product
     *     The Product
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * 
     * @return
     *     The bannermainimage
     */
    public List<Bannermainimage> getBannermainimage() {
        return bannermainimage;
    }

    /**
     * 
     * @param bannermainimage
     *     The bannermainimage
     */
    public void setBannermainimage(List<Bannermainimage> bannermainimage) {
        this.bannermainimage = bannermainimage;
    }

    /**
     * 
     * @return
     *     The mainimage
     */
    public List<Mainimage> getMainimage() {
        return mainimage;
    }

    /**
     * 
     * @param mainimage
     *     The mainimage
     */
    public void setMainimage(List<Mainimage> mainimage) {
        this.mainimage = mainimage;
    }

    /**
     * 
     * @return
     *     The moreimage
     */
    public List<Object> getMoreimage() {
        return moreimage;
    }

    /**
     * 
     * @param moreimage
     *     The moreimage
     */
    public void setMoreimage(List<Object> moreimage) {
        this.moreimage = moreimage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
