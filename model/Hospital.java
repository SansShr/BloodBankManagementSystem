package model;

public class Hospital extends BaseEntity {
    private String hospitalID;         // Hospital's unique ID
    private String hospitalName;
    private String hospitalLocation;
    private String hospitalContact;
    private String bloodBankID;

    // Constructor
    public Hospital(String hospitalID, String hospitalName, String hospitalLocation, String hospitalContact, String bloodBankID) {
        this.hospitalID = hospitalID;
        this.hospitalName = hospitalName;
        this.hospitalLocation = hospitalLocation;
        this.hospitalContact = hospitalContact;
        this.bloodBankID = bloodBankID;
    }

    // Getters and Setters
    public String getHospitalID() {
        return hospitalID;
    }

    public void setHospitalID(String hospitalID) {
        this.hospitalID = hospitalID;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalLocation() {
        return hospitalLocation;
    }

    public void setHospitalLocation(String hospitalLocation) {
        this.hospitalLocation = hospitalLocation;
    }

    public String getHospitalContact() {
        return hospitalContact;
    }

    public void setHospitalContact(String hospitalContact) {
        this.hospitalContact = hospitalContact;
    }

    public String getBloodBankID() {
        return bloodBankID;
    }

    public void setBloodBankID(String bloodBankID) {
        this.bloodBankID = bloodBankID;
    }
}
