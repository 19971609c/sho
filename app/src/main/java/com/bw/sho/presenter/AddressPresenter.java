package com.bw.sho.presenter;

import com.bw.sho.content.AddressContach;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @Auther: 不懂
 * @Date: 2019/3/22 21:22:52
 * @Description:
 */
public class AddressPresenter implements AddressContach.AddressPresenter<AddressContach.AddressView> {
    private AddressContach.AddressView addressView;
    private Reference<AddressContach.AddressView> reference;

    @Override
    public void attachView(AddressContach.AddressView addressView) {
        this.addressView = addressView;
        reference = new WeakReference<>(addressView);
    }

    @Override
    public void detachView(AddressContach.AddressView addressView) {
        reference.clear();
    }
}
