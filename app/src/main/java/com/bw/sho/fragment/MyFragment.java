package com.bw.sho.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;

import com.bw.sho.R;
import com.bw.sho.activity.LoginActivity;
import com.bw.sho.base.BaseFragment;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @Auther: 不懂
 * @Date: 2019/3/21 16:17:49
 * @Description:
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {


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
        useimage.setOnClickListener(this);
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
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                useimage.setImageURI(status.getString("headPic", ""));
                nkname.setText(status.getString("nickName", "点击头像登录"));

                break;
        }
    }
}
