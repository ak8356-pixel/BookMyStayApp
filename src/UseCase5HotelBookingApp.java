import java.util.LinkedList;
import java.util.Queue;

/**
 * =========================================================
 * MAIN CLASS - UseCase5HotelBookingApp
 * =========================================================
 *
 * Use Case 5: Booking Request Queue (FIFO)
 *
 * @author Aradhya
 * @version 5.0
 */

// Reservation class (represents a request)
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

// Booking Queue (FIFO system)
class BookingQueue {

    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add request
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
        System.out.println("Request added for " + reservation.guestName);
    }

    // Display queue
    public void displayQueue() {
        System.out.println("\n===== BOOKING REQUEST QUEUE =====");

        for (Reservation r : queue) {
            r.display();
        }
    }
}

public class UseCase5HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== BOOK MY STAY - BOOKING QUEUE =====");

        BookingQueue bookingQueue = new BookingQueue();

        // Simulating multiple requests
        bookingQueue.addRequest(new Reservation("Aradhya", "Single Room"));
        bookingQueue.addRequest(new Reservation("Rahul", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Priya", "Double Room"));

        // Display queue
        bookingQueue.displayQueue();
    }
}
