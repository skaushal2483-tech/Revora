package revora.ui.owner.service;

import java.util.Scanner;

import revora.model.Owner;
import revora.model.ServiceRequest;

import revora.manager.ServiceManager;

import revora.utility.InputValidator;
import revora.exception.InvalidInputException;

public class ServiceStatusTracking {

    private Scanner scanner;
    private Owner owner;
    private ServiceManager serviceManager;

    public ServiceStatusTracking(Scanner scanner, Owner owner,
            ServiceManager serviceManager) {

        this.scanner = scanner;
        this.owner = owner;
        this.serviceManager = serviceManager;
    }

    public void trackServiceStatus() {

        System.out.println("\n============================================================");
        System.out.println("                 TRACK SERVICE STATUS");
        System.out.println("============================================================");

        System.out.print("\n  Enter Service Request ID: ");
        String requestId = scanner.nextLine().trim();

        try {
            InputValidator.validateNotEmpty(requestId, "Request ID");

        } catch (InvalidInputException e) {
            System.out.println("\n  " + e.getMessage());
            pause();
            return;
        }

        ServiceRequest request =
                serviceManager.findRequestById(requestId);

        if (request == null
                || !request.getOwnerId().equalsIgnoreCase(owner.getOwnerId())) {

            System.out.println("\n  Service request not found.");
            pause();
            return;
        }

        System.out.println("\n============================================================");
        System.out.println("                 SERVICE REQUEST STATUS");
        System.out.println("============================================================");

        System.out.println("  REQUEST ID     : " + request.getRequestId());
        System.out.println("  VEHICLE ID     : " + request.getVehicleId());
        System.out.println("  SERVICE TYPE   : " + formatText(
                request.getServiceCategory().name()));
        System.out.println("  REQUEST STATUS : " + formatText(
                request.getStatus().name()));

        System.out.println("============================================================");

        displayStatusMessage(request.getStatus().name());

        pause();
    }

    // Format Text For Display
    private String formatText(String text) {

        String formatted = text.replace("_", " ").toLowerCase();

        String[] words = formatted.split(" ");

        StringBuilder result = new StringBuilder();

        for (String word : words) {

            result.append(word.substring(0, 1).toUpperCase())
                    .append(word.substring(1))
                    .append(" ");
        }

        return result.toString().trim();
    }

    // Display Status Information
    private void displayStatusMessage(String status) {

        switch (status) {

            case "PENDING":
                System.out.println("\n  Your request is awaiting mechanic approval.");
                break;

            case "ACCEPTED":
                System.out.println("\n  Your service request has been accepted.");
                break;

            case "REJECTED":
                System.out.println("\n  Your service request was rejected.");
                break;

            case "IN_PROGRESS":
                System.out.println("\n  Your vehicle is currently being serviced.");
                break;

            case "COMPLETED":
                System.out.println("\n  Your vehicle service has been completed!");
                break;

            case "CANCELLED":
                System.out.println("\n  This service request has been cancelled.");
                break;

            default:
                System.out.println("\n  Unable to determine request status.");
        }
    }

    // Pause Screen
    private void pause() {
        System.out.print("\n  Press ENTER to continue...");
        scanner.nextLine();
    }
}