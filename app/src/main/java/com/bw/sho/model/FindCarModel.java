package com.bw.sho.model;

import android.util.Log;

import com.bw.sho.api.ApiService;
import com.bw.sho.bean.Circleinfo;
import com.bw.sho.bean.CreatOrderinfo;
import com.bw.sho.bean.FindCarinfo;
import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.content.FindCarContach;
import com.bw.sho.utils.NetWorkManager;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @Auther: 不懂
 * @Date: 2019/3/16 17:10:31
 * @Description:
 */
public class FindCarModel implements FindCarContach.CFindCarModel {

    @Override
    public void getFindCar(String url, int userId, String sessionId, CompositeDisposable disposable, final OnCallBack onCallBack) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<FindCarinfo> car = apiService.findCar(userId, sessionId);
        DisposableSubscriber<FindCarinfo> disposableSubscriber = car.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<FindCarinfo>() {
                    @Override
                    public void onNext(FindCarinfo findCarinfo) {
                        onCallBack.getFindCar(findCarinfo);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //把订阅者添加到订阅管理器
        disposable.add(disposableSubscriber);
    }

    @Override
    public void CircleData(String url, int page, int count, CompositeDisposable disposable, final OnCallBackCircle onCallBackCircle) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<Circleinfo> circle = apiService.Circle(page, count);
        DisposableSubscriber<Circleinfo> disposableSubscriber = circle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<Circleinfo>() {
                    @Override
                    public void onNext(Circleinfo circleinfo) {
                        List<Circleinfo.ResultBean> circleList = circleinfo.getResult();
                        onCallBackCircle.CircleData(circleList);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //把订阅者添加到订阅管理器
        disposable.add(disposableSubscriber);
    }

    @Override
    public void CreateOrder(String url, int userId, String sessionId, String orderInfo, double totalPrice, int addressId, CompositeDisposable disposable, final OnCallBackOrder onCallBackOrder) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<CreatOrderinfo> createOrderFlowable = apiService.CreateOrder(userId, sessionId, orderInfo, totalPrice, addressId);
        DisposableSubscriber<CreatOrderinfo> disposableSubscriber = createOrderFlowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<CreatOrderinfo>() {
                    @Override
                    public void onNext(CreatOrderinfo creatOrderinfo) {
                        String message = creatOrderinfo.getMessage();
                        onCallBackOrder.CreateOrder(creatOrderinfo);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //把订阅者添加到订阅管理器
        disposable.add(disposableSubscriber);
    }
}
