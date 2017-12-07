import lejos.nxt.ColorSensor;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class CalibratePiece implements Behavior {

	public void action() {
		System.out.println("Calibrating Piece");
	
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
		

		Motor.A.rotate(Game.DISTANCESX[0]+10, true);
		Motor.B.rotate(Game.DISTANCESY[1]);
		ColorSensor ls = new ColorSensor(SensorPort.S2);
		int y = 0;
		while (y < 500)
		{
			y++;
			Game.PIECEDETECTED = ls.getRawLightValue();
			Delay.msDelay(1);
		}
	
		Game.CALIBRATEPIECE=false;

	
	}

	public void suppress() {

	}

	public boolean takeControl() {
		return Game.CALIBRATEPIECE;
	}
}
