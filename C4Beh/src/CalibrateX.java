import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class CalibrateX implements Behavior {

	public void action() {
		System.out.println("Calibrating X");
		//reset
		TouchSensor tsX = new TouchSensor(SensorPort.S1);
		TouchSensor tsY = new TouchSensor(SensorPort.S3);
		Motor.B.setSpeed(150);
		Motor.A.setSpeed(150);

		Motor.A.forward();
		Motor.B.backward();

		while (!tsX.isPressed() || !tsY.isPressed()) {
			Delay.nsDelay(10);
			if (tsX.isPressed()) {
				Motor.A.stop();
			}
			if (tsY.isPressed()) {
				Motor.B.stop();
			}
		}
		Motor.A.stop();
		Motor.B.stop();
		
		//rest
		int high = 0, low = 1000, x = 0, gotLow = 0, gotHigh = 1;
		ColorSensor ls = new ColorSensor(SensorPort.S2);

		Motor.A.resetTachoCount();
		Motor.A.setSpeed(150);
		Motor.A.backward();

		while (x < 7) {

			high = ls.getRawLightValue();
			LCD.drawInt(ls.getRawLightValue(), 0, 2);
			// getting High
			if (gotHigh == 0 && high > low) {
				low = high;
			}
			if (high < low - 30 && gotHigh == 0) {
				Game.DISTANCESX[x] = Motor.A.getTachoCount();
				LCD.drawInt(x, 0, 3);
				x++;

				gotLow = 0;
				gotHigh = 1;
			}
			if (high < low && gotLow == 0) {
				low = high;

			}

			if (high > low + 30 && gotLow == 0) {

				gotLow = 1;
				gotHigh = 0;

			}

		}

		Motor.A.stop();
		LCD.drawString("calibrated X", 0, 6);
		
		Game.CALIBRATEX=false;
		
	}

	public void suppress() {

	}

	public boolean takeControl() {
		return Game.CALIBRATEX;
	}
}
