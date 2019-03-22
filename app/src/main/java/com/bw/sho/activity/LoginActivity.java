package com.bw.sho.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginContach.LoginView {

    private EditText log_phone;
    private EditText log_pwd;
    private CheckBox log_chenck;
    private SharedPreferences preferences;
    private LoginPresenter loginPresenter;
    private Map<String, String> map = new HashMap<>();
    private SharedPreferences login;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        preferences = getSharedPreferences("status", MODE_PRIVATE);
        login = getSharedPreferences("logins", MODE_PRIVATE);

        log_chenck = findViewById(R.id.log_chenck);
        log_phone = findViewById(R.id.log_phone);
        log_pwd = findViewById(R.id.log_pwd);
        TextView log_register = findViewById(R.id.log_register);
        Button log_login = findViewById(R.id.log_login);

        //实例p
        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);
        //判断是否记住密码
        if (login.getBoolean("login", false)) {
            log_chenck.setChecked(true);
            String phone1 = login.getString("phone", "");
            String pwd1 = login.getString("pwd", "");
            log_phone.setText(phone1);
            log_pwd.setText(pwd1);
        }

        //点击事件
        log_register.setOnClickListener(this);
        log_login.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_login:
                //获得输入的值
                String phone = log_phone.getText().toString();
                String pwd = log_pwd.getText().toString();
                map.put("phone", phone);
                map.put("pwd", pwd);
                //判断手机号
                boolean mobile = PhoneUtils.isMobile(phone);
                if (!mobile) {
                    Toast.makeText(LoginActivity.this, "手机格式错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pwd.length() < 2 || pwd.length() > 8) {
                    Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                //请求值
                SharedPreferences.Editor edit = login.edit();
                edit.putString("phone", phone);
                edit.putString("pwd", pwd);
                edit.commit();
                loginPresenter.getLoginData(Api.LoginUrl,map);
                break;
            case R.id.log_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    //登录
    @Override
    public void getLoginData(Logininfo body) {
        if (body.getStatus().equals("0000")) {
            Toast.makeText(LoginActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
            /*-----------------------------记住密码----------------------------------------*/
            SharedPreferences.Editor edit1 = login.edit();
            edit1.putBoolean("login", log_chenck.isChecked());
            edit1.commit();
            SharedPreferences.Editor edit = preferences.edit();
            //存登录成功后的数据
            edit.putBoolean("statusId",true);//登录成功
            edit.putString("headPic",body.getResult().getHeadPic());//头像地址
            edit.putString("nickName",body.getResult().getNickName());//昵称
            edit.putInt("userId", body.getResult().getUserId());//用户Id
            edit.putInt("sex", body.getResult().getSex());//性别
            edit.putString("sessionId", body.getResult().getSessionId());//登录凭证
            edit.commit();
            finish();
        }
        if (body.getStatus().equals("1001")) {
            Toast.makeText(LoginActivity.this, body.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void getregisterData(Registerinfo register) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.delachView(this);
    }
}
