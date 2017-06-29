package com.nectarbits.baraati.Chat.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.nectarbits.baraati.Chat.App;
import com.nectarbits.baraati.Chat.utils.helpers.notification.ChatNotificationHelper;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    private static String TAG = GcmBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean enablePush = App.getInstance().getAppSharedHelper().isEnablePushNotifications();
        Log.e(TAG, "--- PUSH. onReceive(), show notification = " + enablePush + " ---");
        if (!enablePush) {
            return;
        }else{
            Bundle extras = intent.getExtras();
            GoogleCloudMessaging googleCloudMessaging = GoogleCloudMessaging.getInstance(context);
            String messageType = googleCloudMessaging.getMessageType(intent);

            if (extras != null && !extras.isEmpty()) {
                if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                    new ChatNotificationHelper(context).parseChatMessage(extras);
                }
            }

            GcmBroadcastReceiver.completeWakefulIntent(intent);
        }
        ComponentName comp = new ComponentName(context.getPackageName(), GCMIntentService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}