package revora.ui.owner.vehicle;

import java.util.Scanner;
import java.time.Year;

import revora.model.Owner;
import revora.model.Vehicle;
import revora.manager.VehicleManager;

import revora.utility.InputValidator;
import revora.exception.InvalidVehicleNumberException;

public class VehicleRegistration {

    private Scanner scanner;
    private VehicleManager vehicleManager;
    private Owner owner;

    public VehicleRegistration(
            Scanner scanner,
            VehicleManager vehicleManager,
            Owner owner) {

        this.scanner = scanner;
        this.vehicleManager = vehicleManager;
        this.owner = owner;
    }

    public void registerVehicle() {

        System.out.println();
        System.out.println("============================================================");
        System.out.println("                    REGISTER VEHICLE");
        System.out.println("============================================================");

        // Vehicle Type
        String vehicleType;

        while (true) {

            System.out.println("\n  Select Vehicle Type:");
            System.out.println("  1. Car");
            System.out.println("  2. Bike");
            System.out.print("  Enter choice: ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                vehicleType = "Car";
                break;

            } else if (choice.equals("2")) {
                vehicleType = "Bike";
                break;
            }

            System.out.println("  Invalid choice! Select 1 or 2.");
        }

        // Registration Number
        String registrationNumber;

        while (true) {

            System.out.print("\n  Enter Registration Number: ");

            registrationNumber = scanner.nextLine()
                    .trim()
                    .replaceAll("[ -]", "")
                    .toUpperCase();

            try {

                InputValidator.validateVehicleNumber(registrationNumber);

                if (vehicleManager.findVehicleByRegistration(
                        registrationNumber) != null) {

                    System.out.println(
                            "  This vehicle is already registered in REVORA.");
                    continue;
                }

                break;

            } catch (InvalidVehicleNumberException e) {

                System.out.println("  " + e.getMessage());
                System.out.println("  Example: PB10AB1234");
            }
        }

        // Brand
        String brand = getValidInput(
                "\n  Enter Vehicle Brand: ",
                "^[A-Za-z][A-Za-z .'-]{0,39}$",
                "  Invalid brand name."
        );

        // Model
        String model = getValidInput(
                "  Enter Vehicle Model: ",
                "^[A-Za-z0-9][A-Za-z0-9 .'-]{0,39}$",
                "  Invalid model name."
        );

        // Manufacturing Year
        int manufacturingYear;

        while (true) {

            System.out.print("  Enter Manufacturing Year: ");

            try {

                manufacturingYear = Integer.parseInt(
                        scanner.nextLine().trim());

                if (manufacturingYear < 1900
                        || manufacturingYear > Year.now().getValue()) {

                    System.out.println(
                            "  Enter a valid manufacturing year.");
                    continue;
                }

                break;

            } catch (NumberFormatException e) {

                System.out.println("  Please enter a valid year.");
            }
        }

        // Fuel Type
        String fuelType;

        while (true) {

            System.out.println("\n  Select Fuel Type:");
            System.out.println("  1. Petrol");
            System.out.println("  2. Diesel");
            System.out.println("  3. Electric");
            System.out.println("  4. CNG");
            System.out.print("  Enter choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

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
                    System.out.println(
                            "  Invalid choice! Try again.");
                    continue;
            }

            break;
        }

        // Generate Vehicle ID
        String vehicleId = vehicleManager.generateVehicleId();

        // Initial odometer value: 0 means not yet recorded
        int currentOdometer = 0;

        // Create Vehicle Object
        Vehicle vehicle = new Vehicle(
                vehicleId,
                owner.getOwnerId(),
                registrationNumber,
                vehicleType,
                brand,
                model,
                manufacturingYear,
                fuelType,
                currentOdometer
        );

        // Save Vehicle
        if (vehicleManager.addVehicle(vehicle)) {

            System.out.println("\n============================================================");
            System.out.println("              VEHICLE REGISTERED SUCCESSFULLY!");
            System.out.println("============================================================");
            System.out.println("  VEHICLE ID       : " + vehicleId);
            System.out.println("  REGISTRATION NO. : " + registrationNumber);
            System.out.println("  VEHICLE          : " + brand + " " + model);
            System.out.println("  OWNER ID         : " + owner.getOwnerId());
            System.out.println("  ODOMETER         : NOT RECORDED");
            System.out.println("============================================================");

        } else {

            System.out.println("\n  Vehicle registration failed.");
        }

        pause();
    }

    private String getValidInput(
            String message,
            String regex,
            String errorMessage) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (input.matches(regex)) {
                return input;
            }

            System.out.println(errorMessage);
        }
    }

    private void pause() {

        System.out.print("\n  Press ENTER to continue...");
        scanner.nextLine();
    }
}