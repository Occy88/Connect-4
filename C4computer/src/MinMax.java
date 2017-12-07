
//green stuff that is code is just me debugging stuff.
public class MinMax
{
	public static final int PLAYER_X = 1, PLAYER_O = 2, FREE_SPOT = 0;

	public int minMax(int board[][], boolean comp, int difficulty, int totalDepth)
	// board is the current state of the board.
	// comp is who is playing at that iteration, difficulty is the depth that
	// changes down the tree,
	// totalDepth never changes so I don't loose myself.
	// depth and total depth start and end the same.
	{
		int[] positionValue = new int[7];// value of position at each of the 7 columns
		int[] possibleMoves = new int[7];// which columns can be played (1=playable,0=non playable or full)
		possibleMoves = possibleMoves(board);
		int turn = comp ? 1 : 2;// check who's turn it is
		// displayBoard(board);
		for (int x = 0; x < 7; x++)
		{

			if (possibleMoves[x] == 1)// if there is a possible move fill out it's positional value
			{
				board = applyMove(board, turn, x);// apply the move then remove it at the end by function removeMove

				int state = state(board);// check state win/full/loss
				if (state == 1 && comp)
				{
					positionValue[x] = 1000;// if computer win value of move maximum.dont evaluate the others (max for
											// comp) so break
					board = removeMove(board, turn, x);
					break;
				} else if (state == 2 && !comp)
				{
					positionValue[x] = -1000;// if player win value of move minimum.dont evaluate the others (min for
												// player) so break
					board = removeMove(board, turn, x);
					break;
				} else if (state == 3)
				{
					positionValue[x] = 0;// if board full => draw so move worth middle.dont evaluate the others (full)
											// so break
					board = removeMove(board, turn, x);
					break;
				} else if (difficulty == 0)
				{// if reached end of tree, evaluate the move and move onto next move
					positionValue[x] = evaluateMove(board, comp);
				} else if (difficulty != 0)
				{// if not end of tree, change the player,take away depth (go deeper), call
					// yourself , change back player, add back depth (go shallower)
					comp = !comp;
					positionValue[x] = minMax(board, comp, --difficulty, totalDepth);
					comp = !comp;
					++difficulty;
				}
				board = removeMove(board, turn, x);

			} else
			{
				if (comp)
				{
					positionValue[x] = -10000000;
				}
				if (!comp)
				{
					positionValue[x] = 1000000;
				}
				;
			}
		}
		int returnValue = -1, compareValue = -1000000, compareValue1 = 10000000;// these large values are meant to
																				// represent infinity and - infinity,
																				// making them that low is good enough.
		/// System.out.println(" ");
		// System.out.println("turn:"+ comp);
		// for (int x=0;x<7;x++)
		// {System.out.print(positionValue[x]+",");}

		if (difficulty == totalDepth)// if it's the final row find the largest column and send that back
		{
			
				
			for (int x = 0; x < 7; x++)
			{
				System.out.print(positionValue[x] + ",");
				if (positionValue[x] >= compareValue)
				{
					if (positionValue[x]==compareValue)
					{
						int random=(int) (Math.random()*3);
						if (random==2)
						{
							returnValue=x;
						}
					}
					else {compareValue = positionValue[x];
					
					returnValue = x;//compare should be greatest.
					}
				}
				
			}
		
			System.out.println("{ "+returnValue+"}");

		} else if (difficulty != totalDepth)// return the largest or smallest value depending on turn
		{
			for (int x = 0; x < 7; x++)
			{

				if (comp != true)
				{
					if (positionValue[x] < compareValue1)
					{
						compareValue1 = positionValue[x];
						returnValue = positionValue[x];
					}
				}
				if (comp == true)
				{
					if (positionValue[x] > compareValue)
					{
						compareValue = positionValue[x];
						returnValue = positionValue[x];
					}
				}
			}

		}
		// System.out.println("returned value:"+returnValue+"depth"+difficulty);
		return returnValue;

	}

	private int[][] removeMove(int[][] board, int turn, int column)
	{

		for (int x = 0; x < 6; x++)
		{
			if (board[x][column] == turn)
			{
				board[x][column] = FREE_SPOT;
				break;
			}
		}
		return board;
	}

