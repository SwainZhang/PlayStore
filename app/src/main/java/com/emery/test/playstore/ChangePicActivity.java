package com.emery.test.playstore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import de.greenrobot.event.EventBus;
import eventbus.ChangePersonDetailEvent;
import utils.photoutil.PhotoUtil;

public class ChangePicActivity extends Activity {
	private PhotoUtil photoUtil;
	private ImageView iv_bg;
	private Button mBt_finish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pic);
		iv_bg = (ImageView) findViewById(R.id.iv_bg);
		mBt_finish = (Button) findViewById(R.id.finish);
		mBt_finish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});

	}

	public static String getAvartarDiskCacheDir(Context context) {
		return (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()) || !Environment
				.isExternalStorageRemovable()) ? Environment
				.getExternalStorageDirectory().getPath()
				+ File.separator
				+ "imagetest/" : context.getCacheDir().getPath()
				+ File.separator + "imagetest/";
	}

	public void show(View view) {
		photoUtil = new PhotoUtil(ChangePicActivity.this);
		photoUtil.showDialog();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);


		// 相册返回
		if (PhotoUtil.CAMRA_SETRESULT_CODE == requestCode) {
			if (resultCode == RESULT_OK) {
				// 相册选中图片路径
				Toast.makeText(getApplicationContext(), "相册选中回调结果", Toast.LENGTH_SHORT).show();
				String cameraPath = photoUtil.getCameraPath(data);
				Bitmap bitmap = photoUtil.readBitmapAutoSize(cameraPath);
				iv_bg.setImageBitmap(bitmap);
				EventBus.getDefault().post(new ChangePersonDetailEvent(null,null,bitmap,"head"));
				//startClipActivity(cameraPath);
			}
		}
		// 相机返回
		else if (PhotoUtil.PHOTO_SETRESULT_CODE == requestCode) {
			if (resultCode == RESULT_OK) {
				Toast.makeText(getApplicationContext(), "相机返回回调结果", Toast.LENGTH_SHORT).show();

				String photoPath = photoUtil.getPhotoPath();
				Bitmap bitmap = photoUtil.readBitmapAutoSize(photoPath);
				iv_bg.setImageBitmap(bitmap);

				EventBus.getDefault().post(new ChangePersonDetailEvent(null,null,bitmap,"head"));

				//startClipActivity(photoPath);

			}
		}

		}

	}

