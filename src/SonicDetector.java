
import lejos.nxt.Button;
import lejos.nxt.LCD;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.RangeReading;
import lejos.robotics.objectdetection.*;


public class SonicDetector implements FeatureListener {

	private static int MAX_DETECT = 70;
	
	// Test program for calibration of UltraSonic
	public static void main(String[] args) throws Exception {
		init();
		Button.ENTER.waitForPressAndRelease();
	}
	
	public static void init()
	{
		SonicDetector listener = new SonicDetector();
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
		RangeFeatureDetector fd = new RangeFeatureDetector(us, MAX_DETECT, 500);
		fd.addListener(listener);
	}

	public void featureDetected(Feature feature, FeatureDetector detector) {
		RangeReading reading = feature.getRangeReading();
			LCD.drawString("Range: " + reading.getRange(), 0, 0);
	}
};