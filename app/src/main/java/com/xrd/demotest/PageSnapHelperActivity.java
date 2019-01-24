package com.xrd.demotest;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PageSnapHelperActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private RecyclerView rvLeft;
    private SmoothScrollLayoutManager layoutManager;
    private MyAdapter mLeftAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_snap_helper);
        rvList = (RecyclerView) findViewById(R.id.rv);
        rvLeft = (RecyclerView) findViewById(R.id.rv_left);
        rvLeft.setLayoutManager(new LinearLayoutManager(this));
        layoutManager = new SmoothScrollLayoutManager(this);
        rvList.setLayoutManager(layoutManager);
        rvList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
                if(itemPosition!=0){
                    outRect.top= (int) getResources().getDimension(R.dimen.activity_margin);
                }
                outRect.bottom=(int) getResources().getDimension(R.dimen.activity_margin);
            }
        });
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvList);
        mLeftAdapter = new MyAdapter(true);
        rvLeft.setAdapter(mLeftAdapter);
        rvList.setAdapter(new MyAdapter(false));
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                    mLeftAdapter.setSelectPostion(firstVisibleItemPosition);
                }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }
    public int currentPosition=0;
    public class MyAdapter extends RecyclerView.Adapter{
        private boolean isLeft;
        private boolean isClick;
        public MyAdapter(boolean isLeft){
            this.isLeft=isLeft;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=null;
            if(isLeft){
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_left, parent, false);
            }else{
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tv, parent, false);

            }
            return new ViewHodler(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ViewHodler holder1 = (ViewHodler) holder;
            holder1.tv.setText(position+"页面");
            holder1.tv.setTag(position);

            if(isLeft){
                if(position==currentPosition){
                    holder1.tv.setSelected(true);
                }else{
                    holder1.tv.setSelected(false);
                }
                holder1.tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int tag = (int) view.getTag();
                        rvList.smoothScrollToPosition(tag);
                        if(currentPosition!=tag){
                            isClick=true;
                            currentPosition=tag;
                            notifyDataSetChanged();
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }

        public void setSelectPostion(int firstVisibleItemPosition) {
            if(!isClick){
                currentPosition=firstVisibleItemPosition;
                notifyDataSetChanged();
            }else{
                isClick=false;
            }
        }
    }
    public class ViewHodler extends RecyclerView.ViewHolder {
        private int index;

        private  TextView tv;

        public ViewHodler(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_child);
        }
    }
}
