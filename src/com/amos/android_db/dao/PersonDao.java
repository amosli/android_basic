package com.amos.android_db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.amos.android_db.MyDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amosli on 14-6-12.
 */
public class PersonDao {
    private Context context;
    MyDBHelper dbHelper;

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

     public List<Person> findAll(){
         List<Person> persons = null;
         SQLiteDatabase db = dbHelper.getReadableDatabase();
         if(db.isOpen()){
             persons = new ArrayList<Person>();

             Cursor cursor = db.query("person", null, null, null, null, null, null);

             while(cursor.moveToNext()){
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



}
