package com.HomeGym.Bluetooth;

import java.nio.ByteBuffer;

import android.util.Log;

public class BTHandler extends BluetoothStreamingHandler{

		ByteBuffer mmByteBuffer = ByteBuffer.allocate(1024);
		
		@Override
		public void onError(Exception e) {
			//mLoadingDialog.cancel();
			//addText("Messgae : Connection error - " +  e.toString() + "\n");
			//mMenu.getItem(0).setTitle(R.string.action_connect);
			Log.v("실행이 되긴 하나요", "에러입니다");
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
			
			if(buffer[length] == '\0') {
					String string = new String(mmByteBuffer.array(), 0, mmByteBuffer.position());
					Log.v("HAHAHAWIJFWAIEFJAWLEFIJAWEJI", string);
					//addText(mClient.getConnectedDevice().getName() + " : " +
					//		new String(mmByteBuffer.array(), 0, mmByteBuffer.position()) + '\n'); 
					mmByteBuffer.clear();
				}
			}
		
		
		/*
		@Override
		public void onData(byte[] buffer, int length, BluetoothSerialClient mClient) { //메시지 읽어서 보여주는 함수
			Log.v("실행이 되긴 하나요", "ondata입니다");
			
			if(length == 0) {
				Log.v("실행이 되긴 하나요", "길이가 영입니다 여기 들어가면 안됨");
				System.out.println("들어온 메시지가 없어");
				return;
			}else{
				//cutByte(buffer,0,length);
			}
			
			if(mmByteBuffer.position() + length >= mmByteBuffer.capacity()) {
				Log.v("실행이 되긴 하나요", "여기로 들어와야된다고요ㅔ발");
				ByteBuffer newBuffer = ByteBuffer.allocate(mmByteBuffer.capacity() * 2); 
				newBuffer.put(mmByteBuffer.array(), 0,  mmByteBuffer.position());
				mmByteBuffer = newBuffer;
			} 
			mmByteBuffer.put(buffer, 0, length);
			if(buffer[length - 1] == '\0') {		
				String string = new String(mmByteBuffer.array(), 0, mmByteBuffer.position());
				Log.v("들어온 메시지가 있어", string);
				System.out.println(string);
				//addText(mClient.getConnectedDevice().getName() + " : " +
				//		string + '\n'); 
				mmByteBuffer.clear();
			}
		}
		*/
		
		@Override
		public boolean onConnected() {
			//addText("Messgae : Connected. " + mClient.getConnectedDevice().getName() + "\n");
			//mLoadingDialog.cancel();
			//mMenu.getItem(0).setTitle(R.string.action_disconnect);
			Log.v("실행이 되긴 하나요", "Messgae : Connected.");
			return true;
		}


		

}
