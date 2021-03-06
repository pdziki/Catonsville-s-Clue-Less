import java.util.ArrayList;
import java.util.Arrays; 

class Board
{
	private class Location
	{
		private int locationNumber;
		private String locationName;
		private boolean isHallway;
		private boolean isOccupied;
		private int up;
		private int down;
		private int left;
		private int right;
		private int secretPassage;

		public Location( int locationNumber, String locationName, boolean isHallway, boolean isOccupied, int up, int down, int left, int right, int secretPassage )
		{
			this.locationNumber = locationNumber;
			this.locationName = locationName;
			this.isHallway = isHallway;
			this.isOccupied = isOccupied;
			this.up = up;
			this.down = down;
			this.left = left;
			this.right = right;
			this.secretPassage = secretPassage;
		}

		public String getLocationName()
		{
			return this.locationName;
		}

		public boolean getIsHallway()
		{
			return this.isHallway;
		}

		public boolean getIsOccupied()
		{
			return this.isOccupied;
		}

		public int getUp()
		{
			return this.up;
		}

		public int getDown()
		{
			return this.down;
		}

		public int getLeft()
		{
			return this.left;
		}

		public int getRight()
		{
			return this.right;
		}

		public int getSecretPassage()
		{
			return this.secretPassage;
		}

		public void setIsOccupied( boolean isOccupiedStatus )
		{
			this.isOccupied = isOccupiedStatus;
		}			
	}

/**
* locationArray: Store all 21 locations (9 rooms and 12 hallways) and their attributes
* playerPosition: Store the position of players
* locationMatrix: Store the next possible position a player can move to 
*/


	Location[] locationArray = new Location[21];
	int[] playerPosition = new int[6];
	String[] playerAvatar = new String[6];
	String description;

	public Board()
	{
		locationArray[0] = new Location( 0, "STUDY", false, false, -1, 11, -1, 9, 8 );
		locationArray[1] = new Location( 1, "HALL", false, false, -1, 12, 9, 10, -2 );
		locationArray[2] = new Location( 2, "LOUNGE", false, false, -1, 13, 10 , -1, 6  );
		locationArray[3] = new Location( 3, "LIBRARY", false, false, 11, 16, -1, 14, -2  );
		locationArray[4] = new Location( 4, "BILLIARD_ROOM", false, false, 12, 17, 14, 15, -2 );
		locationArray[5] = new Location( 5, "DINING_ROOM", false, false, 13, 18, 15, -1, -2 );
		locationArray[6] = new Location( 6, "CONSERVATORY", false, false, 16, -1, -1, 19, 2 );
		locationArray[7] = new Location( 7, "BALLROOM", false, false, 17, -1, 19, 20, -2 );
		locationArray[8] = new Location( 8, "KITCHEN", false, false, 18, -1, 20, -1, 0 );
		locationArray[9] = new Location( 9, "HALLWAY BETWEEN STUDY AND HALL", true, false, -1, -1, 0, 1, -2 );
		locationArray[10] = new Location( 10, "HALLWAY BETWEEN HALL AND LOUNGE", true, true, -1, -1, 1, 2, -2 );
		locationArray[11] = new Location( 11, "HALLWAY BETWEEN STUDY AND LIBRARY", true, true, 0, 3, -1, -1, -2 );
		locationArray[12] = new Location( 12, "HALLWAY BETWEEN HALL AND BILLIARD_ROOM", true, false, 1, 4, -1, -1, -2 );
		locationArray[13] = new Location( 13, "HALLWAY BETWEEN LOUNGE AND DINING_ROOM", true, true, 2, 5, -1, -1, -2 );
		locationArray[14] = new Location( 14, "HALLWAY BETWEEN LIBRARY AND BILLIARD_ROOM", true, false, -1, -1, 3, 4, -2 );
		locationArray[15] = new Location( 15, "HALLWAY BETWEEN BILLIARD_ROOM AND DINING_ROOM", true, false, -1, -1, 4, 5, -2 );
		locationArray[16] = new Location( 16, "HALLWAY BETWEEN LIBRARY AND CONSERVATORY", true, true, 3, 6, -1, -1, -2 );
		locationArray[17] = new Location( 17, "HALLWAY BETWEEN BILLIARD_ROOM AND BALLROOM", true, false, 4, 7, -1, -1, -2 );
		locationArray[18] = new Location( 18, "HALLWAY BETWEEN DINING_ROOM AND KITCHEN", true, false, 5, 8, -1, -1, -2 );
		locationArray[19] = new Location( 19, "HALLWAY BETWEEN CONSERVATORY AND BALLROOM", true, true, -1, -1, 6, 7, -2 );
		locationArray[20] = new Location( 20, "HALLWAY BETWEEN BALLROOM AND KITCHEN", true, true, -1, -1, 7, 8, -2 );

		playerPosition[0] = 10;
		playerPosition[1] = 19;
		playerPosition[2] = 13;
		playerPosition[3] = 11;
		playerPosition[4] = 20;
		playerPosition[5] = 16;

		playerAvatar[0] = "SCARLET";
		playerAvatar[1] = "GREEN";
		playerAvatar[2] = "MUSTARD";
		playerAvatar[3] = "PLUM";
		playerAvatar[4] = "WHITE";
		playerAvatar[5] = "PEACOCK";
	}

