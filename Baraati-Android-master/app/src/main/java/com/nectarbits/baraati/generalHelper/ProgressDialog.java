package com.nectarbits.baraati.generalHelper;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.nectarbits.baraati.R;
import com.wang.avi.AVLoadingIndicatorView;



/**
 * Created by nectabits on 5/5/16.
 */

public class ProgressDialog extends Dialog {

    private Context mContext;



    private TextView txtMessage;
   // private GifImageView imgLoader;
   // AVLoadingIndicatorView avLoadingIndicatorView;
    private Boolean isOfflineDataAvailable=false;
    public ProgressDialog(Context context) {
        super(context,  R.style.TransparentProgressDialog);
        this.mContext=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setDimAmount(0.0f);
        setContentView(R.layout.prrogress_dialog);


        txtMessage=(TextView) findViewById(R.id.txtMessage);
       // avLoadingIndicatorView=(AVLoadingIndicatorView) findViewById(R.id.avi);
        setCancelable(false);
       // imgLoader=(GifImageView) findViewById(R.id.imgLoader);

        /*AlphaAnimation fadeOut;
        fadeOut = new AlphaAnimation(1,0.5f);
        fadeOut.setDuration(2000);
        fadeOut.setRepeatMode(Animation.INFINITE);*/
      // imgLoader.startAnimation(fadeOut);


    }

    public void setMessageOnProgress(String message){
        txtMessage.setText(message);
    }


    @Override
    public void cancel() {
        super.cancel();
    }

    public Boolean getOfflineDataAvailable() {
        return isOfflineDataAvailable;
    }

    public void setOfflineDataAvailable(Boolean offlineDataAvailable) {
        isOfflineDataAvailable = offlineDataAvailable;
    }
}

