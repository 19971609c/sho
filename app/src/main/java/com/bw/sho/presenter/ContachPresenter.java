package com.bw.sho.presenter;

import com.bw.sho.bean.Displayinfo;
import com.bw.sho.bean.HomeBanner;
import com.bw.sho.bean.HomeShow;
import com.bw.sho.content.Contach;
import com.bw.sho.model.ContachModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @Auther: 不懂
 * @Date: 2019/3/16 17:08:56
 * @Description:
 */
public class ContachPresenter implements Contach.ContachPresenter<Contach.ContachView> {

    //软应用
    private Reference<Contach.ContachView> reference;

    private ContachModel contachModel;
    private Contach.ContachView contachView;

    @Override
    public void attachView(Contach.ContachView contachView) {
        //绑定V层
        this.contachView = contachView;
        //绑定
        reference = new WeakReference<>(contachView);
        //实例M层
        contachModel = new ContachModel();
    }

    @Override
    public void delachView(Contach.ContachView contachView) {
        //解绑
        reference.clear();
    }

    //请求轮播图
    @Override
    public void getBannerData(String url) {
        contachModel.getBannerData(url, new Contach.ContachModel.OnCallBack() {
            @Override
            public void backBannerData(HomeBanner body) {
                //放回V层数据
                contachView.getBannerData(body);
            }
        });
    }

    //首页数据
    @Override
    public void getHomeData(String url) {
        contachModel.getHomeData(url, new Contach.ContachModel.OnBackHomeData() {
            @Override
            public void backHomeData(HomeShow body) {
                contachView.getHomeData(body);
            }
        });
    }

    //关键值数据
    @Override
    public void getDisplay(String url, String keyword, int page, int count) {
        contachModel.getDisplay(url, keyword, page, count, new Contach.ContachModel.OnBackDisplayData() {
            @Override
            public void backDisplayData(Displayinfo displayinfo) {
                contachView.getDisplay(displayinfo);
            }
        });
    }

}
