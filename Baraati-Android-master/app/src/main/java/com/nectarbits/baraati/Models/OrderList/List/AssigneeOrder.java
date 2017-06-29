
package com.nectarbits.baraati.Models.OrderList.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssigneeOrder {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("product")
    @Expose
    private String product;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("twolinedescription")
    @Expose
    private String twolinedescription;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("grandTotal")
    @Expose
    private Integer grandTotal;

    /**
     * 
     * @return
     *     The orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 
     * @param orderId
     *     The order_id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * 
     * @return
     *     The orderDate
     */
    public String getOrderDate() {
        return orderDate;
    }

    /**
     * 
     * @param orderDate
     *     The orderDate
     */
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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
     *     The product
     */
    public void setProduct(String product) {
        this.product = product;
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
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 
     * @return
     *     The grandTotal
     */
    public Integer getGrandTotal() {
        return grandTotal;
    }

    /**
     * 
     * @param grandTotal
     *     The grandTotal
     */
    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }

}
