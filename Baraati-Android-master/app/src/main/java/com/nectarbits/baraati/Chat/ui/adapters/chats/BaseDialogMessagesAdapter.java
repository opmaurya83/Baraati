package com.nectarbits.baraati.Chat.ui.adapters.chats;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nectarbits.baraati.ProductDetailActivity;
import com.nectarbits.baraati.generalHelper.AppUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.quickblox.chat.model.QBAttachment;
import com.quickblox.content.model.QBFile;
import com.nectarbits.baraati.R;
import com.nectarbits.baraati.Chat.ui.activities.base.BaseActivity;
import com.nectarbits.baraati.Chat.ui.activities.others.PreviewImageActivity;
import com.nectarbits.baraati.Chat.ui.adapters.base.BaseClickListenerViewHolder;
import com.nectarbits.baraati.Chat.ui.adapters.base.BaseRecyclerViewAdapter;
import com.nectarbits.baraati.Chat.ui.adapters.base.BaseViewHolder;
import com.nectarbits.baraati.Chat.ui.views.maskedimageview.MaskedImageView;
import com.nectarbits.baraati.Chat.ui.views.roundedimageview.RoundedImageView;
import com.nectarbits.baraati.Chat.utils.ColorUtils;
import com.nectarbits.baraati.Chat.utils.DateUtils;
import com.nectarbits.baraati.Chat.utils.FileUtils;
import com.nectarbits.baraati.Chat.utils.image.ImageLoaderUtils;
import com.nectarbits.baraati.Chat.utils.image.ImageUtils;
import com.nectarbits.baraati.Chat.utils.listeners.ChatUIHelperListener;
import com.quickblox.q_municate_core.models.AppSession;
import com.quickblox.q_municate_core.models.CombinationMessage;
import com.quickblox.q_municate_core.utils.ConstsCore;
import com.quickblox.q_municate_db.managers.DataManager;
import com.quickblox.q_municate_db.models.Dialog;
import com.quickblox.users.model.QBUser;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListener;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.ThinDownloadManager;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.Bind;

