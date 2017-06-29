package com.nectarbits.baraati.View;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by nectarbits on 19/05/16.
 */
public class TextViewKozukaGothicProH extends TextView {

    public TextViewKozukaGothicProH(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TextViewKozukaGothicProH(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextViewKozukaGothicProH(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/KozukaGothicProH.otf");
            setTypeface(tf);
        }
    }

}