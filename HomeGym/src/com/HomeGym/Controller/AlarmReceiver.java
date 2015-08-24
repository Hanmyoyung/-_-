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
      
     

   }
   

   
}
