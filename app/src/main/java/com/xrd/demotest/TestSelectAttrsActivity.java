package com.xrd.demotest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xrd.demotest.selectAttrs.*;
import com.xrd.demotest.selectAttrs.SelectAttrsDialog;
import com.xrd.demotest.weight.SoftKeyBoardListener;
import com.xrd.demotest.weight.StatusBarCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WJ on 2019/1/2.
 */

public class TestSelectAttrsActivity extends AppCompatActivity {

    private SelectAttrsDialog attrsDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this,Color.BLACK);
        setContentView(R.layout.test_acitity);

        ((TextView) findViewById(R.id.tv_select)).setText(Html.fromHtml(getTextFromAssets("news.html")));
        ResponsBean responsBean = new ResponsBean();
        final List<Data> oneList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Data data = new Data();
            data.setName("我是规格one：" + i);
            if (i == 0) {
                data.setSelect(true);
            } else {
                data.setSelect(false);
            }
            List<DataChild> twoList = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                DataChild dataChild = new DataChild();
                dataChild.setChildName("规格天我：" + i + j);
                dataChild.setGoodsId(String.valueOf(i) + j);
                if (j == 0) {
                    dataChild.setSelect(true);
                } else {
                    dataChild.setSelect(false);
                }
                twoList.add(dataChild);
            }
            data.setList(twoList);
            oneList.add(data);
        }
        responsBean.setDataList(oneList);
        Gson gson = new Gson();
        String stringEntity = gson.toJson(responsBean);
       /* if (stringEntity.length() > 3000) {
            for (int i = 0; i < stringEntity.length(); i += 3000) {
                if (i + 3000 < stringEntity.length()) {
                    Log.e("json", stringEntity.substring(i, i + 3000));
                } else {
                    Log.e("json", stringEntity.substring(i, stringEntity.length()));
                }
            }
        } else {
            Log.e("json", stringEntity);
        }*/
        Log.e("json", stringEntity);
        attrsDialog = new SelectAttrsDialog();
        ((TextView) findViewById(R.id.tv_select)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attrsDialog.show(getFragmentManager(), "selectAttrs", oneList);
            }
        });
        attrsDialog.setCallBack(new SelectAttrsDialog.OnConfromCallBack() {
            @Override
            public void onConfromCallBack(String goodsId, String count) {
                Toast.makeText(TestSelectAttrsActivity.this, "goodId: " + goodsId + "  count: " + count, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getTextFromAssets(String test) {
        InputStream open = null;
        BufferedReader buffer = null;
        try {
            open = getResources().getAssets().open(test);
            buffer = new BufferedReader(new InputStreamReader(open));
            String result = "";
            String line = "";
            while ((line = buffer.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
           /* try {
                open.close();
                buffer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        return "";
    }
}
