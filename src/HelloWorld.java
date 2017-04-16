
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.LCD;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.objectdetection.*;

public class HelloWorld implements FeatureListener {

	public static int MAX_DETECT = 255;
	
	public static void main(String[] args) throws Exception {
		
		HelloWorld listener = new HelloWorld();
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
		RangeFeatureDetector fd = new RangeFeatureDetector(us, MAX_DETECT, 5);
		fd.addListener(listener);
		Button.ENTER.waitForPressAndRelease();
	}

	public void featureDetected(Feature feature, FeatureDetector detector) {
		int range = (int)feature.getRangeReading().getRange();
		LCD.drawString("Range: " + range,0,0);
		System.out.println("Range:" + range);
	}
};

/*public class HelloWorld implements FeatureListener{
    public static void main(String[] args) {
    	

    	
    	
    	
    	
        //System.out.println("Hello World!");
        //Button.waitForAnyPress();
        //Motor.B.forward();
        //Motor.C.forward();
        
        //LCD.drawString("Program 1", 0, 0);
       Button.waitForAnyPress();
            //       LCD.clear();
       //Motor.B.setSpeed(0);
                   Motor.B.forward();
                   Motor.C.forward();
                   //Motor.C.
              //     LCD.drawString("FORWARD",0,0);
                   Button.waitForAnyPress();
                   //LCD.drawString("BACKWARD",0,0);
                   //Motor.B.backward();
                   //Motor.C.backward();
                   //Button.waitForAnyPress();
                   //Motor.B.stop();
                   //Motor.C.stop();
                   
                   /*LCD.drawString("Program 2", 0, 0);
                   Button.waitForAnyPress();
                               LCD.clear();
                               Motor.C.forward();
                               LCD.drawString("FORWARD",0,0);
                               Button.waitForAnyPress();
                               LCD.drawString("BACKWARD",0,0);
                               Motor.C.backward();
                               Button.waitForAnyPress();
                               Motor.C.stop(); =
    }
}*/