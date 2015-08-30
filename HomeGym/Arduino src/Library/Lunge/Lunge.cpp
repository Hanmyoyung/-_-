#include "Arduino.h"
#include "Lunge.h"

Lunge::Lunge(int pin){
	flexPin=pin;
}

int Lunge::setFlexVal(){
	flexVal = analogRead(flexPin);
}

boolean Lunge::doLunge(){
	if(flexVal>800 && flexVal<890){
		lungeCount++;
		return true;	
	}else{
		return false;
	}	

}

boolean Lunge::isFinish(){
	if(lungeCount>=sFullCount){
		return true;	
	}else{
		return false;
	}
}

int Lunge::getLungeCount(){
	return lungeCount;
}

void Lunge::setLungeCount(){
	lungeCount=0;
}


int Lunge::getFlexVal(){
	return flexVal;
}

