
package com.nectarbits.baraati.Models.CartList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cartlistdatum {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("vendorId")
    @Expose
    private String vendorId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("product_total")
    @Expose
    private Integer productTotal;

    @SerializedName("twolinedescription")
    @Expose
    private String twolinedescription;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("Adminuser")
    @Expose
    private String Adminuser;

    @SerializedName("AdminuserId")
    @Expose
    private String AdminuserId;

    @SerializedName("isown")
    @Expose
    private String isown;


    @SerializedName("userEventTypeEventsId")
    @Expose
    private String userEventTypeEventsId;




    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
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
     *     The product_id
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
     *     The quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * 
     * @param quantity
     *     The quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
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
     *     The userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 
     * @param userid
     *     The userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 
     * @return
     *     The productTotal
     */
    public Integer getProductTotal() {
        return productTotal;
    }

    /**
     * 
     * @param productTotal
     *     The product_total
     */
    public void setProductTotal(Integer productTotal) {
        this.productTotal = productTotal;
    }

    public String getTwolinedescription() {
        return twolinedescription;
    }

    public void setTwolinedescription(String twolinedescription) {
        this.twolinedescription = twolinedescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getAdminuser() {
        return Adminuser;
    }

    public void setAdminuser(String adminuser) {
        Adminuser = adminuser;
    }

    public String getAdminuserId() {
        return AdminuserId;
    }

    public void setAdminuserId(String adminuserId) {
        AdminuserId = adminuserId;
    }

    public String getIsown() {
        return isown;
    }

    public void setIsown(String isown) {
        this.isown = isown;
    }

    public String getUserEventTypeEventsId() {
        return userEventTypeEventsId;
    }

    public void setUserEventTypeEventsId(String userEventTypeEventsId) {
        this.userEventTypeEventsId = userEventTypeEventsId;
    }
}
