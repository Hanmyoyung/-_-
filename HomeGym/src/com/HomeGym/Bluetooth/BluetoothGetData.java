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
		
		Log.v("�ʱⰪ����", getString);
		for(int i=0; i<getString.length(); i++){
			if(getString.charAt(i)=='\0'){
				return true;	
			}
		}
		return false;
	}

	
	public void getData(String getString){
		
		Log.v("�ʱⰪ����", getString);
		for(int i=0; i<getString.length(); i++){
			
			String temp = getString.substring(i,i+1);
			if(getString.charAt(i)!='\0'){
				Log.v("temp������", temp);
				resultData+=temp;
				
			}else{
				Log.v("�� ������", resultData);
				setData();
				i=getString.length();
				break;
			}
		}
    }
	
	public void setData(){
			
			if(resultData.charAt(0)=='0'){
				//Log.v("�̰� �� �³�?", resultData.charAt(0));
				tempData = resultData.substring(2);
				
				Log.v("�µ� ������ �Դϴ�", tempData);
			}else if(resultData.charAt(0)=='3'){
				Log.v("����Ʈ Ƚ�� ������ �Դϴ�", "�K");
			}else if(resultData.charAt(0)=='4'){
				Log.v("ũ��ġ Ƚ�� ������ �Դϴ�", "�K");
			}
		
	}

}
