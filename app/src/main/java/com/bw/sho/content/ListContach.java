package com.bw.sho.content;

import com.bw.sho.bean.OneListinfo;
import com.bw.sho.bean.ThreeListinfo;
import com.bw.sho.bean.TwoListinfo;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Auther: 不懂
 * @Date: 2019/3/16 16:59:48
 * @Description:
 */
public class ListContach {
    //V层
    public interface ListContachView {
        //V层返回到activity的方法
        public void getOnelist(List<OneListinfo.ResultBean> oneList);


        public void getTwoList(List<TwoListinfo.ResultBean> tworesult);


        public void getThreeList(List<ThreeListinfo.ResultBean> threeresult);
    }

    //P层
    public interface ListContachPresenter<ListContachView> {
        //绑定
        public void attachView(ListContachView listContachView);

        //解绑
        public void delachView(ListContachView listContachView);

        //P层的请求数据方法
        public void getOnelist(String url, CompositeDisposable disposable);


        public void getTwoList(String url, String firstCategoryId, CompositeDisposable disposable);


        public void getThreeList(String url, String firstCategoryId, int page, int count, CompositeDisposable disposable);
    }

    //M层
    public interface ListContachModel {
        //M层的请求数据的放发

        public void getOnelist(String url, CompositeDisposable disposable, OnCallBack onCallBack);


        public void getTwoList(String url, String firstCategoryId, CompositeDisposable disposable, OnCallBack onCallBack);


        public void getThreeList(String url, String firstCategoryId, int page, int count, CompositeDisposable disposable, OnCallBack onCallBack);
    }

    //返回数据方法
    public interface OnCallBack {
        public void getOnelist(List<OneListinfo.ResultBean> oneList);

        public void getTwoList(List<TwoListinfo.ResultBean> tworesult);

        public void getThreeList(List<ThreeListinfo.ResultBean> threeresult);
    }

}
