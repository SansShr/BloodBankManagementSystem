package model;

public class BloodRequest extends BaseEntity {
    private String requestID;     // Unique ID for the blood request
    private String hospitalID;    // Associated hospital ID
    private String bloodType;     // Blood group required
    private int quantity;         // Units of blood required

    // Constructor
    public BloodRequest(String requestID, String hospitalID, String bloodType, int quantity) {
        this.requestID = requestID;
        this.hospitalID = hospitalID;
        this.bloodType = bloodType;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
