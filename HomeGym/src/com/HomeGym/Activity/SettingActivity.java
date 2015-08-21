package com.HomeGym.Activity;

import java.util.Calendar;

import com.HomeGym.Bluetooth.BluetoothSetting;
import com.HomeGym.Controller.CameraSetting;
import com.HomeGym.Controller.ScreenService;
import com.HomeGym.Controller.SetImage;
import com.HomeGym.DB.AlarmDBSetting;
import com.example.homegym.R;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class SettingActivity extends PreferenceActivity implements OnDateSetListener {
	
	public String sString;
	private AlarmDBSetting alarmSetting;	
	BluetoothSetting btSetting;
	Calendar calendar = Calendar.getInstance();	
	SharedPreferences prefs;
	AlarmManager am;
    Intent intent;
    PendingIntent sender;
     CameraSetting cSetting;
	Uri currImageURI;
	String path;
    String summaryString;
    
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.app_prefer);	
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(R.drawable.logo);
		
		Preference btnDateFilter = (Preference) findPreference("btnDateFilter");
	    
		am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarmSetting = new AlarmDBSetting(SettingActivity.this);
		
		prefs=PreferenceManager.getDefaultSharedPreferences(this);
	    SharedPreferences.Editor editor= prefs.edit();
		
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
		cSetting = new CameraSetting(SettingActivity.this);
		
		//findPreference("btnDateFilter").setSummary(summary);
		setOnPreferenceChange(findPreference("btnDateFilter"));
	}
	

	
	private Preference.OnPreferenceChangeListener onPreferenceChangeListener = new Preference.OnPreferenceChangeListener() {
		 
	    @Override
	    public boolean onPreferenceChange(Preference preference, Object newValue) {
	    	
	    	if (preference instanceof Preference) {	
	    	    preference.setSummary(alarmSetting.selectValue());  
	    	    Log.i("������û�̶��...", "����� ���Ӹ�����....");
	    	}else if(preference instanceof CheckBoxPreference){
	    		Log.i("������û�̶��...", "���� ������ �ϳ���");
	    		if(prefs.getBoolean("excercise_alarm", true)==false){
	    			//am.cancel(pendingIntent());
	    			Log.i("������û�̶��...", "���� ������ �ϳ���");
	    		}
	    		
	    		
	    	}

	        return true;
	    }
	 
	};

	
	private void setOnPreferenceChange(Preference mPreference) {
	    mPreference.setOnPreferenceChangeListener(onPreferenceChangeListener);
	 
	    onPreferenceChangeListener.onPreferenceChange(mPreference,
	        PreferenceManager.getDefaultSharedPreferences(mPreference.getContext()).getString(mPreference.getKey(), ""));
	}

	
	
	private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener(){
			
		@SuppressWarnings("deprecation")
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub

			Log.i("���� ��� �����°ž�", "�˷��Z");
			calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			calendar.set(Calendar.MINUTE, minute);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			
			Log.i("���� �ð��� ����մϴ�", Integer.toString(hourOfDay)+" �� "+Integer.toString(minute)+" �� ");
			summaryString=Integer.toString(hourOfDay)+" �� "+Integer.toString(minute)+" �� ";
			alarmSetting.insert(summaryString);
			
			setOnPreferenceChange(findPreference("btnDateFilter"));
			
	        PendingIntent pendingAlaramService = pendingIntent();
	        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	        //am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingAlaramService);
	        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingAlaramService);
	        am.setRepeating(AlarmManager.ELAPSED_REALTIME,calendar.getTimeInMillis(), 1000*60*60*24 , pendingAlaramService);
		
		}
	};

	private void showDateDialog(){
	    final Calendar c = Calendar.getInstance();
	    int hour = c.get(Calendar.HOUR_OF_DAY);
	    int minute = c.get(Calendar.MINUTE);
	    new TimePickerDialog(this, timeSetListener ,hour, minute,false).show();
	}
	
	private PendingIntent pendingIntent(){
		Intent intent = new Intent(this, ScreenService.class);
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
				cSetting.setBeforeAfterDialog();
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
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		SetImage setImage = new SetImage();
		if(resultCode == RESULT_OK)
		{
			MainActivity.MainAct.finish();
			PreviewActivity.isResume = true;
			//ī�޶���������
			Log.v("RESULT_OK??", "������");
			if(requestCode == CameraSetting.TAKE_CAMERA) //1
			{
				//���� ������ �̹����信 ������
				Log.v("������", "������");
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ CameraSetting.tempPicturePath)));
				Intent intentToMain = new Intent(this, MainActivity.class);
				intentToMain.putExtra("cameraSetting","camera");
				startActivity(intentToMain);
				finish();
			}
				
			//�ٹ����� ������ ��
			else if(requestCode==CameraSetting.TAKE_GALLERY)//2
			{
				
				currImageURI=data.getData();
				path = cSetting.getRealPathFromURI(currImageURI);
				CameraSetting.tempPicturePath = path;
				//�̹����信 ������
				Intent intentToMain = new Intent(this, MainActivity.class);
				intentToMain.putExtra("cameraSetting","gallery");
				startActivity(intentToMain);
				finish();
				//setImage.setAlbumImageBackground(CameraSetting.tempPicturePath, iv);
				}
			}
		else
		{
			Log.e("camera return error","����");
			return;
		}
	}
}

