#ifndef LEGRAISE_H
#define LEGRAISE_H
#include "Arduino.h"

class LegRaise
{
	public:
		LegRaise(int pin);
		boolean doLegRaise();
		boolean isFinish();
		int getLegRaiseCount();
		int getQrdVal();
		int setQrdVal();
		void setLegRaiseCount();

	private:
		int qrdPin;
		int qrdVal;
		int legRaiseCount = 0;
		int cFullCount = 25;
		int cDuration = 50000;
		int signal = 4;

};

#endif
