package com.HomeGym.Activity;

import com.example.homegym.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class ExerciseActivity extends Activity {


	private static final int PB_START = 1;
	ProgressBar pb;
	boolean flag=true;

	Handler hand = new Handler(){
	
		public void handleMessage(android.os.Message msg){
			switch (msg.what){
			case PB_START:
				if(pb.getProgress()<pb.getMax())
					pb.incrementProgressBy(2);

				break;
			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);
		

		pb = (ProgressBar)findViewById(R.id.pb);

		pb.setMax(100);
		pb.setProgress(0);
		
		if(flag == true){
		hand.sendEmptyMessage(PB_START);
		}
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
