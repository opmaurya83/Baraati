package com.nectarbits.baraati.Chat.utils.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.nectarbits.baraati.Chat.ui.activities.call.CallActivity;
import com.nectarbits.baraati.Chat.utils.SystemUtils;
import com.nectarbits.baraati.Chat.utils.helpers.notification.ChatNotificationHelper;
import com.quickblox.q_municate_core.service.QBServiceConsts;
import com.quickblox.q_municate_db.models.User;

public class ChatMessageReceiver extends BroadcastReceiver {

    private static final String TAG = ChatMessageReceiver.class.getSimpleName();
    private static final String callActivityName = CallActivity.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG, "--- onReceive() ---");

        String activityOnTop = SystemUtils.getNameActivityOnTopStack();

        if (!SystemUtils.isAppRunningNow() && !callActivityName.equals(activityOnTop)) {
            ChatNotificationHelper chatNotificationHelper = new ChatNotificationHelper(context);

            String message = intent.getStringExtra(QBServiceConsts.EXTRA_CHAT_MESSAGE);
            User user = (User) intent.getSerializableExtra(QBServiceConsts.EXTRA_USER);
            String dialogId = intent.getStringExtra(QBServiceConsts.EXTRA_DIALOG_ID);

            chatNotificationHelper.saveOpeningDialogData(user.getUserId(), dialogId);
            chatNotificationHelper.saveOpeningDialog(true);
            chatNotificationHelper.sendNotification(message);
        }
    }
}