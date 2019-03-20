package com.bw.sho.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Auther: 不懂
 * @Date: 2019/3/16 17:30:02
 * @Description:
 */
public class RetrofitUtils {
    //单利
    private static RetrofitUtils retrofitUtils;

    public RetrofitUtils() {
    }

    public static RetrofitUtils getRetrofitUtils() {
        if (retrofitUtils == null) {
            synchronized (RetrofitUtils.class) {
                if (retrofitUtils == null) {
                    retrofitUtils = new RetrofitUtils();
                }
            }
        }
        return retrofitUtils;
    }

    //Retrofit请求
    public Retrofit getRetrofit(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public <T> T getApiService(String url, Class<T> apiService) {
        Retrofit retrofit = getRetrofit(url);
        T t = retrofit.create(apiService);
        return t;
    }

}
