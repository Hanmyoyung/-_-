package com.HomeGym.DB;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.HomeGym.Bluetooth.BluetoothGetData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DBSetting {
	
		private SQLiteDatabase db;
		private DBManager helper;
		private Context dbContext;
		private BluetoothGetData btGet;
		
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
			helper = new DBManager(dbContext,"UserData",null,1); // ���� �����ؾ���!! ���� ������ 4.0
			
		}
		
		
		public void insertValues(String inputData ){
			btGet = BluetoothGetData.getInstance();
			int state;
			int exerciseSeq;
			int exerciseTime;  
			int targetCount=25; 
			int doneCount;
			double percent;
			
			if(inputData.charAt(0)=='3'){// ����Ʈ Ƚ���� ���� �����Ͷ��
				state = 3;
				exerciseSeq =1234;
				exerciseTime = 50;// �������� ���� �ٲ��ߵ�
				targetCount=25;
				doneCount = Integer.valueOf(inputData.substring(2));
				percent = (Double.valueOf(doneCount)/Double.valueOf(targetCount))*100;
				
				insert(state,exerciseSeq,exerciseTime,targetCount ,doneCount,percent);
							
			}else if(inputData.charAt(0)=='4'){//ũ��ġ Ƚ���� ���� �����Ͷ��
				state = 4;
				exerciseSeq =1234;
				exerciseTime = 50;// �������� ���� �ٲ��ߵ�
				targetCount=25;
				doneCount = Integer.valueOf(inputData.substring(2));
				percent = (Double.valueOf(doneCount)/Double.valueOf(targetCount))*100;
				
				insert(state,exerciseSeq,exerciseTime,targetCount ,doneCount,percent);
				
			}
			
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
			values.put("percent", percent);
			
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
		
		
		public double selectExerciseValue(String exerciseString, String thisDate){
			db = helper.getReadableDatabase();
			int state = 0;
			String date = thisDate;
			
			if(exerciseString.equals("squat")){
				state = 3;
			}else if(exerciseString.equals("crunch")){
				state = 4;
			}
			
			//String sQuery = "SELECT percent FROM UserInfo WHERE state = 3 AND date = '"+today+"'";
			String sQuery = "SELECT percent FROM UserInfo WHERE state = "+state+" AND date = '"+date+"'";
			
			Log.v("����¿������ε������� �޾ƿͼ� �޼��� ����Ϸ��� �ؿ�", "�� �ȵ��� ��¥");
			
			Cursor c = db.rawQuery(sQuery, null);
			double percent = 0;
			
			if(c.moveToFirst()){
					do{
						percent = c.getDouble(c.getColumnIndex("percent"));
					}while(c.moveToNext());
					
			}
			
			return percent;
			
			
		}
		
		
		public double selectTotalValue(String thisDate){
			db = helper.getReadableDatabase();
			String date = thisDate;
			
			
			String sQuery = "SELECT percent FROM UserInfo WHERE date = '"+date+"'";
			
			Log.v("����¿������ε������� �޾ƿͼ� �޼��� ����Ϸ��� �ؿ�", "�� �ȵ��� ��¥");
			
			Cursor c = db.rawQuery(sQuery, null);
			double percent = 0;
			
			if(c.moveToFirst()){
					do{
						percent += c.getDouble(c.getColumnIndex("percent"));
					}while(c.moveToNext());
					
			}
			
			percent = percent/2;
			return percent;
			
			
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
