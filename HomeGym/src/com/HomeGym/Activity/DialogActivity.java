package com.HomeGym.Activity;

import com.HomeGym.Controller.Music;
import com.example.homegym.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.ContextThemeWrapper;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class DialogActivity extends Activity  {

   private Button dConfirm,dCancel;
   private AlertDialog alert;
   private static PowerManager.WakeLock wakeLock; 
   
   AlertDialog.Builder builder;
    AlertDialog alertDialog;
    Music music;


   
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      
      requestWindowFeature(Window.FEATURE_NO_TITLE);  

       setContentView(R.layout.activity_dialog);
       //isScreenOn(this);
       music = new Music(DialogActivity.this);
       music.play();
        
       /*
         final Window window = this.getWindow();
          window.setFlags(
              WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
              | WindowManager.LayoutParams.FLAG_FULLSCREEN
              | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
              | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
              WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
              | WindowManager.LayoutParams.FLAG_FULLSCREEN
              | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
              | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
       
          setContentView(R.layout.activity_dialog);
         */ 
         
         final Window window = this.getWindow();
          window.setFlags(
              WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
              | WindowManager.LayoutParams.FLAG_FULLSCREEN
              | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
              WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
              | WindowManager.LayoutParams.FLAG_FULLSCREEN
              | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
       //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  
         
         
       //wakeLock = ((PowerManager)getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK|PowerManager.ACQUIRE_CAUSES_WAKEUP, "tag");

       PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);         
       wakeLock = pm.newWakeLock(                
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK |                
                PowerManager.ACQUIRE_CAUSES_WAKEUP |                
                PowerManager.ON_AFTER_RELEASE, "운동해야합니다"); 
        
         
       wakeLock.acquire(); 
                
          AlertDialog.Builder alert_confirm = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light_Dialog ));
           alert_confirm.setMessage("운동할 시간입니당").setCancelable(false).setPositiveButton("운동할거야",
           new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   // 'YES'
                  music.stop();
                  Intent intent  = new Intent(DialogActivity.this, MainActivity.class);
                  startActivity(intent);
                  
               }
           }).setNegativeButton("싫어",
           new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   // 'No'
                  Toast toast = Toast.makeText(DialogActivity.this, "메롱", Toast.LENGTH_SHORT ); 
                     toast.show();
                     alert.show();

               return;
               }
           });
           alert = alert_confirm.create();
           
           alert.show();
           
   }
   
   @Override
   protected void onStart(){
      super.onStart();
      

   }
   
}