
package com.nectarbits.baraati.Models.Notifications.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

    @SerializedName("notification_id")
    @Expose
    private String notificationId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("isread")
    @Expose
    private String isread;
    @SerializedName("from_user_id")
    @Expose
    private String fromUserId;
    @SerializedName("userEventTypeEventsId")
    @Expose
    private String userEventTypeEventsId;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("eventName")
    @Expose
    private String eventName;


    @SerializedName("notificationDate")
    @Expose
    private String notificationDate;

    @SerializedName("startDate")
    @Expose
    private String startDate;

    @SerializedName("endDate")
    @Expose
    private String endDate;


    @SerializedName("eventStatus")
    @Expose
    private String eventStatus;

    @SerializedName("UserName")
    @Expose
    private String UserName;

    public String getMainEventname() {
        return MainEventname;
    }

    public void setMainEventname(String mainEventname) {
        MainEventname = mainEventname;
    }

    @SerializedName("MainEventname")
    @Expose
    private String MainEventname;

    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;



    /**
     * 
     * @return
     *     The notificationId
     */
    public String getNotificationId() {
        return notificationId;
    }

    /**
     * 
     * @param notificationId
     *     The notification_id
     */
    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The isread
     */
    public String getIsread() {
        return isread;
    }

    /**
     * 
     * @param isread
     *     The isread
     */
    public void setIsread(String isread) {
        this.isread = isread;
    }

    /**
     * 
     * @return
     *     The fromUserId
     */
    public String getFromUserId() {
        return fromUserId;
    }

    /**
     * 
     * @param fromUserId
     *     The from_user_id
     */
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    /**
     * 
     * @return
     *     The userEventTypeEventsId
     */
    public String getUserEventTypeEventsId() {
        return userEventTypeEventsId;
    }

    /**
     * 
     * @param userEventTypeEventsId
     *     The userEventTypeEventsId
     */
    public void setUserEventTypeEventsId(String userEventTypeEventsId) {
        this.userEventTypeEventsId = userEventTypeEventsId;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
