package com.amos.datasave;

import java.io.File;
import java.io.FileOutputStream;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothClass.Device;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

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
	//写数据到sdcard
	public void savePasswordToSDCard(String name, String password) {
		// android 2.1 /sdcard/xx.txt
		// android 2.2 /mnt/sdcard/xx.txt
		// self_define /excard/xx.txt
		
//		File externalStorageDirectory = Environment.getExternalStorageDirectory();
//		String path = externalStorageDirectory.getPath();
//		System.out.println("path:" + path);

		// 要存储的内容
		String content = name + ":" + password;
		
		StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
		long blockSize = statFs.getBlockSize();
		long blockCount = statFs.getBlockCount();
		long sdCardSize = blockSize*blockCount;
		
		Log.d(tag,""+sdCardSize );
		System.out.println("sdCardSize:"+sdCardSize);
		
		Log.d(tag, "检验sdcard是否可用?");
		//判断sdcard是否存在?
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			Log.d(tag, "sdcard不可用!");
			Toast.makeText(context, "没有找到SDCard!", Toast.LENGTH_LONG);
			return ;
		};
		
		;
		
		try {
			// File file = new File("/sdcard/qqpassword.txt");
			// File file = new File(path + "/qqpassword2.txt");
			File file = new File(Environment.getExternalStorageDirectory(), "/qqpassword2.txt");
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(content.getBytes());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
