
public class Card {

	
	public static enum Type{
		SUSPECT, ROOM, WEAPON;
	}

	public static enum Suspect{
		REV_GREEN, COLONEL_MUSTARD, MRS_PEACOCK, PROFESSOR_PLUM, MISS_SCARLET, MRS_WHITE;
	}
	public static enum Room{
		BALLROOM, BILLIARD_ROOM, CONSERVATORY, DINING_ROOM, HALL, KITCHEN, LIBRARY, LOUNGE, STUDY;
	}
	public static enum Weapon{
		CANDLE_STICK, DAGGER, LEAD, PIPE, REVOLVER, ROPE, WRENCH;
	}

	private Suspect suspect;
	private Room room;
	private Weapon weapon;
	private Type type;
	
	public Card(Suspect suspect, Room room, Weapon weapon) {
		
		this.suspect = suspect;
		this.room = room;
		this.weapon = weapon;
		
		if(room == null && suspect == null) {
			this.type = Type.WEAPON;
		}else if(room == null && weapon == null) {
			this.type = Type.SUSPECT;
		}else {
			this.type = Type.ROOM;
		}
		
	}
	
	public Type getType() {
		return this.type;
	}
	
	public Suspect getSuspect() {
		return this.suspect;
	}
	
	public Room getRoom() {
		return this.room;
	}
	
	public Weapon getWeapon() {
		return this.weapon;
	}
	
	public void setType(Type typeOfCard) {
		this.type = typeOfCard; 
	}
	
	public void setSuspect(Suspect suspectName) {
		this.suspect = suspectName;
	}
	
	public void setRoom(Room roomName) {
		this.room = roomName;
	}
	
	public void setWeapon(Weapon weaponName) {
		this.weapon = weaponName;
	}
}
