import java.util.*;

/**
 * =========================================================
 * MAIN CLASS - UseCase10HotelBookingApp
 * =========================================================
 *
 * Use Case 10: Cancellation and Rollback System
 *
 * @author Aradhya
 * @version 10.0
 */

// Reservation
class ReservationUC10 {
    String guestName;
    String roomType;
    String roomId;

    public ReservationUC10(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }
}

// Inventory
class RoomInventoryUC10 {

    private Map<String, Integer> inventory;

    public RoomInventoryUC10() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
    }

    public void increaseRoom(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }

    public void display() {
        System.out.println("\nInventory: " + inventory);
    }
}

// Booking History
class BookingHistoryUC10 {

    private Map<String, ReservationUC10> bookings;

    public BookingHistoryUC10() {
        bookings = new HashMap<>();
    }

    public void add(ReservationUC10 r) {
        bookings.put(r.roomId, r);
    }

    public ReservationUC10 get(String roomId) {
        return bookings.get(roomId);
    }

    public void remove(String roomId) {
        bookings.remove(roomId);
    }
}

// Cancellation Service
class CancellationService {

    private Stack<String> rollbackStack;

    public CancellationService() {
        rollbackStack = new Stack<>();
    }

    public void cancelBooking(String roomId,
                              BookingHistoryUC10 history,
                              RoomInventoryUC10 inventory) {

        System.out.println("\nProcessing cancellation for Room ID: " + roomId);

        // Validate existence
        ReservationUC10 r = history.get(roomId);

        if (r == null) {
            System.out.println("Cancellation FAILED: Reservation not found.");
            return;
        }

        // Push to stack (LIFO tracking)
        rollbackStack.push(roomId);

        // Restore inventory
        inventory.increaseRoom(r.roomType);

        // Remove from history
        history.remove(roomId);

        System.out.println("Cancellation SUCCESS for " + roomId);
    }

    public void displayRollbackStack() {
        System.out.println("\nRollback Stack: " + rollbackStack);
    }
}

public class UseCase10HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== BOOK MY STAY - CANCELLATION SYSTEM =====");

        RoomInventoryUC10 inventory = new RoomInventoryUC10();
        BookingHistoryUC10 history = new BookingHistoryUC10();
        CancellationService service = new CancellationService();

        // Simulate confirmed bookings
        history.add(new ReservationUC10("Aradhya", "Single Room", "SingleRoom-1"));
        history.add(new ReservationUC10("Rahul", "Double Room", "DoubleRoom-1"));

        // Cancel booking
        service.cancelBooking("SingleRoom-1", history, inventory);

        // Invalid cancellation
        service.cancelBooking("SingleRoom-1", history, inventory);

        // Display state
        inventory.display();
        service.displayRollbackStack();
    }
}