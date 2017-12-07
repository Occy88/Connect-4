
import java.io.IOException;

import lejos.nxt.Sound;

import lejos.robotics.subsumption.Behavior;

public class ApplyMove implements Behavior
{
	public static byte[] b;

	public void action()
	{

		// send method
		Notes tone = new Notes();
		Sound.setVolume(70);

		tone.musicPlay("E4", 100);
		tone.musicPlay("G4", 100);
		tone.musicPlay("E5", 100);
		tone.musicPlay("C5", 100);
		tone.musicPlay("D5", 100);
		tone.musicPlay("G5", 300);
		// send b

		try
		{
			// send move

			applyMove();
			toByte(Game.PLAYERMOVE);
			Game.dataOut.write(b);
			System.out.println("sent" + b);
			Game.dataOut.flush();
			// receive move
			System.out.println("getting move");
			Game.COMPMOVE = toInt(Game.dataIn.read());
			System.out.println("move: " + Game.COMPMOVE);
			applyCompMove();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			// receive state:
			System.out.println("getting State: " + Game.win);
			Game.win = toInt(Game.dataIn.read());
			System.out.println("Game State: " + Game.win);

		} catch (IOException e)
		{

			e.printStackTrace();
		}
		Game.DROP_PIECE = true;
		Game.APPLY_MOVE = false;
	}

	public void suppress()
	{

	}

	public boolean takeControl()
	{
		return Game.APPLY_MOVE;

	}

	public static int toInt(int x)
	{
		x -= 65;
		return x;
	}

	public static byte[] toByte(int n)
	{
		String test;
		n++;
		if (n == 1)
		{
			test = "A";
			b = test.getBytes();
		} else if (n == 2)
		{
			test = "B";
			b = test.getBytes();
		} else if (n == 3)
		{
			test = "C";
			b = test.getBytes();
		} else if (n == 4)
		{
			test = "D";
			b = test.getBytes();
		} else if (n == 5)
		{
			test = "E";
			b = test.getBytes();
		} else if (n == 6)
		{
			test = "F";
			b = test.getBytes();
		} else if (n == 7)
		{
			test = "G";
			b = test.getBytes();
		}

		return b;

	}

	public static void applyMove()
	{
		for (int x = 5; x >= 0; x--)
		{
			if (Game.GAMEBORAD[x][Game.PLAYERMOVE] == 0)
			{

				Game.GAMEBORAD[x][Game.PLAYERMOVE] = 1;
				break;
			}
		}
	}

	public static void applyCompMove()
	{
		for (int x = 5; x >= 0; x--)
		{
			if (Game.GAMEBORAD[x][Game.COMPMOVE] == 0)
			{

				Game.GAMEBORAD[x][Game.COMPMOVE] = 2;
				break;
			}
		}
	}

}
