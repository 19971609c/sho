package com.bw.sho.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.bw.sho.R;
import com.bw.sho.base.BaseActivity;
import com.bw.sho.fragment.CircleFragment;
import com.bw.sho.fragment.GoodsCarFragment;
import com.bw.sho.fragment.HomeFragment;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private CircleFragment circleFragment;
    private HomeFragment homeFragment;
    private FragmentManager manager;
    private GoodsCarFragment goodsCarFragment;

    @Override
    protected void initData() {
        //获取fragment管理器
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //添加fragment
        circleFragment = new CircleFragment();
        homeFragment = new HomeFragment();
        goodsCarFragment = new GoodsCarFragment();
        transaction.add(R.id.main_frame, homeFragment);
        transaction.add(R.id.main_frame, circleFragment);
        transaction.add(R.id.main_frame, goodsCarFragment);
        //默认显示第一个
        transaction.show(homeFragment).hide(circleFragment).hide(goodsCarFragment);
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
                transaction.show(homeFragment).hide(circleFragment).hide(goodsCarFragment);
                break;
            case R.id.main_circle:
                transaction.hide(homeFragment).show(circleFragment).hide(goodsCarFragment);
                break;
            case R.id.main_shopping:
                transaction.hide(homeFragment).hide(circleFragment).show(goodsCarFragment);
                break;
            case R.id.main_order:
                break;
            case R.id.main_my:
                break;
        }
        //提交
        transaction.commit();
    }
}
