import java.util.*;

/**
 * =========================================================
 * MAIN CLASS - UseCase9HotelBookingApp
 * =========================================================
 *
 * Use Case 9: Validation and Error Handling
 *
 * @author Aradhya
 * @version 9.0
 */

// Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation
class ReservationUC9 {
    String guestName;
    String roomType;

    public ReservationUC9(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory
class RoomInventoryUC9 {

    private Map<String, Integer> inventory;

    public RoomInventoryUC9() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    public void reduceRoom(String type) throws InvalidBookingException {
        int count = getAvailability(type);

        if (count <= 0) {
            throw new InvalidBookingException("No rooms available for " + type);
        }

        inventory.put(type, count - 1);
    }
}

// Validator
class BookingValidator {

    private Set<String> validRoomTypes;

    public BookingValidator() {
        validRoomTypes = new HashSet<>();
        validRoomTypes.add("Single Room");
        validRoomTypes.add("Double Room");
    }

    public void validate(ReservationUC9 r, RoomInventoryUC9 inventory)
            throws InvalidBookingException {

        // Validate room type
        if (!validRoomTypes.contains(r.roomType)) {
            throw new InvalidBookingException("Invalid Room Type: " + r.roomType);
        }

        // Validate availability
        if (inventory.getAvailability(r.roomType) <= 0) {
            throw new InvalidBookingException("Room not available: " + r.roomType);
        }
    }
}

// Booking Service
class BookingServiceUC9 {

    public void processBooking(ReservationUC9 r, RoomInventoryUC9 inventory) {

        try {
            BookingValidator validator = new BookingValidator();

            // Fail-fast validation
            validator.validate(r, inventory);

            // If valid → proceed
            inventory.reduceRoom(r.roomType);

            System.out.println("Booking SUCCESS for " + r.guestName +
                    " (" + r.roomType + ")");

        } catch (InvalidBookingException e) {
            // Graceful failure
            System.out.println("Booking FAILED: " + e.getMessage());
        }
    }
}

public class UseCase9HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== BOOK MY STAY - VALIDATION SYSTEM =====");

        RoomInventoryUC9 inventory = new RoomInventoryUC9();
        BookingServiceUC9 service = new BookingServiceUC9();

        // Valid booking
        service.processBooking(new ReservationUC9("Aradhya", "Single Room"), inventory);

        // Invalid room type
        service.processBooking(new ReservationUC9("Rahul", "Luxury Room"), inventory);

        // No availability
        service.processBooking(new ReservationUC9("Priya", "Single Room"), inventory);
    }
}