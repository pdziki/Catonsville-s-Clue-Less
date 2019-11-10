import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.net.*;

public class Player {

    private String userName = "";
    private String avatar = "";
    private String log = "";
    private int m_playerNum = 0;
    private boolean accusation = false;

    private int avatarAccuse = 0;
    private int roomAccuse = 0;
    private int weaponAccuse = 0;

    //Minimum of two players so max hand is 9 cards
    Card hand[] = new Card[9];

    
//This key is not being used really
/******Key for reference*************************
    // public static final int SCARLET = 0;
    // public static final int GREEN   = 1;
    // public static final int MUSTARD = 2;
    // public static final int PLUM    = 3;
    // public static final int WHITE   = 4;
    // public static final int PEACOCK = 5;

    // public static final int STUDY     = 0;
    // public static final int HALL      = 1;
    // public static final int LOUNGE    = 2;
    // public static final int LIBRARY   = 3;
    // public static final int BILLIARDS = 4;
    // public static final int DINING    = 5;
    // public static final int KITCHEN   = 6;

    // public static final int ROPE        = 0;
    // public static final int PIPE        = 1;
    // public static final int KNIFE       = 2;
    // public static final int WRENCH      = 3;
    // public static final int CANDLESTICK = 4;
    // public static final int REVOLVER    = 5;
****************************************************** */

    public Player() {
    }

    public Player(String name, int playerNum ) {
        setUserName(name); 
        setPlayerNum(playerNum);
        
        switch(playerNum){
            case 0:
                avatar = "SCARLET";
            case 1:
                avatar = "GREEN";
            case 2:
                avatar = "MUSTARD";
            case 3:
                avatar = "PLUM";
            case 4:
                avatar = "WHITE";
            case 5:
                avatar = "PEACOCK";
        }
        setAvatar(avatar);
    }

//SETTERS AND GETTERS
//////////////////////////////////////////////////
    public void getHand() {
        System.out.println("Your Cards:");
        for(int i = 0; i < 9; i++){
            System.out.println(this.hand[i]);
        }
    }

    public void setHand(Card card, int index) {
        this.hand[index] = card;
    }

//////////////////////////////////////////////////
    public void getLog() {
        System.out.println("Log: " + this.log);
        System.out.println("Would you like to edit your log: (y = 1/ n = 0)");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        if(choice == 1)
            this.setLog();
    }

    public void setLog() {
        System.out.println("Enter your new log.");
        Scanner input = new Scanner(System.in);
        String newLog = input.nextLine();
        this.log = newLog;
        System.out.println("Your new log: " + this.log);
    }

//////////////////////////////////////////////////
    public String getUserName() {
        System.out.println("Player::getUserName");
        return userName;
    }
    public void setUserName(String userName) {
        System.out.println("Player::setUserName");
        this.userName = userName;
    }

//////////////////////////////////////////////////
    public String getAvatar() {
        System.out.println("Player::getAvatar");
        return avatar;
    }
    public void setAvatar(String avatar) {
        System.out.println("Player::setAvatar");
        this.avatar = avatar;
    }

//////////////////////////////////////////////////
    public int getPlayerNum() {
        System.out.println("Player::getPlayerNum");
        return m_playerNum;
    }
    public void setPlayerNum(int playerNum) {
        System.out.println("Player::setPlayerNum");
        this.m_playerNum = playerNum;
    }

//////////////////////////////////////////////////
    public int getavatarAccuse() {
        System.out.println("Player::getavatarAccuse");
        return avatarAccuse;
    }
    public int getroomAccuse() {
        System.out.println("Player::getroomAccuse");
        return roomAccuse;
    }
    public int getweaponAccuse() {
        System.out.println("Player::getweaponAccuse");
        return weaponAccuse;
    }

    public int setavatarAccuse(int avatarAccuse) {
        System.out.println("Player::setavatarAccuse");
        return this.avatarAccuse = avatarAccuse;
    }
    public int setroomAccuse(int roomAccuse) {
        System.out.println("Player::setroomAccuse");
        return this.roomAccuse = roomAccuse;
    }
    public int setweaponAccuse(int weaponAccuse) {
        System.out.println("Player::setweaponAccuse");
        return this.weaponAccuse = weaponAccuse;
    }  

//////////////////////////////////////////////////
    public boolean getAccusationResult() {
        System.out.println("Player::getAccusationResult");
        return accusation;
    }
    public void setAccusationResult(boolean accusation) {
        System.out.println("Player::setAccusationResult");
        accusation = true;
    }

//METHODS Preconditions: must be players turn
//////////////////////////////////////////////////

