import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Model {
    static HashMap<Double, Room> map;
    static Player p1 = new Player(1);
    public static Room currentRoom;

    public static StringBuilder getRoom(){
        return currentRoom.getDesc();
    }

    public static StringBuilder getDirectionList(){
        double[] tmpArr = currentRoom.getDirections();
        StringBuilder dirList = new StringBuilder();
        for (int i = 0; i < 4; i++){
            //go get the room name for the corresponding direction, add to the string with new line after
            if (tmpArr[i] == 0){
                dirList.append("Dead End\n");
            }
            else{
                dirList.append(map.get(tmpArr[i]).getRoomName());
                dirList.append("\n");
            }
        }
        return dirList;
    }

    /*[NAJEE]*/public static boolean movePlayer(String direction){
        double[] directionOptions = currentRoom.getDirections();
        boolean success = false;
        switch (direction.toLowerCase()) {
            case "north":
                if (directionOptions[0] != (double)0) {
                    currentRoom = map.get(directionOptions[0]);
                    success = true;
                }
                break;
            case "south":
                if (directionOptions[1] != (double)0) {
                    currentRoom = map.get(directionOptions[1]);
                    success = true;
                }
                break;
            case "east":
                if (directionOptions[2] != (double)0) {
                    currentRoom = map.get(directionOptions[2]);
                    success = true;
                }
                break;
            case "west":
                if (directionOptions[3] != (double)0) {
                    currentRoom = map.get(directionOptions[3]);
                    success = true;
                }
                break;
        }
        return success;
    }

    /*[All]*/public static void setup() throws FileNotFoundException {
        String fileName;
        File theFile;
        Scanner inputFile;
        HashMap<Double, Room> tmpMap = new HashMap<>();
        HashMap<Room, LinkedList<Item>> itemMap = new HashMap<>();

        //////Room setup [NAJEE]/////
        fileName = "room_data.txt";
        theFile = new File(fileName);
        inputFile = new Scanner(theFile);
        while (inputFile.hasNextLine()) {
            String name = inputFile.nextLine();
            double number = Double.parseDouble(inputFile.nextLine());
            String desc = inputFile.nextLine();

            Room tmpRoom = new Room(number, name, desc);
            tmpMap.put(tmpRoom.getRoomNumber(), tmpRoom);
            itemMap.put(tmpRoom, new LinkedList<Item>());
        }
        inputFile.close();

        /////connection setup [NAJEE]/////
        fileName = "connection_data.txt";
        theFile = new File(fileName);
        inputFile = new Scanner(theFile);
        while (inputFile.hasNextLine()) {
            double roomNumber = Double.parseDouble(inputFile.nextLine());
            double forward = Double.parseDouble(inputFile.nextLine());
            double backward = Double.parseDouble(inputFile.nextLine());
            double right = Double.parseDouble(inputFile.nextLine());
            double left = Double.parseDouble(inputFile.nextLine());

            Room tmp2 = tmpMap.get(roomNumber);
            double[] tmpArr = {forward, backward, right, left};
            tmp2.setDirections(tmpArr);
        }
        inputFile.close();

        ///// item setup [HOLLY] /////
        fileName = "item_data.txt";
        theFile = new File(fileName);
        inputFile = new Scanner(theFile);

        while(inputFile.hasNextLine()){
            // Read item name, description, and room number
            String itemName = inputFile.nextLine();
            String itemDescription = inputFile.nextLine();
            Double roomNumber = Double.parseDouble(inputFile.nextLine());
            Room itemRoom = tmpMap.get(roomNumber);
            // Create item
            Item item = new Item(itemName, itemDescription);
            // add Item to room
            itemRoom.addItem(item);

        }
        inputFile.close();

        ///// weapon item setup [HOLLY] /////
        fileName = "weapon_data.txt";
        theFile = new File(fileName);
        inputFile = new Scanner(theFile);

        while(inputFile.hasNextLine()){
            // Read item name, description, and room number
            String itemName = inputFile.nextLine();
            String itemDescription = inputFile.nextLine();
            int strengthPoints = Integer.parseInt(inputFile.nextLine());
            Double roomNumber = Double.parseDouble(inputFile.nextLine());
            Room itemRoom = tmpMap.get(roomNumber);
            // Create item
            Item item = new Weapon(itemName, itemDescription,strengthPoints);
            // add Item to room
            itemRoom.addItem(item);
            System.out.println(itemRoom.inspectRoom());
        }
        inputFile.close();


        map = tmpMap;
        currentRoom = map.get(p1.getLocation());
    }

    public static void addToInventory(String itemName){
        LinkedList<Item> currentRoomInventory = currentRoom.getRoomInventory();
        for(Item item : currentRoomInventory){
            if(item.itemName.equals(itemName)){
                p1.addToInventory(item);
            }
        }
    }

    /*[NAJEE]*/public static void quitGame(){
        System.exit(0);
    }
}
