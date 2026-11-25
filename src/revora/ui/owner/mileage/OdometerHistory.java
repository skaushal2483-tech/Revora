package revora.ui.owner.mileage;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import revora.model.Vehicle;
import revora.model.OdometerRecord;

import revora.manager.OdometerManager;

public class OdometerHistory {

    private Scanner scanner;
    private Vehicle vehicle;
    private OdometerManager odometerManager;

    public OdometerHistory(Scanner scanner, Vehicle vehicle,
            OdometerManager odometerManager) {

        this.scanner = scanner;
        this.vehicle = vehicle;
        this.odometerManager = odometerManager;
    }

    public void displayHistory() {

        System.out.println("\n==================================================");
        System.out.println("                ODOMETER HISTORY");
        System.out.println("==================================================");

        System.out.println("Vehicle: " + vehicle.getBrand()
                + " " + vehicle.getModel());

        System.out.println("Registration: "
                + vehicle.getRegistrationNumber());

        ArrayList<OdometerRecord> records =
                odometerManager.getRecordsByVehicle(vehicle.getVehicleId());

        if (records.isEmpty()) {
            System.out.println("\nNo odometer records found.");
            pause();
            return;
        }

        records.sort(Comparator.comparing(
                OdometerRecord::getReadingDate));

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println("\n--------------------------------------------------");
        System.out.printf("%-12s %-15s %-15s%n",
                "Record ID", "Date", "Reading (KM)");
        System.out.println("--------------------------------------------------");

        for (OdometerRecord record : records) {

            System.out.printf("%-12s %-15s %-15.2f%n",
                    record.getRecordId(),
                    record.getReadingDate().format(formatter),
                    record.getOdometerReading());
        }

        System.out.println("--------------------------------------------------");
        System.out.println("Total Records: " + records.size());

        System.out.println("\n==================================================");

        pause();
    }

    private void pause() {
        System.out.print("\nPress ENTER to continue...");
        scanner.nextLine();
    }
}