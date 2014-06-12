package com.amos.android_db;

import android.app.Activity;
import android.os.Bundle;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        MyDBHelper myDBHelper = new MyDBHelper(this);
        myDBHelper.getReadableDatabase();
        myDBHelper.close();
    }
}