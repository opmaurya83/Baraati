package com.nectarbits.baraati.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by nectarbits on 19/05/16.
 */
public class EditTextDescription extends EditText {

    public EditTextDescription(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextDescription(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextDescription(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
            setTypeface(tf);
        }
    }

}