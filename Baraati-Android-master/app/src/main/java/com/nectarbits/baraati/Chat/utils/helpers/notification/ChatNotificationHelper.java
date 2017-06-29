package com.nectarbits.baraati.Chat.utils.helpers.notification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.nectarbits.baraati.Chat.App;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.Chat.utils.SystemUtils;
import com.nectarbits.baraati.Chat.utils.helpers.LoginHelper;
import com.nectarbits.baraati.Chat.utils.helpers.SharedHelper;
import com.nectarbits.baraati.Chat.utils.listeners.simple.SimpleGlobalLoginListener;
import com.quickblox.q_municate_core.models.AppSession;
import com.quickblox.q_municate_core.models.NotificationEvent;
import com.quickblox.q_municate_db.managers.DataManager;
import com.quickblox.q_municate_db.models.Dialog;

public class ChatNotificationHelper {

    public static final String MESSAGE = "message";
    public static final String DIALOG_ID = "dialog_id";
    public static final String USER_ID = "user_id";

    private Context context;
    private SharedHelper appSharedHelper;
    private String dialogId;
    private int userId;

    private static String message;
    private static boolean isLoginNow;

    public ChatNotificationHelper(Context context) {
        this.context = context;
        appSharedHelper = App.getInstance().getAppSharedHelper();
    }

    public void parseChatMessage(Bundle extras) {

        if(extras.containsKey("msg")){
            return;
        }
        if (extras.getString(ChatNotificationHelper.MESSAGE) != null) {
            message = extras.getString(ChatNotificationHelper.MESSAGE);
        }

        if (extras.getString(ChatNotificationHelper.USER_ID) != null) {
            userId = Integer.parseInt(extras.getString(ChatNotificationHelper.USER_ID));
        }

        if (extras.getString(ChatNotificationHelper.DIALOG_ID) != null) {
            dialogId = extras.getString(ChatNotificationHelper.DIALOG_ID);
        }

        Log.e(ChatNotificationHelper.class.getSimpleName(), "parseChatMessage: "+SystemUtils.getNameActivityOnTopStack());

        if (SystemUtils.isAppRunningNow()) {
            if(!SystemUtils.getNameActivityOnTopStack().equalsIgnoreCase("com.nectarbits.baraati.Chat.ui.activities.main.MainActivity") && !SystemUtils.getNameActivityOnTopStack().equalsIgnoreCase("com.nectarbits.baraati.Chat.ui.activities.chats.PrivateDialogActivity")){
                sendNotification(message);
            }
            return;
        }



        boolean chatPush = userId != 0 && !TextUtils.isEmpty(dialogId);

        if (chatPush) {
            saveOpeningDialogData(userId, dialogId);
            if (AppSession.getSession().getUser() != null && !isLoginNow) {
               // isLoginNow = true;
              /*  LoginHelper loginHelper = new LoginHelper(context);
                loginHelper.makeGeneralLogin(new GlobalLoginListener());*/
                sendNotification(message);
                return;
            }
        } else {
            // push about call
            Log.e(ChatNotificationHelper.class.getSimpleName(), "Message: "+message);
            if(message!=null) {
                sendNotification(message);
            }
        }

        saveOpeningDialog(false);
    }

    public void sendNotification(String message) {
        NotificationEvent notificationEvent = new NotificationEvent();
        notificationEvent.setTitle(context.getString(R.string.app_name));
        notificationEvent.setSubject(message);
        notificationEvent.setBody(message);

        NotificationManagerHelper.sendNotificationEvent(context, notificationEvent);
    }

    public void saveOpeningDialogData(int userId, String dialogId) {
        appSharedHelper.savePushUserId(userId);
        appSharedHelper.savePushDialogId(dialogId);
    }

    public void saveOpeningDialog(boolean open) {
        appSharedHelper.saveNeedToOpenDialog(open);
    }

    private boolean isPushForPrivateChat() {
        Dialog dialog = DataManager.getInstance().getDialogDataManager().getByDialogId(dialogId);
        return dialog != null && dialog.getType().equals(Dialog.Type.PRIVATE);
    }

    private class GlobalLoginListener extends SimpleGlobalLoginListener {

        @Override
        public void onCompleteQbChatLogin() {
            isLoginNow = false;

            saveOpeningDialog(true);

            Intent intent = SystemUtils.getPreviousIntent(context);
            if (!isPushForPrivateChat() || intent == null) {
                sendNotification(message);
            }
        }

        @Override
        public void onCompleteWithError(String error) {
            isLoginNow = false;

            saveOpeningDialog(false);
        }
    }
}