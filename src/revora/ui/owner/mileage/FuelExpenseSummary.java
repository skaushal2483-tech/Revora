package revora.ui.owner.mileage;
import java.util.Scanner;

import revora.model.Vehicle;
import revora.manager.FuelManager;

public class FuelExpenseSummary {

    private Scanner scanner;
    private Vehicle vehicle;
    private FuelManager fuelManager;

    public FuelExpenseSummary(Scanner scanner, Vehicle vehicle,
            FuelManager fuelManager) {

        this.scanner = scanner;
        this.vehicle = vehicle;
        this.fuelManager = fuelManager;
    }

    public void displaySummary() {

        System.out.println("\n==================================================");
        System.out.println("               FUEL EXPENSE SUMMARY");
        System.out.println("==================================================");

        System.out.println("Vehicle: " + vehicle.getBrand()
                + " " + vehicle.getModel());

        System.out.println("Registration: "
                + vehicle.getRegistrationNumber());

        double totalFuel =
                fuelManager.getTotalFuelConsumed(vehicle.getVehicleId());

        double totalExpense =
                fuelManager.getTotalFuelExpense(vehicle.getVehicleId());

        int totalRefills =
                fuelManager.getRecordsByVehicle(vehicle.getVehicleId()).size();

        System.out.println("\n--------------------------------------------------");

        System.out.printf("Total Fuel Consumed  : %.2f Litres%n", totalFuel);

        System.out.printf("Total Fuel Expense   : Rs. %.2f%n", totalExpense);

        System.out.println("Total Refills        : " + totalRefills);

        if (totalFuel > 0) {

            System.out.printf("Average Price/Litre  : Rs. %.2f%n",
                    totalExpense / totalFuel);
        }

        if (totalRefills > 0) {

            System.out.printf("Average Cost/Refill  : Rs. %.2f%n",
                    totalExpense / totalRefills);
        }

        System.out.println("--------------------------------------------------");
        System.out.println("==================================================");

        if (totalRefills == 0) {
            System.out.println("\nNo fuel expenses recorded yet.");
        }

        pause();
    }

    private void pause() {
        System.out.print("\nPress ENTER to continue...");
        scanner.nextLine();
    }
}