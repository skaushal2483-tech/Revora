package revora.ui.owner.service;
import java.util.Scanner;

import revora.model.Owner;
import revora.manager.VehicleManager;
import revora.manager.ServiceManager;


public class ServiceMenu {

    private Scanner scanner;
    private Owner owner;
    private VehicleManager vehicleManager;
    private ServiceManager serviceManager;

    public ServiceMenu(Scanner scanner, Owner owner,
            VehicleManager vehicleManager,
            ServiceManager serviceManager) {

        this.scanner = scanner;
        this.owner = owner;
        this.vehicleManager = vehicleManager;
        this.serviceManager = serviceManager;
    }

    public void displayServiceMenu() {

        int choice = 0;

        do {
            System.out.println();
            System.out.println("============================================================");
            System.out.println("                    SERVICE MANAGEMENT");
            System.out.println("============================================================");
            System.out.println("  1. Request a Service");
            System.out.println("  2. View My Service Requests");
            System.out.println("  3. Track Service Status");
            System.out.println("  4. Cancel Service Request");
            System.out.println("  5. Back to Dashboard");
            System.out.println("============================================================");

            System.out.print("\n  Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        ServiceRequestCreation creation = new ServiceRequestCreation(
                                scanner, owner, vehicleManager, serviceManager);

                        creation.createServiceRequest();
                        break;

                    case 2:
                        ServiceRequestViewing viewing = new ServiceRequestViewing(
                                scanner, owner, vehicleManager, serviceManager);

                        viewing.viewServiceRequests();
                        break;

                    case 3:
                        ServiceStatusTracking tracking = new ServiceStatusTracking(
                                scanner, owner, serviceManager);

                        tracking.trackServiceStatus();
                        break;

                    case 4:
                        ServiceRequestCancellation cancellation = new ServiceRequestCancellation(
                                scanner, owner, serviceManager);

                        cancellation.cancelServiceRequest();
                        break;

                    case 5:
                        System.out.println("\n  Returning to Owner Dashboard...");
                        break;

                    default:
                        System.out.println("\n  Invalid choice! Please try again.");
                        pause();
                }

            } catch (NumberFormatException e) {
                System.out.println("\n  Invalid input! Enter a numeric choice.");
                pause();
            }

        } while (choice != 5);
    }

    private void pause() {
        System.out.print("\n  Press ENTER to continue...");
        scanner.nextLine();
    }
}