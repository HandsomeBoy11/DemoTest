package com.xrd.demotest;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by WJ on 2018/11/12.
 */

public class MyItemDecorView extends RecyclerView.ItemDecoration {
    private int count;
    private int space;
    private boolean b;
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        outRect.bottom=space;
        outRect.right=b?space:0;
        for (int i = 0; i < count; i++) {
            if(i==position){
                outRect.top=space;
            }
        }
        if(position%count==0){
            outRect.left=b?space:0;
        }

    }
    public void setSpace(int count,int space,boolean b){
        this.count=count;
        this.space=space;
        this.b=b;
    }
}
