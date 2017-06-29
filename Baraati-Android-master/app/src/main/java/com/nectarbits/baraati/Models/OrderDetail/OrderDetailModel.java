
package com.nectarbits.baraati.Models.OrderDetail;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("orderDetail")
    @Expose
    private List<OrderDetail> orderDetail = new ArrayList<OrderDetail>();
    @SerializedName("shippingDetail")
    @Expose
    private List<ShippingDetail> shippingDetail = new ArrayList<ShippingDetail>();
    @SerializedName("grandtotal")
    @Expose
    private Integer grandtotal;

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
     *     The orderDetail
     */
    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    /**
     * 
     * @param orderDetail
     *     The orderDetail
     */
    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

    /**
     * 
     * @return
     *     The shippingDetail
     */
    public List<ShippingDetail> getShippingDetail() {
        return shippingDetail;
    }

    /**
     * 
     * @param shippingDetail
     *     The shippingDetail
     */
    public void setShippingDetail(List<ShippingDetail> shippingDetail) {
        this.shippingDetail = shippingDetail;
    }

    /**
     * 
     * @return
     *     The grandtotal
     */
    public Integer getGrandtotal() {
        return grandtotal;
    }

    /**
     * 
     * @param grandtotal
     *     The grandtotal
     */
    public void setGrandtotal(Integer grandtotal) {
        this.grandtotal = grandtotal;
    }

}
