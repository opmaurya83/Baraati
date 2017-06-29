package com.nectarbits.baraati.Chat.ui.activities.chats;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nectarbits.baraati.Chat.ui.activities.main.MainActivity;
import com.nectarbits.baraati.Chat.utils.FileUtils;
import com.nectarbits.baraati.Chat.utils.listeners.simple.SimpleOnRecycleItemClickListener;
import com.nectarbits.baraati.Singletone.MessageForward;
import com.nectarbits.baraati.Singletone.ShareProductUtils;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.exception.QBResponseException;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.Chat.ui.activities.call.CallActivity;
import com.nectarbits.baraati.Chat.ui.activities.profile.UserProfileActivity;
import com.nectarbits.baraati.Chat.ui.adapters.chats.PrivateDialogMessagesAdapter;
import com.nectarbits.baraati.Chat.ui.fragments.dialogs.base.TwoButtonsDialogFragment;
import com.nectarbits.baraati.Chat.utils.DateUtils;
import com.nectarbits.baraati.Chat.utils.ToastUtils;
import com.nectarbits.baraati.Chat.utils.listeners.FriendOperationListener;
import com.quickblox.q_municate_core.core.command.Command;
import com.quickblox.q_municate_core.models.CombinationMessage;
import com.quickblox.q_municate_core.qb.commands.friend.QBAcceptFriendCommand;
import com.quickblox.q_municate_core.qb.commands.friend.QBRejectFriendCommand;
import com.quickblox.q_municate_core.qb.helpers.QBPrivateChatHelper;
import com.quickblox.q_municate_core.service.QBService;
import com.quickblox.q_municate_core.service.QBServiceConsts;
import com.quickblox.q_municate_core.utils.ChatUtils;
import com.quickblox.q_municate_core.utils.OnlineStatusUtils;
import com.quickblox.q_municate_core.utils.UserFriendUtils;
import com.quickblox.q_municate_db.managers.DataManager;
import com.quickblox.q_municate_db.managers.FriendDataManager;
import com.quickblox.q_municate_db.models.Attachment;
import com.quickblox.q_municate_db.models.Dialog;
import com.quickblox.q_municate_db.models.Message;
import com.quickblox.q_municate_db.models.User;
import com.quickblox.q_municate_db.utils.ErrorUtils;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCTypes;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.OnClick;

public class PrivateDialogActivity extends BaseDialogActivity {

    private FriendOperationAction friendOperationAction;
    private FriendObserver friendObserver;
    private int operationItemPosition;
    private final String TAG = "PrivateDialogActivity";

    public static void start(Context context, User opponent, Dialog dialog) {
        Intent intent = new Intent(context, PrivateDialogActivity.class);
        intent.putExtra(QBServiceConsts.EXTRA_OPPONENT, opponent);
        intent.putExtra(QBServiceConsts.EXTRA_DIALOG, dialog);
        context.startActivity(intent);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initFields();

        if (dialog == null) {
            finish();
        }

        setUpActionBarWithUpButton();

        if (isNetworkAvailable()) {
            deleteTempMessages();
        }

        addObservers();

        initMessagesRecyclerView();



    }


