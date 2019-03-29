package com.bw.sho.presenter;

import com.bw.sho.bean.Logininfo;
import com.bw.sho.bean.Registerinfo;
import com.bw.sho.content.LoginContach;
import com.bw.sho.model.LoginModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Auther: 不懂
 * @Date: 2019/3/16 17:08:56
 * @Description:
 */
public class LoginPresenter implements LoginContach.LoginPresenter<LoginContach.LoginView> {

    //软应用
    private Reference<LoginContach.LoginView> reference;
    private LoginContach.LoginView loginView;
    private LoginModel loginModel;


    @Override
    public void attachView(LoginContach.LoginView loginView) {
        this.loginView = loginView;
        reference = new WeakReference<>(loginView);
        loginModel = new LoginModel();
    }

    @Override
    public void delachView(LoginContach.LoginView loginView) {
        reference.clear();
    }

    //登录
    @Override
    public void getLoginData(String url, Map<String, String> map, CompositeDisposable disposable) {
        loginModel.getLoginData(url, map, disposable, new LoginContach.LoginModel.OnCallBack() {
            @Override
            public void getLoginData(Logininfo body) {
                loginView.getLoginData(body);
            }
        });
    }

    //注册
    @Override
    public void getregisterData(String url, Map<String, String> map, CompositeDisposable disposable) {
        loginModel.getregisterData(url, map, disposable, new LoginContach.LoginModel.OnBackregisterData() {
            @Override
            public void getregisterData(Registerinfo register) {
                loginView.getregisterData(register);
            }
        });
    }

}
