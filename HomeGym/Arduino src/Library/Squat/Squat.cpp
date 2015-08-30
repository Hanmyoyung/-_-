#include "Arduino.h"
#include "Squat.h"

Squat::Squat(int pin){
	flexPin=pin;
}

int Squat::setFlexVal(){
	flexVal = analogRead(flexPin);
}

boolean Squat::doSquat(){
	if(flexVal>800 && flexVal<890){
		squatCount++;
		return true;	
	}else{
		return false;
	}	

}

boolean Squat::isFinish(){
	if(squatCount>=sFullCount){
		return true;	
	}else{
		return false;
	}
}

int Squat::getSquatCount(){
	return squatCount;
}

void Squat::setSquatCount(){
	squatCount=0;
}


int Squat::getFlexVal(){
	return flexVal;
}

