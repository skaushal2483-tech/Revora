package revora.ui.owner.mileage;
import java.util.ArrayList;
import java.util.Scanner;

import revora.model.Owner;
import revora.model.Vehicle;

import revora.manager.VehicleManager;
import revora.manager.OdometerManager;
import revora.manager.FuelManager;

public class MileageFuelMenu {

    private Scanner scanner;
    private Owner owner;
    private VehicleManager vehicleManager;
    private OdometerManager odometerManager;
    private FuelManager fuelManager;

    public MileageFuelMenu(Scanner scanner, Owner owner,
            VehicleManager vehicleManager,
            OdometerManager odometerManager,
            FuelManager fuelManager) {

        this.scanner = scanner;
        this.owner = owner;
        this.vehicleManager = vehicleManager;
        this.odometerManager = odometerManager;
        this.fuelManager = fuelManager;
    }

    public void displayVehicleSelection() {

        int choice;

        while (true) {

            System.out.println("\n==================================================");
            System.out.println("                 SELECT YOUR VEHICLE");
            System.out.println("==================================================");

            ArrayList<Vehicle> vehicles = vehicleManager.getVehiclesByOwner(owner.getOwnerId());

            if (vehicles.isEmpty()) {
                System.out.println("\nNo registered vehicles found.");
                System.out.println("Please register a vehicle in My Garage first.");
                pause();
                return;
            }

            for (int i = 0; i < vehicles.size(); i++) {

                Vehicle vehicle = vehicles.get(i);

                System.out.println((i + 1) + ". "
                        + vehicle.getBrand() + " "
                        + vehicle.getModel()
                        + " (" + vehicle.getRegistrationNumber() + ")");
            }

            System.out.println("0. Back to Dashboard");
            System.out.println("==================================================");

            System.out.print("\nEnter your choice: ");

            try {

                choice = Integer.parseInt(scanner.nextLine());

                if (choice == 0) {
                    return;
                }

                if (choice < 1 || choice > vehicles.size()) {
                    System.out.println("\nInvalid vehicle selection!");
                    pause();
                    continue;
                }

                Vehicle selectedVehicle = vehicles.get(choice - 1);

                System.out.println("\nSelected Vehicle: "
                        + selectedVehicle.getBrand() + " "
                        + selectedVehicle.getModel());

                System.out.println("Registration Number: "
                        + selectedVehicle.getRegistrationNumber());

                VehicleMileageMenu mileageMenu = new VehicleMileageMenu(
                        scanner,
                        selectedVehicle,
                        odometerManager,
                        fuelManager);

                mileageMenu.displayMenu();

            } catch (NumberFormatException e) {

                System.out.println("\nInvalid input! Enter a numeric choice.");
                pause();
            }
        }
    }

    private void pause() {
        System.out.print("\nPress ENTER to continue...");
        scanner.nextLine();
    }
}