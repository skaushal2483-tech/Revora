package revora.ui.owner.service;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

import revora.model.Owner;
import revora.model.Vehicle;
import revora.model.ServiceRequest;

import revora.manager.VehicleManager;
import revora.manager.ServiceManager;

import revora.enums.ServiceCategory;

public class ServiceRequestViewing {

    private Scanner scanner;
    private Owner owner;
    private VehicleManager vehicleManager;
    private ServiceManager serviceManager;

    public ServiceRequestViewing(Scanner scanner, Owner owner,
            VehicleManager vehicleManager, ServiceManager serviceManager) {

        this.scanner = scanner;
        this.owner = owner;
        this.vehicleManager = vehicleManager;
        this.serviceManager = serviceManager;
    }

    public void viewServiceRequests() {

        ArrayList<ServiceRequest> requests =
                serviceManager.getRequestsByOwner(owner.getOwnerId());

        System.out.println("\n============================================================");
        System.out.println("                 MY SERVICE REQUESTS");
        System.out.println("============================================================");

        if (requests.isEmpty()) {
            System.out.println("\n  You haven't submitted any service requests yet.");
            pause();
            return;
        }

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (ServiceRequest request : requests) {

            Vehicle vehicle =
                    vehicleManager.findVehicleById(request.getVehicleId());

            System.out.println("\n  REQUEST ID       : " + request.getRequestId());

            System.out.println("  VEHICLE          : "
                    + (vehicle != null
                            ? vehicle.getRegistrationNumber()
                            : "Vehicle unavailable"));

            System.out.println("  SERVICE CATEGORY : "
                    + formatCategory(request.getServiceCategory()));

            System.out.println("  DESCRIPTION      : "
                    + request.getDescription());

            System.out.println("  PREFERRED DATE   : "
                    + request.getPreferredDate().format(formatter));

            System.out.println("  REQUEST STATUS   : "
                    + formatStatus(request.getStatus().name()));

            System.out.println("------------------------------------------------------------");
        }

        System.out.println("\n  TOTAL REQUESTS: " + requests.size());
        System.out.println("============================================================");

        pause();
    }

    // Format Service Category
    private String formatCategory(ServiceCategory category) {

        String[] words = category.name().split("_");

        StringBuilder formatted = new StringBuilder();

        for (String word : words) {
            formatted.append(word.charAt(0))
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }

        return formatted.toString().trim();
    }

    // Format Service Status
    private String formatStatus(String status) {

        String formatted = status.replace("_", " ").toLowerCase();

        String[] words = formatted.split(" ");

        StringBuilder result = new StringBuilder();

        for (String word : words) {
            result.append(word.substring(0, 1).toUpperCase())
                    .append(word.substring(1))
                    .append(" ");
        }

        return result.toString().trim();
    }

    // Pause Screen
    private void pause() {
        System.out.print("\n  Press ENTER to continue...");
        scanner.nextLine();
    }
}