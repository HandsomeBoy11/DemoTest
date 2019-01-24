package com.xrd.demotest;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultiImgActivity extends AppCompatActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    private MultiImgAdapter multiImgAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_img);
        ButterKnife.bind(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.addItemDecoration(getItemDecoration());
        multiImgAdapter = new MultiImgAdapter(this);
        rvList.setAdapter(multiImgAdapter);
        getData();

    }

    private void getData() {
        String imgPath = "http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg";
        List<List<String>> mList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            List<String> imgs = new ArrayList<>();
            for (int j = 0; j < i; j++) {
                imgs.add(imgPath);
            }
            mList.add(imgs);
        }
        multiImgAdapter.setData(mList);
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        return new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int adapterPosition = parent.getChildAdapterPosition(view);
                if (adapterPosition == 0) {
                    outRect.top = DensityUtil.dip2px(MultiImgActivity.this, 10);
                }
                outRect.bottom = DensityUtil.dip2px(MultiImgActivity.this, 10);
                outRect.left = DensityUtil.dip2px(MultiImgActivity.this, 12);
                outRect.right = DensityUtil.dip2px(MultiImgActivity.this, 12);
            }
        };
    }

    @OnClick(R.id.tv_finish)
    public void onViewClicked() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("flag","flag");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
