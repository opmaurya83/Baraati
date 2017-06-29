package com.nectarbits.baraati.Chat.ui.fragments.imagepicker;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.Chat.ui.activities.base.BaseLoggableActivity;
import com.nectarbits.baraati.Chat.utils.image.ImageUtils;

public class ImageSourcePickDialogFragment extends DialogFragment {

    private static final int POSITION_GALLERY = 0;
    private static final int POSITION_PICTURE = 1;
    private static final int POSITION_VIDEO = 2;
    static Boolean isProfile=false;
    private OnImageSourcePickedListener onImageSourcePickedListener;

    public static void show(FragmentManager fragmentManager, OnImageSourcePickedListener onImageSourcePickedListener) {
        ImageSourcePickDialogFragment fragment = new ImageSourcePickDialogFragment();
        fragment.setOnImageSourcePickedListener(onImageSourcePickedListener);
        fragment.show(fragmentManager, ImageSourcePickDialogFragment.class.getSimpleName());
        isProfile=false;

    }

    public static void show(FragmentManager fragmentManager, OnImageSourcePickedListener onImageSourcePickedListener,Boolean Profile) {
        ImageSourcePickDialogFragment fragment = new ImageSourcePickDialogFragment();
        fragment.setOnImageSourcePickedListener(onImageSourcePickedListener);
        fragment.show(fragmentManager, ImageSourcePickDialogFragment.class.getSimpleName());
      isProfile=Profile;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());
        builder.title(R.string.dlg_choose_image_from);
        if(isProfile)
        {
            builder.items(R.array.dlg_image_pick_profile);
        }else {
            builder.items(R.array.dlg_image_pick);
        }
        builder.itemsCallback(new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog materialDialog, View view, int i,
                    CharSequence charSequence) {
                switch (i) {
                    case POSITION_GALLERY:
                        onImageSourcePickedListener.onImageSourcePicked(ImageSource.GALLERY);
                        break;
                    case POSITION_PICTURE:
                        onImageSourcePickedListener.onImageSourcePicked(ImageSource.CAMERA_PICTURE);
                        break;
                    case POSITION_VIDEO:
                        onImageSourcePickedListener.onImageSourcePicked(ImageSource.CAMERA_VIDEO);
                        break;

                }
            }
        });

        MaterialDialog dialog = builder.build();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    public void setOnImageSourcePickedListener(OnImageSourcePickedListener onImageSourcePickedListener) {
        this.onImageSourcePickedListener = onImageSourcePickedListener;
    }

    public static enum ImageSource {
        GALLERY,
        CAMERA_PICTURE,
        CAMERA_VIDEO
    }

    public static interface OnImageSourcePickedListener {

        void onImageSourcePicked(ImageSource source);
    }

    public static class LoggableActivityImageSourcePickedListener implements OnImageSourcePickedListener {

        private Activity activity;
        private Fragment fragment;

        public LoggableActivityImageSourcePickedListener(Activity activity) {
            this.activity = activity;
        }

        public LoggableActivityImageSourcePickedListener(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onImageSourcePicked(ImageSource source) {
            switch (source) {
                case GALLERY:
                    if (fragment != null) {
                        Activity activity = fragment.getActivity();
                        setupActivityToBeNonLoggable(activity);
                        ImageUtils.startImagePicker(fragment);
                    } else {
                        setupActivityToBeNonLoggable(activity);
                        ImageUtils.startImagePicker(activity);
                    }
                    break;
                case CAMERA_PICTURE:
                    if (fragment != null) {
                        Activity activity = fragment.getActivity();
                        setupActivityToBeNonLoggable(activity);
                        ImageUtils.startCameraForResult(fragment);
                    } else {
                        setupActivityToBeNonLoggable(activity);
                        ImageUtils.startCameraForResult(activity);
                    }
                    break;
                case CAMERA_VIDEO:
                    if (fragment != null) {
                        Activity activity = fragment.getActivity();
                        setupActivityToBeNonLoggable(activity);
                        ImageUtils.startCameraVideoForResult(fragment);
                    } else {
                        setupActivityToBeNonLoggable(activity);
                        ImageUtils.startCameraVideoForResult(activity);
                    }
                    break;
            }
        }

        private void setupActivityToBeNonLoggable(Activity activity) {
            if (activity instanceof BaseLoggableActivity) {
                BaseLoggableActivity loggableActivity = (BaseLoggableActivity) activity;
                loggableActivity.canPerformLogout.set(false);
            }
        }
    }
}