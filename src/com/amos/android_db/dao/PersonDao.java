package com.amos.android_db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.amos.android_db.MyDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amosli on 14-6-12.
 */
public class PersonDao {
    MyDBHelper dbHelper;
    private Context context;

    public PersonDao(Context context) {
        this.context = context;
        dbHelper = new MyDBHelper(context);
    }

    /**
     * 添加一条记录
     */
    public void add(String name, int age) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put("age", age);
            values.put("name", name);
            //不允许插入一个空值,如果contentvalue,一般第二个参
            db.insert("person", null, values);//通过组拼完成的添加的操作
            db.close();
        }
    }

    public void delete(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete("person", "name=?", new String[]{name});
            db.close();
        }
    }

    public void update(String name, String newname, int newage) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if (db.isOpen()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", newname);
            contentValues.put("age", newage);
            db.update("person", contentValues, "name=?", new String[]{name});
            db.close();
        }
    }

    public boolean find(String name) {
        boolean status_result = false;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        public android.database.Cursor query(
//                String table,
//                String[] columns,
//                String selection,
//                String[] selectionArgs,
//                String groupBy,
//                String having,
//                String orderBy)

        if (db.isOpen()) {
            Cursor cursor = db.query("person", null, "name=?", new String[]{name}, null, null, null);
            if (cursor.moveToFirst()) {
                status_result = true;
            }
            cursor.close();
            db.close();
        }
        return status_result;
    }

    public Cursor findAllByCursor() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        if (db.isOpen()) {
            cursor = db.rawQuery("select personid as _id,name,age from person", null);
            //cursor = db.query("person", null, null, null, null, null, null);
        }

        return cursor;
    }


    public List<Person> findAll() {
        List<Person> persons = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (db.isOpen()) {
            persons = new ArrayList<Person>();

            Cursor cursor = db.query("person", null, null, null, null, null, null);

            while (cursor.moveToNext()) {
                Person person = new Person();
                person.setName(cursor.getString(cursor.getColumnIndex("name")));
                person.setAge(cursor.getInt(cursor.getColumnIndex("age")));
                persons.add(person);
            }
            cursor.close();
            db.close();
        }
        return persons;
    }


    public void transferMoney() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            try {

                db.beginTransaction();

                //给amos1账户里设置1000元,amost account=0;
                db.execSQL("update person  set account=?  where name = ?", new Object[]{1000, "amos1"});
                db.execSQL("update person  set account=?  where name = ?", new Object[]{0, "amos2"});

                //从amos1账户里扣除200元
                db.execSQL("update person  set account=account-?  where name = ?", new Object[]{200, "amos1"});
                //把amos1的钱转给amos2
                db.execSQL("update person set account=account+? where name=?", new Object[]{200, "amos2"});

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //显示的设置数据事务是否成功
                db.setTransactionSuccessful();
                db.endTransaction();

                db.close();
            }
        }


    }
}
