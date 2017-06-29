package com.nectarbits.baraati;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nectarbits.baraati.Chat.utils.SystemUtils;
import com.nectarbits.baraati.generalHelper.AppUtils;

import java.util.Date;
import java.util.Map;


public class FireBaseGCMService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            if(!remoteMessage.getData().containsKey("dialog_id")) {
                sendNotification_Resposnsibility(remoteMessage.getData());
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_app)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_HIGH)
                .build();
        /*NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher);
                .setContentTitle("PicStacker")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);*/

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder);
    }


    private void sendNotification_Resposnsibility(Map<String,String> data) {
        Intent intent=null;

        if(data.containsKey("type")) {
            if(data.get("type").equalsIgnoreCase(AppUtils.Assign) || data.get("type").equalsIgnoreCase(AppUtils.Canceled) || data.get("type").equalsIgnoreCase(AppUtils.Completed)) {
                intent = new Intent(this, EventDateResponsibilityActivity.class);
                intent.putExtra(AppUtils.ARG_EVENTTYPE_ID, data.get("userEventTypeEventsId"));
                intent.putExtra(AppUtils.ARG_EVENTTYPE_NAME, data.get("eventName"));
                intent.putExtra(AppUtils.ARG_DATE_FROM, data.get("startDate"));
                intent.putExtra(AppUtils.ARG_DATE_TO, data.get("endDate"));
                intent.putExtra(AppUtils.ARG_FOR_USER_ID, data.get("from_user_id"));
                intent.putExtra(AppUtils.ARG_NOTIFICATION_TYPE, data.get("type"));
                intent.putExtra(AppUtils.ARG_EVENT_STATUS, data.get("eventStatus"));
                intent.putExtra(AppUtils.ARG_NOTIFICATION_DATE, data.get("notificationDate"));
                intent.putExtra(AppUtils.ARG_NOTIFICATION_ID, data.get("notification_id"));
                intent.putExtra(AppUtils.ARG_NOTIFICATION_IS_READ, data.get("isread"));
                if(!SystemUtils.isAppRunningNow())
                {
                    intent.putExtra(AppUtils.ARG_IS_FROM_NOTIFICATION, true);
                }

            }else if(data.get("type").equalsIgnoreCase(AppUtils.Accepted) || data.get("type").equalsIgnoreCase(AppUtils.Rejected)){

                intent = new Intent(this, EventDateActivity_New.class);
                intent.putExtra(AppUtils.ARG_EVENTTYPE_ID, data.get("userEventTypeEventsId"));
                intent.putExtra(AppUtils.ARG_EVENTTYPE_NAME, data.get("eventName"));
                intent.putExtra(AppUtils.ARG_DATE_FROM, data.get("startDate"));
                intent.putExtra(AppUtils.ARG_DATE_TO, data.get("endDate"));
                intent.putExtra(AppUtils.ARG_CATEGORY_ID, data.get("userCategoryId"));
                intent.putExtra(AppUtils.ARG_SUBCATEGORY_ID, data.get("usersubCategoryId"));
                intent.putExtra(AppUtils.ARG_NOTIFICATION_ID, data.get("notification_id"));
                intent.putExtra(AppUtils.ARG_NOTIFICATION_IS_READ, data.get("isread"));
                if(!SystemUtils.isAppRunningNow())
                {
                    intent.putExtra(AppUtils.ARG_IS_FROM_NOTIFICATION, true);
                }

            }
            else {
                return;
            }


        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_app)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(data.get("msg"))
                .setSound(defaultSoundUri)
                .setPriority(Notification.PRIORITY_HIGH)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int)new Date().getTime(), notificationBuilder);
    }
}
