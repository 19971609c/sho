package com.bw.sho.utils;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Auther: 不懂
 * @Date: 2019/3/20 14:20:46
 * @Description:
 */
public class NetWorkManager {

    private static NetWorkManager netWorkManager;

    public NetWorkManager() {
    }

    public static NetWorkManager getInstance() {
        if (netWorkManager == null) {
            synchronized (NetWorkManager.class) {
                if (netWorkManager == null) {
                    netWorkManager = new NetWorkManager();
                }
            }
        }
        return netWorkManager;
    }

    //初始化okhttp
    public OkHttpClient initOkHttp() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("xxx", message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        return okHttpClient;
    }

    //初始化必要对象和参数
    public Retrofit initRetrofit(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(NetWorkManager.getInstance().initOkHttp())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    //
    public <T> T initApiService(String url, Class<T> apiService) {
        Retrofit retrofit = initRetrofit(url);
        T t = retrofit.create(apiService);
        return t;
    }
}
