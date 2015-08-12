package com.HomeGym.Activity;

import java.io.File;

import com.HomeGym.Bluetooth.BluetoothSetting;
import com.HomeGym.Controller.StorageLocationSetting;
import com.HomeGym.Controller.CameraSetting;
import com.HomeGym.Controller.SetImage;
import com.example.homegym.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	public static Uri currImageURI, OutputFileUri;
	private static final int TAKE_GALLERY = 2222;
	private static final int TAKE_CAMERA = 1111;
	public String sString;
	public ImageView iv;
	public int flag;
	BluetoothSetting btSetting;
	//StorageLocationSetting clSetting;
	public String  Folder, Name;
	public static String tempPicturePath;
	public String path;
	CameraSetting cSetting;
	Bitmap profileBitmap;
	public String name = "HomeGym/";
	File file;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		iv = (ImageView) findViewById(R.id.imageView1);
		getActionBar().setDisplayHomeAsUpEnabled(true);
				
		cSetting = new CameraSetting(MainActivity.this);
		//clSetting = new StorageLocationSetting();
		btSetting= new BluetoothSetting(MainActivity.this);	
		btSetting.initDeviceListDialog();
		btSetting.initProgressDialog();		
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	  MenuInflater bottom = getMenuInflater(); 
	  bottom.inflate(R.menu.bottommenu, menu);
	  return super.onCreateOptionsMenu(menu);
	  
	  
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;		
		switch (item.getItemId()) {
				
			case R.id.action_blutooth:
				btSetting.setDialog();
				return true;
				
			case R.id.action_camera:
				cSetting.setCameraDialog();
				return true;
		
			case R.id.action_exercise:
				intent = new Intent(MainActivity.this, PreviewActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				return true;
				
			case R.id.action_acheivementrate:
				intent = new Intent(MainActivity.this, AchievementActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				return true;
				
			case R.id.action_settings:
				intent = new Intent(MainActivity.this, SettingActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		SetImage setImage = new SetImage();
		if(resultCode == RESULT_OK)
		{
			//카메라로찍었을때
			Log.v("RESULT_OK??", "ㅅㅄㅂ");
			if(requestCode == TAKE_CAMERA) //1
			{
				//찍은 사진을 이미지뷰에 보여줌
				Log.v("들어오니", "ㅅㅄㅂ");
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ tempPicturePath)));
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 8;
				Bitmap bm = BitmapFactory.decodeFile(CameraSetting.tempPicturePath, options);
				setImage.setCameraImageBackground(bm, iv, CameraSetting.tempPicturePath);
				}
				
			//앨범에서 가져올 때
			else if(requestCode==TAKE_GALLERY)//2
			{
				
				currImageURI=data.getData();
				path = cSetting.getRealPathFromURI(currImageURI);
				CameraSetting.tempPicturePath = path;
				//이미지뷰에 보여줌
				setImage.setAlbumImageBackground(CameraSetting.tempPicturePath, iv);
				}
			}
		else
		{
			Log.e("camera return error","에러");
			return;
		}
	}

}

