package com.bw.sho.model;

import android.util.Log;

import com.bw.sho.api.ApiService;
import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.bean.WholeOrderinfo;
import com.bw.sho.content.OrderContach;
import com.bw.sho.utils.NetWorkManager;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @Auther: 不懂
 * @Date: 2019/3/28 15:35:38
 * @Description:
 */
public class Ordermodel implements OrderContach.OrderModel {
    //全部订单
    @Override
    public void getWholeOrder(String url, int userId, String sessionId, int status, int page, int count, CompositeDisposable disposable, final OnBackOrder onBackOrder) {
        Log.i("login",userId+"----"+sessionId+"----");
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<WholeOrderinfo> whole = apiService.Whole(userId, sessionId, status, page, count);
        DisposableSubscriber<WholeOrderinfo> disposableSubscriber = whole.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<WholeOrderinfo>() {
                    @Override
                    public void onNext(WholeOrderinfo wholeOrderinfo) {
                        List<WholeOrderinfo.OrderListBean> orderList = wholeOrderinfo.getOrderList();
                        Log.i("login",wholeOrderinfo.getMessage()+"000");
                        onBackOrder.getgetWholeOrder(orderList);
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

    //支付
    @Override
    public void getPayment(String url, int userId, String sessionId, String orderId, int payType, CompositeDisposable disposable, final OnBackOrder onBackOrder) {
        Log.i("ididi",userId+"");
        Log.i("ididi",sessionId);
        Log.i("ididi",orderId);
        Log.i("ididi",payType+"");
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<SHZcarinfo> payment = apiService.Payment(userId, sessionId, orderId, payType);
        DisposableSubscriber<SHZcarinfo> disposableSubscriber = payment.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<SHZcarinfo>() {
                    @Override
                    public void onNext(SHZcarinfo shZcarinfo) {
                        String message = shZcarinfo.getMessage();
                        Log.i("ididi",message);
                        onBackOrder.getPayment(shZcarinfo);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i("ididi","123");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposable.add(disposableSubscriber);

    }


}
