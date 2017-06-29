package com.nectarbits.baraati.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import at.blogc.android.views.ExpandableTextView;

/**
 * Created by root on 8/9/16.
 */
public class ExpandableTextViewDescription extends ExpandableTextView {

    public ExpandableTextViewDescription(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ExpandableTextViewDescription(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExpandableTextViewDescription(Context context) {
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