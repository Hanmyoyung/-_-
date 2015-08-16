package com.HomeGym.Controller;

public class ValueSetting {
	
	public static String goal;
	public static String focus;
	public static Boolean alarmCheck;
	public static int fullSquat;
	public static int fullCrunch;
	public static int fullSquatTime;
	public static int fullCrunchTime;
	
	public String getGoal(){
		return goal;
	}
		
	public String getFocus(){
		if(focus.equals("full")){
			focus = "����";
			fullSquat=25;
			fullCrunch=25;
			fullSquatTime=50;
			fullCrunchTime=50;
			
		}
		else if(focus.equals("upper")){
			focus = "��ü";
			fullSquat=25;
			fullCrunch=25;
			fullSquatTime=50;
			fullCrunchTime=50;
			
		}
		else if(focus.equals("lower")){
			focus = "��ü";
			fullSquat=50;
			fullCrunch=25;
			fullSquatTime=100;
			fullCrunchTime=50;
		}
		else if(focus.equals("stomach")){
			focus = "����";
			fullSquat=25;
			fullCrunch=50;
			fullSquatTime=50;
			fullCrunchTime=100;
		}
		return focus;
	}
	
	public Boolean getAlarm(){
		return alarmCheck;
	}	

}
