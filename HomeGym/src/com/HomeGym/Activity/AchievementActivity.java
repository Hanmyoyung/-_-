package com.HomeGym.Activity;

import com.HomeGym.Bluetooth.BluetoothSetting;
import com.example.homegym.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class AchievementActivity extends Activity {
	
	public String sString;
	BluetoothSetting btSetting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acheivement);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
				

		btSetting= new BluetoothSetting(AchievementActivity.this);		
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

			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this); 
				overridePendingTransition(0,0);
				return true;
				
			case R.id.action_blutooth:
				btSetting.setDialog();
				return true;
				
			case R.id.action_camera:
				intent = new Intent(AchievementActivity.this, CameraActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				return true;
		
			case R.id.action_exercise:
				intent = new Intent(AchievementActivity.this, PreviewActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				return true;
				
				
			case R.id.action_settings:
				intent = new Intent(AchievementActivity.this, SettingActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
