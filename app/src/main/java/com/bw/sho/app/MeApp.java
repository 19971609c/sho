package com.bw.sho.app;

import android.app.Application;
import android.content.SharedPreferences;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @Auther: 不懂
 * @Date: 2019/3/17 15:05:06
 * @Description:
 */
public class MeApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences status = getSharedPreferences("status", MODE_PRIVATE);
        SharedPreferences.Editor edit = status.edit();
        edit.clear();
        edit.commit();
        Fresco.initialize(this);
    }
}
