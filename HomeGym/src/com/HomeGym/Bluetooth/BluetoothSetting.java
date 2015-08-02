package com.HomeGym.Bluetooth;

import java.util.LinkedList;

import com.HomeGym.Bluetooth.BluetoothSerialClient.OnScanListener;

import android.R;
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
import android.widget.Toast;

public class BluetoothSetting {
	
	private Context btContext;
	private BluetoothSerialClient mClient;
	private BluetoothStreamingHandler mBTHandler;
	private AlertDialog mDeviceListDialog;
	private LinkedList<BluetoothDevice> mBluetoothDevices = new LinkedList<BluetoothDevice>();
	private ArrayAdapter<String> mDeviceArrayAdapter;
	private ProgressDialog mLoadingDialog;

	public BluetoothSetting(Context applicationContext,ArrayAdapter<String> arrayAdapter) {
		
		// TODO Auto-generated constructor stub
		btContext = applicationContext;
		mDeviceArrayAdapter = arrayAdapter;
		mClient = BluetoothSerialClient.getInstance();
		mBTHandler= new BTHandler();
		
		//setDialog();
		//initDeviceListDialog();
		//initProgressDialog();
				
	}
	
	private void setDialog(){
		boolean connect = mClient.isConnection();
		if (!connect) {
				mDeviceListDialog.show();
		} else {
				mBTHandler.close();
		}

	}


	private void initDeviceListDialog() {
		//mDeviceArrayAdapter = new ArrayAdapter<String>(btContext,R.layout.simple_list_item_1);
		Log.v("构具构具", "恐救登绰单1?");
		ListView listView = new ListView(btContext);
		listView.setAdapter(mDeviceArrayAdapter);
		Log.v("构具构具", "恐救登绰单?2");
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.v("构具构具", "恐救登绰单?3");
				String item =  (String) parent.getItemAtPosition(position); 
				for(BluetoothDevice device : mBluetoothDevices) {
					if(item.contains(device.getAddress())) {
						Log.v("构具构具", "恐救登绰单?4");
						connect(device);
						mDeviceListDialog.cancel();
					}
				}
			}
		});
		AlertDialog.Builder builder = new AlertDialog.Builder(btContext);
		builder.setTitle("Select bluetooth device");
		builder.setView(listView);
		Log.v("构具构具", "恐救登绰单6?");
		builder.setPositiveButton("Scan",new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int id) {
			  Log.v("构具构具", "快快快酒びし扼っけ;ちだじ矾;け呆だじ矾;だじ埾矾?");
			  scanDevices();
		  }
		 });
		mDeviceListDialog = builder.create();
		Log.v("构具构具", "恐救登绰单7?");
		mDeviceListDialog.setCanceledOnTouchOutside(false);
	}
	

	private void initProgressDialog() {
		 mLoadingDialog = new ProgressDialog(btContext);
		 mLoadingDialog.setCancelable(true);
	}
	
	
	private void connect(BluetoothDevice device) {
		//mLoadingDialog.setMessage("Connecting....");
		//mLoadingDialog.setCancelable(false);
		//mLoadingDialog.show();
		Log.v("构具构具", "恐救登绰单5?");
		BluetoothSerialClient btSet =  mClient;
		btSet.connect(btContext, device, mBTHandler);
	}
	
	private void scanDevices() {
		Log.v("构具构具", "胶牡 叼官捞胶 涝聪促?");
		BluetoothSerialClient btSet = mClient;
		btSet.scanDevices(btContext, new OnScanListener() {
			String message ="";
			@Override
			public void onStart() {
				Log.d("Test", "Scan Start.");
				mLoadingDialog.show();
				message = "Scanning....";
				mLoadingDialog.setMessage("Scanning....");
				mLoadingDialog.setCancelable(true);
				mLoadingDialog.setCanceledOnTouchOutside(false);				
				mLoadingDialog.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						BluetoothSerialClient btSet = mClient;
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
				Log.v("构具构具", "恐救登绰单8?");
			}
		});
	}
	
	private void addDeviceToArrayAdapter(BluetoothDevice device) {
		if(mBluetoothDevices.contains(device)) { 
			mBluetoothDevices.remove(device);
			mDeviceArrayAdapter.remove(device.getName() + "\n" + device.getAddress());
		}
			mBluetoothDevices.add(device);
			mDeviceArrayAdapter.add(device.getName() + "\n" + device.getAddress() );
			mDeviceArrayAdapter.notifyDataSetChanged();
		
	}




	
}





