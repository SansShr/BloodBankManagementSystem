package model;

public class BloodRequest extends BaseEntity {
    private String hospitalID;
    private String bloodType;
    private int quantity;

    // Constructor
    public BloodRequest(String requestID, String hospitalID, String bloodType, int quantity) {
        this.setId(requestID);  // RequestID (Primary Key)
        this.hospitalID = hospitalID;
        this.bloodType = bloodType;
        this.quantity = quantity;
    }

    // Getters and Setters
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
