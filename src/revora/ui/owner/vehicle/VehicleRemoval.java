package revora.ui.owner.vehicle;

import java.util.Scanner;

import revora.model.Owner;
import revora.model.Vehicle;

import revora.manager.VehicleManager;
import revora.manager.ServiceManager;
import revora.manager.OdometerManager;
import revora.manager.FuelManager;

import revora.utility.InputValidator;
import revora.exception.InvalidVehicleNumberException;

public class VehicleRemoval {

    private Scanner scanner;

    private VehicleManager vehicleManager;
    private ServiceManager serviceManager;
    private OdometerManager odometerManager;
    private FuelManager fuelManager;

    private Owner owner;

    public VehicleRemoval(
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

    public void removeVehicle() {

        System.out.println();
        System.out.println("============================================================");
        System.out.println("                     REMOVE VEHICLE");
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
            pause();
            return;
        }

        Vehicle vehicle = vehicleManager.findVehicleByRegistration(
                registrationNumber);

        if (vehicle == null
                || !vehicle.getOwnerId().equalsIgnoreCase(owner.getOwnerId())) {

            System.out.println("\n  VEHICLE NOT FOUND IN YOUR GARAGE.");
            pause();
            return;
        }

        System.out.println("\n  VEHICLE SELECTED FOR REMOVAL:");
        vehicle.displayVehicleDetails();

        System.out.print(
                "\n  Are you sure you want to remove this vehicle? (Y/N): ");

        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("Y")) {

            boolean removed = vehicleManager.removeVehicle(
                    vehicle.getVehicleId(),
                    owner.getOwnerId()
            );

            if (removed) {

                // Remove associated service requests
                serviceManager.removeRequestsByVehicle(
                        vehicle.getVehicleId());

                // Remove associated odometer records
                odometerManager.removeRecordsByVehicle(
                        vehicle.getVehicleId());

                // Remove associated fuel records
                fuelManager.removeRecordsByVehicle(
                        vehicle.getVehicleId());

                System.out.println(
                        "\n  VEHICLE AND ASSOCIATED RECORDS REMOVED SUCCESSFULLY!");

            } else {

                System.out.println("\n  UNABLE TO REMOVE VEHICLE.");
            }

        } else {

            System.out.println("\n  VEHICLE REMOVAL CANCELLED.");
        }

        pause();
    }

    private void pause() {

        System.out.print("\n  Press ENTER to continue...");
        scanner.nextLine();
    }
}