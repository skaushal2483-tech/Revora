package revora.ui.owner.mileage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Scanner;

import revora.model.Vehicle;
import revora.model.OdometerRecord;
import revora.model.FuelRecord;

import revora.manager.OdometerManager;
import revora.manager.FuelManager;

import revora.utility.InputValidator;
import revora.exception.InvalidInputException;

public class OdometerEntry {

    private Scanner scanner;
    private Vehicle vehicle;
    private OdometerManager odometerManager;
    private FuelManager fuelManager;

    public OdometerEntry(
            Scanner scanner,
            Vehicle vehicle,
            OdometerManager odometerManager,
            FuelManager fuelManager) {

        this.scanner = scanner;
        this.vehicle = vehicle;
        this.odometerManager = odometerManager;
        this.fuelManager = fuelManager;
    }

    public void recordOdometerReading() {

        System.out.println("\n==================================================");
        System.out.println("              RECORD ODOMETER READING");
        System.out.println("==================================================");

        System.out.println("Vehicle: " + vehicle.getBrand()
                + " " + vehicle.getModel());

        System.out.println("Registration: "
                + vehicle.getRegistrationNumber());

        // Reading Date
        LocalDate readingDate;

        try {

            System.out.print("\nEnter Reading Date (DD-MM-YYYY): ");

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd-MM-uuuu")
                            .withResolverStyle(ResolverStyle.STRICT);

            readingDate = LocalDate.parse(
                    scanner.nextLine().trim(), formatter);

            if (readingDate.isAfter(LocalDate.now())) {
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

        // Odometer Reading
        double reading;

        try {

            System.out.print("Enter Odometer Reading (KM): ");

            reading = Double.parseDouble(
                    scanner.nextLine().trim());

            InputValidator.validateNonNegativeNumber(
                    reading, "Odometer reading");

        } catch (NumberFormatException e) {

            System.out.println("\nInvalid odometer reading!");
            pause();
            return;

        } catch (InvalidInputException e) {

            System.out.println("\n" + e.getMessage());
            pause();
            return;
        }

        // Get existing records
        ArrayList<OdometerRecord> odometerRecords =
                odometerManager.getRecordsByVehicle(
                        vehicle.getVehicleId());

        ArrayList<FuelRecord> fuelRecords =
                fuelManager.getRecordsByVehicle(
                        vehicle.getVehicleId());

        // Track the latest recorded date
        LocalDate latestRecordedDate = null;

        // Validate against previous odometer records
        for (OdometerRecord previousRecord : odometerRecords) {

            LocalDate previousDate = previousRecord.getReadingDate();
            double previousReading =
                    previousRecord.getOdometerReading();

            if (previousDate.isAfter(readingDate)
                    && reading > previousReading) {

                System.out.println(
                        "\nInvalid reading! An earlier date cannot");

                System.out.println(
                        "have a higher odometer reading than a later date.");

                pause();
                return;
            }

            if (!previousDate.isAfter(readingDate)
                    && reading < previousReading) {

                System.out.println(
                        "\nInvalid reading! Odometer reading cannot");

                System.out.println(
                        "decrease compared to an earlier or same date.");

                pause();
                return;
            }

            if (latestRecordedDate == null
                    || previousDate.isAfter(latestRecordedDate)) {

                latestRecordedDate = previousDate;
            }
        }

        // Validate against previous fuel records
        for (FuelRecord previousRecord : fuelRecords) {

            LocalDate previousDate = previousRecord.getRefillDate();
            double previousReading =
                    previousRecord.getOdometerReading();

            if (previousDate.isAfter(readingDate)
                    && reading > previousReading) {

                System.out.println(
                        "\nInvalid reading! An earlier date cannot");

                System.out.println(
                        "have a higher odometer reading than a later date.");

                pause();
                return;
            }

            if (!previousDate.isAfter(readingDate)
                    && reading < previousReading) {

                System.out.println(
                        "\nInvalid reading! Odometer reading cannot");

                System.out.println(
                        "decrease compared to an earlier or same date.");

                pause();
                return;
            }

            if (latestRecordedDate == null
                    || previousDate.isAfter(latestRecordedDate)) {

                latestRecordedDate = previousDate;
            }
        }

        // Generate Record ID
        String recordId = odometerManager.generateRecordId();

        OdometerRecord record = new OdometerRecord(
                recordId,
                vehicle.getVehicleId(),
                readingDate,
                reading);

        // Save Record
        if (odometerManager.addOdometerRecord(record)) {

            // Update vehicle odometer only for the latest date
            if (latestRecordedDate == null
                    || !readingDate.isBefore(latestRecordedDate)) {

                vehicle.setCurrentOdometer(
                        (int) Math.round(reading));
            }

            System.out.println(
                    "\nOdometer reading recorded successfully!");

            System.out.println("Record ID: " + recordId);
            System.out.println("Reading: " + reading + " KM");

        } else {

            System.out.println("\nUnable to save odometer reading.");
        }

        pause();
    }

    private void pause() {

        System.out.print("\nPress ENTER to continue...");
        scanner.nextLine();
    }
}