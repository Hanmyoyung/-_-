package com.HomeGym.DB;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.HomeGym.Bluetooth.BluetoothGetData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ImgDBSetting {

	private SQLiteDatabase db;
	private ImageDBManager helper;
	private Context dbContext;
	private BluetoothGetData btGet;
	
	/*------테이블 내용---------------------------
	   	String sql = "CREATE TABLE ImgInfo("+
    				"id INTEGER PRIMARY KEY AUTOINCREMENT, "+
    				"type INTEGER, "+ //1:before/ 2: after  
    				"path String, "+
    				"date DATE);";
	------------------------------------------*/
	
	public ImgDBSetting(Context context){
		dbContext = context;
		Log.i("ImgDBSetting", "디비 이미지 세팅 클래스 생성자입니다.");
		
		helper = new ImageDBManager(dbContext,"ImgInfo",null,2); // 버전 조심해야함!! 현재 버전이 1.0
		
	}
	
	
	public void insert(String imgPath, int type){
		db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		// set the format to sql date time
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = new Date();
	
		values.put("path", imgPath);
		values.put("date", dateFormat.format(date));
		values.put("type", type);
		
		db.insert("ImgInfo", null, values);
	}
	
	public void update(){
		db = helper.getWritableDatabase();	
		db.execSQL("UPDATE ImgInfo SET COLUMN_NAME = datetime('now')");
	}
	
	public void delete(String name){
		db = helper.getWritableDatabase();
		db.delete("ImgInfo", "path?", new String[]{name});
	}
	
	
	public String selectPatheValue(int thisType){
		db = helper.getReadableDatabase();
		//String sQuery = "SELECT percent FROM UserInfo WHERE state = 3 AND date = '"+today+"'";
		
		String sQuery = "SELECT path FROM ImgInfo WHERE id = (SELECT MAX(id) FROM ImgInfo WHERE type = "+thisType+")" ;			
		Cursor c = db.rawQuery(sQuery, null);		
		String path="";		
		if(c.moveToFirst()){
				do{
					path = c.getString(c.getColumnIndex("path"));
					Log.i("-------디비디비디비-------","-------path만 출력-------");
					Log.i("경로는요?",path);
					Log.i("-------디비디비디비-------","-------path만 출력-------");
				}while(c.moveToNext());
				
			}
	
		
		return path;
		
		
	}
	

	public void select(){
		db = helper.getReadableDatabase();
		
		String sQuery = "SELECT * FROM ImgInfo ";
		Cursor c = db.rawQuery(sQuery, null);
		
		while(c.moveToNext()){
			int id = c.getInt(c.getColumnIndex("id"));
			int type = c.getInt(c.getColumnIndex("type"));
			String path = c.getString(c.getColumnIndex("path"));
			
			Log.i("-------디비디비디비-------","-------전체선택-------");
			Log.i("id는요??",Integer.toString(id));
			Log.i("state는요??",Integer.toString(type));
			try{
				Log.i("경로는요?",path);
			}catch(Exception e){};
			Log.i("--------디비디비------","--------전체선택------");

		}
		
	}

}
