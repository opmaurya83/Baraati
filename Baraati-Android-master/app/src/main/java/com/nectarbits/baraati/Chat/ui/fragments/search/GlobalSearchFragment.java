package com.nectarbits.baraati.Chat.ui.fragments.search;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.Chat.ui.activities.profile.UserProfileActivity;
import com.nectarbits.baraati.Chat.ui.adapters.search.GlobalSearchAdapter;
import com.nectarbits.baraati.Chat.ui.fragments.base.BaseFragment;
import com.nectarbits.baraati.Chat.ui.fragments.dialogs.base.OneButtonDialogFragment;
import com.nectarbits.baraati.Chat.ui.views.recyclerview.SimpleDividerItemDecoration;
import com.nectarbits.baraati.Chat.utils.KeyboardUtils;
import com.nectarbits.baraati.Chat.utils.listeners.SearchListener;
import com.nectarbits.baraati.Chat.utils.listeners.UserOperationListener;
import com.nectarbits.baraati.Chat.utils.listeners.simple.SimpleOnRecycleItemClickListener;
import com.quickblox.q_municate_core.core.command.Command;
import com.quickblox.q_municate_core.models.AppSession;
import com.quickblox.q_municate_core.qb.commands.QBFindUsersCommand;
import com.quickblox.q_municate_core.qb.commands.friend.QBAddFriendCommand;
import com.quickblox.q_municate_core.service.QBService;
import com.quickblox.q_municate_core.service.QBServiceConsts;
import com.quickblox.q_municate_core.utils.UserFriendUtils;
import com.quickblox.q_municate_db.managers.DataManager;
import com.quickblox.q_municate_db.managers.FriendDataManager;
import com.quickblox.q_municate_db.managers.UserRequestDataManager;
import com.quickblox.q_municate_db.models.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnTouch;

public class GlobalSearchFragment extends BaseFragment implements SearchListener, SwipyRefreshLayout.OnRefreshListener {

    private static final int SEARCH_DELAY = 1000;
    private static final int MIN_VALUE_FOR_SEARCH = 3;

    @Bind(R.id.contacts_swipyrefreshlayout)
    SwipyRefreshLayout swipyRefreshLayout;

    @Bind(R.id.contacts_recyclerview)
    RecyclerView contactsRecyclerView;

    private Timer searchTimer;
    private int page = 1;
    private int totalEntries;
    private UserOperationAction userOperationAction;
    private DataManager dataManager;
    private Observer commonObserver;
    private GlobalSearchAdapter globalSearchAdapter;
    private List<User> usersList;
    private String searchQuery;
    private boolean excludedMe;

    public static GlobalSearchFragment newInstance() {
        return new GlobalSearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_global_search, container, false);

        activateButterKnife(view);

        initFields();
        initContactsList(usersList);
        initCustomListeners();

