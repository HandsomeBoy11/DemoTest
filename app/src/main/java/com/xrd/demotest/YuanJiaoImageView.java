package com.xrd.demotest;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by WJ on 2018/11/22.
 */


public class YuanJiaoImageView extends ImageView {

    private  int integer;
    private Context mContext;
    //圆角弧度
    private float[] rids = {10,
            10,
            10,
            10,
            10,
            10,
            10,
            10};
//    private float[] rids = new float[8];

    public YuanJiaoImageView(Context context) {
        super(context);
    }

    public YuanJiaoImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YuanJiaoImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
    }


    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        //绘制圆角imageview
        path.addRoundRect(new RectF(0,0,w,h),rids,Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
    public void setRids(float dp){
        rids[0]=dp;
        rids[1]=dp;
        rids[2]=dp;
        rids[3]=dp;
        rids[4]=dp;
        rids[5]=dp;
        rids[6]=dp;
        rids[7]=dp;
        invalidate();
    }
}
