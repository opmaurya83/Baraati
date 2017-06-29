
package com.nectarbits.baraati.Models.VendorBazaar;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetail {

    @SerializedName("productId")
    @Expose
    private String productId;
    @SerializedName("vendorId")
    @Expose
    private String vendorId;
    @SerializedName("Product")
    @Expose
    private String Product;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("twolinedescription")
    @Expose
    private String twolinedescription;
    @SerializedName("longDescription")
    @Expose
    private String longDescription;
    @SerializedName("bannermainimage")
    @Expose
    private List<Bannermainimage_> bannermainimage = new ArrayList<Bannermainimage_>();
    @SerializedName("mainimage")
    @Expose
    private List<Mainimage_> mainimage = new ArrayList<Mainimage_>();
    @SerializedName("moreimage")
    @Expose
    private List<Object> moreimage = new ArrayList<Object>();

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
     *     The Product
     */
    public String getProduct() {
        return Product;
    }

    /**
     * 
     * @param product
     *     The Product
     */
    public void setProduct(String product) {
        this.Product = product;
    }

    /**
     * 
     * @return
     *     The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.price = price;
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
     *     The bannermainimage
     */
    public List<Bannermainimage_> getBannermainimage() {
        return bannermainimage;
    }

    /**
     * 
     * @param bannermainimage
     *     The bannermainimage
     */
    public void setBannermainimage(List<Bannermainimage_> bannermainimage) {
        this.bannermainimage = bannermainimage;
    }

    /**
     * 
     * @return
     *     The mainimage
     */
    public List<Mainimage_> getMainimage() {
        return mainimage;
    }

    /**
     * 
     * @param mainimage
     *     The mainimage
     */
    public void setMainimage(List<Mainimage_> mainimage) {
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

}
