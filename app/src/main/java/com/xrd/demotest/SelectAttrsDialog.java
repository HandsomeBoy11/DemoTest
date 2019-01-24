package com.xrd.demotest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xrd.demotest.listener.ShowGoodImgListener;
import com.xrd.demotest.listener.SubmitSpecCombListener;
import com.xrd.demotest.observer.IObservable;
import com.xrd.demotest.observer.IObserver;
import com.xrd.demotest.observer.ObserverEventCode;
import com.xrd.demotest.observer.ObserverHolder;
import com.xrd.demotest.weight.RoundImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WJ on 2018/11/28.
 */

public class SelectAttrsDialog extends BaseDialog implements View.OnClickListener, IObserver {
    private int mHeightPixels;
    private static final long TIME = 300;       // 动画的时间
    private LinearLayout llBg;
    private RoundImageView ivPic;
    private TextView tvPrice;
    private TextView tvCode;
    private TextView tvConfrom;
    private TextView tvJian;
    private TextView tvCount;
    private TextView tvJia;
    //    private RecyclerView rvList;
    private SelectAttrsAdapter adapter;
    private List<Object> mList = new ArrayList<>();
    private LinearLayout llContainer;
    private SpecBean mSpecBean;
    private UiData mUiData;
    private ProductModel products;
    //展示商品图片
    private ShowGoodImgListener showGoodImgListener;
    private SubmitSpecCombListener submitSpecCombListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObserverHolder.getInstance().register(this);
    }

    @Override
    protected void init() {
        findView();
        initData(mSpecBean);
        initScreen();
        // 打开的动画
        openAnim();
    }
    private void findView() {
        llBg = (LinearLayout) mView.findViewById(R.id.ll_bg);
        ivPic = (RoundImageView) mView.findViewById(R.id.iv_pic);
        tvPrice = (TextView) mView.findViewById(R.id.tv_price);
        tvCode = (TextView) mView.findViewById(R.id.tv_code);
        tvConfrom = (TextView) mView.findViewById(R.id.tv_confrom);
        tvJian = (TextView) mView.findViewById(R.id.tv_jian);
        tvCount = (TextView) mView.findViewById(R.id.tv_count);
        tvJia = (TextView) mView.findViewById(R.id.tv_jia);
        llContainer = (LinearLayout) mView.findViewById(R.id.ll_container);
        tvConfrom.setOnClickListener(this);
        tvJian.setOnClickListener(this);
        tvJia.setOnClickListener(this);
    }

    /**
     * 初始化展示的数据
     */
    private void initData(SpecBean bean) {

        if (showGoodImgListener != null) {
            showGoodImgListener.displayImg(ivPic, "");
        }
        //  2018/11/29 处理的是所有的规格占位
        List<SpecBean.AttrsBean> attrs = bean.getAttrs();

        List<ProductModel.AttributesEntity> pAttr = new ArrayList<>();//每一行规格属性的集合
        int groupId = 0;
        for (SpecBean.AttrsBean attr : attrs) {
            ProductModel.AttributesEntity group = new ProductModel.AttributesEntity();
            group.setName(attr.getKey());
            group.setId(groupId);
            for (SpecBean.AttrsBean.ValueBean item : attr.getValue()) {
                group.getAttributeMembers()//将规格属性添加到小组集合中
                        .add(new ProductModel.AttributesEntity.AttributeMembersEntity(groupId, item.getId(), item.getName()));
            }
            pAttr.add(group);//将小组添加到每行集合中
            groupId++;
        }
        // 2018/11/29  处理所有商品
        List<SpecBean.CombsBean> comb = bean.getCombs();
        Map<String, BaseSkuModel> initData = new HashMap<>();
        for (SpecBean.CombsBean g : comb) {
            initData.put(g.getComb(),
                    new BaseSkuModel(g.getPrice() + "", g.getStock()));
        }

        /**
         * 所有规格 的 组名
         */
        List<String> groupNameList = new ArrayList<String>();
        for (SpecBean.AttrsBean s : attrs) {
            groupNameList.add(s.getKey());
        }

        mUiData = new UiData();
        mUiData.setGroupNameList(groupNameList);//设置 规格 的 组名
        products = new ProductModel();

        products.setProductStocks(initData);
        products.setAttributes(pAttr);

        SpaceItemDecoration decoration = new SpaceItemDecoration(DensityUtil.dip2px(mActivity,3f), DensityUtil.dip2px(mActivity,6f));
        //添加list组
        for (int i = 0; i < products.getAttributes().size(); i++) {
            View view = View.inflate(mActivity, R.layout.attrs_item, null);
            TextView tvGuige = (TextView) view.findViewById(R.id.tv_guige);
            RecyclerView rvList = (RecyclerView) view.findViewById(R.id.rv_list);
            SpecLayoutManager specLayoutManager = new SpecLayoutManager();
            rvList.setLayoutManager(specLayoutManager);
            /**
             * 参数1：每组规格中所有属性的集合
             * 参数2：每组规格的组名称
             */
            SelectAttrsAdapter skuAdapter = new SelectAttrsAdapter(products.getAttributes().get(i).getAttributeMembers(), products.getAttributes().get(i).getName());
            mUiData.getAdapters().add(skuAdapter);
            specLayoutManager.setAutoMeasureEnabled(true);   //必须，防止recyclerview高度为wrap时测量item高度0
            rvList.addItemDecoration(decoration);
           rvList.setAdapter(skuAdapter);

            tvGuige.setText(products.getAttributes().get(i).getName());
            llContainer.addView(view);
        }
        // SKU 计算
        mUiData.setResult(Sku.skuCollection(products.getProductStocks()));

        for (String key : mUiData.getResult().keySet()) {//key:商品规格对应id逗号分割 value:为BaseSkuModel对象，中有价格和库存量
            Log.d("SKU Result", "key = " + key + " value = " + mUiData.getResult().get(key));
        }

        //设置点击监听器
        for (SelectAttrsAdapter adapter : mUiData.getAdapters()) {
            ItemClickListener listener = new ItemClickListener(mUiData, adapter);
            adapter.setOnClickListener(listener);
        }
        // 初始化按钮
        for (int i = 0; i < mUiData.getAdapters().size(); i++) {
            for (ProductModel.AttributesEntity.AttributeMembersEntity entity : mUiData.getAdapters().get(i).getAttributeMembersEntities()) {
                if (mUiData.getResult().get(entity.getAttributeMemberId() + "") == null
                        || mUiData.getResult().get(entity.getAttributeMemberId() + "").getStock() <= 0) {
                    entity.setStatus(2);//灰色填充
                }
            }
        }


    }


    @Override
    protected int getLayoutId() {
        return R.layout.select_attrs_layout;
    }

    @Override
    public float setAlpha() {
        return 0.3f;
    }

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    public void show(FragmentManager manager, String tag, SpecBean bean) {
        this.mSpecBean = bean;
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
        objectAnimator.start();
    }

    public void closeAnim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llBg, "translationY", 0, mHeightPixels);
        objectAnimator.setDuration(TIME);
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confrom://确认
                closeAnim();
                break;
            case R.id.tv_jian://减数量
                String count = tvCount.getText().toString().trim();
                Integer mCount = Integer.valueOf(count);
                if (mCount > 1) {
                    mCount--;
                    tvCount.setText(String.valueOf(mCount));
                }
                break;
            case R.id.tv_jia://加数量
                String count1 = tvCount.getText().toString().trim();
                Integer mCount1 = Integer.valueOf(count1);
                if (mCount1 <= 200) {
                    mCount1++;
                    tvCount.setText(String.valueOf(mCount1));
                }
                break;
        }
    }

    /**
     * 选择的规格id
     */
    private String specId = "null";

    private String price = "";

    private long productStock = 0;


    private void doShowSpecId(String s) {
        specId = s;
        if (s.equals("null")) {
            tvCode.setText("");
            tvPrice.setText("");
            return;
        }
        s = s.substring(0, s.length() - 1);
        specId = s;
        productStock = mUiData.getResult().get(s).getStock();
        price = mUiData.getResult().get(s).getPrice();
        tvPrice.setText("￥ " + price);
        tvCode.setText("库存 " + productStock);
        try {
            String specImgPath = getCombsBean().getSpecImg();
            if (showGoodImgListener != null) {
                showGoodImgListener.displayImg(ivPic, specImgPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Log.d(TAG, "specid " + s + " price" + mUiData.getResult().get(s).getPrice() + " stock" + mUiData.getResult().get(s).getStock());
    }
    private SpecBean.CombsBean getCombsBean() {
        SpecBean.CombsBean combsBean = null;
        for (SpecBean.CombsBean c : mSpecBean.getCombs()) {
            if (specId.equals(c.getComb())) {
                combsBean = c;
            }
        }
        return combsBean;
    }
    private void doShowSpecTips(String s) {
        tvCode.setText(s);
    }

    @Override
    public void onMessageReceived(IObservable observable, Object msg, int flag) {
        String info = (String) msg;
        switch (flag) {
            case ObserverEventCode.SKU_GETSPECID:
                doShowSpecId(info);
                break;

            case ObserverEventCode.SKU_TIPS:
                doShowSpecTips(info);
                break;

            default:
                break;
        }
    }
    /**
     * 设置展示图片的listener
     *
     * @param showGoodImgListener
     */
    public SelectAttrsDialog setShowGoodImgListener(ShowGoodImgListener showGoodImgListener) {
        this.showGoodImgListener = showGoodImgListener;
        return this;
    }


    /**
     * 设置 规格选择完成 后 提交 时的回掉
     *
     * @param submitSpecCombListener
     */
    public SelectAttrsDialog setSubmitSpecCombListener(SubmitSpecCombListener submitSpecCombListener) {
        this.submitSpecCombListener = submitSpecCombListener;
        return this;
    }
    @Override
    public void onDestroyView() {
        ObserverHolder.getInstance().unregister(this);
        showGoodImgListener=null;
        submitSpecCombListener=null;
        super.onDestroyView();
    }
}
