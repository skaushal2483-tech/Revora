package revora.ui.owner.mileage;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import revora.model.Vehicle;
import revora.model.FuelRecord;

import revora.manager.FuelManager;

public class FuelHistory {

    private Scanner scanner;
    private Vehicle vehicle;
    private FuelManager fuelManager;

    public FuelHistory(Scanner scanner, Vehicle vehicle,
            FuelManager fuelManager) {

        this.scanner = scanner;
        this.vehicle = vehicle;
        this.fuelManager = fuelManager;
    }

    public void displayHistory() {

        System.out.println("\n==================================================");
        System.out.println("                  FUEL HISTORY");
        System.out.println("==================================================");

        System.out.println("Vehicle: " + vehicle.getBrand()
                + " " + vehicle.getModel());

        System.out.println("Registration: "
                + vehicle.getRegistrationNumber());

        ArrayList<FuelRecord> records =
                fuelManager.getRecordsByVehicle(vehicle.getVehicleId());

        if (records.isEmpty()) {
            System.out.println("\nNo fuel records found.");
            pause();
            return;
        }

        records.sort(Comparator.comparing(FuelRecord::getRefillDate));

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println("\n--------------------------------------------------");
        System.out.printf("%-12s %-12s %-10s %-10s%n",
                "Record ID", "Date", "Litres", "Cost");
        System.out.println("--------------------------------------------------");

        for (FuelRecord record : records) {

            System.out.printf("%-12s %-12s %-10.2f Rs.%-10.2f%n",
                    record.getRecordId(),
                    record.getRefillDate().format(formatter),
                    record.getFuelQuantity(),
                    record.getTotalCost());
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("Total Fuel Consumed : %.2f Litres%n",
                fuelManager.getTotalFuelConsumed(vehicle.getVehicleId()));

        System.out.printf("Total Fuel Expense  : Rs. %.2f%n",
                fuelManager.getTotalFuelExpense(vehicle.getVehicleId()));

        System.out.println("Total Records       : " + records.size());
        System.out.println("==================================================");

        pause();
    }

    private void pause() {
        System.out.print("\nPress ENTER to continue...");
        scanner.nextLine();
    }
}