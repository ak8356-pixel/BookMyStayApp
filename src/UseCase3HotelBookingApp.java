import java.util.HashMap;
import java.util.Map;

/**
 * =========================================================
 * MAIN CLASS - UseCase3HotelBookingApp
 * =========================================================
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Description:
 * This class demonstrates centralized inventory management
 * using HashMap.
 *
 * @author Aradhya
 * @version 3.0
 */

// Inventory Class (Single Source of Truth)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initialize room counts
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Book room (decrease count)
    public void bookRoom(String roomType) {
        if (inventory.containsKey(roomType) && inventory.get(roomType) > 0) {
            inventory.put(roomType, inventory.get(roomType) - 1);
            System.out.println(roomType + " booked successfully.");
        } else {
            System.out.println(roomType + " is NOT available.");
        }
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("\n===== CURRENT ROOM INVENTORY =====");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " → Available: " + entry.getValue());
        }
    }
}

public class UseCase3HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== BOOK MY STAY - INVENTORY SYSTEM =====");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Show initial state
        inventory.displayInventory();

        // Perform some bookings
        System.out.println("\n--- Booking Operations ---");
        inventory.bookRoom("Single Room");
        inventory.bookRoom("Suite Room");
        inventory.bookRoom("Suite Room");
        inventory.bookRoom("Suite Room"); // should show not available

        // Show updated state
        inventory.displayInventory();
    }
}
