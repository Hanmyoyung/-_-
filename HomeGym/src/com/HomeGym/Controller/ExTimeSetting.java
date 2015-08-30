package com.HomeGym.Controller;

import com.HomeGym.Bluetooth.BluetoothGetData;

import android.util.Log;

public class ExTimeSetting {
	
	public BluetoothGetData btGet = BluetoothGetData.getInstance();	
	int fillPercent;
	
	public int upperTime(){
		if(ValueSetting.focus.equals("상체")){		
			fillPercent = 1;		
		}
		else fillPercent = 5; 	
		
		return fillPercent;
	}
	
	public int lowerTime(){
		if(ValueSetting.focus.equals("하체")){		
			fillPercent = 1;		
		}
		else fillPercent = 5; 	
		return fillPercent;
		
	}
	
	public int stomachTime(){
		
		if(ValueSetting.focus.equals("복근")){		
			fillPercent = 1;		
		}
		else fillPercent = 5; 	
		
		return fillPercent;
		
	}
	
	public int restTime(){
		double temp;
		
		try{
			temp = Double.parseDouble(btGet.getTemp());
		}
		catch(Exception e){
			temp = 0.0;
		}
		if(temp <= 26){
			fillPercent = 10;
		}
		else fillPercent = 5;
		
		return fillPercent;
		
	}
	

}
