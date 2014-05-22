package com.amos.datasave;

import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

@SuppressLint("WorldWriteableFiles")
public class savePasswordService {
	private Context context;

	private String tag = "savePasswordService";

	public savePasswordService(Context context) {
		this.context = context;
	}

	public void savePasswordToFile(String name, String password) {
		// 这里设置文件的权限
		String content = name + ":" + password;
		Log.d(tag, "设置文件的读写权限");
		try {
			FileOutputStream fileOutput = context.openFileOutput("LoginTestConfig.txt", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
			fileOutput.write(content.getBytes());
			fileOutput.flush();
			fileOutput.close();
		} catch (Exception e) {
			Log.d(tag, "设置文件的读写权限失败!");
			e.printStackTrace();
		}

	}

}
