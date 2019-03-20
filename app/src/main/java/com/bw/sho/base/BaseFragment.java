package com.bw.sho.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Auther: 不懂
 * @Date: 2019/3/13 16:52:15
 * @Description:
 */
public abstract class BaseFragment extends Fragment {
    //是否加载过视图
    protected boolean isCreatView = false;
    //是否加载过数据
    protected boolean isLoadData = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLatoutId(), container, false);
        //获取控件
        initView(view);
        //加载过视图设置true
        isCreatView = true;
        isloadData();
        return view;
    }

    //视图对用户是否可见,系统方法

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isloadData();
    }

    private void isloadData() {
        //判断是否加载过视图
        if (!isCreatView) {
            return;
        }
        //判断是否可见
        if (getUserVisibleHint()) {
            //判断是否加载过数据
            if (!isLoadData) {
                //加载数据
                initData();
                //事件监听
                initListener();
                //加载过数据设置为true
                isLoadData = true;
            }
        } else {
            if (isLoadData) {
                //停止加载
                stopLoad();
            }
        }
    }


    //得到当前界面的布局文件id(由子类实现)
    protected abstract int getLatoutId();

    //加载view
    protected abstract void initView(View view);


    //加载数据
    protected abstract void initData();

    //事件监听
    protected abstract void initListener();

    //停止加载
    protected abstract void stopLoad();


    //销毁视图

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isCreatView = false;
        isLoadData = false;
    }
}
