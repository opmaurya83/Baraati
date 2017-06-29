package com.nectarbits.baraati.Chat.ui.activities.chats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.nectarbits.baraati.R;
import com.nectarbits.baraati.Chat.ui.activities.others.BaseFriendsListActivity;
import com.nectarbits.baraati.Chat.ui.adapters.friends.FriendsAdapter;
import com.nectarbits.baraati.Chat.ui.adapters.friends.SelectableFriendsAdapter;
import com.nectarbits.baraati.Chat.ui.views.recyclerview.SimpleDividerItemDecoration;
import com.nectarbits.baraati.Chat.utils.ToastUtils;
import com.nectarbits.baraati.Chat.utils.listeners.SelectUsersListener;
import com.nectarbits.baraati.Chat.utils.listeners.simple.SimpleOnRecycleItemClickListener;
import com.quickblox.q_municate_db.models.User;

import java.util.List;

import butterknife.Bind;

public class NewGroupDialogActivity extends BaseFriendsListActivity implements SelectUsersListener {

    @Bind(R.id.members_edittext)
    EditText membersEditText;

    public static void start(Context context) {
        Intent intent = new Intent(context, NewGroupDialogActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_new_group;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFields();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpActionBarWithUpButton();
        initCustomListeners();
    }

    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        ((SelectableFriendsAdapter) friendsAdapter).setSelectUsersListener(this);
        friendsRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));;
    }

    @Override
    protected FriendsAdapter getFriendsAdapter() {
        return new SelectableFriendsAdapter(this, getFriendsList(), true);
    }

    @Override
    protected void performDone() {
        List<User> selectedFriendsList = ((SelectableFriendsAdapter) friendsAdapter).getSelectedFriendsList();
        if (!selectedFriendsList.isEmpty()) {
            CreateGroupDialogActivity.start(this, selectedFriendsList);
        } else {
            ToastUtils.longToast(R.string.new_group_no_friends_for_creating_group);
        }
    }

    @Override
    public void onSelectedUsersChanged(int count, String fullNames) {
        membersEditText.setText(fullNames);
    }

    private void initFields() {
        title = getString(R.string.new_group_title);
    }

    private void initCustomListeners() {
        friendsAdapter.setOnRecycleItemClickListener(new SimpleOnRecycleItemClickListener<User>() {

            @Override
            public void onItemClicked(View view, User entity, int position) {
                ((SelectableFriendsAdapter) friendsAdapter).selectFriend(position);
            }
        });
    }
}