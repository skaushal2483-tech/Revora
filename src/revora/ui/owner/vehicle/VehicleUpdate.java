package revora.ui.owner.vehicle;

import java.util.Scanner;
import java.time.Year;

import revora.model.Owner;
import revora.model.Vehicle;
import revora.manager.VehicleManager;

import revora.utility.InputValidator;
import revora.exception.InvalidVehicleNumberException;
import revora.exception.InvalidInputException;

public class VehicleUpdate {

    private Scanner scanner;
    private VehicleManager vehicleManager;
    private Owner owner;

    public VehicleUpdate(
            Scanner scanner,
            VehicleManager vehicleManager,
            Owner owner) {

        this.scanner = scanner;
        this.vehicleManager = vehicleManager;
        this.owner = owner;
    }

    public void updateVehicle() {

        System.out.println();
        System.out.println("============================================================");
        System.out.println("                     UPDATE VEHICLE");
        System.out.println("============================================================");

        System.out.print("\n  Enter Registration Number: ");

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

        int choice = 0;

        do {

            System.out.println();
            System.out.println("============================================================");
            System.out.println("                   VEHICLE UPDATE MENU");
            System.out.println("============================================================");
            System.out.println("  VEHICLE: " + vehicle.getBrand()
                    + " " + vehicle.getModel());
            System.out.println("  1. Update Registration Number");
            System.out.println("  2. Update Brand");
            System.out.println("  3. Update Model");
            System.out.println("  4. Update Manufacturing Year");
            System.out.println("  5. Update Fuel Type");
            System.out.println("  6. Exit");
            System.out.println("============================================================");

            System.out.print("  Enter your choice: ");

            try {

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        updateRegistrationNumber(vehicle);
                        break;

                    case 2:
                        updateBrand(vehicle);
                        break;

                    case 3:
                        updateModel(vehicle);
                        break;

                    case 4:
                        updateManufacturingYear(vehicle);
                        break;

                    case 5:
                        updateFuelType(vehicle);
                        break;

                    case 6:
                        System.out.println(
                                "\n  Returning to My Garage...");
                        break;

                    default:
                        System.out.println("\n  Invalid choice!");
                        pause();
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "\n  Enter a valid numeric choice.");
                pause();
            }

        } while (choice != 6);
    }

    // Update Registration Number
    private void updateRegistrationNumber(Vehicle vehicle) {

        System.out.println(
                "\n  CURRENT REGISTRATION: "
                        + vehicle.getRegistrationNumber());

        while (true) {

            System.out.print("  Enter New Registration Number: ");

            String registration = scanner.nextLine()
                    .trim()
                    .replaceAll("[ -]", "")
                    .toUpperCase();

            try {

                InputValidator.validateVehicleNumber(registration);

                Vehicle existing =
                        vehicleManager.findVehicleByRegistration(
                                registration);

                if (existing != null && existing != vehicle) {

                    System.out.println(
                            "  Registration number already exists.");
                    continue;
                }

                saveChanges();

                vehicle.setRegistrationNumber(registration);

                System.out.println(
                        "\n  Registration number updated successfully!");
                break;

            } catch (InvalidVehicleNumberException e) {

                System.out.println("  " + e.getMessage());
            }
        }
    }

    // Update Brand
    private void updateBrand(Vehicle vehicle) {

        System.out.println("\n  CURRENT BRAND: " + vehicle.getBrand());

        String brand = getValidInput(
                "  Enter New Brand: ", "brand");

        saveChanges();

        vehicle.setBrand(brand);

        System.out.println("\n  Brand updated successfully!");
    }

    // Update Model
    private void updateModel(Vehicle vehicle) {

        System.out.println("\n  CURRENT MODEL: " + vehicle.getModel());

        String model = getValidInput(
                "  Enter New Model: ", "model");

        saveChanges();

        vehicle.setModel(model);

        System.out.println("\n  Model updated successfully!");
    }

    // Update Manufacturing Year
    private void updateManufacturingYear(Vehicle vehicle) {

        System.out.println(
                "\n  CURRENT YEAR: " + vehicle.getManufacturingYear());

        while (true) {

            System.out.print("  Enter New Manufacturing Year: ");

            try {

                int year = Integer.parseInt(scanner.nextLine().trim());

                if (year < 1900 || year > Year.now().getValue()) {

                    throw new InvalidInputException(
                            "Enter a valid manufacturing year.");
                }

                saveChanges();

                vehicle.setManufacturingYear(year);

                System.out.println(
                        "\n  Manufacturing year updated successfully!");
                break;

            } catch (NumberFormatException e) {

                System.out.println("  Enter a valid numeric year.");

            } catch (InvalidInputException e) {

                System.out.println("  " + e.getMessage());
            }
        }
    }

    // Update Fuel Type
    private void updateFuelType(Vehicle vehicle) {

        String fuelType = "";

        while (true) {

            System.out.println("\n  SELECT NEW FUEL TYPE");
            System.out.println("  1. Petrol");
            System.out.println("  2. Diesel");
            System.out.println("  3. Electric");
            System.out.println("  4. CNG");

            System.out.print("  Enter choice: ");

            switch (scanner.nextLine().trim()) {

                case "1":
                    fuelType = "Petrol";
                    break;

                case "2":
                    fuelType = "Diesel";
                    break;

                case "3":
                    fuelType = "Electric";
                    break;

                case "4":
                    fuelType = "CNG";
                    break;

                default:
                    System.out.println("  Invalid choice!");
                    continue;
            }

            break;
        }

        saveChanges();

        vehicle.setFuelType(fuelType);

        System.out.println("\n  Fuel type updated successfully!");
    }

    // Reusable Brand and Model Validation
    private String getValidInput(
            String message, String fieldType) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {

                if (fieldType.equals("brand")) {
                    InputValidator.validateBrand(input);
                } else {
                    InputValidator.validateModel(input);
                }

                return input;

            } catch (InvalidInputException e) {

                System.out.println("  " + e.getMessage());
            }
        }
    }

    private void saveChanges() {

        System.out.print(
                "\n  Press ENTER to save changes...");
        scanner.nextLine();
    }

    private void pause() {

        System.out.print(
                "\n  Press ENTER to continue...");
        scanner.nextLine();
    }
}