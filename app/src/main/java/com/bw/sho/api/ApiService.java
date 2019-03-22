package com.bw.sho.api;

import com.bw.sho.bean.Discussinfo;
import com.bw.sho.bean.Displayinfo;
import com.bw.sho.bean.FindCarinfo;
import com.bw.sho.bean.HomeBanner;
import com.bw.sho.bean.HomeShow;
import com.bw.sho.bean.Logininfo;
import com.bw.sho.bean.Registerinfo;
import com.bw.sho.bean.SHZcarinfo;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * @Auther: 不懂
 * @Date: 2019/3/16 17:41:34
 * @Description:
 */
public interface ApiService {
    //轮播图
    @GET("small/commodity/v1/bannerShow")
    Flowable<HomeBanner> getBanner();

    //首页数据
    @GET("small/commodity/v1/commodityList")
    Flowable<HomeShow> getHomeData();

    //商品详情数据
    @GET("small/commodity/v1/findCommodityDetailsById")
    Flowable<Discussinfo> getDiscussData(@Query("commodityId") int commodityId);

    //登录
    @FormUrlEncoded
    @POST("small/user/v1/login")
    Flowable<Logininfo> getLoginData(@FieldMap Map<String, String> map);

    //注册
    @FormUrlEncoded
    @POST("small/user/v1/register")
    Flowable<Registerinfo> getRegister(@FieldMap Map<String, String> map);

    //关键词
    @GET("small/commodity/v1/findCommodityByKeyword")
    Flowable<Displayinfo> getDisplay(@Query("keyword") String keyword, @Query("page") int page, @Query("count") int count);

    //购物车
    @PUT("small/order/verify/v1/syncShoppingCart")
    Call<ResponseBody> getCar(@Header("userId") int userId, @Header("sessionId") String sessionId, @Query("data") String data);

    //查询购物车
    @GET("small/order/verify/v1/findShoppingCart")
    Flowable<FindCarinfo> findCar(@Header("userId") int userId, @Header("sessionId") String sessionId);

    //添加地址
    @FormUrlEncoded
    @POST("small/user/v1/register")
    Flowable<SHZcarinfo> getAddress(@Header("userId") int userId,@Header("sessionId") String sessionId,@FieldMap Map<String, String> map);
}
