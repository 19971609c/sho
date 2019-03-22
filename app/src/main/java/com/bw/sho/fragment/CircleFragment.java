package com.bw.sho.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import com.bw.sho.R;
import com.bw.sho.base.BaseFragment;

/**
 * @Auther: 不懂
 * @Date: 2019/3/14 09:02:41
 * @Description:
 */
public class CircleFragment extends BaseFragment {

    private boolean isGetData = false;
    @Override
    protected void stopLoad() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.car_recycle);
    }

    @Override
    protected int getLatoutId() {
        return R.layout.circle_fragment;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            Log.d("wang","cir");
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
}
