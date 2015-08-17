package com.HomeGym.Activity;

import com.HomeGym.Bluetooth.BluetoothGetData;
import com.HomeGym.Bluetooth.BluetoothSetting;
import com.HomeGym.Controller.CameraSetting;
import com.HomeGym.Controller.SetImage;
import com.HomeGym.Controller.ValueSetting;
import com.example.homegym.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PreviewActivity extends Activity {
	
	public ImageButton bluetoothSync;
	public Button start;
	public String sString;
	public TextView focus;
	public TextView temp;
	public TextView squatInfo;
	public TextView restInfo;
	public TextView crunchInfo;
	public BluetoothGetData btGet;
	BluetoothSetting btSetting;
	ValueSetting vs = new ValueSetting();
	String goal;
	String temperature;
	SharedPreferences prefs;
	CameraSetting cSetting;
	Uri currImageURI;
	String path;
	SetImage setImage;
	//ImageView iv;
	public static boolean isResume = false;

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//String.xml가면 타이틀 바 이름 바꿀 수 있음
		//getActionBar().setDisplayShowHomeEnabled(false);// 아이콘 없애기
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_preview);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		prefs=PreferenceManager.getDefaultSharedPreferences(this);
	    SharedPreferences.Editor editor= prefs.edit();
	    btGet = BluetoothGetData.getInstance();	
	    
		start = (Button)findViewById(R.id.start);	
		focus = (TextView)findViewById(R.id.focus);
		temp = (TextView)findViewById(R.id.temp);
		squatInfo = (TextView)findViewById(R.id.squatInfo);
		restInfo = (TextView)findViewById(R.id.restInfo);
		crunchInfo = (TextView)findViewById(R.id.crunchInfo);
		
		btSetting= new BluetoothSetting(PreviewActivity.this);		
		btSetting.initDeviceListDialog();
		btSetting.initProgressDialog();
		cSetting = new CameraSetting(PreviewActivity.this);
		
		//sString = "0";// 온도 받아오기
		//btSetting.sendStringData(sString);
		
		setting();
		
		start.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent = new Intent(PreviewActivity.this, SquatActivity.class);//excercise액티비티 확인할라고 임의로 넘김
				startActivity(intent);
				sString = "1";
				btSetting.sendStringData(sString);
				overridePendingTransition(0,0);
			}
		});
		
		focus.setText("집중부위 : "+vs.getFocus());
		Log.v("집중부위",vs.getFocus());
		temp.setText("온도는요?   "+btGet.getTemp());
		Log.v("온도는요?",btGet.getTemp()); 
		
		
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
				cSetting.setBeforeAfterDialog();
				return true;
		
			case R.id.action_exercise:
				intent = new Intent(PreviewActivity.this, PreviewActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				finish();
				return true;
				
			case R.id.action_acheivementrate:
				intent = new Intent(PreviewActivity.this, AchievementActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				finish();
				return true;
				
			case R.id.action_settings:
				intent = new Intent(PreviewActivity.this, SettingActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				finish();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	public void setting(){
		
		ValueSetting.alarmCheck = prefs.getBoolean("excercise_alarm", true);
		ValueSetting.goal = prefs.getString("excercise_goal" , "");
		ValueSetting.focus = prefs.getString("excercise_focus" , "");
		
		Log.i("focus값은", ValueSetting.focus);
		/*
		if(Double.parseDouble(btGet.getTemp())<= 26){
			restInfo.setText(("[휴식] 10초"));
		}else restInfo.setText(("[휴식] 20초"));
		*/
		if(vs.getFocus().equals("상체")){
			Log.i("dididkdkdkdk", "들어오냐고오오오오");
			squatInfo.setText("[스쿼트] 25회/50초");
			crunchInfo.setText("[크런치] 25회/50초");	
		}
		else if(vs.getFocus().equals("하체")){
			squatInfo.setText("[스쿼트] 50회/100초");
			crunchInfo.setText("[크런치] 25회/50초");	
		}
		else if(vs.getFocus().equals("복근")){
			squatInfo.setText("[스쿼트] 25회/50초");
			crunchInfo.setText("[크런치] 50회/100초");	
		}
		else if(vs.getFocus().equals("전신")){
			squatInfo.setText("[스쿼트] 25회/50초");
			crunchInfo.setText("[크런치] 25회/50초");	
		}
	    //Log.v("excercise_goal", goal);
	    //Log.v("excercise_focus", focus);
	   // if(ValueSetting.alarmCheck == true){
	    //	Log.v("alarm", "true");
	    //}else Log.v("alarm", "false");
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		setImage = new SetImage();
		if(resultCode == RESULT_OK)
		{
			MainActivity.MainAct.finish();
			isResume = true;
			//카메라로찍었을때
			Log.v("RESULT_OK??", "ㅅㅄㅂ");
			if(requestCode == CameraSetting.TAKE_CAMERA) //1
			{
				//찍은 사진을 이미지뷰에 보여줌
				Log.v("들어오니", "ㅅㅄㅂ");
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ CameraSetting.tempPicturePath)));
				Intent intentToMain = new Intent(this, MainActivity.class);
				intentToMain.putExtra("cameraSetting","camera");
				startActivity(intentToMain);
				finish();
			}
				
			//앨범에서 가져올 때
			else if(requestCode==CameraSetting.TAKE_GALLERY)//2
			{
				
				currImageURI=data.getData();
				path = cSetting.getRealPathFromURI(currImageURI);
				CameraSetting.tempPicturePath = path;
				//이미지뷰에 보여줌
				Intent intentToMain = new Intent(this, MainActivity.class);
				intentToMain.putExtra("cameraSetting","gallery");
				startActivity(intentToMain);
				finish();
				//setImage.setAlbumImageBackground(CameraSetting.tempPicturePath, iv);
				}
			}
		else
		{
			Log.e("camera return error","에러");
			return;
		}
	}
}
