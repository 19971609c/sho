package com.bw.sho.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.bw.sho.gen.DaoMaster;
import com.bw.sho.gen.DaoSession;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * @Auther: 不懂
 * @Date: 2019/3/17 15:05:06
 * @Description:
 */
public class MeApp extends Application {

    public static MeApp meApp;
    private DaoMaster.DevOpenHelper findcar;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;

    public static MeApp getInstance() {
        return meApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        meApp = this;
        //清空登录状态
        SharedPreferences status = getSharedPreferences("status", MODE_PRIVATE);
        SharedPreferences.Editor edit = status.edit();
        edit.clear();
        edit.commit();
        //设置磁盘缓存
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryName("images")
                .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
                .build();
        //设置磁盘缓存的配置,生成配置文件
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .build();
        Fresco.initialize(this, config); //不设置默认传一个参数既可
        //设置GreenDao
        setDatBase();
    }

    private void setDatBase() {
        findcar = new DaoMaster.DevOpenHelper(this, "shop_findcar", null);
        db = findcar.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
