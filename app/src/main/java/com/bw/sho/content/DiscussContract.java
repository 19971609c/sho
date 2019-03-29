package com.bw.sho.content;

import com.bw.sho.bean.Discussinfo;
import com.bw.sho.bean.SHZcarinfo;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Auther: 不懂
 * @Date: 2019/3/19 10:15:25
 * @Description:
 */
public class DiscussContract {
    //v
    public interface DiscussView {
        //返回数据
        //详情
        public void getDiscussData(Discussinfo.ResultBean result);

        //加入购物车
        public void getCar(SHZcarinfo shZcarinfo);
    }

    //p
    public interface DiscussPresenter<DiscussView> {
        //绑定
        public void attachView(DiscussView discussView);

        //解绑
        public void detachView(DiscussView discussView);

        //p请求数据
        //详情
        public void getDiscussData(String url, int commodityId, CompositeDisposable disposable);

        //加入购物车
        public void getCar(String carUrl, int userId, String sessionId, String data, CompositeDisposable disposable);
    }

    public interface DiscussModel {
        //m请求数据
        //详情
        public void getDiscussData(String url, int commodityId, CompositeDisposable disposable, backDiscussData backDiscussData);

        //m返回数据给p
        //详情
        public interface backDiscussData {
            //m返回数据
            void getDiscussData(Discussinfo.ResultBean result);
        }

        //购物车
        public void getCar(String carUrl, int userId, String sessionId, String data, CompositeDisposable disposable, backCarData backCarData);

        //购物车
        public interface backCarData {
            //m返回数据
            void getCarData(SHZcarinfo shZcarinfo);
        }
    }
}
