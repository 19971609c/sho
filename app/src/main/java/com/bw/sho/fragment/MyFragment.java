package com.bw.sho.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.activity.AddressActivity;
import com.bw.sho.activity.LoginActivity;
import com.bw.sho.base.BaseFragment;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @Auther: 不懂
 * @Date: 2019/3/21 16:17:49
 * @Description:
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {

    private boolean isGetData = false;
    private SharedPreferences status;
    private SimpleDraweeView useimage;
    private TextView nkname;

    @Override
    protected int getLatoutId() {
        return R.layout.my_fragment;
    }

    @Override
    protected void initView(View view) {
        status = getActivity().getSharedPreferences("status", getActivity().MODE_PRIVATE);
        useimage = view.findViewById(R.id.my_image);
        nkname = view.findViewById(R.id.my_nikname);
        LinearLayout address = view.findViewById(R.id.my_address);

        useimage.setOnClickListener(this);
        address.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        useimage.setImageURI(status.getString("headPic", ""));
        nkname.setText(status.getString("nickName", "点击头像登录"));
    }

    @Override
    protected void stopLoad() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_image:
                if (!status.getBoolean("statusId", false)) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.my_address:
                if (status.getBoolean("statusId", false)) {
                    startActivity(new Intent(getActivity(), AddressActivity.class));
                }
                break;
        }
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            useimage.setImageURI(status.getString("headPic", ""));
            nkname.setText(status.getString("nickName", "点击头像登录"));
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
        useimage.setImageURI(status.getString("headPic", ""));
        nkname.setText(status.getString("nickName", "点击头像登录"));
    }

}
