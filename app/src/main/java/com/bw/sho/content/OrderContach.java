package com.bw.sho.content;

import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.bean.WholeOrderinfo;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Auther: 不懂
 * @Date: 2019/3/28 15:27:44
 * @Description:
 */
public class OrderContach {
    //v
    public interface OrderView {
        public void getWholeOrder(List<WholeOrderinfo.OrderListBean> orderwhileList);

        public void getPayment(SHZcarinfo shZcarinfo);
    }

    //p
    public interface OrderPresenter<OrderView> {
        public void attachView(OrderView orderView);

        public void detachView(OrderView orderView);

        public void getWholeOrder(String url, int userId, String sessionId, int status, int page, int count, CompositeDisposable disposable);

        public void getPayment(String url, int userId, String sessionId, String orderId, int payType, CompositeDisposable disposable);
    }

    public interface OrderModel {
        public void getWholeOrder(String url, int userId, String sessionId, int status, int page, int count, CompositeDisposable disposable, OnBackOrder onBackOrder);

        public void getPayment(String url, int userId, String sessionId, String orderId, int payType, CompositeDisposable disposable, OnBackOrder onBackOrder);

        public interface OnBackOrder {
            public void getgetWholeOrder(List<WholeOrderinfo.OrderListBean> orderwhileList);

            public void getPayment(SHZcarinfo shZcarinfo);
        }
    }
}
