package revora.manager;

import java.util.ArrayList;
import revora.model.ServiceRequest;
import revora.enums.ServiceStatus;

public class ServiceManager {

    private ArrayList<ServiceRequest> serviceRequests;
    private int requestCounter;

    public ServiceManager() {
        serviceRequests = new ArrayList<>();
        requestCounter = 1001;
    }

    public String generateRequestId() {
        return "SRV" + requestCounter++;
    }

    public boolean addServiceRequest(ServiceRequest request) {

        if (findRequestById(request.getRequestId()) != null) {
            return false;
        }

        serviceRequests.add(request);
        return true;
    }

    public ServiceRequest findRequestById(String requestId) {

        for (ServiceRequest request : serviceRequests) {

            if (request.getRequestId().equalsIgnoreCase(requestId)) {
                return request;
            }
        }

        return null;
    }

    public ArrayList<ServiceRequest> getRequestsByOwner(String ownerId) {

        ArrayList<ServiceRequest> ownerRequests = new ArrayList<>();

        for (ServiceRequest request : serviceRequests) {

            if (request.getOwnerId().equalsIgnoreCase(ownerId)) {
                ownerRequests.add(request);
            }
        }

        return ownerRequests;
    }

    public boolean updateRequestStatus(
            String requestId, ServiceStatus status) {

        ServiceRequest request = findRequestById(requestId);

        if (request == null) {
            return false;
        }

        request.setStatus(status);
        return true;
    }

    public boolean cancelRequest(String requestId, String ownerId) {

        ServiceRequest request = findRequestById(requestId);

        if (request == null
                || !request.getOwnerId().equalsIgnoreCase(ownerId)
                || request.getStatus() != ServiceStatus.PENDING) {
            return false;
        }

        request.setStatus(ServiceStatus.CANCELLED);
        return true;
    }

    public void removeRequestsByVehicle(String vehicleId) {

        serviceRequests.removeIf(
                request -> request.getVehicleId()
                        .equalsIgnoreCase(vehicleId));
    }

    public void removeRequestsByOwner(String ownerId) {

        serviceRequests.removeIf(
                request -> request.getOwnerId()
                        .equalsIgnoreCase(ownerId));
    }

    public int getTotalRequests() {
        return serviceRequests.size();
    }
}