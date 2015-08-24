package com.HomeGym.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class AlarmDBManager extends SQLiteOpenHelper {


    public AlarmDBManager(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	String sql = "CREATE TABLE AlarmInfo("+
    				"id INTEGER PRIMARY KEY AUTOINCREMENT, "+
    				"alarm String);";
    	
    	db.execSQL(sql);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	String sql = "drop table if exists AlarmInfo";
    	db.execSQL(sql);
    	
    	onCreate(db);
    	
    }
}
