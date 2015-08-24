package com.HomeGym.DB;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.HomeGym.Bluetooth.BluetoothGetData;
import com.HomeGym.Controller.ValueSetting;

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
		
		/*------테이블 내용---------------------------
		String sql = "CREATE TABLE UserInfo("+
				"id INTEGER PRIMARY KEY AUTOINCREMENT, "+
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
			helper = new DBManager(dbContext,"UserExcerInfo",null,1); // 버전 조심해야함!! 현재 버전이 1.0
			
		}
		
		
		public void insertValues(String inputData ){
			btGet = BluetoothGetData.getInstance();
			int state;
			int exerciseSeq;
			int exerciseTime;  
			int targetCount; 
			int doneCount;
			double percent;
			
			if(inputData.charAt(0)=='3'){// 스쿼트 횟수를 담은 데이터라면
				state = 3;
				exerciseSeq =1234;
				exerciseTime = ValueSetting.fullSquatTime;// 설정값에 따라서 바뀌어야됨
				targetCount=ValueSetting.fullSquat;
				doneCount = Integer.valueOf(inputData.substring(2));
				percent = (Double.valueOf(doneCount)/Double.valueOf(targetCount))*100;
				
				insert(state,exerciseSeq,exerciseTime,targetCount ,doneCount,percent);
							
			}else if(inputData.charAt(0)=='5'){//크런치 횟수를 답은 데이터라면
				state = 5;
				exerciseSeq =1234;
				exerciseTime = ValueSetting.fullLungeTime;// 설정값에 따라서 바뀌어야됨
				targetCount=ValueSetting.fullLunge;
				doneCount = Integer.valueOf(inputData.substring(2));
				percent = (Double.valueOf(doneCount)/Double.valueOf(targetCount))*100;
				
				insert(state,exerciseSeq,exerciseTime,targetCount ,doneCount,percent);
				
			}else if(inputData.charAt(0)=='6'){//크런치 횟수를 답은 데이터라면
				state = 6;
				exerciseSeq =1234;
				exerciseTime = ValueSetting.fullLegRaiseTime;// 설정값에 따라서 바뀌어야됨
				targetCount=ValueSetting.fullLegRaise;
				doneCount = Integer.valueOf(inputData.substring(2));
				percent = (Double.valueOf(doneCount)/Double.valueOf(targetCount))*100;
				
				insert(state,exerciseSeq,exerciseTime,targetCount ,doneCount,percent);
				
			}else if(inputData.charAt(0)=='4'){//크런치 횟수를 답은 데이터라면
				state = 4;
				exerciseSeq =1234;
				exerciseTime = ValueSetting.fullCrunchTime;// 설정값에 따라서 바뀌어야됨
				targetCount=ValueSetting.fullCrunch;
				doneCount = Integer.valueOf(inputData.substring(2));
				percent = (Double.valueOf(doneCount)/Double.valueOf(targetCount))*100;
				
				insert(state,exerciseSeq,exerciseTime,targetCount ,doneCount,percent);
				
			}else{
				
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
			}else if(exerciseString.equals("lunge")){
				state = 5;
			}else if(exerciseString.equals("legRaise")){
				state = 6;
			}
			
			String sQuery = "SELECT percent FROM UserInfo WHERE id = (SELECT MAX(id) FROM UserInfo WHERE date = '"+date+"' AND state = "+state+")";
			
			Cursor c = db.rawQuery(sQuery, null);
			double percent = 0;
			
			if(c.moveToFirst()){
				do{
					percent = c.getDouble(c.getColumnIndex("percent"));
				}while(c.moveToNext());					
			}			
			return percent;			
		}
		
		
		public double selectTodayValue(String thisDate){
			db = helper.getReadableDatabase();
			String date = thisDate;
			
			
			String sQuery = "SELECT AVG(percent) FROM UserInfo GROUP BY date HAVING date = '"+date+"'";
			
			Cursor c = db.rawQuery(sQuery, null);
			double percent = 0;
			
	
			if(c.moveToLast()){
					percent =c.getDouble(0);
			}
			return percent;
			
			
		}

		
		public double selectMonthValue(int thisYear, int thisMonth){
			db = helper.getReadableDatabase();
			
			String date;
			double percent = 0;
			
			if(thisMonth<10){
        		date = Integer.toString(thisYear)+"-0"+Integer.toString(thisMonth)+"-01";
        	}else{
        		date = Integer.toString(thisYear)+"-"+Integer.toString(thisMonth)+"-01";
        	}
						
			String sQuery = "SELECT AVG(percent) FROM UserInfo GROUP BY date HAVING date > '"+date+"'";					
			Cursor c = db.rawQuery(sQuery, null);
								
			if(c.moveToLast()){
				percent =c.getDouble(0);
			}
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
				
				Log.i("-------디비디비디비-------","-------사용자정보디비-------");
				Log.i("id는요??",Integer.toString(id));
				Log.i("state는요??",Integer.toString(state));
				Log.i("날짜는요?",date);
				Log.i("운동 순서는요?",Integer.toString(exerciseSeq));
				Log.i("해당 운동 시간은요?",Integer.toString(exerciseTime));
				Log.i("해당운동 수행 횟수는요?",Integer.toString(doneCount));
				Log.i("해당운동 목표 횟수는요?",Integer.toString(targetCount));
				Log.i("해당운동 달성률은요?",Double.toString(percent));
				Log.i("--------디비디비------","--------사용자정보디비------");

			}
			
		}

}
