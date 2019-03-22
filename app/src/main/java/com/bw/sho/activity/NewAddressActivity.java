package com.bw.sho.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.bw.sho.R;
import com.bw.sho.base.BaseActivity;
import com.bw.sho.content.AddressContach;
import com.bw.sho.presenter.AddressPresenter;

import java.util.HashMap;
import java.util.Map;

public class NewAddressActivity extends BaseActivity implements View.OnClickListener, AddressContach.AddressView {

    private EditText add;
    private EditText address;
    private EditText phone;
    private EditText realName;
    private EditText zipCode;
    private AddressPresenter addressPresenter;
    private Map<String, String> map = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_address;
    }

    @Override
    protected void initView() {
        add = findViewById(R.id.new_add);
        address = findViewById(R.id.new_address);
        phone = findViewById(R.id.new_phone);
        realName = findViewById(R.id.new_realName);
        zipCode = findViewById(R.id.new_zipCode);
        findViewById(R.id.new_but).setOnClickListener(this);
        //实例p
        addressPresenter = new AddressPresenter();
        addressPresenter.attachView(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_but:
                String newAdd = add.getText().toString();
                String newAddress = address.getText().toString();
                String newPhone = phone.getText().toString();
                String newRealName = realName.getText().toString();
                String newZipCode = zipCode.getText().toString();
                map.put("realName",newRealName);
                map.put("phone",newPhone);
                map.put("address",newAddress);
                map.put("zipCode",newZipCode);
                if (TextUtils.isEmpty(newAdd) && TextUtils.isEmpty(newAddress) && TextUtils.isEmpty(newPhone) && TextUtils.isEmpty(newRealName) && TextUtils.isEmpty(newZipCode)) {
                    //添加地址
                   // addressPresenter.getAddress();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addressPresenter.detachView(this);
    }
}

