package com.xrd.demotest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xrd.demotest.weight.MultiImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WJ on 2018/12/3.
 */

public class MultiImgAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private LayoutInflater inflater;
    private List<List<String>> mList=new ArrayList<>();

    public MultiImgAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.multi_img_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        List<String> list = mList.get(position);
        holder1.multiImg.setList(list);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setData(List<List<String>> mList) {
        this.mList.clear();
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_des)
        TextView tvDes;
        @BindView(R.id.multi_img)
        MultiImageView multiImg;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
