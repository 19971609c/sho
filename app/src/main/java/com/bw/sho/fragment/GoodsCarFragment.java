package com.bw.sho.fragment;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.adapter.FindCarAdapter;
import com.bw.sho.api.Api;
import com.bw.sho.app.MeApp;
import com.bw.sho.base.BaseFragment;
import com.bw.sho.bean.FindCarResclt;
import com.bw.sho.bean.FindCarinfo;
import com.bw.sho.content.FindCarContach;
import com.bw.sho.gen.FindCarRescltDao;
import com.bw.sho.presenter.FindCarPresenter;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/20 19:21:49
 * @Description:
 */
public class GoodsCarFragment extends BaseFragment implements View.OnClickListener {

    private boolean isGetData = false;
    private RecyclerView recyclerView;
    private SharedPreferences status;
    private SwipeRefreshLayout scroll;
    private Handler handler = new Handler();
    private CheckBox check;
    private FindCarAdapter findCarAdapter;
    private TextView money;
    private FindCarRescltDao findCarRescltDao;
    private List<FindCarResclt> findCarResclts;

    @Override
    protected int getLatoutId() {
        return R.layout.goodscar_fragment;

    }

    @Override
    protected void initView(View view) {
        //数据库
        findCarRescltDao = MeApp.getInstance().getDaoSession().getFindCarRescltDao();
        findCarRescltDao.deleteAll();
        status = getActivity().getSharedPreferences("status", getActivity().MODE_PRIVATE);
        recyclerView = view.findViewById(R.id.car_recycle);
        check = view.findViewById(R.id.car_check);
        money = view.findViewById(R.id.car_money);
        TextView sum = view.findViewById(R.id.car_sum);
        check.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        scroll = view.findViewById(R.id.car_scro);
        //设置下拉时圆圈的颜色（可以尤多种颜色拼成）
        scroll.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light);
        //设置下拉时圆圈的背景颜色（这里设置成白色）
        scroll.setProgressBackgroundColorSchemeResource(android.R.color.white);

        if (status.getBoolean("statusId", false)) {
            showCar();
        }
    }

    @Override
    protected void initData() {
        scroll.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showCar();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scroll.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void stopLoad() {

    }

    //点击加载数据
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.car_check:
                if (check.isChecked()) {
                    if (findCarResclts != null) {
                        findCarAdapter.isCheck(true);
                    }
                } else {
                    if (findCarResclts != null) {
                        findCarAdapter.isCheck(false);
                    }
                }
                break;
        }
    }

    private void showCar() {
        findCarResclts = findCarRescltDao.loadAll();
        findCarAdapter = new FindCarAdapter(getActivity(), findCarResclts);
        recyclerView.setAdapter(findCarAdapter);
        //设置
        findCarAdapter.setOncheckClick(new FindCarAdapter.OncheckClick() {
            @Override
            public void setBoolean(boolean c) {
                check.setChecked(c);
            }
        });
        //价格
        findCarAdapter.setOnMoneyClick(new FindCarAdapter.OnMoneyClick() {
            @Override
            public void getData(Integer moneys) {
                money.setText("$" + moneys + ".00");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //每次进入刷新
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            if (status.getBoolean("statusId", false)) {
                showCar();
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
    public void onResume() {
        super.onResume();
    }
}
