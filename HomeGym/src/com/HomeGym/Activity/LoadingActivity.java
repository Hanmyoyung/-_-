package com.HomeGym.Activity;

import com.example.homegym.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
 
public class LoadingActivity extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        final Intent intent = new Intent(LoadingActivity.this, MainActivity.class); 
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
 
            @Override
            public void run() {
                finish();// 3 초후 이미지를 닫아버림
                startActivity(intent);
            }
        }, 3000);        
    }
}

