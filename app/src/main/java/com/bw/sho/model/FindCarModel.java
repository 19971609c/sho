package com.bw.sho.model;

import android.util.Log;

import com.bw.sho.api.ApiService;
import com.bw.sho.bean.FindCarinfo;
import com.bw.sho.content.FindCarContach;
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
}
