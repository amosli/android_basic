package com.example.android_content_provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class ContentProvider extends Activity {


    private static String tag = "ContentProvider.class";

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //内容解析者
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.amos.android_db.provider.PersonProvider/persons");
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int age = cursor.getInt(cursor.getColumnIndex("age"));
            System.out.println("name:"+name+" age:"+age);
            Log.d(tag,"用户名:"+name+" 年龄:"+age);
        }
    }
}
