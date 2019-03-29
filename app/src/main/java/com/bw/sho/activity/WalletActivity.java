package com.bw.sho.activity;

import com.bw.sho.R;
import com.bw.sho.base.BaseActivity;
import com.bw.sho.bean.Logininfo;
import com.bw.sho.bean.Registerinfo;
import com.bw.sho.content.LoginContach;

public class WalletActivity extends BaseActivity implements LoginContach.LoginView {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void getLoginData(Logininfo body) {

    }

    @Override
    public void getregisterData(Registerinfo register) {

    }
}
