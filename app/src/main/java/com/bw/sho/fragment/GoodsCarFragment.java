package com.bw.sho.fragment;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.adapter.FindCarAdapter;
import com.bw.sho.api.Api;
import com.bw.sho.base.BaseFragment;
import com.bw.sho.bean.FindCarinfo;
import com.bw.sho.content.FindCarContach;
import com.bw.sho.presenter.FindCarPresenter;

import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/20 19:21:49
 * @Description:
 */
public class GoodsCarFragment extends BaseFragment implements FindCarContach.FindCarView, View.OnClickListener {

    private RecyclerView recyclerView;
    private FindCarPresenter findCarPresenter;
    private SharedPreferences status;
    private SwipeRefreshLayout scroll;
    private Handler handler = new Handler();
    private TextView list;
    private CheckBox check;
    private FindCarAdapter findCarAdapter;
    private List<FindCarinfo.ResultBean> result;
    private TextView money;

    @Override
    protected int getLatoutId() {
        return R.layout.goodscar_fragment;

    }

    @Override
    protected void initView(View view) {
        status = getActivity().getSharedPreferences("status", getActivity().MODE_PRIVATE);
        recyclerView = view.findViewById(R.id.car_recycle);
        list = view.findViewById(R.id.car_list);
        check = view.findViewById(R.id.car_check);
        money = view.findViewById(R.id.car_money);
        TextView sum = view.findViewById(R.id.car_sum);


        list.setOnClickListener(this);
        check.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        scroll = view.findViewById(R.id.car_scro);
        //设置下拉时圆圈的颜色（可以尤多种颜色拼成）
        scroll.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light);
        //设置下拉时圆圈的背景颜色（这里设置成白色）
        scroll.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //设置样式
        findCarPresenter = new FindCarPresenter();
        findCarPresenter.attachView(this);
    }

    @Override
    protected void initData() {
        getFindcar();
        scroll.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFindcar();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scroll.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    private void getFindcar() {
        if (status.getBoolean("statusId", false)) {
            int userId = status.getInt("userId", 0);
            String sessionId = status.getString("sessionId", null);
            findCarPresenter.getFindCar(Api.findCarUrl, userId, sessionId);
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void stopLoad() {

    }

    @Override
    public void getFindCar(FindCarinfo findCarinfo) {
        list.setVisibility(View.GONE);
        result = findCarinfo.getResult();
        findCarAdapter = new FindCarAdapter(getActivity(), result);
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
                money.setText(moneys+"");
            }
        });
    }

    //点击加载数据
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.car_list:
                getFindcar();
                break;
            case R.id.car_check:
                if (check.isChecked()) {
                    if (result != null) {
                        findCarAdapter.isCheck(true);
                }
                } else {
                    if (result != null) {
                        findCarAdapter.isCheck(false);
                    }
                }
                break;
        }
    }
}
