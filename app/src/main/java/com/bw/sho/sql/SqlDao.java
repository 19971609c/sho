package com.bw.sho.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 不懂
 * @Date: 2019/3/14 17:13:37
 * @Description:
 */
public class SqlDao {
    private final SQLiteDatabase db;
    //创建构建方法

    public SqlDao(Context context) {
        //创建数据库对象
        CreateSql createSql = new CreateSql(context);
        //获取数据库操作类
        db = createSql.getWritableDatabase();
    }

    //添加
    public void add(String cont) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("cont", cont);
        db.insert("record", null, contentValues);
    }

    //删除
    public void delete() {
        db.delete("record", null, null);
    }

    //查询
    public List<String> select() {
        List<String> list = new ArrayList<>();
        Cursor record = db.query("record", null, null, null, null, null, null, null);
        //判断是否有第一个
        if (record.moveToFirst()) {
            //循环取值
            do {
                list.add(record.getString(record.getColumnIndex("cont")));
            } while (record.moveToNext());//指向下一个
        }
        return list;
    }

}
