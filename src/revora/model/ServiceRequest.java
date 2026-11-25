package revora.model;

import java.time.LocalDate;
import revora.enums.ServiceCategory;
import revora.enums.ServiceStatus;

public class ServiceRequest {

    private String requestId;
    private String ownerId;
    private String vehicleId;
    private ServiceCategory serviceCategory;
    private String description;
    private LocalDate preferredDate;
    private ServiceStatus status;

    public ServiceRequest(String requestId, String ownerId,
            String vehicleId, ServiceCategory serviceCategory,
            String description, LocalDate preferredDate,
            ServiceStatus status) {

        this.requestId = requestId;
        this.ownerId = ownerId;
        this.vehicleId = vehicleId;
        this.serviceCategory = serviceCategory;
        this.description = description;
        this.preferredDate = preferredDate;
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getPreferredDate() {
        return preferredDate;
    }

    public ServiceStatus getStatus() {
        return status;
    }

    public void setStatus(ServiceStatus status) {
        this.status = status;
    }
}