        addActions();
        addObservers();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        globalSearchAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeActions();
        deleteObservers();
    }

    @OnTouch(R.id.contacts_recyclerview)
    boolean touchContactsList(View view, MotionEvent event) {
        KeyboardUtils.hideKeyboard(baseActivity);
        return false;
    }

    @Override
    public void prepareSearch() {
        clearOldData();

//в
    }

    @Override
    public void search(String searchQuery) {
        this.searchQuery = searchQuery;
        clearOldData();

        if (!baseActivity.checkNetworkAvailableWithError()) {
            return;
        }

        startSearch();
        if (globalSearchAdapter != null && !globalSearchAdapter.getAllItems().isEmpty()) {
            globalSearchAdapter.setFilter(searchQuery);
        }
    }

    @Override
    public void cancelSearch() {
        searchQuery = null;
        searchTimer.cancel();
        clearOldData();

        if (globalSearchAdapter != null) {
            updateList();
        }
    }

    @Override
    public void onRefresh(SwipyRefreshLayoutDirection swipyRefreshLayoutDirection) {
        if (!usersList.isEmpty() && usersList.size() < totalEntries) {
            page++;
            searchUsers();
        }
    }

    @Override
    public void onConnectedToService(QBService service) {
        super.onConnectedToService(service);
        if (friendListHelper != null && globalSearchAdapter != null) {
            globalSearchAdapter.setFriendListHelper(friendListHelper);
        }
    }

    @Override
    public void onChangedUserStatus(int userId, boolean online) {
        super.onChangedUserStatus(userId, online);
        globalSearchAdapter.notifyDataSetChanged();
    }

    private void initFields() {
        dataManager = DataManager.getInstance();
        searchTimer = new Timer();
        usersList = new ArrayList<>();
        userOperationAction = new UserOperationAction();
        commonObserver = new CommonObserver();
        swipyRefreshLayout.setEnabled(false);
    }

    private void initContactsList(List<User> usersList) {
        globalSearchAdapter = new GlobalSearchAdapter(baseActivity, usersList);
        globalSearchAdapter.setFriendListHelper(friendListHelper);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactsRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));;
        contactsRecyclerView.setAdapter(globalSearchAdapter);
        globalSearchAdapter.setUserOperationListener(userOperationAction);
    }

    private void initCustomListeners() {
        globalSearchAdapter.setOnRecycleItemClickListener(new SimpleOnRecycleItemClickListener<User>() {

            @Override
            public void onItemClicked(View view, User user, int position) {
                boolean isFriend = dataManager.getFriendDataManager().existsByUserId(user.getUserId());
                boolean outgoingUser = dataManager.getUserRequestDataManager()
                        .existsByUserId(user.getUserId());
                if (isFriend || outgoingUser) {
                    UserProfileActivity.start(baseActivity, user.getUserId());
                }
            }
        });

        swipyRefreshLayout.setOnRefreshListener(this);
    }

    private void updateList() {
        globalSearchAdapter.setList(usersList);
    }

    private void updateContactsList(List<User> usersList) {
        this.usersList = usersList;
        globalSearchAdapter.setList(usersList);
        globalSearchAdapter.setFilter(searchQuery);
    }

    private void removeActions() {
        baseActivity.removeAction(QBServiceConsts.FIND_USERS_SUCCESS_ACTION);
        baseActivity.removeAction(QBServiceConsts.FIND_USERS_FAIL_ACTION);

        baseActivity.removeAction(QBServiceConsts.ADD_FRIEND_SUCCESS_ACTION);
        baseActivity.removeAction(QBServiceConsts.ADD_FRIEND_FAIL_ACTION);

        baseActivity.updateBroadcastActionList();
    }

    private void addActions() {
        baseActivity.addAction(QBServiceConsts.FIND_USERS_SUCCESS_ACTION, new FindUserSuccessAction());
        baseActivity.addAction(QBServiceConsts.FIND_USERS_FAIL_ACTION, new FindUserFailAction());

        baseActivity.addAction(QBServiceConsts.ADD_FRIEND_SUCCESS_ACTION, new AddFriendSuccessAction());
        baseActivity.addAction(QBServiceConsts.ADD_FRIEND_FAIL_ACTION, failAction);

        baseActivity.updateBroadcastActionList();
    }

    private void addObservers() {
        dataManager.getUserRequestDataManager().addObserver(commonObserver);
        dataManager.getFriendDataManager().addObserver(commonObserver);
    }

    private void deleteObservers() {
        if(dataManager != null) {
            dataManager.getUserRequestDataManager().deleteObserver(commonObserver);
            dataManager.getFriendDataManager().deleteObserver(commonObserver);
        }
    }

    private void clearOldData() {
        usersList.clear();
        page = 1;
        excludedMe = false;
    }

    private void startSearch() {
        searchTimer.cancel();
        searchTimer = new Timer();
        searchTimer.schedule(new SearchTimerTask(), SEARCH_DELAY);
    }

    private void searchUsers() {
        if (!TextUtils.isEmpty(searchQuery) && checkSearchDataWithError(searchQuery)) {
            QBFindUsersCommand.start(baseActivity, AppSession.getSession().getUser(), searchQuery, page);
        }
    }

    private boolean checkSearchDataWithError(String searchQuery) {
        boolean correct = searchQuery != null && searchQuery.length() >= MIN_VALUE_FOR_SEARCH;
        if (correct) {
            return true;
        } else {
            OneButtonDialogFragment.show(getChildFragmentManager(), R.string.search_at_last_items, true);
            return false;
        }
    }

    private void addToFriendList(final int userId) {
        if (!baseActivity.checkNetworkAvailableWithError()) {
            return;
        }

        baseActivity.showProgress();
        QBAddFriendCommand.start(baseActivity, userId);
        KeyboardUtils.hideKeyboard(baseActivity);
    }

    private void checkForEnablingRefreshLayout() {
        swipyRefreshLayout.setEnabled(usersList.size() != totalEntries);
    }

    private void parseResult(Bundle bundle) {
        String searchQuery = bundle.getString(QBServiceConsts.EXTRA_CONSTRAINT);
        totalEntries = bundle.getInt(QBServiceConsts.EXTRA_TOTAL_ENTRIES);

        if (excludedMe) {
            totalEntries--;
        }

        if (GlobalSearchFragment.this.searchQuery.equals(searchQuery)) {
            Collection<User> newUsersCollection = (Collection<User>) bundle.getSerializable(QBServiceConsts.EXTRA_USERS);
            if (newUsersCollection != null && !newUsersCollection.isEmpty()) {
                checkForExcludeMe(newUsersCollection);

                usersList.addAll(newUsersCollection);

                updateContactsList(usersList);
            }
        } else {
            search(GlobalSearchFragment.this.searchQuery);
        }
    }

    private void checkForExcludeMe(Collection<User> usersCollection) {
        User me = UserFriendUtils.createLocalUser(AppSession.getSession().getUser());
        if (usersCollection.contains(me)) {
            usersCollection.remove(me);
            excludedMe = true;
            totalEntries--;
        }
    }

    private class SearchTimerTask extends TimerTask {

        @Override
        public void run() {
            searchUsers();
        }
    }

    private class FindUserSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) {
            parseResult(bundle);

            swipyRefreshLayout.setRefreshing(false);
            checkForEnablingRefreshLayout();
        }
    }

    private class FindUserFailAction implements Command {

        @Override
        public void execute(Bundle bundle) {
            OneButtonDialogFragment.show(getChildFragmentManager(), R.string.search_users_not_found, true);
            usersList.clear();

            swipyRefreshLayout.setRefreshing(false);
            checkForEnablingRefreshLayout();
        }
    }

    private class UserOperationAction implements UserOperationListener {

        @Override
        public void onAddUserClicked(int userId) {
            addToFriendList(userId);
        }
    }

    private class AddFriendSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) {
            int userId = bundle.getInt(QBServiceConsts.EXTRA_FRIEND_ID);

            User addedUser = dataManager.getUserDataManager().get(userId);
            globalSearchAdapter.notifyDataSetChanged();

            baseActivity.hideProgress();
        }
    }

    private class CommonObserver implements Observer {

        @Override
        public void update(Observable observable, Object data) {
            if (data != null) {
                if (data.equals(UserRequestDataManager.OBSERVE_KEY) || data.equals(FriendDataManager.OBSERVE_KEY)) {
                    updateList();
                }
            }
        }
    }
}