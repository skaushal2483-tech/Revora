package revora.ui.owner.vehicle;

import java.util.ArrayList;
import java.util.Scanner;

import revora.model.Owner;
import revora.model.Vehicle;
import revora.manager.VehicleManager;

public class VehicleViewing {

    private Scanner scanner;
    private VehicleManager vehicleManager;
    private Owner owner;

    public VehicleViewing(
            Scanner scanner,
            VehicleManager vehicleManager,
            Owner owner) {

        this.scanner = scanner;
        this.vehicleManager = vehicleManager;
        this.owner = owner;
    }

    public void viewAllVehicles() {

        System.out.println();
        System.out.println("============================================================");
        System.out.println("                       MY GARAGE");
        System.out.println("============================================================");

        ArrayList<Vehicle> vehicles =
                vehicleManager.getVehiclesByOwner(owner.getOwnerId());

        if (vehicles.isEmpty()) {

            System.out.println("\n  YOUR GARAGE IS CURRENTLY EMPTY.");
            System.out.println("  Register your first vehicle to get started!");

        } else {

            System.out.println(
                    "\n  TOTAL REGISTERED VEHICLES: " + vehicles.size());

            int count = 1;

            for (Vehicle vehicle : vehicles) {

                System.out.println();
                System.out.println("------------------------------------------------------------");
                System.out.println("  VEHICLE " + count);
                System.out.println("------------------------------------------------------------");

                System.out.println(
                        "  Vehicle ID         : " + vehicle.getVehicleId());

                System.out.println(
                        "  Registration No.   : "
                                + vehicle.getRegistrationNumber());

                System.out.println(
                        "  Vehicle Type       : " + vehicle.getVehicleType());

                System.out.println(
                        "  Brand              : " + vehicle.getBrand());

                System.out.println(
                        "  Model              : " + vehicle.getModel());

                System.out.println(
                        "  Manufacturing Year : "
                                + vehicle.getManufacturingYear());

                System.out.println(
                        "  Fuel Type          : " + vehicle.getFuelType());

                if (vehicle.getCurrentOdometer() == 0) {

                    System.out.println(
                            "  Odometer Reading   : NOT RECORDED");

                } else {

                    System.out.println(
                            "  Odometer Reading   : "
                                    + vehicle.getCurrentOdometer() + " KM");
                }

                count++;
            }

            System.out.println("\n============================================================");
            System.out.println("             END OF VEHICLE RECORDS");
            System.out.println("============================================================");
        }

        System.out.print("\n  Press ENTER to return...");
        scanner.nextLine();
    }
}