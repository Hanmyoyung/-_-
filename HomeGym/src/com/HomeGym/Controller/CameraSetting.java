package com.HomeGym.Controller;

import java.io.File;

import com.HomeGym.Activity.MainActivity;
import com.HomeGym.DB.ImgDBSetting;

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
	
	public static final int TAKE_GALLERY = 2222;
	public static final int TAKE_CAMERA = 1111;
	Context cameraContext;
	public static int flag, baflag;
	public String  Folder, Name;
	public static String tempPicturePath, btempPicturePath, atempPicturePath; //b는 before이미지 경로, a는 after이미지경로
	public String path;
	CameraSetting cSetting;
	Bitmap profileBitmap; 
	public String name = "HomeGym/";
	File file;
	Intent intent;
	ImgDBSetting imgSetting;
	MainActivity mainActivity = new MainActivity();
	StorageLocationSetting slSetting = new StorageLocationSetting();
	
	public CameraSetting(Context applicationContext){
		// TODO Auto-generated constructor stub
		 cameraContext= applicationContext;	
		 imgSetting = new ImgDBSetting(cameraContext);
		 Log.i("뭐야 이거", "카메라 세팅 새성자입니다.");
	}
	
	public void setBeforeAfterDialog(){
		Log.v("들어오냐", "setBeforeAfterDialog");
		AlertDialog.Builder builder = new AlertDialog.Builder(cameraContext);     // 여기서 this는 Activity의 this
		// 여기서 부터는 알림창의 속성 설정
		builder.setTitle("변경할 항목을 선택하세요")        // 제목 설정
		.setCancelable(true)        // 뒤로 버튼 클릭시 취소 가능 설정
		.setPositiveButton("Before", new DialogInterface.OnClickListener(){      
			public void onClick(DialogInterface dialog, int whichButton){
				baflag = 1;   
				setCameraDialog();
			}
		})
		.setNegativeButton("After", new DialogInterface.OnClickListener(){      
			public void onClick(DialogInterface dialog, int whichButton){
				baflag = 2;
				setCameraDialog();
			}
		});

		AlertDialog dialog = builder.create();    // 알림창 객체 생성

		dialog.show();    // 알림창 띄우기
	}
	
	public void setCameraDialog(){
		Log.v("들어오냐", "setCameraDialog");
		AlertDialog.Builder builder = new AlertDialog.Builder(cameraContext);     // 여기서 this는 Activity의 this
		// 여기서 부터는 알림창의 속성 설정
		builder.setTitle("사진을 가져올 위치를 선택하세요")        // 제목 설정
		.setCancelable(true)        // 뒤로 버튼 클릭시 취소 가능 설정
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

		AlertDialog dialog = builder.create();    // 알림창 객체 생성

		dialog.show();    // 알림창 띄우기
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
		/*----카메라 선택----*/
			if(flag == 1){ 
				Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				Camera camera = Camera.open();;
				camera.release();
				/*----사진저장경로 바꾸기----*/	
				Name=slSetting.createName(System.currentTimeMillis());
				tempPicturePath = Folder+Name;
				File DirPath =new File(Environment.getExternalStorageDirectory()+"/HomeGym/");
				if(!DirPath.exists()) DirPath.mkdirs();
				file = new File(DirPath, Name);
				tempPicturePath= file.getAbsolutePath();
				
				if(baflag==1){ //before변경
					btempPicturePath = tempPicturePath;
					imgSetting.insert(btempPicturePath,1);
					//imgSetting.select();
					
				}else{
					atempPicturePath = tempPicturePath;
					imgSetting.insert(atempPicturePath,2);
					//imgSetting.select();
				}
				
				Log.v("파일경로", tempPicturePath);
				intent1.putExtra( MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
				((Activity)cameraContext).startActivityForResult(intent1, TAKE_CAMERA);
				
			}
		/*----갤러리 선택----*/
		else if(CameraSetting.flag == 2){ 
			Intent intent2 = new Intent();
			intent2.setAction(Intent.ACTION_GET_CONTENT);
			intent2.setType("image/*");
			((Activity)cameraContext).startActivityForResult(intent2, TAKE_GALLERY);
			
		}

	}

	
}
