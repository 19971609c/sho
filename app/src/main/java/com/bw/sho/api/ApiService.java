package com.bw.sho.api;

import com.bw.sho.bean.Discussinfo;
import com.bw.sho.bean.HomeBanner;
import com.bw.sho.bean.HomeShow;
import com.bw.sho.bean.Logininfo;
import com.bw.sho.bean.Registerinfo;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Auther: 不懂
 * @Date: 2019/3/16 17:41:34
 * @Description:
 */
public interface ApiService {
    //轮播图
    @GET("small/commodity/v1/bannerShow")
    Call<HomeBanner> getBanner();

    //首页数据
    @GET("small/commodity/v1/commodityList")
    Call<HomeShow> getHomeData();

    //商品详情数据
    @GET("small/commodity/v1/findCommodityDetailsById")
    Call<Discussinfo> getDiscussData(@Query("commodityId") int commodityId);

    //登录
    @FormUrlEncoded
    @POST("small/user/v1/login")
    Call<Logininfo> getLoginData(@FieldMap Map<String, String> map);

    //注册
    @FormUrlEncoded
    @POST("small/user/v1/register")
    Call<Registerinfo> getRegister(@FieldMap Map<String, String> map);
}
