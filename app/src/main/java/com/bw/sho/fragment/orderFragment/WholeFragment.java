package com.bw.sho.fragment.orderFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.activity.ConfirmOrderActivity;
import com.bw.sho.activity.PaymentActivity;
import com.bw.sho.adapter.WholeOrderAdapter;
import com.bw.sho.api.Api;
import com.bw.sho.base.BaseFragment;
import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.bean.WholeOrderinfo;
import com.bw.sho.content.OrderContach;
import com.bw.sho.presenter.OrderPresenter;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Auther: 不懂
 * @Date: 2019/3/28 11:57:34
 * @Description:
 */
public class WholeFragment extends BaseFragment implements OrderContach.OrderView, View.OnClickListener {
    private boolean isGetData = false;
    private RecyclerView recyclerView;
    private CompositeDisposable disposable = new CompositeDisposable();
    private OrderPresenter orderPresenter;
    private int page = 1;


    @Override
    protected int getLatoutId() {
        return R.layout.whole_fragment;
    }

    @Override
    protected void initView(View view) {
        //登录状态
        SharedPreferences status = getActivity().getSharedPreferences("status", getActivity().MODE_PRIVATE);
        int userId = status.getInt("userId", 0);
        String sessionId = status.getString("sessionId", null);
        Log.i("login", userId + "----" + sessionId + "----");
        //找控件
        TextView show = view.findViewById(R.id.wh_show);
        recyclerView = view.findViewById(R.id.wh_recycle);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        show.setOnClickListener(this);

        orderPresenter = new OrderPresenter();
        orderPresenter.attachView(this);
    }

    @Override
    protected void initData() {
        SharedPreferences status = getActivity().getSharedPreferences("status", getActivity().MODE_PRIVATE);
        int userId = status.getInt("userId", 0);
        String sessionId = status.getString("sessionId", null);
        if (status.getBoolean("statusId", false)) {
            Log.i("login", userId + "----" + sessionId + "----");
            orderPresenter.getWholeOrder(Api.WholetUrl, userId, sessionId, 0, page, 5, disposable);
        }
    }

    @Override
    public void getWholeOrder(List<WholeOrderinfo.OrderListBean> orderwhileList) {
        WholeOrderAdapter wholeOrderAdapter = new WholeOrderAdapter(getActivity(), orderwhileList);
        recyclerView.setAdapter(wholeOrderAdapter);
        wholeOrderAdapter.setOnPayment(new WholeOrderAdapter.OnPayment() {
            @Override
            public void getId(String id, int price) {
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                intent.putExtra("orderid", id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getPayment(SHZcarinfo shZcarinfo) {

    }

    @Override
    public void onClick(View v) {
        SharedPreferences status = getActivity().getSharedPreferences("status", getActivity().MODE_PRIVATE);
        int userId = status.getInt("userId", 0);
        String sessionId = status.getString("sessionId", null);
        if (status.getBoolean("statusId", false)) {
            Log.i("login", userId + "----" + sessionId + "----");
            orderPresenter.getWholeOrder(Api.WholetUrl, userId, sessionId, 0, page, 5, disposable);
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            SharedPreferences status = getActivity().getSharedPreferences("status", getActivity().MODE_PRIVATE);
            int userId = status.getInt("userId", 0);
            String sessionId = status.getString("sessionId", null);
            if (status.getBoolean("statusId", false)) {
                Log.i("login", userId + "----" + sessionId + "----");
                orderPresenter.getWholeOrder(Api.WholetUrl, userId, sessionId, 0, page, 5, disposable);
            }
        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }


    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        orderPresenter.detachView(this);
        boolean disposed = disposable.isDisposed();
        if (!disposed) {
            disposable.clear();
            disposable.dispose();
        }
    }


}
