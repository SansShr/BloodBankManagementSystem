package dao;

import model.Donation;
import db.DBConnection;
import exception.DonationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonationsDAO {

    // Method to add a new donation
    public void addDonation(Donation donation) throws DonationException {
        String query = "INSERT INTO Donations (DonationID, DonorID, DonationDate) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, donation.getDonationID());
            stmt.setString(2, donation.getDonorID());
            stmt.setDate(3, Date.valueOf(donation.getDonationDate()));

            int rows = stmt.executeUpdate();
            if (rows == 0) throw new DonationException("Failed to add donation.");
        } catch (SQLException e) {
            throw new DonationException("Error adding donation: " + e.getMessage(), e);
        }
    }

    // Method to update a donation
    public void updateDonation(Donation donation) throws DonationException {
        String query = "UPDATE Donations SET DonorID = ?, DonationDate = ? WHERE DonationID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, donation.getDonorID());
            stmt.setDate(2, Date.valueOf(donation.getDonationDate()));
            stmt.setString(3, donation.getDonationID());

            int rows = stmt.executeUpdate();
            if (rows == 0) throw new DonationException("Failed to update donation.");
        } catch (SQLException e) {
            throw new DonationException("Error updating donation: " + e.getMessage(), e);
        }
    }

    // Method to delete a donation by its ID
    public void deleteDonation(String id) throws DonationException {
        String query = "DELETE FROM Donations WHERE DonationID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id);
            int rows = stmt.executeUpdate();
            if (rows == 0) throw new DonationException("Failed to delete donation.");
        } catch (SQLException e) {
            throw new DonationException("Error deleting donation: " + e.getMessage(), e);
        }
    }

    // Method to get a donation by its ID
    public Donation getDonationById(String id) throws DonationException {
        String query = "SELECT * FROM Donations WHERE DonationID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Donation(
                        rs.getString("DonationID"),
                        rs.getString("DonorID"),
                        rs.getDate("DonationDate").toLocalDate()
                );
            } else {
                throw new DonationException("Donation not found.");
            }
        } catch (SQLException e) {
            throw new DonationException("Error retrieving donation: " + e.getMessage(), e);
        }
    }

    // Method to get all donations
    public List<Donation> getAllDonations() throws DonationException {
        List<Donation> list = new ArrayList<>();
        String query = "SELECT * FROM Donations";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                list.add(new Donation(
                        rs.getString("DonationID"),
                        rs.getString("DonorID"),
                        rs.getDate("DonationDate").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            throw new DonationException("Error retrieving donations: " + e.getMessage(), e);
        }
        return list;
    }
}
