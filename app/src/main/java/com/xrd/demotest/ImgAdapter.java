package com.xrd.demotest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.List;

/**
 * Created by WJ on 2018/12/11.
 */

public class ImgAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private final LayoutInflater inflater;
    /*<item>http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg</item>
        <item>http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg</item>
        <item>http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg</item>*/
    List<String> mList= Arrays.asList("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg",
            "http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg","http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
    List<Integer> imgs=Arrays.asList(R.drawable.one,R.drawable.two);

    public ImgAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.img_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        Glide.with(mContext).load(imgs.get(position)).into(holder1.iv);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        public ViewHolder(View itemView) {
            super(itemView);
             iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}
