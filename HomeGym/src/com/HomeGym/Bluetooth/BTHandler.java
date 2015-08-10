package com.HomeGym.Bluetooth;

import java.nio.ByteBuffer;

import android.util.Log;

public class BTHandler extends BluetoothStreamingHandler{

		ByteBuffer mmByteBuffer = ByteBuffer.allocate(2048);
		String tempString= new String();
		BluetoothGetData get = new BluetoothGetData();
		//boolean isData;
		
		@Override
		public void onError(Exception e) {
			//mLoadingDialog.cancel();
			//addText("Messgae : Connection error - " +  e.toString() + "\n");
			//mMenu.getItem(0).setTitle(R.string.action_connect);
			Log.v("실행이 되긴 하나요", "에러입니다");
		}
		
		public void initData(boolean isData){
			if(isData ==true){
				get.getData(tempString);
				tempString = new String();
			}
		}
		
		@Override
		public void onDisconnected() {
			//mMenu.getItem(0).setTitle(R.string.action_connect);
			//mLoadingDialog.cancel();
			Log.v("실행이 되긴 하나요", "Messgae : Disconnected.\n");
			//addText("Messgae : Disconnected.\n");
		}
		
		
		
		public void onData(byte[] buffer, int length, BluetoothSerialClient mClient) { //메시지 읽어서 보여주는 함수
			Log.v("실행이 되긴 하나요", "ondata입니다");
			 
			if(length == 0) return;
			
			if(mmByteBuffer.position() + length >= mmByteBuffer.capacity()) {
					Log.v("실행이 되긴 하나요", "mmByteBuffer.position() + length입니다");
					ByteBuffer newBuffer = ByteBuffer.allocate(mmByteBuffer.capacity() * 2); 
					newBuffer.put(mmByteBuffer.array(), 0,  mmByteBuffer.position());
					mmByteBuffer = newBuffer;
			} 
			
			mmByteBuffer.put(buffer, 0, length);
			Log.v("장난함??length가 얼마인데?", Integer.toString(length));
			
			if(buffer[length] == '\0') {
					String string = new String(mmByteBuffer.array(), 0, mmByteBuffer.position());
					tempString +=string;
					initData(get.isData(tempString));
					Log.v("HAHAHAWIJFWAIEFJAWLEFIJAWEJI", tempString);
					Log.v("왜안되는건지 진짜 모르겠다아유",get.tempData);
					mmByteBuffer.clear();
				}
			}
		
		
		
		@Override
		public boolean onConnected() {
			//addText("Messgae : Connected. " + mClient.getConnectedDevice().getName() + "\n");
			//mLoadingDialog.cancel();
			//mMenu.getItem(0).setTitle(R.string.action_disconnect);
			Log.v("실행이 되긴 하나요", "Messgae : Connected.");
			return true;
		}


		

}
