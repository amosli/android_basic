package com.amos.android_db.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import com.amos.android_db.MyDBHelper;
import com.amos.android_db.dao.Person;
import com.amos.android_db.dao.PersonDao;

import java.util.List;

/**
 * Created by amosli on 14-6-17.
 */
public class PersonProvider extends ContentProvider {

    //创建一个路径识别器
    //常量UriMatcher.NO_MATCH表示不匹配任何路径的返回码,也就是说如果找不到匹配的类型,返回-1
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int ALL_PERSON = 1;
    private static final int PERSON = 2;
    private static final int OTHER = 3;

    private static String tag="PersonProvider.class";


    static{
        //1.指定一个路径的匹配规则
        //如果路径满足content://com.amos.android_db.provider.PersonProvider/persons,返回值就是(ALL_PERSON)=1
        uriMatcher.addURI("com.amos.android_db.provider.PersonProvider","persons",ALL_PERSON);

        //2.如果路径满足content://com.amos.android_db.provider.PersonProvider/person/3,返回值就是(PERSON)=2
        //#号为通配符
        uriMatcher.addURI("com.amos.android_db.provider.PersonProvider","person/#",PERSON);
        //3.如果路径满足content://com.amos.android_db.provider.PersonProvider/other,返回值就是(OTHER)=3
        uriMatcher.addURI("com.amos.android_db.provider.PersonProvider","other",OTHER);

    }


    /**
     * 一般是对象第一次被创建时调用的方法
     *
     * @return
     */
    @Override
    public boolean onCreate() {

        return false;
    }

    /**
     * 让别人去调用返回结果
     *
     * @param uri
     * @param projection    选择的列
     * @param selection     查询条件
     * @param selectionArgs 查询条件的value
     * @param sortOrder     排序
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int result = uriMatcher.match(uri);
        switch(result){
            //如果路径满足content://com.amos.android_db.provider.PersonProvider/persons,返回值就是(ALL_PERSON)=1
            case ALL_PERSON:
                PersonDao dao = new PersonDao(this.getContext());
                return dao.findAllByCursor();

            //2.如果路径满足content://com.amos.android_db.provider.PersonProvider/person/3,返回值就是(PERSON)=2
            case PERSON:
                long id = ContentUris.parseId(uri);
                SQLiteDatabase database = new MyDBHelper(this.getContext()).getReadableDatabase();
                if(database.isOpen()){
                    database.execSQL("select * person where personid = "+id);
                    return database.query("person", null, "personid", new String[]{id + ""}, null, null, null);
                    //不要关闭数据库,否则就没有数据了.
                }
            case OTHER:
                Log.d(tag,"我是其他匹配规则!");
                break;
            default:
                throw new RuntimeException("出错了!!");

        }



        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
