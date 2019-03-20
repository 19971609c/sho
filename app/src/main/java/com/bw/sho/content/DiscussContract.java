package com.bw.sho.content;

import com.bw.sho.bean.Discussinfo;

/**
 * @Auther: 不懂
 * @Date: 2019/3/19 10:15:25
 * @Description:
 */
public class DiscussContract {
    //v
    public interface DiscussView {
        //返回数据
        public void getDiscussData(Discussinfo.ResultBean result);
    }

    //p
    public interface DiscussPresenter<DiscussView> {
        //绑定
        public void attachView(DiscussView discussView);

        //解绑
        public void detachView(DiscussView discussView);

        //p请求数据
        public void getDiscussData(String url, int commodityId);
    }

    public interface DiscussModel {
        //m请求数据
        public void getDiscussData(String url, int commodityId, backDiscussData backDiscussData);

        //m返回数据给p
        public interface backDiscussData {
            //m返回数据
            void getDiscussData(Discussinfo.ResultBean result);
        }
    }
}
