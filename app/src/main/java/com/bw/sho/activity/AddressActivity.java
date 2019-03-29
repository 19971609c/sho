package com.bw.sho.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bw.sho.R;
import com.bw.sho.adapter.AddressAdapter;
import com.bw.sho.api.Api;
import com.bw.sho.base.BaseActivity;
import com.bw.sho.bean.Addressinfo;
import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.content.AddressContach;
import com.bw.sho.presenter.AddressPresenter;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class AddressActivity extends BaseActivity implements View.OnClickListener, AddressContach.AddressView {

    private RecyclerView recycler;
    private AddressPresenter addressPresenter;
    private SharedPreferences status;
    private int userId;
    private String sessionId;
    //订阅者管理器
    CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    protected void initView() {
        //
        status = getSharedPreferences("status", MODE_PRIVATE);
        userId = status.getInt("userId", 0);
        sessionId = status.getString("sessionId", null);
        Button newAddress = findViewById(R.id.add_button);
        recycler = findViewById(R.id.add_recycler);
        //设置管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);
        newAddress.setOnClickListener(this);
        //关联P
        addressPresenter = new AddressPresenter();
        addressPresenter.attachView(this);
    }

    @Override
    protected void initData() {
        //获取数据
        addressPresenter.addressList(Api.AddressUrl, userId, sessionId, disposable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button:
                startActivity(new Intent(AddressActivity.this, NewAddressActivity.class));
                break;
        }
    }

    @Override
    public void getAddress(SHZcarinfo shZcarinfo) {

    }

    @Override
    public void AddressList(List<Addressinfo.ResultBean> addressList) {
        AddressAdapter addressAdapter = new AddressAdapter(this, addressList);
        recycler.setAdapter(addressAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        addressPresenter.addressList(Api.AddressUrl, userId, sessionId, disposable);
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
