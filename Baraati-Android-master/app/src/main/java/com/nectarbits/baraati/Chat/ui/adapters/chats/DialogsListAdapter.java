package com.nectarbits.baraati.Chat.ui.adapters.chats;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nectarbits.baraati.R;
import com.nectarbits.baraati.Chat.ui.activities.base.BaseActivity;
import com.nectarbits.baraati.Chat.ui.adapters.base.BaseListAdapter;
import com.nectarbits.baraati.Chat.ui.views.roundedimageview.RoundedImageView;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.quickblox.q_municate_core.models.DialogWrapper;
import com.quickblox.q_municate_core.utils.ConstsCore;
import com.quickblox.q_municate_db.models.Dialog;
import com.quickblox.q_municate_db.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DialogsListAdapter extends BaseListAdapter<DialogWrapper> {

    public DialogsListAdapter(BaseActivity baseActivity, List<DialogWrapper> objectsList) {
        super(baseActivity, objectsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        DialogWrapper dialogWrapper = getItem(position);
        Dialog dialog = dialogWrapper.getDialog();

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_dialog, null);

            viewHolder = new ViewHolder();

            viewHolder.avatarImageView = (RoundedImageView) convertView.findViewById(R.id.avatar_imageview);
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.name_textview);
            viewHolder.lastMessageTextView = (TextView) convertView.findViewById(R.id.last_message_textview);
            viewHolder.unreadMessagesTextView = (TextView) convertView.findViewById(
                    R.id.unread_messages_textview);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if (Dialog.Type.PRIVATE.equals(dialog.getType())) {
            User opponentUser = dialogWrapper.getOpponentUser();
            if (opponentUser.getFullName() != null) {
                viewHolder.nameTextView.setText(opponentUser.getFullName());
                displayAvatarImage(opponentUser.getAvatar(), viewHolder.avatarImageView);
            } else {
                viewHolder.nameTextView.setText(resources.getString(R.string.deleted_user));
            }
        } else {
            viewHolder.nameTextView.setText(dialog.getTitle());
            viewHolder.avatarImageView.setImageResource(R.drawable.placeholder_group);
            displayGroupPhotoImage(dialog.getPhoto(), viewHolder.avatarImageView);
        }

        long totalCount = dialogWrapper.getTotalCount();

        if (totalCount > ConstsCore.ZERO_INT_VALUE) {
            viewHolder.unreadMessagesTextView.setText(totalCount + ConstsCore.EMPTY_STRING);
            viewHolder.unreadMessagesTextView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.unreadMessagesTextView.setVisibility(View.GONE);
        }

        if(isLink(dialogWrapper.getLastMessage()))
        {
            try {
                viewHolder.lastMessageTextView.setText(getLink(dialogWrapper.getLastMessage()).getString(AppUtils.ARG_PRODUCT_NAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            viewHolder.lastMessageTextView.setText(dialogWrapper.getLastMessage());
        }

        return convertView;
    }

    private static class ViewHolder {

        public RoundedImageView avatarImageView;
        public TextView nameTextView;
        public TextView lastMessageTextView;
        public TextView unreadMessagesTextView;
    }


    private Boolean isLink(String message){
        Boolean isLink=false;
        try {
            JSONObject jsonObject=new JSONObject(message);
            isLink=true;
        } catch (Exception e) {

            isLink=false;
        }

        return isLink;
    }

    private JSONObject getLink(String message){

        JSONObject jsonObject=null;
        try {
            jsonObject=new JSONObject(message);

        } catch (Exception e) {


        }

        return jsonObject;
    }
}