package com.HomeGym.Activity;

import java.io.File;

import com.HomeGym.Bluetooth.BluetoothSetting;
import com.HomeGym.Controller.CameraSetting;
import com.HomeGym.Controller.SetImage;
import com.HomeGym.Controller.ValueSetting;
import com.HomeGym.Activity.PreviewActivity;
import com.HomeGym.DB.ImgDBSetting;
import com.example.homegym.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	public static Activity MainAct;
	
	public static Uri currImageURI, OutputFileUri;
	private static final int TAKE_GALLERY = 2222;
	private static final int TAKE_CAMERA = 1111;
	public String sString;
	public ImageView iv, biv, aiv;
	public TextView uHeight, uWeight, uBMI, bmiInfo;
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
	ValueSetting vs = new ValueSetting();
	String height;
	String weight;
	double dBmi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getActionBar().setIcon(R.drawable.logo);
		
		biv = (ImageView) findViewById(R.id.beforeImg);
		aiv = (ImageView) findViewById(R.id.afterImg);
		uHeight = (TextView) findViewById(R.id.userHeight);
		uWeight = (TextView) findViewById(R.id.userWeight);
		uBMI = (TextView) findViewById(R.id.userBMI);
		bmiInfo = (TextView) findViewById(R.id.BMIinfo);
		getActionBar().setDisplayHomeAsUpEnabled(true);
						
		MainAct = this;
		imgSetting=new ImgDBSetting(MainActivity.this);
		setImage = new SetImage();
		cSetting = new CameraSetting(MainActivity.this);
		//clSetting = new StorageLocationSetting();
		btSetting= new BluetoothSetting(MainActivity.this);	
		btSetting.initDeviceListDialog();
		btSetting.initProgressDialog();		
		setMainImage();		
		
		prefs=PreferenceManager.getDefaultSharedPreferences(this);
	    SharedPreferences.Editor editor= prefs.edit();
		


	}
	
	public void setMainImage(){	
		try{
			biv.setImageDrawable(null);
			setImage.setAlbumImageBackground(imgSetting.selectPatheValue(1), biv);
		}catch(Exception e){		
			biv.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
			biv.setImageResource(R.drawable.before1);		
		}	
		
		try{
			aiv.setImageDrawable(null);
			setImage.setAlbumImageBackground(imgSetting.selectPatheValue(2), aiv);
		}catch(Exception e){		
			aiv.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
			aiv.setImageResource(R.drawable.after1);			
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


		ValueSetting.userHeight = prefs.getString("userHeight", "");
		ValueSetting.userWeight = prefs.getString("userWeight" , "");
		
     		if(vs.getHeight().equals("")||vs.getWeight().equals("")){
     			height = "0.0";
     			weight = "0.0";
     			uBMI.setText("나의 BMI : "+getBMI());
     			bmiInfo.setText("나의 비만도 : "+getBMIinfo());
     		}else{
        		height = vs.getHeight();
        		Log.i("키",vs.getHeight());
         		weight = vs.getWeight();
         		Log.i("몸무게",vs.getWeight());
         		uBMI.setText("나의 BMI : "+getBMI());
         		bmiInfo.setText("나의 비만도 : "+getBMIinfo());
         		
     		}
     		    		
		uHeight.setText("나의 키 : "+height);
		//Log.v("집중부위",vs.getFocus());
		uWeight.setText("나의 몸무게 : "+weight);
		
		
 		
     if(PreviewActivity.isResume == true){
    	 PreviewActivity.isResume = false;
    	 if(CameraSetting.baflag == 1){
    		 iv = biv;
    	 }else iv = aiv;
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
			if(requestCode == TAKE_CAMERA) //1
			{
				//찍은 사진을 이미지뷰에 보여줌
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
	
	public String getBMI(){
		String bmi="";
		if(height.equals("0.0")|| weight.equals("0.0")){
			bmi = "키와 몸무게를 입력하세요";
			return bmi;
		}
		double dheight = Double.valueOf(height).doubleValue()/100.0;
		Log.i("미터키", Double.toString(dheight));
		double ddheight = dheight*dheight;
		double dweight = Double.valueOf(weight).doubleValue();

		dBmi = dweight/ddheight;
		Log.i("bmi", Double.toString(dBmi));
		bmi = String.format("%.2f", dBmi);
		
		return bmi;
				
	}
	
	public String getBMIinfo(){
		
		String bmiInfo;
		
		if(height.equals("0.0")|| weight.equals("0.0")){
			bmiInfo="키와 몸무게를 입력하세요";
		}else if (dBmi<18.5){
			bmiInfo = "저체중";
		}else if(dBmi<23){
			bmiInfo = "정상";
		}else if(dBmi<25){
			bmiInfo = "과체중";
		}else if(dBmi<30){
			bmiInfo = "비만";
		}
		else bmiInfo = "고도비만";
		
		return bmiInfo;
	}


}

