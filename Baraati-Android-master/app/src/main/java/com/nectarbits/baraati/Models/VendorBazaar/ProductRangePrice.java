
package com.nectarbits.baraati.Models.VendorBazaar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductRangePrice {

    @SerializedName("maxprice")
    @Expose
    private String maxprice;
    @SerializedName("minprice")
    @Expose
    private String minprice;

    /**
     * 
     * @return
     *     The maxprice
     */
    public String getMaxprice() {
        return maxprice;
    }

    /**
     * 
     * @param maxprice
     *     The maxprice
     */
    public void setMaxprice(String maxprice) {
        this.maxprice = maxprice;
    }

    /**
     * 
     * @return
     *     The minprice
     */
    public String getMinprice() {
        return minprice;
    }

    /**
     * 
     * @param minprice
     *     The minprice
     */
    public void setMinprice(String minprice) {
        this.minprice = minprice;
    }

}
