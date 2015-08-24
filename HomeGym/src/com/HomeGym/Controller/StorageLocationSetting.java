package com.HomeGym.Controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class StorageLocationSetting {

	public static Intent intent;
	String path;
	String dir = null;
	String Edir = null;
	String name = "/HomeGym";
	public static Uri uri; 

	
	public String getExternalMounts(){

		    String reg = "(?i).*vold.*(vfat|ntfs|exfat|fat32|ext3|ext4).*rw.*";
		    String s = "";
		    try {

		        final Process process = new ProcessBuilder().command("mount")
		        											.redirectErrorStream(true).start();
		        process.waitFor();
		        final InputStream is = process.getInputStream();
		        final byte[] buffer = new byte[1024];
		        while (is.read(buffer) != -1) {
		            s = s + new String(buffer);
		        }
		        is.close();
		    } catch (final Exception e) {
		        e.printStackTrace();
		    }


		    final String[] lines = s.split("\n");
		    for (String line : lines) {
		        if (!line.toLowerCase(Locale.US).contains("asec")) {
		            if (line.matches(reg)) {
		                String[] parts = line.split(" ");
		                for (String part : parts) {
		                    if (part.startsWith("/"))
		                        if (!part.toLowerCase(Locale.US).contains("vold"))
		                            path=part;
		                }
		            }
		        }
		    }
		    return path;
		}

	
	public String createName(long dateTaken){
		Date date= new Date(dateTaken);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
		return dateFormat.format(date)+".jpg";
	}
	
	public String createInternalFolder(){
		String dir=Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + name;
		//String dir = Environment.DIRECTORY_DCIM + name;
		File folder = new File(dir);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		uri = Uri.fromFile(folder);
		return dir;    		
	}
	
	public String createExternalFolder(){
		String Edir= getExternalMounts() + "/" + name;
		File Efolder = new File(Edir);
		Efolder.mkdirs();
		Uri.fromFile(Efolder);
		return Edir;

	}	
}

