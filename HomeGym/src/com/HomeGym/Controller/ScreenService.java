package com.HomeGym.Controller;

import com.HomeGym.Activity.DialogActivity;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class ScreenService extends Service {


	private AlarmReceiver mReceiver = new AlarmReceiver();
	private String MESSAGE = "A";

	@Override

	public IBinder onBind(Intent intent) {

		return null;

	}

	@Override

	public void onCreate() {
		super.onCreate();

		Intent i = new Intent(this, DialogActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startActivity(i);	

	}




	@Override

	public int onStartCommand(Intent intent, int flags, int startId){

		super.onStartCommand(intent, flags, startId);
		
		if(intent != null){
			if(intent.getAction()==null){
				if(mReceiver==null){
					mReceiver = new AlarmReceiver();					
					IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
					registerReceiver(mReceiver, filter);
					Log.i("SCreenService", "onSTartCommand");
				}
			}
		}
		return START_REDELIVER_INTENT;

	}

	

	@Override

	public void onDestroy(){		 	

		super.onDestroy();

		if(mReceiver != null){

			unregisterReceiver(mReceiver);

		}

	}

}

