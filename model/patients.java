package model;

public class patients {
    private String patientId;
    private String firstName;
    private String lastName;
    private String patientBloodType;
    private int patientAge;
    private String patientGender;
    private String medicalHistory;
    private String hospitalId;

    // Constructor
    public patients(String patientId, String firstName, String lastName, String patientBloodType, 
                   int patientAge, String patientGender, String medicalHistory, String hospitalId) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patientBloodType = patientBloodType;
        this.patientAge = patientAge;
        this.patientGender = patientGender;
        this.medicalHistory = medicalHistory;
        this.hospitalId = hospitalId;
    }

    // Getters and Setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatientBloodType() {
        return patientBloodType;
    }

    public void setPatientBloodType(String patientBloodType) {
        this.patientBloodType = patientBloodType;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
