import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

import java.net.*;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.TextArea;


/* Client is the driver of the game on client computers. It takes two arguments (IP and port) and connects to the
server.
*/

public class Client{
	
	static GUI thisGUI;
	static String userName;


	public static void main(String[] args) throws IOException, ClassNotFoundException{
		
		// check to make sure we got the args
		/*
		if (args.length != 2) {
			System.err.println("Missing arugments. java PlayerClient <hostname> <port>");
			System.exit(1);
		}
		*/
		
		//get host
		String host = JOptionPane.showInputDialog("Enter the host IP address");

		
		
		// parse host
		//String host = args[0];
		// parse port
		int port;
		{
			String tempPort = JOptionPane.showInputDialog("Enter the host port");
			port = Integer.parseInt(tempPort);
		}

		
		Socket gameSocket = new Socket(host, port);
		
		Scanner inScn = new Scanner(System.in);
		
		// get input and output streams
		//define input and output streams
		ObjectOutputStream out = new ObjectOutputStream( gameSocket.getOutputStream());
		ObjectInputStream in = new ObjectInputStream( gameSocket.getInputStream());
		
		// accept connectionStatus message from Server
		MessageConnectionStatus incomingMessage = (MessageConnectionStatus) getMessage(in);
		
		// call startUp and store it as outgoingMessage
		MessageConnectionStatus outgoingMessage = startUp(incomingMessage, inScn);
		
		// what player is this
		int playerNum = outgoingMessage.getPlayer();
		
		// send output message
		sendMessage(outgoingMessage, out, playerNum);

		//create Player with info from outgoingMessage
		Player currentPlayer = new Player(outgoingMessage.getText(), outgoingMessage.getPlayer());
		
		// create, launch, and grab reference to GUI
		/*Uses this method:
		 * https://stackoverflow.com/questions/25873769/launch-javafx-application-from-another-class
		 */
		
		new Thread() {
			@Override
			public void run() {
				javafx.application.Application.launch(GUI.class);
			}
		}.start();
		
		thisGUI = GUI.waitForGUI();
		
		// run a While loop until agmeOver = true;
		
		boolean gameOver = false;
		
		while( gameOver == false) {
			gameOver = playGame(currentPlayer, in, out, thisGUI);
		}
		
		// close the socket
		gameSocket.close();
		
	}
	
	
	/* startUp takes the MessageConnectionStatus from Server and creates a return MEssageConnectionStatus
	 * 
	 */
	private static MessageConnectionStatus startUp( MessageConnectionStatus inMessage, Scanner inScn) {
		// create outgoing Message
		MessageConnectionStatus outMessage = new MessageConnectionStatus();
		
		// ask the player their username
//		System.out.println("Please enter a username.");
//		outMessage.setText( inScn.nextLine());
		
        String userInput;
        userInput = JOptionPane.showInputDialog("Please enter a username.");
        userName = userInput;
        outMessage.setText(userInput);
        
		// are we player 1?
		if( inMessage.getInt() == 1) {
//			// if this is 1st client we ask how many players to wait for
//			System.out.println("You are the 1st player to connect. How many players do you wish to play with?");
//			// store users response as the genericInt in outgoingMessage
//			outMessage.setInt(inScn.nextInt());
			
			// if this is 1st client we ask how many players to wait for
			userInput = JOptionPane.showInputDialog("You are the 1st player to connect. How many players do you wish to play with?");
            int choice = Integer.parseInt(userInput);
			// store users response as the genericInt in outgoingMessage
			outMessage.setInt(choice);
			
		}
		// grab the avatar array from inMessage
		boolean [] tempAvatars = inMessage.getAvatars();
		
		// what player does user want to be
//		System.out.println("Which character do you want to be?");
		
		
		// figure out which avatars are available. We start with the 1st line asking players choice
		String availableAvatars = "Which character do you want to be?\n";
		//then we loop through the array we got from inMessage and add to String with whichecer ones
		// are available
		for( int i = 0; i < 6; i++) {
			// check if that character is available
					
			if (tempAvatars[i] == false) {
				switch( i ) {
					case 0:
					{
						availableAvatars = availableAvatars + "0: Miss Scarlett\n";
						break;
					}
					case 1:
					{
						availableAvatars = availableAvatars + "1: Reverend Green\n";
						break;
					}
					case 2:
					{
						availableAvatars = availableAvatars + "2: Colonel Mustard\n";
						break;
					}
					case 3:
					{
						availableAvatars = availableAvatars + "3: Professor Plum\n";
						break;
					}
					case 4:
					{
						availableAvatars = availableAvatars + "4: Mrs. White\n";
						break;
					}
					case 5:
					{
						availableAvatars = availableAvatars + "5: Mrs. Peacock\n";
						break;
					}
				}
			}		
		}
		String userInput2;
		userInput2 = JOptionPane.showInputDialog(availableAvatars);
		
		//grab the players choice
//		int playerChoice = inScn.nextInt();
		int playerChoice = Integer.parseInt(userInput2);
				
		//set that element to true
		tempAvatars[playerChoice] = true;
		
		// set whichPlayer to playerChoice
		outMessage.setPlayer(playerChoice);
		// write the new array to outMessage
		outMessage.setAvatars(tempAvatars);
		
		return outMessage;
		
	}
	
