package com.bw.sho.content;

/**
 * @Auther: 不懂
 * @Date: 2019/3/22 21:18:01
 * @Description:
 */
public class AddressContach {
    //v
    public interface AddressView {

    }

    //p
    public interface AddressPresenter<AddressView> {
        public void attachView(AddressView addressView);

        public void detachView(AddressView addressView);
    }

    //m
    public interface AddressMdel {

    }
}
