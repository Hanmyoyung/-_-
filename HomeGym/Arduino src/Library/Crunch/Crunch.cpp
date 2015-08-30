#include "Arduino.h"
#include "Crunch.h"

Crunch::Crunch(int pin){
	qrdPin=pin;
}

int Crunch::setQrdVal(){
	qrdVal = digitalRead(signal);
}

boolean Crunch::doCrunch(){
	if(qrdVal==0){
		crunchCount++; 
		return true;	
	}else{
		return false;
	}	

}

boolean Crunch::isFinish(){
	if(crunchCount>=cFullCount){
		return true;	
	}else{
		return false;
	}
}

int Crunch::getCrunchCount(){
	return crunchCount;
}

void Crunch::setCrunchCount(){
	crunchCount=0;
}

/*
int Squat::getFlexVal(){
	return flexVal;
}
*/
