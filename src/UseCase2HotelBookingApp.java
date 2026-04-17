/**
 * =========================================================
 * MAIN CLASS - UseCase2HotelBookingApp
 * =========================================================
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Description:
 * This class introduces object-oriented modeling using
 * abstraction, inheritance, and polymorphism.
 *
 * @author Aradhya
 * @version 2.0
 */

abstract class Room {
    String type;
    int beds;
    double price;

    // Constructor
    Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    // Display method
    void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// Single Room
class SingleRoom extends Room {
    SingleRoom() {
        super("Single Room", 1, 1500);
    }
}

// Double Room
class DoubleRoom extends Room {
    DoubleRoom() {
        super("Double Room", 2, 2500);
    }
}

// Suite Room
class SuiteRoom extends Room {
    SuiteRoom() {
        super("Suite Room", 3, 5000);
    }
}

public class UseCase2HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== BOOK MY STAY - ROOM TYPES =====");

        // Creating room objects (Polymorphism)
        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        // Static availability
        boolean singleAvailable = true;
        boolean doubleAvailable = false;
        boolean suiteAvailable = true;

        // Display details
        System.out.println("\n--- Single Room ---");
        r1.displayDetails();
        System.out.println("Available: " + singleAvailable);

        System.out.println("\n--- Double Room ---");
        r2.displayDetails();
        System.out.println("Available: " + doubleAvailable);

        System.out.println("\n--- Suite Room ---");
        r3.displayDetails();
        System.out.println("Available: " + suiteAvailable);
    }
}