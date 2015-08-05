package com.HomeGym.Activity;

import java.util.LinkedList;

import com.HomeGym.Bluetooth.BTHandler;
import com.HomeGym.Bluetooth.BluetoothSerialClient;
import com.HomeGym.Bluetooth.BluetoothSerialClient.OnScanListener;
import com.HomeGym.Bluetooth.BluetoothSetting;
import com.HomeGym.Bluetooth.BluetoothStreamingHandler;
import com.example.homegym.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class PreviewActivity extends Activity {
	
	public ImageButton bluetoothSync;
	public Button start;
	public String sString;
	BluetoothSetting btSetting;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//String.xml가면 타이틀 바 이름 바꿀 수 있음
		//getActionBar().setDisplayShowHomeEnabled(false);// 아이콘 없애기
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_preview);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		start = (Button)findViewById(R.id.start);	
		btSetting= new BluetoothSetting(PreviewActivity.this);		
		btSetting.initDeviceListDialog();
		btSetting.initProgressDialog();
		
		start.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent = new Intent(PreviewActivity.this, SquatActivity.class);//excercise액티비티 확인할라고 임의로 넘김
				startActivity(intent);
				sString = "temp";
				btSetting.sendStringData(sString);
				overridePendingTransition(0,0);
			}
		});
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
				return true;
		
			case R.id.action_exercise:
				intent = new Intent(PreviewActivity.this, PreviewActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				return true;
				
			case R.id.action_acheivementrate:
				intent = new Intent(PreviewActivity.this, AchievementActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				return true;
				
			case R.id.action_settings:
				intent = new Intent(PreviewActivity.this, SettingActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
