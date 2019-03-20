package com.bw.sho.model;

import com.bw.sho.api.ApiService;
import com.bw.sho.bean.Discussinfo;
import com.bw.sho.content.DiscussContract;
import com.bw.sho.utils.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        ApiService apiService = RetrofitUtils.getRetrofitUtils().getApiService(url, ApiService.class);
        apiService.getDiscussData(commodityId).enqueue(new Callback<Discussinfo>() {
            @Override
            public void onResponse(Call<Discussinfo> call, Response<Discussinfo> response) {
                Discussinfo body = response.body();
                Discussinfo.ResultBean result = body.getResult();
                backDiscussData.getDiscussData(result);
            }

            @Override
            public void onFailure(Call<Discussinfo> call, Throwable t) {

            }
        });
    }
}
