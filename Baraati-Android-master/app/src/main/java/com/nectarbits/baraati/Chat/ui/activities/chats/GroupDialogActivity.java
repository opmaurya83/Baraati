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

import com.nectarbits.baraati.Chat.ui.activities.main.MainActivity;
import com.nectarbits.baraati.Chat.utils.FileUtils;
import com.nectarbits.baraati.Chat.utils.listeners.simple.SimpleOnRecycleItemClickListener;
import com.nectarbits.baraati.Singletone.MessageForward;
import com.nectarbits.baraati.Singletone.ShareProductUtils;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.exception.QBResponseException;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.Chat.ui.adapters.chats.GroupDialogMessagesAdapter;
import com.quickblox.q_municate_core.models.AppSession;
import com.quickblox.q_municate_core.models.CombinationMessage;
import com.quickblox.q_municate_core.qb.commands.chat.QBUpdateStatusMessageCommand;
import com.quickblox.q_municate_core.qb.helpers.QBGroupChatHelper;
import com.quickblox.q_municate_core.qb.helpers.QBPrivateChatHelper;
import com.quickblox.q_municate_core.service.QBService;
import com.quickblox.q_municate_core.service.QBServiceConsts;
import com.quickblox.q_municate_core.utils.ChatUtils;
import com.quickblox.q_municate_db.models.Attachment;
import com.quickblox.q_municate_db.models.Dialog;
import com.quickblox.q_municate_db.models.State;
import com.quickblox.q_municate_db.models.User;
import com.quickblox.q_municate_db.utils.ErrorUtils;
import com.quickblox.users.model.QBUser;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.OnClick;

public class GroupDialogActivity extends BaseDialogActivity {

    private static final String TAG = GroupDialogActivity.class.getSimpleName();

    public static void start(Context context, ArrayList<User> friends) {
        Intent intent = new Intent(context, GroupDialogActivity.class);
        intent.putExtra(QBServiceConsts.EXTRA_FRIENDS, friends);
        context.startActivity(intent);
    }

    public static void start(Context context, Dialog dialog) {
        Intent intent = new Intent(context, GroupDialogActivity.class);
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

        initMessagesRecyclerView();
    }

