package revora.ui.owner.vehicle;

import java.util.Scanner;

import revora.model.Owner;
import revora.model.Vehicle;
import revora.manager.VehicleManager;
import revora.utility.InputValidator;
import revora.exception.InvalidVehicleNumberException;

public class VehicleSearch {

    private Scanner scanner;
    private VehicleManager vehicleManager;
    private Owner owner;

    public VehicleSearch(Scanner scanner, VehicleManager vehicleManager, Owner owner) {
        this.scanner = scanner;
        this.vehicleManager = vehicleManager;
        this.owner = owner;
    }

    public void searchVehicle() {

        System.out.println();
        System.out.println("============================================================");
        System.out.println("                      SEARCH VEHICLE");
        System.out.println("============================================================");

        System.out.print("\n  Enter Vehicle Registration Number: ");

        String registrationNumber = scanner.nextLine()
                .trim()
                .replaceAll("[ -]", "")
                .toUpperCase();

        try {
            InputValidator.validateVehicleNumber(registrationNumber);

        } catch (InvalidVehicleNumberException e) {
            System.out.println("\n  " + e.getMessage());
            System.out.print("\n  Press ENTER to return...");
            scanner.nextLine();
            return;
        }

        Vehicle vehicle = vehicleManager.findVehicleByRegistration(registrationNumber);

        if (vehicle == null ||
                !vehicle.getOwnerId().equalsIgnoreCase(owner.getOwnerId())) {

            System.out.println("\n  VEHICLE NOT FOUND IN YOUR GARAGE.");

        } else {

            System.out.println("\n  VEHICLE FOUND SUCCESSFULLY!");

            vehicle.displayVehicleDetails();
        }

        System.out.print("\n  Press ENTER to return...");
        scanner.nextLine();
    }
}