package com.HomeGym.Activity;

import com.HomeGym.Controller.ExTimeSetting;
import com.HomeGym.Controller.TimeProgress;
import com.example.homegym.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class SquatActivity extends Activity {

	ExTimeSetting tSetting = new ExTimeSetting();
	private ProgressBar pb;
	private String next = "Crunch";
	private int fillBarPercent = tSetting.lowerTime();// 나중에 2로 바꿔야함
	
	TimeProgress tp = new TimeProgress();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_squat);
		pb = (ProgressBar)findViewById(R.id.squatBar);
		tp.timeProgress(SquatActivity.this, next, pb, fillBarPercent);

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
