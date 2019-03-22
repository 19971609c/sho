package com.bw.sho.presenter;

import com.bw.sho.bean.FindCarinfo;
import com.bw.sho.content.FindCarContach;
import com.bw.sho.model.FindCarModel;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @Auther: 不懂
 * @Date: 2019/3/16 17:08:56
 * @Description:
 */
public class FindCarPresenter implements FindCarContach.FindCarPresenter<FindCarContach.FindCarView> {

    //软应用
    private Reference<FindCarContach.FindCarView> reference;
    private FindCarContach.FindCarView findCarView;
    private FindCarModel findCarModel;

    @Override
    public void attachView(FindCarContach.FindCarView findCarView) {
        this.findCarView = findCarView;
        reference = new WeakReference<>(findCarView);
        findCarModel = new FindCarModel();
    }

    @Override
    public void delachView(FindCarContach.FindCarView findCarView) {
        if (reference != null) {
            reference.clear();
            reference = null;
        }
    }

    @Override
    public void getFindCar(String url, int userId, String sessionId) {
        findCarModel.getFindCar(url, userId, sessionId, new FindCarContach.CFindCarModel.OnCallBack() {
            @Override
            public void getFindCar(FindCarinfo findCarinfo) {
                findCarView.getFindCar(findCarinfo);
            }
        });
    }


}