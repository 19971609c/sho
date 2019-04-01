package com.bw.sho.model;

import android.util.Log;

import com.bw.sho.api.ApiService;
import com.bw.sho.bean.OneListinfo;
import com.bw.sho.bean.ThreeListinfo;
import com.bw.sho.bean.TwoListinfo;
import com.bw.sho.content.ListContach;
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
public class ListModel implements ListContach.ListContachModel {

    @Override
    public void getOnelist(String url, CompositeDisposable disposable, final ListContach.OnCallBack onCallBack) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<OneListinfo> oneList = apiService.getOneList();
        DisposableSubscriber<OneListinfo> disposableSubscriber = oneList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<OneListinfo>() {
                    @Override
                    public void onNext(OneListinfo oneListinfo) {
                        List<OneListinfo.ResultBean> oneList = oneListinfo.getResult();
                        onCallBack.getOnelist(oneList);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposable.add(disposableSubscriber);

    }

    @Override
    public void getTwoList(String url, String firstCategoryId, CompositeDisposable disposable, final ListContach.OnCallBack onCallBack) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<TwoListinfo> twoList = apiService.getTwoList(firstCategoryId);
        twoList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<TwoListinfo>() {
                    @Override
                    public void onNext(TwoListinfo twoListinfo) {
                        String message = twoListinfo.getMessage();
                        Log.i("idididid0", message);
                        List<TwoListinfo.ResultBean> Tworesult = twoListinfo.getResult();
                        onCallBack.getTwoList(Tworesult);
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
    public void getThreeList(String url, String firstCategoryId, int page, int count, CompositeDisposable disposable, final ListContach.OnCallBack onCallBack) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<ThreeListinfo> threeList = apiService.getThreeList(firstCategoryId, page, count);
        threeList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<ThreeListinfo>() {
                    @Override
                    public void onNext(ThreeListinfo threeListinfo) {

                        List<ThreeListinfo.ResultBean> Threeresult = threeListinfo.getResult();
                        Log.i("three", Threeresult.size() + "");
                        onCallBack.getThreeList(Threeresult);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i("three-------", "失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
