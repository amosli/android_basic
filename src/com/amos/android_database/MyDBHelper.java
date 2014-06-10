package com.amos.android_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by amosli on 14-6-10.
 */
public class MyDBHelper extends SQLiteOpenHelper{
    /**
     * 创建数据库的构造方法
     * @param context 应用程序上下文
     * name 数据库的名字
     * factory 查询数据库的游标工厂一般情况下用sdk默认的
     * version 数据库的版本一般大于0
     */
    public MyDBHelper(Context context) {
        super(context, "test.db", null, 4);
    }
    private String tag = "MyDBHelper.class";
    /**
     * 在数据库第一次创建时会执行
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(tag, "onCreate.....");
        //创建一个数据库
        db.execSQL("create table person (personid integer primary key autoincrement ,name varchar(30) )");

    }

    /**
     * 更新数据的时候调用的方法
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(tag,"onUpgrade*******");
        //增加一列
        db.execSQL("alter table person add phone varchar(13) null");

    }


}
