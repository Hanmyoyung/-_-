package com.HomeGym.Controller;



import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
   
   MediaPlayer player;
   private NotificationManager notiManager;
   private Context aContext;
   private static PowerManager.WakeLock sCpuWakeLock;    
   

 
   @Override
   public void onReceive(Context context, Intent intent) {
      
      aContext = context;
      Log.i("잉... 여기까지 안오는것같은데", "개슬픔");
      Log.i("잉...", intent.getAction());
      /*
      if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {   
         Intent i = new Intent(context, AlarmAlert.class);
         i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         context.startActivity(i);         

      }
      */
      if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
         Intent i = new Intent();
         //i.setClassName(context,"com.HomeGym.Activity.AlarmAlert");
         i.setClassName(context,"com.HomeGym.Activity.DialogActivity");
         i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         context.startActivity(i);
      }
      
      
      /*
      PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);         
        sCpuWakeLock = pm.newWakeLock(                
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK |                
                PowerManager.ACQUIRE_CAUSES_WAKEUP |                
                PowerManager.ON_AFTER_RELEASE, "운동해야합니다");        
         
        sCpuWakeLock.acquire();    
       */
      /*
        try {   

               //Intent mIntent = new Intent(context, MainActivity.class);    // SnoozeActivity는 알람 끄고 스누즈 시키는 화면
              Intent mIntent = new Intent(context, AlarmAlert.class);    // SnoozeActivity는 알람 끄고 스누즈 시키는 화면
               mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(mIntent);
              
        } catch (Exception e) {
               Log.e("ALARM", Log.getStackTraceString(e));
        }
       
      /*
        try {

         Intent intentAlarm = new Intent(context,AlarmAlert.class);

         PendingIntent pIntent = PendingIntent.getActivity(context, 0, intentAlarm, PendingIntent.FLAG_ONE_SHOT);

         pIntent.send();

      } catch (CanceledException e) {

         // TODO Auto-generated catch block

         e.printStackTrace();

      }

      */

   }
   

   
}
