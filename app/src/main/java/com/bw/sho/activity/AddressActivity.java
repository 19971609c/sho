package com.bw.sho.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.bw.sho.R;
import com.bw.sho.base.BaseActivity;

public class AddressActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address;
    }

    @Override
    protected void initView() {
        Button newAddress = findViewById(R.id.add_button);
        newAddress.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button:
                startActivity(new Intent(AddressActivity.this, NewAddressActivity.class));
                break;
        }
    }
}
