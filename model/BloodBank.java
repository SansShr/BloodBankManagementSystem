package model;

public class BloodBank {
    private String bloodBankID;
    private String bloodBankName;
    private String bloodBankLocation;
    private String bloodBankContact;
    private String operatingHours;

    // Constructor
    public BloodBank(String bloodBankID, String bloodBankName, String bloodBankLocation, String bloodBankContact, String operatingHours) {
        this.bloodBankID = bloodBankID;
        this.bloodBankName = bloodBankName;
        this.bloodBankLocation = bloodBankLocation;
        this.bloodBankContact = bloodBankContact;
        this.operatingHours = operatingHours;
    }

    // Getters and Setters
    public String getBloodBankID() {
        return bloodBankID;
    }

    public void setBloodBankID(String bloodBankID) {
        this.bloodBankID = bloodBankID;
    }

    public String getBloodBankName() {
        return bloodBankName;
    }

    public void setBloodBankName(String bloodBankName) {
        this.bloodBankName = bloodBankName;
    }

    public String getBloodBankLocation() {
        return bloodBankLocation;
    }

    public void setBloodBankLocation(String bloodBankLocation) {
        this.bloodBankLocation = bloodBankLocation;
    }

    public String getBloodBankContact() {
        return bloodBankContact;
    }

    public void setBloodBankContact(String bloodBankContact) {
        this.bloodBankContact = bloodBankContact;
    }

    public String getOperatingHours() {
        return operatingHours;
    }

    public void setOperatingHours(String operatingHours) {
        this.operatingHours = operatingHours;
    }
}
