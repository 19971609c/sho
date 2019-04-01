package com.bw.sho.model;

import android.util.Log;

import com.bw.sho.api.ApiService;
import com.bw.sho.bean.Addressinfo;
import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.bean.Wallerinfo;
import com.bw.sho.content.AddressContach;
import com.bw.sho.utils.NetWorkManager;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @Auther: 不懂
 * @Date: 2019/3/22 21:25:04
 * @Description:
 */
public class AddressModel implements AddressContach.AddressMdel {

    //添加地址
    @Override
    public void getAddress(String url, int userId, String sessionId, Map<String, String> map, CompositeDisposable disposable, final BackAddress backAddress) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<SHZcarinfo> address = apiService.getAddress(userId, sessionId, map);
        DisposableSubscriber<SHZcarinfo> disposableSubscriber = address.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<SHZcarinfo>() {
                    @Override
                    public void onNext(SHZcarinfo shZcarinfo) {
                        backAddress.getAddress(shZcarinfo);
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //把订阅者添加到订阅者管理器
        disposable.add(disposableSubscriber);
    }

    //地址列表
    @Override
    public void addressList(String url, int userId, String sessionId, CompositeDisposable disposable, final BackAddressList backAddressList) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<Addressinfo> address = apiService.Address(userId, sessionId);
        DisposableSubscriber<Addressinfo> disposableSubscriber = address.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<Addressinfo>() {
                    @Override
                    public void onNext(Addressinfo addressinfo) {
                        List<Addressinfo.ResultBean> addressList = addressinfo.getResult();
                        backAddressList.AddressList(addressList);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //把订阅者添加到订阅者管理器
        disposable.add(disposableSubscriber);
    }

    //钱包
    @Override
    public void walletList(String url, int userId, String sessionId, int page, int count, CompositeDisposable disposable, final BackWalletList backWalletList) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<Wallerinfo> waller = apiService.Waller(userId, sessionId, page, count);
        DisposableSubscriber<Wallerinfo> balance = waller.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<Wallerinfo>() {
                    @Override
                    public void onNext(Wallerinfo wallerinfo) {
                        Wallerinfo.ResultBean result = wallerinfo.getResult();
                        backWalletList.walletList(result);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposable.add(balance);
    }
}
