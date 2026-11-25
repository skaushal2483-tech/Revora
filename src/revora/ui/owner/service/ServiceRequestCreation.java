package revora.ui.owner.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Scanner;

import revora.model.Owner;
import revora.model.Vehicle;
import revora.model.ServiceRequest;

import revora.manager.VehicleManager;
import revora.manager.ServiceManager;

import revora.enums.ServiceCategory;
import revora.enums.ServiceStatus;

import revora.utility.InputValidator;
import revora.exception.InvalidInputException;

public class ServiceRequestCreation {

    private Scanner scanner;
    private Owner owner;
    private VehicleManager vehicleManager;
    private ServiceManager serviceManager;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    public ServiceRequestCreation(Scanner scanner, Owner owner,
            VehicleManager vehicleManager, ServiceManager serviceManager) {

        this.scanner = scanner;
        this.owner = owner;
        this.vehicleManager = vehicleManager;
        this.serviceManager = serviceManager;
    }

    public void createServiceRequest() {

        ArrayList<Vehicle> vehicles =
                vehicleManager.getVehiclesByOwner(owner.getOwnerId());

        System.out.println("\n============================================================");
        System.out.println("                   REQUEST A SERVICE");
        System.out.println("============================================================");

        if (vehicles.isEmpty()) {
            System.out.println("\n  You have no registered vehicles.");
            System.out.println("  Please register a vehicle in My Garage first.");
            pause();
            return;
        }

        // Select Vehicle
        System.out.println("\n  SELECT YOUR VEHICLE");
        System.out.println("------------------------------------------------------------");

        for (int i = 0; i < vehicles.size(); i++) {

            Vehicle vehicle = vehicles.get(i);

            System.out.println("  " + (i + 1) + ". "
                    + vehicle.getVehicleId() + " | "
                    + vehicle.getRegistrationNumber() + " | "
                    + vehicle.getBrand() + " "
                    + vehicle.getModel());
        }

        System.out.println("  0. Back");

        int vehicleChoice = readInteger(
                "\n  Select your vehicle: ", 0, vehicles.size());

        if (vehicleChoice == 0) {
            return;
        }

        Vehicle selectedVehicle = vehicles.get(vehicleChoice - 1);

        // Select Service Category
        System.out.println("\n============================================================");
        System.out.println("                   SERVICE CATEGORY");
        System.out.println("============================================================");

        ServiceCategory[] categories = ServiceCategory.values();

        for (int i = 0; i < categories.length; i++) {
            System.out.println("  " + (i + 1) + ". "
                    + formatCategory(categories[i]));
        }

        System.out.println("  0. Back");

        int categoryChoice = readInteger(
                "\n  Select service category: ", 0, categories.length);

        if (categoryChoice == 0) {
            return;
        }

        ServiceCategory selectedCategory =
                categories[categoryChoice - 1];

        // Enter Description
        String description;

        while (true) {

            System.out.print("\n  Describe the service required: ");
            description = scanner.nextLine().trim();

            try {

                InputValidator.validateNotEmpty(
                        description, "Service description");

                if (description.length() > 250) {
                    throw new InvalidInputException(
                            "Description cannot exceed 250 characters.");
                }

                break;

            } catch (InvalidInputException e) {
                System.out.println("  " + e.getMessage());
            }
        }

        // Enter Preferred Date
        LocalDate preferredDate;

        while (true) {

            System.out.print("\n  Enter Preferred Date (DD-MM-YYYY): ");
            String dateInput = scanner.nextLine().trim();

            try {

                preferredDate = LocalDate.parse(dateInput, DATE_FORMAT);

                if (preferredDate.isBefore(LocalDate.now())) {
                    throw new InvalidInputException(
                            "Past dates are not allowed.");
                }

                break;

            } catch (DateTimeParseException e) {
                System.out.println(
                        "  Invalid date. Please use DD-MM-YYYY format.");

            } catch (InvalidInputException e) {
                System.out.println("  " + e.getMessage());
            }
        }

        // Create Service Request
        String requestId = serviceManager.generateRequestId();

        ServiceRequest request = new ServiceRequest(
                requestId,
                owner.getOwnerId(),
                selectedVehicle.getVehicleId(),
                selectedCategory,
                description,
                preferredDate,
                ServiceStatus.PENDING);

        boolean added = serviceManager.addServiceRequest(request);

        if (added) {

            System.out.println("\n============================================================");
            System.out.println("              SERVICE REQUEST CREATED!");
            System.out.println("============================================================");
            System.out.println("  Request ID       : " + requestId);
            System.out.println("  Vehicle          : "
                    + selectedVehicle.getRegistrationNumber());
            System.out.println("  Service Category : "
                    + formatCategory(selectedCategory));
            System.out.println("  Description      : " + description);
            System.out.println("  Preferred Date   : "
                    + preferredDate.format(DATE_FORMAT));
            System.out.println("  Request Status   : Pending");
            System.out.println("============================================================");

        } else {
            System.out.println("\n  Unable to create service request.");
        }

        pause();
    }

    // Read Valid Integer
    private int readInteger(String message, int min, int max) {

        while (true) {

            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {

                int choice = Integer.parseInt(input);

                if (choice >= min && choice <= max) {
                    return choice;
                }

                System.out.println(
                        "  Please enter a choice between " + min + " and " + max + ".");

            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid numeric choice.");
            }
        }
    }

    // Format Category For Display
    private String formatCategory(ServiceCategory category) {

        String[] words = category.name().split("_");

        StringBuilder formatted = new StringBuilder();

        for (String word : words) {

            formatted.append(word.charAt(0))
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }

        return formatted.toString().trim();
    }

    // Pause Screen
    private void pause() {
        System.out.print("\n  Press ENTER to continue...");
        scanner.nextLine();
    }
}