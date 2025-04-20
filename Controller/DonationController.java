package Controller;

import model.Donation;
import dao.DonationsDAO;
import exception.DonationException;

import java.util.List;

public class DonationController {

    private DonationsDAO donationDAO;

    public DonationController() {
        donationDAO = new DonationsDAO();
    }

    public void addDonation(Donation donation) throws DonationException {
        donationDAO.addDonation(donation);
    }

    public void updateDonation(Donation donation) throws DonationException {
        donationDAO.updateDonation(donation);
    }

    public void deleteDonation(String donationID) throws DonationException {
        donationDAO.deleteDonation(donationID);
    }

    public Donation getDonationById(String donationID) throws DonationException {
        return donationDAO.getDonationById(donationID);
    }

    public List<Donation> getAllDonations() throws DonationException {
        return donationDAO.getAllDonations();
    }
}