package com.bw.sho.content;

import com.bw.sho.bean.Addressinfo;
import com.bw.sho.bean.SHZcarinfo;

import java.util.List;
import java.util.Map;

/**
 * @Auther: 不懂
 * @Date: 2019/3/22 21:18:01
 * @Description:
 */
public class AddressContach {
    //v
    public interface AddressView {
        public void getAddress(SHZcarinfo shZcarinfo);

        public void AddressList(List<Addressinfo.ResultBean> addressList);
    }

    //p
    public interface AddressPresenter<AddressView> {
        public void attachView(AddressView addressView);

        public void detachView(AddressView addressView);

        //添加地址
        public void getAddress(String url, int userId, String sessionId, Map<String, String> map);

        //地址列表
        public void addressList(String url, int userId, String sessionId);
    }

    //m
    public interface AddressMdel {
        //添加地址
        public void getAddress(String url, int userId, String sessionId, Map<String, String> map, BackAddress backAddress);

        public interface BackAddress {
            public void getAddress(SHZcarinfo shZcarinfo);
        }

        //地址列表
        public void addressList(String url, int userId, String sessionId, BackAddressList backAddressList);

        public interface BackAddressList {
            public void AddressList(List<Addressinfo.ResultBean> addressList);
        }
    }
}
