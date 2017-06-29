package com.nectarbits.baraati.Utils;

import android.accounts.Account;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;

import com.nectarbits.baraati.generalHelper.AppUtils;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 11/12/16.
 */

public class ContactService extends Service {
    private static final String TAG = ContactService.class.getSimpleName();
    private int mContactCount;
    Cursor cursor = null;
    static ContentResolver mContentResolver = null;

    // Content provider authority
    public static final String AUTHORITY = "com.android.contacts";
    // Account typek
    public static final String ACCOUNT_TYPE = "com.example.myapp.account";
    // Account
    public static final String ACCOUNT = "myApp";

    // Instance fields
    Account mAccount;
    Bundle settingsBundle;

    @Override
    public void onCreate() {
        super.onCreate();
        // Get contact count at start of service
       // mContactCount = getContactCount();
        if(!Hawk.contains(AppUtils.OFFLINE_PHONE_CONTACT)){
            new changeInContact().execute();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Get contact count at start of service
        this.getContentResolver().registerContentObserver(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, true, mObserver);
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private int getContactCount() {
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                return cursor.getCount();
            } else {
                cursor.close();
                return 0;
            }
        } catch (Exception ignore) {
        } finally {
            cursor.close();
        }
        return 0;
    }

    private ContentObserver mObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            this.onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            new changeInContact().execute();
        }
    };

    public class changeInContact extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... arg0) {
            ArrayList<Integer> arrayListContactID = new ArrayList<Integer>();


            List<Contact> contactList= new ContactsProvider().getContacts();
            int currentCount = contactList.size();
            Hawk.put(AppUtils.OFFLINE_PHONE_CONTACT,contactList);
            Log.e(TAG, "doInBackground: "+currentCount);
            if (currentCount > mContactCount) {
                // Contact Added
                Log.e(TAG, "doInBackground: added");
            } else if (currentCount < mContactCount) {
                // Delete Contact
                Log.e(TAG, "doInBackground: deleted");
            } else if (currentCount == mContactCount) {
                // Update Contact
                Log.e(TAG, "doInBackground: updated");
            }
            mContactCount = currentCount;
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            //contactService = false;
        } // End of post
    }
}
