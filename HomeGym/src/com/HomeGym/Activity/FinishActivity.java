package com.HomeGym.Activity;

import com.example.homegym.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
 
public class FinishActivity extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
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
}

