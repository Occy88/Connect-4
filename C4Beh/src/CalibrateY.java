import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class CalibrateY implements Behavior
{

	@SuppressWarnings("unused")
	private ColorSensor ls;

	public CalibrateY()
	{
		ls = new ColorSensor(SensorPort.S2);
	}

	public void action()
	{

		System.out.println("Calibrating y");
		// reset
		TouchSensor tsX = new TouchSensor(SensorPort.S1);
		TouchSensor tsY = new TouchSensor(SensorPort.S3);
		Motor.B.setSpeed(150);
		Motor.A.setSpeed(150);

		Motor.A.forward();
		Motor.B.backward();

		while (!tsX.isPressed() || !tsY.isPressed())
		{
			Delay.nsDelay(10);
			if (tsX.isPressed())
			{
				Motor.A.stop();
			}
			if (tsY.isPressed())
			{
				Motor.B.stop();
			}
		}
		Motor.A.stop();
		Motor.B.stop();

		// rest
		int high = 0, low = 1000, x = 0, gotLow = 0, gotHigh = 1;
		ColorSensor ls = new ColorSensor(SensorPort.S2);

		Motor.B.resetTachoCount();
		Motor.B.setSpeed(150);
		Motor.B.forward();

		while (x < 6)
		{

			high = ls.getRawLightValue();
			LCD.drawInt(ls.getRawLightValue(), 0, 2);

			if (gotHigh == 0 && high > low)
			{
				low = high;
			}
			if (high < low - 30 && gotHigh == 0)
			{
				gotLow = 0;
				gotHigh = 1;
			}
			if (high < low && gotLow == 0)
			{
				low = high;
			}
			if (high > low + 30 && gotLow == 0)
			{
				Game.DISTANCESY[x] = Motor.B.getTachoCount();
				LCD.drawInt(x, 0, 3);
				gotLow = 1;
				gotHigh = 0;
				x++;
			}

		}
		Motor.B.stop();// check for anomalies in order to make this less error prone
		LCD.drawString("Calibrated Y", 0, 1);

		Game.CALIBRATEY = false;

	}

	public void suppress()
	{

	}

	public boolean takeControl()
	{
		return Game.CALIBRATEY;
	}

}
