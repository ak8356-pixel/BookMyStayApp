import java.util.*;

/**
 * =========================================================
 * MAIN CLASS - UseCase8HotelBookingApp
 * =========================================================
 *
 * Use Case 8: Booking History & Reporting
 *
 * @author Aradhya
 * @version 8.0
 */

// Reservation (simple model for history)
class ReservationUC8 {
    String guestName;
    String roomType;
    String roomId;

    public ReservationUC8(String guestName, String roomType, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
    }

    public void display() {
        System.out.println("Guest: " + guestName +
                " | Room: " + roomType +
                " | ID: " + roomId);
    }
}

// Booking History (stores confirmed bookings)
class BookingHistory {

    private List<ReservationUC8> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Add confirmed booking
    public void addReservation(ReservationUC8 r) {
        history.add(r);
    }

    // Get all bookings
    public List<ReservationUC8> getHistory() {
        return history;
    }

    // Display all
    public void displayHistory() {
        System.out.println("\n===== BOOKING HISTORY =====");

        for (ReservationUC8 r : history) {
            r.display();
        }
    }
}

// Report Service (separate logic)
class BookingReportService {

    public void generateSummary(List<ReservationUC8> history) {

        System.out.println("\n===== BOOKING REPORT =====");

        Map<String, Integer> countMap = new HashMap<>();

        for (ReservationUC8 r : history) {
            countMap.put(r.roomType,
                    countMap.getOrDefault(r.roomType, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            System.out.println(entry.getKey() + " → Bookings: " + entry.getValue());
        }
    }
}

public class UseCase8HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== BOOK MY STAY - HISTORY SYSTEM =====");

        BookingHistory history = new BookingHistory();
        BookingReportService report = new BookingReportService();

        // Simulated confirmed bookings
        history.addReservation(new ReservationUC8("Aradhya", "Single Room", "SingleRoom-1"));
        history.addReservation(new ReservationUC8("Rahul", "Double Room", "DoubleRoom-1"));
        history.addReservation(new ReservationUC8("Priya", "Single Room", "SingleRoom-2"));

        // Display history
        history.displayHistory();

        // Generate report
        report.generateSummary(history.getHistory());
    }
}