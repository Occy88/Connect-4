
import lejos.nxt.comm.Bluetooth;
import lejos.robotics.subsumption.Behavior;

public class Connect implements Behavior
{
	
	public void action()
	{

		System.out.println("Listening");
		Game.BTLink = Bluetooth.waitForConnection();
		Game.dataOut =Game.BTLink.openDataOutputStream();
		Game.dataIn = Game.BTLink.openDataInputStream();
		System.out.println("connected");
		Game.CONNECT = false;

	}

	public void suppress()
	{

	}

	public boolean takeControl()
	{
		return Game.CONNECT;

	}
}
