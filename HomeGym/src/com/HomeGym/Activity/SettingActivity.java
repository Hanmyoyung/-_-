package com.HomeGym.Activity;

import java.util.Calendar;

import com.HomeGym.Bluetooth.BluetoothSetting;
import com.HomeGym.Controller.ScreenService;
import com.example.homegym.R;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class SettingActivity extends PreferenceActivity implements OnDateSetListener {
	
	public String sString;
	BluetoothSetting btSetting;
	
	AlarmManager am;
    Intent intent;
    PendingIntent sender;
    
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.app_prefer);	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Preference btnDateFilter = (Preference) findPreference("btnDateFilter");
	    
		 am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

		
		btnDateFilter.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
	        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	        @Override
	        public boolean onPreferenceClick(Preference preference) {
	            showDateDialog();
	            return false;
	        }
	    });
		
		btSetting= new BluetoothSetting(SettingActivity.this);	
		btSetting.initDeviceListDialog();
		btSetting.initProgressDialog();
		
	}
	
	
	
	private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener(){
			
		@SuppressWarnings("deprecation")
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub

			Log.i("여기 몇번 읽히는거야", "알랴줫");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calendar.set(Calendar.MINUTE, minute);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			
			Log.i("설정 시간을 출력합니다", Integer.toString(hourOfDay)+" 시 "+Integer.toString(minute)+" 분 ");
	        
	        PendingIntent pendingAlaramService = pendingIntent();
	        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	        //am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingAlaramService);
	        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingAlaramService);
	       //am.setRepeating(AlarmManager.ELAPSED_REALTIME,calendar.getTimeInMillis(), 1000*60*60*24 , pendingAlaramService);
		
		}
	};

	private void showDateDialog(){
	    // Use the current date as the default date in the picker
	    final Calendar c = Calendar.getInstance();
	    int hour = c.get(Calendar.HOUR_OF_DAY);
	    int minute = c.get(Calendar.MINUTE);
	    new TimePickerDialog(this, timeSetListener ,hour, minute,false).show();
	}
	
	private PendingIntent pendingIntent(){
	//Intent intent = new Intent(this, AlarmReceiver.class);
		Intent intent = new Intent(this, ScreenService.class);
		//startService(intent);	
        //PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
		PendingIntent sender = PendingIntent.getService(this, 0, intent, 0);
		return sender;
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

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		// TODO Auto-generated method stub
		
	}
}
