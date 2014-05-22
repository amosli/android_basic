package com.amos.dataread;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private String tag = "MainActivity";
	EditText et_name;
	EditText et_password;
	Button bt_login;
	CheckBox cb_remember;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et_name = (EditText) this.findViewById(R.id.et_name);
		et_password = (EditText) this.findViewById(R.id.et_password);
		
		Log.d(tag, "准备读取数据");
		File file = new File("/data/data/com.amos.datasave/files/LoginTestConfig.txt");
		try {
			FileInputStream fis= new FileInputStream(file);
			byte[] bytes = new byte[1024];
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int len;
			while((len=fis.read(bytes))>0){
				bos.write(bytes, 0, len);
			}
			String result = new String(bos.toByteArray());
			Log.d(tag, result);
			Toast.makeText(this, "读取数据成功!"+result, Toast.LENGTH_LONG);
			String name = result.split(":")[0];
			String password = result.split(":")[1];
			Log.d(tag,name);
			Log.d(tag,password);
			et_name.setText(name);
			et_password.setText(password);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
