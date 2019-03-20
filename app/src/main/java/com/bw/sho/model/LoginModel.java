package com.bw.sho.model;

import android.util.Log;
import com.bw.sho.api.ApiService;
import com.bw.sho.bean.Logininfo;
import com.bw.sho.bean.Registerinfo;
import com.bw.sho.content.LoginContach;
import com.bw.sho.utils.RetrofitUtils;

import java.util.Map;

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
        ApiService apiService = RetrofitUtils.getRetrofitUtils().getApiService(url, ApiService.class);
        Call<Logininfo> loginData = apiService.getLoginData(map);
        loginData.enqueue(new Callback<Logininfo>() {
            @Override
            public void onResponse(Call<Logininfo> call, Response<Logininfo> response) {
                Logininfo body = response.body();
                String status = body.getStatus();
                Log.i("login",status);
                onCallBack.getLoginData(body);
            }

            @Override
            public void onFailure(Call<Logininfo> call, Throwable t) {

            }
        });
    }

    //注册
    @Override
    public void getregisterData(String url, Map<String, String> map, final OnBackregisterData onBackregisterData) {
        ApiService apiService = RetrofitUtils.getRetrofitUtils().getApiService(url, ApiService.class);
        Call<Registerinfo> register = apiService.getRegister(map);
        register.enqueue(new Callback<Registerinfo>() {
            @Override
            public void onResponse(Call<Registerinfo> call, Response<Registerinfo> response) {
                Registerinfo register = response.body();
                onBackregisterData.getregisterData(register);
            }

            @Override
            public void onFailure(Call<Registerinfo> call, Throwable t) {

            }
        });
    }

}
