#include "Arduino.h"
#include "Bluetooth.h"
#include "SoftwareSerial.h"


Bluetooth::Bluetooth(int a){
	strcpy(temp, "0\0"); // ���ڿ��� ����� \0�� ������!!!
    	strcpy(start, "1\0"); // ���ڿ��� ����� \0�� ������!!!
    	strcpy(finish, "2\0"); // ���ڿ��� ����� \0�� ������!!!
}

int Bluetooth::recData(char getData){
	
            buffer[index++] = getData;

       for(uint8_t i = 0; i < CHECK_BUFF; ++i) {
            Serial.println(buffer[i]);
            Serial.println();
         }
		
            // ���۰� �� á�ų� ���ڿ��� ������ ��,
            // �������� ������.
            if(index == BUFF_SIZE || data == '\0'){
		chooseState();
	     }
            // 9600bps �������� delay �� 1ms �� ��� �Ѵ�.
            delay(5);
	
	return state;
}

void Bluetooth::chooseState(){
	 
	if(strcmp(buffer,start)==0){
		Serial.println("equal start");
                index = 0;
                index1 = 0;
                state=1;

         }else if(strcmp(buffer,temp)==0){
                Serial.println("equal temp");
                index = 0;
                index1 = 0;
		state = 12;
   	}else if(strcmp(buffer,finish)==0){
                Serial.println("equal finish");
                index = 0;
                index1 = 0;
		state = 0;
   	}
	
	for(uint8_t i = 0; i < index; ++i) {
                buffer[i]='\0';
        }     
           
       index = 0;

}

float Bluetooth::sendTempData(int reading){
	String prefix = String("0_");
        V = reading*5;
        V = V/1024;
        C = 100*(V-0.5);
        tp1 = (C*100);
        tp2 = tp1;
        tp3 = tp2/100;
	return tp3;
/*
        currentTemp = floatToString(temperature, tp3, 2);
        currentTemp+='\0';
        currentTemp = prefix+currentTemp;
        Serial.println(currentTemp); 
	
	return currentTemp;
*/
}

