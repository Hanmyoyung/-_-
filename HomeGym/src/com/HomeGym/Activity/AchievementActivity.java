package com.HomeGym.Activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.HomeGym.Bluetooth.BluetoothSetting;
import com.HomeGym.Controller.CameraSetting;
import com.HomeGym.Controller.SetImage;
import com.HomeGym.DB.DBSetting;
import com.example.homegym.R;
import com.handstudio.android.hzgrapherlib.graphview.RadarGraphView;
import com.handstudio.android.hzgrapherlib.vo.radargraph.RadarGraph;
import com.handstudio.android.hzgrapherlib.vo.radargraph.RadarGraphVO;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class AchievementActivity extends Activity {
	
	public String sString;
	private BluetoothSetting btSetting;
	private DBSetting dbSetting;
	private ViewGroup layoutGraphView;
	private float crunchPercent;
	private float squatPercent;
	private float legRaisePercent;
	private float lungePercent;
	private float todayPercent;
	private float monthPercent;
	private Button dateBtn;
	private String thisDate;
	private RadarGraphView rgView;
	int thisYear, thisMonth, thisDay,reMonth;
	GregorianCalendar calendar;
	CameraSetting cSetting;
	Uri currImageURI;
	String path;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_acheivement);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setIcon(R.drawable.logo);
		
		dateBtn = (Button)findViewById(R.id.dateBtn);// 버튼이 위로 와야함..뭐 이런 개같은
		layoutGraphView = (ViewGroup) findViewById(R.id.layoutGraphView);
		
		bluetoothSetting();
		getDateData();
		getDBData();
		setRadarGraph();
		layoutGraphView.invalidate();
		cSetting = new CameraSetting(AchievementActivity.this);
		
         dateBtn.setOnClickListener(new OnClickListener(){
        	 @Override
        	 public void onClick(View v){
        		 	if(v.isClickable()){
        		 			new DatePickerDialog(AchievementActivity.this, dateSetListener, thisYear, thisMonth, thisDay).show();
        		 	}
        	 }
         });
		
		
	}
	
	private void bluetoothSetting(){
		btSetting= new BluetoothSetting(AchievementActivity.this);		
		btSetting.initDeviceListDialog();
		btSetting.initProgressDialog();	
		
	}
	
	
	private void getDateData(){
		 calendar = new GregorianCalendar();
         thisYear = calendar.get(Calendar.YEAR);
         thisMonth = calendar.get(Calendar.MONTH);
         thisDay= calendar.get(Calendar.DAY_OF_MONTH);
         
         reMonth = thisMonth+1;
         if(reMonth<10){
        	 thisDate = Integer.toString(thisYear)+"-0"+Integer.toString(reMonth)+"-"+Integer.toString(thisDay);
         }else{
        	 thisDate = Integer.toString(thisYear)+"-"+Integer.toString(reMonth)+"-"+Integer.toString(thisDay);
         }
         
         dateBtn.setText(thisYear+" 년 " +reMonth+" 월 "+thisDay+" 일 ");
        
	}
	
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
             // TODO Auto-generated method stub
        	thisYear = year;
        	thisMonth=monthOfYear+1;
        	thisDay = dayOfMonth;
        	
        	if(thisMonth<10){
        		thisDate = Integer.toString(thisYear)+"-0"+Integer.toString(thisMonth)+"-"+Integer.toString(thisDay);
        	}else{
        		thisDate = Integer.toString(thisYear)+"-"+Integer.toString(thisMonth)+"-"+Integer.toString(thisDay);
        	}
        	
        	if(rgView !=null){
        		layoutGraphView.removeView(rgView);
        		rgView = null;
        	}        	
        	dateBtn.setText(thisYear+" 년 " +thisMonth+" 월 "+thisDay+" 일 ");
        	getDBData();
        	setRadarGraph();	
        }
    };

  
	private void getDBData(){
		dbSetting = new DBSetting(AchievementActivity.this);
		dbSetting.select();
		squatPercent=Float.valueOf(Double.toString(dbSetting.selectExerciseValue("squat", thisDate)));
		crunchPercent=Float.valueOf(Double.toString(dbSetting.selectExerciseValue("crunch",thisDate)));
		legRaisePercent = Float.valueOf(Double.toString(dbSetting.selectExerciseValue("legRaise", thisDate)));
		lungePercent = Float.valueOf(Double.toString(dbSetting.selectExerciseValue("lunge", thisDate)));
		todayPercent = Float.valueOf(Double.toString(dbSetting.selectTodayValue(thisDate)));
		monthPercent = Float.valueOf(Double.toString(dbSetting.selectMonthValue(thisYear,thisMonth)));
		//squatPercent=Integer.valueOf(stringValue).intValue();
		// 상체 하체 복근 전신 오늘 운동한 양
		
		
	}
	private void setRadarGraph() {
	
		RadarGraphVO vo = makeRadarGraphDefaultSetting();
		rgView = new RadarGraphView(this, vo);
		layoutGraphView.addView(rgView);
	}
	
	/**
	 * make simple line graph
	 * @return
	 */
	
	private RadarGraphVO makeRadarGraphDefaultSetting() {
		
		String[] legendArr 	= {"Cunch","Squat","Leg Raise","Lunge","Today","Month"};
		float[] graph1 		= {crunchPercent,squatPercent,legRaisePercent,lungePercent,todayPercent, monthPercent};

		List<RadarGraph> arrGraph 		= new ArrayList<RadarGraph>();		
		//arrGraph.add(new RadarGraph("exercise", 0xaa00ffff, graph1));
		arrGraph.add(new RadarGraph("exercise", 0xAA3333AA, graph1));
		RadarGraphVO vo = new RadarGraphVO(legendArr, arrGraph);
		return vo;
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
				cSetting.setBeforeAfterDialog();
				return true;
		
			case R.id.action_exercise:
				intent = new Intent(AchievementActivity.this, PreviewActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				finish();
				return true;
				
				
			case R.id.action_settings:
				intent = new Intent(AchievementActivity.this, SettingActivity.class);
				startActivity(intent);
				overridePendingTransition(0,0);
				finish();
				return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		SetImage setImage = new SetImage();
		if(resultCode == RESULT_OK)
		{
			MainActivity.MainAct.finish();
			PreviewActivity.isResume = true;
			//카메라로찍었을때
			if(requestCode == CameraSetting.TAKE_CAMERA) //1
			{
				//찍은 사진을 이미지뷰에 보여줌
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ CameraSetting.tempPicturePath)));
				Intent intentToMain = new Intent(this, MainActivity.class);
				intentToMain.putExtra("cameraSetting","camera");
				startActivity(intentToMain);
				finish();
			}
				
			//앨범에서 가져올 때
			else if(requestCode==CameraSetting.TAKE_GALLERY)//2
			{
				
				currImageURI=data.getData();
				path = cSetting.getRealPathFromURI(currImageURI);
				CameraSetting.tempPicturePath = path;
				//이미지뷰에 보여줌
				Intent intentToMain = new Intent(this, MainActivity.class);
				intentToMain.putExtra("cameraSetting","gallery");
				startActivity(intentToMain);
				finish();
				//setImage.setAlbumImageBackground(CameraSetting.tempPicturePath, iv);
				}
			}
		else
		{
			Log.e("camera return error","에러");
			return;
		}
	}
}
