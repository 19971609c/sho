package com.bw.sho.presenter;

import com.bw.sho.bean.Addressinfo;
import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.content.AddressContach;
import com.bw.sho.model.AddressModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Auther: 不懂
 * @Date: 2019/3/22 21:22:52
 * @Description:
 */
public class AddressPresenter implements AddressContach.AddressPresenter<AddressContach.AddressView> {
    private AddressContach.AddressView addressView;
    private Reference<AddressContach.AddressView> reference;
    private AddressModel addressModel;

    @Override
    public void attachView(AddressContach.AddressView addressView) {
        this.addressView = addressView;
        reference = new WeakReference<>(addressView);
        addressModel = new AddressModel();
    }

    @Override
    public void detachView(AddressContach.AddressView addressView) {
        reference.clear();
    }

    //添加地址

    @Override
    public void getAddress(String url, int userId, String sessionId, Map<String, String> map, CompositeDisposable disposable) {
        addressModel.getAddress(url, userId, sessionId, map, disposable, new AddressContach.AddressMdel.BackAddress() {
            @Override
            public void getAddress(SHZcarinfo shZcarinfo) {
                addressView.getAddress(shZcarinfo);
            }
        });
    }

    //地址列表
    @Override
    public void addressList(String url, int userId, String sessionId, CompositeDisposable disposable) {
        addressModel.addressList(url, userId, sessionId, disposable, new AddressContach.AddressMdel.BackAddressList() {
            @Override
            public void AddressList(List<Addressinfo.ResultBean> addressList) {
                addressView.AddressList(addressList);
            }
        });
    }
}
