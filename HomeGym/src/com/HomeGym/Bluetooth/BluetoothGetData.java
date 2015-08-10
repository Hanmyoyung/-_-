package com.HomeGym.Bluetooth;

import java.util.StringTokenizer;

import android.util.Log;

public class BluetoothGetData {
	
	public String tempString;
	String resultData = "";
	String tempData = "";
	public BluetoothGetData(){
		
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
				i=getString.length();
				break;
			}
		}
    }
	
	public void setData(){
			
			if(resultData.charAt(0)=='0'){
				//Log.v("이거 영 맞냐?", resultData.charAt(0));
				tempData = resultData.substring(2);
				
				Log.v("온도 데이터 입니다", tempData);
			}else if(resultData.charAt(0)=='3'){
				Log.v("스쿼트 횟수 데이터 입니다", "핳");
			}else if(resultData.charAt(0)=='4'){
				Log.v("크런치 횟수 데이터 입니다", "핳");
			}
		
	}

}
