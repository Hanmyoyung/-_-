package com.HomeGym.DB;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DBSetting {
	
		SQLiteDatabase db;
		DBManager helper;
		Context dbContext;
		
		/*------���̺� ����---------------------------
		String sql = "CREATE TABLE UserInfo("+
				"state INTEGER, "+
				"date DATE, "+
				"exerciseSeq INTEGER, "+
				"exerciseTime INTEGER, "+
				"targetCount INTEGER, "+
				"doneCount INTEGER, "+
				"percent DOUBLE);";
		------------------------------------------*/
		
		public DBSetting(Context context){
			dbContext = context;
			helper = new DBManager(dbContext,"UserInfo",null,4); // ���� �����ؾ���!! ���� ������ 4.0
			
		}
		
		
		public void testMethod(){
			insert(1,123,50,25,10,10.0);
			//update();
			select();
			//helper.onUpgrade(db, 3, 4); // onUpgrade �޼ҵ� �θ������� ���� ���� �ٲ㼭 �ٿ��ְ� �ٽ� ���鶧�� DBManager�� �����̶� ����ȭ���Ѿ���
			//String sql = "drop table if exists UserInfo";
	    	//db.execSQL(sql);

			
		}
		
		
		public void insert(int state, int exerciseSeq, int exerciseTime,  int targetCount , int doneCount, double percent){
			db = helper.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			
			// set the format to sql date time
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
			Date date = new Date();
		
			values.put("state", state);
			values.put("date", dateFormat.format(date));
			values.put("exerciseSeq", exerciseSeq);
			values.put("exerciseTime", exerciseTime);
			values.put("targetCount", targetCount);
			values.put("doneCount", doneCount);
			values.put("percent", doneCount/targetCount);
			
			db.insert("UserInfo", null, values);
		}
		
		public void update(){
			db = helper.getWritableDatabase();	
			db.execSQL("UPDATE UserInfo SET COLUMN_NAME = datetime('now')");
		}
		
		public void delete(String name){
			db = helper.getWritableDatabase();
			db.delete("UserInfo", "exercise?", new String[]{name});
		}
		
		public void select(){
			db = helper.getReadableDatabase();
			Cursor c = db.query("UserInfo", null, null, null, null, null, null,null);

			
			while(c.moveToNext()){
				int state = c.getInt(c.getColumnIndex("state"));
				String date = c.getString(c.getColumnIndex("date"));
				int exerciseSeq = c.getInt(c.getColumnIndex("exerciseSeq"));
				int exerciseTime = c.getInt(c.getColumnIndex("exerciseTime"));
				int doneCount = c.getInt(c.getColumnIndex("doneCount"));
				int targetCount = c.getInt(c.getColumnIndex("targetCount"));
				double percent = c.getDouble(c.getColumnIndex("percent"));
				
				Log.i("--------------","--------------");
				Log.i("��¥ ��� ������ ���ڴ�",date);
				Log.i("� ����",Integer.toString(exerciseSeq));
				Log.i("doneCount",Integer.toString(doneCount));
				Log.i("�޼���",Double.toString(percent));
				Log.i("--------------","--------------");

			}
			
		}

}
