package com.HomeGym.Controller;

import java.io.File;

import com.HomeGym.Activity.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class CameraSetting {
	
	private static final int TAKE_GALLERY = 2222;
	private static final int TAKE_CAMERA = 1111;
	Context cameraContext;
	public static int flag;
	public String  Folder, Name;
	public static String tempPicturePath;
	public String path;
	CameraSetting cSetting;
	Bitmap profileBitmap;
	public String name = "HomeGym/";
	File file;
	Intent intent;
	MainActivity mainActivity = new MainActivity();
	StorageLocationSetting slSetting = new StorageLocationSetting();
	public CameraSetting(Context applicationContext){
		// TODO Auto-generated constructor stub
		 cameraContext= applicationContext;					
	}
	
	public void setCameraDialog(){
		Log.v("������", "setCameraDialog");
		AlertDialog.Builder builder = new AlertDialog.Builder(cameraContext);     // ���⼭ this�� Activity�� this
		// ���⼭ ���ʹ� �˸�â�� �Ӽ� ����
		builder.setTitle("������ ������ ��ġ�� �����ϼ���")        // ���� ����
		.setCancelable(true)        // �ڷ� ��ư Ŭ���� ��� ���� ����
		.setPositiveButton("Camera", new DialogInterface.OnClickListener(){      
			public void onClick(DialogInterface dialog, int whichButton){
				flag = 1;   
				//((MainActivity)cameraContext).selectCamera();
				selectCamera();
				//moveIntent();
			}
		})
		.setNegativeButton("Gallery", new DialogInterface.OnClickListener(){      
			public void onClick(DialogInterface dialog, int whichButton){
				flag = 2;
				//((MainActivity)cameraContext).selectCamera();
				selectCamera();
				//moveIntent();
			}
		});

		AlertDialog dialog = builder.create();    // �˸�â ��ü ����

		dialog.show();    // �˸�â ����
	}
	
	public String getRealPathFromURI(Uri contentURI) {
	    String result;
	    Cursor cursor = cameraContext.getContentResolver().query(contentURI, null, null, null, null);
	    if (cursor == null) { 
	        result = contentURI.getPath();
	    } else { 
	        cursor.moveToFirst(); 
	        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
	        result = cursor.getString(idx);
	        cursor.close();
	    }
	    return result;
	}
	
	public void selectCamera(){
		/*----ī�޶� ����----*/
		if(flag == 1){ 
			Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			Camera camera = Camera.open();;
            camera.release();
			/*----���������� �ٲٱ�----*/	
			Name=slSetting.createName(System.currentTimeMillis());
			CameraSetting.tempPicturePath = Folder+Name;
			File DirPath =new File(Environment.getExternalStorageDirectory()+"/HomeGym/");
			if(!DirPath.exists()) DirPath.mkdirs();
			file = new File(DirPath, Name);
			CameraSetting.tempPicturePath= file.getAbsolutePath(); 
            Log.v("���ϰ��", CameraSetting.tempPicturePath);
			intent1.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			((Activity)cameraContext).startActivityForResult(intent1, TAKE_CAMERA);
			
		}
		/*----������ ����----*/
		else if(CameraSetting.flag == 2){ 
			Intent intent2 = new Intent();
			intent2.setAction(Intent.ACTION_GET_CONTENT);
			intent2.setType("image/*");
			((Activity)cameraContext).startActivityForResult(intent2, TAKE_GALLERY);
			
		}

	}

	
}
