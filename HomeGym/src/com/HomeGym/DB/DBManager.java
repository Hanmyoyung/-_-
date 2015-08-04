package com.HomeGym.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DBManager extends SQLiteOpenHelper {
 
    public DBManager(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	String sql = "CREATE TABLE UserInfo("+
    				"date DATE DEFAULT (date('now')), "+
    				"exercise INTEGER, "+
    				"doneCount INTEGER, "+
    				"targetCount INTEGER, "+
    				"percent DOUBLE);";
    	db.execSQL(sql);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	String sql = "drop table if exists UserInfo";
    	db.execSQL(sql);
    	
    	onCreate(db);
    	
    }
}