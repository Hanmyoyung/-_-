package com.HomeGym.Activity;

import com.HomeGym.Bluetooth.BluetoothSetting;
import com.HomeGym.ExcerciseController.ValueSetting;
import com.example.homegym.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	public String sString;
	BluetoothSetting btSetting;
	String goal;
	String focus;
	SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
				
		btSetting= new BluetoothSetting(MainActivity.this);	
		btSetting.initDeviceListDialog();
		btSetting.initProgressDialog();
		
		prefs=PreferenceManager.getDefaultSharedPreferences(this);
	    SharedPreferences.Editor editor= prefs.edit();
		
	    setting();
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
				intent = new Intent(MainActivity.this, CameraActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
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

