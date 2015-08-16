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
	
	/*------���̺� ����---------------------------
	   	String sql = "CREATE TABLE AlarmInfo("+
    				"id INTEGER PRIMARY KEY AUTOINCREMENT, "+
    				"alarm String);";
	------------------------------------------*/
	
	public AlarmDBSetting(Context context){
		dbContext = context;
		Log.i("���� �̰�", "��� �̹��� ���� Ŭ���� �������Դϴ�.");	
		helper = new AlarmDBManager(dbContext,"AlarmInfo",null,1); // ���� �����ؾ���!! ���� ������ 1.0
		
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
		Log.v("����¿� �˶� �޾ƿͼ�  ����Ϸ��� �ؿ�", "�� �ȵ��� ��¥");		
		Cursor c = db.rawQuery(sQuery, null);		
		String alarmSet="";		
		if(c.moveToFirst()){
				do{
					alarmSet = c.getString(c.getColumnIndex("alarm"));
					Log.i("-------�������-------","-------alarm ���-------");
					Log.i("�˶� �ð�����?",alarmSet);
					Log.i("-------�������-------","-------alarm ���-------");
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
			
			Log.i("-------�������-------","-------�ٱܾ��-------");
			Log.i("id�¿�??",Integer.toString(id));
			Log.i("state�¿�??",Integer.toString(type));
			try{
				Log.i("��δ¿�?",path);
			}catch(Exception e){};
			Log.i("--------�����------","--------�ٱܾ��------");

		}
		
	}

}
