package com.HomeGym.Activity;

import com.HomeGym.Bluetooth.BluetoothGetData;
import com.HomeGym.Bluetooth.BluetoothSetting;
import com.HomeGym.ExcerciseController.ValueSetting;
import com.example.homegym.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;

public class PreviewActivity extends Activity {
	
	public ImageButton bluetoothSync;
	public Button start;
	public String sString;
	public TextView focus;
	public TextView temp;
	public BluetoothGetData btGet;
	BluetoothSetting btSetting;
	ValueSetting vs = new ValueSetting();
	String goal;
	String temperature;
	SharedPreferences prefs;
	

    
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
		
		btSetting= new BluetoothSetting(PreviewActivity.this);		
		btSetting.initDeviceListDialog();
		btSetting.initProgressDialog();
		
		
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
				intent = new Intent(PreviewActivity.this, CameraActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				finish();
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
	    //Log.v("excercise_goal", goal);
	    //Log.v("excercise_focus", focus);
	   // if(ValueSetting.alarmCheck == true){
	    //	Log.v("alarm", "true");
	    //}else Log.v("alarm", "false");
		
	}
}
