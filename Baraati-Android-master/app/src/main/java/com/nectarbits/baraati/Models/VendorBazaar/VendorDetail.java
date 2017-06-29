
package com.nectarbits.baraati.Models.VendorBazaar;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorDetail {

    @SerializedName("vendorName")
    @Expose
    private String vendorName;

    @SerializedName("Company_Name")
    @Expose
    private String Company_Name="";

    @SerializedName("specialist")
    @Expose
    private String specialist="";


    @SerializedName("vendorId")
    @Expose
    private String vendorId;
    @SerializedName("twolinedescription")
    @Expose
    private String twolinedescription;
    @SerializedName("longDescription")
    @Expose
    private String longDescription;
    @SerializedName("bannermainimage")
    @Expose
    private List<Bannermainimage> bannermainimage = new ArrayList<Bannermainimage>();
    @SerializedName("mainimage")
    @Expose
    private List<Mainimage> mainimage = new ArrayList<Mainimage>();
    @SerializedName("moreimage")
    @Expose
    private List<Moreimage> moreimage = new ArrayList<Moreimage>();

    /**
     * 
     * @return
     *     The vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * 
     * @param vendorName
     *     The vendorName
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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
    public List<Bannermainimage> getBannermainimage() {
        return bannermainimage;
    }

    /**
     * 
     * @param bannermainimage
     *     The bannermainimage
     */
    public void setBannermainimage(List<Bannermainimage> bannermainimage) {
        this.bannermainimage = bannermainimage;
    }

    /**
     * 
     * @return
     *     The mainimage
     */
    public List<Mainimage> getMainimage() {
        return mainimage;
    }

    /**
     * 
     * @param mainimage
     *     The mainimage
     */
    public void setMainimage(List<Mainimage> mainimage) {
        this.mainimage = mainimage;
    }

    /**
     * 
     * @return
     *     The moreimage
     */
    public List<Moreimage> getMoreimage() {
        return moreimage;
    }

    /**
     * 
     * @param moreimage
     *     The moreimage
     */
    public void setMoreimage(List<Moreimage> moreimage) {
        this.moreimage = moreimage;
    }

    public String getCompany_Name() {
        return Company_Name;
    }

    public void setCompany_Name(String company_Name) {
        Company_Name = company_Name;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }
}
