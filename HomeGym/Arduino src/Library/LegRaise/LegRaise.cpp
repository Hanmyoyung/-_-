#include "Arduino.h"
#include "LegRaise.h"

LegRaise::LegRaise(int pin){
	qrdPin=pin;
}

int LegRaise::setQrdVal(){
	qrdVal = digitalRead(signal);
}

boolean LegRaise::doLegRaise(){
	if(qrdVal==0){
		legRaiseCount++; 
		return true;	
	}else{
		return false;
	}	

}

boolean LegRaise::isFinish(){
	if(legRaiseCount>=cFullCount){
		return true;	
	}else{
		return false;
	}
}

int LegRaise::getLegRaiseCount(){
	return legRaiseCount;
}

void LegRaise::setLegRaiseCount(){
	legRaiseCount=0;
}

/*
int Squat::getFlexVal(){
	return flexVal;
}
*/
