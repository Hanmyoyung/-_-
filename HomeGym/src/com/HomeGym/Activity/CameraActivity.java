package com.HomeGym.Activity;

import com.HomeGym.Bluetooth.BluetoothSetting;
import com.HomeGym.Controller.StorageLocationSetting;
import com.HomeGym.Controller.CameraSetting;
import com.HomeGym.Controller.SetImage;
import com.example.homegym.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

public class CameraActivity extends Activity {
	
	private static final int TAKE_GALLERY = 0;
	private static final int TAKE_CAMERA = 1;
	int flag;
	public String sString;
	public ImageView iv;
	BluetoothSetting btSetting;
	StorageLocationSetting clSetting;
	public String tempPicturePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
				
		btSetting= new BluetoothSetting(CameraActivity.this);		
		btSetting.initDeviceListDialog();
		btSetting.initProgressDialog();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		SetImage setImage = new SetImage();
		if(resultCode == RESULT_OK)
		{
		//카메라로찍었을때
			if(requestCode == TAKE_CAMERA) //1
			{
				if(data == null) return;
				//찍은 사진을 이미지뷰에 보여줌
				Bitmap bm = (Bitmap)data.getExtras().get("data");
				setImage.setCameraImageDrawable(bm, iv, tempPicturePath);
			}
				//앨범에서 가져올 때
			else if(requestCode==TAKE_GALLERY)//2
			{
				
				Uri currImageURI=data.getData();
				String path = getRealPathFromURI(currImageURI);
				tempPicturePath = path;
				//이미지뷰에 보여줌
				setImage.setAlbumImageDrawable(path, iv);
				}
			}
		else
		{
			Log.e("camera return error","에러");
			return;
		}
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

			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this); 
				overridePendingTransition(0,0);
				return true;
				
			case R.id.action_blutooth:
				btSetting.setDialog();
				return true;
				
			case R.id.action_camera:
				intent = new Intent(CameraActivity.this, CameraActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				finish();
				return true;
		
			case R.id.action_exercise:
				intent = new Intent(CameraActivity.this, PreviewActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				finish();
				return true;
				
			case R.id.action_acheivementrate:
				intent = new Intent(CameraActivity.this, AchievementActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				finish();
				return true;
				
			case R.id.action_settings:
				intent = new Intent(CameraActivity.this, SettingActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				finish();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	public void selectCamera(){
		if(flag == 2){
			Intent intent2 = new Intent(Intent.ACTION_PICK);
			intent2.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
			startActivityForResult(intent2, TAKE_GALLERY);
		}
		else if(flag == 1){
			Intent intent1 = new Intent();
			//사진저장경로 바꾸기			
			//File file = new File(Environment.getExternalStorageDirectory(), 저장될곳+"/" + "저장될이름");
			tempPicturePath = clSetting.createInternalFolder();
			intent1.putExtra(MediaStore.EXTRA_OUTPUT, tempPicturePath);
			intent1.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent1, TAKE_CAMERA);
		}

	}
	
	private String getRealPathFromURI(Uri contentURI) {
	    String result;
	    Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
	    if (cursor == null) { 
	        result = contentURI.getPath();
	    } else { 
	        cursor.moveToFirst(); 
	        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
	        result = cursor.getString(idx);
	        cursor.close();
	    }
	    return result;
	}

}
