
package com.nectarbits.baraati.Models.OrderList;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderListModel {

    @SerializedName("productDetail")
    @Expose
    private List<ProductDetail> productDetail = new ArrayList<ProductDetail>();
    @SerializedName("status")
    @Expose
    private String status;

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

}
