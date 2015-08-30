#include <blinkLED.h>
#include <Bluetooth.h>
#include <Lunge.h>
#include <LegRaise.h>
#include <TimeSetting.h>
#include <Crunch.h>
#include <Piezo.h>
#include <Squat.h>
#include <Pitches.h>
#include <FloatToString.h>
#include <SoftwareSerial.h>


#define rxPin 6 
#define txPin 7 
#define BUFF_SIZE 10
#define CHECK_BUFF 2

int temperaturePin = 1;
char temperature [BUFF_SIZE];
String currentTemp;
String count;
String doOrNot;

int ledPins[] = {2,3};

uint8_t buffer[BUFF_SIZE];// 송신용 버퍼
byte checkBuf [CHECK_BUFF];
String prefix; 
int index = 0;


SoftwareSerial swSerial(txPin, rxPin);
Squat squat(0);
Lunge lunge(0);
Piezo piezo(10);
Crunch crunch(4);
LegRaise legRaise(4);
Bluetooth bt(0);

int state=0;




void setup() {
  // put your setup code here, to run once:
    Serial.begin(115200);
    swSerial.begin(115200);
    Bluetooth bt(0);

    for(int i = 0; i <= 2; i++){     
      pinMode(ledPins[i],OUTPUT); // 8개의 LED를 출력으로 설정.   
   } 
}

