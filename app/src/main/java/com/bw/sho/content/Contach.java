package com.bw.sho.content;

import com.bw.sho.bean.Displayinfo;
import com.bw.sho.bean.HomeBanner;
import com.bw.sho.bean.HomeShow;

/**
 * @Auther: 不懂
 * @Date: 2019/3/16 16:59:48
 * @Description:
 */
public class Contach {
    //V层
    public interface ContachView {
        //V层返回到activity的方法
        //返回轮图数据
        public void getBannerData(HomeBanner body);

        //返回首页数据
        public void getHomeData(HomeShow body);

        //返回关键字数据
        public void getDisplay(Displayinfo displayinfo);
    }

    //P层
    public interface ContachPresenter<ContachView> {
        //绑定
        public void attachView(ContachView contachView);

        //解绑
        public void delachView(ContachView contachView);

        //P层的请求数据方法
        //请求轮播图
        public void getBannerData(String url);

        //请求首页数据
        public void getHomeData(String url);

        //请求关键字数据
        public void getDisplay(String url, String keyword, int page, int count);
    }

    //M层
    public interface ContachModel {
        //M层的请求数据的放发
        //请求轮播图数据
        public void getBannerData(String url, OnCallBack onCallBack);

        //返回数据方法
        public interface OnCallBack {
            //对应M的放回方法
            //返回轮播图数据
            public void backBannerData(HomeBanner body);
        }

        //请求首页数据
        public void getHomeData(String url, OnBackHomeData onBackHomeData);

        public interface OnBackHomeData {
            //返回首页数据
            public void backHomeData(HomeShow body);
        }

        //请求关键字数据
        public void getDisplay(String url, String keyword, int page, int count, OnBackDisplayData onBackDisplayData);

        public interface OnBackDisplayData {
            //返回首页数据
            public void backDisplayData(Displayinfo displayinfo);
        }

    }
}
