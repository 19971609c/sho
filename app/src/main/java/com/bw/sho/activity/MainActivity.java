package com.bw.sho.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.bw.sho.R;
import com.bw.sho.base.BaseActivity;
import com.bw.sho.fragment.CircleFragment;
import com.bw.sho.fragment.GoodsCarFragment;
import com.bw.sho.fragment.HomeFragment;
import com.bw.sho.fragment.MyFragment;
import com.bw.sho.fragment.OrderFragment;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private CircleFragment circleFragment;
    private HomeFragment homeFragment;
    private FragmentManager manager;
    private GoodsCarFragment goodsCarFragment;
    private MyFragment myFragment;
    private OrderFragment orderFragment;

    @Override
    protected void initData() {
        //获取fragment管理器
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //添加fragment
        homeFragment = new HomeFragment();
        circleFragment = new CircleFragment();
        goodsCarFragment = new GoodsCarFragment();
        orderFragment = new OrderFragment();
        myFragment = new MyFragment();
        transaction.add(R.id.main_frame, homeFragment);
        transaction.add(R.id.main_frame, circleFragment);
        transaction.add(R.id.main_frame, goodsCarFragment);
        transaction.add(R.id.main_frame, orderFragment);
        transaction.add(R.id.main_frame, myFragment);
       /* //制定那个Fragment
        int id = getIntent().getIntExtra("Fragmentid", 0);
        if (id == 1) {
            transaction.hide(homeFragment).hide(circleFragment).hide(goodsCarFragment).hide(myFragment).show(orderFragment);
            transaction.commit();
            return;
        }*/
        //默认显示第一个
        transaction.show(homeFragment).hide(circleFragment).hide(goodsCarFragment).hide(orderFragment).hide(myFragment);
        //提交
        transaction.commit();
    }

    @Override
    protected void initView() {
        //找控件
        RadioGroup mainGroup = findViewById(R.id.main_group);
        //mainGroup改变监听
        mainGroup.setOnCheckedChangeListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    //mainGroup改变事件
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = manager.beginTransaction();
        //判断页面
        switch (checkedId) {
            case R.id.main_home:
                transaction.show(homeFragment).hide(circleFragment).hide(goodsCarFragment).hide(myFragment).hide(orderFragment);
                break;
            case R.id.main_circle:
                transaction.hide(homeFragment).show(circleFragment).hide(goodsCarFragment).hide(myFragment).hide(orderFragment);
                break;
            case R.id.main_shopping:
                transaction.hide(homeFragment).hide(circleFragment).show(goodsCarFragment).hide(myFragment).hide(orderFragment);
                break;
            case R.id.main_order:
                transaction.hide(homeFragment).hide(circleFragment).hide(goodsCarFragment).hide(myFragment).show(orderFragment);
                break;
            case R.id.main_my:
                transaction.hide(homeFragment).hide(circleFragment).hide(goodsCarFragment).show(myFragment).hide(orderFragment);
                break;
        }
        //提交
        transaction.commit();
    }

}
