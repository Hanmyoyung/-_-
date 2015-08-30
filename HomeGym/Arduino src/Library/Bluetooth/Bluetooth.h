#ifndef BLUETOOTH_H
#define BLUETOOTH_H
#define BUFF_SIZE 10
#define CHECK_BUFF 2
#include "Arduino.h"

class Bluetooth
{
	public:
		Bluetooth(int a);
		int recData(char data);
		void sendData(); 
		//void setSerial();
		void chooseState();
		float sendTempData(int reading);


	private:
		char buffer[BUFF_SIZE];// 수신용 버퍼
		byte buffer1[BUFF_SIZE];// 송신용 버퍼
		byte checkBuf [CHECK_BUFF];

		char start [CHECK_BUFF];
		char temperature [BUFF_SIZE];
		char temp [CHECK_BUFF];
		char finish [CHECK_BUFF];

		int index = 0;
		int index1 = 0;
		char data;
		char data1;
		int state = 0;
		float V;
		float C; 
		int tp1;
		float tp2;
		float tp3;
		String currentTemp;

};

#endif
