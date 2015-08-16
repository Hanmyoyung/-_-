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
	private DBManager helper;
	private Context dbContext;
	private BluetoothGetData btGet;
	
	/*------���̺� ����---------------------------
	"CREATE TABLE ImgInfo("+
    				"id INTEGER PRIMARY KEY AUTOINCREMENT, "+
    				"path String, "+
    				"date DATE);";
	------------------------------------------*/
	
	public ImgDBSetting(Context context){
		dbContext = context;
		helper = new DBManager(dbContext,"ImgInfo",null,1); // ���� �����ؾ���!! ���� ������ 1.0
		
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
	
	
	public void selectPatheValue(int thisType){
		db = helper.getReadableDatabase();
		//String sQuery = "SELECT percent FROM UserInfo WHERE state = 3 AND date = '"+today+"'";
		
		String sQuery = "SELECT path FROM ImgInfo WHERE type = "+thisType+" AND id = (SELECT MAX(id) FROM ImgInfo)" ;
		
		Log.v("����¿� �н� �޾ƿͼ�  ����Ϸ��� �ؿ�", "�� �ȵ��� ��¥");
		
		Cursor c = db.rawQuery(sQuery, null);
		
		String path="";
		
		if(c.moveToFirst()){
				do{
					path = c.getString(c.getColumnIndex("path"));
					Log.i("-------�������-------","-------path�� ���-------");
					Log.i("��δ¿�?",path);
					Log.i("-------�������-------","-------path�� ���-------");
				}while(c.moveToNext());
				
		}
		
		//return percent;
		
		
	}
	

	
	public void select(){
		db = helper.getReadableDatabase();
		Cursor c = db.query("UserInfo", null,null, null, null, null, null, null);

		
		while(c.moveToNext()){
			int id = c.getInt(c.getColumnIndex("id"));
			int state = c.getInt(c.getColumnIndex("state"));
			String date = c.getString(c.getColumnIndex("date"));
			int exerciseSeq = c.getInt(c.getColumnIndex("exerciseSeq"));
			int exerciseTime = c.getInt(c.getColumnIndex("exerciseTime"));
			int doneCount = c.getInt(c.getColumnIndex("doneCount"));
			int targetCount = c.getInt(c.getColumnIndex("targetCount"));
			double percent = c.getDouble(c.getColumnIndex("percent"));
			
			Log.i("-------�������-------","-------���������-------");
			Log.i("id�¿�??",Integer.toString(id));
			Log.i("state�¿�??",Integer.toString(state));
			Log.i("��¥�¿�?",date);
			Log.i("� �����¿�?",Integer.toString(exerciseSeq));
			Log.i("�ش� � �ð�����?",Integer.toString(exerciseTime));
			Log.i("�ش� ���� Ƚ���¿�?",Integer.toString(doneCount));
			Log.i("�ش� ��ǥ Ƚ���¿�?",Integer.toString(targetCount));
			Log.i("�ش� �޼�������?",Double.toString(percent));
			Log.i("--------�����------","--------�����������------");

		}
		
	}

}
