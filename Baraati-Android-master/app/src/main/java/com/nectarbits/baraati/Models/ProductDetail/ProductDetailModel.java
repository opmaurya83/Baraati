
package com.nectarbits.baraati.Models.ProductDetail;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductDetailModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("productDetail")
    @Expose
    private List<ProductDetail> productDetail = new ArrayList<ProductDetail>();
    @SerializedName("product_allAttribute")
    @Expose
    private List<ProductAllAttribute> productAllAttribute = new ArrayList<ProductAllAttribute>();

    @SerializedName("averageRating")
    @Expose
    private String averageRating;

    @SerializedName("totaluserRated")
    @Expose
    private String totaluserRated;

    @SerializedName("Iscart")
    @Expose
    private String Iscart;

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

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getTotaluserRated() {
        return totaluserRated;
    }

    public void setTotaluserRated(String totaluserRated) {
        this.totaluserRated = totaluserRated;
    }

    public String getIscart() {
        return Iscart;
    }

    public void setIscart(String iscart) {
        Iscart = iscart;
    }
}
