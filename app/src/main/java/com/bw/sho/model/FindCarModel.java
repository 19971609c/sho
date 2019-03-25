package com.bw.sho.model;

import android.util.Log;

import com.bw.sho.api.ApiService;
import com.bw.sho.bean.Circleinfo;
import com.bw.sho.bean.CreateOrder;
import com.bw.sho.bean.FindCarinfo;
import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.content.FindCarContach;
import com.bw.sho.utils.NetWorkManager;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @Auther: 不懂
 * @Date: 2019/3/16 17:10:31
 * @Description:
 */
public class FindCarModel implements FindCarContach.CFindCarModel {

    @Override
    public void getFindCar(String url, int userId, String sessionId, final OnCallBack onCallBack) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<FindCarinfo> car = apiService.findCar(userId, sessionId);
        car.subscribeOn(Schedulers.io())
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
    }

    @Override
    public void CircleData(String url, int page, int count, final OnCallBackCircle onCallBackCircle) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<Circleinfo> circle = apiService.Circle(page, count);
        circle.subscribeOn(Schedulers.io())
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
    }

    @Override
    public void CreateOrder(String url, int userId, String sessionId, String orderInfo, double totalPrice, int addressId, OnCallBackOrder onCallBackOrder) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<SHZcarinfo> createOrderFlowable = apiService.CreateOrder(userId, sessionId, orderInfo, totalPrice, addressId);
        createOrderFlowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<SHZcarinfo>() {
                    @Override
                    public void onNext(SHZcarinfo shZcarinfo) {
                        String message = shZcarinfo.getMessage();
                        Log.i("qwe",message);
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
