package com.xrd.demotest;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xrd.demotest.weight.MoneyUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_get)
    TextView tvGet;
    private List<AttrsBean> mList;
    /*管理Observables 和 Subscribers订阅*/
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        createData();
        rxFunc();
        processView();
    }

    private void processView() {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if(s.length()>0){
                    et.setTextSize(39);
                    if(s.contains(".")){
                        int i = s.indexOf(".");
                        if(i==0){
                            et.setText(0+s);
                            et.setSelection(et.getText().toString().trim().length());
                        }
                        if(s.length()-1>i+2){
                            et.setText(s.substring(0,i+3));
                            et.setSelection(et.getText().toString().trim().length());
                        }
                        if(s.equals("0")||s.equals("0.0")||s.equals("0.")||s.equals("0.00")){
                            tvGet.setSelected(false);
                            tvGet.setClickable(false);
                        }else{
                            tvGet.setSelected(true);
                            tvGet.setClickable(true);
                        }
                    }else{
                        if(s.equals("00")){
                            et.setText(s.substring(0,1));
                            et.setSelection(et.getText().toString().trim().length());
                        }
                        if(s.length()>5){
                            if(!s.substring(5,6).equals(".")){
                                et.setText(s.substring(0,5));
                                et.setSelection(et.getText().toString().trim().length());
                            }

                        }
                        if(s.equals("0")||s.equals("0.0")||s.equals("0.")||s.equals("0.00")||s.equals("00")){
                            tvGet.setSelected(false);
                            tvGet.setClickable(false);
                        }else{
                            tvGet.setSelected(true);
                            tvGet.setClickable(true);
                        }
                    }
                }else{
                    tvGet.setSelected(false);
                    tvGet.setClickable(false);
                    et.setHint("");
                    et.setTextSize(20);
                }
            }
        });
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    String s = et.getText().toString().trim();
                    if (s.length() == 0) {
                        et.setHint("");
                    }
                } else {
                    String s = et.getText().toString().trim();
                    if (s.length() == 0) {
                        et.setHint("请输入金额");
                        et.setTextSize(20);
                    } else {
                        et.setHint("");
                        et.setTextSize(39);
                    }
                }
            }
        });
    }

    private void rxFunc() {
        mCompositeSubscription.add(Observable.from(mList)
                .flatMap(new Func1<AttrsBean, Observable<AttrsChildBean>>() {
                    @Override
                    public Observable<AttrsChildBean> call(AttrsBean attrsBean) {
                        return Observable.from(attrsBean.getList());
                    }
                })
                .filter(new Func1<AttrsChildBean, Boolean>() {
                    @Override
                    public Boolean call(AttrsChildBean attrsChildBean) {
                        return attrsChildBean.getType() == 1;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AttrsChildBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AttrsChildBean attrsChildBean) {
//                        Toast.makeText(Main2Activity.this, "dsfjakf", Toast.LENGTH_SHORT).show();
                        Log.d("attrsChildBean", attrsChildBean.getTypeName());
//                        Log.d("attrsChildBean",Thread.currentThread()+"");
                    }
                }));

    }

    private void createData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<AttrsChildBean> childBeanList = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                AttrsChildBean attrsChildBean = new AttrsChildBean(j, "类别" + j, new ArrayList<Bean>());
                childBeanList.add(attrsChildBean);
            }
            AttrsBean attrsBean = new AttrsBean("imgPath" + i, 10 + "", i + "", childBeanList);
            mList.add(attrsBean);
        }
    }

  /*  @OnClick(R.id.tv)
    public void onViewClicked() {
        startActivity(new Intent(this, MultiImgActivity.class));
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.clear();
    }

    @OnClick(R.id.tv_get)
    public void onViewClicked() {
        String trim = et.getText().toString().trim();
        tvGet.setText(trim);
    }
}
