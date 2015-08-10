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
		
		/*------테이블 내용---------------------------
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
			helper = new DBManager(dbContext,"UserInfo",null,4); // 버전 조심해야함!! 현재 버전이 4.0
			
		}
		
		
		public void testMethod(){
			insert(1,123,50,25,10,10.0);
			//update();
			select();
			//helper.onUpgrade(db, 3, 4); // onUpgrade 메소드 부를때마다 옆에 버전 바꿔서 붙여주고 다시 만들때는 DBManager에 버전이랑 동기화시켜야함
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
				Log.i("날짜 재발 됐으면 좋겠다",date);
				Log.i("운동 순서",Integer.toString(exerciseSeq));
				Log.i("doneCount",Integer.toString(doneCount));
				Log.i("달성률",Double.toString(percent));
				Log.i("--------------","--------------");

			}
			
		}

}
