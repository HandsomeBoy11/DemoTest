package com.xrd.demotest.selectAttrs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xrd.demotest.BaseDialog;
import com.xrd.demotest.DensityUtil;
import com.xrd.demotest.MaxHeightLinearView;
import com.xrd.demotest.R;
import com.xrd.demotest.weight.RoundImageView;
import com.xrd.demotest.weight.SoftKeyBoardListener;
import com.xrd.demotest.weight.TextEditTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by WJ on 2019/1/2.
 */

public class SelectAttrsDialog extends BaseDialog implements View.OnClickListener {

    private int mHeightPixels;
    private static final long TIME = 300;       // 动画的时间
    private RoundImageView ivIcon;
    private MaxHeightLinearView llBg;
    private List<Data> mData;
    private FlowLayout flOne;
    private FlowLayout flTwo;
    private List<TextView> textViews;

    private int mIndex = 0;
    private SparseArray<List<DataChild>> sparseArray;
    private Map<Integer, List<TextView>> map;
    private boolean finalIsChild;
    private TextView tvAttrOne;
    private TextView tvAttrTwo;
    private TextView tvGoodsId;
    private TextView tvPrice;
    private TextEditTextView tvCount;
    private int MIN_MARK=1;
    private int MAX_MARK=200;
    private Integer beforeCount;

    @Override
    protected void init() {
        findView();
        initData();
        initScreen();
        // 打开的动画
        openAnim();
    }

