package com.HomeGym.ExcerciseController;

public class ValueSetting {
	
	public static String goal;
	public static String focus;
	public static Boolean alarmCheck;
	
	public String getGoal(){
		return goal;
	}
	
	public String getFocus(){
		if(focus.equals("full")){
			focus = "����";
		}
		else if(focus.equals("upper")){
			focus = "��ü";
		}
		else if(focus.equals("lower")){
			focus = "��ü";
		}
		else if(focus.equals("stomach")){
			focus = "����";
		}
		return focus;
	}
	
	public Boolean getAlarm(){
		return alarmCheck;
	}
	
	

}
