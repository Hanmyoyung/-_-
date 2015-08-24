package com.HomeGym.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class ImageDBManager extends SQLiteOpenHelper{

	public ImageDBManager(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.i("ImageDBManager", "이미지 디비 메니저 생성자입니다.");
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	String sql = "CREATE TABLE ImgInfo("+
    				"id INTEGER PRIMARY KEY AUTOINCREMENT, "+
    				"type INTEGER, "+ //1:before/ 2: after  
    				"path String, "+
    				"date DATE);";
    	db.execSQL(sql);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	String sql = "drop table if exists ImgInfo";
    	db.execSQL(sql);
    	
    	onCreate(db);
    	
    }
}


