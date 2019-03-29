package com.bw.sho.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.sho.R;
import com.bw.sho.adapter.ConfirmAdapter;
import com.bw.sho.adapter.ConfirmAddressAdapter;
import com.bw.sho.api.Api;
import com.bw.sho.base.BaseActivity;
import com.bw.sho.bean.Addressinfo;
import com.bw.sho.bean.Circleinfo;
import com.bw.sho.bean.CreatOrderinfo;
import com.bw.sho.bean.CreateOrder;
import com.bw.sho.bean.Discussinfo;
import com.bw.sho.bean.FindCarinfo;
import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.content.AddressContach;
import com.bw.sho.content.FindCarContach;
import com.bw.sho.presenter.AddressPresenter;
import com.bw.sho.presenter.FindCarPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class ConfirmOrderActivity extends BaseActivity implements AddressContach.AddressView, View.OnClickListener, FindCarContach.FindCarView {

    private AddressPresenter addressPresenter;
    private SharedPreferences status;
    private int userId;
    private String sessionId;
    private CompositeDisposable disposable = new CompositeDisposable();
    private RecyclerView address;
    private List<Discussinfo.ResultBean> list;
    private TextView text;
    private TextView num;
    private TextView rice;
    private RecyclerView shopping;
    private FindCarPresenter findCarPresenter;
    private FindCarPresenter findCarPresenter1;
    private double price;
    private String json;
    private Integer integer = 0;
    private ConfirmAddressAdapter confirmAddressAdapter;
    private int commodityId;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void initView() {
        //获得信息
        status = getSharedPreferences("status", MODE_PRIVATE);
        userId = status.getInt("userId", 0);
        sessionId = status.getString("sessionId", null);
        //获得商品信息
        Intent intent = getIntent();
        Discussinfo.ResultBean resultBean = (Discussinfo.ResultBean) intent.getSerializableExtra("result");
        commodityId = resultBean.getCommodityId();
        //添加到一个集合
        list = new ArrayList<>();
        list.add(resultBean);
        //添加订单
        List<CreateOrder> orderlist = new ArrayList<>();
        orderlist.add(new CreateOrder(commodityId, 1));
        Gson gson = new Gson();
        json = gson.toJson(orderlist);
        //找控件
        text = findViewById(R.id.con_text);
        address = findViewById(R.id.con_address);
        num = findViewById(R.id.con_num);
        rice = findViewById(R.id.con_price);
        Button tj = findViewById(R.id.con_tj);
        shopping = findViewById(R.id.con_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        address.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        shopping.setLayoutManager(linearLayoutManager1);

        text.setOnClickListener(this);
        tj.setOnClickListener(this);

        addressPresenter = new AddressPresenter();
        addressPresenter.attachView(this);
        findCarPresenter1 = new FindCarPresenter();
        findCarPresenter1.attachView(this);

        //查询地址
        addressPresenter.addressList(Api.AddressUrl, userId, sessionId, disposable);

    }

    @Override
    protected void initData() {
        num.setText("共" + list.size() + "件商品");
        ConfirmAdapter confirmAdapter = new ConfirmAdapter(this, list);
        shopping.setAdapter(confirmAdapter);
        //价格
        confirmAdapter.setOnMoneyClick(new ConfirmAdapter.OnMoneyClick() {
            @Override
            public void getData(Integer money) {
                price = money;
                rice.setText("需支付" + money + ".00元");
            }
        });
        //数量
        confirmAdapter.setOnNumClick(new ConfirmAdapter.OnNumClick() {
            @Override
            public void getData(int num) {
                //添加订单
                List<CreateOrder> orderlist = new ArrayList<>();
                orderlist.add(new CreateOrder(commodityId, num));
                Gson gson = new Gson();
                json = gson.toJson(orderlist);
            }
        });

    }

    @Override
    public void getAddress(SHZcarinfo shZcarinfo) {

    }

    @Override
    public void AddressList(List<Addressinfo.ResultBean> addressList) {

        confirmAddressAdapter = new ConfirmAddressAdapter(this, addressList);
        address.setAdapter(confirmAddressAdapter);
        //返回地址Id
        confirmAddressAdapter.getCallBackConfirmId(new ConfirmAddressAdapter.CallBackConfirmId() {

            @Override
            public void getId(String id) {
                integer = Integer.valueOf(id);
                address.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.con_text:
                address.setVisibility(View.VISIBLE);
                break;
            case R.id.con_tj:
                if (integer != 0) {
                    findCarPresenter1.CreateOrder(Api.CreateUrl, userId, sessionId, json, price, integer, disposable);
                    return;
                }
                Toast.makeText(ConfirmOrderActivity.this, "请选择收货地址", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void getFindCar(FindCarinfo findCarinfo) {

    }

    @Override
    public void CircleData(List<Circleinfo.ResultBean> circleList) {

    }

    @Override
    public void CreateOrder(CreatOrderinfo shZcarinfo) {
        String status = shZcarinfo.getStatus();
        if (status.equals("0000")) {
            Toast.makeText(ConfirmOrderActivity.this, shZcarinfo.getMessage(), Toast.LENGTH_SHORT).show();
           /* Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Fragmentid", 1);
            startActivity(intent);*/
            String orderId = shZcarinfo.getOrderId();
            Intent intent = new Intent(ConfirmOrderActivity.this, PaymentActivity.class);
            intent.putExtra("orderid", orderId);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addressPresenter.detachView(this);
        findCarPresenter1.delachView(this);
        boolean disposed = disposable.isDisposed();
        if (!disposed) {
            disposable.clear();
            disposable.dispose();
        }
    }
}
