package com.HomeGym.Bluetooth;

public abstract class BluetoothStreamingHandler {
	
		public abstract void onError(Exception e);
		public abstract boolean onConnected();
		public abstract void onDisconnected();
		public abstract void onData(byte[] buffer, int length, BluetoothSerialClient mClient);
		

		public final boolean close() {
			BluetoothSerialClient btSet = BluetoothSerialClient.getInstance();
			if(btSet != null) 
				return btSet.close();
			return false;
		}
		public final boolean write(byte[] buffer) {
			BluetoothSerialClient btSet = BluetoothSerialClient.getInstance();
			if(btSet != null) 
				return btSet.write(buffer);
			return false;
		}



}


