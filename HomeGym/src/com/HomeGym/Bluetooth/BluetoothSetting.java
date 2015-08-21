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
   public static boolean isConnected;
   
   public BluetoothSetting(Context applicationContext){
      // TODO Auto-generated constructor stub
      btContext = applicationContext;
      mBTHandler= new BTHandler();   
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
         Log.i("되는거니", "보냈습니당");
      }
   }

   public void initDeviceListDialog() {
      mDeviceArrayAdapter = new ArrayAdapter<String>(btContext,R.layout.item_device);
      Log.v("뭐야뭐야", "왜안되는데1?");
      ListView listView = new ListView(btContext);
      listView.setAdapter(mDeviceArrayAdapter);
      Log.v("뭐야뭐야", "왜안되는데?2");
      listView.setOnItemClickListener(new OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.v("뭐야뭐야", "왜안되는데?3");
            String item =  (String) parent.getItemAtPosition(position); 
            for(BluetoothDevice device : mBluetoothDevices) {
               if(item.contains(device.getAddress())) {
                  Log.v("뭐야뭐야", "왜안되는데?4");
                  connect(device);
                  mDeviceListDialog.cancel();
               }
            }
         }
      });
      AlertDialog.Builder builder = new AlertDialog.Builder(btContext);
      builder.setTitle("연결할 블루투스 장치를 선택하세요");
      builder.setView(listView);
      Log.v("뭐야뭐야", "왜안되는데6?");
      builder.setPositiveButton("Scan",new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {
           Log.v("뭐야뭐야", "우우우아ㅣㅇ라ㅓㅁ;ㅑㅐㅈ러;ㅁ댜ㅐㅈ러;ㅐㅈ댬러?");
           scanDevices();
        }
       });
      mDeviceListDialog = builder.create();
      Log.v("뭐야뭐야", "왜안되는데7?");
      mDeviceListDialog.setCanceledOnTouchOutside(false);
   }
   

   public void initProgressDialog() {
       mLoadingDialog = new ProgressDialog(btContext);
       mLoadingDialog.setCancelable(true);
   }
   
   
   public void connect(BluetoothDevice device) {
      //mLoadingDialog.setMessage("Connecting....");
      //mLoadingDialog.setCancelable(false);
      //mLoadingDialog.show();
      Log.v("뭐야뭐야", "왜안되는데5?");
      BluetoothSerialClient btSet =  BluetoothSerialClient.getInstance();
      
      if(btSet.connect(btContext, device, mBTHandler)==true){
         isConnected = true;
         alertDialog(isConnected);
         String sString = "0";// 온도 받아오기
         sendStringData(sString);
         
      }else{
         isConnected = false;
         alertDialog(isConnected);
      }
   }
   
   public void alertDialog(boolean isConnected){
      AlertDialog.Builder builder = new AlertDialog.Builder(btContext);     // 여기서 this는 Activity의 this

      // 여기서 부터는 알림창의 속성 설정
      if(isConnected==true){
      builder.setTitle("Connecting...")        // 제목 설정
      .setMessage("블루투가 연결되었습니당")        // 메세지 설정
      .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정

      .setPositiveButton("확인", new DialogInterface.OnClickListener(){       

       // 확인 버튼 클릭시 설정
         public void onClick(DialogInterface dialog, int whichButton){
            dialog.cancel();
         }
      });

      AlertDialog dialog = builder.create();    // 알림창 객체 생성

      dialog.show();    // 알림창 띄우기
      }else{
         builder.setTitle("Connecting...")        // 제목 설정
         .setMessage("블루투가 연결되었습니당")        // 메세지 설정
         .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정

         .setPositiveButton("확인", new DialogInterface.OnClickListener(){       

          // 확인 버튼 클릭시 설정
            public void onClick(DialogInterface dialog, int whichButton){
               dialog.cancel();
            }
         });


         AlertDialog dialog = builder.create();    // 알림창 객체 생성

         dialog.show();    // 알림창 띄우기
         }      
   }
   
   public void scanDevices() {
      Log.v("뭐야뭐야", "스캔 디바이스 입니다?");
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
            Log.v("뭐야뭐야", "왜안되는데8?");
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