	public int evaluateMove(int[][] board, boolean turn)// move has been placed requires value of position on board from
														// computers perspective
	// 4 in a row max/min points returned,
	// 3 with one hole, 50 points added/taken away depending on player
	// 2 with 2 holes, 3 points added/taken away depending on player
	// 1 with 3 holes , did not evaluate, did not think it is important
	{
		int[] pos = new int[4];// four consecutive positions.
		int posValue = 0;
		// check horizontal
		for (int x = 0; x < 6; x++)
		{
			for (int y = 0; y < 4; y++)// only need to check 4 positions...
			{
				for (int z = 0; z < 4; z++)// 4 in a row
				{
					pos[z] = board[x][y + z];
				}
				if (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
				{
					return 1000;
				} else if (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
				{
					return -1000;
				}
				// checking for 1 hole free
				else if ((pos[0] == FREE_SPOT && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == FREE_SPOT && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == FREE_SPOT && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == FREE_SPOT))
				{
					posValue += 50;
				} else if ((pos[0] == FREE_SPOT && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == FREE_SPOT && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == FREE_SPOT && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == FREE_SPOT))
				{
					posValue -= 50;
				}
				// checking for 2 hole free
				else if ((pos[0] == FREE_SPOT && pos[1] == FREE_SPOT && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == FREE_SPOT && pos[2] == FREE_SPOT && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == FREE_SPOT && pos[3] == FREE_SPOT))
				{
					posValue += 3;
				} else if ((pos[0] == FREE_SPOT && pos[1] == FREE_SPOT && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == FREE_SPOT && pos[2] == FREE_SPOT && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == FREE_SPOT && pos[3] == FREE_SPOT))
				{
					posValue -= 3;
				}

			}
		}
		// check vertical win
		for (int y = 0; y < 7; y++)
		{
			for (int x = 0; x < 3; x++)// only need to check 3 positions...
			{
				for (int z = 0; z < 4; z++)// 4 in a row
				{
					pos[z] = board[x + z][y];
				}
				if (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
				{
					return 1000;
				} else if (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
				{
					return -1000;
				}
				// checking for 1 hole free
				else if ((pos[0] == FREE_SPOT && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == FREE_SPOT && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == FREE_SPOT && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == FREE_SPOT))
				{
					posValue += 50;
				} else if ((pos[0] == FREE_SPOT && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == FREE_SPOT && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == FREE_SPOT && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == FREE_SPOT))
				{
					posValue -= 50;
				}
				// checking for 2 hole free
				else if ((pos[0] == FREE_SPOT && pos[1] == FREE_SPOT && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == FREE_SPOT && pos[2] == FREE_SPOT && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == FREE_SPOT && pos[3] == FREE_SPOT))
				{
					posValue += 3;
				} else if ((pos[0] == FREE_SPOT && pos[1] == FREE_SPOT && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == FREE_SPOT && pos[2] == FREE_SPOT && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == FREE_SPOT && pos[3] == FREE_SPOT))
				{
					posValue -= 3;
				}
			}
		}
		// check diagonal wins A
		for (int y = 0; y < 4; y++)
		{
			for (int x = 0; x < 3; x++)
			{

				for (int z = 0; z < 4; z++)// increment both by z
				{
					pos[z] = board[x + z][y + z];
				}
				if (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
				{
					return 1000;
				} else if (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
				{
					return -1000;
				}
				// checking for 1 hole free
				else if ((pos[0] == FREE_SPOT && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == FREE_SPOT && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == FREE_SPOT && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == FREE_SPOT))
				{
					posValue += 50;
				} else if ((pos[0] == FREE_SPOT && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == FREE_SPOT && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == FREE_SPOT && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == FREE_SPOT))
				{
					posValue -= 50;
				}
				// checking for 2 hole free
				else if ((pos[0] == FREE_SPOT && pos[1] == FREE_SPOT && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == FREE_SPOT && pos[2] == FREE_SPOT && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == FREE_SPOT && pos[3] == FREE_SPOT))
				{
					posValue += 3;
				} else if ((pos[0] == FREE_SPOT && pos[1] == FREE_SPOT && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == FREE_SPOT && pos[2] == FREE_SPOT && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == FREE_SPOT && pos[3] == FREE_SPOT))
				{
					posValue -= 3;
				}

			}
		}
		// check diagonal wins B
		for (int y = 6; y > 2; y--)
		{
			for (int x = 0; x < 3; x++)
			{

				for (int z = 0; z < 4; z++)// increment both by z
				{
					pos[z] = board[x + z][y - z];
				}
				if (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
				{
					return 1000;
				} else if (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
				{
					return -1000;
				}
				// checking for 1 hole free
				else if ((pos[0] == FREE_SPOT && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == FREE_SPOT && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == FREE_SPOT && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == FREE_SPOT))
				{
					posValue += 50;
				} else if ((pos[0] == FREE_SPOT && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == FREE_SPOT && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == FREE_SPOT && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == FREE_SPOT))
				{
					posValue -= 50;
				}
				// checking for 2 hole free
				else if ((pos[0] == FREE_SPOT && pos[1] == FREE_SPOT && pos[2] == PLAYER_X && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == FREE_SPOT && pos[2] == FREE_SPOT && pos[3] == PLAYER_X)
						|| (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == FREE_SPOT && pos[3] == FREE_SPOT))
				{
					posValue += 3;
				} else if ((pos[0] == FREE_SPOT && pos[1] == FREE_SPOT && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == FREE_SPOT && pos[2] == FREE_SPOT && pos[3] == PLAYER_O)
						|| (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == FREE_SPOT && pos[3] == FREE_SPOT))
				{
					posValue -= 3;
				}

			}
		}
		return posValue;// if non winning party return 0 i.e. no connections.
		// if position gives me 3 in a row with blank space add 10 to value of return
		// if position gives me 2 in a row with two blank spaces add 5 to value of
		// return
		// do opposite for opponent.
	}

	public int[][] createNewBoard(int board[][])
	{
		int[][] newBoard = board;
		return newBoard;
	}

	public int[][] applyMove(int[][] newBoard, int player, int position)
	{

		for (int x = 5; x >= 0; x--)
		{

			if (newBoard[x][position] == FREE_SPOT)
			{
				newBoard[x][position] = player;
				break;
			}
		}
		return newBoard;
	}

	public int[] possibleMoves(int board[][])
	{
		// return lowermost variable in any column.
		int[] freeSpace = new int[7];

		for (int y = 0; y < 7; y++)
		{
			for (int x = 5; x >= 0; x--)
			{
				if (board[x][y] == FREE_SPOT)
				{
					freeSpace[y] = 1;
					break;
				}
			}
		}

		return freeSpace;
	}

	public int state(int board[][])// if state=3 board full no connection, if state=1 player_x win,if state=2
									// player_o win,
	{
		int[] pos = new int[4];// four consecutive positions.
		// check horizontal
		for (int x = 0; x < 6; x++)
		{
			for (int y = 0; y < 4; y++)// only need to check 4 positions...
			{
				for (int z = 0; z < 4; z++)// 4 in a row
				{
					pos[z] = board[x][y + z];
				}
				if (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == PLAYER_X)// return
																											// winning
																											// party
				{
					return PLAYER_X;
				} else if (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
				{
					return PLAYER_O;
				}

			}
		}
		// check vertical win
		for (int y = 0; y < 7; y++)
		{
			for (int x = 0; x < 3; x++)// only need to check 3 positions...
			{
				for (int z = 0; z < 4; z++)// 4 in a row
				{
					pos[z] = board[x + z][y];
				}
				if (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == PLAYER_X)// return
																											// winning
																											// party
				{
					return PLAYER_X;
				} else if (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
				{
					return PLAYER_O;
				}
			}
		}
		// check diagonal wins A
		for (int y = 0; y < 4; y++)
		{
			for (int x = 0; x < 3; x++)
			{

				for (int z = 0; z < 4; z++)// increment both by z
				{
					pos[z] = board[x + z][y + z];
				}
				if (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == PLAYER_X)// return
																											// winning
																											// party
				{
					return PLAYER_X;
				} else if (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
				{
					return PLAYER_O;
				}

			}
		}
		// check diagonal wins B
		for (int y = 6; y > 2; y--)
		{
			for (int x = 0; x < 3; x++)
			{

				for (int z = 0; z < 4; z++)// increment both by z
				{
					pos[z] = board[x + z][y - z];
				}
				if (pos[0] == PLAYER_X && pos[1] == PLAYER_X && pos[2] == PLAYER_X && pos[3] == PLAYER_X)// return
																											// winning
																											// party
				{
					return PLAYER_X;
				} else if (pos[0] == PLAYER_O && pos[1] == PLAYER_O && pos[2] == PLAYER_O && pos[3] == PLAYER_O)
				{
					return PLAYER_O;
				}

			}
		}
		// check full
		int check = 0;
		for (int x = 0; x < 6; x++)
		{
			for (int y = 0; y < 7; y++)
			{
				if (board[x][y] == FREE_SPOT)
					return check;

			}
		}
		if (check == 0)
		{
			check = 3;
			return check;// returns state 3 if board full.
		}
		return 0;// if non winning party return 0 i.e. no connections.

	}

}