	public int getPlayerPosition( int playerNumber )
	{
		return playerPosition[playerNumber];
	}
	
	public void setPlayerPosition( int playerNumber, int playerNewPosition )
	{
		this.playerPosition[playerNumber] = playerNewPosition;
	}
	
	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public String toString()
	{
		return getDescription();
	}

	public Message processMove( Message moveMessage )
	{
		int playerNumber = moveMessage.getPlayer();
		int direction = moveMessage.getInt();

		int playerCurrentPosition = getPlayerPosition( playerNumber );	
		int playerNextPosition = -1;
		String returnText;
		Message returnMessage;

		switch( direction )
		{
			case 1: 
			{
				playerNextPosition = this.locationArray[playerCurrentPosition].getUp();
				break;
			}
			case 2:
			{
				playerNextPosition = this.locationArray[playerCurrentPosition].getDown();
				break;
			}
			case 3:
			{
				playerNextPosition = this.locationArray[playerCurrentPosition].getLeft();
				break;
			}
			case 4:
			{
				playerNextPosition = this.locationArray[playerCurrentPosition].getRight();
				break;
			}
			case 5:
			{
				playerNextPosition = this.locationArray[playerCurrentPosition].getSecretPassage();
				break;
			}				
		}

		if( playerNextPosition == -1 )
		{
			returnText = new String( " move is invalid because player cannot move out of the boundary." );
			
			returnMessage = new Message( 17, playerNumber, 0, returnText );
		}

		else if( playerNextPosition == -2 )
		{
			returnText = new String (" move is invalid because no secret passages are found in the current location." );

			returnMessage = new Message( 17, playerNumber, 0, returnText );
		}

		else if( this.locationArray[playerNextPosition].getIsOccupied() && this.locationArray[playerNextPosition].getIsHallway() )
		{
			returnText = new String( " move is invalid because player cannot move into an occupied hallway." );

			returnMessage = new Message( 17, playerNumber, 0, returnText );
		}

		else
		{
			int canGuess;
			returnText = new String( " move is successful and position is updated." );
			
			if( this.locationArray[playerNextPosition].getIsHallway() )
			{
				canGuess = 0;
			}
			else
			{
				canGuess = 1;
			}

			returnMessage = new Message (16, playerNumber, canGuess, returnText );

			this.locationArray[playerCurrentPosition].setIsOccupied( false );
			this.locationArray[playerNextPosition].setIsOccupied( true );
			playerPosition[playerNumber] = playerNextPosition;
		}

		setDescription( "Player " + ( playerNumber+1 ) + "'s" + returnText );

		return returnMessage;
	}

	public Message processGuess( MessageAccusation guessMessage )
	{
		int playerMakingGuess = guessMessage.getPlayer();
		String crimeLocation = guessMessage.getRoom().toString();
		
		int returnInt;
		String returnText;
		Message returnMessage;

		if ( crimeLocation.equalsIgnoreCase( this.locationArray[playerPosition[playerMakingGuess]].locationName ))
		{
			int playerUnderGuess = -1;

			switch( guessMessage.getSuspect().getSuspect() )
			{
				case MISS_SCARLET:
				{
					playerUnderGuess = 0;
					break;
				}
				case REV_GREEN:
				{
					playerUnderGuess = 1;
					break;	
				}			
				case COLONEL_MUSTARD:
				{	
					playerUnderGuess = 2;
					break;
				}
				case PROFESSOR_PLUM:
				{
					playerUnderGuess = 3;
					break;
				}
				case MRS_WHITE:
				{
					playerUnderGuess = 4;
					break;
				}
				case MRS_PEACOCK:
				{
					playerUnderGuess = 5;
					break;
				}
			}

			returnText = new String( " room of the guess is the same as room of the player. Valid guess." );
			returnInt = 20;

			playerPosition[playerUnderGuess] = playerPosition[playerMakingGuess];
		}

		else
		{
			returnText = new String( " room of the guess is different from the room of the player. Invalid guess." );
			returnInt = 21;
		}

		returnMessage = new Message( returnInt, playerMakingGuess, 0, returnText );

		return returnMessage;			
	}

	public String getStatus()
	{
		String positionName;
		String avatarName;
		String status = new String();

		for( int i = 0; i < 6; i++ )
		{
			avatarName = this.playerAvatar[i];
			positionName = this.locationArray[playerPosition[i]].locationName;

			status += "Player " + ( i+1 ) + " (" + avatarName + ") is at " + positionName + ".\n";
		}

		return status;
	}
	
	public MessageGUIUpdate getGUIUpdate()
	{
		int messageType = 12;
		int whichPlayer = -1;

		MessageGUIUpdate returnMessage = new MessageGUIUpdate(messageType, whichPlayer, playerPosition);
		returnMessage.setText(Arrays.toString(playerPosition));

		return returnMessage;
	}
}
