package com.bw.sho.content;

import com.bw.sho.bean.Circleinfo;
import com.bw.sho.bean.CreatOrderinfo;
import com.bw.sho.bean.FindCarinfo;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Auther: 不懂
 * @Date: 2019/3/21 10:32:09
 * @Description:
 */
public class FindCarContach {
    //V层
    public interface FindCarView {
        //V层返回到activity的方法
        public void getFindCar(FindCarinfo findCarinfo);

        public void CircleData(List<Circleinfo.ResultBean> circleList);

        public void CreateOrder(CreatOrderinfo shZcarinfo);
    }

    //P层
    public interface FindCarPresenter<FindCarView> {
        //绑定
        public void attachView(FindCarView findCarView);

        //解绑
        public void delachView(FindCarView findCarView);

        //P层的请求数据方法
        public void getFindCar(String url, int userId, String sessionId, CompositeDisposable disposable);

        public void CircleData(String url, int page, int count, CompositeDisposable disposable);

        public void CreateOrder(String url, int userId, String sessionId, String orderInfo, double totalPrice, int addressId, CompositeDisposable disposable);
    }

    //M层
    public interface CFindCarModel {
        //M层的请求数据的放发
        public void getFindCar(String url, int userId, String sessionId, CompositeDisposable disposable, OnCallBack onCallBack);

        //返回数据方法
        public interface OnCallBack {
            //对应M的放回方法
            public void getFindCar(FindCarinfo findCarinfo);
        }

        public void CircleData(String url, int page, int count, CompositeDisposable disposable, OnCallBackCircle onCallBackCircle);

        public interface OnCallBackCircle {
            //对应M的放回方法
            public void CircleData(List<Circleinfo.ResultBean> circleList);
        }

        public void CreateOrder(String url, int userId, String sessionId, String orderInfo, double totalPrice, int addressId, CompositeDisposable disposable, OnCallBackOrder onCallBackOrder);

        public interface OnCallBackOrder {
            //对应M的放回方法
            public void CreateOrder(CreatOrderinfo shZcarinfo);
        }
    }
}
