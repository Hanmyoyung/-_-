package com.HomeGym.Controller;

import com.HomeGym.Activity.FinishActivity;
import com.HomeGym.Activity.RestActivity;
import com.HomeGym.Bluetooth.BluetoothGetData;
import com.HomeGym.Bluetooth.BluetoothSetting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

public class TimeProgress {
  
   private Handler hand = new Handler();
   private Thread TimeThread;
   private static final int PB_START = 0x1;
   public int ProgressStatus = 0;
   public static int count=0;
   ExcerciseSequence NextExcercise = new ExcerciseSequence();
   Activity activity;
   private Intent intent;
   private Context tContext;
   public String bString;
   private BluetoothSetting btSetting;
   private BluetoothGetData btGet;
   private ToastHandler handler;
   
   public void timeProgress(final Context context, final String Present, final String next, final ProgressBar pb, final int percent){
	   final Intent intentToEx = NextExcercise.nextExcercise(context, next);   
	   final Intent intentToRest = new Intent(context, RestActivity.class);
	   final String present = Present;
	   tContext = context;
   if (percent == 10 || percent == 5) intent = intentToEx;
   else{
      intent = intentToRest;
      intent.putExtra("next", next);
      if(next.equals("finish")){
            intent = new Intent(context, FinishActivity.class);
         }
   }
   
   handler = new ToastHandler(context);
   TimeThread = new Thread(new Runnable(){
      @Override
      public void run(){
         while (true) { 
            if(ProgressStatus < 100){
            	ProgressStatus += percent;
            	try{
            			btGet = BluetoothGetData.getInstance();	
            			if(present == "Squat"|| present =="Lunge"){
            
            					if (btGet.getDoNotExcerData() == null || btGet.getDoNotExcerData().length() == 0) {
                					Log.v("Squat,Lunge", "운동을 하고 있습니다.");
            					} else { 
            						// 값이 있는 경우 처리
            						handler.sendMessage(handler.obtainMessage(1));
            					}
            		
            			}else if(present == "Crunch"){
            				if (btGet.getDoNotExcerData() == null || btGet.getDoNotExcerData().length() == 0) {
            					Log.v("Crunch.", "운동을 하고 있습니다.");
            				} else { 
            		    // 값이 있는 경우 처리
            					handler.sendMessage(handler.obtainMessage(2));
            				}
            			}else if(present =="Legraise"){
            				if (btGet.getDoNotExcerData() == null || btGet.getDoNotExcerData().length() == 0) {
            					Log.v("Legraise.", "운동을 하고 있습니다.");
            				} else { 
            					// 값이 있는 경우 처리
            					handler.sendMessage(handler.obtainMessage(3));
            				}
            			}
            }catch(Exception e){
            	Log.e("Exception", "Time Progress 에러입니다.");}
            }
            else if(ProgressStatus >= 100){
               hand.removeCallbacks(TimeThread);
               TimeThread.interrupt();
               context.startActivity(intent);
               ((Activity) context).overridePendingTransition(0,0);
               ((Activity) context).finish();
               ((Activity) context).overridePendingTransition(0,0);
               btSetting= new BluetoothSetting((Activity)context);
               bString = "2";
               btSetting.sendStringData(bString);
               break;
               
            }
            hand.post(new Runnable(){
               @Override
               public void run(){
                  pb.setProgress(ProgressStatus);
               }
            });
            try{
               TimeThread.sleep(1000);
            }catch (InterruptedException e){
               e.printStackTrace();
            }
         }
      }
   });
   TimeThread.start();
   }
   
}


class ToastHandler extends Handler {

	public Context tContext;
	private BluetoothGetData btGet;
	private Toast t;
	public ToastHandler(Context context){
		tContext = context;
		btGet = BluetoothGetData.getInstance();
	}
    @Override

    public void handleMessage(Message msg) {

    	Handler handler = new Handler();
        switch (msg.what) {
        case 1:
        	t = Toast.makeText(tContext,btGet.getDoNotExcerData(), Toast.LENGTH_SHORT);
            t.show();
            
            handler.postDelayed(new Runnable() {
            	@Override
            	public void run() {
            		// TODO Auto-generated method stub
            		t.cancel();
            		btGet.setDoNotExcerData();
            	}
            }, 100);

            
            break;
            
        case 2:
        	t = Toast.makeText(tContext,"무릎에"+btGet.getDoNotExcerData(), Toast.LENGTH_SHORT);
            t.show();

            handler.postDelayed(new Runnable() {
            	@Override
            	public void run() {
            		// TODO Auto-generated method stub
            		t.cancel();
            		btGet.setDoNotExcerData();
            	}
            }, 100);

            
            break;
        case 3:
        	t = Toast.makeText(tContext,"바닥에"+btGet.getDoNotExcerData(), Toast.LENGTH_SHORT);
            t.show();

            handler.postDelayed(new Runnable() {
            	@Override
            	public void run() {
            		// TODO Auto-generated method stub
            		t.cancel();
            		btGet.setDoNotExcerData();
            	}
            }, 100);

            
            break;
        }
        super.handleMessage(msg);
    }
}




