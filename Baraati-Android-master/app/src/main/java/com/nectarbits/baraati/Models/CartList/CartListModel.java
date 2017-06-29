
package com.nectarbits.baraati.Models.CartList;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CartListModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("cartlistdata")
    @Expose
    private List<Cartlistdatum> cartlistdata = new ArrayList<Cartlistdatum>();
    @SerializedName("grandsum")
    @Expose
    private Integer grandsum;

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
     *     The cartlistdata
     */
    public List<Cartlistdatum> getCartlistdata() {
        return cartlistdata;
    }

    /**
     * 
     * @param cartlistdata
     *     The cartlistdata
     */
    public void setCartlistdata(List<Cartlistdatum> cartlistdata) {
        this.cartlistdata = cartlistdata;
    }

    /**
     * 
     * @return
     *     The grandsum
     */
    public Integer getGrandsum() {
        return grandsum;
    }

    /**
     * 
     * @param grandsum
     *     The grandsum
     */
    public void setGrandsum(Integer grandsum) {
        this.grandsum = grandsum;
    }

}
