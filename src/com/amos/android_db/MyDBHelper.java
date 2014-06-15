package com.amos.android_db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by amosli on 14-6-12.
 */
public class MyDBHelper extends SQLiteOpenHelper {
    /**
     * @param context
     */
    public MyDBHelper(Context context) {
        super(context, "sqlitedb", null, 2);
    }

    /**
     * 数据库第一次创建的时候调用此方法
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists person (personid integer primary key autoincrement ,name varchar(30) ,age integer(3) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("alter table person add account integer null");
    }
}
