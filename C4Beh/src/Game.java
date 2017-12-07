import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.nxt.comm.BTConnection;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Game
{

	public static DataOutputStream	dataOut;
	public static DataInputStream	dataIn;
	public static BTConnection		BTLink;
	public static BTConnection		btLink;
	public static int				commandValue, transmitReceived;
	public static int[][]			GAMEBORAD		= new int[6][7];
	public static int[][]			COORDS			= new int[7][2];
	public static int[]				DISTANCESY		= new int[6];
	public static int[]				DISTANCESX		= new int[7];
	public static boolean			SCAN			= false, CALIBRATEPIECE = true, CALIBRATEX = true,
			CALIBRATEY = true, HASPLAYED = false, APPLY_MOVE = false, DROP_PIECE = false, CONNECT = true;;
	public static int				PIECEDETECTED	= 0, PLAYERMOVE = 0, COMPMOVE = 0, win = 0;

	public static void main(String[] args)
	{

		Behavior Connect = new Connect();
		Behavior CalY = new CalibrateY();
		Behavior Scan = new Scan();
		Behavior CalX = new CalibrateX();
		Behavior ApplyMove = new ApplyMove();
		Behavior HasPlayed = new HasPlayed();
		Behavior CalPiece = new CalibratePiece();
		Behavior DropPiece = new DropPiece();
		Behavior[] B =
		{ HasPlayed, DropPiece, ApplyMove, Scan, CalPiece, CalX, CalY, Connect };
		Arbitrator arbs = new Arbitrator(B);
		arbs.start();

	}

}
