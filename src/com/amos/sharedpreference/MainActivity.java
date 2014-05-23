package com.amos.sharedpreference;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
	String tag = "MainActivity";
	EditText et_name;// 用户名
	EditText et_password;// 密码
	Button bt_login;// 登录按钮
	CheckBox cb_password;// 单选框

	// 用来保存参数的接口
	SharedPreferences sharedPreference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化
		et_name = (EditText) this.findViewById(R.id.et_name);
		et_password = (EditText) this.findViewById(R.id.et_password);
		bt_login = (Button) this.findViewById(R.id.bt_login);
		cb_password = (CheckBox) this.findViewById(R.id.cb_password);

		// 初始化参数配置
		sharedPreference=this.getSharedPreferences("spconfig", MODE_PRIVATE);
		if(sharedPreference.getBoolean("issetup", false)){
			et_name.setText(sharedPreference.getString("name", ""));
			et_password.setText(sharedPreference.getString("password", ""));
		}
		
		
		// 注册监听事件
		bt_login.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_login:
			String name = et_name.getText().toString().trim();
			String password = et_password.getText().toString().trim();
			System.out.println("name:"+name);
			System.out.println("password:"+password);
			// 定义用来保存参数的接口
			Editor edit = sharedPreference.edit();
			edit.putString("name", name);
			edit.putString("password", password);
			edit.putBoolean("issetup", true);
			//清空编辑器
			edit.clear();
			
			// 当有两个编辑器(Editor)进行编辑同一个sharedPreference时,最后一个提交的将会生效
			edit.commit();
			
		default:
			break;
		}

	}

}