package com.nectarbits.baraati.retrofit;



import android.util.Log;

import com.nectarbits.baraati.BaraatiApp;
import com.nectarbits.baraati.Models.CartList.Cartlistdatum;
import com.nectarbits.baraati.Models.EventType.EventType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by nectabits on 7/4/16.
 */
public class RequestBodyBuilder {

    private static final String TAG = RequestBodyBuilder.class.getSimpleName();
    private static RequestBodyBuilder requestBodyBuilder = new RequestBodyBuilder();

    private RequestBodyBuilder() {

    }

    public static RequestBodyBuilder getInstanse() {
        return requestBodyBuilder;

    }

    /**
     * Register Request method
     * @param password
     * @param phone
     * @param firstname
     * @param lastname
     * @param emailId
     * @return
     */
    public JSONObject get_Request_For_Register(String password, String phone, String firstname, String lastname,
                                                      String emailId) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("password", password);
            jsonObject.put("phone", phone);
            jsonObject.put("firstName", firstname);
            jsonObject.put("lastName", "lastname");
            jsonObject.put("emailId", emailId);
            jsonObject.put("device", "android");
            jsonObject.put("deviceId", BaraatiApp.getUDID());
            jsonObject.put("udid", BaraatiApp.getUDID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject get_Request_For_Login(String emailid,
                                               String password,String loginusing) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("password", password);
            jsonObject.put("emailid", emailid);
            jsonObject.put("device", "android");
            jsonObject.put("deviceId", BaraatiApp.getUDID());
            jsonObject.put("udid", BaraatiApp.getUDID());
            jsonObject.put("loginusing",loginusing);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * Forgot Password
     *
     * @param emailId
     * @return
     */
    public JSONObject getRequestForgotPasswordResponse(String emailId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("emailid", emailId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * Reset Password
     *
     * @param emailId
     * @param password
     * @return
     */
    public JSONObject getRequestResetPasswordResponse(String emailId, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("emailid", emailId);
            jsonObject.put("newpassword", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getRequestSubcategory(String categoryId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("categoryId", categoryId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getRequestEvents(String categoryId,String subcategoryId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("categoryId", categoryId);
            jsonObject.put("subcategoryId", subcategoryId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getRequestLogoutResponse(String user_id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user_id);
            jsonObject.put("udid", BaraatiApp.getUDID());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getRequestEventTypes(String categoryId,String subcategoryId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("categoryId", categoryId);
            jsonObject.put("event_categoryId", subcategoryId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public JSONObject getRequestAddCheckList(String userid,List<EventType> arrayList) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", userid);
            JSONArray jsonArray=new JSONArray();
            for (int i = 0; i < arrayList.size(); i++) {
                JSONObject jsonObject1=new JSONObject();
                jsonObject1.put("eventTypeEvent_id", arrayList.get(i).getEventTypeEventId());
                jsonObject1.put("eventCategoryId", arrayList.get(i).getEvent_categoryId());
                jsonObject1.put("eventTypeId", arrayList.get(i).getEventId());
                jsonObject1.put("categoryId", arrayList.get(i).getCategoryId());
                jsonObject1.put("subcategoryId", arrayList.get(i).getSubcategoryId());
                jsonArray.put(jsonObject1);
            }
            jsonObject.put("EventData", jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getRequestUserCheckListResponse(String userid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", userid);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /* Divya 23-08-2016 */
    public JSONObject getRequestVendorList(String eventTypeId){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("eventTypeId", eventTypeId);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     *
     * @param eventTypeId
     * @param sorton_product
     * @param sortby_product
     * @param sorton_vendor
     * @param sortby_vendor
     * @return
     */

    public JSONObject getRequestVendorList(String eventTypeId,String sorton_product, String sortby_product,String sorton_vendor,String sortby_vendor,String Attribute_vendor,String Attribute_product,String range_product){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("eventTypeId", eventTypeId);

            if(sorton_product.length()>0){
                jsonObject.put("sorton_product", sorton_product);
            }

            if(sortby_product.length()>0){
                jsonObject.put("sortby_product", sortby_product);
            }

            if(sorton_vendor.length()>0){
                jsonObject.put("sorton_vendor", sorton_vendor);
            }

            if(Attribute_vendor.length()>0){
                jsonObject.put("Attribute_vendor", new JSONArray(Attribute_vendor));
            }

            if(Attribute_product.length()>0){
                jsonObject.put("Attribute_product", new JSONArray(Attribute_product));
            }

            if(range_product.length()>0){
                jsonObject.put("range_product", range_product);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }



    public JSONObject getRequestVendorDetail(String vendorId){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("vendorId", vendorId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
 /* Divya 23-08-2016 */




    public JSONObject getRequestDeleteUserEventResponse(String userid,String usersubCategoryId,String userEventId,String userEventTypeEventsId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", userid);
            jsonObject.put("usersubCategoryId", usersubCategoryId);
            jsonObject.put("userEventId", userEventId);
            jsonObject.put("userEventTypeEventsId", userEventTypeEventsId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getRequestAddEventDate(String userEventTypeEventsId,String startDate,String endDate) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userEventTypeEventsId", userEventTypeEventsId);
            jsonObject.put("startDate", startDate);
            jsonObject.put("endDate", endDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Request for send Inquiry
     * @param email_id
     * @param description
     * @param budget
     * @param for_user_id
     * @param userId
     * @param userEventTypeEventsId
     * @param vendorId
     * @return
     */
    public JSONObject getRequestForSendInquiry(String email_id,String description,String budget,String for_user_id,String userId,String userEventTypeEventsId,String vendorId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email_id", email_id);
            jsonObject.put("description", description);
            jsonObject.put("budget",budget);
            jsonObject.put("foruser",for_user_id);
            jsonObject.put("userId",userId);
            jsonObject.put("userEventTypeEventsId",userEventTypeEventsId);
            jsonObject.put("vendorId",vendorId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "getRequestForSendInquiry: "+jsonObject.toString() );
        return jsonObject;
    }

    /**
     * Request for product detail
     * @param productId
     * @return
     */
    public JSONObject getRequestForProductDetail(String productId,String user_id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("productId", productId);
            jsonObject.put("user_id", user_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }




    /**
     * Request for update profile
     * @param userid
     * @param phone
     * @param firstName
     * @param lastName
     * @param city
     * @param state
     * @param country
     * @param Address
     * @param DeliveryAddress
     * @param user_profile_image
     * @return
     */
    public JSONObject get_Request_For_UPDATE_Profile(String userid, String phone, String firstName, String lastName,
                                               String city,String state,String country, String Address, String DeliveryAddress, String user_profile_image) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("userid", userid);
            jsonObject.put("phone", phone);
            jsonObject.put("firstName", firstName);
            jsonObject.put("lastName", lastName);

            jsonObject.put("city", city);
            jsonObject.put("state", state);
            jsonObject.put("country", country);
            jsonObject.put("Address", Address);
            jsonObject.put("DeliveryAddress", DeliveryAddress);
            jsonObject.put("user_profile_image", user_profile_image);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * Change Password
     * @param emailid
     * @param oldpassword
     * @param newpassword
     * @return
     */
    public JSONObject getRequest_ChangePasswordResponse(String emailid, String oldpassword,String newpassword) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("emailid", emailid);
            jsonObject.put("oldpassword", oldpassword);
            jsonObject.put("newpassword", newpassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * Add/Update review
     * @param customer_id
     * @param product_id
     * @param rating
     * @param comment
     * @param nickname
     * @return
     */

    public JSONObject getRequestBuilderForSubmitReview(String customer_id,String product_id,int rating, String comment, String nickname){
        JSONObject jsonObject = new JSONObject();

        try {
            //jsonObject.put("lang", GH.getLanguage());
            jsonObject.put("user_id",customer_id);
            jsonObject.put("product_id",product_id);
            jsonObject.put("rating",rating);
            jsonObject.put("comment",comment);
            jsonObject.put("nickname",nickname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Edit user review
     * @param customer_id
     * @param product_id
     * @param rating
     * @param comment
     * @param nickname
     * @return
     */
    public JSONObject getRequestBuilderForEditReview(String customer_id,String product_id,int rating, String comment, String nickname,String product_review_id){
        JSONObject jsonObject = new JSONObject();

        try {
          /*  jsonObject.put("lang", GH.getLanguage());*/
            jsonObject.put("user_id",customer_id);
            jsonObject.put("product_id",product_id);
            jsonObject.put("rating",rating);
            jsonObject.put("comment",comment);
            jsonObject.put("nickname",nickname);
            jsonObject.put("product_review_id",product_review_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Request builder for getting review
     * @param productId
     * @param userID
     * @return
     */
    public JSONObject getRequestReviewListingResponse(String productId,String userID) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("product_id", productId);
            jsonObject.put("user_id", userID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * Request builder for getting review
     * @param productId
     * @param userID
     * @return
     */
    public JSONObject getRequest_Review_Sort_Response(String productId,String userID,String topreview,String mostrecent) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("product_id", productId);
            jsonObject.put("user_id", userID);
            if(topreview.length()>0){
                jsonObject.put("topreview", topreview);
            }

            if(mostrecent.length()>0){
                jsonObject.put("mostrecent", mostrecent);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * Add to Cart request
     * @param userid
     * @param productId
     * @param quantity
     * @param price
     * @return
     */
    public JSONObject Request_AddtoCart_UpdateCart(String userid, String productId, String quantity, String price,String for_user_id,String userEventTypeEventsId) {
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject_final=new JSONObject();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("product_id", productId);
            jsonObject.put("quantity", quantity);
            jsonObject.put("price", price);
            jsonArray.put(jsonObject);
            jsonObject_final.put("userid",userid);
            jsonObject_final.put("for_user_id",for_user_id);
            jsonObject_final.put("userEventTypeEventsId",userEventTypeEventsId);
            jsonObject_final.put("productData",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_AddtoCart_UpdateCart: "+jsonObject_final.toString());
        return jsonObject_final;
    }

    /**
     * Add to Cart request
     * @param userid
     * @param productId
     * @param quantity
     * @param price
     * @return
     */
    public JSONObject Request_AddtoCart_UpdateCart(String userid, String productId, String quantity, String price) {
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject_final=new JSONObject();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("product_id", productId);
            jsonObject.put("quantity", quantity);
            jsonObject.put("price", price);
            jsonArray.put(jsonObject);
            jsonObject_final.put("userid",userid);
            jsonObject_final.put("productData",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_AddtoCart_UpdateCart: "+jsonObject_final.toString());
        return jsonObject_final;
    }

    /**
     * Cart List Request
     * @param userid
     * @return
     */
    public JSONObject Request_CartList(String userid) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("userid", userid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_CartList: "+jsonObject.toString());
        return jsonObject;
    }

    /**
     * Remove product from cart reqiest
     * @param userid
     * @param product_id
     * @return
     */
    public JSONObject Request_Remove_Product_From_Cart(String userid,String product_id) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("userid", userid);
            jsonObject.put("product_id", product_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_Remove_Product_From_Cart: "+jsonObject.toString());
        return jsonObject;
    }


    /**
     * Get Shipping Address
     * @param userid
     * @return
     */
    public JSONObject Request_ShippingAddress(String userid) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("userid", userid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_ShippingAddress: "+jsonObject.toString());
        return jsonObject;
    }


    public JSONObject Request_PlaceOrder(String userid, String address, List<Cartlistdatum> cartlistdata) {
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject_final=new JSONObject();
        try {
            for (int i = 0; i < cartlistdata.size(); i++) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", cartlistdata.get(i).getName());
                jsonObject.put("product_id", cartlistdata.get(i).getProductId());
                jsonObject.put("quantity", cartlistdata.get(i).getQuantity());
                jsonObject.put("price", cartlistdata.get(i).getPrice());
                jsonObject.put("userid", cartlistdata.get(i).getUserid());
                jsonObject.put("for_user_id", cartlistdata.get(i).getAdminuserId());
                jsonObject.put("userEventTypeEventsId", cartlistdata.get(i).getUserEventTypeEventsId());
                jsonArray.put(jsonObject);
            }


            jsonObject_final.put("userid",userid);
            jsonObject_final.put("address",address);
            jsonObject_final.put("cartlistdata",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_AddtoCart_UpdateCart: "+jsonObject_final.toString());
        return jsonObject_final;
    }

    /**
     *
     * @param userid
     * @return
     */
    public JSONObject Request_GetOrderList(String userid) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("userid", userid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_GetOrderList: "+jsonObject.toString());
        return jsonObject;
    }


    /**
     * Get Order Detail
     * @param userid
     * @param orderid
     * @param prodcut_id
     * @return
     */
    public JSONObject Request_GetOrderDetail(String userid,String orderid,String prodcut_id) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("userid", userid);
            jsonObject.put("orderid", orderid);
            jsonObject.put("prodcut_id", prodcut_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_GetOrderList: "+jsonObject.toString());
        return jsonObject;
    }

    /**
     * Compare product request
     * @param keyword
     * @return
     */
    public JSONObject Request_CompareProdcut(String keyword) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("keyword", keyword);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_CompareProdcut: "+jsonObject.toString());
        return jsonObject;
    }


    /**
     * Update phone number
     * @param emailid
     * @param phone
     * @return
     */
    public JSONObject Request_MobileUpdate(String emailid,String phone,String firstname,String lastname) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("emailid", emailid);
            jsonObject.put("phone", phone);
            jsonObject.put("firstname", firstname);
            jsonObject.put("lastname", lastname);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_CompareProdcut: "+jsonObject.toString());
        return jsonObject;
    }

    /**
     * Share responsibility
     * @param fromuser
     * @param touser
     * @param userEventTypeEventsId
     * @param userCategoryId
     * @param usersubCategoryId
     * @return
     */
    public JSONObject Request_ShareResposibility(String fromuser,String touser,String userEventTypeEventsId,String userCategoryId,String usersubCategoryId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fromuser", fromuser);
            jsonObject.put("touser", touser);
            jsonObject.put("userEventTypeEventsId", userEventTypeEventsId);
            jsonObject.put("userCategoryId", userCategoryId);
            jsonObject.put("usersubCategoryId", usersubCategoryId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_CompareProdcut: "+jsonObject.toString());
        return jsonObject;
    }


    /**
     * List of user responsibilty
     * @param userId
     * @param userEventTypeEventsId
     * @return
     */
    public JSONObject Request_ListResposibility(String userId,String userEventTypeEventsId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", userId);
            jsonObject.put("userEventTypeEventsId", userEventTypeEventsId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_CompareProdcut: "+jsonObject.toString());
        return jsonObject;
    }

    /**
     * Remove Responsibilty
     * @param fromuser
     * @param touser
     * @param userEventTypeEventsId
     * @return
     */
    public JSONObject Request_DeleteResposibility(String fromuser,String touser,String userEventTypeEventsId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fromuser", fromuser);
            jsonObject.put("touser", touser);
            jsonObject.put("userEventTypeEventsId", userEventTypeEventsId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_CompareProdcut: "+jsonObject.toString());
        return jsonObject;
    }


    /**
     * Notification List
     * @param userid
     * @return
     */
    public JSONObject Request_GetNotifications(String userid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", userid);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_GetNotifications: "+jsonObject.toString());
        return jsonObject;
    }


    /**
     * Read Notifications
     * @param userId
     * @param notification_id
     * @return
     */
    public JSONObject Request_ReadNotifications(String userId,String notification_id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", userId);
            jsonObject.put("notification_id", notification_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_ReadNotifications: "+jsonObject.toString());
        return jsonObject;
    }

    /**
     * Accept/Reject Responsibility
     * @param userid
     * @param foruserid
     * @param userEventTypeEventsId
     * @param decison
     * @return
     */
    public JSONObject Request_Accept_Reject(String userid,String foruserid,String userEventTypeEventsId,String decison) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", userid);
            jsonObject.put("foruserid", foruserid);
            jsonObject.put("userEventTypeEventsId", userEventTypeEventsId);
            jsonObject.put("decison", decison);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_ReadNotifications: "+jsonObject.toString());
        return jsonObject;
    }

    /**
     * Request Inquiry List
     * @param userid
     * @return
     */
    public JSONObject Request_InquiryList(String userid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", userid);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_InquiryList: "+jsonObject.toString());
        return jsonObject;
    }


    /**
     * Complete Event Request
     * @param userId
     * @param userEventTypeEventsId
     * @return
     */
    public JSONObject Request_CompleteEvent(String userId,String userEventTypeEventsId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", userId);
            jsonObject.put("userEventTypeEventsId", userEventTypeEventsId);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_CompleteEvent: "+jsonObject.toString());
        return jsonObject;
    }


    /**
     * Request for user register or not
     * @param emailid
     * @param phone
     * @return
     */
    public JSONObject Request_UserExistance(String emailid,String phone) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("emailid", emailid);
            jsonObject.put("phone", phone);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_UserExistance: "+jsonObject.toString());
        return jsonObject;
    }

    /**
     * OTP Request
     * @param phone
     * @return
     */
    public JSONObject Request_OPT(String phone) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("phone", phone);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "Request_OPT: "+jsonObject.toString());
        return jsonObject;
    }

    /**
     * Request for update quickblox it to server
     * @param userid
     * @param quickBlockuserId
     * @return
     */
    public JSONObject getRequestToUpdateQuickBlocID(String userid,String quickBlockuserId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userid", userid);
            jsonObject.put("quickBlockuserId", quickBlockuserId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    /**
     * Request to get quickblox user
     * @param phone
     * @return
     */
    public JSONObject getReqiest_QuickBloxUserList(String phone) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phone", phone);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "getReqiest_QuickBloxUserList: "+phone);
        return jsonObject;
    }
}
