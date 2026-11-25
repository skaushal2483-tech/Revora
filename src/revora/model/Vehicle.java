package revora.model;

public class Vehicle {

    private String vehicleId;
    private String ownerId;
    private String registrationNumber;
    private String vehicleType;
    private String brand;
    private String model;
    private int manufacturingYear;
    private String fuelType;
    private int currentOdometer;

    public Vehicle(String vehicleId, String ownerId,
                   String registrationNumber, String vehicleType,
                   String brand, String model, int manufacturingYear,
                   String fuelType, int currentOdometer) {

        this.vehicleId = vehicleId;
        this.ownerId = ownerId;
        this.registrationNumber = registrationNumber;
        this.vehicleType = vehicleType;
        this.brand = brand;
        this.model = model;
        this.manufacturingYear = manufacturingYear;
        this.fuelType = fuelType;
        this.currentOdometer = currentOdometer;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getManufacturingYear() {
        return manufacturingYear;
    }

    public String getFuelType() {
        return fuelType;
    }

    public int getCurrentOdometer() {
        return currentOdometer;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setManufacturingYear(int manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public void setCurrentOdometer(int currentOdometer) {
        this.currentOdometer = currentOdometer;
    }

    public void displayVehicleDetails() {

        System.out.println("============================================================");
        System.out.println("                    VEHICLE DETAILS");
        System.out.println("============================================================");
        System.out.println("  Vehicle ID         : " + vehicleId);
        System.out.println("  Registration No.   : " + registrationNumber);
        System.out.println("  Vehicle Type       : " + vehicleType);
        System.out.println("  Brand              : " + brand);
        System.out.println("  Model              : " + model);
        System.out.println("  Manufacturing Year : " + manufacturingYear);
        System.out.println("  Fuel Type          : " + fuelType);

        if (currentOdometer == 0) {
            System.out.println("  Current Odometer   : NOT RECORDED");
        } else {
            System.out.println("  Current Odometer   : "
                    + currentOdometer + " KM");
        }

        System.out.println("============================================================");
    }
}