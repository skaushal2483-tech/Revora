package revora.model;

import java.time.LocalDate;

public class FuelRecord {

    private String recordId;
    private String vehicleId;
    private LocalDate refillDate;
    private double odometerReading;
    private double fuelQuantity;
    private double pricePerLitre;
    private double totalCost;

    public FuelRecord(String recordId, String vehicleId,
            LocalDate refillDate, double odometerReading,
            double fuelQuantity, double pricePerLitre) {

        this.recordId = recordId;
        this.vehicleId = vehicleId;
        this.refillDate = refillDate;
        this.odometerReading = odometerReading;
        this.fuelQuantity = fuelQuantity;
        this.pricePerLitre = pricePerLitre;
        this.totalCost = fuelQuantity * pricePerLitre;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public LocalDate getRefillDate() {
        return refillDate;
    }

    public double getOdometerReading() {
        return odometerReading;
    }

    public double getFuelQuantity() {
        return fuelQuantity;
    }

    public double getPricePerLitre() {
        return pricePerLitre;
    }

    public double getTotalCost() {
        return totalCost;
    }
}