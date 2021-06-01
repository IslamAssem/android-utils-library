package com.islamassem.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class AutoResizeTextView extends androidx.appcompat.widget.AppCompatTextView {

    public AutoResizeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AutoResizeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoResizeTextView(Context context) {
        super(context);
    }


    int currentSize = 14;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getLineCount()>2)
            setTextSize(currentSize--);
    }
}