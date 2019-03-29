package com.bw.sho.content;

import com.bw.sho.bean.Logininfo;
import com.bw.sho.bean.Registerinfo;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Auther: 不懂
 * @Date: 2019/3/16 16:59:48
 * @Description:
 */
public class LoginContach {
    //V层
    public interface LoginView {
        //V层返回到activity的方法
        //返回登录数据
        public void getLoginData(Logininfo body);

        //返回注册数据
        public void getregisterData(Registerinfo register);
    }

    //P层
    public interface LoginPresenter<LoginView> {
        //绑定
        public void attachView(LoginView loginView);

        //解绑
        public void delachView(LoginView loginView);

        //P层的请求数据方法
        //请求登录图
        public void getLoginData(String url, Map<String, String> map, CompositeDisposable disposable);

        //请求注册数据
        public void getregisterData(String url, Map<String, String> map,CompositeDisposable disposable);
    }

    //M层
    public interface LoginModel {
        //M层的请求数据的放发
        //请求登录图数据
        public void getLoginData(String url, Map<String, String> map,CompositeDisposable disposable, OnCallBack onCallBack);

        //返回数据方法
        public interface OnCallBack {
            //对应M的放回方法
            //返回登录图数据
            public void getLoginData(Logininfo body);
        }

        //请求注册数据
        public void getregisterData(String url, Map<String, String> map,CompositeDisposable disposable, OnBackregisterData onBackregisterData);

        public interface OnBackregisterData {
            //返回注册数据
            public void getregisterData(Registerinfo register);
        }

    }
}
