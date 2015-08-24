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
	
	public static Activity PrevAct;
	
	public ImageButton bluetoothSync;
	public Button start;
	public String sString;
	public TextView focus;
	public TextView temp;
	public TextView squatInfo, lungeInfo, legraiseInfo;
	public TextView restInfo1,restInfo2,restInfo3;
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
		
		PrevAct = this;
		
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//String.xml가면 타이틀 바 이름 바꿀 수 있음
		//getActionBar().setDisplayShowHomeEnabled(false);// 아이콘 없애기
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_preview);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(R.drawable.logo);
		
		prefs=PreferenceManager.getDefaultSharedPreferences(this);
	    SharedPreferences.Editor editor= prefs.edit();
	    btGet = BluetoothGetData.getInstance();	
	    
		start = (Button)findViewById(R.id.start);	
		focus = (TextView)findViewById(R.id.focus);
		temp = (TextView)findViewById(R.id.temp);
		squatInfo = (TextView)findViewById(R.id.squatInfo);
		restInfo1 = (TextView)findViewById(R.id.restInfo1);
		restInfo2 = (TextView)findViewById(R.id.restInfo2);
		restInfo3 = (TextView)findViewById(R.id.restInfo3);
		crunchInfo = (TextView)findViewById(R.id.crunchInfo);
		legraiseInfo = (TextView)findViewById(R.id.legraiseInfo);
		lungeInfo = (TextView)findViewById(R.id.lungeInfo);
		
		btSetting= new BluetoothSetting(PreviewActivity.this);		
		btSetting.initDeviceListDialog();
		btSetting.initProgressDialog();
		cSetting = new CameraSetting(PreviewActivity.this);
	
		setting();
		
		start.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent = new Intent(PreviewActivity.this, SquatActivity.class);//excercise액티비티 확인할라고 임의로 넘김
				startActivity(intent);
				finish();
				sString = "1";
				btSetting.sendStringData(sString);
				overridePendingTransition(0,0);
			}
		});
		
		if(vs.getFocus().equals("")){
			focus.setText("집중부위 : "+"설정해주세요");
		}else focus.setText("집중부위 : "+vs.getFocus());
		Log.v("집중부위",vs.getFocus());
		temp.setText("온도는요?   "+ValueSetting.temp);
		Log.v("온도는요?",ValueSetting.temp); 
		
		
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
		
		if(Double.parseDouble(ValueSetting.temp)<= 26){
			restInfo1.setText(("[휴식] 10초"));
			restInfo2.setText(("[휴식] 10초"));
			restInfo3.setText(("[휴식] 10초"));
		}else{ 
			restInfo1.setText(("[휴식] 20초"));
			restInfo2.setText(("[휴식] 20초"));
			restInfo3.setText(("[휴식] 20초"));
		}
		
		
		if(vs.getFocus().equals("상체")){
			squatInfo.setText("[스쿼트] 25회/50초");
			crunchInfo.setText("[크런치] 25회/50초");	
			lungeInfo.setText("[런지] 25회/50초");	
			legraiseInfo.setText("[레그레이즈] 25회/50초");	
		}
		else if(vs.getFocus().equals("하체")){
			squatInfo.setText("[스쿼트] 50회/100초");
			crunchInfo.setText("[크런치] 25회/50초");	
			lungeInfo.setText("[런지] 50회/100초");	
			legraiseInfo.setText("[레그레이즈] 25회/50초");	
		}
		else if(vs.getFocus().equals("복근")){
			lungeInfo.setText("[런지] 25회/50초");	
			legraiseInfo.setText("[레그레이즈] 50회/100초");	
			squatInfo.setText("[스쿼트] 25회/50초");
			crunchInfo.setText("[크런치] 50회/100초");	
		}
		else if(vs.getFocus().equals("전신")){
			lungeInfo.setText("[런지] 25회/50초");	
			legraiseInfo.setText("[레그레이즈] 25회/50초");	
			squatInfo.setText("[스쿼트] 25회/50초");
			crunchInfo.setText("[크런치] 25회/50초");	
		}else if(vs.getFocus().equals("")){
			lungeInfo.setText("[런지] 25회/50초");	
			legraiseInfo.setText("[레그레이즈] 25회/50초");	
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

			if(requestCode == CameraSetting.TAKE_CAMERA) //1
			{
				//찍은 사진을 이미지뷰에 보여줌

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
