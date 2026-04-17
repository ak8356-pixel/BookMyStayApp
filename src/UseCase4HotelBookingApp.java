import java.util.HashMap;
import java.util.Map;

/**
 * =========================================================
 * MAIN CLASS - UseCase4HotelBookingApp
 * =========================================================
 *
 * Use Case 4: Search Available Rooms (Read-Only)
 *
 * @author Aradhya
 * @version 4.0
 */

// Room base class (same concept as UC2)
abstract class Room {
    String type;
    double price;

    Room(String type, double price) {
        this.type = type;
        this.price = price;
    }

    void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Price: ₹" + price);
    }
}

class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1500);
    }
}

class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2500);
    }
}

class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 5000);
    }
}

// Inventory (same idea as UC3)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0);   // unavailable
        inventory.put("Suite Room", 2);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

// Search Service (NEW CONCEPT)
class SearchService {

    public void searchAvailableRooms(RoomInventory inventory) {

        System.out.println("\n===== AVAILABLE ROOMS =====");

        Map<String, Integer> data = inventory.getInventory();

        for (Map.Entry<String, Integer> entry : data.entrySet()) {

            String type = entry.getKey();
            int count = entry.getValue();

            // Filter unavailable rooms
            if (count > 0) {

                Room room;

                // Create corresponding room object
                if (type.equals("Single Room")) {
                    room = new SingleRoom();
                } else if (type.equals("Double Room")) {
                    room = new DoubleRoom();
                } else {
                    room = new SuiteRoom();
                }

                room.displayDetails();
                System.out.println("Available: " + count);
                System.out.println("--------------------------");
            }
        }
    }
}

public class UseCase4HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== BOOK MY STAY - SEARCH SYSTEM =====");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Search (read-only)
        SearchService search = new SearchService();
        search.searchAvailableRooms(inventory);
    }
}
