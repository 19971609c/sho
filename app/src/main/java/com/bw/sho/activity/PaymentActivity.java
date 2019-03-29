package com.bw.sho.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bw.sho.R;
import com.bw.sho.api.Api;
import com.bw.sho.base.BaseActivity;
import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.bean.WholeOrderinfo;
import com.bw.sho.content.OrderContach;
import com.bw.sho.presenter.OrderPresenter;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class PaymentActivity extends BaseActivity implements View.OnClickListener, OrderContach.OrderView, RadioGroup.OnCheckedChangeListener {

    private OrderPresenter orderPresenter;
    private int userId;
    private String sessionId;
    private String orderid;
    private int typid = 0;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initView() {

        SharedPreferences status = getSharedPreferences("status", MODE_PRIVATE);
        userId = status.getInt("userId", 0);
        sessionId = status.getString("sessionId", null);
        //orderid
        Intent intent = getIntent();
        orderid = intent.getStringExtra("orderid");
        RadioGroup group = findViewById(R.id.p_group);
        Button payment = findViewById(R.id.p_payment);
        payment.setOnClickListener(this);
        group.setOnCheckedChangeListener(this);
        orderPresenter = new OrderPresenter();
        orderPresenter.attachView(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        if (typid != 0) {
            orderPresenter.getPayment(Api.PaymentUrl, userId, sessionId, orderid, typid, disposable);
        }else {
            Toast.makeText(this, "请选择支付方式", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void getWholeOrder(List<WholeOrderinfo.OrderListBean> orderwhileList) {

    }

    @Override
    public void getPayment(SHZcarinfo shZcarinfo) {
        Toast.makeText(this, shZcarinfo.getMessage(), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.p_alipay:
                typid = 3;
                break;
            case R.id.p_wechat:
                typid = 2;
                break;
            case R.id.p_balance:
                typid = 1;
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        orderPresenter.detachView(this);
        boolean disposed = disposable.isDisposed();
        if (!disposed) {
            disposable.clear();
            disposable.dispose();
        }
    }


}
