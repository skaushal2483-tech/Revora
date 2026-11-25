package revora.ui;

import java.util.Scanner;

import revora.manager.OwnerManager;
import revora.manager.VehicleManager;
import revora.manager.ServiceManager;
import revora.manager.OdometerManager;
import revora.manager.FuelManager;

public class WelcomeMenu {

    private Scanner scanner;

    private OwnerManager ownerManager;
    private VehicleManager vehicleManager;
    private ServiceManager serviceManager;
    private OdometerManager odometerManager;
    private FuelManager fuelManager;

    public WelcomeMenu() {

        scanner = new Scanner(System.in);

        ownerManager = new OwnerManager();
        vehicleManager = new VehicleManager();
        serviceManager = new ServiceManager();
        odometerManager = new OdometerManager();
        fuelManager = new FuelManager();
    }

    public void displayWelcomePage() {

        int choice;

        do {

            System.out.println();
            System.out.println("============================================================");
            System.out.println();
            System.out.println("                         R E V O R A");
            System.out.println("                  YOUR MACHINE. OUR MASTERY.");
            System.out.println();
            System.out.println("============================================================");

            System.out.println();
            System.out.println("   1.  About Us");
            System.out.println("   2.  Enter REVORA");
            System.out.println("   3.  Exit");
            System.out.println();

            System.out.println("============================================================");

            System.out.print("\n   Enter your choice: ");

            try {

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        System.out.println("\n   About REVORA");
                        System.out.println(
                                "   Our story is coming soon...");
                        pause();
                        break;

                    case 2:

                        AuthenticationMenu authenticationMenu =
                                new AuthenticationMenu(
                                        scanner,
                                        ownerManager,
                                        vehicleManager,
                                        serviceManager,
                                        odometerManager,
                                        fuelManager);

                        authenticationMenu.displayAuthenticationMenu();
                        break;

                    case 3:
                        System.out.println(
                                "\n   Thank you for choosing REVORA.");
                        System.out.println(
                                "   Drive Safe. Stay Ahead.");
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

                choice = 0;
            }

        } while (choice != 3);
    }

    private void pause() {

        System.out.print("\n   Press ENTER to continue...");
        scanner.nextLine();
    }
}