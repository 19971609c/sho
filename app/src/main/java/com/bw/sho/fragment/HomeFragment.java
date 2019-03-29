package com.bw.sho.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bw.sho.R;
import com.bw.sho.activity.DetailsActivity;
import com.bw.sho.activity.SearchActivity;
import com.bw.sho.adapter.HomeAdapter;
import com.bw.sho.api.Api;
import com.bw.sho.base.BaseFragment;
import com.bw.sho.bean.Displayinfo;
import com.bw.sho.bean.HomeBanner;
import com.bw.sho.bean.HomeShow;
import com.bw.sho.content.Contach;
import com.bw.sho.presenter.ContachPresenter;
import com.bw.sho.view.SearchBoxView;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Auther: 不懂
 * @Date: 2019/3/14 08:58:45
 * @Description:
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, Contach.ContachView {
    private SearchBoxView searchBox;
    private RecyclerView recycle;
    private ContachPresenter contachPresenter;
    private List<HomeBanner.ResultBean> result;
    private HomeBanner homeBanner;
    private ImageView common;
    //订阅管理器
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void initData() {
        //实例P层
        contachPresenter = new ContachPresenter();
        //绑定
        contachPresenter.attachView(this);
        //请求轮播数据
        contachPresenter.getBannerData(Api.HomeBannerUrl, disposable);
    }

    //找控件
    @Override
    protected void initView(View view) {
        searchBox = view.findViewById(R.id.home_search);
        recycle = view.findViewById(R.id.home_recycle);
        common = view.findViewById(R.id.home_common);
        //设置样式
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recycle.setLayoutManager(manager);
        common.setOnClickListener(this);

        //回调放法
        searchBox.setOnIntent(new SearchBoxView.OnIntent() {
            @Override
            public void onintent() {
                //跳转到搜索页面
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLatoutId() {
        return R.layout.home_fragment;
    }

    //得到轮播数据
    @Override
    public void getBannerData(HomeBanner body) {
        this.homeBanner = body;
        result = body.getResult();
        //请求首页数据
        contachPresenter.getHomeData(Api.HomeUrl, disposable);
    }

    //得到首页数据
    @Override
    public void getHomeData(HomeShow body) {
        HomeAdapter homeAdapter = new HomeAdapter(getActivity(), body, homeBanner);
        recycle.setAdapter(homeAdapter);
        //得到详情ID
        homeAdapter.getCallBackId(new HomeAdapter.CallBackId() {
            @Override
            public void getId(int id) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("commodityId", id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getDisplay(Displayinfo displayinfo) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_common:

                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑
        contachPresenter.delachView(this);
        boolean disposed = disposable.isDisposed();
        if (!disposed) {
            //消除订阅
            disposable.clear();
            //解除订阅
            disposable.dispose();
        }
    }
}
