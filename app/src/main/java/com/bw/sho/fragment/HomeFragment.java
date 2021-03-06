package com.bw.sho.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bw.sho.R;
import com.bw.sho.activity.DetailsActivity;
import com.bw.sho.activity.ListActivity;
import com.bw.sho.activity.SearchActivity;
import com.bw.sho.adapter.HomeAdapter;
import com.bw.sho.adapter.ListOneAdapter;
import com.bw.sho.adapter.ListTwoAdapter;
import com.bw.sho.api.Api;
import com.bw.sho.base.BaseFragment;
import com.bw.sho.bean.Displayinfo;
import com.bw.sho.bean.HomeBanner;
import com.bw.sho.bean.HomeShow;
import com.bw.sho.bean.OneListinfo;
import com.bw.sho.bean.ThreeListinfo;
import com.bw.sho.bean.TwoListinfo;
import com.bw.sho.content.Contach;
import com.bw.sho.content.ListContach;
import com.bw.sho.presenter.ContachPresenter;
import com.bw.sho.presenter.ListPresenter;
import com.bw.sho.view.SearchBoxView;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Auther: 不懂
 * @Date: 2019/3/14 08:58:45
 * @Description:
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, Contach.ContachView, ListContach.ListContachView {
    private SearchBoxView searchBox;
    private RecyclerView recycle;
    private ContachPresenter contachPresenter;
    private List<HomeBanner.ResultBean> result;
    private HomeBanner homeBanner;
    private ImageView common;
    //订阅管理器
    private  CompositeDisposable disposable = new CompositeDisposable();
    private ListPresenter listPresenter;
    private RecyclerView towre;

    @Override
    protected void initData() {
        //实例P层
        contachPresenter = new ContachPresenter();
        listPresenter = new ListPresenter();
        //绑定
        contachPresenter.attachView(this);
        listPresenter.attachView(this);
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
                //请求一级列表数据
                listPresenter.getOnelist(Api.OneListUrl, disposable);
                break;
        }
    }

    //一级列表数据
    @Override
    public void getOnelist(List<OneListinfo.ResultBean> oneList) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_onelist, null, false);
        //RecyclerView列表控件
        RecyclerView ontList = view.findViewById(R.id.one_list);
        towre = view.findViewById(R.id.two_list);
        //设置管理器
        ontList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //设置适配器
        ListOneAdapter listOneAdapter = new ListOneAdapter(getActivity(), oneList);
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

    //二级列表数据
    @Override
    public void getTwoList(List<TwoListinfo.ResultBean> tworesult) {
        //设置管理器
        towre.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //设置适配器
        ListTwoAdapter listTwoAdapter = new ListTwoAdapter(getActivity(), tworesult);
        towre.setAdapter(listTwoAdapter);
        listTwoAdapter.setOnTwoClick(new ListTwoAdapter.OnTwoClick() {
            @Override
            public void setIdData(String id) {
                //跳转到显示页面
                Intent intent = new Intent(getActivity(), ListActivity.class);
                //传值
                intent.putExtra("Mid", id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getThreeList(List<ThreeListinfo.ResultBean> threeresult) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑
        contachPresenter.delachView(this);
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
