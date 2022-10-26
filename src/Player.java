import java.util.ArrayList;
import java.util.LinkedList;

public class Player {
    private double location;

    // [HOLLY] inventory -> collection of all pick up items
    private LinkedList<Item> inventory = new LinkedList<>();

    public Player (double initRoom){
        this .location = initRoom;
    }

    public double getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }


    public void addToInventory(Item item){
        inventory.add(item);
    }

    public LinkedList<Item> getPlayerInventory(){
        return this.inventory;
    }


    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }
}
