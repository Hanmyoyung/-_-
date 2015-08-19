package com.HomeGym.Activity;

import com.HomeGym.Bluetooth.BluetoothGetData;
import com.HomeGym.DB.DBSetting;
import com.example.homegym.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

 
public class FinishActivity extends Activity {
 
    private BluetoothGetData btData = BluetoothGetData.getInstance();
    private DBSetting dbSetting;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        
        dbSetting = new DBSetting(FinishActivity.this);
                        
        final Intent intent = new Intent(FinishActivity.this, MainActivity.class); 
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
 
            @Override
            public void run() {
                finish();// 3 ���� �̹����� �ݾƹ���
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        }, 3000);        
    }
	
	@Override
	protected void onDestroy(){	
		super.onDestroy();
		
		//if(btData.getCrunch()!= null){
		try{
			dbSetting.insertValues(btData.getCrunch());
			dbSetting.insertValues(btData.getSquat());
			dbSetting.insertValues(btData.getLunge());
			dbSetting.insertValues(btData.getLegRaise());
			dbSetting.select();
		}catch(Exception e){
			Log.i("����� ��������� �������� �ʾҾ��", "������ ���ϰ� ��� �ϴٴ�....");
		}
		//}
		
	}
}

