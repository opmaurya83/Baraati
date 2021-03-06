package com.nectarbits.baraati.Chat.utils.helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.quickblox.videochat.webrtc.QBRTCTypes;

import java.util.ArrayList;

public class SystemPermissionHelper {

    public static final int PERMISSIONS_FOR_CALL_REQUEST = 15;
    public static final int PERMISSIONS_FOR_IMPORT_FRIENDS_REQUEST = 16;
    public static final int PERMISSIONS_FOR_SAVE_FILE_REQUEST = 17;

    private final Activity activity;

    public SystemPermissionHelper(Activity activity) {
        this.activity = activity;
    }


    public void requestPermission(int requestCode, String permission) {
        ActivityCompat.requestPermissions(activity, new String[]{permission},
                requestCode);
    }

    public void requestPermissions(int requestCode, String... permissions) {
        ActivityCompat.requestPermissions(activity, permissions,
                requestCode);
    }

    public boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(activity.getApplicationContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isAllPermissionGranted(String... permissions) {
        for (String permission : permissions) {
            if (!isPermissionGranted(permission)) {
                return false;
            }
        }

        return true;
    }

    public void checkAndRequestPermissions(int requestCode, String... permissions) {
        if (collectDeniedPermissions(permissions).length > 0) {
            requestPermissions(requestCode, collectDeniedPermissions(permissions));
        }
    }

    private String[] collectDeniedPermissions(String... permissions) {
        ArrayList<String> deniedPermissionsList = new ArrayList<>();
        for (String permission : permissions) {
            if (!isPermissionGranted(permission)) {
                deniedPermissionsList.add(permission);
            }
        }

        return deniedPermissionsList.toArray(new String[deniedPermissionsList.size()]);
    }

    public boolean isAllPermissionsGrantedForCallByType(QBRTCTypes.QBConferenceType qbConferenceType) {
        if (QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO.equals(qbConferenceType)) {
            return isPermissionGranted(Manifest.permission.RECORD_AUDIO);
        } else {
            return isAllPermissionGranted(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA);
        }
    }

    public boolean isCameraPermissionGranted(){
        return isPermissionGranted(Manifest.permission.CAMERA);
    }

    public boolean isMicriphonePermissionGranted(){
        return isPermissionGranted(Manifest.permission.RECORD_AUDIO);
    }

    public void requestPermissionsForCallByType(QBRTCTypes.QBConferenceType qbConferenceType){
        if (QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO.equals(qbConferenceType)) {
            checkAndRequestPermissions(PERMISSIONS_FOR_CALL_REQUEST, Manifest.permission.RECORD_AUDIO);
        } else {
            checkAndRequestPermissions(PERMISSIONS_FOR_CALL_REQUEST, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA);
        }
    }

    public boolean isAllPermissionsGrantedForImportFriends(){
        return isAllPermissionGranted(Manifest.permission.READ_CONTACTS);
    }

    public void requestPermissionsForImportFriends(){
        checkAndRequestPermissions(PERMISSIONS_FOR_IMPORT_FRIENDS_REQUEST, Manifest.permission.READ_CONTACTS);
    }

    public boolean isAllPermissionsGrantedForSaveFile(){
        return isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void requestPermissionsForSaveFile(){
        checkAndRequestPermissions(PERMISSIONS_FOR_SAVE_FILE_REQUEST, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public ArrayList<String> collectDeniedPermissionsFomResult(String permissions[], int[] grantResults){
        ArrayList<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++){
            if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                deniedPermissions.add(permissions[i]);
            }
        }

        return deniedPermissions;
    }

    public static void openSystemSettings(Context context){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }
}
