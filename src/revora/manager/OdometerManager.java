package revora.manager;

import java.util.ArrayList;
import revora.model.OdometerRecord;

public class OdometerManager {

    private ArrayList<OdometerRecord> odometerRecords;
    private int recordCounter;

    public OdometerManager() {
        odometerRecords = new ArrayList<>();
        recordCounter = 1001;
    }

    public String generateRecordId() {
        return "ODO" + recordCounter++;
    }

    public boolean addOdometerRecord(OdometerRecord record) {

        if (findRecordById(record.getRecordId()) != null) {
            return false;
        }

        odometerRecords.add(record);
        return true;
    }

    public OdometerRecord findRecordById(String recordId) {

        for (OdometerRecord record : odometerRecords) {

            if (record.getRecordId().equalsIgnoreCase(recordId)) {
                return record;
            }
        }

        return null;
    }

    public ArrayList<OdometerRecord> getRecordsByVehicle(String vehicleId) {

        ArrayList<OdometerRecord> vehicleRecords = new ArrayList<>();

        for (OdometerRecord record : odometerRecords) {

            if (record.getVehicleId().equalsIgnoreCase(vehicleId)) {
                vehicleRecords.add(record);
            }
        }

        return vehicleRecords;
    }

    public double getLatestReading(String vehicleId) {

        double latestReading = -1;

        for (OdometerRecord record : odometerRecords) {

            if (record.getVehicleId().equalsIgnoreCase(vehicleId)
                    && record.getOdometerReading() > latestReading) {

                latestReading = record.getOdometerReading();
            }
        }

        return latestReading;
    }

    public void removeRecordsByVehicle(String vehicleId) {

        odometerRecords.removeIf(
                record -> record.getVehicleId()
                        .equalsIgnoreCase(vehicleId));
    }

    public int getTotalRecords() {
        return odometerRecords.size();
    }
}