
package com.nectarbits.baraati.Models.ProductDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bannermainimage {

    @SerializedName("bannermainimage")
    @Expose
    private String bannermainimage;
    @SerializedName("bannerThumimage")
    @Expose
    private String bannerThumimage;

    /**
     * 
     * @return
     *     The bannermainimage
     */
    public String getBannermainimage() {
        return bannermainimage;
    }

    /**
     * 
     * @param bannermainimage
     *     The bannermainimage
     */
    public void setBannermainimage(String bannermainimage) {
        this.bannermainimage = bannermainimage;
    }

    /**
     * 
     * @return
     *     The bannerThumimage
     */
    public String getBannerThumimage() {
        return bannerThumimage;
    }

    /**
     * 
     * @param bannerThumimage
     *     The bannerThumimage
     */
    public void setBannerThumimage(String bannerThumimage) {
        this.bannerThumimage = bannerThumimage;
    }

}
