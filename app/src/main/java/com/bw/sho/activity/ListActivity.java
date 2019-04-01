package com.bw.sho.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bw.sho.R;
import com.bw.sho.adapter.ListOneAdapter;
import com.bw.sho.adapter.ListThreeAdapter;
import com.bw.sho.adapter.ListTwoAdapter;
import com.bw.sho.api.Api;
import com.bw.sho.base.BaseActivity;
import com.bw.sho.bean.OneListinfo;
import com.bw.sho.bean.ThreeListinfo;
import com.bw.sho.bean.TwoListinfo;
import com.bw.sho.content.ListContach;
import com.bw.sho.presenter.ListPresenter;
import com.bw.sho.view.SearchBoxView;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ListActivity extends BaseActivity implements View.OnClickListener, ListContach.ListContachView {

    private SearchBoxView searchBox;
    private RecyclerView recycle;
    private ImageView common;
    private ListPresenter listPresenter;
    private CompositeDisposable disposable = new CompositeDisposable();
    private RecyclerView towre;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView() {
        searchBox = findViewById(R.id.li_search);
        recycle = findViewById(R.id.li_recycle);
        common = findViewById(R.id.li_common);
        //设置样式
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        recycle.setLayoutManager(manager);
        common.setOnClickListener(this);

        listPresenter = new ListPresenter();
        listPresenter.attachView(this);
    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        String mid = intent.getStringExtra("Mid");
        Log.i("three-------", mid);
        listPresenter.getThreeList(Api.ThreeListUrl, mid, 1, 6, disposable);

        //回调放法
        searchBox.setOnIntent(new SearchBoxView.OnIntent() {
            @Override
            public void onintent() {
                //跳转到搜索页面
                Intent intent = new Intent(ListActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_common:
                //请求一级列表数据
                listPresenter.getOnelist(Api.OneListUrl, disposable);
                break;
        }
    }

    //one
    @Override
    public void getOnelist(List<OneListinfo.ResultBean> oneList) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_onelist, null, false);
        //RecyclerView列表控件
        RecyclerView ontList = view.findViewById(R.id.one_list);
        towre = view.findViewById(R.id.two_list);
        //设置管理器
        ontList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //设置适配器
        ListOneAdapter listOneAdapter = new ListOneAdapter(this, oneList);
        ontList.setAdapter(listOneAdapter);

        final PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popupwindow_background));
        popupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        //这是背景色
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        popupWindow.setTouchable(true);
        //显示
        popupWindow.showAsDropDown(searchBox);

        listOneAdapter.setOnOneClick(new ListOneAdapter.OnOneClick() {
            @Override
            public void setIdData(String id) {
                //请求二级列表
                listPresenter.getTwoList(Api.TwoListUrl, id, disposable);
            }
        });
    }

    //two
    @Override
    public void getTwoList(List<TwoListinfo.ResultBean> tworesult) {
        //设置管理器
        towre.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        //设置适配器
        ListTwoAdapter listTwoAdapter = new ListTwoAdapter(this, tworesult);
        towre.setAdapter(listTwoAdapter);
        listTwoAdapter.setOnTwoClick(new ListTwoAdapter.OnTwoClick() {
            @Override
            public void setIdData(String id) {
                Log.i("three-------", id);
                listPresenter.getThreeList(Api.ThreeListUrl, id, 1, 6, disposable);
            }
        });
    }

    //three
    @Override
    public void getThreeList(List<ThreeListinfo.ResultBean> threeresult) {
        ListThreeAdapter listThreeAdapter = new ListThreeAdapter(this, threeresult);
        recycle.setAdapter(listThreeAdapter);
        listThreeAdapter.setOnOneClick(new ListThreeAdapter.OnOneClick() {
            @Override
            public void setIdData(int id) {
                Intent intent = new Intent(ListActivity.this, DetailsActivity.class);
                intent.putExtra("commodityId", id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑
        listPresenter.delachView(this);
        boolean disposed = disposable.isDisposed();
        if (!disposed) {
            //消除订阅
            disposable.clear();
            //解除订阅
            disposable.dispose();
        }
    }
}
