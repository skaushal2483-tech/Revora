package revora.ui.owner.mileage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import revora.model.Vehicle;
import revora.model.FuelRecord;

import revora.manager.FuelManager;

public class FuelEfficiency {

    private Scanner scanner;
    private Vehicle vehicle;
    private FuelManager fuelManager;

    public FuelEfficiency(Scanner scanner, Vehicle vehicle,
            FuelManager fuelManager) {

        this.scanner = scanner;
        this.vehicle = vehicle;
        this.fuelManager = fuelManager;
    }

    public void calculateEfficiency() {

        System.out.println("\n==================================================");
        System.out.println("             FUEL EFFICIENCY CALCULATOR");
        System.out.println("==================================================");

        ArrayList<FuelRecord> records =
                fuelManager.getRecordsByVehicle(vehicle.getVehicleId());

        if (records.size() < 2) {
            System.out.println("\nInsufficient fuel records!");
            System.out.println("At least two fuel records are required.");
            pause();
            return;
        }

        records.sort(Comparator.comparing(FuelRecord::getRefillDate));

        double totalDistance = 0;
        double totalFuel = 0;
        int validIntervals = 0;

        for (int i = 1; i < records.size(); i++) {

            FuelRecord previous = records.get(i - 1);
            FuelRecord current = records.get(i);

            double distance = current.getOdometerReading()
                    - previous.getOdometerReading();

            if (distance > 0 && current.getRefillDate()
                    .isAfter(previous.getRefillDate())) {

                totalDistance += distance;
                totalFuel += current.getFuelQuantity();
                validIntervals++;
            }
        }

        if (validIntervals == 0 || totalFuel <= 0) {
            System.out.println("\nUnable to calculate fuel efficiency.");
            System.out.println("Please check your fuel and odometer records.");
            pause();
            return;
        }

        double efficiency = totalDistance / totalFuel;

        System.out.println("\nVehicle: " + vehicle.getBrand()
                + " " + vehicle.getModel());

        System.out.printf("Total Distance Travelled : %.2f KM%n",
                totalDistance);

        System.out.printf("Fuel Considered          : %.2f Litres%n",
                totalFuel);

        System.out.printf("Estimated Fuel Efficiency: %.2f KM/L%n",
                efficiency);

        System.out.println("\nNote: This is an approximate estimate.");
        System.out.println("Accuracy may vary with partial refills.");

        System.out.println("==================================================");

        pause();
    }

    private void pause() {
        System.out.print("\nPress ENTER to continue...");
        scanner.nextLine();
    }
}