    @Override
    public void chatCreatLocally() {
        super.chatCreatLocally();
        Log.e(TAG, "chatCreatLocally:........"+ ShareProductUtils.getInstance().getProduct_id());
        Log.e(TAG, "chatCreatLocally:........"+(baseChatHelper != null)+" "+(dialog != null)+" "+!ShareProductUtils.getInstance().getProduct_id().equalsIgnoreCase("0"));
        if (baseChatHelper != null && dialog != null) {
            if(!ShareProductUtils.getInstance().getProduct_id().equalsIgnoreCase("0")) {
                try {
                    Log.e(TAG, "onCreate: message send");
                    JSONObject jsonObject = new JSONObject();

                    try {
                        jsonObject.put(AppUtils.ARG_PRODUCT_ID, ShareProductUtils.getInstance().getProduct_id());
                        jsonObject.put(AppUtils.ARG_PRODUCT_NAME, ShareProductUtils.getInstance().getProduct_name());
                        jsonObject.put(AppUtils.ARG_IMAGE_URL, ShareProductUtils.getInstance().getImage_ulr());
                        jsonObject.put(AppUtils.ARG_USER_EVENTTYPE_EVENTID, ShareProductUtils.getInstance().getmUserEventTypeEventID());
                        jsonObject.put(AppUtils.ARG_FOR_USER_ID, ShareProductUtils.getInstance().getmFor_USER_ID());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ((QBPrivateChatHelper) baseChatHelper).sendPrivateMessage(jsonObject, opponentUser.getUserId());
                    ShareProductUtils.getInstance().reset();
                } catch (QBResponseException e) {
                    e.printStackTrace();
                }
            }else if(MessageForward.getInstance().getForward()){

                CombinationMessage combinationMessage=MessageForward.getInstance().getCombinationMessage();
                if(combinationMessage.getAttachment()==null) {
                    try {
                        ((QBPrivateChatHelper) baseChatHelper).sendPrivateMessage(combinationMessage.getBody(), opponentUser.getUserId());
                        MessageForward.getInstance().reset();
                    } catch (QBResponseException e) {
                        e.printStackTrace();
                    }
                }else if(isLink(combinationMessage.getBody())){

                    JSONObject jsonObject = getLink(combinationMessage.getBody());

                    try {
                        jsonObject.put(AppUtils.ARG_PRODUCT_ID, jsonObject.getString(AppUtils.ARG_PRODUCT_ID));
                        jsonObject.put(AppUtils.ARG_PRODUCT_NAME,  jsonObject.getString(AppUtils.ARG_PRODUCT_NAME));
                        jsonObject.put(AppUtils.ARG_IMAGE_URL, jsonObject.getString(AppUtils.ARG_IMAGE_URL));
                        jsonObject.put(AppUtils.ARG_USER_EVENTTYPE_EVENTID,  jsonObject.getString(AppUtils.ARG_USER_EVENTTYPE_EVENTID));
                        jsonObject.put(AppUtils.ARG_FOR_USER_ID, jsonObject.getString(AppUtils.ARG_FOR_USER_ID));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        ((QBPrivateChatHelper) baseChatHelper).sendPrivateMessage(jsonObject, opponentUser.getUserId());
                        MessageForward.getInstance().reset();
                    } catch (QBResponseException e) {
                        e.printStackTrace();
                    }
                }else if(combinationMessage.getAttachment().getType()== Attachment.Type.PICTURE){
                    File file=new File(new FileUtils().getInitFolder()+"/"+combinationMessage.getAttachment().getAttachmentId()+".jpg");
                    if(file.exists()){
                        canPerformLogout.set(true);
                        startLoadAttachFile(file, dialog.getDialogId());
                        MessageForward.getInstance().reset();
                    }
                }else if(combinationMessage.getAttachment().getType()== Attachment.Type.VIDEO){
                    File file=new File(new FileUtils().getInitFolder()+"/"+combinationMessage.getAttachment().getAttachmentId()+".mp4");
                    if(file.exists()){
                        canPerformLogout.set(true);
                        startLoadAttachFile(file, dialog.getDialogId());
                        MessageForward.getInstance().reset();
                    }
                }
            }
        }

    }

    @Override
    protected void addActions() {
        super.addActions();

        addAction(QBServiceConsts.ACCEPT_FRIEND_SUCCESS_ACTION, new AcceptFriendSuccessAction());
        addAction(QBServiceConsts.ACCEPT_FRIEND_FAIL_ACTION, failAction);

        addAction(QBServiceConsts.REJECT_FRIEND_SUCCESS_ACTION, new RejectFriendSuccessAction());
        addAction(QBServiceConsts.REJECT_FRIEND_FAIL_ACTION, failAction);

        updateBroadcastActionList();
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkForCorrectChat();

        if (isNetworkAvailable()) {
            startLoadDialogMessages();
        }

        checkMessageSendingPossibility();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteObservers();
    }

    @Override
    protected void updateActionBar() {
        setOnlineStatus(opponentUser);

        checkActionBarLogo(opponentUser.getAvatar(), R.drawable.placeholder_user);
    }

    @Override
    protected void onConnectServiceLocally(QBService service) {
        onConnectServiceLocally();
        setOnlineStatus(opponentUser);
    }

    @Override
    protected void onFileLoaded(QBFile file, String dialogId) {
        if(!dialogId.equals(dialog.getDialogId())){
            return;
        }

        try {
            privateChatHelper.sendPrivateMessageWithAttachImage(file, opponentUser.getUserId());
        } catch (QBResponseException exc) {
            ErrorUtils.showError(this, exc);
        }
    }

    @Override
    protected Bundle generateBundleToInitDialog() {
        Bundle bundle = new Bundle();
        bundle.putInt(QBServiceConsts.EXTRA_OPPONENT_ID, opponentUser.getUserId());
        return bundle;
    }

    @Override
    protected void initMessagesRecyclerView() {
        super.initMessagesRecyclerView();
        messagesAdapter = new PrivateDialogMessagesAdapter(this, friendOperationAction, combinationMessagesList, this, dialog);
        messagesRecyclerView.addItemDecoration(
                new StickyRecyclerHeadersDecoration((StickyRecyclerHeadersAdapter) messagesAdapter));
        findLastFriendsRequest();

        messagesRecyclerView.setAdapter(messagesAdapter);

        scrollMessagesToBottom();

        messagesAdapter.setOnRecycleItemClickListener(new SimpleOnRecycleItemClickListener(){
            @Override
            public void onItemClicked(View view, Object entity, int position) {
                super.onItemClicked(view, entity, position);
                Log.e(TAG, "onItemClicked: "+position);
            }

            @Override
            public void onItemLongClicked(View view, Object entity, final int position) {
                super.onItemLongClicked(view, entity, position);
                final CombinationMessage combinationMessage=(CombinationMessage)entity;
                Log.e(TAG, "onItemLongClicked: "+combinationMessage.getBody());

                final CharSequence colors[];
                if(combinationMessage.getAttachment()==null) {
                    colors = new CharSequence[]{getString(R.string.str_copy), getString(R.string.str_forward),getString(R.string.str_delete)};
                }else
                {
                    colors = new CharSequence[]{getString(R.string.str_forward),getString(R.string.str_delete)};
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle(getString(R.string.str_msg));
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(colors[which].toString().equalsIgnoreCase(getString(R.string.str_copy))){
                            int sdk = android.os.Build.VERSION.SDK_INT;
                            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mContext
                                        .getSystemService(mContext.CLIPBOARD_SERVICE);
                                clipboard.setText(combinationMessage.getBody());
                            } else {
                                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mContext
                                        .getSystemService(mContext.CLIPBOARD_SERVICE);
                                android.content.ClipData clip = android.content.ClipData
                                        .newPlainText("text", combinationMessage.getBody());
                                clipboard.setPrimaryClip(clip);
                            }
                        }else if(colors[which].toString().equalsIgnoreCase(getString(R.string.str_forward))){


                            MessageForward.getInstance().setCombinationMessage(combinationMessage);
                            MessageForward.getInstance().setForward(true);
                            MainActivity.start(mContext);
                        }else{


                            dataManager.getMessageDataManager().deleteByMessageId(combinationMessagesList.get(position).getMessageId());
                            messagesAdapter.notifyDataSetChanged();
                        }

                    }
                });
                builder.show();

            }
        });

    }

    @Override
    protected void updateMessagesList() {
        initActualExtras();
        checkForCorrectChat();

        int oldMessagesCount = messagesAdapter.getAllItems().size();

        this.combinationMessagesList = createCombinationMessagesList();
        Log.d(TAG, "combinationMessagesList = " + combinationMessagesList);
        messagesAdapter.setList(combinationMessagesList);
        findLastFriendsRequest();

        checkForScrolling(oldMessagesCount);
    }

    private void initActualExtras() {
        opponentUser = (User) getIntent().getExtras().getSerializable(QBServiceConsts.EXTRA_OPPONENT);
        dialog = (Dialog) getIntent().getExtras().getSerializable(QBServiceConsts.EXTRA_DIALOG);
    }

    @Override
    public void notifyChangedUserStatus(int userId, boolean online) {
        super.notifyChangedUserStatus(userId, online);

        if (opponentUser != null && opponentUser.getUserId() == userId) {
            setOnlineStatus(opponentUser);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.private_dialog_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean isFriend = DataManager.getInstance().getFriendDataManager().getByUserId(
                opponentUser.getUserId()) != null;
        if (!isFriend && item.getItemId() != android.R.id.home) {
            ToastUtils.longToast(R.string.dialog_user_is_not_friend);
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_audio_call:
                callToUser(opponentUser, QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO);
                break;
            case R.id.switch_camera_toggle:
                callToUser(opponentUser, QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void checkMessageSendingPossibility() {
       // boolean enable = dataManager.getFriendDataManager().existsByUserId(opponentUser.getUserId()) && isNetworkAvailable();
        boolean enable = true;
        checkMessageSendingPossibility(enable);
    }

    @OnClick(R.id.toolbar)
    void openProfile(View view) {
        UserProfileActivity.start(this, opponentUser.getUserId());
    }

    private void initFields() {
        chatHelperIdentifier = QBService.PRIVATE_CHAT_HELPER;
        friendOperationAction = new FriendOperationAction();
        friendObserver = new FriendObserver();
        initActualExtras();
//        opponentUser = (User) getIntent().getExtras().getSerializable(QBServiceConsts.EXTRA_OPPONENT);
//        dialog = (Dialog) getIntent().getExtras().getSerializable(QBServiceConsts.EXTRA_DIALOG);
        combinationMessagesList = createCombinationMessagesList();
        title = opponentUser.getFullName();
    }

    private void addObservers() {
        dataManager.getFriendDataManager().addObserver(friendObserver);
    }

    private void deleteObservers() {
        dataManager.getFriendDataManager().deleteObserver(friendObserver);
    }

    private void findLastFriendsRequest() {
        ((PrivateDialogMessagesAdapter) messagesAdapter).findLastFriendsRequestMessagesPosition();
        messagesAdapter.notifyDataSetChanged();
    }

    private void setOnlineStatus(User user) {
        if (user != null) {
            if (friendListHelper != null) {
                /*Disable last seen*/
               /* String offlineStatus = getString(R.string.last_seen, DateUtils.toTodayYesterdayShortDateWithoutYear2(user.getLastLogin()),
                        DateUtils.formatDateSimpleTime(user.getLastLogin()));
                setActionBarSubtitle(
                        OnlineStatusUtils.getOnlineStatus(this, friendListHelper.isUserOnline(user.getUserId()), offlineStatus));*/
            }
        }
    }

    public void sendMessage(View view) {
        sendMessage(true);
    }

    private void callToUser(User user, QBRTCTypes.QBConferenceType qbConferenceType) {
        if (!isChatInitializedAndUserLoggedIn()) {
            ToastUtils.longToast(R.string.call_chat_service_is_initializing);
            return;
        }
        List<QBUser> qbUserList = new ArrayList<>(1);
        qbUserList.add(UserFriendUtils.createQbUser(user));
        CallActivity.start(PrivateDialogActivity.this, qbUserList, qbConferenceType, null);
    }

    private void acceptUser(final int userId) {
        if (isNetworkAvailable()) {
            if (!isChatInitializedAndUserLoggedIn()) {
                ToastUtils.longToast(R.string.call_chat_service_is_initializing);
                return;
            }

            showProgress();
            QBAcceptFriendCommand.start(this, userId);
        } else {
            ToastUtils.longToast(R.string.dlg_fail_connection);
            return;
        }
    }

    private void rejectUser(final int userId) {
        if (isNetworkAvailable()) {
            if (!isChatInitializedAndUserLoggedIn()) {
                ToastUtils.longToast(R.string.call_chat_service_is_initializing);
                return;
            }

            showRejectUserDialog(userId);
        } else {
            ToastUtils.longToast(R.string.dlg_fail_connection);
            return;
        }
    }

    private void showRejectUserDialog(final int userId) {
        User user = DataManager.getInstance().getUserDataManager().get(userId);
        if (user == null) {
            return;
        }

        TwoButtonsDialogFragment.show(getSupportFragmentManager(),
                getString(R.string.dialog_message_reject_friend, user.getFullName()),
                new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        showProgress();
                        QBRejectFriendCommand.start(PrivateDialogActivity.this, userId);
                    }
                });
    }

    private void checkForCorrectChat() {
        Dialog updatedDialog = null;
        if (dialog != null) {
            updatedDialog = dataManager.getDialogDataManager().getByDialogId(dialog.getDialogId());
        } else {
            finish();
        }

        if (updatedDialog == null) {
            finish();
        } else {
            dialog = updatedDialog;
        }
    }

    private class FriendOperationAction implements FriendOperationListener {

        @Override
        public void onAcceptUserClicked(int position, int userId) {
            operationItemPosition = position;
            acceptUser(userId);
        }

        @Override
        public void onRejectUserClicked(int position, int userId) {
            operationItemPosition = position;
            rejectUser(userId);
        }
    }

    private class AcceptFriendSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) {
            ((PrivateDialogMessagesAdapter) messagesAdapter).clearLastRequestMessagePosition();
            messagesAdapter.notifyItemChanged(operationItemPosition);
            startLoadDialogMessages();
            hideProgress();
        }
    }

    private class RejectFriendSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) {
            ((PrivateDialogMessagesAdapter) messagesAdapter).clearLastRequestMessagePosition();
            messagesAdapter.notifyItemChanged(operationItemPosition);
            startLoadDialogMessages();
            hideProgress();
        }
    }

    private class FriendObserver implements Observer {

        @Override
        public void update(Observable observable, Object data) {
            if (data != null && data.equals(FriendDataManager.OBSERVE_KEY)) {
                checkForCorrectChat();
                checkMessageSendingPossibility();
            }
        }
    }
}