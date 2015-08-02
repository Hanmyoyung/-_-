package com.HomeGym.Activity;

import java.util.LinkedList;

import com.HomeGym.Bluetooth.BTHandler;
import com.HomeGym.Bluetooth.BluetoothSerialClient;
import com.HomeGym.Bluetooth.BluetoothSerialClient.OnScanListener;
import com.example.homegym.R;
import com.example.homegym.R.id;
import com.example.homegym.R.layout;
import com.example.homegym.R.menu;
import com.HomeGym.Bluetooth.BluetoothStreamingHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;

public class BTConnectActivity extends Activity {


	public ImageButton bluetoothSync;
	public ImageButton bluetooth;
	private BluetoothSerialClient mClient;
	private BluetoothStreamingHandler mBTHandler;
	private AlertDialog mDeviceListDialog;
	private LinkedList<BluetoothDevice> mBluetoothDevices = new LinkedList<BluetoothDevice>();
	private ArrayAdapter<String> mDeviceArrayAdapter;
	private ProgressDialog mLoadingDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_btconnect);
		
		//bluetoothSync= (ImageButton)findViewById(R.id.bluetoothsync);
		//bluetooth= (ImageButton)findViewById(R.id.bluetooth);
		
		mClient = BluetoothSerialClient.getInstance();
		mBTHandler= new BTHandler();
		getActionBar().setTitle("블루투스 확인");// 타이틀 바꾸기
		getActionBar().setDisplayShowHomeEnabled(false);// 아이콘 없애기
		
		initDeviceListDialog();
		initProgressDialog();
		
		bluetooth.setOnClickListener(new View.OnClickListener(){
			   public void onClick(View v){
				   boolean connect = mClient.isConnection();
						if (!connect) {
							mDeviceListDialog.show();
						} else {
							mBTHandler.close();
						}

			   }

			});

	}
	
	private void initDeviceListDialog() {
		mDeviceArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_device);
		ListView listView = new ListView(getApplicationContext());
		listView.setAdapter(mDeviceArrayAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String item =  (String) parent.getItemAtPosition(position); 
				for(BluetoothDevice device : mBluetoothDevices) {
					if(item.contains(device.getAddress())) {
						connect(device);
						mDeviceListDialog.cancel();
					}
				}
			}
		});
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select bluetooth device");
		builder.setView(listView);
		builder.setPositiveButton("Scan",
		 new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int id) {
			  scanDevices();
		  }
		 });
		mDeviceListDialog = builder.create();
		mDeviceListDialog.setCanceledOnTouchOutside(false);
	}
	

	private void initProgressDialog() {
		 mLoadingDialog = new ProgressDialog(this);
		 mLoadingDialog.setCancelable(true);
	}
	
	
	private void connect(BluetoothDevice device) {
		//mLoadingDialog.setMessage("Connecting....");
		//mLoadingDialog.setCancelable(false);
		//mLoadingDialog.show();
		BluetoothSerialClient btSet =  mClient;
		btSet.connect(getApplicationContext(), device, mBTHandler);
	}
	
	private void scanDevices() {
		BluetoothSerialClient btSet = mClient;
		btSet.scanDevices(getApplicationContext(), new OnScanListener() {
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
						btSet.cancelScan(getApplicationContext());
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
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
