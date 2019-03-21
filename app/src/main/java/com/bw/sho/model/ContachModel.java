package com.bw.sho.model;

import com.bw.sho.api.ApiService;
import com.bw.sho.bean.Displayinfo;
import com.bw.sho.bean.HomeBanner;
import com.bw.sho.bean.HomeShow;
import com.bw.sho.content.Contach;
import com.bw.sho.utils.NetWorkManager;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @Auther: 不懂
 * @Date: 2019/3/16 17:10:31
 * @Description:
 */
public class ContachModel implements Contach.ContachModel {

    //请求轮播图
    @Override
    public void getBannerData(String url, final OnCallBack onCallBack) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<HomeBanner> banner = apiService.getBanner();
        banner.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<HomeBanner>() {
                    @Override
                    public void onNext(HomeBanner result) {
                        onCallBack.backBannerData(result);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    //请求首页数据
    @Override
    public void getHomeData(String url, final OnBackHomeData onBackHomeData) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<HomeShow> homeData = apiService.getHomeData();
        homeData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<HomeShow>() {
                    @Override
                    public void onNext(HomeShow homeShow) {
                        onBackHomeData.backHomeData(homeShow);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //关键字数据
    @Override
    public void getDisplay(String url, String keyword, int page, int count, final OnBackDisplayData onBackDisplayData) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<Displayinfo> display = apiService.getDisplay(keyword, page, count);
        display.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<Displayinfo>() {
                    @Override
                    public void onNext(Displayinfo displayinfo) {
                        onBackDisplayData.backDisplayData(displayinfo);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
