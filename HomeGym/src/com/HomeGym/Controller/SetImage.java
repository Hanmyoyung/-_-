package com.HomeGym.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

public class SetImage
{
	Drawable resize(String path)
	{
		Bitmap bmp;
		BitmapFactory.Options options2 = new BitmapFactory.Options();
		options2.inSampleSize = 4;
		bmp = BitmapFactory.decodeFile(path, options2);

		BitmapDrawable dbmp = new BitmapDrawable(bmp);
		return (Drawable)dbmp;
	}

	public void setCameraImageBackground(Bitmap bm, ImageView iv, String tempPicturePath)
	{
		BitmapDrawable bmd = new BitmapDrawable(bm);

		File copyFile = new File(CameraSetting.tempPicturePath);
		try{		
			/*copyFile.createNewFile();
			OutputStream out = null;
			out = new FileOutputStream(copyFile);
			bm.compress(CompressFormat.JPEG, 70, out);
			out.close();
*/
			if(copyFile.exists() && copyFile.length() > 0)
			{		
				if(bm.getWidth()>bm.getHeight()){
					Matrix matrix = new Matrix();
					matrix.postRotate(90);
					bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);		
				}

				BitmapDrawable dbmp = new BitmapDrawable(bm);
				//Drawable dr = resize(CameraSetting.tempPicturePath);
				Drawable dr = (Drawable)dbmp;
				iv.setBackgroundDrawable(dr);
				//Drawable dr = resize(CameraSetting.tempPicturePath);
				//iv.setBackgroundDrawable(dr);
				//Drawable dr = resize(tempPicturePath);
				//iv.setBackgroundDrawable(dr);
			}
			else
			{
				
			}
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void setCameraImageDrawable(Bitmap bm, ImageView iv, String tempPicturePath)
	{
		BitmapDrawable bmd = new BitmapDrawable(bm);
		File copyFile = new File(CameraSetting.tempPicturePath);

		try{
			copyFile.createNewFile();
			FileOutputStream out = null;
			out = new FileOutputStream(copyFile);
			bm.compress(CompressFormat.JPEG, 70, out);
			out.close();

			if(copyFile.exists() && copyFile.length() > 0)
			{
				if(bm.getWidth()>bm.getHeight()){
					Matrix matrix = new Matrix();
					matrix.postRotate(90);
					bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);		
				}

				BitmapDrawable dbmp = new BitmapDrawable(bm);
				//Drawable dr = resize(tempPicturePath);
				Drawable dr = (Drawable)dbmp;
				iv.setBackgroundDrawable(dr);
				//Drawable dr = resize(tempPicturePath);
				//iv.setBackgroundDrawable(dr);
			}
			else
			{
				
			}
			}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	} 

	public void setAlbumImageBackground(String path, ImageView iv)
	{
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		
		int fscale = options.outHeight;
		if(options.outWidth > options.outHeight)
		{
			fscale = options.outWidth;
		}

		Bitmap bmp;

		if(800<fscale)
		{
			fscale = fscale/800;
			BitmapFactory.Options options2 = new BitmapFactory.Options();
			options2.inSampleSize = fscale;
			bmp = BitmapFactory.decodeFile(path, options2);
		}
		else
		{
			bmp = BitmapFactory.decodeFile(path);
		}
		
		if(bmp.getWidth()>bmp.getHeight()){
			Matrix matrix = new Matrix();
			matrix.postRotate(90);
			bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);		
		}

		BitmapDrawable dbmp = new BitmapDrawable(bmp);
			Drawable dr = (Drawable)dbmp;
			iv.setBackgroundDrawable(dr);
			
	}

	public void setAlbumImageDrawable(String path, ImageView iv)
	{
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		
		int fscale = options.outHeight;
		
		if(options.outWidth > options.outHeight)
		{
			fscale = options.outWidth;
		}

		Bitmap bmp;

		if(800 < fscale)
		{
			fscale = fscale/800;
			BitmapFactory.Options options2 = new BitmapFactory.Options();
			options2.inSampleSize = fscale;
			bmp = BitmapFactory.decodeFile(path, options2);
		}
		else
		{
			bmp = BitmapFactory.decodeFile(path);
		}

		BitmapDrawable dbmp = new BitmapDrawable(bmp);
		Drawable dr = (Drawable)dbmp;
		iv.setImageDrawable(dr);
	}
}
