package com.nectarbits.baraati.Chat.ui.adapters.chats;

import android.view.View;
import android.view.ViewGroup;

import com.nectarbits.baraati.R;
import com.nectarbits.baraati.Chat.ui.activities.base.BaseActivity;
import com.nectarbits.baraati.Chat.ui.adapters.base.BaseClickListenerViewHolder;
import com.nectarbits.baraati.Chat.utils.DateUtils;
import com.nectarbits.baraati.Chat.utils.listeners.ChatUIHelperListener;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.quickblox.q_municate_core.models.CombinationMessage;
import com.quickblox.q_municate_db.models.Attachment;
import com.quickblox.q_municate_db.models.Dialog;
import com.quickblox.q_municate_db.models.State;

import java.util.List;

public class GroupDialogMessagesAdapter extends BaseDialogMessagesAdapter {

    public GroupDialogMessagesAdapter(BaseActivity baseActivity, List<CombinationMessage> objectsList,
            ChatUIHelperListener chatUIHelperListener, Dialog dialog) {
        super(baseActivity, objectsList);
        this.chatUIHelperListener = chatUIHelperListener;
        this.dialog = dialog;
    }

    @Override
    public PrivateDialogMessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case TYPE_REQUEST_MESSAGE:
                return new ViewHolder(this, layoutInflater.inflate(R.layout.item_notification_message, viewGroup, false));
            case TYPE_OWN_MESSAGE:
                return new ViewHolder(this, layoutInflater.inflate(R.layout.item_message_own, viewGroup, false));
            case TYPE_OPPONENT_MESSAGE:
                return new ViewHolder(this, layoutInflater.inflate(R.layout.item_group_message_opponent, viewGroup, false));
            default:
                return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(getItem(position));
    }

    @Override
    public void onBindViewHolder(BaseClickListenerViewHolder<CombinationMessage> baseClickListenerViewHolder, int position) {
        ViewHolder viewHolder = (ViewHolder) baseClickListenerViewHolder;

        CombinationMessage combinationMessage = getItem(position);
        boolean ownMessage = !combinationMessage.isIncoming(currentUser.getId());
        boolean notificationMessage = combinationMessage.getNotificationType() != null;
        boolean isLink=isLink(combinationMessage.getBody());
        String avatarUrl = null;
        String senderName;

        if (viewHolder.verticalProgressBar != null) {
            viewHolder.verticalProgressBar.setProgressDrawable(baseActivity.getResources().getDrawable(R.drawable.vertical_progressbar));
        }

        if (notificationMessage) {
            viewHolder.messageTextView.setText(combinationMessage.getBody());
            viewHolder.timeTextMessageTextView.setText(DateUtils.formatDateSimpleTime(combinationMessage.getCreatedDate()));
        } else {

            resetUI(viewHolder);

            if (ownMessage) {
                avatarUrl = combinationMessage.getDialogOccupant().getUser().getAvatar();
            } else {
                senderName = combinationMessage.getDialogOccupant().getUser().getFullName();
                avatarUrl = combinationMessage.getDialogOccupant().getUser().getAvatar();
                viewHolder.nameTextView.setTextColor(colorUtils.getRandomTextColorById(combinationMessage.getDialogOccupant().getUser().getUserId()));
                viewHolder.nameTextView.setText(senderName);
            }

            if (combinationMessage.getAttachment() != null) {

                viewHolder.timeAttachMessageTextView.setText(DateUtils.formatDateSimpleTime(combinationMessage.getCreatedDate()));
                setViewVisibility(viewHolder.progressRelativeLayout, View.VISIBLE);
                if(combinationMessage.getAttachment().getType()== Attachment.Type.PICTURE) {
                    displayAttachImageById(combinationMessage.getAttachment().getAttachmentId(), viewHolder);
                }else{
                    displayAttachVideoById(combinationMessage.getAttachment().getAttachmentId(), viewHolder);
                }
            }else if (isLink) {
                viewHolder.timeProductMessageTextView.setText(DateUtils.formatDateSimpleTime(combinationMessage.getCreatedDate()));
                setViewVisibility(viewHolder.progressRelativeLayout, View.VISIBLE);
                displayProductImage(getLink(combinationMessage.getBody(), AppUtils.ARG_IMAGE_URL),viewHolder,getLink(combinationMessage.getBody()));
                viewHolder.product_name_textview.setText(getLink(combinationMessage.getBody(),AppUtils.ARG_PRODUCT_NAME));

            } else {
                setViewVisibility(viewHolder.textMessageView, View.VISIBLE);
                viewHolder.timeTextMessageTextView.setText(DateUtils.formatDateSimpleTime(combinationMessage.getCreatedDate()));
                viewHolder.messageTextView.setText(combinationMessage.getBody());
            }
        }

        displayAvatarImage(avatarUrl, viewHolder.avatarImageView);
    }
}