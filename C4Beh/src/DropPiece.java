import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.subsumption.Behavior;

public class DropPiece implements Behavior
{
	public void action()
	{
		Notes tone = new Notes();
	
		Sound.setVolume(50);

		
		Motor.A.setSpeed(150);
		System.out.println("Dropping Piece from:" + Game.PLAYERMOVE + " to: " + Game.COMPMOVE);
		int adjustment = -220;
		Motor.A.rotate(Game.DISTANCESX[Game.COMPMOVE] - Game.DISTANCESX[Game.PLAYERMOVE] + adjustment);
		Motor.C.setSpeed(150);
		Motor.C.rotate(-220);
		Motor.C.rotate(220);
	
	
		// music files here and game state here...
		 if (Game.win == 1)
		{
			 LCD.clear();
			 System.out.println("I win :D");
			// win for comp
			tone.musicPlay("G3", 100);
			tone.musicPlay("C4", 100);
			tone.musicPlay("E4", 100);
			tone.musicPlay("G4", 100);
			tone.musicPlay("C5", 100);
			tone.musicPlay("E5", 100);
			tone.musicPlay("G5", 300);
			tone.musicPlay("E5", 300);

			tone.musicPlay("G#3", 100);
			tone.musicPlay("C4", 100);
			tone.musicPlay("D#4", 100);
			tone.musicPlay("G#4", 100);
			tone.musicPlay("C5", 100);
			tone.musicPlay("D#5", 100);
			tone.musicPlay("G#5", 300);
			tone.musicPlay("D#5", 300);

			tone.musicPlay("A#3", 100);
			tone.musicPlay("D4", 100);
			tone.musicPlay("F4", 100);
			tone.musicPlay("A#4", 100);
			tone.musicPlay("D5", 100);
			tone.musicPlay("F5", 100);
			tone.musicPlay("A#5", 300);
			tone.musicPlay("A#5", 100);
			tone.musicPlay("A#5", 100);
			tone.musicPlay("A#5", 100);
			tone.musicPlay("C6", 700);
			Game.HASPLAYED=true;
			Game.APPLY_MOVE = false;
			Game.DROP_PIECE = false;

		} else if (Game.win == 2)
		{
			//lose
			 LCD.clear();
			 System.out.println("I loose :-(");
			tone.musicPlay("C5", 300);
			tone.musicPlay("G4", 300);
			tone.musicPlay("E4", 200);
			tone.musicPlay("A4", 200);
			tone.musicPlay("B4", 200);
			tone.musicPlay("A4", 200);
			tone.musicPlay("G#4", 200);
			tone.musicPlay("A#4",200);
			tone.musicPlay("G#4",200);
			tone.musicPlay("E4", 100);
			tone.musicPlay("D4", 150);
			tone.musicPlay("E4", 400);
			Game.HASPLAYED=true;
			Game.APPLY_MOVE = false;
			Game.DROP_PIECE = false;
		} else if (Game.win == 3)
		{
			// draw
			 LCD.clear();
			 System.out.println("well what a waste of time");
			tone.musicPlay("E5", 150);
			tone.musicPlay("E5", 300);
			tone.musicPlay("E5", 300);
			tone.musicPlay("C5", 100);
			tone.musicPlay("E5", 300);
			tone.musicPlay("G5", 550);
			tone.musicPlay("G4", 575);
			Game.HASPLAYED=true;
			Game.APPLY_MOVE = false;
			Game.DROP_PIECE = false;

		}
		else {
		Game.HASPLAYED = false;
		Game.APPLY_MOVE = false;
		Game.DROP_PIECE = false;
		}
	}

	public void suppress()
	{

	}

	public boolean takeControl()
	{
		return Game.DROP_PIECE;
	}
}
