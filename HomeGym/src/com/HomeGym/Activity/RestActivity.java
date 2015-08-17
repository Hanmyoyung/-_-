package com.HomeGym.Activity;

import com.HomeGym.Bluetooth.BluetoothSetting;
import com.HomeGym.Controller.ExTimeSetting;
import com.HomeGym.Controller.TimeProgress;
import com.example.homegym.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class RestActivity extends Activity {

	ExTimeSetting tSetting = new ExTimeSetting();
	private ProgressBar pb;
	private int fillBarPercent = 10; //tSetting.restTime();
	TimeProgress tp = new TimeProgress();
	public String sString;
	BluetoothSetting btSetting;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rest);
		
		pb = (ProgressBar)findViewById(R.id.restBar);

		Intent intent = getIntent();
		final String next = intent.getExtras().getString("next");
		btSetting= new BluetoothSetting(RestActivity.this);		
		
		
		btSetting.initDeviceListDialog();
		btSetting.initProgressDialog();
		
		//sString = "1";
		//btSetting.sendStringData(sString);
		
		tp.timeProgress(RestActivity.this, next, pb, fillBarPercent);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bottommenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
