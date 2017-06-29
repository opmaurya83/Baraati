package com.nectarbits.baraati.Chat.ui.activities.chats;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.nectarbits.baraati.Models.UserList.ResultItem;
import com.nectarbits.baraati.Models.UserList.UserDetail;
import com.nectarbits.baraati.Models.UserList.UserListModel;
import com.nectarbits.baraati.Singletone.UserDetails;
import com.nectarbits.baraati.Utils.Contact;
import com.nectarbits.baraati.Utils.ContactsProvider;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nectarbits.baraati.generalHelper.ProgressDialog;
import com.nectarbits.baraati.generalHelper.SingletonUtils;
import com.nectarbits.baraati.retrofit.RequestBodyBuilder;
import com.nectarbits.baraati.retrofit.RetrofitBuilder;
import com.orhanobut.hawk.Hawk;
import com.quickblox.chat.model.QBDialog;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.Chat.ui.activities.base.BaseLoggableActivity;
import com.nectarbits.baraati.Chat.ui.adapters.friends.FriendsAdapter;
import com.nectarbits.baraati.Chat.utils.KeyboardUtils;
import com.nectarbits.baraati.Chat.utils.listeners.simple.SimpleOnRecycleItemClickListener;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBRequestCanceler;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBPagedRequestBuilder;
import com.quickblox.q_municate_core.core.command.Command;
import com.quickblox.q_municate_core.qb.commands.chat.QBCreatePrivateChatCommand;
import com.quickblox.q_municate_core.service.QBService;
import com.quickblox.q_municate_core.service.QBServiceConsts;
import com.quickblox.q_municate_core.utils.ChatUtils;
import com.quickblox.q_municate_core.utils.UserFriendUtils;
import com.quickblox.q_municate_db.managers.DataManager;
import com.quickblox.q_municate_db.models.Dialog;
import com.quickblox.q_municate_db.models.DialogOccupant;
import com.quickblox.q_municate_db.models.Friend;
import com.quickblox.q_municate_db.models.User;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewMessageActivity extends BaseLoggableActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private static final String TAG = NewMessageActivity.class.getSimpleName();
    @Bind(R.id.friends_recyclerview)
    RecyclerView friendsRecyclerView;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.toolbar_progressbar)
    ProgressBar toolbar_progressbar;
    private DataManager dataManager;
    private FriendsAdapter friendsAdapter;
    private User selectedUser;
    ProgressDialog progressDialog;



    int totol_pages=0;
    int current_page=1;
    int page=1;
    List<User> userList=new ArrayList<>();
    Collection<String> contactnumber=new ArrayList<>();
    List<String> contactnumber_temp=new ArrayList<String>();
    List<Contact> contactList=new ArrayList<Contact>();

    List<Friend> friendsList = new ArrayList<Friend>();
    List<Friend> friendsList1=new ArrayList<Friend>();
    Collection<User> userCollection_temp=new ArrayList<>();
    public static void start(Context context) {
        Intent intent = new Intent(context, NewMessageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_friends_list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        if(Hawk.contains(AppUtils.OFFLINE_CHAT_USERLIST)){
            userList.addAll((List<User>) Hawk.get(AppUtils.OFFLINE_CHAT_USERLIST));
        }
        initFields();
        setUpActionBarWithUpButton();



        addActions();

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Hawk.delete(AppUtils.OFFLINE_PHONE_CONTACT);
                initRecyclerView();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "Contact onResume: ");
        initRecyclerView();
        initCustomListeners();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActions();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_message_menu, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;

        if (searchMenuItem != null) {
            searchView = (SearchView) searchMenuItem.getActionView();
        }

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnQueryTextListener(this);
            searchView.setOnCloseListener(this);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_group:
                NewGroupDialogActivity.start(this);
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onClose() {
        cancelSearch();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String searchQuery) {
        KeyboardUtils.hideKeyboard(this);
        search(searchQuery);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String searchQuery) {
        search(searchQuery);
        return true;
    }

    @Override
    public void onConnectedToService(QBService service) {
        super.onConnectedToService(service);
        if (friendListHelper != null && friendsAdapter!=null) {
            friendsAdapter.setFriendListHelper(friendListHelper);
        }
    }

    @Override
    public void onChangedUserStatus(int userId, boolean online) {
        super.onChangedUserStatus(userId, online);
        if(friendsAdapter!=null)
        friendsAdapter.notifyDataSetChanged();
    }

    private void initFields() {
        title = getString(R.string.contacts);
        dataManager = DataManager.getInstance();
    }

    private void initRecyclerView() {



        AsyncTaskCompat.executeParallel(new AsyncTask<Void, Void, Void>() {



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.e(TAG, "Contact update start........................");
                 totol_pages=0;
                 current_page=1;
                 page=1;
                contactnumber.clear();
                contactnumber_temp.clear();
                contactList.clear();
                friendsList.clear();
                friendsList1.clear();
                userCollection_temp.clear();
                friendsAdapter = new FriendsAdapter(NewMessageActivity.this,userList , true);
                friendsAdapter.setFriendListHelper(friendListHelper);
                friendsRecyclerView.setLayoutManager(new LinearLayoutManager(NewMessageActivity.this));
                friendsRecyclerView.setAdapter(friendsAdapter);
                initCustomListeners();

                if(!Hawk.contains(AppUtils.OFFLINE_CHAT_USERLIST))
                    progressDialog.show();
                else
                    toolbar_progressbar.setVisibility(View.VISIBLE);
             //   dataManager.getUserDataManager().deleteAll();

            }

            @Override
            protected Void doInBackground(Void... params) {
                if(Hawk.contains(AppUtils.OFFLINE_PHONE_CONTACT))
                {
                    contactList.clear();
                    contactList.addAll((List<Contact>)Hawk.get(AppUtils.OFFLINE_PHONE_CONTACT));
                }else{
                    contactList=new ContactsProvider().getContacts();
                    Hawk.put(AppUtils.OFFLINE_PHONE_CONTACT,contactList);
                }


                for (int i = 0; i <contactList.size() ; i++) {
                    if(contactList.get(i).phone!=null) {
                    contactnumber.add(contactList.get(i).phone);
                        if(!UserDetails.getInstance(NewMessageActivity.this).getUser_phone().equalsIgnoreCase(contactList.get(i).phone))
                         contactnumber_temp.add(contactList.get(i).phone);
                    }
                }

                Log.e(TAG, "contacts: "+contactnumber.size());
                return null;
            }

            @Override
            protected void onPostExecute(Void bitmap) {


                for (int start = 0; start < contactnumber_temp.size(); start += 400) {
                    int end = Math.min(start + 400, contactnumber_temp.size());
                    List<String> sublist = contactnumber_temp.subList(start, end);
                    totol_pages++;
                    System.out.println(sublist);
                }
                Log.e(TAG, "totla pages: "+totol_pages);

               // fetch_contacts();

                GetContacts(TextUtils.join(",",contactnumber_temp));


            }
        });


    }

    /**
     * Load Contacts from server
     */
    private void GetContacts(String phonnos) {


        Call<UserListModel> call = RetrofitBuilder.getInstance().getRetrofit().GetQuickBloxUserList(RequestBodyBuilder.getInstanse().getReqiest_QuickBloxUserList(phonnos));

        call.enqueue(new Callback<UserListModel>() {
            @Override
            public void onResponse(Call<UserListModel> call, Response<UserListModel> response) {

                List<Friend> list=new ArrayList<Friend>();
                UserListModel userListModel=response.body();
                if(!response.body().getStatus().equalsIgnoreCase("Fail")) {
                    List<ResultItem> resultItems = userListModel.getResult();
                    friendsList.clear();
                    friendsList1.clear();
                    for (int i = 0; i < resultItems.size(); i++) {
                        UserDetail userDetail = resultItems.get(i).getUserDetail();
                        User user = new User();
                        if (!TextUtils.isEmpty(userDetail.getQuickBlockuserId())) {
                            user.setUserId(Integer.parseInt(userDetail.getQuickBlockuserId()));
                        } else {
                            user.setUserId(123);
                        }
                        user.setEmail(userDetail.getEmail());
                        user.setExternalId(userDetail.getUserId());
                        user.setAvatar(userDetail.getProfilePicture());
                        user.setPhone(resultItems.get(i).getPhone());
                        user.setFullName(userDetail.getFirstName());

                        friendsList.add(new Friend(user));
                        dataManager.getUserDataManager().create(user);

                        dataManager.getFriendDataManager().create(new Friend(user));
                        friendsList1.add(new Friend(user));
                    }
                    Collections.sort(friendsList1, new Comparator<Friend>() {
                        public int compare(Friend s1, Friend s2) {
                            return s1.getUser().getFullName().compareToIgnoreCase(s2.getUser().getFullName());
                        }
                    });

                    for (int j = 0; j < contactList.size(); j++) {
                        Boolean aBoolean = false;
                        if (contactList.get(j).phone != null) {
                            for (int k = 0; k < friendsList.size(); k++) {
                                if (friendsList.get(k).getUser().getPhone().equalsIgnoreCase(contactList.get(j).phone)) {
                                    aBoolean = true;
                                }
                            }
                        } else {
                            aBoolean = true;
                        }

                        if (!aBoolean) {
                            User user = new User();
                            user.setFullName(contactList.get(j).name);
                            user.setEmail(contactList.get(j).email);
                            user.setPhone("+" + contactList.get(j).phone);
                            list.add(new Friend(user));
                        }
                    }

                    Collections.sort(list, new Comparator<Friend>() {
                        public int compare(Friend s1, Friend s2) {
                            return s1.getUser().getFullName().compareToIgnoreCase(s2.getUser().getFullName());
                        }
                    });

                    friendsList1.addAll(list);

                    Log.e(TAG, "onSuccess 2: " + friendsList1.size());

                    userList.clear();
                    userList.addAll(UserFriendUtils.getUsersFromFriends(friendsList1));

                    Hawk.put(AppUtils.OFFLINE_CHAT_USERLIST, userList);
                    if (friendsAdapter != null) {

                        friendsAdapter = new FriendsAdapter(NewMessageActivity.this, userList, true);
                        friendsAdapter.setFriendListHelper(friendListHelper);
                        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(NewMessageActivity.this));
                        friendsRecyclerView.setAdapter(friendsAdapter);
                        initCustomListeners();
                    }
                    Log.e(TAG, "Contact update end........................");

                }
                toolbar_progressbar.setVisibility(View.GONE);
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<UserListModel> call, Throwable t) {

            }
        });
    }
    /**
     * Fetch Contact from quickblox
     */
    void fetch_contacts(){
        page=0;
        Collection<String> stringList=new ArrayList<>();
        for (int start = 0; start < contactnumber_temp.size(); start += 400) {
            page++;
            stringList.clear();
            int end = Math.min(start + 400, contactnumber_temp.size());
            stringList.addAll(contactnumber_temp.subList(start, end));
            System.out.println(stringList);

            if(page==current_page) {
                break;
            }


        }

        QBPagedRequestBuilder qbPagedRequestBuilder=new QBPagedRequestBuilder();
        qbPagedRequestBuilder.setPage(1);
        qbPagedRequestBuilder.setPerPage(400);


        QBUsers.getUsersByPhoneNumbers(stringList, qbPagedRequestBuilder, new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> qbUsers, Bundle bundle) {
                Log.e(TAG, "onSuccess: "+qbUsers.size());
                Collection<User> userCollection = UserFriendUtils.createUsersList(qbUsers);
                userCollection_temp.addAll(userCollection);
                for (User user : userCollection) {


                    friendsList.add(new Friend(user));

                    Log.e(TAG, "onSuccess 1: "+new Friend(user).getUser().getExternalId()+" "+ SingletonUtils.getInstance().getStrings().size());
                 /*   dataManager.getUserDataManager().create(user);

                    dataManager.getFriendDataManager().create(new Friend(user));*/

                   /* for(String s : SingletonUtils.getInstance().getStrings())
                    {
                        Log.e(TAG, "onSuccess: "+s+" "+new Friend(user).getUser().getExternalId());
                        if(s.equalsIgnoreCase(new Friend(user).getUser().getExternalId())){
                            aBoolean=false;
                        }
                    }
                    if(aBoolean) {*/
                        friendsList1.add(new Friend(user));
                    /*}*/
                }

                Collections.sort(friendsList1, new Comparator<Friend>(){
                    public int compare(Friend s1, Friend s2) {
                        return s1.getUser().getFullName().compareToIgnoreCase(s2.getUser().getFullName());
                    }
                });
                current_page++;
                List<Friend> list=new ArrayList<Friend>();
                if(current_page>totol_pages) {

                    for (int j = 0; j < contactList.size(); j++) {
                        Boolean aBoolean = false;
                        if (contactList.get(j).phone != null) {
                            for (int k = 0; k < friendsList.size(); k++) {
                                if (friendsList.get(k).getUser().getPhone().equalsIgnoreCase(contactList.get(j).phone)) {
                                    aBoolean = true;
                                }
                            }
                        } else {
                            aBoolean = true;
                        }

                        if (!aBoolean) {
                            User user = new User();
                            user.setFullName(contactList.get(j).name);
                            user.setEmail(contactList.get(j).email);
                            user.setPhone("+" + contactList.get(j).phone);
                            list.add(new Friend(user));
                        }
                    }

                    Collections.sort(list, new Comparator<Friend>(){
                        public int compare(Friend s1, Friend s2) {
                            return s1.getUser().getFullName().compareToIgnoreCase(s2.getUser().getFullName());
                        }
                    });

                    friendsList1.addAll(list);
                    Log.e(TAG, "onSuccess 2: " + friendsList1.size());

                    userList.clear();
                    userList.addAll(UserFriendUtils.getUsersFromFriends(friendsList1));

                    Hawk.put(AppUtils.OFFLINE_CHAT_USERLIST, userList);
                    if (friendsAdapter != null) {

                        friendsAdapter = new FriendsAdapter(NewMessageActivity.this, userList, true);
                        friendsAdapter.setFriendListHelper(friendListHelper);
                        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(NewMessageActivity.this));
                        friendsRecyclerView.setAdapter(friendsAdapter);
                        initCustomListeners();
                    }
                    dataManager.getFriendDataManager().deleteAll();
                    for (User user : userCollection_temp) {

                    dataManager.getUserDataManager().create(user);
                    dataManager.getFriendDataManager().create(new Friend(user));

                    }

                    toolbar_progressbar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    swipeRefreshLayout.isRefreshing();
                    swipeRefreshLayout.setRefreshing(false);
                    Log.e(TAG, "Contact update end........................");
                }else{
                    fetch_contacts();
                }
            }

            @Override
            public void onError(QBResponseException e) {
                e.printStackTrace();
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void initCustomListeners() {
        friendsAdapter.setOnRecycleItemClickListener(new SimpleOnRecycleItemClickListener<User>() {

            @Override
            public void onItemClicked(View view, User user, int position) {
                super.onItemClicked(view, user, position);
                selectedUser = user;
                    if(user.getUserId()>0) {
                        checkForOpenChat(user);
                    }else{
                        String shareBody = "I love this application. To get Baraati Click on: https://goo.gl/1nqIwg";
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Baraati Android App");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share"));
                    }

            }
        });
    }

    private void addActions() {
        addAction(QBServiceConsts.CREATE_PRIVATE_CHAT_SUCCESS_ACTION, new CreatePrivateChatSuccessAction());
        addAction(QBServiceConsts.CREATE_PRIVATE_CHAT_FAIL_ACTION, failAction);

        updateBroadcastActionList();
    }

    private void removeActions() {
        removeAction(QBServiceConsts.CREATE_PRIVATE_CHAT_SUCCESS_ACTION);
        removeAction(QBServiceConsts.CREATE_PRIVATE_CHAT_FAIL_ACTION);

        updateBroadcastActionList();
    }

    private void checkForOpenChat(User user) {
        DialogOccupant dialogOccupant = dataManager.getDialogOccupantDataManager().getDialogOccupantForPrivateChat(user.getUserId());
        if (dialogOccupant != null && dialogOccupant.getDialog() != null) {
            startPrivateChat(dialogOccupant.getDialog());
        } else {
            if (checkNetworkAvailableWithError()) {
                showProgress();
                QBCreatePrivateChatCommand.start(this, user);
            }
        }
    }

    private void startPrivateChat(Dialog dialog) {
        PrivateDialogActivity.start(this, selectedUser, dialog);
        finish();
    }

    private void search(String searchQuery) {
        if (friendsAdapter != null) {
            friendsAdapter.setFilter(searchQuery);
        }
    }

    private void cancelSearch() {
        if (friendsAdapter != null) {
            friendsAdapter.flushFilter();
        }
    }

    private class CreatePrivateChatSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) {
            hideProgress();
            QBDialog qbDialog = (QBDialog) bundle.getSerializable(QBServiceConsts.EXTRA_DIALOG);
            startPrivateChat(ChatUtils.createLocalDialog(qbDialog));
        }
    }
}