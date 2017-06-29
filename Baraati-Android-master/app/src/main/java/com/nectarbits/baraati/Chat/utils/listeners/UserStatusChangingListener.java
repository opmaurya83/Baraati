package com.nectarbits.baraati.Chat.utils.listeners;

public interface UserStatusChangingListener {

    void onChangedUserStatus(int userId, boolean online);
}