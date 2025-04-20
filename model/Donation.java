package model;

import java.time.LocalDate;

public class Donation {
    private String donationID;
    private String donorID;
    private LocalDate donationDate;

    // Constructor
    public Donation(String donationID, String donorID, LocalDate donationDate) {
        this.donationID = donationID;
        this.donorID = donorID;
        this.donationDate = donationDate;
    }

    // Getters and Setters
    public String getDonationID() {
        return donationID;
    }

    public void setDonationID(String donationID) {
        this.donationID = donationID;
    }

    public String getDonorID() {
        return donorID;
    }

    public void setDonorID(String donorID) {
        this.donorID = donorID;
    }

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "donationID='" + donationID + '\'' +
                ", donorID='" + donorID + '\'' +
                ", donationDate=" + donationDate +
                '}';
    }
}
