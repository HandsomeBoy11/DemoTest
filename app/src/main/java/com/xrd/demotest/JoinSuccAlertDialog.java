package com.xrd.demotest;

import android.app.Dialog;
import android.content.Context;
import android.os.SystemClock;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class JoinSuccAlertDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private Display display;


    public JoinSuccAlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public JoinSuccAlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.join_succ_alertdialog, null);

        // 获取自定义Dialog布局中的控件
         lLayout_bg = (LinearLayout) view.findViewById(R.id.ll_bg);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams(DensityUtil.dip2px(context,140), DensityUtil.dip2px(context,98)));

        return this;
    }


    public JoinSuccAlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }
    public void dismiss(){
        dialog.dismiss();
    }

    public void show() {
        //setLayout();
        dialog.show();

    }
}
