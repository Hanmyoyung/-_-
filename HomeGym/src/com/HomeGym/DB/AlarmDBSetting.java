package com.HomeGym.DB;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.HomeGym.Bluetooth.BluetoothGetData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AlarmDBSetting {
	
	private SQLiteDatabase db;
	private AlarmDBManager helper;
	private Context dbContext;
	
	/*------테이블 내용---------------------------
	   	String sql = "CREATE TABLE AlarmInfo("+
    				"id INTEGER PRIMARY KEY AUTOINCREMENT, "+
    				"alarm String);";
	------------------------------------------*/
	
	public AlarmDBSetting(Context context){
		dbContext = context;
		Log.i("뭐야 이거", "디비 이미지 세팅 클래스 생성자입니다.");	
		helper = new AlarmDBManager(dbContext,"AlarmInfo",null,1); // 버전 조심해야함!! 현재 버전이 1.0
		
	}
	
	
	public void insert(String alarmset){
		db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
			
		values.put("alarm", alarmset);
	
		db.insert("AlarmInfo", null, values);
	}
	

	
	
	public String selectValue(){
		db = helper.getReadableDatabase();
		//String sQuery = "SELECT percent FROM UserInfo WHERE state = 3 AND date = '"+today+"'";
		
		String sQuery = "SELECT alarm FROM AlarmInfo WHERE id = (SELECT MAX(id) FROM AlarmInfo)" ;		
		Log.v("여기는요 알람 받아와서  출력하려고 해요", "왜 안들어가냐 진짜");		
		Cursor c = db.rawQuery(sQuery, null);		
		String alarmSet="";		
		if(c.moveToFirst()){
				do{
					alarmSet = c.getString(c.getColumnIndex("alarm"));
					Log.i("-------디비디비디비-------","-------alarm 출력-------");
					Log.i("알람 시간은요?",alarmSet);
					Log.i("-------디비디비디비-------","-------alarm 출력-------");
				}while(c.moveToNext());
				
			}
	
		
		return alarmSet;
		
		
	}
	

	public void select(){
		db = helper.getReadableDatabase();
		
		String sQuery = "SELECT * FROM ImgInfo ";
		Cursor c = db.rawQuery(sQuery, null);
		
		while(c.moveToNext()){
			int id = c.getInt(c.getColumnIndex("id"));
			int type = c.getInt(c.getColumnIndex("type"));
			String path = c.getString(c.getColumnIndex("path"));
			
			Log.i("-------디비디비디비-------","-------다긁어옴-------");
			Log.i("id는요??",Integer.toString(id));
			Log.i("state는요??",Integer.toString(type));
			try{
				Log.i("경로는요?",path);
			}catch(Exception e){};
			Log.i("--------디비디비------","--------다긁어옴------");

		}
		
	}

}
