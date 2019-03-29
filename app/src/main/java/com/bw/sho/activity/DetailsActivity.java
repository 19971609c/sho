package com.bw.sho.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.sho.R;
import com.bw.sho.adapter.DetailsAdapter;
import com.bw.sho.api.Api;
import com.bw.sho.bean.AddCarinfo;
import com.bw.sho.bean.Circleinfo;
import com.bw.sho.bean.CreatOrderinfo;
import com.bw.sho.bean.Discussinfo;
import com.bw.sho.bean.FindCarResclt;
import com.bw.sho.bean.FindCarinfo;
import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.content.DiscussContract;
import com.bw.sho.content.FindCarContach;
import com.bw.sho.presenter.DiscussPresenter;
import com.bw.sho.presenter.FindCarPresenter;
import com.bw.sho.view.IdeaScrollView;
import com.bw.sho.view.IdeaViewPager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import qiu.niorgai.StatusBarCompat;

public class DetailsActivity extends AppCompatActivity implements DiscussContract.DiscussView, View.OnClickListener, FindCarContach.FindCarView {


    private IdeaViewPager viewPager;
    private IdeaScrollView ideaScrollView;
    private TextView text;
    private LinearLayout header;
    private RadioGroup radioGroup;
    private LinearLayout headerParent;
    private ImageView icon;
    private View layer;
    private float currentPercentage = 0;
    private Map<Integer, String> headerMap = new HashMap<>();
    private RadioGroup.OnCheckedChangeListener radioGroupListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                radioButton.setTextColor(radioButton.isChecked() ? getRadioCheckedAlphaColor(currentPercentage) : getRadioAlphaColor(currentPercentage));
                if (radioButton.isChecked() && isNeedScrollTo) {
                    ideaScrollView.setPosition(i);
                }
            }
        }
    };
    private boolean isNeedScrollTo = true;
    private DiscussPresenter discussPresenter;
    private RecyclerView recycler;
    private TextView discuss_num;
    private TextView money;
    private TextView num;
    private WebView webView;
    private TextView title;
    private TextView weig;
    private View two;
    private SharedPreferences status;
    private int commodityId;
    private FindCarPresenter findCarPresenter;
    private int price;
    private Discussinfo.ResultBean result;
    //订阅者管理器
    CompositeDisposable disposable = new CompositeDisposable();
    private int userId;
    private String sessionId;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        StatusBarCompat.translucentStatusBar(this);
        //验证是否登录
        status = getSharedPreferences("status", MODE_PRIVATE);
        userId = status.getInt("userId", 0);
        sessionId = status.getString("sessionId", null);
        Log.i("userid", userId + "-----" + sessionId);
        //传过来的Id
        Intent intent = getIntent();
        int commodityId = intent.getIntExtra("commodityId", 0);
        //找布局
        inview();

        View one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        View three = findViewById(R.id.three);
        ArrayList<Integer> araryDistance = new ArrayList<>();

        //请求数据
        initData(commodityId);

        Rect rectangle = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        ideaScrollView.setViewPager(viewPager, getMeasureHeight(headerParent) - rectangle.top);
        icon.setImageAlpha(0);
        radioGroup.setAlpha(0);
        radioGroup.check(radioGroup.getChildAt(0).getId());

        araryDistance.add(0);
        araryDistance.add(getMeasureHeight(one) - getMeasureHeight(headerParent));
        araryDistance.add(getMeasureHeight(one) + getMeasureHeight(two) - getMeasureHeight(headerParent));
        araryDistance.add(getMeasureHeight(one) + getMeasureHeight(two) + getMeasureHeight(three) - getMeasureHeight(headerParent));

        ideaScrollView.setArrayDistance(araryDistance);

        ideaScrollView.setOnScrollChangedColorListener(new IdeaScrollView.OnScrollChangedColorListener() {
            @Override
            public void onChanged(float percentage) {

                int color = getAlphaColor(percentage > 0.9f ? 1.0f : percentage);
                header.setBackgroundDrawable(new ColorDrawable(color));
                radioGroup.setBackgroundDrawable(new ColorDrawable(color));
                icon.setImageAlpha((int) ((percentage > 0.9f ? 1.0f : percentage) * 255));
                radioGroup.setAlpha((percentage > 0.9f ? 1.0f : percentage) * 255);

                setRadioButtonTextColor(percentage);

            }

            @Override
            public void onChangedFirstColor(float percentage) {

            }

            @Override
            public void onChangedSecondColor(float percentage) {

            }
        });

        ideaScrollView.setOnSelectedIndicateChangedListener(new IdeaScrollView.OnSelectedIndicateChangedListener() {
            @Override
            public void onSelectedChanged(int position) {
                isNeedScrollTo = false;
                //radioGroup.check(radioGroup.getChildAt(position).getId());
                isNeedScrollTo = true;
            }
        });

        radioGroup.setOnCheckedChangeListener(radioGroupListener);
    }

    //找控件
    private void inview() {
        header = (LinearLayout) findViewById(R.id.header);
        headerParent = (LinearLayout) findViewById(R.id.headerParent);
        icon = (ImageView) findViewById(R.id.icon);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        ideaScrollView = (IdeaScrollView) findViewById(R.id.ideaScrollView);
        viewPager = (IdeaViewPager) findViewById(R.id.viewPager);
        layer = findViewById(R.id.layer);
        ImageView back = findViewById(R.id.de_back);
        recycler = findViewById(R.id.de_discuss);
        discuss_num = findViewById(R.id.de_discuss_num);
        money = findViewById(R.id.de_money);
        num = findViewById(R.id.de_num);
        webView = findViewById(R.id.de_web);
        title = findViewById(R.id.de_title);
        weig = findViewById(R.id.de_weight);
        Button shopping = findViewById(R.id.de_shopping);
        Button order = findViewById(R.id.de_order);
        shopping.setOnClickListener(this);
        order.setOnClickListener(this);
        back.setOnClickListener(this);
        //实例p
        findCarPresenter = new FindCarPresenter();
        discussPresenter = new DiscussPresenter();

        findCarPresenter.attachView(this);
        discussPresenter.attachView(this);

    }

    //请求数据
    private void initData(int commodityId) {
        //请求数据
        discussPresenter.getDiscussData(Api.DiscussUrl, commodityId, disposable);
    }

    //接收到的值
    @Override
    public void getDiscussData(Discussinfo.ResultBean result) {
        this.result = result;
        //---------------------------------------------------------------
        commodityId = result.getCommodityId();
        //----------------------------------------------------------------
        price = result.getPrice();
        discuss_num.setText("当前评论总数 " + result.getCommentNum());
        money.setText("$" + result.getPrice());
        num.setText("已销售" + result.getSaleNum() + "件");
        title.setText(result.getDescribe());
        weig.setText("重量 " + result.getWeight());
        //轮播图
        String[] split = result.getPicture().split(",");
        Glide.with(this).load(split[0]).into(icon);
        List<ImageView> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            ImageView imageView = new ImageView(this);
            Glide.with(this).load(split[i]).into(imageView);
            list.add(imageView);
        }
        DetailsAdapter detailsAdapter = new DetailsAdapter(list);
        viewPager.setAdapter(detailsAdapter);
        //详情
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        String s = "<script type=\"text/javascript\">" +
                "var imgs=document.getElementsByTagName('img');" +
                "for(var i = 0; i<imgs.length; i++){" +
                "imgs[i].style.width='100%';" +
                "imgs[i].style.height='auto';" +
                "}" +
                "</script>";
        String details = result.getDetails();
        webView.loadDataWithBaseURL(null, details + s + "<html><body>", "textml", "utf-8", null);

    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.de_back:
                finish();
                break;
            case R.id.de_shopping://加入购物车
                if (status.getBoolean("statusId", false)) {
                    //查询购物车
                    findCarPresenter.getFindCar(Api.findCarUrl, userId, sessionId, disposable);
                } else {
                    setLogin();
                }
                break;
            case R.id.de_order:
                if (status.getBoolean("statusId", false)) {
                    Intent intent = new Intent(DetailsActivity.this, ConfirmOrderActivity.class);
                    intent.putExtra("result", result);
                    startActivity(intent);
                } else {
                    setLogin();
                }
                break;
        }
    }

    //添加购物车
    @Override
    public void getCar(SHZcarinfo shZcarinfo) {
        if (shZcarinfo.getStatus().equals("0000")) {
            Toast.makeText(DetailsActivity.this, shZcarinfo.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(DetailsActivity.this, shZcarinfo.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private List<AddCarinfo> list2 = new ArrayList<>();

    //查询购物车
    @Override
    public void getFindCar(FindCarinfo findCarinfo) {
        List<FindCarResclt> result = findCarinfo.getResult();
        for (int i = 0; i < result.size(); i++) {
            list2.add(new AddCarinfo(result.get(i).getCommodityId(), result.get(i).getCount()));
        }
        list2.add(new AddCarinfo(commodityId, 1));
        Gson gson = new Gson();
        String json = gson.toJson(list2);
        Log.i("json", json);
        //添加购物车
        discussPresenter.getCar(Api.CarUrl, userId, sessionId, json, disposable);
    }

    @Override
    public void CircleData(List<Circleinfo.ResultBean> circleList) {

    }

    @Override
    public void CreateOrder(CreatOrderinfo shZcarinfo) {

    }

    //提示用户登录
    private void setLogin() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tvConfirm = (TextView) contentView.findViewById(R.id.tv_confirm);
        TextView tvCancel = (TextView) contentView.findViewById(R.id.tv_cancel);
        tvConfirm.setText("登录");
        tvCancel.setText("取消");
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, LoginActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        // 设置PopupWindow以外部分的背景颜色  有一种变暗的效果
        final WindowManager.LayoutParams wlBackground = getWindow().getAttributes();
        wlBackground.alpha = 0.5f;      // 0.0 完全不透明,1.0完全透明
        getWindow().setAttributes(wlBackground);
        // 当PopupWindow消失时,恢复其为原来的颜色
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                wlBackground.alpha = 1.0f;
                getWindow().setAttributes(wlBackground);
            }
        });
        //设置PopupWindow进入和退出动画
        popupWindow.setAnimationStyle(R.style.anim_popup_centerbar);
        // 设置PopupWindow显示在中间
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }


    public void setRadioButtonTextColor(float percentage) {
        if (Math.abs(percentage - currentPercentage) >= 0.1f) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                radioButton.setTextColor(radioButton.isChecked() ? getRadioCheckedAlphaColor(percentage) : getRadioAlphaColor(percentage));
            }
            this.currentPercentage = percentage;
        }
    }

    public int getMeasureHeight(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredHeight();
    }

    public int getAlphaColor(float f) {
        return Color.argb((int) (f * 255), 0x09, 0xc1, 0xf4);
    }

    public int getLayerAlphaColor(float f) {
        return Color.argb((int) (f * 255), 0x09, 0xc1, 0xf4);
    }

    public int getRadioCheckedAlphaColor(float f) {
        return Color.argb((int) (f * 255), 0x44, 0x44, 0x44);
    }

    public int getRadioAlphaColor(float f) {
        return Color.argb((int) (f * 255), 0xFF, 0xFF, 0xFF);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        discussPresenter.detachView(this);
        findCarPresenter.delachView(this);
        boolean disposed = disposable.isDisposed();
        if (!disposed) {
            //取消订阅
            disposable.clear();
            //解除订阅
            disposable.dispose();
        }
    }


}
