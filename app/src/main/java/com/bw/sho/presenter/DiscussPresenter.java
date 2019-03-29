package com.bw.sho.presenter;

import com.bw.sho.bean.Discussinfo;
import com.bw.sho.bean.SHZcarinfo;
import com.bw.sho.content.DiscussContract;
import com.bw.sho.model.DiscussModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @Auther: 不懂
 * @Date: 2019/3/19 10:43:19
 * @Description:
 */
public class DiscussPresenter implements DiscussContract.DiscussPresenter<DiscussContract.DiscussView> {

    private DiscussContract.DiscussView discussView;
    private Reference<DiscussContract.DiscussView> reference;
    private DiscussModel discussModel;

    @Override
    public void attachView(DiscussContract.DiscussView discussView) {
        //实例v
        this.discussView = discussView;
        //绑定
        reference = new WeakReference<>(discussView);
        //实例m
        discussModel = new DiscussModel();
    }

    @Override
    public void detachView(DiscussContract.DiscussView discussView) {
        if (reference != null) {
            reference.clear();
            reference = null;
        }
    }

    //m返回数据
    //详情
    @Override
    public void getDiscussData(String url, int commodityId, CompositeDisposable disposable) {
        discussModel.getDiscussData(url, commodityId, disposable, new DiscussContract.DiscussModel.backDiscussData() {
            @Override
            public void getDiscussData(Discussinfo.ResultBean result) {
                //v返回数据
                discussView.getDiscussData(result);
            }
        });
    }


    //购物车
    @Override
    public void getCar(String carUrl, int userId, String sessionId, String data, CompositeDisposable disposable) {
        discussModel.getCar(carUrl, userId, sessionId, data, disposable, new DiscussContract.DiscussModel.backCarData() {
            @Override
            public void getCarData(SHZcarinfo shZcarinfo) {
                discussView.getCar(shZcarinfo);
            }
        });
    }
}
