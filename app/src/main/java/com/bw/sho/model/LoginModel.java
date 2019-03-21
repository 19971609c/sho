package com.bw.sho.model;

import android.util.Log;
import com.bw.sho.api.ApiService;
import com.bw.sho.bean.Logininfo;
import com.bw.sho.bean.Registerinfo;
import com.bw.sho.content.LoginContach;
import com.bw.sho.utils.NetWorkManager;
import com.bw.sho.utils.RetrofitUtils;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Auther: 不懂
 * @Date: 2019/3/19 10:57:46
 * @Description:
 */
public class LoginModel implements LoginContach.LoginModel {

    //m请求数据
    //登录
    @Override
    public void getLoginData(String url, Map<String, String> map, final OnCallBack onCallBack) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        Flowable<Logininfo> loginData = apiService.getLoginData(map);
        loginData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<Logininfo>() {
                    @Override
                    public void onNext(Logininfo logininfo) {
                        onCallBack.getLoginData(logininfo);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //注册
    @Override
    public void getregisterData(String url, Map<String, String> map, final OnBackregisterData onBackregisterData) {
        ApiService apiService = NetWorkManager.getInstance().initApiService(url, ApiService.class);
        apiService.getRegister(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<Registerinfo>() {
                    @Override
                    public void onNext(Registerinfo registerinfo) {
                        onBackregisterData.getregisterData(registerinfo);
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
