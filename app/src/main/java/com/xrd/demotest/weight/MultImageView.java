package com.xrd.demotest.weight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by WJ on 2019/1/9.
 */

public class MultImageView extends LinearLayout {
    public MultImageView(Context context) {
        this(context,null);
    }

    public MultImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public MultImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(widthMeasureSpec);
    }
}
