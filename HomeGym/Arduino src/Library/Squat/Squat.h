#ifndef SQUAT_H
#define SQUAT_H
#include "Arduino.h"

class Squat
{
	public:
		Squat(int pin);
		boolean doSquat();
		boolean isFinish();
		int getSquatCount();
		int getFlexVal();
		int setFlexVal();
		void setSquatCount();
		//void countSquat();

	private:
		int flexPin;
		int flexVal;
		int squatCount=0;
		int sFullCount=25;
		int cDuration = 50000;
};

#endif
