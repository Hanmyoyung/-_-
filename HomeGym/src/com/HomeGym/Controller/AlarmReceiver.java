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
      Log.i("��... ������� �ȿ��°Ͱ�����", "������");
      Log.i("��...", intent.getAction());
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
                PowerManager.ON_AFTER_RELEASE, "��ؾ��մϴ�");        
         
        sCpuWakeLock.acquire();    
       */
      /*
        try {   

               //Intent mIntent = new Intent(context, MainActivity.class);    // SnoozeActivity�� �˶� ���� ������ ��Ű�� ȭ��
              Intent mIntent = new Intent(context, AlarmAlert.class);    // SnoozeActivity�� �˶� ���� ������ ��Ű�� ȭ��
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
