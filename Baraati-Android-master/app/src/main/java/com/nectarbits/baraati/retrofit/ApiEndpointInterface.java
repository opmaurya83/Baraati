package com.nectarbits.baraati.retrofit;



import com.nectarbits.baraati.Models.AddCheckList.AddCheckListModel;
import com.nectarbits.baraati.Models.AddDates.AddDateModel;
import com.nectarbits.baraati.Models.AddUpdateAddress.AddUpdateAddressModel;
import com.nectarbits.baraati.Models.Address.AddressModel;
import com.nectarbits.baraati.Models.AddtoCart.AddtoCartModel;
import com.nectarbits.baraati.Models.CartList.CartListModel;
import com.nectarbits.baraati.Models.ChangePasswordModel.ChangePasswordModel;
import com.nectarbits.baraati.Models.Compare.ProductCompareModel;
import com.nectarbits.baraati.Models.DeleteUserEvent.DeleteUserEventModel;
import com.nectarbits.baraati.Models.Event.EventModel;
import com.nectarbits.baraati.Models.EventType.EventTypeModel;
import com.nectarbits.baraati.Models.ForgotPassword.ForgotPasswordModel;
import com.nectarbits.baraati.Models.InquiryModel.List.InquiryListModel;
import com.nectarbits.baraati.Models.InquiryModel.Send.InquiryModel;
import com.nectarbits.baraati.Models.Login.LoginModel;
import com.nectarbits.baraati.Models.Logout.LogoutModel;
import com.nectarbits.baraati.Models.MainCategory.MainCategoryModel;
import com.nectarbits.baraati.Models.Notifications.List.NotificationModel;
import com.nectarbits.baraati.Models.Notifications.Read.NotificationReadModel;
import com.nectarbits.baraati.Models.Notifications.Request.AcceptRejectModel;
import com.nectarbits.baraati.Models.OrderDetail.OrderDetailModel;
import com.nectarbits.baraati.Models.OrderList.OrderListModel;
import com.nectarbits.baraati.Models.OrderPlace.OrderPlaceModel;
import com.nectarbits.baraati.Models.PhoneUPdate.MobileUpdateModel;
import com.nectarbits.baraati.Models.ProductDetail.ProductDetailModel;
import com.nectarbits.baraati.Models.RattingList.ProductRatingListModel;
import com.nectarbits.baraati.Models.Register.OTPModel;
import com.nectarbits.baraati.Models.Register.RegisterModel;
import com.nectarbits.baraati.Models.Register.UserCheck.UserCheckModel;
import com.nectarbits.baraati.Models.ResetPassword.ResetPasswordModel;
import com.nectarbits.baraati.Models.Resposibility.Add.AddResponsibilityModel;
import com.nectarbits.baraati.Models.Resposibility.Delete.DeleteResponsibilityModel;
import com.nectarbits.baraati.Models.Resposibility.List.ListResponsibilityModel;
import com.nectarbits.baraati.Models.SubCategory.SubCategoryModel;
import com.nectarbits.baraati.Models.UpdateProfile.UpdateProfileModel;
import com.nectarbits.baraati.Models.UpdateQuickBloxid.UpdateQuickBloxModel;
import com.nectarbits.baraati.Models.UserEvent.Complete.CompleteEventModel;
import com.nectarbits.baraati.Models.UserEvent.UserEventModel;
import com.nectarbits.baraati.Models.UserList.UserListModel;
import com.nectarbits.baraati.Models.Vendor.VendorList;
import com.nectarbits.baraati.Models.VendorBazaar.VendorBazaarModel;
import com.nectarbits.baraati.Models.VendorDetail.VendorDetailModel;
import com.nectarbits.baraati.Models.WriteReview.WriteReviewModel;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by nectabits on 31/3/16.
 */
public interface ApiEndpointInterface {

