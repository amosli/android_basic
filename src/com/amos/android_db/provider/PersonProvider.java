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
import com.amos.android_db.dao.PersonDao;

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
    private static final int INSERT = 4;
    private static final int DELETE = 5;
    private static final int UPDATE = 6;

    private MyDBHelper myDBHelper = null;

    private static String tag = "PersonProvider.class";

    static {
        //1.指定一个路径的匹配规则
        //如果路径满足content://com.amos.android_db.provider.PersonProvider/persons,返回值就是(ALL_PERSON)=1
        uriMatcher.addURI("com.amos.android_db.provider.PersonProvider", "persons", ALL_PERSON);

        //2.如果路径满足content://com.amos.android_db.provider.PersonProvider/person/3,返回值就是(PERSON)=2
        //#号为通配符
        uriMatcher.addURI("com.amos.android_db.provider.PersonProvider", "person/#", PERSON);
        //3.如果路径满足content://com.amos.android_db.provider.PersonProvider/other,返回值就是(OTHER)=3
        uriMatcher.addURI("com.amos.android_db.provider.PersonProvider", "other", OTHER);

        //4.插入数据,如果路径满足content://com.amos.android_db.provider.PersonProvider/insert,返回值就是(INSERT)=4
        uriMatcher.addURI("com.amos.android_db.provider.PersonProvider", "insert", INSERT);
        //5.删除数据,如果路径满足content://com.amos.android_db.provider.PersonProvider/delete,返回值就是(DELETE)=5
        uriMatcher.addURI("com.amos.android_db.provider.PersonProvider", "delete", DELETE);
        //6.更新数据,如果路径满足content://com.amos.android_db.provider.PersonProvider/update,返回值就是(UPDATE)=6
        uriMatcher.addURI("com.amos.android_db.provider.PersonProvider", "update", UPDATE);


    }


    /**
     * 一般是对象第一次被创建时调用的方法
     *
     * @return
     */
    @Override
    public boolean onCreate() {
        //初始化MyDBHelper
        myDBHelper = new MyDBHelper(this.getContext());
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
        switch (result) {
            //如果路径满足content://com.amos.android_db.provider.PersonProvider/persons,返回值就是(ALL_PERSON)=1
            case ALL_PERSON:
                PersonDao dao = new PersonDao(this.getContext());
                return dao.findAllByCursor();

            //2.如果路径满足content://com.amos.android_db.provider.PersonProvider/person/3,返回值就是(PERSON)=2
            case PERSON:
                long id = ContentUris.parseId(uri);
                SQLiteDatabase database = new MyDBHelper(this.getContext()).getReadableDatabase();
                if (database.isOpen()) {
                    database.execSQL("select * person where personid = " + id);
                    return database.query("person", null, "personid", new String[]{id + ""}, null, null, null);
                    //不要关闭数据库,否则就没有数据了.
                }
            case OTHER:
                Log.d(tag, "我是其他匹配规则!");
                break;
            default:
                throw new RuntimeException("无法识别该URI,出错了!!");

        }


        return null;
    }

    @Override
    public String getType(Uri uri) {
        int result = uriMatcher.match(uri);
        switch (result){
            case ALL_PERSON:
                return "List<Person>";
            case PERSON:
                return "Person";
            default:return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {


        //content://com.amos.android_db.provider.PersonProvider/insert
        int result = uriMatcher.match(uri);
        switch (result) {
            case INSERT:
                SQLiteDatabase database = myDBHelper.getWritableDatabase();
                if (database.isOpen()) {
                    database.insert("person", null, values);
                }
                return uri;

            default:
                throw new RuntimeException("无法识别该URI,出错了!!");
        }


    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //删除操作
        //content://com.amos.android_db.provider.PersonProvider/delete
        int result = uriMatcher.match(uri);
        switch (result) {
            case DELETE:
                SQLiteDatabase database = myDBHelper.getWritableDatabase();
                return database.delete("person", selection, selectionArgs);

            default:
                throw new RuntimeException("无法识别该URI,出错了!!");
        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
       //更新操作
        //content://com.amos.android_db.provider.PersonProvider/update
        int result = uriMatcher.match(uri);
        switch (result) {
            case UPDATE:
                SQLiteDatabase database = myDBHelper.getWritableDatabase();
                return database.update("person", values, selection, selectionArgs);

            default:
                throw new RuntimeException("无法识别该URI,出错了!!");
        }


    }


}
