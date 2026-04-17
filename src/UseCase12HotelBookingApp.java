import java.io.*;
import java.util.*;

/**
 * =========================================================
 * MAIN CLASS - UseCase12HotelBookingApp
 * =========================================================
 *
 * Use Case 12: Persistence and Recovery
 *
 * @author Aradhya
 * @version 12.0
 */

// Reservation (Serializable)
class ReservationUC12 implements Serializable {
    String guestName;
    String roomType;
    String roomId;

    public ReservationUC12(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public void display() {
        System.out.println(guestName + " | " + roomType + " | " + roomId);
    }
}

// Inventory (Serializable)
class RoomInventoryUC12 implements Serializable {

    Map<String, Integer> inventory;

    public RoomInventoryUC12() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    public void display() {
        System.out.println("Inventory: " + inventory);
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "hotel_data.ser";

    // Save state
    public void save(RoomInventoryUC12 inventory,
                     List<ReservationUC12> history) {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(inventory);
            out.writeObject(history);

            System.out.println("\nData saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load state
    public Object[] load() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            RoomInventoryUC12 inventory =
                    (RoomInventoryUC12) in.readObject();

            List<ReservationUC12> history =
                    (List<ReservationUC12>) in.readObject();

            System.out.println("\nData loaded successfully.");

            return new Object[]{inventory, history};

        } catch (Exception e) {
            System.out.println("\nNo previous data found. Starting fresh.");
            return null;
        }
    }
}

public class UseCase12HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== BOOK MY STAY - PERSISTENCE SYSTEM =====");

        PersistenceService service = new PersistenceService();

        RoomInventoryUC12 inventory;
        List<ReservationUC12> history;

        // Try loading existing data
        Object[] data = service.load();

        if (data != null) {
            inventory = (RoomInventoryUC12) data[0];
            history = (List<ReservationUC12>) data[1];
        } else {
            inventory = new RoomInventoryUC12();
            history = new ArrayList<>();
        }

        // Simulate booking
        history.add(new ReservationUC12("Aradhya", "Single Room", "SingleRoom-1"));

        // Display current state
        inventory.display();
        System.out.println("\nBooking History:");
        for (ReservationUC12 r : history) {
            r.display();
        }

        // Save before exit
        service.save(inventory, history);
    }
}
