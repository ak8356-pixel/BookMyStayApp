import java.util.*;

/**
 * =========================================================
 * MAIN CLASS - UseCase7HotelBookingApp
 * =========================================================
 *
 * Use Case 7: Add-On Services for Reservation
 *
 * @author Aradhya
 * @version 7.0
 */

// Add-On Service
class AddOnService {
    String name;
    double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public void display() {
        System.out.println(name + " - ₹" + cost);
    }
}

// Add-On Service Manager
class AddOnServiceManager {

    // reservationId → list of services
    private Map<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service.name + " to " + reservationId);
    }

    // Display services
    public void displayServices(String reservationId) {
        System.out.println("\nServices for " + reservationId + ":");

        List<AddOnService> list = serviceMap.get(reservationId);

        if (list == null || list.isEmpty()) {
            System.out.println("No services added.");
            return;
        }

        for (AddOnService s : list) {
            s.display();
        }
    }

    // Calculate total cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;

        List<AddOnService> list = serviceMap.get(reservationId);

        if (list != null) {
            for (AddOnService s : list) {
                total += s.cost;
            }
        }

        return total;
    }
}

public class UseCase7HotelBookingApp {

    public static void main(String[] args) {

        System.out.println("===== BOOK MY STAY - ADD ON SERVICES =====");

        AddOnServiceManager manager = new AddOnServiceManager();

        // Example reservation IDs (from UC6 concept)
        String res1 = "SingleRoom-1";
        String res2 = "SuiteRoom-1";

        // Add services
        manager.addService(res1, new AddOnService("Breakfast", 500));
        manager.addService(res1, new AddOnService("Airport Pickup", 800));

        manager.addService(res2, new AddOnService("Spa", 1200));

        // Display
        manager.displayServices(res1);
        System.out.println("Total Cost: ₹" + manager.calculateTotalCost(res1));

        manager.displayServices(res2);
        System.out.println("Total Cost: ₹" + manager.calculateTotalCost(res2));
    }
}
