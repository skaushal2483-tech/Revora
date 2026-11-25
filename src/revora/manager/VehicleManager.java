package revora.manager;

import java.util.ArrayList;
import revora.model.Vehicle;

public class VehicleManager {

    private ArrayList<Vehicle> vehicles;
    private int vehicleCounter;

    public VehicleManager() {
        vehicles = new ArrayList<>();
        vehicleCounter = 1001;
    }

    public String generateVehicleId() {
        return "VEH" + vehicleCounter++;
    }

    public boolean addVehicle(Vehicle vehicle) {

        if (findVehicleByRegistration(
                vehicle.getRegistrationNumber()) != null) {
            return false;
        }

        vehicles.add(vehicle);
        return true;
    }

    public Vehicle findVehicleByRegistration(String registrationNumber) {

        for (Vehicle vehicle : vehicles) {

            if (vehicle.getRegistrationNumber()
                    .equalsIgnoreCase(registrationNumber)) {
                return vehicle;
            }
        }

        return null;
    }

    public Vehicle findVehicleById(String vehicleId) {

        for (Vehicle vehicle : vehicles) {

            if (vehicle.getVehicleId().equalsIgnoreCase(vehicleId)) {
                return vehicle;
            }
        }

        return null;
    }

    public ArrayList<Vehicle> getVehiclesByOwner(String ownerId) {

        ArrayList<Vehicle> ownerVehicles = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {

            if (vehicle.getOwnerId().equalsIgnoreCase(ownerId)) {
                ownerVehicles.add(vehicle);
            }
        }

        return ownerVehicles;
    }

    public boolean updateVehicle(Vehicle updatedVehicle) {

        Vehicle existingVehicle =
                findVehicleById(updatedVehicle.getVehicleId());

        if (existingVehicle == null) {
            return false;
        }

        Vehicle duplicateVehicle =
                findVehicleByRegistration(
                        updatedVehicle.getRegistrationNumber());

        if (duplicateVehicle != null
                && !duplicateVehicle.getVehicleId()
                        .equalsIgnoreCase(updatedVehicle.getVehicleId())) {
            return false;
        }

        existingVehicle.setRegistrationNumber(
                updatedVehicle.getRegistrationNumber());

        existingVehicle.setBrand(updatedVehicle.getBrand());
        existingVehicle.setModel(updatedVehicle.getModel());

        existingVehicle.setManufacturingYear(
                updatedVehicle.getManufacturingYear());

        existingVehicle.setFuelType(updatedVehicle.getFuelType());

        existingVehicle.setCurrentOdometer(
                updatedVehicle.getCurrentOdometer());

        return true;
    }

    public boolean removeVehicle(String vehicleId, String ownerId) {

        Vehicle vehicle = findVehicleById(vehicleId);

        if (vehicle == null
                || !vehicle.getOwnerId().equalsIgnoreCase(ownerId)) {
            return false;
        }

        vehicles.remove(vehicle);
        return true;
    }

    public void removeVehiclesByOwner(String ownerId) {

        vehicles.removeIf(
                vehicle -> vehicle.getOwnerId().equalsIgnoreCase(ownerId));
    }

    public int getVehicleCount(String ownerId) {

        int count = 0;

        for (Vehicle vehicle : vehicles) {

            if (vehicle.getOwnerId().equalsIgnoreCase(ownerId)) {
                count++;
            }
        }

        return count;
    }

    public int getTotalVehicles() {
        return vehicles.size();
    }
}