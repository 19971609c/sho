package com.bw.sho.activity;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.adapter.WalletAdapter;
import com.bw.sho.api.Api;
import com.bw.sho.base.BaseActivity;
import com.bw.sho.base.OnLoadMoreListener;
import com.bw.sho.bean.Addressinfo;
import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.bean.Wallerinfo;
import com.bw.sho.content.AddressContach;
import com.bw.sho.presenter.AddressPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class WalletActivity extends BaseActivity implements AddressContach.AddressView {

    private RecyclerView recycle;
    private int userId;
    private String sessionId;
    private CompositeDisposable disposable = new CompositeDisposable();
    private AddressPresenter addressPresenter;
    private TextView money;
    private int page = 1;
    private List<Wallerinfo.ResultBean.DetailListBean> list = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void initView() {
        //获取userId,sessionId
        SharedPreferences status = getSharedPreferences("status", MODE_PRIVATE);
        userId = status.getInt("userId", 0);
        sessionId = status.getString("sessionId", null);
        //找控件
        money = findViewById(R.id.wall_money);
        recycle = findViewById(R.id.wall_recycle);
        swipeRefreshLayout = findViewById(R.id.wall_sr);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycle.setLayoutManager(manager);

        //设置下拉时圆圈的颜色（可以尤多种颜色拼成）
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light);
        //设置下拉时圆圈的背景颜色（这里设置成白色）
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);

        addressPresenter = new AddressPresenter();
        addressPresenter.attachView(this);

    }


    @Override
    protected void initData() {
        addressPresenter.walletList(Api.WalletUrl, userId, sessionId, page, 10, disposable);

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                addressPresenter.walletList(Api.WalletUrl, userId, sessionId, page, 10, disposable);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        //上拉加载
        recycle.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            protected void onLoading(int countItem, int lastItem) {
                page++;
                addressPresenter.walletList(Api.WalletUrl, userId, sessionId, page, 10, disposable);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void walletList(Wallerinfo.ResultBean result) {
        money.setText(result.getBalance() + "");
        List<Wallerinfo.ResultBean.DetailListBean> detailList = result.getDetailList();
        if (page == 1) {
            list = new ArrayList<>();
        }
        list.addAll(detailList);
        WalletAdapter walletAdapter = new WalletAdapter(this, list);
        recycle.setAdapter(walletAdapter);
        recycle.scrollToPosition(list.size() - detailList.size() -2);
    }

    @Override
    public void getAddress(SHZcarinfo shZcarinfo) {

    }

    @Override
    public void AddressList(List<Addressinfo.ResultBean> addressList) {

    }


}
