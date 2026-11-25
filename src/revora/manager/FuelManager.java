package revora.manager;

import java.util.ArrayList;
import revora.model.FuelRecord;

public class FuelManager {

    private ArrayList<FuelRecord> fuelRecords;
    private int recordCounter;

    public FuelManager() {
        fuelRecords = new ArrayList<>();
        recordCounter = 1001;
    }

    public String generateRecordId() {
        return "FUEL" + recordCounter++;
    }

    public boolean addFuelRecord(FuelRecord record) {

        if (findRecordById(record.getRecordId()) != null) {
            return false;
        }

        fuelRecords.add(record);
        return true;
    }

    public FuelRecord findRecordById(String recordId) {

        for (FuelRecord record : fuelRecords) {

            if (record.getRecordId().equalsIgnoreCase(recordId)) {
                return record;
            }
        }

        return null;
    }

    public ArrayList<FuelRecord> getRecordsByVehicle(String vehicleId) {

        ArrayList<FuelRecord> vehicleRecords = new ArrayList<>();

        for (FuelRecord record : fuelRecords) {

            if (record.getVehicleId().equalsIgnoreCase(vehicleId)) {
                vehicleRecords.add(record);
            }
        }

        return vehicleRecords;
    }

    public double getTotalFuelConsumed(String vehicleId) {

        double totalFuel = 0;

        for (FuelRecord record : fuelRecords) {

            if (record.getVehicleId().equalsIgnoreCase(vehicleId)) {
                totalFuel += record.getFuelQuantity();
            }
        }

        return totalFuel;
    }

    public double getTotalFuelExpense(String vehicleId) {

        double totalExpense = 0;

        for (FuelRecord record : fuelRecords) {

            if (record.getVehicleId().equalsIgnoreCase(vehicleId)) {
                totalExpense += record.getTotalCost();
            }
        }

        return totalExpense;
    }

    public void removeRecordsByVehicle(String vehicleId) {

        fuelRecords.removeIf(
                record -> record.getVehicleId()
                        .equalsIgnoreCase(vehicleId));
    }

    public int getTotalRecords() {
        return fuelRecords.size();
    }
}