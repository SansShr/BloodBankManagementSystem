package model;

public class EmergencyService {
    private String serviceId;
    private String requestId;
    private String serviceDetails;

    // Constructor
    public EmergencyService(String serviceId, String requestId, String serviceDetails) {
        this.serviceId = serviceId;
        this.requestId = requestId;
        this.serviceDetails = serviceDetails;
    }

    // Getters and Setters
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(String serviceDetails) {
        this.serviceDetails = serviceDetails;
    }
}
