package com.amos.datasave;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	String tag = "MainActivity";
	EditText et_name;// 用户名
	EditText et_password;// 密码
	Button bt_login;// 登录按钮
	CheckBox cb_password;// 单选框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化
		et_name = (EditText) this.findViewById(R.id.et_name);
		et_password = (EditText) this.findViewById(R.id.et_password);
		bt_login = (Button) this.findViewById(R.id.bt_login);
		cb_password = (CheckBox) this.findViewById(R.id.cb_password);

		StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
		long blockSize = statFs.getBlockSize();
		long blockCount = statFs.getBlockCount();
		long sdCardSize = blockSize*blockCount;
		Log.d(tag,""+sdCardSize );
		
		// 注册点击事件
		bt_login.setOnClickListener(this);

	}

	// 点击事件
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_login:

			if (cb_password.isChecked()) {// 如果单选框被选中了
				// 保存数据到rom
//				new savePasswordService(this).savePasswordToFile(et_name.getText().toString(), et_password.getText().toString());
				
				//save to sdcard
				new savePasswordService(this).savePasswordToSDCard(et_name.getText().toString(), et_password.getText().toString());
				//Toast.makeText(this, "保存数据成功!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "没有保存数据!", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
