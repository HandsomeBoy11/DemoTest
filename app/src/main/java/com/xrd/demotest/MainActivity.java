package com.xrd.demotest;

import android.content.Intent;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.xrd.demotest.listener.ShowGoodImgListener;
import com.xrd.demotest.listener.SubmitSpecCombListener;
import com.xrd.demotest.weight.RoundImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvList;
    private YuanJiaoImageView iv;
    Handler mHandler=new Handler();
    private SelectAttrsDialog selectAttrsDialog;
    private RoundImageView roundIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("log","onCreate");
        String flag = getIntent().getStringExtra("flag");
        Log.d("log",flag+"122445");
        iv = (YuanJiaoImageView) findViewById(R.id.iv);
        roundIv = (RoundImageView) findViewById(R.id.round_ic);
        rvList = (RecyclerView) findViewById(R.id.rv);
        MyItemDecorView myItemDecorView = new MyItemDecorView();
        myItemDecorView.setSpace(3,12,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(gridLayoutManager);
        rvList.addItemDecoration(myItemDecorView);
        MyAdapter myAdapter = new MyAdapter(this);
        rvList.setAdapter(myAdapter);
        iv.setImageResource(R.drawable.normal_display);
        roundIv.setOnClickListener(this);
        getDialogData();
//        getPhoto();
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomDialog();

                /*final JoinSuccAlertDialog builder = new JoinSuccAlertDialog(MainActivity.this).builder();
                builder.show();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        builder.dismiss();
                    }
                },3000);*/
            }
        });

    }
    public void getPhoto(){
        String url = "http://test.belightinnovation.com/file/download?fileID=af7c5d9861d241ed972e2a5b9a08f774";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("MainActivity", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("MainActivity", "onResponse: " + response.body().string());
                final String string = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Glide.with(MainActivity.this).load(string).into(iv);
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("log","onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("log","onDestroy");

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d("log","onSaveInstanceState");
        outState.putString("key","保存文字");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("log","onRestoreInstanceState");
        if(savedInstanceState!=null){
            String key = savedInstanceState.getString("key");
            if(!TextUtils.isEmpty(key))
            Log.d("onRestoreInstanceState",key);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("log","onPause");
    }

    private void getDialogData() {
        selectAttrsDialog = new SelectAttrsDialog();

        selectAttrsDialog.setShowGoodImgListener(new ShowGoodImgListener() {
            @Override
            public void displayImg(ImageView iv, String imgUrl) {
                Glide.with(MainActivity.this).load(imgUrl).into(iv);
            }
        }).setSubmitSpecCombListener(new SubmitSpecCombListener() {
            @Override
            public void onSubmit(SpecBean.CombsBean combBean, int num) {

            }
        });
    }
    public void showBottomDialog(){
        SpecBean bean = new Gson().fromJson(json, SpecBean.class);
        Log.e("json===",json);
        if(selectAttrsDialog!=null){
            selectAttrsDialog.show(getFragmentManager(),"selectAttrs",bean);
        }
    }

    String json = "{\n" +
            "    \"attrs\": [\n" +
            "        {\n" +
            "            \"key\": \"颜色\",\n" +
            "            \"value\": [\n" +
            "                {\n" +
            "                    \"id\": 3,\n" +
            "                    \"name\": \"红色\",\n" +
            "                    \"ownId\": 1\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 4,\n" +
            "                    \"name\": \"蓝色\",\n" +
            "                    \"ownId\": 1\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"key\": \"重量\",\n" +
            "            \"value\": [\n" +
            "                {\n" +
            "                    \"id\": 5,\n" +
            "                    \"name\": \"10KG\",\n" +
            "                    \"ownId\": 2\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 6,\n" +
            "                    \"name\": \"20KG\",\n" +
            "                    \"ownId\": 2\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 7,\n" +
            "                    \"name\": \"30KG\",\n" +
            "                    \"ownId\": 2\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"key\": \"产地\",\n" +
            "            \"value\": [\n" +
            "                {\n" +
            "                    \"id\": 24,\n" +
            "                    \"name\": \"江油\",\n" +
            "                    \"ownId\": 22\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 23,\n" +
            "                    \"name\": \"绵阳\",\n" +
            "                    \"ownId\": 22\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 31,\n" +
            "                    \"name\": \"四川绵阳市涪城区的撒啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊\",\n" +
            "                    \"ownId\": 22\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"key\": \"尺寸\",\n" +
            "            \"value\": [\n" +
            "                {\n" +
            "                    \"id\": 20,\n" +
            "                    \"name\": \"30cm\",\n" +
            "                    \"ownId\": 14\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 19,\n" +
            "                    \"name\": \"20cm\",\n" +
            "                    \"ownId\": 14\n" +
            "                },\n" +
            "                {\n" +
            "                    \"id\": 18,\n" +
            "                    \"name\": \"10cm\",\n" +
            "                    \"ownId\": 14\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ],\n" +
            "    \"combs\": [\n" +
            "        {\n" +
            "            \"comb\": \"4,6,23,20\",\n" +
            "            \"desc\": \"蓝色-20KG-绵阳-30cm\",\n" +
            "            \"id\": 10,\n" +
            "            \"price\": 1.0,\n" +
            "            \"productId\": 5,\n" +
            "            \"stock\": 2,\n" +
            "            \"specImg\":\"http://ww3.sinaimg.cn/mw600/0073ob6Pgy1fo91049t9mj30lc0w0dnh.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"comb\": \"4,5,23,19\",\n" +
            "            \"desc\": \"蓝色-10KG-绵阳-20cm\",\n" +
            "            \"id\": 8,\n" +
            "            \"price\": 22.0,\n" +
            "            \"productId\": 5,\n" +
            "            \"stock\": 333,\n" +
            "             \"specImg\":\"http://wx4.sinaimg.cn/mw600/0072bW0Xly1fo908gkyqjj30en0miabp.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"comb\": \"4,5,24,19\",\n" +
            "            \"desc\": \"蓝色-10KG-江油-20cm\",\n" +
            "            \"id\": 9,\n" +
            "            \"price\": 1.0,\n" +
            "            \"productId\": 5,\n" +
            "            \"stock\": 2,\n" +
            "             \"specImg\":\"http://wx1.sinaimg.cn/mw600/0072bW0Xly1fo8zz4znwyj30hq0qoabz.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"comb\": \"3,6,24,18\",\n" +
            "            \"desc\": \"红色-20KG-江油-10\",\n" +
            "            \"id\": 11,\n" +
            "            \"price\": 1.0,\n" +
            "            \"productId\": 5,\n" +
            "            \"stock\": 2,\n" +
            "            \"specImg\":\"http://wx2.sinaimg.cn/mw600/0072bW0Xly1fo8zf0pn15j30ia0tzdox.jpg\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.round_ic:
                startActivity(new Intent(this,Main2Activity.class));
                break;
        }
    }
}
