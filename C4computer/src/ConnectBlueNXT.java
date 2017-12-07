import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;

import lejos.pc.comm.NXTInfo;
import lejos.util.Delay;
import lejos.pc.comm.NXTCommBluecove;
import lejos.pc.comm.NXTCommException;

public class ConnectBlueNXT
{
	public static int[][]		game	= new int[6][7];
	public static int			move;
	public static int			transmissionReceived;
	public static byte[]		b;
	public static Clip			clip;
	public static LineListener	ll;
	public static LineEvent		le;

	public static void main(String[] args) throws Exception

	{
		for (int n = 0; n < 16; n++)
		{
			play(song(n));
			clip.close();
			System.out.println(n);
		}

	

		int y1 = (int) (Math.random() * 16);
		play(song(y1));

		NXTInfo nxtInfo = new NXTInfo();
		nxtInfo.deviceAddress = "00165308F4C2";
		NXTCommBluecove com = new NXTCommBluecove();
		InputStream in = com.getInputStream();
		OutputStream out = com.getOutputStream();
		try
		{
			while (!com.open(nxtInfo))
			{
				System.out.println("Connecting");
				Delay.msDelay(2000);
			}
		} catch (NXTCommException e)
		{
			e.printStackTrace();
		}

		// START GAME:

		draw();

		while (true)
		{

			if (clip.getMicrosecondPosition() >= clip.getMicrosecondLength())// play next song
			{
				clip.close();
				y1 = (int) (Math.random() * 16);
				play(song(y1));
				System.out.println(y1);

			}
			move = toInt(in.read());// receive player move here
			if (clip.getMicrosecondPosition() >= clip.getMicrosecondLength())// play next song
			{
				clip.close();
				y1 = (int) (Math.random() * 16);
				play(song(y1));
				System.out.println(y1);

			}

			System.out.println("reveived" + move);

			applyMove();
			draw();

			move = new MinMax().minMax(game, true, 7, 7);// final two values are depth, first is changed // second stays
		
			if (clip.getMicrosecondPosition() >= clip.getMicrosecondLength())// play next song
			{
				clip.close();
				y1 = (int) (Math.random() * 16);
				play(song(y1));
				System.out.println(y1);

			}

			applyCompMove();
			int a = new MinMax().state(game);

			b = toByte(move);// convert to byte to send
			System.out.println("sending"+move);
			out.write(b);
			out.flush();
			if (a == 1)
			{
				clip.close();
				play("C:\\Users\\octav\\Desktop\\LejosMusic\\win.wav");
				System.out.println("You suck how could you loose to this shit");
				b = toByte(a);
				System.out.println("sending state " + a);
				out.write(b);
				out.flush();
				break;
			} else if (a == 2)
			{
				clip.close();
				play("C:\\Users\\octav\\Desktop\\LejosMusic\\loose.wav");
				System.out.println("What took you so long you fucking noob");
				b = toByte(a);
				System.out.println("sending state " + a);
				out.write(b);
				out.flush();
				break;
			} else if (a == 3)
			{
				clip.close();
				play("C:\\Users\\octav\\Desktop\\LejosMusic\\loose.wav");

				System.out.println("well what a waste of time");
				b = toByte(a);
				System.out.println("sending state " + a);
				out.write(b);
				out.flush();
				break;
			}

			a = 0;

			b = toByte(a);
			out.write(b);
			System.out.println("sending state 0");
			out.flush();

			draw();

		}

		System.out.println("receiving info");
		transmissionReceived = in.read();
		System.out.println(transmissionReceived);

		com.close();
		System.out.println("closed");
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

	public static void play(String file)
	{
		try
		{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(file));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			clip.addLineListener(ll);

		} catch (Exception ex)
		{
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}

	}

	public static void applyMove()
	{
		for (int x = 5; x >= 0; x--)
		{
			if (game[x][move] == 0)
			{

				game[x][move] = 2;
				break;
			}
		}
	}

	public static void applyCompMove()
	{
		for (int x = 5; x >= 0; x--)
		{
			if (game[x][move] == 0)
			{

				game[x][move] = 1;
				break;
			}
		}
	}

	public static void draw()
	{
		for (int x = 0; x < 6; x++)// draw
		{
			System.out.println(" ");
			System.out.print("|");
			for (int y = 0; y < 7; y++)
			{
				if (game[x][y] == 0)
				{
					System.out.print("   ");
				}
				if (game[x][y] == 1)
				{
					System.out.print(" X ");
				}
				if (game[x][y] == 2)
				{
					System.out.print(" O ");
				}
				System.out.print("|");

			}
		}
		System.out.println(" ");
		System.out.print("|");
		for (int x = 1; x < 8; x++)
		{
			System.out.print(" " + x + " |");
		}
	}

	public static String song(int n)
	{
		n++;
		return "C:\\Users\\octav\\Desktop\\LejosMusic\\"+n+".wav";
	}
}
