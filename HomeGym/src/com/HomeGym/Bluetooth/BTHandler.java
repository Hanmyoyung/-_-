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
			Log.v("������ �Ǳ� �ϳ���", "�����Դϴ�");
		}
		
		@Override
		public void onDisconnected() {
			//mMenu.getItem(0).setTitle(R.string.action_connect);
			//mLoadingDialog.cancel();
			Log.v("������ �Ǳ� �ϳ���", "Messgae : Disconnected.\n");
			//addText("Messgae : Disconnected.\n");
		}
		
		
		
		public void onData(byte[] buffer, int length, BluetoothSerialClient mClient) { //�޽��� �о �����ִ� �Լ�
			Log.v("������ �Ǳ� �ϳ���", "ondata�Դϴ�");
			 
			if(length == 0) return;
			
			if(mmByteBuffer.position() + length >= mmByteBuffer.capacity()) {
					Log.v("������ �Ǳ� �ϳ���", "mmByteBuffer.position() + length�Դϴ�");
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
		public void onData(byte[] buffer, int length, BluetoothSerialClient mClient) { //�޽��� �о �����ִ� �Լ�
			Log.v("������ �Ǳ� �ϳ���", "ondata�Դϴ�");
			
			if(length == 0) {
				Log.v("������ �Ǳ� �ϳ���", "���̰� ���Դϴ� ���� ���� �ȵ�");
				System.out.println("���� �޽����� ����");
				return;
			}else{
				//cutByte(buffer,0,length);
			}
			
			if(mmByteBuffer.position() + length >= mmByteBuffer.capacity()) {
				Log.v("������ �Ǳ� �ϳ���", "����� ���;ߵȴٰ��Ĺ�");
				ByteBuffer newBuffer = ByteBuffer.allocate(mmByteBuffer.capacity() * 2); 
				newBuffer.put(mmByteBuffer.array(), 0,  mmByteBuffer.position());
				mmByteBuffer = newBuffer;
			} 
			mmByteBuffer.put(buffer, 0, length);
			if(buffer[length - 1] == '\0') {		
				String string = new String(mmByteBuffer.array(), 0, mmByteBuffer.position());
				Log.v("���� �޽����� �־�", string);
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
			Log.v("������ �Ǳ� �ϳ���", "Messgae : Connected.");
			return true;
		}


		

}