	/* 
	 * 
	 */
	private static boolean playGame(Player thisPlayer, ObjectInputStream in, ObjectOutputStream out, GUI givenGUI) throws IOException, ClassNotFoundException{
		// store a boolean flag that we return
		boolean gameOver = false;
				
		
		// receive a message from the server
		Message inMessage = getMessage(in);
		
		
		// run a switch where we handle the Message as appropriate
		switch( inMessage.getType()) {
			// type 2 means its this players turn
			case 2:
			{
				// invoke the processTurn method that returns boolean for gameOver?
				processTurn(thisPlayer, in, out, givenGUI);
				break;
			}
			// type 11 means its a status message, for now print to screen
			
			// type 9 means this is a deal message, pass to player
			case 9:
			{
				//cast to MessageDeal
				inMessage = (MessageDeal) inMessage;
				thisPlayer.setHand((MessageDeal) inMessage);
				thisPlayer.getHand();
				((MessageDeal) inMessage).displayCards();
				break;
			}
			// type 10 means this is board status update
			case 10:
			{
				// do something with board status update?
				break;
			}
			// case 13 means someone else guessed and we have to disprove it
			case 4:
			{
				// cast it to MessageAccusation
				Message guessResult = thisPlayer.disprove( (MessageAccusation) inMessage);
				//cast it
				MessageCheckGuess guessResultCasted = (MessageCheckGuess) guessResult;
				//error check
				System.out.println("Result of guess check is " + guessResultCasted.getDisproven());
				sendMessage(guessResultCasted, out, thisPlayer.getPlayerNum());
				break;
			}
			// case 14 means game is over
			case 14:
			{
				gameOver = true;
				//game is over so we exit
				System.exit(0);
				break;
			}
			// case 7 means we win
			case 7:
			{
				System.out.println("You win!");
				JOptionPane.showMessageDialog(null, "YOU WIN!");
				break;
			}
			// case 8 means we lose
			case 8:
			{
				System.out.println("You lose!");
				JOptionPane.showMessageDialog(null, "YOU LOSE\n" + inMessage.getText());
				break;
			}
			
		}
		
		return gameOver;
		
	}
	
