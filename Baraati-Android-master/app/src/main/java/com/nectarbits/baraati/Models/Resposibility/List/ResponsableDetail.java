
package com.nectarbits.baraati.Models.Resposibility.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsableDetail {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("invitation_status")
    @Expose
    private String invitationStatus;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;

    /**
     * `
     * @return
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The invitationStatus
     */
    public String getInvitationStatus() {
        return invitationStatus;
    }

    /**
     * 
     * @param invitationStatus
     *     The invitation_status
     */
    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The profilePicture
     */
    public String getProfilePicture() {
        return profilePicture;
    }

    /**
     * 
     * @param profilePicture
     *     The profilePicture
     */
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

}
