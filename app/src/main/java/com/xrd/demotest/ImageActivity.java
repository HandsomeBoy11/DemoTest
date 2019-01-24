package com.xrd.demotest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

/**
 * Created by WJ on 2018/12/11.
 */

public class ImageActivity extends AppCompatActivity {

    private RecyclerView rvList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        ImgAdapter imgAdapter = new ImgAdapter(this);
        rvList.setAdapter(imgAdapter);
    }
}
