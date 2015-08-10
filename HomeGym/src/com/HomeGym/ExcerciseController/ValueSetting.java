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
			focus = "전신";
		}
		else if(focus.equals("upper")){
			focus = "상체";
		}
		else if(focus.equals("lower")){
			focus = "하체";
		}
		else if(focus.equals("stomach")){
			focus = "복근";
		}
		return focus;
	}
	
	public Boolean getAlarm(){
		return alarmCheck;
	}
	
	

}
