package revora.ui;

import java.util.Scanner;

import revora.model.Owner;

import revora.manager.OwnerManager;
import revora.manager.VehicleManager;
import revora.manager.ServiceManager;
import revora.manager.OdometerManager;
import revora.manager.FuelManager;

import revora.ui.owner.profile.OwnerProfile;
import revora.ui.owner.vehicle.VehicleMenu;
import revora.ui.owner.service.ServiceMenu;
import revora.ui.owner.service.ServiceHistory;
import revora.ui.owner.mileage.MileageFuelMenu;

public class OwnerDashboard {

    private Scanner scanner;
    private Owner owner;
    private OwnerManager ownerManager;
    private VehicleManager vehicleManager;
    private ServiceManager serviceManager;
    private OdometerManager odometerManager;
    private FuelManager fuelManager;

    public OwnerDashboard(
            Scanner scanner,
            Owner owner,
            OwnerManager ownerManager,
            VehicleManager vehicleManager,
            ServiceManager serviceManager,
            OdometerManager odometerManager,
            FuelManager fuelManager) {

        this.scanner = scanner;
        this.owner = owner;
        this.ownerManager = ownerManager;
        this.vehicleManager = vehicleManager;
        this.serviceManager = serviceManager;
        this.odometerManager = odometerManager;
        this.fuelManager = fuelManager;
    }

    public void displayDashboard() {

        int choice = 0;

        do {

            System.out.println();
            System.out.println("============================================================");
            System.out.println("                         R E V O R A");
            System.out.println("                  YOUR MACHINE. OUR MASTERY.");
            System.out.println("============================================================");
            System.out.println();
            System.out.println("   WELCOME BACK, "
                    + owner.getFirstName().toUpperCase() + "!");
            System.out.println("   OWNER ID: " + owner.getOwnerId());
            System.out.println();
            System.out.println("============================================================");
            System.out.println("                     OWNER DASHBOARD");
            System.out.println("============================================================");
            System.out.println();
            System.out.println("   1. My Profile");
            System.out.println("   2. My Garage");
            System.out.println("   3. Service Management");
            System.out.println("   4. Service History");
            System.out.println("   5. Mileage & Fuel Tracking");
            System.out.println("   6. Logout");
            System.out.println();
            System.out.println("============================================================");

            System.out.print("\n   Enter your choice: ");

            try {

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:

                        OwnerProfile profile = new OwnerProfile(
                                scanner,
                                owner,
                                ownerManager,
                                vehicleManager,
                                serviceManager,
                                odometerManager,
                                fuelManager);

                        profile.displayProfile();

                        if (ownerManager.findOwnerByEmail(
                                owner.getEmail()) == null) {
                            return;
                        }

                        break;

                    case 2:

                        VehicleMenu vehicleMenu = new VehicleMenu(
                                scanner,
                                vehicleManager,
                                serviceManager,
                                odometerManager,
                                fuelManager,
                                owner);

                        vehicleMenu.displayVehicleMenu();
                        break;

                    case 3:

                        ServiceMenu serviceMenu = new ServiceMenu(
                                scanner,
                                owner,
                                vehicleManager,
                                serviceManager);

                        serviceMenu.displayServiceMenu();
                        break;

                    case 4:

                        ServiceHistory serviceHistory = new ServiceHistory(
                                scanner,
                                owner,
                                vehicleManager,
                                serviceManager);

                        serviceHistory.displayServiceHistory();
                        break;

                    case 5:

                        MileageFuelMenu mileageFuelMenu = new MileageFuelMenu(
                                scanner,
                                owner,
                                vehicleManager,
                                odometerManager,
                                fuelManager);

                        mileageFuelMenu.displayVehicleSelection();
                        break;

                    case 6:

                        System.out.println("\n   LOGGING OUT OF REVORA...");
                        System.out.println("   DRIVE SAFE. SEE YOU AGAIN!");
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

        } while (choice != 6);
    }

    private void pause() {

        System.out.print("\n   Press ENTER to continue...");
        scanner.nextLine();
    }
}