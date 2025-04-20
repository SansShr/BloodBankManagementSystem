package model;

import java.util.Date;

public class BloodStock extends BaseEntity {
    private String bloodBankId;
    private String bloodType;
    private int quantity;
    private Date expirationDate;

    public BloodStock(String id, String bloodBankId, String bloodType, int quantity, Date expirationDate) {
        this.setId(id);  // From BaseEntity
        this.bloodBankId = bloodBankId;
        this.bloodType = bloodType;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }

    // Getters and setters for all fields
    public String getBloodBankId() {
        return bloodBankId;
    }

    public void setBloodBankId(String bloodBankId) {
        this.bloodBankId = bloodBankId;
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

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