public abstract class BaseDialogMessagesAdapter
        extends BaseRecyclerViewAdapter<CombinationMessage, BaseClickListenerViewHolder<CombinationMessage>> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    protected static final int TYPE_REQUEST_MESSAGE = 0;
    protected static final int TYPE_OWN_MESSAGE = 1;
    protected static final int TYPE_OPPONENT_MESSAGE = 2;

    protected DataManager dataManager;
    protected ChatUIHelperListener chatUIHelperListener;
    protected ImageUtils imageUtils;
    protected Dialog dialog;
    protected ColorUtils colorUtils;
    protected QBUser currentUser;

    private FileUtils fileUtils;

    public BaseDialogMessagesAdapter(BaseActivity baseActivity, List<CombinationMessage> objectsList) {
        super(baseActivity, objectsList);

        dataManager = DataManager.getInstance();
        imageUtils = new ImageUtils(baseActivity);
        colorUtils = new ColorUtils();
        fileUtils = new FileUtils();
        currentUser = AppSession.getSession().getUser();
    }

    @Override
    public long getHeaderId(int position) {
        CombinationMessage combinationMessage = getItem(position);
        return DateUtils.toShortDateLong(combinationMessage.getCreatedDate());
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.item_chat_sticky_header_date, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        View view = holder.itemView;

        TextView headerTextView = (TextView) view.findViewById(R.id.header_date_textview);
        CombinationMessage combinationMessage = getItem(position);
        headerTextView.setText(DateUtils.toTodayYesterdayFullMonthDate(combinationMessage.getCreatedDate()));
    }

    protected void displayAttachImageById(String attachId, final ViewHolder viewHolder) {
        String privateUrl = QBFile.getPrivateUrlForUID(attachId);
        displayAttachImage(privateUrl, viewHolder);
    }

    protected void displayAttachVideoById(String attachId, final ViewHolder viewHolder) {
        String privateUrl = QBFile.getPrivateUrlForUID(attachId);
        displayAttachVideo(attachId, privateUrl, viewHolder);
    }

    protected void displayAttachImage(String attachUrl, final ViewHolder viewHolder) {
        ImageLoader.getInstance().displayImage(attachUrl, viewHolder.attachImageView,
                ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS, new ImageLoadingListener(viewHolder),
                new SimpleImageLoadingProgressListener(viewHolder));
    }

    protected void displayAttachVideo(String attachId, String attachUrl, final ViewHolder viewHolder) {

        Uri uri_source = Uri.parse(attachUrl);
        Uri uri_destination = Uri.parse(new FileUtils().getInitFolder() + "/" + attachId + ".mp4");
        File file = new File(new FileUtils().getInitFolder() + "/" + attachId + ".mp4");
        ThinDownloadManager downloadManager = new ThinDownloadManager();
        VideoDownloaded videoDownloaded = new VideoDownloaded(uri_source, viewHolder, uri_destination);
        if (!file.exists()) {
            downloadManager.add(videoDownloaded);
        } else {
            videoDownloaded.alreadDownloaded();
        }


    }

    protected void displayProductImage(String attachUrl, final ViewHolder viewHolder, JSONObject jsonObject) {
        ImageLoader.getInstance().displayImage(attachUrl, viewHolder.productImageView,
                ImageLoaderUtils.UIL_DEFAULT_DISPLAY_OPTIONS, new ProductImageLoadingListener(viewHolder, jsonObject),
                new SimpleImageLoadingProgressListener(viewHolder));
    }

    protected int getMessageStatusIconId(boolean isDelivered, boolean isRead) {
        int iconResourceId = 0;

        if (isRead) {
            iconResourceId = R.drawable.ic_status_mes_sent_received;
        } else if (isDelivered) {
            iconResourceId = R.drawable.ic_status_mes_sent;
        }

        return iconResourceId;
    }

    protected void setMessageStatus(ImageView imageView, boolean messageDelivered, boolean messageRead) {
        imageView.setImageResource(getMessageStatusIconId(messageDelivered, messageRead));
    }

    protected int getItemViewType(CombinationMessage combinationMessage) {
        boolean ownMessage = !combinationMessage.isIncoming(currentUser.getId());
        if (combinationMessage.getNotificationType() == null) {
            if (ownMessage) {
                return TYPE_OWN_MESSAGE;
            } else {
                return TYPE_OPPONENT_MESSAGE;
            }
        } else {
            return TYPE_REQUEST_MESSAGE;
        }
    }

    protected void resetUI(ViewHolder viewHolder) {
        setViewVisibility(viewHolder.attachMessageRelativeLayout, View.GONE);
        setViewVisibility(viewHolder.progressRelativeLayout, View.GONE);
        setViewVisibility(viewHolder.textMessageView, View.GONE);
    }

    //    @Override
    //    public void onAbsolutePathExtFileReceived(String absolutePath) {
    //        chatUIHelperListener.onScreenResetPossibilityPerformLogout(false);
    //        imageUtils.showFullImage((android.app.Activity) context, absolutePath);
    //    }

    protected void setViewVisibility(View view, int visibility) {
        if (view != null) {
            view.setVisibility(visibility);
        }
    }

    protected Boolean isLink(String message) {
        Boolean isLink = false;
        try {

            JSONObject jsonObject = new JSONObject(message);
            Log.e(BaseDialogMessagesAdapter.class.getSimpleName(), "isLink: " + jsonObject.toString());
            isLink = true;
        } catch (Exception e) {

            isLink = false;
        }

        return isLink;
    }

    protected String getLink(String message, String key) {
        String value = "";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(message);
            value = jsonObject.getString(key);
        } catch (Exception e) {


        }

        return value;
    }

    protected JSONObject getLink(String message) {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(message);

        } catch (Exception e) {


        }

        return jsonObject;
    }

    protected static class ViewHolder extends BaseViewHolder<CombinationMessage> {

        @Nullable
        @Bind(R.id.avatar_imageview)
        RoundedImageView avatarImageView;

        @Nullable
        @Bind(R.id.name_textview)
        TextView nameTextView;

        @Nullable
        @Bind(R.id.text_message_view)
        View textMessageView;

        @Nullable
        @Bind(R.id.text_message_delivery_status_imageview)
        ImageView messageDeliveryStatusImageView;

        @Nullable
        @Bind(R.id.attach_message_delivery_status_imageview)
        ImageView attachDeliveryStatusImageView;

        @Nullable
        @Bind(R.id.progress_relativelayout)
        FrameLayout progressRelativeLayout;

        @Nullable
        @Bind(R.id.attach_message_relativelayout)
        FrameLayout attachMessageRelativeLayout;

        @Nullable
        @Bind(R.id.message_textview)
        TextView messageTextView;

        @Nullable
        @Bind(R.id.attach_imageview)
        MaskedImageView attachImageView;

        @Nullable
        @Bind(R.id.time_text_message_textview)
        TextView timeTextMessageTextView;

        @Nullable
        @Bind(R.id.time_attach_message_textview)
        TextView timeAttachMessageTextView;

        @Nullable
        @Bind(R.id.vertical_progressbar)
        ProgressBar verticalProgressBar;

        @Nullable
        @Bind(R.id.centered_progressbar)
        ProgressBar centeredProgressBar;

        @Nullable
        @Bind(R.id.accept_friend_imagebutton)
        ImageView acceptFriendImageView;

        @Nullable
        @Bind(R.id.divider_view)
        View dividerView;

        @Nullable
        @Bind(R.id.reject_friend_imagebutton)
        ImageView rejectFriendImageView;

        /*Mahesh*/
        @Nullable
        @Bind(R.id.product_name_textview)
        TextView product_name_textview;

        @Nullable
        @Bind(R.id.product_message_relativelayout)
        FrameLayout productMessageRelativeLayout;


        @Nullable
        @Bind(R.id.product_imageview)
        MaskedImageView productImageView;

        @Nullable
        @Bind(R.id.time_product_message_textview)
        TextView timeProductMessageTextView;

        @Nullable
        @Bind(R.id.product_message_delivery_status_imageview)
        ImageView productDeliveryStatusImageView;

        @Nullable
        @Bind(R.id.chatMessage)
        FrameLayout chatMessage;

        public ViewHolder(BaseDialogMessagesAdapter adapter, View view) {
            super(adapter, view);
        }
    }


    /*Mahesh*/

    public class ImageLoadingListener extends SimpleImageLoadingListener {

        private ViewHolder viewHolder;
        private Bitmap loadedImageBitmap;
        private String imageUrl;

        public ImageLoadingListener(ViewHolder viewHolder) {
            this.viewHolder = viewHolder;
        }

        @Override
        public void onLoadingStarted(String imageUri, View view) {
            super.onLoadingStarted(imageUri, view);
            viewHolder.verticalProgressBar.setProgress(ConstsCore.ZERO_INT_VALUE);
            viewHolder.centeredProgressBar.setProgress(ConstsCore.ZERO_INT_VALUE);
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            Log.e(BaseDialogMessagesAdapter.class.getSimpleName(), "onLoadingFailed: " + imageUri);
            updateUIAfterLoading();
            imageUrl = null;
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, final Bitmap loadedBitmap) {
            initMaskedImageView(loadedBitmap);
            fileUtils.checkExistsFile(imageUri, loadedBitmap);
            this.imageUrl = imageUri;
        }

        private void initMaskedImageView(Bitmap loadedBitmap) {
            loadedImageBitmap = loadedBitmap;
            viewHolder.attachImageView.setOnClickListener(receiveImageFileOnClickListener());
            viewHolder.attachImageView.setImageBitmap(loadedImageBitmap);
            setViewVisibility(viewHolder.attachMessageRelativeLayout, View.VISIBLE);
            setViewVisibility(viewHolder.attachImageView, View.VISIBLE);

            updateUIAfterLoading();
        }

        private void updateUIAfterLoading() {
            if (viewHolder.progressRelativeLayout != null) {
                setViewVisibility(viewHolder.progressRelativeLayout, View.GONE);
            }
        }

        private View.OnClickListener receiveImageFileOnClickListener() {
            return new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (imageUrl != null) {
                        view.startAnimation(AnimationUtils.loadAnimation(baseActivity, R.anim.chat_attached_file_click));
                        PreviewImageActivity.start(baseActivity, imageUrl);
                    }
                }
            };
        }
    }

    public class SimpleImageLoadingProgressListener implements ImageLoadingProgressListener {

        private ViewHolder viewHolder;

        public SimpleImageLoadingProgressListener(ViewHolder viewHolder) {
            this.viewHolder = viewHolder;
        }

        @Override
        public void onProgressUpdate(String imageUri, View view, int current, int total) {
            viewHolder.verticalProgressBar.setProgress(Math.round(100.0f * current / total));
        }
    }

    /*Uri downloadUri = Uri.parse("http://tcrn.ch/Yu1Ooo1");
    Uri destinationUri = Uri.parse(this.getExternalCacheDir().toString()+"/test.mp4");
    DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
            .setRetryPolicy(new DefaultRetryPolicy())
            .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
           // .setDownloadContext(downloadContextObject)//Optional
            .setStatusListener(new DownloadStatusListenerV1() {
                @Override
                public void onDownloadComplete(DownloadRequest downloadRequest) {

                }

                @Override
                public void onDownloadFailed(DownloadRequest downloadRequest, int errorCode, String errorMessage) {

                }

                @Override
                public void onProgress(DownloadRequest downloadRequest, long totalBytes, long downloadedBytes, int progress) {

                }
            });
*/
    public class VideoDownloaded extends DownloadRequest implements DownloadStatusListenerV1 {

        private ViewHolder viewHolder;
        private Bitmap loadedImageBitmap;

        private Uri local_Uri;

        public VideoDownloaded(Uri uri) {
            super(uri);
        }

        public VideoDownloaded(Uri uri, ViewHolder viewHolder, Uri destinationUri) {
            super(uri);
            this.viewHolder = viewHolder;
            this.local_Uri = destinationUri;
            setStatusListener(this);
            setRetryPolicy(new DefaultRetryPolicy());
            setDestinationURI(destinationUri).setPriority(Priority.HIGH);

            viewHolder.verticalProgressBar.setProgress(ConstsCore.ZERO_INT_VALUE);
            viewHolder.centeredProgressBar.setProgress(ConstsCore.ZERO_INT_VALUE);

        }

        @Override
        public void onDownloadComplete(DownloadRequest downloadRequest) {
            initMaskedImageView();

        }

        private void initMaskedImageView() {
            loadedImageBitmap = ThumbnailUtils.createVideoThumbnail(new File(local_Uri.getPath()).getPath(), MediaStore.Video.Thumbnails.MINI_KIND);

            if (loadedImageBitmap != null)
                ImageLoaderUtils.addVideoIcon(baseActivity, loadedImageBitmap);

            viewHolder.attachImageView.setOnClickListener(receiveImageFileOnClickListener());
            viewHolder.attachImageView.setImageBitmap(loadedImageBitmap);
            setViewVisibility(viewHolder.attachMessageRelativeLayout, View.VISIBLE);
            setViewVisibility(viewHolder.attachImageView, View.VISIBLE);

            updateUIAfterLoading();
        }

        private View.OnClickListener receiveImageFileOnClickListener() {
            return new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (local_Uri != null) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(local_Uri, "video/mp4");
                        baseActivity.startActivity(intent);

                    }
                }
            };
        }

        private void updateUIAfterLoading() {
            if (viewHolder.progressRelativeLayout != null) {
                setViewVisibility(viewHolder.progressRelativeLayout, View.GONE);
            }
        }

        void alreadDownloaded() {
            initMaskedImageView();
        }

        @Override
        public void onDownloadFailed(DownloadRequest downloadRequest, int errorCode, String errorMessage) {

        }

        @Override
        public void onProgress(DownloadRequest downloadRequest, long totalBytes, long downloadedBytes, int progress) {

            viewHolder.verticalProgressBar.setProgress(progress);
        }
    }

    public class ProductImageLoadingListener extends SimpleImageLoadingListener {

        private ViewHolder viewHolder;
        private Bitmap loadedImageBitmap;
        private String imageUrl;
        private JSONObject jsonObject;

        public ProductImageLoadingListener(ViewHolder viewHolder, JSONObject jsonObject) {
            this.viewHolder = viewHolder;
            this.jsonObject = jsonObject;
        }

        @Override
        public void onLoadingStarted(String imageUri, View view) {
            super.onLoadingStarted(imageUri, view);
            viewHolder.verticalProgressBar.setProgress(ConstsCore.ZERO_INT_VALUE);
            viewHolder.centeredProgressBar.setProgress(ConstsCore.ZERO_INT_VALUE);
        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            updateUIAfterLoading();
            imageUrl = null;
        }

        @Override
        public void onLoadingComplete(String imageUri, View view, final Bitmap loadedBitmap) {
            initMaskedImageView(loadedBitmap);
            fileUtils.checkExistsFile(imageUri, loadedBitmap);
            this.imageUrl = imageUri;
        }

        private void initMaskedImageView(Bitmap loadedBitmap) {
            loadedImageBitmap = loadedBitmap;

            viewHolder.productImageView.setImageBitmap(loadedImageBitmap);
            setViewVisibility(viewHolder.productMessageRelativeLayout, View.VISIBLE);
            setViewVisibility(viewHolder.productImageView, View.VISIBLE);

            viewHolder.productImageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (loadedImageBitmap != null) {
                        try {
                            Intent i = new Intent(baseActivity, ProductDetailActivity.class);
                            i.putExtra(AppUtils.ARG_PRODUCT_ID, jsonObject.getString(AppUtils.ARG_PRODUCT_ID));
                            i.putExtra(AppUtils.ARG_PRODUCT_NAME, jsonObject.getString(AppUtils.ARG_PRODUCT_NAME));
                            i.putExtra(AppUtils.ARG_FOR_USER_ID, jsonObject.getString(AppUtils.ARG_FOR_USER_ID));
                            i.putExtra(AppUtils.ARG_USER_EVENTTYPE_EVENTID, jsonObject.getString(AppUtils.ARG_USER_EVENTTYPE_EVENTID));
                            baseActivity.startActivity(i);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

            updateUIAfterLoading();
        }

        private void updateUIAfterLoading() {
            if (viewHolder.progressRelativeLayout != null) {
                setViewVisibility(viewHolder.progressRelativeLayout, View.GONE);
            }
        }

        private View.OnClickListener receiveImageFileOnClickListener() {
            return new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (imageUrl != null) {
                        view.startAnimation(AnimationUtils.loadAnimation(baseActivity, R.anim.chat_attached_file_click));
                        PreviewImageActivity.start(baseActivity, imageUrl);
                    }
                }
            };
        }
    }
}