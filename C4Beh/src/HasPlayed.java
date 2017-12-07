import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class HasPlayed implements Behavior {

	public void action() {
		//reset
		System.out.println("Checking for Play");
		TouchSensor tsX = new TouchSensor(SensorPort.S1);
		TouchSensor tsY = new TouchSensor(SensorPort.S3);
		Motor.B.setSpeed(200);
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
		//music for player's turn...
	
		Notes tone = new Notes();
	
		Sound.setVolume(100);
		tone.musicPlay("G5", 100);
		tone.musicPlay("C5", 400);
		
		//check
		UltrasonicSensor us= new UltrasonicSensor(SensorPort.S4);
		while (true)
		{int played=0;
			while(us.getDistance()<30)
			{
				played=1;
			}
			if(played==1)
			{
				break;
			}
		}
		
		Game.SCAN=true;
				
		

	}

	public void suppress() {

	}

	public boolean takeControl() {
		return !Game.HASPLAYED;
	}
}   
