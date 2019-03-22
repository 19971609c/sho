package com.bw.sho.model;

import android.util.Log;

import com.bw.sho.api.Api;
import com.bw.sho.api.ApiService;
import com.bw.sho.bean.Discussinfo;
import com.bw.sho.content.DiscussContract;
import com.bw.sho.utils.NetWorkManager;

import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Auther: 不懂
 * @Date: 2019/3/19 10:57:46
 * @Description:
 */
public class DiscussModel implements DiscussContract.DiscussModel {
    private JSONObject jsonObject;

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
    public void getCar(String carUrl, final int userId, final String sessionId, String data, backCarData backCarData) {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(Api.CarUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = build.create(ApiService.class);
        Call<ResponseBody> car = apiService.getCar(userId, sessionId, data);
        car.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    Log.d("xxx", string + "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

}