    /**
     * Register user
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/registration")
    Call<RegisterModel> Register(@Field("Register_Request") JSONObject jsonObject);

    /**
     * Login user
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/login")
    Call<LoginModel> Login(@Field("Login_Request") JSONObject jsonObject);


    /**
     *
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/forgotpassword")
    Call<ForgotPasswordModel> ForgotPassword(@Field("Forgotpassword_Request") JSONObject jsonObject);


    /**
     *
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/changePassword")
    Call<ResetPasswordModel> ResetPassword(@Field("changeRequest") JSONObject jsonObject);


    /**
     *
      * @return
     */
    @POST("ws_user/mainCategory")
    Call<MainCategoryModel> GetMainCategory();


    /**
     *Load Subcategory
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/subCategory")
    Call<SubCategoryModel> GetSubCategory(@Field("subcategoryRequest") JSONObject jsonObject);

    /**
     *Load Subcategory
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/getEvent")
    Call<EventTypeModel> GetEventTypes(@Field("eventRequest") JSONObject jsonObject);

/**
 *
 * @param jsonObject
 * @return
 */

    @FormUrlEncoded
    @POST("ws_user/logout")
    Call<LogoutModel> Logout(@Field("Logout_Request") JSONObject jsonObject);


    /**
     *Load Subcategory
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/getEventList")
    Call<EventModel> GetEvents(@Field("eventlistRequest") JSONObject jsonObject);


    /**
     *Load Subcategory
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/addUserEvent")
    Call<AddCheckListModel> AddCheckList(@Field("addEventRequest") JSONObject jsonObject);


    /**
     *Load Subcategory
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/getUserEvents")
    Call<UserEventModel> GetUserCheckList(@Field("getEventRequest") JSONObject jsonObject);


     /* Divya 23-08-2016 */
    /**
     *
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/VendorList")
    Call<VendorList> GetVendorList(@Field("vendorRequest") JSONObject jsonObject);

    /**
     *
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/vendorDetail")
    Call<VendorDetailModel> GetVendorDetail(@Field("vendorDetailRequest") JSONObject jsonObject);

     /* Divya 23-08-2016 */


    /**
     *Delete User Events
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/deleteuserEvent")
    Call<DeleteUserEventModel> DeleteUserEvent(@Field("deleteEventRequest") JSONObject jsonObject);


    /**
     *Delete User Events
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/addEventDate")
    Call<AddDateModel> AddDates(@Field("addDateRequest") JSONObject jsonObject);


    /**
     * Get Vendor Bazaar List
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/VendorList")
    Call<VendorBazaarModel> GetVendorBazaarList(@Field("vendorRequest") JSONObject jsonObject);

    /**
     * Vendor Inquiry
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/addInquiry")
    Call<InquiryModel> SendVendroInquiry(@Field("addInquiryRequest") JSONObject jsonObject);

    /**
     * Get Product Detail
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/productDetail")
    Call<ProductDetailModel> GetProductDetail(@Field("productDetailRequest") JSONObject jsonObject);



    /**
     * Get Update Profile
     * @return
     */
    @Multipart
    @POST("ws_user/editprofile")
    Call<UpdateProfileModel> GetUpdateProfile(@PartMap Map<String,Object> objectMap , @PartMap() Map<String, RequestBody> partMap);


    /**
     * Get Update Profile
     * @return
     */
    @Multipart
    @POST("ws_user/add_edit")
    Call<UpdateProfileModel> GetUpdateProfileOverlay(@PartMap Map<String,Object> objectMap , @PartMap() Map<String, RequestBody> partMap);


