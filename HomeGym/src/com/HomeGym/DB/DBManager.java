package com.HomeGym.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DBManager extends SQLiteOpenHelper {
 
    public DBManager(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	String sql = "CREATE TABLE UserInfo("+
    				"id INTEGER PRIMARY KEY AUTOINCREMENT, "+
    				"state INTEGER, "+
    				"date DATE, "+
    				"exerciseSeq INTEGER, "+
    				"exerciseTime INTEGER, "+
    				"targetCount INTEGER, "+
    				"doneCount INTEGER, "+
    				"percent DOUBLE);";
    	Log.i("����", "����� ���� �ϴ� �̴ϱ�");
    	db.execSQL(sql);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	String sql = "drop table if exists UserInfo";
    	db.execSQL(sql);
    	
    	Log.i("�K�K�K�K�K", "��� ���׷��̵� �ƽ��ϴ�.");
    	onCreate(db);
    	
    }
}
