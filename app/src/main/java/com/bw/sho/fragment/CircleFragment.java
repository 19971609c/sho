package com.bw.sho.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bw.sho.R;
import com.bw.sho.adapter.CircleAdapter;
import com.bw.sho.api.Api;
import com.bw.sho.base.BaseFragment;
import com.bw.sho.base.OnLoadMoreListener;
import com.bw.sho.bean.Circleinfo;
import com.bw.sho.bean.CreatOrderinfo;
import com.bw.sho.bean.FindCarinfo;
import com.bw.sho.content.FindCarContach;
import com.bw.sho.presenter.FindCarPresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Auther: 不懂
 * @Date: 2019/3/14 09:02:41
 * @Description:
 */
public class CircleFragment extends BaseFragment implements FindCarContach.FindCarView {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout scroll;
    private FindCarPresenter findCarPresenter;
    private int pages = 1;
    private int count = 10;
    private Handler handler = new Handler();
    private List<Circleinfo.ResultBean> list = null;
    //订阅管理器
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void initData() {
        findCarPresenter.CircleData(Api.CircleUrl, pages, count, disposable);

        scroll.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pages = 1;
                findCarPresenter.CircleData(Api.CircleUrl, pages, count, disposable);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scroll.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        recyclerView.addOnScrollListener(new OnLoadMoreListener() {
            @Override
            protected void onLoading(int countItem, int lastItem) {
                pages++;
                findCarPresenter.CircleData(Api.CircleUrl, pages, count, disposable);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scroll.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.cr_recycle);
        scroll = view.findViewById(R.id.cr_scro);
        //设置下拉时圆圈的颜色（可以尤多种颜色拼成）
        scroll.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light);
        //设置下拉时圆圈的背景颜色（这里设置成白色）
        scroll.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置样式
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        //实例p
        findCarPresenter = new FindCarPresenter();
        findCarPresenter.attachView(this);
    }

    @Override
    protected int getLatoutId() {
        return R.layout.circle_fragment;
    }

    @Override
    public void getFindCar(FindCarinfo findCarinfo) {

    }

    @Override
    public void CircleData(List<Circleinfo.ResultBean> circleList) {
        if (circleList != null) {
            if (pages == 1) {
                list = new ArrayList<>();
            }
            list.addAll(circleList);
            CircleAdapter circleAdapter = new CircleAdapter(getActivity(), list);
            recyclerView.setAdapter(circleAdapter);
            recyclerView.scrollToPosition(list.size() - circleList.size());
        }
    }

    @Override
    public void CreateOrder(CreatOrderinfo shZcarinfo) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
