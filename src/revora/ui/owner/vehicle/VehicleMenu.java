package revora.ui.owner.vehicle;

import java.util.Scanner;

import revora.model.Owner;

import revora.manager.VehicleManager;
import revora.manager.ServiceManager;
import revora.manager.OdometerManager;
import revora.manager.FuelManager;

public class VehicleMenu {

    private Scanner scanner;

    private VehicleManager vehicleManager;
    private ServiceManager serviceManager;
    private OdometerManager odometerManager;
    private FuelManager fuelManager;

    private Owner owner;

    public VehicleMenu(
            Scanner scanner,
            VehicleManager vehicleManager,
            ServiceManager serviceManager,
            OdometerManager odometerManager,
            FuelManager fuelManager,
            Owner owner) {

        this.scanner = scanner;
        this.vehicleManager = vehicleManager;
        this.serviceManager = serviceManager;
        this.odometerManager = odometerManager;
        this.fuelManager = fuelManager;
        this.owner = owner;
    }

    public void displayVehicleMenu() {

        int choice = 0;

        do {

            System.out.println();
            System.out.println("============================================================");
            System.out.println("                         MY GARAGE");
            System.out.println("============================================================");
            System.out.println("  1. Add Vehicle");
            System.out.println("  2. View All Vehicles");
            System.out.println("  3. Search Vehicle");
            System.out.println("  4. Update Vehicle");
            System.out.println("  5. Remove Vehicle");
            System.out.println("  6. Back to Dashboard");
            System.out.println("============================================================");

            System.out.print("  Enter your choice: ");

            try {

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        VehicleRegistration registration =
                                new VehicleRegistration(
                                        scanner, vehicleManager, owner);

                        registration.registerVehicle();
                        break;

                    case 2:
                        VehicleViewing viewing =
                                new VehicleViewing(
                                        scanner, vehicleManager, owner);

                        viewing.viewAllVehicles();
                        break;

                    case 3:
                        VehicleSearch search =
                                new VehicleSearch(
                                        scanner, vehicleManager, owner);

                        search.searchVehicle();
                        break;

                    case 4:
                        VehicleUpdate update =
                                new VehicleUpdate(
                                        scanner, vehicleManager, owner);

                        update.updateVehicle();
                        break;

                    case 5:
                        VehicleRemoval removal =
                                new VehicleRemoval(
                                        scanner,
                                        vehicleManager,
                                        serviceManager,
                                        odometerManager,
                                        fuelManager,
                                        owner);

                        removal.removeVehicle();
                        break;

                    case 6:
                        System.out.println(
                                "\n  Returning to Owner Dashboard...");
                        break;

                    default:
                        System.out.println(
                                "\n  Invalid choice! Please try again.");
                        pause();
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "\n  Enter a valid numeric choice.");
                pause();
            }

        } while (choice != 6);
    }

    private void pause() {

        System.out.print("\n  Press ENTER to continue...");
        scanner.nextLine();
    }
}