	// write a method that processes a players turn
	private static void processTurn(Player thisPlayer, ObjectInputStream in, ObjectOutputStream out, GUI givenGUI) throws IOException, ClassNotFoundException{
		// boolean for turnOver
		boolean turnOver = false;
		// boolean for gameOver, only sets to true is acc
		// run this loop until turnOver is true
		while( turnOver == false) {
			// get a Message for the start of the players turn
			Message firstAction = thisPlayer.playerTurn();
			
			// is it a move?
			if (firstAction.getType() == 3 ) {
				// send that to the client
				sendMessage(firstAction, out, thisPlayer.getPlayerNum());
				// get Message back
				Message moveResult = getMessage(in);
				// check to see if it was a valid move
				if ( moveResult.getType() == 16 ) {
					// if it was a valid move can they make a guess?
					if ( moveResult.getInt() == 1 ) {
						// after a valid move they're only going down one of these cases and then turn is over
						turnOver = true;
						Message guessMessage = thisPlayer.makeGuess();
						// did player make a guess, an accusation, or pass?
						switch( guessMessage.getType() ) {
							//case 4 means they made a guess
							case 4:
							{		
								//send guesMessage to server
								sendMessage(guessMessage, out, thisPlayer.getPlayerNum());
								
								// was guess valid?
								Message guessValidity = (Message) getMessage(in);
								if( guessValidity.getType() == 21) {
									System.out.println("Invalid guess, you're not in that room.");
									JOptionPane.showMessageDialog(null, "Invalid guess, you're not in that room.");
									//send back a pass message but we probably want to actually trigger another
									Message passMessage = new Message(6, thisPlayer.getPlayerNum());
									sendMessage(passMessage, out, thisPlayer.getPlayerNum());
									break;
								}
								// if guess was valid
								else {
									// receive result back
									MessageCheckGuess resultMessage = (MessageCheckGuess) getMessage(in);
									
									//send result to player and store return in finalAct
									Message finalAct = thisPlayer.getGuessResult(resultMessage);
									
									// did player Accuse?
									if ( finalAct.getType() == 5) {
										
										//send accusage method to server
										sendMessage(finalAct, out, thisPlayer.getPlayerNum());
										System.out.println("Send accusation to Server.");
										// get result
										Message accuseResult = (Message) getMessage(in);
										{
											boolean gotResult = false;
											while( gotResult == false) {
												if( accuseResult.getType() == 18) {
													gotResult = true;
												}
											}
										}
										System.out.println("Received result from Server");
										// pass result to Player
										thisPlayer.getAccuseResult((MessageCheckSolution) accuseResult);
									}
									// if player didn't accuse they massed
									else {
										//send pass message
										sendMessage(finalAct, out, thisPlayer.getPlayerNum());
									}
								}
								break;	
							}
							// case 5 means they are making an accusation
							case 5:
							{
								//send accusage method to server
								sendMessage(guessMessage, out, thisPlayer.getPlayerNum());
								// get result
								MessageCheckSolution accuseResult = (MessageCheckSolution) getMessage(in);
								// pass result to Player
								thisPlayer.getAccuseResult(accuseResult);
								break;
							}
							// case 6 means they're passing
							case 6:
							{
								// send it to Server
								Message passMessage = new Message(6, thisPlayer.getPlayerNum());
								sendMessage(passMessage, out, thisPlayer.getPlayerNum());
								break;
							}		
						}
					}
					// if they can't make a guess they can only pass
					else {
						// turn is over
						turnOver = true;
						Message passMessage = new Message(6, thisPlayer.getPlayerNum());
						sendMessage(passMessage, out, thisPlayer.getPlayerNum());
					}
				}
				else {
				// if it wasn't a valid move we do nothing and this loop repeats
				//System.out.println("Invalid move requested, starting turn over again");
				JOptionPane.showMessageDialog(null, "Invalid move requested, starting turn over again");
				
				}
			}
			// is it a accusation?
			else if( firstAction.getType() == 5) {
				//turn is over
				turnOver = true;
				// send accusation to server
				sendMessage(firstAction, out, thisPlayer.getPlayerNum());
				// get result
				
				MessageCheckSolution accuseResult = (MessageCheckSolution) getMessage(in);
				// pass result to Player
				thisPlayer.getAccuseResult(accuseResult);
			}
			// if its not a move or accusation its a pass
			else {
				// pass means turn is over
				turnOver = true;
				// send it to Server
				Message passMessage = new Message(6, thisPlayer.getPlayerNum());
				sendMessage(passMessage, out, thisPlayer.getPlayerNum());
			}
		}
	}
	
	private static void sendMessage(Message outMessage, ObjectOutputStream out, int thisPlayer) throws IOException{
		//System.out.println("Send Message of Type " + outMessage.getType());
		outMessage.setPlayer(thisPlayer);
		out.writeObject(outMessage);
		out.flush();
	}
	
	private static Message getMessage(ObjectInputStream in) throws IOException, ClassNotFoundException{
		
		//if its a board update or a status update we should print it straight to gui
		//set boolean flag for whether we should return
		boolean passToPlayer = false;
		//declare and initialize inMessage but it will be overwritten by the below loop
		Message inMessage = new Message();
		
		while(!passToPlayer) {
			//grab message
			inMessage = (Message) in.readObject();
			//run a switch on the message type
			switch(inMessage.getType()) {
				//case 12 is board status update
				case 12:
				{
					//Pete's method for parsing String to int array
					int counter = 0;
					int position[] = new int[6];
					String arr = inMessage.getText();
					arr = arr.substring(1, arr.length()-1);
					arr = arr.replace(",", "");
					System.out.println(arr);
					for(int i =0; i<arr.length()-1; i++) {
						if(i >0 && arr.charAt(i-1)!=' ') {
							continue;
						}
						String hold = "";
						char c = arr.charAt(i);
						char nextChar = arr.charAt(i+1);
						if(c == ' ') {
							continue;
						}
						if(nextChar != ' ') {
							hold+= c;
							hold+= nextChar;
						}else {
							hold +=c;
						}
						System.out.println(hold);
						int x = Integer.parseInt(hold);
						position[counter] = x;
						counter++;
					}
					MessageGUIUpdate castedMessage = (MessageGUIUpdate) inMessage;
					castedMessage.setPlayerNewPosition(position);
					thisGUI.setAvatarPosition((MessageGUIUpdate) castedMessage);
					break;
				}
				//case 11 is status update we should send to the updateArea
				case 11:
				{
					sendStatus(thisGUI, inMessage.getText());
					
					break;
				}
				// otherwise we just return it
				default:
				{
					//the return should break the method but just in case...
					passToPlayer = true;
					
				}
			}
		}
		return inMessage;
	}
	
	// method to update the gui 
	private static void sendStatus(GUI givenGUI, String newString) {
		//send String to updateArea in gui
		givenGUI.printStatus(newString);
	}
}
