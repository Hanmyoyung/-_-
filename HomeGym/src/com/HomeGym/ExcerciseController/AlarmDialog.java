package com.HomeGym.ExcerciseController;

import com.example.homegym.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AlarmDialog {

	Context aContext;
	Dialog dialog;
	AlertDialog.Builder builder;
	AlertDialog alertDialog;
	
	public AlarmDialog(Context context){
		aContext = context;
		dialog = new Dialog(aContext);
	}
	
	public void setDialog(){
		
		LayoutInflater inflater = (LayoutInflater)aContext.getSystemService(aContext.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.alarm_dialog, null);
			TextView text = (TextView)layout.findViewById(R.id.text);
			text.setText("Hello, this is a cutom dialog");
			//ImageView image = (ImageView)layout.findViewById( R.id.image);
			//image.setImageResource( R.drawable.android);
			builder = new AlertDialog.Builder(aContext);
			builder.setView(layout);
			alertDialog = builder.create();

	}
	


	
	
	
}
