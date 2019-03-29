package com.bw.sho.fragment;


import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.RadioGroup;

import com.bw.sho.R;
import com.bw.sho.base.BaseFragment;
import com.bw.sho.fragment.orderFragment.WholeFragment;

/**
 * @Auther: 不懂
 * @Date: 2019/3/21 16:17:49
 * @Description:
 */
public class OrderFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private WholeFragment wholeFragment;
    private FragmentTransaction transaction;
    private FragmentManager manager;

    @Override
    protected int getLatoutId() {
        return R.layout.order_fragment;
    }

    @Override
    protected void initView(View view) {
        SharedPreferences status = getActivity().getSharedPreferences("status", getActivity().MODE_PRIVATE);
        int userId = status.getInt("userId", 0);
        String sessionId = status.getString("sessionId", null);
        Log.i("login",userId+"----"+sessionId+"----");
        RadioGroup group = view.findViewById(R.id.or_group);
        group.setOnCheckedChangeListener(this);

    }

    @Override
    protected void initData() {
        manager = getChildFragmentManager();
        transaction = manager.beginTransaction();
        wholeFragment = new WholeFragment();
        transaction.add(R.id.or_frame, wholeFragment);
        transaction.show(wholeFragment);
        transaction.commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }


}
