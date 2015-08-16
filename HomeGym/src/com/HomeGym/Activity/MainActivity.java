package com.HomeGym.Activity;

import java.io.File;

import com.HomeGym.Bluetooth.BluetoothSetting;
import com.HomeGym.Controller.CameraSetting;
import com.HomeGym.Controller.SetImage;
import com.HomeGym.DB.ImgDBSetting;
import com.example.homegym.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	public static Activity MainAct;
	
	public static Uri currImageURI, OutputFileUri;
	private static final int TAKE_GALLERY = 2222;
	private static final int TAKE_CAMERA = 1111;
	public String sString;
	public ImageView iv, biv, aiv;
	public int flag;
	BluetoothSetting btSetting;
	//StorageLocationSetting clSetting;
	public String  Folder, Name;
	public String path;
	CameraSetting cSetting;
	Bitmap profileBitmap;
	public String name = "HomeGym/";
	private ImgDBSetting imgSetting;
	File file;
	SetImage setImage;
	SharedPreferences prefs;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		biv = (ImageView) findViewById(R.id.beforeImg);
		aiv = (ImageView) findViewById(R.id.afterImg);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//aiv.setImageResource(R.drawable.after);
		//biv.setImageResource(R.drawable.before);
		
				
		MainAct = this;
		imgSetting=new ImgDBSetting(MainActivity.this);
		setImage = new SetImage();
		cSetting = new CameraSetting(MainActivity.this);
		//clSetting = new StorageLocationSetting();
		btSetting= new BluetoothSetting(MainActivity.this);	
		btSetting.initDeviceListDialog();
		btSetting.initProgressDialog();		
		
		setMainImage();
		

	}
	
	public void setMainImage(){
		setImage.setAlbumImageBackground(imgSetting.selectPatheValue(1), biv);
		setImage.setAlbumImageBackground(imgSetting.selectPatheValue(2), aiv);
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
				//cSetting.setCameraDialog();
				cSetting.setBeforeAfterDialog();
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
	
	@Override
    public void onResume()
    {
     Log.d("start", "onResume");
     super.onResume();
     
     if(PreviewActivity.isResume == true){
    	 PreviewActivity.isResume = false;
    	 Intent intent = getIntent();
    	 String cs = intent.getExtras().getString("cameraSetting");
     	if(cs.equals("camera")){
     		BitmapFactory.Options options = new BitmapFactory.Options();
     		options.inSampleSize = 8;
     		Bitmap bm = BitmapFactory.decodeFile(CameraSetting.tempPicturePath, options);
     		
     		setImage.setCameraImageBackground(bm, iv, CameraSetting.tempPicturePath); 
     	}
     	else if(cs.equals("gallery")){
     		setImage.setAlbumImageBackground(CameraSetting.tempPicturePath, iv);
     	}
     }
    }


	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		
		if(resultCode == RESULT_OK)
		{
			//카메라로찍었을때
			Log.v("RESULT_OK??", "ㅅㅄㅂ");
			if(requestCode == TAKE_CAMERA) //1
			{
				//찍은 사진을 이미지뷰에 보여줌
				Log.v("들어오니", "ㅅㅄㅂ");
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ CameraSetting.tempPicturePath)));
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 8;
				Bitmap bm = BitmapFactory.decodeFile(CameraSetting.tempPicturePath, options);
				if(CameraSetting.baflag == 1){
					iv = biv;
				}else iv = aiv;
				iv.setImageDrawable(null);
				setImage.setCameraImageBackground(bm, iv, CameraSetting.tempPicturePath);
				}
				
			//앨범에서 가져올 때
			else if(requestCode==TAKE_GALLERY)//2
			{

				currImageURI=data.getData();
				path = cSetting.getRealPathFromURI(currImageURI);
				CameraSetting.tempPicturePath = path;
				//이미지뷰에 보여줌
				if(CameraSetting.baflag == 1){
					iv = biv;
					CameraSetting.btempPicturePath = CameraSetting.tempPicturePath;
					imgSetting.insert(path, 1);
					//imgSetting.select();
				}
				else{
					iv = aiv;
					CameraSetting.atempPicturePath = CameraSetting.tempPicturePath;
					imgSetting.insert(path, 2);
					//imgSetting.select();
				}
				iv.setImageDrawable(null);
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

