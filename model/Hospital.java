package model;

public class Hospital extends BaseEntity {
    private String hospitalName;
    private String hospitalLocation;
    private String hospitalContact;
    private String bloodBankID;

    // Constructor
    public Hospital(String id, String hospitalName, String hospitalLocation, String hospitalContact, String bloodBankID) {
        this.setId(id);
        this.hospitalName = hospitalName;
        this.hospitalLocation = hospitalLocation;
        this.hospitalContact = hospitalContact;
        this.bloodBankID = bloodBankID;
    }

    // Getters and Setters
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
