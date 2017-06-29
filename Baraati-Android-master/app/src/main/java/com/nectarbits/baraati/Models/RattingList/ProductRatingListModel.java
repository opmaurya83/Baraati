
package com.nectarbits.baraati.Models.RattingList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductRatingListModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ratingDetail")
    @Expose
    private List<RatingDetail> ratingDetail = new ArrayList<RatingDetail>();
    @SerializedName("averageRating")
    @Expose
    private Float averageRating;
    @SerializedName("Rating_1")
    @Expose
    private String rating1;
    @SerializedName("Rating_2")
    @Expose
    private String rating2;
    @SerializedName("Rating_3")
    @Expose
    private String rating3;
    @SerializedName("Rating_4")
    @Expose
    private String rating4;
    @SerializedName("Rating_5")
    @Expose
    private String rating5;
    @SerializedName("canRate")
    @Expose
    private String canRate;

    @SerializedName("alreadyRated")
    @Expose
    private String alreadyRated;


    @SerializedName("msg")
    @Expose
    private String msg;


    @SerializedName("userwithrate1")
    @Expose
    private String userwithrate1;

    @SerializedName("userwithrate2")
    @Expose
    private String userwithrate2;

    @SerializedName("userwithrate3")
    @Expose
    private String userwithrate3;

    @SerializedName("userwithrate4")
    @Expose
    private String userwithrate4;

    @SerializedName("userwithrate5")
    @Expose
    private String userwithrate5;


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
     *     The ratingDetail
     */
    public List<RatingDetail> getRatingDetail() {
        return ratingDetail;
    }

    /**
     * 
     * @param ratingDetail
     *     The ratingDetail
     */
    public void setRatingDetail(List<RatingDetail> ratingDetail) {
        this.ratingDetail = ratingDetail;
    }

    /**
     * 
     * @return
     *     The averageRating
     */
    public Float getAverageRating() {
        return averageRating;
    }

    /**
     * 
     * @param averageRating
     *     The averageRating
     */
    public void setAverageRating(Float averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * 
     * @return
     *     The rating1
     */
    public String getRating1() {
        return rating1;
    }

    /**
     * 
     * @param rating1
     *     The Rating_1
     */
    public void setRating1(String rating1) {
        this.rating1 = rating1;
    }

    /**
     * 
     * @return
     *     The rating2
     */
    public String getRating2() {
        return rating2;
    }

    /**
     * 
     * @param rating2
     *     The Rating_2
     */
    public void setRating2(String rating2) {
        this.rating2 = rating2;
    }

    /**
     * 
     * @return
     *     The rating3
     */
    public String getRating3() {
        return rating3;
    }

    /**
     * 
     * @param rating3
     *     The Rating_3
     */
    public void setRating3(String rating3) {
        this.rating3 = rating3;
    }

    /**
     * 
     * @return
     *     The rating4
     */
    public String getRating4() {
        return rating4;
    }

    /**
     * 
     * @param rating4
     *     The Rating_4
     */
    public void setRating4(String rating4) {
        this.rating4 = rating4;
    }

    /**
     * 
     * @return
     *     The rating5
     */
    public String getRating5() {
        return rating5;
    }

    /**
     * 
     * @param rating5
     *     The Rating_5
     */
    public void setRating5(String rating5) {
        this.rating5 = rating5;
    }

    /**
     * 
     * @return
     *     The canRate
     */
    public String getCanRate() {
        return canRate;
    }

    /**
     * 
     * @param canRate
     *     The canRate
     */
    public void setCanRate(String canRate) {
        this.canRate = canRate;
    }

    public String getAlreadyRated() {
        return alreadyRated;
    }

    public void setAlreadyRated(String alreadyRated) {
        this.alreadyRated = alreadyRated;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserwithrate1() {
        return userwithrate1;
    }

    public void setUserwithrate1(String userwithrate1) {
        this.userwithrate1 = userwithrate1;
    }

    public String getUserwithrate2() {
        return userwithrate2;
    }

    public void setUserwithrate2(String userwithrate2) {
        this.userwithrate2 = userwithrate2;
    }

    public String getUserwithrate3() {
        return userwithrate3;
    }

    public void setUserwithrate3(String userwithrate3) {
        this.userwithrate3 = userwithrate3;
    }

    public String getUserwithrate4() {
        return userwithrate4;
    }

    public void setUserwithrate4(String userwithrate4) {
        this.userwithrate4 = userwithrate4;
    }

    public String getUserwithrate5() {
        return userwithrate5;
    }

    public void setUserwithrate5(String userwithrate5) {
        this.userwithrate5 = userwithrate5;
    }
}
