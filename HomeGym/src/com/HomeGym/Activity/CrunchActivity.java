package com.HomeGym.Activity;

import com.HomeGym.ExcerciseController.TimeProgress;
import com.example.homegym.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class CrunchActivity extends Activity {

	private ProgressBar pb;
	private String next = "finish";
	private int fillBarPercent = 2;// ���߿� 2�� �ٲ����
	
	TimeProgress tp = new TimeProgress();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crunch);
		//final Intent intent = new Intent(CrunchActivity.this, RestActivity.class);
		
		pb = (ProgressBar)findViewById(R.id.crunchBar);
		tp.timeProgress(CrunchActivity.this, next, pb, fillBarPercent);
		
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