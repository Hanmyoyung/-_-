package com.HomeGym.Activity;

import com.example.homegym.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
 
public class LoadingActivity extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
         
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
 
            @Override
            public void run() {
                finish();       // 3 ���� �̹����� �ݾƹ���
            }
        }, 3000);
    }
}

