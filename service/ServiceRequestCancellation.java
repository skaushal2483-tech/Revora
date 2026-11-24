package revora.ui.owner.service;

import java.util.Scanner;

import revora.model.Owner;
import revora.model.ServiceRequest;

import revora.manager.ServiceManager;

import revora.utility.InputValidator;
import revora.exception.InvalidInputException;

public class ServiceRequestCancellation {

    private Scanner scanner;
    private Owner owner;
    private ServiceManager serviceManager;

    public ServiceRequestCancellation(Scanner scanner, Owner owner,
            ServiceManager serviceManager) {

        this.scanner = scanner;
        this.owner = owner;
        this.serviceManager = serviceManager;
    }

    public void cancelServiceRequest() {

        System.out.println("\n============================================================");
        System.out.println("                CANCEL SERVICE REQUEST");
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

        if (request.getStatus() != revora.enums.ServiceStatus.PENDING) {

            System.out.println("\n  This request cannot be cancelled.");
            System.out.println("  Only PENDING requests can be cancelled.");
            pause();
            return;
        }

        System.out.println("\n  REQUEST DETAILS");
        System.out.println("------------------------------------------------------------");
        System.out.println("  Request ID : " + request.getRequestId());
        System.out.println("  Vehicle ID : " + request.getVehicleId());
        System.out.println("  Status     : Pending");
        System.out.println("------------------------------------------------------------");

        System.out.print("\n  Are you sure you want to cancel this request? (Y/N): ");

        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("N")) {

            System.out.println("\n  Cancellation aborted.");
            pause();
            return;
        }

        if (!confirmation.equalsIgnoreCase("Y")) {

            System.out.println("\n  Invalid confirmation. Cancellation aborted.");
            pause();
            return;
        }

        boolean cancelled = serviceManager.cancelRequest(
                requestId, owner.getOwnerId());

        if (cancelled) {

            System.out.println("\n  Service request cancelled successfully!");
            System.out.println("  Request ID: " + requestId);

        } else {

            System.out.println("\n  Unable to cancel service request.");
        }

        pause();
    }

    // Pause Screen
    private void pause() {
        System.out.print("\n  Press ENTER to continue...");
        scanner.nextLine();
    }
}