package revora.ui;

import java.util.Scanner;

import revora.model.Owner;

import revora.manager.OwnerManager;
import revora.manager.VehicleManager;
import revora.manager.ServiceManager;
import revora.manager.OdometerManager;
import revora.manager.FuelManager;

import revora.ui.auth.OwnerLogin;
import revora.ui.auth.OwnerRegistration;

public class AuthenticationMenu {

    private Scanner scanner;

    private OwnerManager ownerManager;
    private VehicleManager vehicleManager;
    private ServiceManager serviceManager;
    private OdometerManager odometerManager;
    private FuelManager fuelManager;

    public AuthenticationMenu(
            Scanner scanner,
            OwnerManager ownerManager,
            VehicleManager vehicleManager,
            ServiceManager serviceManager,
            OdometerManager odometerManager,
            FuelManager fuelManager) {

        this.scanner = scanner;
        this.ownerManager = ownerManager;
        this.vehicleManager = vehicleManager;
        this.serviceManager = serviceManager;
        this.odometerManager = odometerManager;
        this.fuelManager = fuelManager;
    }

    public void displayAuthenticationMenu() {

        int choice = 0;

        do {

            System.out.println();
            System.out.println("============================================================");
            System.out.println("                         R E V O R A");
            System.out.println("                  YOUR MACHINE. OUR MASTERY.");
            System.out.println("============================================================");
            System.out.println("   1. Mechanic Login");
            System.out.println("   2. Owner Login");
            System.out.println("   3. Owner Registration");
            System.out.println("   4. Exit");
            System.out.println("============================================================");

            System.out.print("\n   Enter your choice: ");

            try {

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        System.out.println(
                                "\n   Mechanic Login coming soon...");
                        pause();
                        break;

                    case 2:

                        OwnerLogin ownerLogin =
                                new OwnerLogin(scanner, ownerManager);

                        Owner loggedInOwner =
                                ownerLogin.loginOwner();

                        if (loggedInOwner != null) {

                            OwnerDashboard dashboard =
                                    new OwnerDashboard(
                                            scanner,
                                            loggedInOwner,
                                            ownerManager,
                                            vehicleManager,
                                            serviceManager,
                                            odometerManager,
                                            fuelManager);

                            dashboard.displayDashboard();
                        }

                        break;

                    case 3:

                        OwnerRegistration registration =
                                new OwnerRegistration(
                                        scanner, ownerManager);

                        registration.registerOwner();
                        break;

                    case 4:

                        System.out.println(
                                "\n   Returning to the welcome page...");
                        break;

                    default:

                        System.out.println(
                                "\n   Invalid choice! Please try again.");
                        pause();
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "\n   Invalid input! Enter a numeric choice.");
                pause();
            }

        } while (choice != 4);
    }

    private void pause() {

        System.out.print("\n   Press ENTER to continue...");
        scanner.nextLine();
    }
}