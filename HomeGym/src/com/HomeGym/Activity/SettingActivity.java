package com.HomeGym.Activity;

import com.HomeGym.Bluetooth.BluetoothSetting;
import com.example.homegym.R;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class SettingActivity extends PreferenceActivity {
	
	public String sString;
	BluetoothSetting btSetting;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.app_prefer);

		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	
		btSetting= new BluetoothSetting(SettingActivity.this);	
		btSetting.initDeviceListDialog();
		btSetting.initProgressDialog();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bottommenu, menu);
		return true;
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
				intent = new Intent(SettingActivity.this, CameraActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				finish();
				return true;
		
			case R.id.action_exercise:
				intent = new Intent(SettingActivity.this, PreviewActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				finish();
				return true;
				
			case R.id.action_acheivementrate:
				intent = new Intent(SettingActivity.this, AchievementActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				finish();
				return true;
				
		}
		return super.onOptionsItemSelected(item);
	}
}
