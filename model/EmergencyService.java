package model;

public class EmergencyService extends BaseEntity {
    private String serviceID;      // Unique ID for the emergency service
    private String requestID;      // Associated blood request ID
    private String serviceDetails; // Details about the emergency service

    // Constructor
    public EmergencyService(String serviceID, String requestID, String serviceDetails) {
        this.serviceID = serviceID;
        this.requestID = requestID;
        this.serviceDetails = serviceDetails;
    }

    // Getters and Setters
    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(String serviceDetails) {
        this.serviceDetails = serviceDetails;
    }
}
