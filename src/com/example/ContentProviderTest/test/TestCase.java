package com.example.ContentProviderTest.test;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.test.AndroidTestCase;

/**
 * Created by amosli on 14-6-19.
 */
public class TestCase extends AndroidTestCase {

    public void testInsert(){
            ContentResolver contentResolver = getContext().getContentResolver();
            Uri uri = Uri.parse("content://com.amos.android_db.provider.PersonProvider/insert");
            ContentValues values = new ContentValues();
            values.put("name", "bill");
            values.put("age", 18);
            contentResolver.insert(uri, values);
    }
    public void testDelete(){
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = Uri.parse("content://com.amos.android_db.provider.PersonProvider/delete");
        contentResolver.delete(uri,"name=?",new String[]{"amos96"});
    }
    public void testUpdate(){
        ContentResolver contentResolver = getContext().getContentResolver();
        Uri uri = Uri.parse("content://com.amos.android_db.provider.PersonProvider/update");
        ContentValues contentValues = new ContentValues();
        contentValues.put("name","jack");
        contentValues.put("age",30);
        contentResolver.update(uri,contentValues,"name=?",new String[]{"amos97"});
    }

}
