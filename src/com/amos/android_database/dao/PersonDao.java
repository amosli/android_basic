package com.amos.android_database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.amos.android_database.MyDBHelper;
import com.amos.android_database.domain.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amosli on 14-6-11.
 */
public class PersonDao {
    private MyDBHelper myDBHelper;
    public String tag = "PersonDao.class";

    //在new出来的时候就实现myDBHelper初始化
    public PersonDao(Context context) {
        myDBHelper = new MyDBHelper(context);
    }

    //增加
    public void addPerson(String name, String phone) {
        SQLiteDatabase database = myDBHelper.getWritableDatabase();
        //先判断数据库是否可用
        if (database.isOpen()) {
            //执行插入操作
            //database.execSQL("insert into person (name,phone) values('"+name+"','"+phone+"')");

            //推荐如下写法
            database.execSQL("insert into person (name,phone) values(?,?)", new Object[]{name, phone});
            database.close();
        }
    }

    //查找
    public boolean findPerson(String phone) {
        boolean result = false;
        SQLiteDatabase database = myDBHelper.getReadableDatabase();
        if (database.isOpen()) {
            //database.execSQL("select * from phone='"+phone+"'");

            Cursor cursor = database.rawQuery("select * from person where phone=?", new String[]{phone});
            if (cursor.moveToFirst()) {//游标是否移动到下一行,如果是,那说明有数据返回
                Log.d(tag, "count:" + cursor.getColumnCount());
                int nameIndex = cursor.getColumnIndex("name");
                Log.d(tag, "name:" + cursor.getString(nameIndex));
                cursor.close();
                result = true;
            } else {
                result = false;

            }
            database.close();
        }
        return result;
    }

    //删除一条数据
    public void deletePerson(String phone) {
        SQLiteDatabase database = myDBHelper.getWritableDatabase();
        if (database.isOpen()) {
            database.execSQL("delete from person where phone=?", new Object[]{phone});
        }
        database.close();
    }

    //更新一条数据
    public void updatePerson(String phone, String newName, String newPhone) {
        SQLiteDatabase database = myDBHelper.getWritableDatabase();
        if (database.isOpen()) {
            database.execSQL("update person set name=?,phone=? where phone=?", new Object[]{newName, newPhone, phone});
        }
        database.close();
    }

    //查找所有person
    public List<Person> findAllPerson(){
        List<Person> personList = new ArrayList<Person>();
        SQLiteDatabase database = myDBHelper.getReadableDatabase();
        if(database.isOpen()){
            Cursor cursor = database.rawQuery("select * from person ", null);
            while(cursor.moveToNext()){
                int nameIndex = cursor.getColumnIndex("name");
                int phoneIndex = cursor.getColumnIndex("phone");
                String name = cursor.getString(nameIndex);
                String phone = cursor.getString(phoneIndex);

                Person  person = new Person(name,phone);
                Log.d(tag,person.toString());

                personList.add(person);
            }

        }
        database.close();
        return personList;
    }


}
