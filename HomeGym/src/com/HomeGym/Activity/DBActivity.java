package com.HomeGym.Activity;

import java.util.TimeZone;

import com.HomeGym.DB.DBManager;
import com.example.homegym.R;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class DBActivity extends Activity {

	SQLiteDatabase db;
	DBManager helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_db);
		
		helper = new DBManager(DBActivity.this,"UserInfo",null,1);
		//helper.onUpgrade(db, oldVersion, newVersion);
		
		insert(10,10,10,10.0);
		//update();
		select();
		
		//helper.onUpgrade(db, 2, 3);
		
		 
	}
	
	/*
		"date DATETIME, "+
		"exercise INTEGER, "+
		"doneCount INTEGER, "+
		"targetCount INTEGER, "+
		"percent DOUBLE);";
	*/
	
	public void insert(int exercise, int doneCount, int targetCount, double percent){
		db = helper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		
		values.put("exercise", exercise);
		values.put("doneCount", doneCount);
		values.put("targetCount", targetCount);
		values.put("percent", percent);
		
		db.insert("UserInfo", null, values);
	}
	
	//public void update(String name, int datetime){
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
		Cursor c = db.query("UserInfo", null, null, null, null, null, null);

		
		while(c.moveToNext()){
			int date = c.getInt(c.getColumnIndex("date"));
			int exercise = c.getInt(c.getColumnIndex("exercise"));
			int doneCount = c.getInt(c.getColumnIndex("doneCount"));
			int targetCount = c.getInt(c.getColumnIndex("targetCount"));
			double percent = c.getDouble(c.getColumnIndex("percent"));
			
			
			//java.util.Date utilDate = new java.util.Date(c.getDate("regdate").getTime());

			//java.util.Date utilDate = rs.getTimestamp("regdate");    //->java.sql.Timestamp가  Date을 상속하기 



			
			//String datetime = c.getString(c.getColumnIndex("datetime"));
			
			//Log.i("날짜 재발 됐으면 좋겠다",date);
			Log.i("운동",Integer.toString(exercise));
			Log.i("doneCount",Integer.toString(doneCount));
			//Log.i("datetime",datetime);

		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.db, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
