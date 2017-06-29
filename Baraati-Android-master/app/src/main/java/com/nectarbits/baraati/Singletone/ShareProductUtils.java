package com.nectarbits.baraati.Singletone;

/**
 * Created by root on 11/11/16.
 */
public class ShareProductUtils {
    String product_id="0";
    String product_name="";
    String image_ulr="";
    Boolean isSharing=false;
    String mUserEventTypeEventID="";
    String mFor_USER_ID="0";
    private static ShareProductUtils ourInstance = new ShareProductUtils();

    public static ShareProductUtils getInstance() {
        return ourInstance;
    }

    private ShareProductUtils() {
    }

    public void reset(){
        product_id="0";
        product_name="";
        image_ulr="";
        isSharing=false;
        mUserEventTypeEventID="";
        mFor_USER_ID="0";
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getImage_ulr() {
        return image_ulr;
    }

    public void setImage_ulr(String image_ulr) {
        this.image_ulr = image_ulr;
    }

    public Boolean getSharing() {
        return isSharing;
    }

    public void setSharing(Boolean sharing) {
        isSharing = sharing;
    }

    public String getmUserEventTypeEventID() {
        return mUserEventTypeEventID;
    }

    public void setmUserEventTypeEventID(String mUserEventTypeEventID) {
        this.mUserEventTypeEventID = mUserEventTypeEventID;
    }

    public String getmFor_USER_ID() {
        return mFor_USER_ID;
    }

    public void setmFor_USER_ID(String mFor_USER_ID) {
        this.mFor_USER_ID = mFor_USER_ID;
    }
}
