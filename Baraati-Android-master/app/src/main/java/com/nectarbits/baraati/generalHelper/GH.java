package com.nectarbits.baraati.generalHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.VectorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.widget.Toast;


import com.nectarbits.baraati.Models.UpdateQuickBloxid.UpdateQuickBloxModel;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.View.BadgeDrawable;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.quickblox.q_municate_core.models.AppSession;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nectarbits on 18/05/16.
 */
public class GH {
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality) {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.NO_WRAP);
    }

    public static String RetrofitBufferReaderResponse(Response<ResponseBody> response) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        reader = new BufferedReader(new InputStreamReader(response.body().byteStream()));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static boolean isValidMobile(String mobile) {
        if (mobile.trim().equals("") || mobile.trim() == null || mobile.length() < 10 || mobile.length() > 14) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidEmail(String email) {
        if (email.trim() == null || email.trim().equals("") || !email.contains("@") || !email.contains(".")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidPassword(String password) {
        if (password.trim() == null || password.trim().equals("") || password.length() < 6 || password.length() > 15) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidName(String name) {
        if (name.trim() == null || name.trim().equals("") || name.length() < 6 || name.length() > 20) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidString(String str) {
        if (str.trim() == null || str.trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isInternetOn(Context context) {

        // get Connectivity Manager object to check connection
        ConnectivityManager connec =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {

            // if connected with internet

            //   Toast.makeText(context, " Connected ", Toast.LENGTH_LONG).show();
            return true;

        } else if (
                connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {

            Toast.makeText(context, context.getString(R.string.str_no_internet), Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }


    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        } else {
            Toast.makeText(context, "No internet connection found.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }

    public static List<Date> getDates(String dateString1, String dateString2)
    {
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1 .parse(dateString1);
            date2 = df1 .parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while(!cal1.after(cal2))
        {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {
        BadgeDrawable badge;
        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }
        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }



    public static Boolean check_prefix(String phone, String match, int character)
    {

        try
        {
            if(phone.substring(0,character).equals(match) || phone.substring(0,character)==match)
            {
                return true;
            }else
            {
                return false;
            }
        }catch(Exception ex)
        {
            return false;
        }


    }

    public static String getFormatedAmount(String amount){
        DecimalFormat IndianCurrencyFormat = new DecimalFormat("##,##,###");
        return IndianCurrencyFormat.format(Integer.parseInt(amount));
       // return NumberFormat.getNumberInstance(new Locale("en", "IN")).format(Integer.parseInt(amount));
    }


    /**
     * Load Product data
     */
    public static void UpdateQuickBloxID(Context context) {


        Call<UpdateQuickBloxModel> call = RetrofitBuilder.getInstance().getRetrofit().UpdateQuickBloxID(RequestBodyBuilder.getInstanse().getRequestToUpdateQuickBlocID(UserDetails.getInstance(context).getUser_id(),""+ AppSession.getSession().getUser().getId()));

        call.enqueue(new Callback<UpdateQuickBloxModel>() {
            @Override
            public void onResponse(Call<UpdateQuickBloxModel> call, Response<UpdateQuickBloxModel> response) {

            }

            @Override
            public void onFailure(Call<UpdateQuickBloxModel> call, Throwable t) {

            }
        });
    }


}
