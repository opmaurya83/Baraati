package com.nectarbits.baraati;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.nectarbits.baraati.generalHelper.AppUtils;

/**
 * Created by root on 2/9/16.
 */
public class NotifyService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent_new = new Intent(context, SplashscreenActivity.class);
        intent_new.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent_new,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.logo)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(intent.getExtras().getString(AppUtils.ARG_REMINDER_TITLE))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
