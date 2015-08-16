package com.HomeGym.Controller;

import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

public class Music {
	
	Context mContext;
	MediaPlayer player;
	
	public Music(Context applicationContext){
		mContext  = applicationContext;
	}
	
	
	public void play(){

		Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM); 
		player = new MediaPlayer();
		try {
			player.setDataSource(mContext, alert);
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();

		}

		final AudioManager audioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);

		if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
			player.setAudioStreamType(AudioManager.STREAM_ALARM);
			player.setLooping(true);
			try {
				player.prepare();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			player.start();
		}
	}
	
	public void stop(){
		if(player.isPlaying()){
			player.stop();
			player.release();
		}
	}



}
