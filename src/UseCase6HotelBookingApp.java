import java.util.*;

/**
 * =========================================================
 * MAIN CLASS - UseCase6HotelBookingApp
 * =========================================================
 *
 * Use Case 6: Booking Confirmation & Allocation
 *
 * @author Aradhya
 * @version 6.0
 */

// Reservation (renamed)
class ReservationUC6 {
    String guestName;
    String roomType;

    public ReservationUC6(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory Service (renamed)
class RoomInventoryUC6 {

    private HashMap<String, Integer> inventory;

    public RoomInventoryUC6() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceRoom(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }
}

// Booking Queue (renamed)
class BookingQueueUC6 {

    Queue<ReservationUC6> queue = new LinkedList<>();

    public void addRequest(ReservationUC6 r) {
        queue.add(r);
    }

    public ReservationUC6 getNext() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Booking Service (MAIN LOGIC)
class BookingServiceUC6 {

    private Set<String> allocatedRoomIds;
    private HashMap<String, Set<String>> allocationMap;
    private int counter = 1;

    public BookingServiceUC6() {
        allocatedRoomIds = new HashSet<>();
        allocationMap = new HashMap<>();
    }

    public void processBooking(ReservationUC6 r, RoomInventoryUC6 inventory) {

        System.out.println("\nProcessing request for " + r.guestName);

        // Check availability
        if (inventory.getAvailability(r.roomType) > 0) {

            // Generate unique room ID
            String roomId = r.roomType.replace(" ", "") + "-" + counter++;

            // Ensure uniqueness
            if (!allocatedRoomIds.contains(roomId)) {

                allocatedRoomIds.add(roomId);

                // Map room type → assigned IDs
                allocationMap.putIfAbsent(r.roomType, new HashSet<>());
                allocationMap.get(r.roomType).add(roomId);

                // Update inventory
                inventory.reduceRoom(r.roomType);

                System.out.println("Booking CONFIRMED for " + r.guestName);
                System.out.println("Assigned Room ID: " + roomId);

            }

        } else {
            System.out.println("Booking FAILED for " + r.guestName + " (No rooms available)");
        }
    }

    public void displayAllocations() {
        System.out.println("\n===== FINAL ALLOCATIONS =====");

        for (Map.Entry<String, Set<String>> entry : allocationMap.entrySet()) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }
}

public class UseCase6HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== BOOK MY STAY - BOOKING SYSTEM =====");

        // Initialize components
        RoomInventoryUC6 inventory = new RoomInventoryUC6();
        BookingQueueUC6 queue = new BookingQueueUC6();
        BookingServiceUC6 service = new BookingServiceUC6();

        // Add requests
        queue.addRequest(new ReservationUC6("Aradhya", "Single Room"));
        queue.addRequest(new ReservationUC6("Rahul", "Single Room"));
        queue.addRequest(new ReservationUC6("Priya", "Single Room")); // should fail

        // Process queue (FIFO)
        while (!queue.isEmpty()) {
            ReservationUC6 r = queue.getNext();
            service.processBooking(r, inventory);
        }

        // Show results
        service.displayAllocations();
    }
}