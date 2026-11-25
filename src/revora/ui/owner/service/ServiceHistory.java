package revora.ui.owner.service;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import revora.model.Owner;
import revora.model.Vehicle;
import revora.model.ServiceRequest;

import revora.manager.VehicleManager;
import revora.manager.ServiceManager;

import revora.enums.ServiceStatus;

public class ServiceHistory {

    private Scanner scanner;
    private Owner owner;
    private VehicleManager vehicleManager;
    private ServiceManager serviceManager;

    public ServiceHistory(Scanner scanner, Owner owner,
            VehicleManager vehicleManager, ServiceManager serviceManager) {

        this.scanner = scanner;
        this.owner = owner;
        this.vehicleManager = vehicleManager;
        this.serviceManager = serviceManager;
    }

    public void displayServiceHistory() {

        System.out.println("\n==================================================");
        System.out.println("                 SERVICE HISTORY");
        System.out.println("==================================================");

        ArrayList<ServiceRequest> requests =
                serviceManager.getRequestsByOwner(owner.getOwnerId());

        int completedCount = 0;
        int cancelledCount = 0;
        int rejectedCount = 0;

        for (ServiceRequest request : requests) {

            if (request.getStatus() == ServiceStatus.COMPLETED) {
                completedCount++;
            } else if (request.getStatus() == ServiceStatus.CANCELLED) {
                cancelledCount++;
            } else if (request.getStatus() == ServiceStatus.REJECTED) {
                rejectedCount++;
            }
        }

        int totalHistory = completedCount + cancelledCount + rejectedCount;

        if (totalHistory == 0) {

            System.out.println("\nNo service history available yet.");
            System.out.println("Completed, cancelled and rejected requests");
            System.out.println("will appear here.");

        } else {

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd-MM-yyyy");

            int serialNumber = 1;

            for (ServiceRequest request : requests) {

                ServiceStatus status = request.getStatus();

                if (status == ServiceStatus.COMPLETED
                        || status == ServiceStatus.CANCELLED
                        || status == ServiceStatus.REJECTED) {

                    Vehicle vehicle =
                            vehicleManager.findVehicleById(request.getVehicleId());

                    System.out.println("\n--------------------------------------------------");
                    System.out.println("Record No.       : " + serialNumber++);
                    System.out.println("Request ID       : " + request.getRequestId());

                    if (vehicle != null) {
                        System.out.println("Vehicle          : "
                                + vehicle.getBrand() + " "
                                + vehicle.getModel());

                        System.out.println("Registration No. : "
                                + vehicle.getRegistrationNumber());
                    } else {
                        System.out.println("Vehicle ID       : "
                                + request.getVehicleId());
                    }

                    System.out.println("Service Category : "
                            + formatText(request.getServiceCategory().name()));

                    System.out.println("Description      : "
                            + request.getDescription());

                    System.out.println("Preferred Date   : "
                            + request.getPreferredDate().format(formatter));

                    System.out.println("Final Status     : "
                            + formatText(status.name()));
                }
            }

            System.out.println("\n==================================================");
            System.out.println("             SERVICE HISTORY SUMMARY");
            System.out.println("==================================================");
            System.out.println("Completed Services : " + completedCount);
            System.out.println("Cancelled Requests : " + cancelledCount);
            System.out.println("Rejected Requests  : " + rejectedCount);
            System.out.println("Total Records      : " + totalHistory);
        }

        System.out.println("\n==================================================");
        System.out.println("Press ENTER to return...");
        scanner.nextLine();
    }

    private String formatText(String text) {

        String[] words = text.toLowerCase().split("_");
        StringBuilder formatted = new StringBuilder();

        for (String word : words) {
            formatted.append(
                    Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }

        return formatted.toString().trim();
    }
}