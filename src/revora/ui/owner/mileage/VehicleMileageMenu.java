package revora.ui.owner.mileage;

import java.util.Scanner;

import revora.model.Vehicle;

import revora.manager.OdometerManager;
import revora.manager.FuelManager;

public class VehicleMileageMenu {

    private Scanner scanner;
    private Vehicle vehicle;
    private OdometerManager odometerManager;
    private FuelManager fuelManager;

    public VehicleMileageMenu(Scanner scanner, Vehicle vehicle,
            OdometerManager odometerManager,
            FuelManager fuelManager) {

        this.scanner = scanner;
        this.vehicle = vehicle;
        this.odometerManager = odometerManager;
        this.fuelManager = fuelManager;
    }

    public void displayMenu() {

        int choice = 0;

        do {
            System.out.println("\n==================================================");
            System.out.println("             MILEAGE & FUEL TRACKING");
            System.out.println("==================================================");
            System.out.println("Vehicle: " + vehicle.getBrand()
                    + " " + vehicle.getModel());
            System.out.println("Registration: "
                    + vehicle.getRegistrationNumber());
            System.out.println("==================================================");
            System.out.println("1. Record Odometer Reading");
            System.out.println("2. Record Fuel Refill");
            System.out.println("3. View Odometer History");
            System.out.println("4. View Fuel History");
            System.out.println("5. Calculate Average Fuel Efficiency");
            System.out.println("6. View Fuel Expense Summary");
            System.out.println("7. Back to Vehicle Selection");
            System.out.println("==================================================");

            System.out.print("\nEnter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        OdometerEntry odometerEntry = new OdometerEntry(
                                scanner,
                                vehicle,
                                odometerManager,
                                fuelManager);

                        odometerEntry.recordOdometerReading();
                        break;

                    case 2:
                        FuelEntry fuelEntry = new FuelEntry(
                                scanner,
                                vehicle,
                                fuelManager,
                                odometerManager);

                        fuelEntry.recordFuelRefill();
                        break;

                    case 3:
                        OdometerHistory odometerHistory = new OdometerHistory(
                                scanner, vehicle, odometerManager);

                        odometerHistory.displayHistory();
                        break;

                    case 4:
                        FuelHistory fuelHistory = new FuelHistory(
                                scanner, vehicle, fuelManager);

                        fuelHistory.displayHistory();
                        break;

                    case 5:
                        FuelEfficiency fuelEfficiency = new FuelEfficiency(
                                scanner, vehicle, fuelManager);

                        fuelEfficiency.calculateEfficiency();
                        break;

                    case 6:
                        FuelExpenseSummary expenseSummary = new FuelExpenseSummary(
                                scanner, vehicle, fuelManager);

                        expenseSummary.displaySummary();
                        break;

                    case 7:
                        System.out.println("\nReturning to vehicle selection...");
                        break;

                    default:
                        System.out.println("\nInvalid choice! Please try again.");
                        pause();
                }

            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input! Enter a numeric choice.");
                pause();
            }

        } while (choice != 7);
    }

    private void pause() {
        System.out.print("\nPress ENTER to continue...");
        scanner.nextLine();
    }
}