void loop(){
  if(state==0){
       blinkRed(ledPins[1],true);
       blinkBlue(ledPins[0],false);
      
      if(swSerial.available()){
            while(swSerial.available()) {
                state=bt.recData(swSerial.read());
                Serial.print("아니 이게 stat 값이라ㅗ");
                //Serial.println(String(print));
             }
        }
    }
  
   else if(state==1){ // 사용자가 운동을 시작하였습니다. 부저에서 소리가 납니다.
        // 센서값을 저장할 변수  
        squat.setFlexVal();// 아날로그를 입력 받음 (0~1023) 
        Serial.print("사용자가 운동을 시작하길 기다리는 state1입니다. 현재 flex 센서의 값은");
        Serial.println(squat.getFlexVal());
        piezo.startSound();
        state=2;        
  }
  else if(state==2){ //사용자가 squat 운동을 합니다.    
        squat.setFlexVal();
        Serial.println("사용자가 squat운동을 진행하는 state2 입니다.");
        if(squat.doSquat()==true){
            Serial.print("운동한 횟수는요?");
            Serial.println(squat.getSquatCount());
            Serial.println(squat.getFlexVal());
            blinkRed(ledPins[1],false);
            blinkBlue(ledPins[0],true);
         
      }

    if(piezo.exSound()==true){
        if(squat.getFlexVal()<800){
            Serial.println(squat.getFlexVal());
            Serial.println("더 구부려야 합니다");
            blinkRed(ledPins[1],true);
            blinkBlue(ledPins[0],false);


            doOrNot = String("7\0");
            Serial.println(doOrNot); 
       
            doOrNot.getBytes(checkBuf,2);
  
            for(uint8_t i = 0; i < CHECK_BUFF; ++i) {
                 Serial.println(checkBuf[i]);
                 Serial.println();
             }
       
            for(uint8_t i = 0; i < CHECK_BUFF; ++i) {
                swSerial.write(checkBuf[i]);
            }   
            doOrNot="";         
            for(uint8_t i = 0; i < CHECK_BUFF; ++i) {
                checkBuf[i]='\0';
            }
            
        }else if(squat.getFlexVal()>890){
          Serial.println(squat.getFlexVal());
          Serial.println("덜 구부려야 합니다");
          blinkRed(ledPins[1],true);
          blinkBlue(ledPins[0],false);

          
          doOrNot= String("8_\0");
          Serial.println(doOrNot); 
 
          doOrNot.getBytes(checkBuf,2);
  
          for(uint8_t i = 0; i < CHECK_BUFF; ++i) {
               Serial.println(checkBuf[i]);
               Serial.println();
        }
       
        for(uint8_t i = 0; i < CHECK_BUFF; ++i) {
               swSerial.write(checkBuf[i]);
        }
        doOrNot="";
      }
    }
    delay(1000);
    
    if(swSerial.available()){
        Serial.println("뭐야 여기 들어오긴 하는거야?");
         while(swSerial.available()) {
            if(bt.recData(swSerial.read())==0){
                blinkRed(ledPins[1],false);
                blinkBlue(ledPins[0],false);
                Serial.println("스쿼트 시간 끝");
                String prefix = String("3_");
                count= String(squat.getSquatCount());
                count+='\0';
                count = prefix+count;
                Serial.println(count); 
                count.getBytes(buffer,5);
        
              // 블루투스를 통하여 받은 데이터를 되돌려준다.
       
                for(uint8_t i = 0; i < 5; ++i) {
                      swSerial.write(buffer[i]);
                }

                for(uint8_t i = 0; i < BUFF_SIZE; ++i) {
                      buffer[i]='\0';
                }
                //Serial.println("");
                count="";
                Serial.print("버퍼 초기화 되니");
                Serial.println(count);
                index = 0;
                squat.setSquatCount();
                state = 3;
            }
        }
     }
  } else if(state == 3){ //휴식
      //piezo.restSound();
      Serial.println("휴식시간입니다");
      piezo.restSound();
      blinkRed(ledPins[1],true);
      blinkBlue(ledPins[0],false);
      
      if(swSerial.available()){
        Serial.println("휴식시간입니다 들어오긴 하는거야?");
         while(swSerial.available()) {         
            if(bt.recData(swSerial.read())==0){
              state=4;
            }
         }
        
      }
  }else if(state == 4){ //크런치
      piezo.startSound();
      state=5;
      
  }else if(state == 5){ //크런치
      crunch.setQrdVal();
      Serial.println("사용자가 crunch운동을 진행하는 state5 입니다.");
      if(crunch.doCrunch()==true){
           Serial.print("크런치 횟수는요?");
           Serial.println(crunch.getCrunchCount());
           blinkRed(ledPins[1],false);
            blinkBlue(ledPins[0],true);
      }      
      if(piezo.exSound()==true){
          if(crunch.doCrunch()==false){
              blinkRed(ledPins[1],true);
              blinkBlue(ledPins[0],false);
              doOrNot = String("1\0");
              Serial.println(doOrNot); 
       
              doOrNot.getBytes(checkBuf,2);
  
              for(uint8_t i = 0; i < CHECK_BUFF; ++i) {
                  Serial.println(checkBuf[i]);
                  Serial.println();
               }
       
                for(uint8_t i = 0; i < CHECK_BUFF; ++i) {
                    swSerial.write(checkBuf[i]);
                }            
                for(uint8_t i = 0; i < CHECK_BUFF; ++i) {
                    checkBuf[i]='\0';
                }
                doOrNot="";
          }
      }
      delay(1000);
                
      if(swSerial.available()){
          Serial.println("뭐야 여기 들어오긴 하는거야?");
          while(swSerial.available()) {    
          if(bt.recData(swSerial.read())==0){
              //piezo.endSound();
              Serial.println("크런치 시간 끝");            
              String prefix = String("4_");
              count= String(crunch.getCrunchCount());
              count+='\0';
              count = prefix+count;
              Serial.println(count); 
              count.getBytes(buffer,5);
               
              // 블루투스를 통하여 받은 데이터를 되돌려준다.
       
              for(uint8_t i = 0; i < 5; ++i) {
                    swSerial.write(buffer[i]);
              }

              for(uint8_t i = 0; i < BUFF_SIZE; ++i) {
                    buffer[i]='\0';
              }
              //Serial.println("");
              index = 0;
              crunch.setCrunchCount();
              count="";
              state = 6;
          }
    }
  }
  }
  else if(state == 6){ //크런치
      Serial.println("운동 끝!!");
      piezo.endSound();
      state=7;
  }
  else if(state==7){ // 휴식
  //아무것도 하지마
  Serial.println("휴식시간입니다");
     blinkRed(ledPins[1],true);
     blinkBlue(ledPins[0],false);
      piezo.restSound();
      if(swSerial.available()){
        Serial.println("휴식시간입니다 들어오긴 하는거야?");
         while(swSerial.available()) {         
            if(bt.recData(swSerial.read())==0){
              state=8;
            }
         }
        
      }

  }
  else if(state==8){ // 런지
    lunge.setFlexVal();
    Serial.println("사용자가 Lunge운동을 진행하는 state8 입니다.");
    if(lunge.doLunge()==true){
         Serial.print("운동한 횟수는요?");
         Serial.println(lunge.getLungeCount());
         blinkRed(ledPins[1],false);
         blinkBlue(ledPins[0],true);
    }
    if(piezo.exSound()==true){
         if(lunge.getFlexVal()<800){
            Serial.println(lunge.getFlexVal());
            Serial.println("더 구부려야 합니다");
            blinkRed(ledPins[1],true);
            blinkBlue(ledPins[0],false);
             doOrNot = String("7\0");
            Serial.println(doOrNot); 
       
            doOrNot.getBytes(checkBuf,2);
  
            for(uint8_t i = 0; i < CHECK_BUFF; ++i) {
                 Serial.println(checkBuf[i]);
                 Serial.println();
             }
       
            for(uint8_t i = 0; i < CHECK_BUFF; ++i) {
                swSerial.write(checkBuf[i]);
            }   
            doOrNot="";         
            for(uint8_t i = 0; i < CHECK_BUFF; ++i) {
                checkBuf[i]='\0';
            }
      
        }else if(lunge.getFlexVal()>890){
            Serial.println(lunge.getFlexVal());
            Serial.println("덜 구부려야 합니다");
            blinkRed(ledPins[1],true);
            blinkBlue(ledPins[0],false);
            doOrNot= String("8_\0");
            Serial.println(doOrNot); 
       
            doOrNot.getBytes(buffer,4);
  
            for(uint8_t i = 0; i < 9; ++i) {
               Serial.println(buffer[i]);
               Serial.println();
            }
       
            for(uint8_t i = 0; i < 11; ++i) {
               swSerial.write(buffer[i]);
            }

            index=0;
            doOrNot="";

            for(uint8_t i = 0; i < BUFF_SIZE; ++i) {
                buffer[i]='\0';
            }
        }
    }
    delay(1000);

    Serial.println("뭐야 여기 들어오긴 하는거야?");
         while(swSerial.available()) 
       
            if(bt.recData(swSerial.read())==0){
                Serial.println("런지 시간 끝");
                String prefix = String("5_");
                count= String(lunge.getLungeCount());
                count+='\0';
                count = prefix+count;
                Serial.println(count); 
                count.getBytes(buffer,5);
        
                 for(uint8_t i = 0; i < 11; ++i) {
                    Serial.println(buffer[i]);
                    Serial.println();
                }
       
              // 블루투스를 통하여 받은 데이터를 되돌려준다.
       
                for(uint8_t i = 0; i < 5; ++i) {
                      swSerial.write(buffer[i]);
                }

                for(uint8_t i = 0; i < BUFF_SIZE; ++i) {
                      buffer[i]='\0';
                }
                //Serial.println("");
                index = 0;

                lunge.setLungeCount();
                state = 9;
                count="";
            }
    }   
    else if(state==9){ // 휴식
     //아무것도 하지마
      Serial.println("휴식시간입니다");
      piezo.restSound();
      blinkRed(ledPins[1],true);
      blinkBlue(ledPins[0],false);
      if(swSerial.available()){
        Serial.println("휴식시간입니다 들어오긴 하는거야?");
         while(swSerial.available()) {         
            if(bt.recData(swSerial.read())==0){
              state=10;
            }
         }
        
      }
  }
    
    
    
  else if(state == 10){ //레그 레이즈
        
      legRaise.setQrdVal();
      Serial.println("사용자가 LegRaise운동을 진행하는 state10 입니다.");
      if(legRaise.doLegRaise()==true){
           Serial.print("크런치 횟수는요?");
           Serial.println(legRaise.getLegRaiseCount());
           blinkRed(ledPins[1],false);
           blinkBlue(ledPins[0],true);
      }
      
      
      if(piezo.exSound()==true){
        if(legRaise.doLegRaise()==false){
              blinkRed(ledPins[1],true);
              blinkBlue(ledPins[0],false);
              doOrNot= String("1\0");
              Serial.println(doOrNot); 
       
              doOrNot.getBytes(checkBuf,2);
  
              for(uint8_t i = 0; i < CHECK_BUFF; ++i) {
                  Serial.println(checkBuf[i]);
                  Serial.println();
               }
       
                for(uint8_t i = 0; i < CHECK_BUFF; ++i) {
                    swSerial.write(checkBuf[i]);
                }
            
                for(uint8_t i = 0; i < CHECK_BUFF; ++i) {
                    checkBuf[i]='\0';
                }
                doOrNot="";
          }
        }
      
      delay(1000);      
      if(swSerial.available()){
            if(bt.recData(swSerial.read())==0){
                //piezo.endSound();
                Serial.println("레그레이즈 시간 끝");            
                String prefix = String("6_");
                count = String(legRaise.getLegRaiseCount());
                count+='\0';
                count = prefix+count;
                Serial.println(count); 
                count.getBytes(buffer,5);
        
                 for(uint8_t i = 0; i < 11; ++i) {
                    Serial.println(buffer[i]);
                    Serial.println();
                }
       
              // 블루투스를 통하여 받은 데이터를 되돌려준다.
       
                for(uint8_t i = 0; i < 5; ++i) {
                      swSerial.write(buffer[i]);
                }

                for(uint8_t i = 0; i < BUFF_SIZE; ++i) {
                buffer[i]='\0';
            }
                //Serial.println("");
                index = 0;
                legRaise.setLegRaiseCount();
                state = 11;
                count="";
            }
    }
  }
   
  else if(state==12){ //현재 온도 받아오는 state
        Serial.println("여기로 와야합니다");
        int reading = analogRead(temperaturePin);
        float tempf = bt.sendTempData(reading);
        prefix = String("0_");
        currentTemp = floatToString(temperature, tempf, 2);
        currentTemp+='\0';
        currentTemp = prefix+currentTemp;
        Serial.println(currentTemp); 
        currentTemp.getBytes(buffer,8);

        for(uint8_t i = 0; i < 11; ++i) {
               swSerial.write(buffer[i]);
        }
        
         for(uint8_t i = 0; i < BUFF_SIZE; ++i) {
                buffer[i]='\0';
            }
          state = 0;
 
          
      }

    else if(state==11){ //종료
  //아무것도 하지마
       blinkRed(ledPins[1],true);
       blinkBlue(ledPins[0],true);
       Serial.println("다시 처음으로 돌아갑니다!!");
           for(uint8_t i = 0; i < BUFF_SIZE; ++i) {
                buffer[i]='\0';

            }
       index = 0;
       state = 0;
  
  }
}



    




