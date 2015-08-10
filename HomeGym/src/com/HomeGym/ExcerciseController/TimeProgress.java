package com.HomeGym.ExcerciseController;

import com.HomeGym.Activity.FinishActivity;
import com.HomeGym.Activity.RestActivity;
import com.HomeGym.Bluetooth.BluetoothSetting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

public class TimeProgress {
	
	private Handler hand = new Handler();
	private Thread TimeThread;
	private static final int PB_START = 0x1;
	public int ProgressStatus = 0;
	public static int count=0;
	ExcerciseSequence NextExcercise = new ExcerciseSequence();
	Activity activity;
	private Intent intent;
	public String bString;
	BluetoothSetting btSetting;
	
	public void timeProgress(final Context context, final String next, final ProgressBar pb, final int percent){
	final Intent intentToEx = NextExcercise.nextExcercise(context, next);	
	final Intent intentToRest = new Intent(context, RestActivity.class);
	
	if (percent == 25) intent = intentToEx;
	else{
		intent = intentToRest;
		intent.putExtra("next", next);
		if(next.equals("finish")){
            intent = new Intent(context, FinishActivity.class);
         }
	}
	
	TimeThread = new Thread(new Runnable(){
		@Override
		public void run(){
			while (true) { 
				if(ProgressStatus < 100){
				ProgressStatus += percent;
				}
				else if(ProgressStatus >= 100){
					Log.v("여기 언제들어오나요","빨리 들어오면 안되는데");
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
