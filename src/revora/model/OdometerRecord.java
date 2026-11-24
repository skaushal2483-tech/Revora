package revora.model;

import java.time.LocalDate;

public class OdometerRecord {

    private String recordId;
    private String vehicleId;
    private LocalDate readingDate;
    private double odometerReading;

    public OdometerRecord(String recordId, String vehicleId,
            LocalDate readingDate, double odometerReading) {

        this.recordId = recordId;
        this.vehicleId = vehicleId;
        this.readingDate = readingDate;
        this.odometerReading = odometerReading;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public LocalDate getReadingDate() {
        return readingDate;
    }

    public double getOdometerReading() {
        return odometerReading;
    }

    public void setOdometerReading(double odometerReading) {
        this.odometerReading = odometerReading;
    }
}