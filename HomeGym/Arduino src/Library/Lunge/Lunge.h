#ifndef LUNGE_H
#define LUNGE_H
#include "Arduino.h"

class Lunge
{
	public:
		Lunge(int pin);
		boolean doLunge();
		boolean isFinish();
		int getLungeCount();
		int getFlexVal();
		int setFlexVal();
		void setLungeCount();
		//void countSquat();

	private:
		int flexPin;
		int flexVal;
		int lungeCount=0;
		int sFullCount=25;
		int cDuration = 50000;
};

#endif
