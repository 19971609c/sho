package com.bw.sho.model;

import android.util.Log;

import com.bw.sho.api.ApiService;
import com.bw.sho.bean.Discussinfo;
import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.content.DiscussContract;
import com.bw.sho.utils.NetWorkManager;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @Auther: 不懂
 * @Date: 2019/3/19 10:57:46
 * @Description:
 */
public class DiscussModel implements DiscussContract.DiscussModel {

    //m请求数据

    //商品详情数据
    @Override
    public void getDiscussData(String url, int commodityId, final backDiscussData backDiscussData) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<Discussinfo> discussData = apiService.getDiscussData(commodityId);
        discussData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<Discussinfo>() {
                    @Override
                    public void onNext(Discussinfo discussinfo) {
                        Discussinfo.ResultBean result = discussinfo.getResult();
                        backDiscussData.getDiscussData(result);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //购物车
    @Override
    public void getCar(String url, Map<String, String> map, String json, backCarData backCarData) {

        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<SHZcarinfo> car = apiService.getCar(map, json);
        car.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SHZcarinfo>() {
                    @Override
                    public void accept(SHZcarinfo shZcarinfo) throws Exception {
                        Log.e("shZcarinfo", "accept: " + shZcarinfo.getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        String message = throwable.getMessage();
                        Log.i("sss", message);
                    }
                });
    }

}
