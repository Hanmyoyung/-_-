package com.HomeGym.Bluetooth;

import com.HomeGym.Controller.ValueSetting;

import android.util.Log;

public class BluetoothGetData {
	
	private static BluetoothGetData btData;
	public String tempString;
	public String resultData = "";
	public String tempData = "";
	public String squatData;
	public String crunchData;
	public String lungeData;
	public String legRaiseData;
	
	private BluetoothGetData(){
		
	}
	
	public static BluetoothGetData getInstance(){
		if(btData==null){
			btData = new BluetoothGetData();
		}
		return btData;
	}
	
	public String getTemp(){
		return tempData;
	}
	
	public String getSquat(){
		return squatData;
	}
	
	public String getCrunch(){
		return crunchData;
	}
	
	public String getLunge(){
		return lungeData;
	}
	
	public String getLegRaise(){
		return legRaiseData;
	}
	

	public boolean isData(String getString){
		
		Log.v("초기값은요", getString);
		for(int i=0; i<getString.length(); i++){
			if(getString.charAt(i)=='\0'){
				return true;	
			}
		}
		return false;
	}

	
	public void getData(String getString){
		
		Log.v("초기값은요", getString);
		for(int i=0; i<getString.length(); i++){
			
			String temp = getString.substring(i,i+1);
			if(getString.charAt(i)!='\0'){
				Log.v("temp값은요", temp);
				resultData+=temp;
				
			}else{
				Log.v("널 만났어", resultData);
				setData();
				resultData = "";
				i=getString.length();
				break;
			}
		}
    }
	
	public void setData(){
			
		
			if(resultData.charAt(0)=='0'){
				//Log.v("이거 영 맞냐?", resultData.charAt(0));
				if(tempData!=null){
					tempData="";
				}
				tempData = resultData.substring(2);
				ValueSetting.temp = tempData; 
				//Log.v("온도 데이터 입니다", tempData);
			}else if(resultData.charAt(0)=='3'){
				//Log.v("스쿼트 횟수 데이터 입니다", "핳");
				if(squatData!=null){
					squatData="";
				}
				//squatData = Integer.valueOf(resultData.substring(2));
				squatData = resultData;
				//Log.v("스쿼트 데이터 입니다 입니다", String.valueOf(squatData));
			}else if(resultData.charAt(0)=='4'){
				//Log.v("크런치 횟수 데이터 입니다", "핳");
				if(crunchData!=null){
					crunchData="";
				}
				//crunchData = Integer.valueOf(resultData.substring(2));
				crunchData = resultData;
				//Log.v("크런치 데이터 입니다 입니다", String.valueOf(crunchData));
			}else if(resultData.charAt(0)=='5'){
				//Log.v("크런치 횟수 데이터 입니다", "핳");
				if(lungeData!=null){
					lungeData="";
				}
				//crunchData = Integer.valueOf(resultData.substring(2));
				lungeData = resultData;
				//Log.v("크런치 데이터 입니다 입니다", String.valueOf(crunchData));
			}else if(resultData.charAt(0)=='6'){
				//Log.v("크런치 횟수 데이터 입니다", "핳");
				if(legRaiseData!=null){
					legRaiseData="";
				}
				//crunchData = Integer.valueOf(resultData.substring(2));
				legRaiseData = resultData;
				//Log.v("크런치 데이터 입니다 입니다", String.valueOf(crunchData));
			}else{
				
			}
		
	}

}
