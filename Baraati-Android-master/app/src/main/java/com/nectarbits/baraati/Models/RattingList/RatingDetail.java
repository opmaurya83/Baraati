
package com.nectarbits.baraati.Models.RattingList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingDetail {

    @SerializedName("product_review_id")
    @Expose
    private String productReviewId;
    @SerializedName("user_id")
    @Expose
    private String customerId;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("date")
    @Expose
    private String date;

    /**
     * 
     * @return
     *     The productReviewId
     */
    public String getProductReviewId() {
        return productReviewId;
    }

    /**
     * 
     * @param productReviewId
     *     The product_review_id
     */
    public void setProductReviewId(String productReviewId) {
        this.productReviewId = productReviewId;
    }

    /**
     * 
     * @return
     *     The customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * 
     * @param customerId
     *     The customer_id
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * 
     * @return
     *     The user
     */
    public String getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * 
     * @return
     *     The rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * 
     * @param rating
     *     The rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * 
     * @return
     *     The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * 
     * @param comment
     *     The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

}
