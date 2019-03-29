package com.bw.sho.presenter;

import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.bean.WholeOrderinfo;
import com.bw.sho.content.OrderContach;
import com.bw.sho.model.Ordermodel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Auther: 不懂
 * @Date: 2019/3/28 15:33:27
 * @Description:
 */
public class OrderPresenter implements OrderContach.OrderPresenter<OrderContach.OrderView> {

    private OrderContach.OrderView orderView;
    private Reference<OrderContach.OrderView> reference;
    private Ordermodel ordermodel;

    @Override
    public void attachView(OrderContach.OrderView orderView) {
        this.orderView = orderView;
        reference = new WeakReference<>(orderView);
        ordermodel = new Ordermodel();
    }

    @Override
    public void detachView(OrderContach.OrderView orderView) {
        reference.clear();
    }

    @Override
    public void getWholeOrder(String url, int userId, String sessionId, int status, int page, int count, CompositeDisposable disposable) {
        ordermodel.getWholeOrder(url, userId, sessionId, status, page, count, disposable, new OrderContach.OrderModel.OnBackOrder() {
            @Override
            public void getgetWholeOrder(List<WholeOrderinfo.OrderListBean> orderwhileList) {
                orderView.getWholeOrder(orderwhileList);
            }

            @Override
            public void getPayment(SHZcarinfo shZcarinfo) {

            }
        });
    }

    @Override
    public void getPayment(String url, int userId, String sessionId, String orderId, int payType, CompositeDisposable disposable) {
        ordermodel.getPayment(url, userId, sessionId, orderId, payType, disposable, new OrderContach.OrderModel.OnBackOrder() {
            @Override
            public void getgetWholeOrder(List<WholeOrderinfo.OrderListBean> orderwhileList) {

            }

            @Override
            public void getPayment(SHZcarinfo shZcarinfo) {
                orderView.getPayment(shZcarinfo);
            }
        });
    }

}
