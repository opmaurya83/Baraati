
package com.nectarbits.baraati.Models.VendorDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mainimage {

    @SerializedName("Mainimage")
    @Expose
    private String mainimage;
    @SerializedName("Thumimage")
    @Expose
    private String thumimage;

    /**
     * 
     * @return
     *     The mainimage
     */
    public String getMainimage() {
        return mainimage;
    }

    /**
     * 
     * @param mainimage
     *     The Mainimage
     */
    public void setMainimage(String mainimage) {
        this.mainimage = mainimage;
    }

    /**
     * 
     * @return
     *     The thumimage
     */
    public String getThumimage() {
        return thumimage;
    }

    /**
     * 
     * @param thumimage
     *     The Thumimage
     */
    public void setThumimage(String thumimage) {
        this.thumimage = thumimage;
    }

}
