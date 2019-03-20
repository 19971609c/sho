package com.bw.sho.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.bw.sho.R;
import com.bw.sho.api.Api;
import com.bw.sho.base.BaseActivity;
import com.bw.sho.bean.Logininfo;
import com.bw.sho.bean.Registerinfo;
import com.bw.sho.content.LoginContach;
import com.bw.sho.presenter.LoginPresenter;
import com.bw.sho.utils.PhoneUtils;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, LoginContach.LoginView {

    private EditText re_phone;
    private EditText re_pwd;
    private Map<String, String> map = new HashMap<>();
    private LoginPresenter loginPresenter;

    @Override
    protected void initData() {
        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);
    }

    @Override
    protected void initView() {
        re_phone = findViewById(R.id.re_phone);
        re_pwd = findViewById(R.id.re_pwd);
        Button re_register = findViewById(R.id.re_register);
        re_register.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_register:
                //获取值
                String phone = re_phone.getText().toString();
                String pwd = re_pwd.getText().toString();
                //判断手机号格式
                boolean mobile = PhoneUtils.isMobile(phone);
                if (!mobile) {
                    Toast.makeText(RegisterActivity.this, "手机格式错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pwd.length() < 6 || pwd.length() > 8) {
                    Toast.makeText(RegisterActivity.this, "密码必须六到八位", Toast.LENGTH_SHORT).show();
                    return;
                }
                //定义Map集合存账号密码
                map.put("phone", phone);
                map.put("pwd", pwd);
                //调用Presenter方法
                loginPresenter.getregisterData(Api.RegisterUrl, map);
                break;
        }
    }

    @Override
    public void getLoginData(Logininfo body) {

    }

    //注册
    @Override
    public void getregisterData(Registerinfo register) {
        if (register.getStatus().equals("0000")) {
            //成功
            Toast.makeText(RegisterActivity.this, register.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (register.getStatus().equals("1001")) {
            //失败
            Toast.makeText(RegisterActivity.this, register.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.delachView(this);
    }
}
