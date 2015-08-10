package com.HomeGym.Bluetooth;

import java.util.LinkedList;

import com.HomeGym.Bluetooth.BluetoothSerialClient.OnScanListener;
import com.example.homegym.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BluetoothSetting {
	
	private Context btContext;
	private BluetoothStreamingHandler mBTHandler;
	private AlertDialog mDeviceListDialog;
	private LinkedList<BluetoothDevice> mBluetoothDevices = new LinkedList<BluetoothDevice>();
	private ArrayAdapter<String> mDeviceArrayAdapter;
	private ProgressDialog mLoadingDialog;
	static private BluetoothSetting bThis = null;
	private boolean isConnect;
	
	public BluetoothSetting(Context applicationContext){
		// TODO Auto-generated constructor stub
		btContext = applicationContext;
		mBTHandler= new BTHandler();
				
		
		
	}
	
	public static BluetoothSetting getInstance(Context applicationContext) {
		if(bThis == null) {
			bThis = new BluetoothSetting(applicationContext);
		}
		return bThis;
	}

	public void setDialog(){
		boolean connect = BluetoothSerialClient.getInstance().isConnection();
		if (!connect) {
				mDeviceListDialog.show();				
		} else {
				mBTHandler.close();
		}
	}

	public void sendStringData(String data) {

		data += '\0';
		byte[] buffer = data.getBytes();
		if(mBTHandler.write(buffer)) {
			Log.i("�Ǵ°Ŵ�", "���½��ϴ�");
		}
	}

	public void initDeviceListDialog() {
		mDeviceArrayAdapter = new ArrayAdapter<String>(btContext,R.layout.item_device);
		Log.v("���߹���", "�־ȵǴµ�1?");
		ListView listView = new ListView(btContext);
		listView.setAdapter(mDeviceArrayAdapter);
		Log.v("���߹���", "�־ȵǴµ�?2");
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.v("���߹���", "�־ȵǴµ�?3");
				String item =  (String) parent.getItemAtPosition(position); 
				for(BluetoothDevice device : mBluetoothDevices) {
					if(item.contains(device.getAddress())) {
						Log.v("���߹���", "�־ȵǴµ�?4");
						connect(device);
						mDeviceListDialog.cancel();
					}
				}
			}
		});
		AlertDialog.Builder builder = new AlertDialog.Builder(btContext);
		builder.setTitle("Select bluetooth device");
		builder.setView(listView);
		Log.v("���߹���", "�־ȵǴµ�6?");
		builder.setPositiveButton("Scan",new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int id) {
			  Log.v("���߹���", "����ƤӤ���ä�;��������;����������;�����Ϸ�?");
			  scanDevices();
		  }
		 });
		mDeviceListDialog = builder.create();
		Log.v("���߹���", "�־ȵǴµ�7?");
		mDeviceListDialog.setCanceledOnTouchOutside(false);
	}
	

	public void initProgressDialog() {
		 mLoadingDialog = new ProgressDialog(btContext);
		 mLoadingDialog.setCancelable(true);
	}
	
	
	public void connect(BluetoothDevice device) {
		mLoadingDialog.setMessage("Connecting....");
		mLoadingDialog.setCancelable(false);
		mLoadingDialog.show();
		Log.v("���߹���", "�־ȵǴµ�5?");
		BluetoothSerialClient btSet =  BluetoothSerialClient.getInstance();
		
		if(btSet.connect(btContext, device, mBTHandler)==true){
			mLoadingDialog.setMessage("���� �Ǿ����ϴ�");
			mLoadingDialog.setCancelable(false);
			mLoadingDialog.show();
		}else{
			mLoadingDialog.setMessage("������� �ʾҽ��ϴ�.");
			mLoadingDialog.setCancelable(false);
			mLoadingDialog.show();
		}
	}
	
	public void scanDevices() {
		Log.v("���߹���", "��ĵ ����̽� �Դϴ�?");
		BluetoothSerialClient btSet = BluetoothSerialClient.getInstance();
		btSet.scanDevices(btContext, new OnScanListener() {
			String message ="";
			@Override
			public void onStart() {
				Log.d("Test", "Scan Start.");
				mLoadingDialog.show();
				message = "Scanning....";
				mLoadingDialog.setMessage("Scanning....");
				mLoadingDialog.setCancelable(true);
				mLoadingDialog.setCanceledOnTouchOutside(true);				
				mLoadingDialog.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						BluetoothSerialClient btSet = BluetoothSerialClient.getInstance();
						btSet.cancelScan(btContext);
					}
				}); 
			}
			
			@Override
			public void onFoundDevice(BluetoothDevice bluetoothDevice) {
				addDeviceToArrayAdapter(bluetoothDevice);
				message += "\n" + bluetoothDevice.getName() + "\n" + bluetoothDevice.getAddress();
				mLoadingDialog.setMessage(message);
			}
			
			@Override
			public void onFinish() {
				Log.d("Test", "Scan finish.");
				message = "";
				mLoadingDialog.cancel();
				mLoadingDialog.setCancelable(false);
				mLoadingDialog.setOnCancelListener(null);
				mDeviceListDialog.show();
				Log.v("���߹���", "�־ȵǴµ�8?");
			}
		});
	}
	
	public void addDeviceToArrayAdapter(BluetoothDevice device) {
		if(mBluetoothDevices.contains(device)) { 
			mBluetoothDevices.remove(device);
			mDeviceArrayAdapter.remove(device.getName() + "\n" + device.getAddress());
		}
			mBluetoothDevices.add(device);
			mDeviceArrayAdapter.add(device.getName() + "\n" + device.getAddress() );
			mDeviceArrayAdapter.notifyDataSetChanged();
		
	}




	
}





