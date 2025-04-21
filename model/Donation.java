package model;

import java.sql.Date;
import java.time.LocalDate;

public class Donation {
    private String donationID;
    private String donorID;
    private String donationDate;

    // Constructor
    public Donation(String donationID, String donorID, String sqlDonationDate) {
        this.donationID = donationID;
        this.donorID = donorID;
        this.donationDate = sqlDonationDate;
    }

    // Getters and Setters (if needed)
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

    public String getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(String donationDate) {
        this.donationDate = donationDate;
    }
}

