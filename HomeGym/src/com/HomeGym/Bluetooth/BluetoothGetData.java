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
	public String doNotExcerData;

	
	private BluetoothGetData(){
		
	}
	
	public static BluetoothGetData getInstance(){
		if(btData==null){
			btData = new BluetoothGetData();
		}
		return btData;
	}
	
	public String getDoNotExcerData(){
		return doNotExcerData;
	}
	
	public void setDoNotExcerData(){
		doNotExcerData="";
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
				resultData = "";
				i=getString.length();
				break;
			}
		}
    }
	
	public void setData(){
			
		try{
			if(resultData.charAt(0)=='0'){
				if(tempData!=null){
					tempData="";
				}
				tempData = resultData.substring(2);
				ValueSetting.temp = tempData; 
			}else if(resultData.charAt(0)=='3'){
				if(squatData!=null){
					squatData="";
				}
				squatData = resultData;
			}else if(resultData.charAt(0)=='4'){
				if(crunchData!=null){
					crunchData="";
				}
				crunchData = resultData;
			}else if(resultData.charAt(0)=='5'){
				if(lungeData!=null){
					lungeData="";
				}
				lungeData = resultData;
				Log.e("���� ������ �������", lungeData);
			}else if(resultData.charAt(0)=='6'){
				if(legRaiseData!=null){
					legRaiseData="";
				}
				legRaiseData = resultData;
			}else if(resultData.charAt(0)=='7'){
				if(doNotExcerData!=null){
					doNotExcerData="";
				}
				doNotExcerData="���ݴ� ����������";
			}else if(resultData.charAt(0)=='8'){
				if(doNotExcerData!=null){
					doNotExcerData="";
				}
				doNotExcerData="���ݴ� �ö󰡼���";
			}else if(resultData.charAt(0)=='9'){
				if(doNotExcerData!=null){
					doNotExcerData="";
				}
				doNotExcerData="";
			}else if(resultData.charAt(0)=='1'){
				if(doNotExcerData!=null){
					doNotExcerData="";
				}
				doNotExcerData="���� �ʾҽ��ϴ�.";
				Log.e("���⿡���;��ϰŵ��",doNotExcerData);
			}
		}catch(Exception e){
			Log.e("�̻���", "�ƿ� ���� �ٿ�� ���� �ȵǴ°ǵ�...");
		}
	}

}