    @Override
    protected void initMessagesRecyclerView() {
        super.initMessagesRecyclerView();
        messagesAdapter = new GroupDialogMessagesAdapter(this, combinationMessagesList, this, dialog);
        messagesRecyclerView.addItemDecoration(
                new StickyRecyclerHeadersDecoration((StickyRecyclerHeadersAdapter) messagesAdapter));
        messagesRecyclerView.setAdapter(messagesAdapter);

        scrollMessagesToBottom();

        messagesAdapter.setOnRecycleItemClickListener(new SimpleOnRecycleItemClickListener(){
            @Override
            public void onItemClicked(View view, Object entity, int position) {
                super.onItemClicked(view, entity, position);
                Log.e(TAG, "onItemClicked: "+position);
            }

            @Override
            public void onItemLongClicked(View view, Object entity, int position) {
                super.onItemLongClicked(view, entity, position);
                final CombinationMessage combinationMessage=(CombinationMessage)entity;
                Log.e(TAG, "onItemLongClicked: "+combinationMessage.getBody());

                final CharSequence colors[];
                if(combinationMessage.getAttachment()==null) {
                    colors = new CharSequence[]{getString(R.string.str_copy), getString(R.string.str_forward)};
                }else
                {
                    colors = new CharSequence[]{getString(R.string.str_forward)};
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
                        }else{


                            MessageForward.getInstance().setCombinationMessage(combinationMessage);
                            MessageForward.getInstance().setForward(true);
                            MainActivity.start(mContext);
                        }

                    }
                });
                builder.show();

            }
        });
    }

    @Override
    public void chatCreatLocally() {
        super.chatCreatLocally();
        Log.e(TAG, "chatCreatLocally:........"+ ShareProductUtils.getInstance().getProduct_id());
        Log.e(TAG, "chatCreatLocally:........"+(baseChatHelper != null)+" "+(dialog != null)+" "+!ShareProductUtils.getInstance().getProduct_id().equalsIgnoreCase("0"));
        if (baseChatHelper != null && dialog != null ) {
            if (!ShareProductUtils.getInstance().getProduct_id().equalsIgnoreCase("0")) {
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

                    ((QBGroupChatHelper) baseChatHelper).sendGroupMessage(dialog.getRoomJid(), jsonObject);
                    ShareProductUtils.getInstance().reset();
                } catch (QBResponseException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (MessageForward.getInstance().getForward()) {

                CombinationMessage combinationMessage = MessageForward.getInstance().getCombinationMessage();
                if (combinationMessage.getAttachment() == null) {
                    try {
                        ((QBGroupChatHelper) baseChatHelper).sendGroupMessage(dialog.getRoomJid(),combinationMessage.getBody());
                        MessageForward.getInstance().reset();
                    } catch (QBResponseException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (isLink(combinationMessage.getBody())) {

                    JSONObject jsonObject = getLink(combinationMessage.getBody());

                    try {
                        jsonObject.put(AppUtils.ARG_PRODUCT_ID, jsonObject.getString(AppUtils.ARG_PRODUCT_ID));
                        jsonObject.put(AppUtils.ARG_PRODUCT_NAME, jsonObject.getString(AppUtils.ARG_PRODUCT_NAME));
                        jsonObject.put(AppUtils.ARG_IMAGE_URL, jsonObject.getString(AppUtils.ARG_IMAGE_URL));
                        jsonObject.put(AppUtils.ARG_USER_EVENTTYPE_EVENTID, jsonObject.getString(AppUtils.ARG_USER_EVENTTYPE_EVENTID));
                        jsonObject.put(AppUtils.ARG_FOR_USER_ID, jsonObject.getString(AppUtils.ARG_FOR_USER_ID));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        ((QBGroupChatHelper) baseChatHelper).sendGroupMessage(dialog.getRoomJid(),jsonObject);
                        MessageForward.getInstance().reset();
                    } catch (QBResponseException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (combinationMessage.getAttachment().getType() == Attachment.Type.PICTURE) {
                    File file = new File(new FileUtils().getInitFolder() + "/" + combinationMessage.getAttachment().getAttachmentId() + ".jpg");
                    if (file.exists()) {
                        canPerformLogout.set(true);
                        startLoadAttachFile(file, dialog.getDialogId());
                        MessageForward.getInstance().reset();
                    }
                } else if (combinationMessage.getAttachment().getType() == Attachment.Type.VIDEO) {
                    File file = new File(new FileUtils().getInitFolder() + "/" + combinationMessage.getAttachment().getAttachmentId() + ".mp4");
                    if (file.exists()) {
                        canPerformLogout.set(true);
                        startLoadAttachFile(file, dialog.getDialogId());
                        MessageForward.getInstance().reset();
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateData();

        if (isNetworkAvailable()) {
            startLoadDialogMessages();
        }

        checkMessageSendingPossibility();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.group_dialog_menu, menu);
        return true;
    }

    @Override
    protected void onConnectServiceLocally(QBService service) {
        onConnectServiceLocally();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (GroupDialogDetailsActivity.UPDATE_DIALOG_REQUEST_CODE == requestCode && GroupDialogDetailsActivity.RESULT_LEAVE_GROUP == resultCode) {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onFileLoaded(QBFile file, String dialogId) {
        if(!dialogId.equals(dialog.getDialogId())){
            return;
        }

        try {
            ((QBGroupChatHelper) baseChatHelper).sendGroupMessageWithAttachImage(dialog.getRoomJid(), file);
        } catch (QBResponseException e) {
            ErrorUtils.showError(this, e);
        }
    }

    @Override
    protected Bundle generateBundleToInitDialog() {
        return null;
    }

    @Override
    protected void updateMessagesList() {
        int oldMessagesCount = messagesAdapter.getAllItems().size();

        this.combinationMessagesList = createCombinationMessagesList();
        processCombinationMessages();
        messagesAdapter.setList(combinationMessagesList);

        checkForScrolling(oldMessagesCount);
    }

    @Override
    protected void checkMessageSendingPossibility() {
        checkMessageSendingPossibility(isNetworkAvailable());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_group_details:
                GroupDialogDetailsActivity.start(this, dialog.getDialogId());
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void updateActionBar() {
        if (isNetworkAvailable() && dialog != null) {
            setActionBarTitle(dialog.getTitle());
            checkActionBarLogo(dialog.getPhoto(), R.drawable.placeholder_group);
        }
    }

    private void initFields() {
        chatHelperIdentifier = QBService.GROUP_CHAT_HELPER;
        dialog = (Dialog) getIntent().getExtras().getSerializable(QBServiceConsts.EXTRA_DIALOG);
        combinationMessagesList = createCombinationMessagesList();
        if (dialog != null)
        title = dialog.getTitle();
    }

    private void processCombinationMessages(){
        QBUser currentUser = AppSession.getSession().getUser();
        for (CombinationMessage cm :combinationMessagesList){
            boolean ownMessage = !cm.isIncoming(currentUser.getId());
            if (!State.READ.equals(cm.getState()) && !ownMessage && isNetworkAvailable()) {
                cm.setState(State.READ);
                QBUpdateStatusMessageCommand.start(this, ChatUtils.createQBDialogFromLocalDialog(dataManager, dialog), cm, false);
            } else if (ownMessage) {
                cm.setState(State.READ);
                dataManager.getMessageDataManager().update(cm.toMessage(), false);
            }
        }
    }

    public void sendMessage(View view) {
        sendMessage(false);
    }
}