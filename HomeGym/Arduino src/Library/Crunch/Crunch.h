#ifndef CRUNCH_H
#define CRUNCH_H
#include "Arduino.h"

class Crunch
{
	public:
		Crunch(int pin);
		boolean doCrunch();
		boolean isFinish();
		int getCrunchCount();
		int getQrdVal();
		int setQrdVal();
		void setCrunchCount();

	private:
		int qrdPin;
		int qrdVal;
		int crunchCount = 0;
		int cFullCount = 25;
		int cDuration = 50000;
		int signal = 4;

};

#endif
