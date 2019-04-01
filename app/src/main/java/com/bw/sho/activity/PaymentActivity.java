package com.bw.sho.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
    private String price;

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
        price = intent.getStringExtra("price");
        RadioGroup group = findViewById(R.id.p_group);
        Button payment = findViewById(R.id.p_payment);
        payment.setText("支付金额:" + price + "元");
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
        } else {
            Toast.makeText(this, "请选择支付方式", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void getWholeOrder(List<WholeOrderinfo.OrderListBean> orderwhileList) {

    }

    @Override
    public void getPayment(SHZcarinfo shZcarinfo) {
        if (shZcarinfo.getStatus().equals("0000")) {
            showpopupWindow();
        } else {
            Toast.makeText(this, shZcarinfo.getMessage() + "请从新支付", Toast.LENGTH_SHORT).show();
        }

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

    private void showpopupWindow() {
        ImageView p_finsh;

        LayoutInflater layoutInflater = LayoutInflater.from(PaymentActivity.this);
        View view = layoutInflater.inflate(R.layout.popupwindofinsh, null);

        // final PopupWindow popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        final PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popupwindow_background));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);

        p_finsh = (ImageView) view.findViewById(R.id.p_finsh);
        p_finsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                popupWindow.dismiss();
            }
        });

        // PopupWindow弹出位置
        //popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        backgroundAlpha(0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    // 设置屏幕透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp);
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
