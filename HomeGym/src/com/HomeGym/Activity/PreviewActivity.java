package com.HomeGym.Activity;

import com.HomeGym.Bluetooth.BluetoothSetting;
import com.HomeGym.ExcerciseController.ValueSetting;
import com.example.homegym.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PreviewActivity extends Activity {
	
	public ImageButton bluetoothSync;
	public Button start;
	public String sString;
	public TextView focus;
	public TextView temp;
	BluetoothSetting btSetting;
	ValueSetting vs = new ValueSetting();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//String.xml���� Ÿ��Ʋ �� �̸� �ٲ� �� ����
		//getActionBar().setDisplayShowHomeEnabled(false);// ������ ���ֱ�
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_preview);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		start = (Button)findViewById(R.id.start);	
		focus = (TextView)findViewById(R.id.focus);
		temp = (TextView)findViewById(R.id.temp);
		
		btSetting= new BluetoothSetting(PreviewActivity.this);		
		btSetting.initDeviceListDialog();
		btSetting.initProgressDialog();
		
		start.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent intent = new Intent(PreviewActivity.this, SquatActivity.class);//excercise��Ƽ��Ƽ Ȯ���Ҷ�� ���Ƿ� �ѱ�
				startActivity(intent);
				sString = "temp";
				btSetting.sendStringData(sString);
				overridePendingTransition(0,0);
			}
		});
		
		focus.setText("���ߺ��� : "+vs.getFocus());
		temp.setText("�µ��¿�?");
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
