package com.xrd.demotest;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WJ on 2018/11/28.
 */

public class SelectAttrsAdapter extends RecyclerView.Adapter {
    private static final int GUIGE = 0x100;
    private static final int CHILD = 0x101;
    private static final int NOMAL = 0x102;
    public  String groupName;
    private  List<ProductModel.AttributesEntity.AttributeMembersEntity> mAttributeMembersEntities;
    private Context mContext;
    private List<Object> mList = new ArrayList<>();
    private LayoutInflater inflater;
    private List<Object> data;
    private ProductModel.AttributesEntity.AttributeMembersEntity currentSelectedItem;

    public SelectAttrsAdapter(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }
    public SelectAttrsAdapter(List<ProductModel.AttributesEntity.AttributeMembersEntity> attributeMembersEntities, String groupName) {
        this.mAttributeMembersEntities = attributeMembersEntities;
        this.groupName = groupName;
    }

    public ProductModel.AttributesEntity.AttributeMembersEntity getCurrentSelectedItem() {
        return currentSelectedItem;
    }

    public void setCurrentSelectedItem(ProductModel.AttributesEntity.AttributeMembersEntity currentSelectedItem) {
        this.currentSelectedItem = currentSelectedItem;
    }

    public List<ProductModel.AttributesEntity.AttributeMembersEntity> getAttributeMembersEntities() {
        return mAttributeMembersEntities;
    }

  /*  @Override
    public int getItemViewType(int position) {
        Object o = mList.get(position);
        if (o instanceof AttrsChildBean) {
            return GUIGE;
        } else if (o instanceof Bean) {
            return CHILD;
        }
        return super.getItemViewType(position);
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.child, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.setData(mAttributeMembersEntities.get(position));
        holder1.setClick(position);
    }

    @Override
    public int getItemCount() {
        return mAttributeMembersEntities.size();
    }

 /*   public void setData(List<Object> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_child);
        }

        public void setData(ProductModel.AttributesEntity.AttributeMembersEntity entity) {
            mTextView.setText(entity.getName());
            switch (entity.getStatus()) {
                case 0:
                    mTextView.setAlpha(1f);
                    mTextView.setBackgroundResource(R.drawable.normal_bg);
                    mTextView.setTextColor(Color.BLACK);
                    break;
                case 1:
                    mTextView.setAlpha(1f);
                    mTextView.setBackgroundResource(R.drawable.checked_bg);
                    mTextView.setTextColor(Color.WHITE);
                    break;
                case 2:
                    mTextView.setAlpha(0.4f);
                    mTextView.setBackgroundResource(R.drawable.unclickable_bg);
                    mTextView.setTextColor(Color.BLACK);
                    break;
            }
        }

        public void setClick(final int position) {
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickListener != null)
                        mOnClickListener.onItemClickListener(position);
                }
            });
        }
    }
    private OnClickListener mOnClickListener;
    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }
    public interface OnClickListener {
        void onItemClickListener(int position);
    }
}
