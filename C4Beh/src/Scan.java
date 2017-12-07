import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class Scan implements Behavior
{

	public void action()
	{
		// reset

		Game.CALIBRATEPIECE = false;
		Game.CALIBRATEX = false;
		Game.CALIBRATEY = false;
		LCD.clear();
		System.out.println("Scanning");
		System.out.println("LightValue: " + Game.PIECEDETECTED);
		Motor.A.setSpeed(200);
		Motor.B.setSpeed(200);

		for (int x = 0; x < 7; x++)
		{
			for (int y = 5; y >= 0; y--)
			{

				if (Game.GAMEBORAD[y][x] == 0)
				{
					Game.COORDS[x][0] = x;
					LCD.drawInt(x, 0, x);
					Game.COORDS[x][1] = y;
					LCD.drawInt(y, 2, x);
					break;
				} else if (y == 0 && Game.GAMEBORAD[5][x] != 0)
				{
					Game.COORDS[x][0] = -1;
					Game.COORDS[x][1] = -1;
				}

			}
		}

		ColorSensor ls = new ColorSensor(SensorPort.S2);
	
		int found = 0, average = 0,skip=0;
		int currentColumn = -1;
	
		for (int z = 0; z < 7 && found == 0; z++)
		{
			if (Game.COORDS[z][0] != -1&&Game.COORDS[z][1]!=-1)
			{
				if (currentColumn == -1)
				{
					Motor.A.rotate(Game.DISTANCESX[Game.COORDS[z][0]]+25, true);//size 7 array
					Motor.B.rotate(Game.DISTANCESY[Game.COORDS[z][1]]);//size 6 array.
				} else
				{
					
				
					Motor.A.rotate(Game.DISTANCESX[Game.COORDS[z][0]] - Game.DISTANCESX[Game.COORDS[currentColumn][0]],true);
					Motor.B.rotate(Game.DISTANCESY[Game.COORDS[z][1]] - Game.DISTANCESY[Game.COORDS[currentColumn][1]]);
				
				}
				
				currentColumn++;
				currentColumn+=skip;
				skip=0;
				Delay.msDelay(1000);
				for (int z1 = 0; z1 < 200; z1++)
				{
					average += ls.getRawLightValue();
				}
				average /= 200;
				if (average < Game.PIECEDETECTED - 100)
				{
					System.out.println("found player move:" + z);
					Game.PLAYERMOVE = z;
					found = 1;
					Game.APPLY_MOVE = true;
					Game.SCAN = false;

				}
				
			}
			else{skip++;}
				

		}
		if (found == 0)
		{
			System.out.println("found player move:" + 6);
			Game.PLAYERMOVE = 6;
			found = 1;
			Game.APPLY_MOVE = true;
			Game.SCAN = false;
		}

	}

	public void suppress()
	{

	}

	public boolean takeControl()
	{
		return Game.SCAN;
	}
}
