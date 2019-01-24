package com.xrd.demotest;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;


public class DensityUtil {

    /**
     * 判断@还是2@
     * 0dpi ~ 120dpi 	ldpi 	240 x 320
     * 120dpi ~ 160dpi 	mdpi 	320 x 480
     * 160dpi ~ 240dpi 	hdpi 	480 x 800
     * 240dpi ~ 320dpi 	xhdpi 	720 x 1280
     * 320dpi ~ 480dpi 	xxhdpi 	1080 x 1920
     * 480dpi ~ 640dpi 	xxxhdpi 	2560x1440
     * 以xxhdpi这个为准 1080*1920 大于就用xxxhpi
     */
    public static String getDevicePixels(Context ctx) {
        try {
            DisplayMetrics dm = new DisplayMetrics();
            ((Activity) ctx).getWindowManager().getDefaultDisplay().getMetrics(dm);
            int hp = dm.heightPixels;
            int wp = dm.widthPixels;
            if (wp * hp <= 1080 * 1920) return "2";
            else return "3";
        } catch (Exception e) {
            return "3";
        }
    }

    /**
     * 获取屏幕尺寸
     *
     * @param context
     * @return
     */
    public static int[] getScreenSize(Context context) {
        Activity activity = null;
        DisplayMetrics dm = new DisplayMetrics();
        int[] size = null;
        if (context instanceof Activity) {
            activity = ((Activity) context);
            // 取得窗口属性
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            // 窗口的宽度
            int screenWidth = dm.widthPixels;
            // 窗口高度
            int screenHeight = dm.heightPixels;
            size = new int[2];
            size[0] = screenWidth;
            size[1] = screenHeight;
        }
        return size;
    }


    /**
     * 获取屏幕DPI(屏幕密度)
     *
     * @param ctx
     * @return
     */
    public static int getScreenDPI(Context ctx) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) ctx).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context ctx, float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context ctx, float pxValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
