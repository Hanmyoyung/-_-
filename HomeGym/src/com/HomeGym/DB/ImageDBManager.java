package com.HomeGym.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class ImageDBManager extends SQLiteOpenHelper{

	public ImageDBManager(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	String sql = "CREATE TABLE ImgInfo("+
    				"id INTEGER PRIMARY KEY AUTOINCREMENT, "+
    				"type INTEGER , "+ //0:before/ 1: after  
    				"path String, "+
    				"date DATE);";
    	Log.i("에휴", "여기로 오긴 하는 겁니까");
    	db.execSQL(sql);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	String sql = "drop table if exists ImgInfo";
    	db.execSQL(sql);
    	
    	Log.i("핳핳핳핳핳", "디비가 업그레이드 됐습니다.");
    	onCreate(db);
    	
    }
}


