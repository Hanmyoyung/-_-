#include "Arduino.h"
#include "Pitches.h"
#ifndef PIEZO_H
#define PIEZO_H

class Piezo
{
	public:
		Piezo(int pin);
		void startSound();
		boolean exSound();
		void restSound();
		void endSound();

	private:
		int piezoPin;
		int melody[8] = {NOTE_E4, NOTE_C4, NOTE_D4, NOTE_G3, NOTE_G3, NOTE_D4, NOTE_E4,NOTE_C4};
		int soundCount=0;
};
#endif
	


