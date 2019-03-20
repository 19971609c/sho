package com.bw.sho.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @Auther: 不懂
 * @Date: 2019/3/14 17:06:09
 * @Description:
 */
public class CreateSql extends SQLiteOpenHelper {
    public CreateSql(Context context) {
        super(context, "bw,db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建数据库
        db.execSQL("create table record(id integer primary key autoincrement,cont text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
