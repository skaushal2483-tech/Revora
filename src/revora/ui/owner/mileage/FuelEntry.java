package revora.ui.owner.mileage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Scanner;

import revora.model.Vehicle;
import revora.model.FuelRecord;
import revora.model.OdometerRecord;

import revora.manager.FuelManager;
import revora.manager.OdometerManager;

import revora.utility.InputValidator;
import revora.exception.InvalidInputException;

public class FuelEntry {

    private Scanner scanner;
    private Vehicle vehicle;
    private FuelManager fuelManager;
    private OdometerManager odometerManager;

    public FuelEntry(
            Scanner scanner,
            Vehicle vehicle,
            FuelManager fuelManager,
            OdometerManager odometerManager) {

        this.scanner = scanner;
        this.vehicle = vehicle;
        this.fuelManager = fuelManager;
        this.odometerManager = odometerManager;
    }

    public void recordFuelRefill() {

        System.out.println("\n==================================================");
        System.out.println("                 RECORD FUEL REFILL");
        System.out.println("==================================================");

        System.out.println("Vehicle: " + vehicle.getBrand()
                + " " + vehicle.getModel());

        System.out.println("Registration: "
                + vehicle.getRegistrationNumber());

        // Refill Date
        LocalDate refillDate;

        try {

            System.out.print("\nEnter Refill Date (DD-MM-YYYY): ");

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd-MM-uuuu")
                            .withResolverStyle(ResolverStyle.STRICT);

            refillDate = LocalDate.parse(
                    scanner.nextLine().trim(), formatter);

            if (refillDate.isAfter(LocalDate.now())) {
                throw new InvalidInputException(
                        "Future dates are not allowed.");
            }

        } catch (DateTimeParseException e) {

            System.out.println("\nInvalid date! Enter a valid date.");
            pause();
            return;

        } catch (InvalidInputException e) {

            System.out.println("\n" + e.getMessage());
            pause();
            return;
        }

        double odometerReading;
        double fuelQuantity;
        double pricePerLitre;

        try {

            // Odometer Reading
            System.out.print("Enter Odometer Reading (KM): ");

            odometerReading = Double.parseDouble(
                    scanner.nextLine().trim());

            InputValidator.validateNonNegativeNumber(
                    odometerReading, "Odometer reading");

            // Get existing records
            ArrayList<OdometerRecord> odometerRecords =
                    odometerManager.getRecordsByVehicle(
                            vehicle.getVehicleId());

            ArrayList<FuelRecord> fuelRecords =
                    fuelManager.getRecordsByVehicle(
                            vehicle.getVehicleId());

            // Validate against previous odometer records
            for (OdometerRecord previousRecord : odometerRecords) {

                LocalDate previousDate =
                        previousRecord.getReadingDate();

                double previousReading =
                        previousRecord.getOdometerReading();

                if (previousDate.isAfter(refillDate)
                        && odometerReading > previousReading) {

                    throw new InvalidInputException(
                            "An earlier date cannot have a higher "
                            + "odometer reading than a later date.");
                }

                if (!previousDate.isAfter(refillDate)
                        && odometerReading < previousReading) {

                    throw new InvalidInputException(
                            "Odometer reading cannot decrease "
                            + "compared to an earlier or same date.");
                }
            }

            // Validate against previous fuel records
            for (FuelRecord previousRecord : fuelRecords) {

                LocalDate previousDate =
                        previousRecord.getRefillDate();

                double previousReading =
                        previousRecord.getOdometerReading();

                if (previousDate.isAfter(refillDate)
                        && odometerReading > previousReading) {

                    throw new InvalidInputException(
                            "An earlier date cannot have a higher "
                            + "odometer reading than a later date.");
                }

                if (!previousDate.isAfter(refillDate)
                        && odometerReading < previousReading) {

                    throw new InvalidInputException(
                            "Odometer reading cannot decrease "
                            + "compared to an earlier or same date.");
                }
            }

            // Fuel Quantity
            System.out.print("Enter Fuel Quantity (Litres): ");

            fuelQuantity = Double.parseDouble(
                    scanner.nextLine().trim());

            InputValidator.validatePositiveNumber(
                    fuelQuantity, "Fuel quantity");

            // Price Per Litre
            System.out.print("Enter Price Per Litre (Rs.): ");

            pricePerLitre = Double.parseDouble(
                    scanner.nextLine().trim());

            InputValidator.validatePositiveNumber(
                    pricePerLitre, "Price per litre");

        } catch (NumberFormatException e) {

            System.out.println("\nInvalid numeric input!");
            pause();
            return;

        } catch (InvalidInputException e) {

            System.out.println("\n" + e.getMessage());
            pause();
            return;
        }

        // Generate Record ID
        String recordId = fuelManager.generateRecordId();

        // Create Fuel Record
        FuelRecord record = new FuelRecord(
                recordId,
                vehicle.getVehicleId(),
                refillDate,
                odometerReading,
                fuelQuantity,
                pricePerLitre);

        // Save Record
        if (fuelManager.addFuelRecord(record)) {

            // Find the latest existing record date
            LocalDate latestRecordedDate = null;

            for (OdometerRecord previousRecord :
                    odometerManager.getRecordsByVehicle(
                            vehicle.getVehicleId())) {

                LocalDate date = previousRecord.getReadingDate();

                if (latestRecordedDate == null
                        || date.isAfter(latestRecordedDate)) {

                    latestRecordedDate = date;
                }
            }

            for (FuelRecord previousRecord :
                    fuelManager.getRecordsByVehicle(
                            vehicle.getVehicleId())) {

                if (previousRecord.getRecordId().equals(recordId)) {
                    continue;
                }

                LocalDate date = previousRecord.getRefillDate();

                if (latestRecordedDate == null
                        || date.isAfter(latestRecordedDate)) {

                    latestRecordedDate = date;
                }
            }

            // Update vehicle odometer only for the latest date
            if (latestRecordedDate == null
                    || !refillDate.isBefore(latestRecordedDate)) {

                vehicle.setCurrentOdometer(
                        (int) Math.round(odometerReading));
            }

            System.out.println("\nFuel refill recorded successfully!");
            System.out.println("Record ID       : " + recordId);
            System.out.println("Fuel Quantity   : " + fuelQuantity + " Litres");
            System.out.println("Price Per Litre : Rs. " + pricePerLitre);

            System.out.printf(
                    "Total Fuel Cost : Rs. %.2f%n",
                    record.getTotalCost());

        } else {

            System.out.println("\nUnable to save fuel record.");
        }

        pause();
    }

    private void pause() {

        System.out.print("\nPress ENTER to continue...");
        scanner.nextLine();
    }
}