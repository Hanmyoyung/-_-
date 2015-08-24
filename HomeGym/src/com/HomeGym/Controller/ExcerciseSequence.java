package com.HomeGym.Controller;

import com.HomeGym.Activity.CrunchActivity;
import com.HomeGym.Activity.FinishActivity;
import com.HomeGym.Activity.LegraiseActivity;
import com.HomeGym.Activity.LungeActivity;
import com.HomeGym.Activity.SquatActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ExcerciseSequence {
	Intent intent;
	String next;
	Context context;
	
	public ExcerciseSequence() {
		// TODO Auto-generated constructor stub
	}

	public Intent nextExcercise(Context context, String string){
		
		if(string.equals("Squat")){
			intent = new Intent(context,SquatActivity.class);
			//context.startActivity(intent);
			
		}
		else if(string.equals("Crunch")){
			intent = new Intent(context,CrunchActivity.class);
			//context.startActivity(intent);
		}
		else if(string.equals("Lunge")){
			intent = new Intent(context,LungeActivity.class);
			//context.startActivity(intent);
		}
		else if(string.equals("Legraise")){
			intent = new Intent(context,LegraiseActivity.class);
			//context.startActivity(intent);
		}
		else if(string.equals("finish")){
			intent = new Intent(context,FinishActivity.class);
			//context.startActivity(intent);
		}
		return intent;
	}

}
