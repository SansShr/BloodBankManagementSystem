package model;

public class Donor extends BaseEntity {
    private String donorID;           // Donor's unique ID
    private String firstName;
    private String lastName;
    private String donorPhone;
    private String donorBloodType;
    private int age;

    // Constructor
    public Donor(String donorID, String firstName, String lastName, String donorPhone, String donorBloodType, int age) {
        this.donorID = donorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.donorPhone = donorPhone;
        this.donorBloodType = donorBloodType;
        this.age = age;
    }

    // Getters and Setters
    public String getDonorID() {
        return donorID;
    }

    public void setDonorID(String donorID) {
        this.donorID = donorID;
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

    public String getDonorPhone() {
        return donorPhone;
    }

    public void setDonorPhone(String donorPhone) {
        this.donorPhone = donorPhone;
    }

    public String getDonorBloodType() {
        return donorBloodType;
    }

    public void setDonorBloodType(String donorBloodType) {
        this.donorBloodType = donorBloodType;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
