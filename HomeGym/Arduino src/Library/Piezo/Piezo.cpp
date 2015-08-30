#include "Arduino.h"
#include "Piezo.h"
#include "Pitches.h"


Piezo::Piezo(int pin){
	piezoPin=pin;
}

void Piezo::startSound(){
	tone(piezoPin, NOTE_C4);
        delay(100);
        noTone(piezoPin);
}

boolean Piezo::exSound(){
	
      soundCount++;
      Serial.println("HaHaHAHAHaHAHAHAHAHAH");
      if(soundCount%2==0){
          tone(piezoPin,NOTE_E4);
          delay(100);
          noTone(piezoPin); 
	  return true;
      }
      return false;
}

void Piezo::restSound(){
      	soundCount=0;
	for (int thisNote = 0; thisNote < 8; thisNote++) {
        	int noteDurations[8] = {1,1,1,3,1,1,1,3 };
        	int noteDuration = (10000/12)*noteDurations[thisNote];
        	tone(piezoPin, melody[thisNote],noteDuration);
        	int pauseBetweenNotes = noteDuration*1.01;
        	delay(pauseBetweenNotes);
        	// stop the tone playing:
        	noTone(piezoPin);
   	}

}
 
void Piezo::endSound(){
	tone(piezoPin, NOTE_G4);
        delay(100);
        noTone(piezoPin);
}




