package com.bw.sho.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.bw.sho.bean.OrderPagerinfo;
import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.bean.Wallerinfo;
import com.bw.sho.content.AddressContach;
import com.bw.sho.content.FindCarContach;
import com.bw.sho.presenter.AddressPresenter;
import com.bw.sho.presenter.FindCarPresenter;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private TextView num;
    private TextView rice;
    private RecyclerView shopping;
    private FindCarPresenter findCarPresenter1;
    private double price;
    private String json;
    private Integer integer = 471;
    private ConfirmAddressAdapter confirmAddressAdapter;
    private List<OrderPagerinfo> orderlist = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void initView() {
        //找控件
        address = findViewById(R.id.con_addressa);
        num = findViewById(R.id.con_num);
        rice = findViewById(R.id.con_price);
        Button tj = findViewById(R.id.con_tj);
        shopping = findViewById(R.id.con_list);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        shopping.setLayoutManager(linearLayoutManager1);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        address.setLayoutManager(linearLayoutManager);

        //获得信息
        status = getSharedPreferences("status", MODE_PRIVATE);
        userId = status.getInt("userId", 0);
        sessionId = status.getString("sessionId", null);
        //p
        addressPresenter = new AddressPresenter();
        addressPresenter.attachView(this);
        findCarPresenter1 = new FindCarPresenter();
        findCarPresenter1.attachView(this);
        //查询地址
        addressPresenter.addressList(Api.AddressUrl, userId, sessionId, disposable);

        //注册EventBus
        EventBus.getDefault().register(this);
        //循环取值
        List<CreateOrder> commodlist = new ArrayList<>();
        for (int i = 0; i < orderlist.size(); i++) {
            int commodityId = orderlist.get(i).getCommodityId();
            int count = orderlist.get(i).getCount();
            commodlist.add(new CreateOrder(commodityId, count));
        }
        Gson gson = new Gson();
        json = gson.toJson(commodlist);


        tj.setOnClickListener(this);
    }

    @Override
    public void AddressList(List<Addressinfo.ResultBean> addressList) {

        confirmAddressAdapter = new ConfirmAddressAdapter(this, addressList);
        address.setAdapter(confirmAddressAdapter);
        //返回地址Id

        confirmAddressAdapter.getCallBackConfirmId(new ConfirmAddressAdapter.CallBackConfirmId() {
            @Override
            public void getMid(int id) {
                Log.i("xxxx", "---id" + id);
                integer = id;
            }
        });

    }

    @Override
    protected void initData() {

        num.setText("共" + orderlist.size() + "件商品");
        ConfirmAdapter confirmAdapter = new ConfirmAdapter(this, orderlist);
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
                List<CreateOrder> commodlist = new ArrayList<>();
                for (int i = 0; i < orderlist.size(); i++) {
                    int commodityId = orderlist.get(i).getCommodityId();
                    int count = orderlist.get(i).getCount();
                    commodlist.add(new CreateOrder(commodityId, count));
                }
                Gson gson = new Gson();
                json = gson.toJson(commodlist);
            }
        });

    }

    @Override
    public void getAddress(SHZcarinfo shZcarinfo) {

    }


    @Override
    public void walletList(Wallerinfo.ResultBean result) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            intent.putExtra("price", price+"");
            startActivity(intent);
            finish();
        }
    }

    //eventbus接收到的集合
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getEventBus(List<OrderPagerinfo> list) {
        orderlist.clear();
        orderlist.addAll(list);
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
        //销毁EventBus
        EventBus.getDefault().unregister(this);
    }
}