    public Message getGuessResult(MessageCheckSolution message){
    	if(message.cards[0] != null)
    		System.out.println("Disproved by: "+ message.cards[0]);
    	else if(message.cards[0] == null)
    		System.out.println("No one was able to disprove");
    	
    	System.out.println("Would you like to make an accusation (y = 1/ n = 0)");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        if(choice == 1)
            return this.makeAccusation();
        else
        	return new Message(6, m_playerNum);
    }
    
    public void getAccuseResult(MessageCheckSolution message){
    	if(message.cards[0] != null) {
    		System.out.println("Disproved by: "+ message.cards[0]);
    		System.out.println("YOU LOSE");
    	}
    	else
    		System.out.println("YOU WIN!");
    }
    
    
    
    
    
    // The user is asked who what and where 
    // and a message containing their guess is returned.
    public Message makeAccusation(){
    	
    	 Card personGuess = new Card(Card.Suspect.REV_GREEN,null,null);
         Card weaponGuess = new Card(null,null,Card.Weapon.CANDLE_STICK);
         Card roomGuess = new Card(null,Card.Room.BALLROOM ,null);
    	
        System.out.println("Player::makeAccusation");
                //Get the suspect suggestion from user
        boolean invalid = true; 
        while(invalid){
            System.out.println("Who do you think did it?");
            System.out.println("[1] REV_GREEN");
            System.out.println("[2] COLONEL_MUSTARD");
            System.out.println("[3] MRS_PEACOCK");
            System.out.println("[4] PROFESSOR_PLUM");
            System.out.println("[5] MISS_SCARLET");
            System.out.println("[6] MRS_WHITE");
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    personGuess.setSuspect(Card.Suspect.REV_GREEN);
                    invalid = false;
                    break;
                case 2:
                    personGuess.setSuspect(Card.Suspect.COLONEL_MUSTARD);
                    invalid = false;
                    break;
                case 3:
                    personGuess.setSuspect(Card.Suspect.MRS_PEACOCK);
                    invalid = false;
                    break;
                case 4:
                    personGuess.setSuspect(Card.Suspect.PROFESSOR_PLUM);
                    invalid = false;
                    break;
                case  5:
                    personGuess.setSuspect(Card.Suspect.MISS_SCARLET);
                    invalid = false;
                    break;
                case 6:
                    personGuess.setSuspect(Card.Suspect.MRS_WHITE);
                    invalid = false;
                    break;
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
        }

        //Get the room suggestion from user
        invalid = true; 
        while(invalid){
            System.out.println("Where do you think they did it?");
            System.out.println("[1] BALLROOM");
            System.out.println("[2] BILLIARD_ROOM");
            System.out.println("[3] CONSERVATORY");
            System.out.println("[4] DINING_ROOM");
            System.out.println("[5] HALL");
            System.out.println("[6] KITCHEN");
            System.out.println("[7] LIBRARY");
            System.out.println("[8] LOUNGE");
            System.out.println("[9] STUDY");
            
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    roomGuess.setRoom(Card.Room.BALLROOM);
                    invalid = false;
                    break;
                case 2:
                    roomGuess.setRoom(Card.Room.BILLIARD_ROOM);
                    invalid = false;
                    break;
                case 3:
                    roomGuess.setRoom(Card.Room.CONSERVATORY);
                    invalid = false;
                    break;
                case 4:
                    roomGuess.setRoom(Card.Room.DINING_ROOM);
                    invalid = false;
                    break;
                case  5:
                    roomGuess.setRoom(Card.Room.HALL);
                    invalid = false;
                    break;
                case 6:
                    roomGuess.setRoom(Card.Room.KITCHEN);
                    invalid = false;
                    break;
                case 7:
                    roomGuess.setRoom(Card.Room.LIBRARY);
                    invalid = false;
                    break;
                case 8:
                    roomGuess.setRoom(Card.Room.LOUNGE);
                    invalid = false;
                    break;
                case 9:
                    roomGuess.setRoom(Card.Room.STUDY);
                    invalid = false;
                    break;
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
        }

        //Get the weapon suggestion from user
        invalid = true; 
        while(invalid){
            System.out.println("Who do you think did it?");
            System.out.println("[1] CANDLE_STICK");
            System.out.println("[2] DAGGER");
            System.out.println("[3] LEAD");
            System.out.println("[4] PIPE");
            System.out.println("[5] REVOLVER");
            System.out.println("[6] ROPE");
            System.out.println("[7] WRENCH");
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            switch(choice){
                case 1:
                    weaponGuess.setWeapon(Card.Weapon.CANDLE_STICK);
                    invalid = false;
                    break;
                case 2:
                    weaponGuess.setWeapon(Card.Weapon.DAGGER);
                    invalid = false;
                    break;
                case 3:
                    weaponGuess.setWeapon(Card.Weapon.LEAD);
                    invalid = false;
                    break;
                case 4:
                    weaponGuess.setWeapon(Card.Weapon.PIPE);
                    invalid = false;
                    break;
                case  5:
                    weaponGuess.setWeapon(Card.Weapon.REVOLVER);
                    invalid = false;
                    break;
                case 6:
                    weaponGuess.setWeapon(Card.Weapon.ROPE);
                    invalid = false;
                    break;
                case 7:
                    weaponGuess.setWeapon(Card.Weapon.WRENCH);
                    invalid = false;
                    break;
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
        }

        MessageAccusation message1 = new MessageAccusation (personGuess, roomGuess, weaponGuess, 5);
        return message1; 
    }






    // Called directly from client.
    //The user can either pass or guess. The user is asked who what and where 
    // and a message containing their guess is returned.
    public Message makeGuess(){
        Card personGuess = new Card(Card.Suspect.REV_GREEN,null,null);
        Card weaponGuess = new Card(null,null,Card.Weapon.CANDLE_STICK);
        Card roomGuess = new Card(null,Card.Room.BALLROOM ,null);

        //Our software uses accuse variables for both guesses and accusations 
        System.out.println("Player::makeGuess");
        //returns guess or pass message
        System.out.println("Enter [1] to pass instead of guessing any other key to guess");
        // System.out.println("[1] Guess");
        // System.out.println("[2] Pass");

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        if(choice == 1)
            return new Message(6, m_playerNum);      //pass message
        
        //Get the suspect suggestion from user
        int invalid = 1; 
        while(invalid == 1){
            System.out.println("Who do you think did it?");
            System.out.println("[1] REV_GREEN");
            System.out.println("[2] COLONEL_MUSTARD");
            System.out.println("[3] MRS_PEACOCK");
            System.out.println("[4] PROFESSOR_PLUM");
            System.out.println("[5] MISS_SCARLET");
            System.out.println("[6] MRS_WHITE");
            input = new Scanner(System.in);
            choice = input.nextInt();
            switch(choice){
                case 1:
                    personGuess.setSuspect(Card.Suspect.REV_GREEN);
                    invalid = 0;
                    break;
                case 2:
                    personGuess.setSuspect(Card.Suspect.COLONEL_MUSTARD);
                    invalid = 0;
                    break;
                case 3:
                    personGuess.setSuspect(Card.Suspect.MRS_PEACOCK);
                    invalid = 0;
                    break;
                case 4:
                    personGuess.setSuspect(Card.Suspect.PROFESSOR_PLUM);
                    invalid = 0;
                    break;
                case  5:
                    personGuess.setSuspect(Card.Suspect.MISS_SCARLET);
                    invalid = 0;
                    break;
                case 6:
                    personGuess.setSuspect(Card.Suspect.MRS_WHITE);
                    invalid = 0;
                    break;
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
        }

        //Get the room suggestion from user
        invalid = 1; 
        while(invalid == 1){
            System.out.println("Where do you think they did it?");
            System.out.println("[1] BALLROOM");
            System.out.println("[2] BILLIARD_ROOM");
            System.out.println("[3] CONSERVATORY");
            System.out.println("[4] DINING_ROOM");
            System.out.println("[5] HALL");
            System.out.println("[6] KITCHEN");
            System.out.println("[7] LIBRARY");
            System.out.println("[8] LOUNGE");
            System.out.println("[9] STUDY");
            
            input = new Scanner(System.in);
            choice = input.nextInt();
            switch(choice){
                case 1:
                    roomGuess.setRoom(Card.Room.BALLROOM);
                    invalid = 0;
                    break;
                case 2:
                    roomGuess.setRoom(Card.Room.BILLIARD_ROOM);
                    invalid = 0;
                    break;
                case 3:
                    roomGuess.setRoom(Card.Room.CONSERVATORY);
                    invalid = 0;
                    break;
                case 4:
                    roomGuess.setRoom(Card.Room.DINING_ROOM);
                    invalid = 0;
                    break;
                case  5:
                    roomGuess.setRoom(Card.Room.HALL);
                    invalid = 0;
                    break;
                case 6:
                    roomGuess.setRoom(Card.Room.KITCHEN);
                    invalid = 0;
                    break;
                case 7:
                    roomGuess.setRoom(Card.Room.LIBRARY);
                    invalid = 0;
                    break;
                case 8:
                    roomGuess.setRoom(Card.Room.LOUNGE);
                    invalid = 0;
                    break;
                case 9:
                    roomGuess.setRoom(Card.Room.STUDY);
                    invalid = 0;
                    break;
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
        }

        //Get the weapon suggestion from user
        invalid = 1; 
        while(invalid == 1){
            System.out.println("Who do you think did it?");
            System.out.println("[1] CANDLE_STICK");
            System.out.println("[2] DAGGER");
            System.out.println("[3] LEAD");
            System.out.println("[4] PIPE");
            System.out.println("[5] REVOLVER");
            System.out.println("[6] ROPE");
            System.out.println("[7] WRENCH");
            input = new Scanner(System.in);
            choice = input.nextInt();
            switch(choice){
                case 1:
                    weaponGuess.setWeapon(Card.Weapon.CANDLE_STICK);
                    invalid = 0;
                    break;
                case 2:
                    weaponGuess.setWeapon(Card.Weapon.DAGGER);
                    invalid = 0;
                    break;
                case 3:
                    weaponGuess.setWeapon(Card.Weapon.LEAD);
                    invalid = 0;
                    break;
                case 4:
                    weaponGuess.setWeapon(Card.Weapon.PIPE);
                    invalid = 0;
                    break;
                case  5:
                    weaponGuess.setWeapon(Card.Weapon.REVOLVER);
                    invalid = 0;
                    break;
                case 6:
                    weaponGuess.setWeapon(Card.Weapon.ROPE);
                    invalid = 0;
                    break;
                case 7:
                    weaponGuess.setWeapon(Card.Weapon.WRENCH);
                    invalid = 0;
                    break;
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
        }

        MessageAccusation message = new MessageAccusation (personGuess, roomGuess, weaponGuess, 4);
        return message;        
    }



    //Asks user which direction they want to move in and returns message with that movement
    public Message move(){
        System.out.println("Player::move");
        while(true){
            System.out.println("[1] UP");
            System.out.println("[2] DOWN");
            System.out.println("[3] LEFT");
            System.out.println("[4] RIGHT");
            System.out.println("[5] DIAGONAL");
            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            
            Message message = new Message(3, m_playerNum);
            
            switch(choice){
                case 1:
                	message.setInt(1);
                    break;
                case 2:
                	message.setInt(2);
                    break;
                case 3:
                	message.setInt(3);
                    break;
                case 4:
                	message.setInt(4);
                    break;
                case 5:
                	message.setInt(5);
                    break;
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
            return message;
        }
    }

    
    

    //Called directly from Client. Compares the cards within the message to players hand.
    public Message disprove(MessageAccusation message){
        System.out.println("Player::disprove");
        Card personDisprove = new Card(null, null, null);
        Card weaponDisprove = new Card(null, null, null);
        Card roomDisprove = new Card(null, null, null);
        for(int i = 0; i < 9; i++){
            if(this.hand[i] == message.getSuspect())
                personDisprove = message.getSuspect();
            if(this.hand[i] == message.getWeapon())
                weaponDisprove = message.getWeapon();
            if(this.hand[i] == message.getRoom());
                roomDisprove = message.getRoom();
        }

        while(true){
            if(personDisprove != null)
                System.out.println("[1] Disprove with: "+personDisprove);
            if(weaponDisprove != null)
                System.out.println("[2] Disprove with: "+weaponDisprove);
            if(roomDisprove != null)
                System.out.println("[3] Disprove with: "+roomDisprove);

            if(roomDisprove == null && weaponDisprove == null && personDisprove == null){
                System.out.println("You cannot disprove this guess");
                MessageCheckSolution cannotDisprove = new MessageCheckSolution(false, null);      //pass message
                cannotDisprove.setPlayer(m_playerNum);
                return cannotDisprove;
            }

            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();
            
            MessageCheckSolution canDisprove = new MessageCheckSolution(true, null);      //pass message
            //canDisprove.incorrectCards
            
            switch(choice){
                case 1:
                    //canDisprove.
                    break;
                case 2:
                    //return new Message(weaponDisprove);
                    break;
                case 3:
                    //return new Message(roomDisprove);
                    break;
                default:
                    System.out.println("INVALID ENTRY TRY AGAIN");
                    break;
            }
            
            return canDisprove;
        }
        
    }



    //Called directly from client
    public Message playerTurn(){
        while(true){
            System.out.println("Player::playerTurn");
            System.out.println("[1] Move");
            System.out.println("[2] Make Accusation");
            System.out.println("[3] View Hand");
            System.out.println("[4] View/Edit Log");
            System.out.println("[5] Pass");
            //System.out.println("[5] Disprove");       

            Scanner input = new Scanner(System.in);
            int choice = input.nextInt();

            switch(choice){
            case 1:
                return this.move();
            case 2:
                return this.makeAccusation();
            case 3:
                this.getHand();
                break;
            case 4:
                this.getLog();
                break;
            case 5:
                return new Message(6, m_playerNum);      //pass message
            default:
                System.out.println("INVALID ENTRY TRY AGAIN");
                break;
            }
        }
    }
}