    private void findView() {
        llBg = (MaxHeightLinearView) mView.findViewById(R.id.ll_bg);
        ivIcon = (RoundImageView) mView.findViewById(R.id.iv_icon);
        tvPrice = (TextView) mView.findViewById(R.id.tv_price);
        tvGoodsId = (TextView) mView.findViewById(R.id.tv_id);
        RelativeLayout rlClose = (RelativeLayout) mView.findViewById(R.id.rl_close);
        tvAttrOne = (TextView) mView.findViewById(R.id.tv_attr1);
        tvAttrTwo = (TextView) mView.findViewById(R.id.tv_attr2);
        flOne = (FlowLayout) mView.findViewById(R.id.fl_one);
        flTwo = (FlowLayout) mView.findViewById(R.id.fl_two);
        TextView tvJian = (TextView) mView.findViewById(R.id.tv_jian);
        tvCount = (TextEditTextView) mView.findViewById(R.id.tv_count);
        TextView tvJia = (TextView) mView.findViewById(R.id.tv_jia);
        TextView tvConfrom = (TextView) mView.findViewById(R.id.tv_confrom);
        rlClose.setOnClickListener(this);
        tvJian.setOnClickListener(this);
        tvConfrom.setOnClickListener(this);
        tvJia.setOnClickListener(this);
        tvCount.setSelection(tvCount.getText().toString().trim().length());
        SoftKeyBoardListener.setListener(mActivity, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                Log.e("keyBoardShow","keyBoardShow");
                tvCount.setCursorVisible(true);
            }

            @Override
            public void keyBoardHide(int height) {
                Log.e("keyBoardHide","keyBoardHide");
                String trim = tvCount.getText().toString().trim();
                if(TextUtils.isEmpty(trim)){
                    tvCount.setText("1");
                }
                tvCount.setCursorVisible(false);
            }
        });
        tvCount.setOnKeyBoardHideListener(new TextEditTextView.OnKeyBoardHideListener() {
            @Override
            public void onKeyHide(int keyCode, KeyEvent event) {
                Log.e("onKeyHide","onKeyHide");
                String trim = tvCount.getText().toString().trim();
                if(TextUtils.isEmpty(trim)){
                    tvCount.setText("1");
                }
                tvCount.setCursorVisible(false);
            }
        });
        tvCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {
                Log.e("beforeTextChanged",s.toString());
                beforeCount = Integer.valueOf(TextUtils.isEmpty(s.toString())?"1":s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int i1, int i2) {
                Log.e("onTextChanged",s.toString()+"   start   "+start);
                if(!TextUtils.isEmpty(s.toString())&&s.toString().length()>0&&start>=0){
                    if(MIN_MARK!=-1&&MAX_MARK!=-1){
                        int num=Integer.valueOf(s.toString());
                        if(num>MAX_MARK){
                            s=String.valueOf(beforeCount);
                            tvCount.setText(s);
                            tvCount.setSelection(s.length());
                        }else if(num<MIN_MARK){
                            s=String.valueOf(beforeCount);
                            tvCount.setText(s);
                            tvCount.setSelection(s.length());
                        }
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("afterTextChanged",editable.toString());
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.select_attrs_dialog;
    }

    @Override
    public float setAlpha() {
        return 0.3f;
    }

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    public void show(FragmentManager manager, String tag, List<Data> mData) {
        this.mData = mData;
        show(manager, tag, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void initScreen() {
        WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        // 获取屏幕的高度
        mHeightPixels = dm.heightPixels;
    }

    public void openAnim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llBg, "translationY", mHeightPixels, 0);
        objectAnimator.setDuration(TIME);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }

    public void closeAnim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llBg, "translationY", 0, mHeightPixels);
        objectAnimator.setDuration(TIME);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                dismiss();
            }
        });
    }

    public void initData() {
        textViews = new ArrayList<>();
        textViews.clear();
        flOne.removeAllViews();
        map = new HashMap<>();
        sparseArray = new SparseArray<>();
        for (int i = 0; i < mData.size(); i++) {
            Data data = mData.get(i);
            Holder holder = new Holder();
            TextView textView = getTextView(data, holder, i);
            textViews.add(textView);
            flOne.addView(textView);
            List<TextView> tvVeiws = new ArrayList<>();
            List<DataChild> list = mData.get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                DataChild dataChild = list.get(j);
                Holder holder1 = new Holder();
                TextView tv = getChildTextView(dataChild, holder1, j);
                tvVeiws.add(tv);
                if (data.isSelect()) {
                    flTwo.addView(tv);
                }
            }
            sparseArray.put(i, list);
            map.put(i, tvVeiws);
        }
    }

    /**
     * 二级textview获取
     * @param dataChild
     * @param holder
     * @param index
     * @return
     */
    private TextView getChildTextView(DataChild dataChild, Holder holder, int index) {

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView tv = new TextView(mActivity);
        lp.setMargins(DensityUtil.dip2px(mActivity,5), DensityUtil.dip2px(mActivity,5), DensityUtil.dip2px(mActivity,5), DensityUtil.dip2px(mActivity,5));
        tv.setPadding(DensityUtil.dip2px(mActivity,9), DensityUtil.dip2px(mActivity,5), DensityUtil.dip2px(mActivity,9), DensityUtil.dip2px(mActivity,5));
        if (dataChild.isSelect()) {
            tv.setBackgroundResource(R.drawable.orange_round5);
            tv.setTextColor(Color.parseColor("#ED6D00"));
        } else {
            tv.setBackgroundResource(R.drawable.grag_bg_round5);
            tv.setTextColor(Color.parseColor("#343434"));
        }
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(lp);
        tv.setTextSize(14);
        tv.setText(dataChild.getChildName());
        holder.textView = tv;
        holder.index = index;
        tv.setTag(holder);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Holder tag = (Holder) v.getTag();
                clickChange(tag, true);
            }
        });
        return tv;
    }

    /**
     * 一级textview获取
     * @param data
     * @param holder
     * @param index
     * @return
     */
    private TextView getTextView(Data data, Holder holder, int index) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final TextView tv = new TextView(mActivity);
        lp.setMargins(DensityUtil.dip2px(mActivity,5), DensityUtil.dip2px(mActivity,5), DensityUtil.dip2px(mActivity,5), DensityUtil.dip2px(mActivity,5));
        tv.setPadding(DensityUtil.dip2px(mActivity,9), DensityUtil.dip2px(mActivity,5), DensityUtil.dip2px(mActivity,9), DensityUtil.dip2px(mActivity,5));
        if (data.isSelect()) {
            tv.setBackgroundResource(R.drawable.orange_round5);
            tv.setTextColor(Color.parseColor("#ED6D00"));
        } else {
            tv.setBackgroundResource(R.drawable.grag_bg_round5);
            tv.setTextColor(Color.parseColor("#343434"));
        }
        tv.setGravity(Gravity.CENTER);
        tv.setLayoutParams(lp);
        tv.setTextSize(14);
        tv.setText(data.getName());
        holder.textView = tv;
        holder.index = index;
        tv.setTag(holder);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Holder tag = (Holder) v.getTag();
                mIndex = tag.index;
                clickChange(tag, false);
            }
        });
        return tv;
    }

    /**
     * 点击选中规格状态切换
     * @param tag
     * @param isChild
     */
    private void clickChange(Holder tag, boolean isChild) {
        if (isChild) {//更改二级规格选中状态
            Log.e("clickChange", "clickChange: " + map.size() + "mindex=" + mIndex+"tag.index:"+tag.index);
            List<TextView> views = map.get(mIndex);
            List<DataChild> dataChildren = sparseArray.get(mIndex);
            flTwo.removeAllViews();
            for (int i = 0; i < views.size(); i++) {
                if (i == tag.index) {
                    dataChildren.get(i).setSelect(true);
                    views.get(tag.index).setBackgroundResource(R.drawable.orange_round5);
                    views.get(tag.index).setTextColor(Color.parseColor("#ED6D00"));
                } else {
                    dataChildren.get(i).setSelect(false);
                    views.get(i).setBackgroundResource(R.drawable.grag_bg_round5);
                    views.get(i).setTextColor(Color.parseColor("#343434"));
                }
                flTwo.addView(views.get(i));
            }
        } else {
            //设置一级规格选中状态
            for (int i = 0; i < textViews.size(); i++) {
                if (i == tag.index) {
                    textViews.get(i).setBackgroundResource(R.drawable.orange_round5);
                    textViews.get(i).setTextColor(Color.parseColor("#ED6D00"));
                } else {
                    textViews.get(i).setBackgroundResource(R.drawable.grag_bg_round5);
                    textViews.get(i).setTextColor(Color.parseColor("#343434"));
                }
            }
            //对二级规格重置
            List<TextView> views = map.get(mIndex);
            List<DataChild> childList = sparseArray.get(mIndex);
            flTwo.removeAllViews();
            for (int i = 0; i < views.size(); i++) {
                if (/*childList.get(i).isSelect()*/
                        i==0) {
                    childList.get(i).setSelect(true);
                    views.get(i).setBackgroundResource(R.drawable.orange_round5);
                    views.get(i).setTextColor(Color.parseColor("#ED6D00"));
                } else {
                    childList.get(i).setSelect(false);
                    views.get(i).setBackgroundResource(R.drawable.grag_bg_round5);
                    views.get(i).setTextColor(Color.parseColor("#343434"));
                }
                flTwo.addView(views.get(i));
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_close://关闭dialog
                closeAnim();
                break;
            case R.id.tv_jian://数量减
                doCountDown();
                break;
            case R.id.tv_jia://数量加
                doCountUp();
                break;
            case R.id.tv_confrom://确认
                doConfrom();
                closeAnim();
                break;
        }
    }
    private OnConfromCallBack callBack;

    public void setCallBack(OnConfromCallBack callBack) {
        this.callBack = callBack;
    }

    public interface OnConfromCallBack{
        void onConfromCallBack(String goodsId,String count);
    }

    /**
     * 确认
     */
    private void doConfrom(){
        List<DataChild> dataChildren = sparseArray.get(mIndex);
        String goodsId=null;
        for (int i = 0; i < dataChildren.size(); i++) {
            DataChild dataChild = dataChildren.get(i);
            if(dataChild.isSelect()){
                goodsId = dataChild.getGoodsId();
                break;
            }
        }
        String finalCount = tvCount.getText().toString().trim();
        if(callBack!=null){
            callBack.onConfromCallBack(goodsId,finalCount);
        }
    }

    /**
     * 增加数量
     */
    private void doCountUp() {
        String count = tvCount.getText().toString().trim();
        Integer nCount = Integer.valueOf(count);
        if (nCount < 200) {
            nCount++;
            tvCount.setText(String.valueOf(nCount));
        }
    }

    /**
     * 减少数量
     */
    private void doCountDown() {
        String count = tvCount.getText().toString().trim();
        Integer nCount = Integer.valueOf(count);
        if(nCount>1){
            nCount--;
            tvCount.setText(String.valueOf(nCount));
        }
    }

    static class Holder {
        public TextView textView;
        public int index;
        public List<TextView> textViewList;

    }
}
