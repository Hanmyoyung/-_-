#include "Arduino.h"
#include "blinkLED.h"

void blinkRed(int led, boolean red){

  if(red==false){
          digitalWrite(led, LOW); 
   }else {
          digitalWrite(led, HIGH);
   } 
}

void blinkBlue(int led,boolean blue){

  if(blue==false){
          digitalWrite(led, LOW); 
   }else 
          digitalWrite(led, HIGH); 
}