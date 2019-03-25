package com.bw.sho.activity;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.sho.R;
import com.bw.sho.api.Api;
import com.bw.sho.base.BaseActivity;
import com.bw.sho.bean.Addressinfo;
import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.content.AddressContach;
import com.bw.sho.presenter.AddressPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewAddressActivity extends BaseActivity implements View.OnClickListener, AddressContach.AddressView {

    private EditText add;
    private EditText address;
    private EditText phone;
    private EditText realName;
    private EditText zipCode;
    private AddressPresenter addressPresenter;
    private Map<String, String> map = new HashMap<>();
    private SharedPreferences status;

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
        //
        status = getSharedPreferences("status", MODE_PRIVATE);
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
                map.put("realName", newRealName);
                map.put("phone", newPhone);
                map.put("address", newAdd);
                map.put("zipCode", newZipCode);
                if (!TextUtils.isEmpty(newAdd) && !TextUtils.isEmpty(newAddress) && !TextUtils.isEmpty(newPhone) && !TextUtils.isEmpty(newRealName) && !TextUtils.isEmpty(newZipCode)) {
                    int userId = status.getInt("userId", 0);
                    String sessionId = status.getString("sessionId", null);
                    addressPresenter.getAddress(Api.NewAddressUrl, userId, sessionId, map);
                }
                break;
        }
    }

    //添加地址
    @Override
    public void getAddress(SHZcarinfo shZcarinfo) {
        if (shZcarinfo.getStatus().equals("0000")) {
            Toast.makeText(NewAddressActivity.this, shZcarinfo.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(NewAddressActivity.this, shZcarinfo.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void AddressList(List<Addressinfo.ResultBean> addressList) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addressPresenter.detachView(this);
    }

}

