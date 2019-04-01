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
import com.bw.sho.bean.Wallerinfo;
import com.bw.sho.content.AddressContach;
import com.bw.sho.presenter.AddressPresenter;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

public class NewAddressActivity extends BaseActivity implements View.OnClickListener, AddressContach.AddressView {

    private EditText add;
    private EditText address;
    private EditText phone;
    private EditText realName;
    private EditText zipCode;
    private AddressPresenter addressPresenter;
    private Map<String, String> map = new HashMap<>();
    private SharedPreferences status;
    //订阅者管理器
    CompositeDisposable disposable = new CompositeDisposable();
    private CityPickerView mPicker;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_address;
    }

    @Override
    protected void initView() {
        //三级联动申明对象
        mPicker = new CityPickerView();
        mPicker.init(this);
        //找控件
        add = findViewById(R.id.new_add);
        address = findViewById(R.id.new_address);
        phone = findViewById(R.id.new_phone);
        realName = findViewById(R.id.new_realName);
        zipCode = findViewById(R.id.new_zipCode);
        findViewById(R.id.new_check).setOnClickListener(this);
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
                    addressPresenter.getAddress(Api.NewAddressUrl, userId, sessionId, map, disposable);
                }
                break;
            case R.id.new_check:
                //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo
                CityConfig cityConfig = new CityConfig.Builder().build();
                mPicker.setConfig(cityConfig);
                //显示
                mPicker.showCityPicker();
                //监听选择点击事件及返回结果
                mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
                    @Override
                    public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                        address.setText(province.getName() + city.getName() + district.getName());
                    }

                    @Override
                    public void onCancel() {

                    }
                });
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
    public void walletList(Wallerinfo.ResultBean result) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addressPresenter.detachView(this);
        boolean disposed = disposable.isDisposed();
        if (!disposed) {
            //取消订阅
            disposable.clear();
            //解除订阅
            disposable.dispose();
        }
    }

}

