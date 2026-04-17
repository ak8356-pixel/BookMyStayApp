import java.util.*;

/**
 * =========================================================
 * MAIN CLASS - UseCase11HotelBookingApp
 * =========================================================
 *
 * Use Case 11: Concurrency and Synchronization
 *
 * @author Aradhya
 * @version 11.0
 */

// Reservation
class ReservationUC11 {
    String guestName;
    String roomType;

    public ReservationUC11(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Inventory (CRITICAL SECTION)
class RoomInventoryUC11 {

    private Map<String, Integer> inventory;

    public RoomInventoryUC11() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1); // only 1 to show race condition
    }

    // synchronized method → thread-safe
    public synchronized boolean bookRoom(String type, String guest) {

        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {
            System.out.println(guest + " is booking...");

            // simulate delay (forces race condition if unsynchronized)
            try { Thread.sleep(100); } catch (Exception e) {}

            inventory.put(type, available - 1);

            System.out.println("Booking SUCCESS for " + guest);
            return true;

        } else {
            System.out.println("Booking FAILED for " + guest);
            return false;
        }
    }

    public void display() {
        System.out.println("\nFinal Inventory: " + inventory);
    }
}

// Thread class
class BookingThread extends Thread {

    private ReservationUC11 reservation;
    private RoomInventoryUC11 inventory;

    public BookingThread(ReservationUC11 r, RoomInventoryUC11 inventory) {
        this.reservation = r;
        this.inventory = inventory;
    }

    public void run() {
        inventory.bookRoom(reservation.roomType, reservation.guestName);
    }
}

public class UseCase11HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== BOOK MY STAY - CONCURRENCY SYSTEM =====");

        RoomInventoryUC11 inventory = new RoomInventoryUC11();

        // Simulating multiple users
        BookingThread t1 = new BookingThread(
                new ReservationUC11("Aradhya", "Single Room"), inventory);

        BookingThread t2 = new BookingThread(
                new ReservationUC11("Rahul", "Single Room"), inventory);

        BookingThread t3 = new BookingThread(
                new ReservationUC11("Priya", "Single Room"), inventory);

        // Start threads simultaneously
        t1.start();
        t2.start();
        t3.start();

        // Wait for completion
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (Exception e) {}

        // Final state
        inventory.display();
    }
}