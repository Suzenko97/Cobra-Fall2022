//[HOLLY]//
public class Weapon extends Item{
    int strengthPoints;

    public Weapon(String itemName, String itemDescription, int strengthPoints) {
        super(itemName, itemDescription);
        this.strengthPoints = strengthPoints;
    }

    public void pickup(Room currentRoom){ currentRoom.addItem(this);}
}
