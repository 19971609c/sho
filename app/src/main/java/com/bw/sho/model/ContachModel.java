package com.bw.sho.model;

import com.bw.sho.api.ApiService;
import com.bw.sho.bean.HomeBanner;
import com.bw.sho.bean.HomeShow;
import com.bw.sho.content.Contach;
import com.bw.sho.utils.RetrofitUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Auther: 不懂
 * @Date: 2019/3/16 17:10:31
 * @Description:
 */
public class ContachModel implements Contach.ContachModel {

    //请求轮播图
    @Override
    public void getBannerData(String url, final OnCallBack onCallBack) {
        ApiService apiService = RetrofitUtils.getRetrofitUtils().getApiService(url, ApiService.class);
        apiService.getBanner().enqueue(new Callback<HomeBanner>() {
            @Override
            public void onResponse(Call<HomeBanner> call, Response<HomeBanner> response) {
                HomeBanner body = response.body();
                onCallBack.backBannerData(body);
            }

            @Override
            public void onFailure(Call<HomeBanner> call, Throwable t) {
                String message = t.getMessage();
            }
        });
    }

    //请求首页数据
    @Override
    public void getHomeData(String url,final OnBackHomeData onBackHomeData) {
        ApiService apiService = RetrofitUtils.getRetrofitUtils().getApiService(url, ApiService.class);
        apiService.getHomeData().enqueue(new Callback<HomeShow>() {
            @Override
            public void onResponse(Call<HomeShow> call, Response<HomeShow> response) {
                HomeShow body = response.body();
                onBackHomeData.backHomeData(body);
            }

            @Override
            public void onFailure(Call<HomeShow> call, Throwable t) {

            }
        });
    }
}