    /**
     *
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/passwordChange")
    Call<ChangePasswordModel> ChangePassword(@Field("changeRequest") JSONObject jsonObject);


    /**
     * Add Product Review
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/Addrating")
    Call<WriteReviewModel> WriteReview(@Field("addratingRequest") JSONObject getrating );


    /**
     * Update user review
     * @param jsonObject
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/editRating")
    Call<WriteReviewModel> UpdateReview(@Field("editratingRequest") JSONObject jsonObject);

    /**
     * Get Product Ratings
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/getRating")
    Call<ProductRatingListModel> GetProductRatings(@Field("getrating") JSONObject getrating);

    /**
     * Add to cart
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/addcartlist")
    Call<AddtoCartModel> AddtoCart(@Field("addcartlist_Request") JSONObject getrating);

    /**
     * Get Cart List
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/getcartlist")
    Call<CartListModel> GetCartList(@Field("getcartlist_Request") JSONObject getrating);


    /**
     * Update Cart
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/updatecartlist")
    Call<CartListModel> UpdateCart(@Field("updatecartlist_Request") JSONObject getrating);


    /**
     * Delete Cart Item
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/removecartlist")
    Call<CartListModel> DeleteCartItem(@Field("removecartlist_Request") JSONObject getrating);



    /**
     * Delete Cart Item
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/getshippingAddress")
    Call<AddressModel> GetShippingAddress(@Field("getshipping_Request") JSONObject getrating);

    /**
     * Add/Update Cart
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/editshippingAddress")
    Call<AddUpdateAddressModel> AddUpdateAddress(@Field("editshipping_Request") JSONObject getrating);


    /**
     * Place Order
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/addquotation")
    Call<OrderPlaceModel> PlaceOrder(@Field("addquotation_Request") JSONObject getrating);

    /**
     * Get OrderList
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/orderlist")
    Call<com.nectarbits.baraati.Models.OrderList.List.OrderListModel> GetOrderList(@Field("orderlist_Request") JSONObject getrating);

    /**
     * Get Order Detail
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/orderDetail")
    Call<OrderDetailModel> GetOrderDetail(@Field("orderdetail_Request") JSONObject getrating);

    /**
     * Compare Products
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/compareProduct")
    Call<ProductCompareModel> CompareProduct(@Field("compare_Request") JSONObject getrating);

    /**
     * Update Mobile
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("ws_user/editphone")
    Call<MobileUpdateModel> MobileUpdate(@Field("editphoneRequest") JSONObject getrating);


    /**
     * Share responsibility
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/shareEvent")
    Call<AddResponsibilityModel> ShareResposnsibility(@Field("shareeventRequest") JSONObject getrating);

    /**
     * Share responsibility
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/removeResponsibility")
    Call<DeleteResponsibilityModel> RemoveResposnsibility(@Field("removeRequest") JSONObject getrating);


    /**
     * List Responsibility
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/assignedList")
    Call<ListResponsibilityModel> ListResposnsibility(@Field("getassignedRequest") JSONObject getrating);



    /**
     * List Notifications
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/getnotification")
    Call<NotificationModel> GetNotifications(@Field("notificationRequest") JSONObject getrating);




    /**
     * Read Notifications
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/readNotification")
    Call<NotificationReadModel> ReadNotifications(@Field("readRequest") JSONObject getrating);


    /**
     * List Notifications
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/Accept_reject")
    Call<AcceptRejectModel> Aceept_Reject_Request(@Field("decisonRequest") JSONObject getrating);



    /**
     * List Inquiries
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/getinquiry")
    Call<InquiryListModel> GetInquiryList(@Field("inquryRequest") JSONObject getrating);


    /**
     * Complete Event
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/completeTask")
    Call<CompleteEventModel> CompletEvent(@Field("taskcompleteRequest") JSONObject getrating);


    /**
     * Check User registered or not
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/checkExist")
    Call<UserCheckModel> CheckUserExistance(@Field("checkRequest") JSONObject getrating);


    /**
     * send OTP
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/sendOTP")
    Call<OTPModel> SendOTP(@Field("sendRequest") JSONObject getrating);



    /**
     * send OTP
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/updatequickblock")
    Call<UpdateQuickBloxModel> UpdateQuickBloxID(@Field("update_Request") JSONObject getrating);


    /**
     * Get QuickBlox user list
     * @param getrating
     * @return
     */
    @FormUrlEncoded
    @POST("webservice/compareUser")
    Call<UserListModel> GetQuickBloxUserList(@Field("compare_Request") JSONObject getrating);

}
