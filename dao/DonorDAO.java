package dao;

import model.Donor;
import db.DBConnection;
import exception.DonorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonorDAO {

    // Add a new donor to the database
    public void addDonor(Donor donor) throws DonorException {
        String query = "INSERT INTO Donors (DonorID, FirstName, LastName, DonorPhone, DonorBloodType, Age) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, donor.getDonorID());
            stmt.setString(2, donor.getFirstName());
            stmt.setString(3, donor.getLastName());
            stmt.setString(4, donor.getDonorPhone());
            stmt.setString(5, donor.getDonorBloodType());
            stmt.setInt(6, donor.getAge());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new DonorException("Failed to add donor.");
            }

        } catch (SQLException e) {
            throw new DonorException("Error adding donor: " + e.getMessage(), e);
        }
    }

    // Update an existing donor
    public void updateDonor(Donor donor) throws DonorException {
        String updateQuery = "UPDATE Donors SET FirstName = ?, LastName = ?, DonorPhone = ?, DonorBloodType = ?, Age = ? WHERE DonorID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, donor.getFirstName());
            stmt.setString(2, donor.getLastName());
            stmt.setString(3, donor.getDonorPhone());
            stmt.setString(4, donor.getDonorBloodType());
            stmt.setInt(5, donor.getAge());
            stmt.setString(6, donor.getDonorID());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new DonorException("Failed to update donor.");
            }

            System.out.println("Updated " + rowsAffected + " rows for DonorID: " + donor.getDonorID());

        } catch (SQLException e) {
            throw new DonorException("Error updating donor: " + e.getMessage(), e);
        }
    }

    // Delete a donor by DonorID
    public void deleteDonor(String donorID) throws DonorException {
        String deleteQuery = "DELETE FROM Donors WHERE DonorID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

            stmt.setString(1, donorID);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new DonorException("Failed to delete donor.");
            }

        } catch (SQLException e) {
            throw new DonorException("Error deleting donor: " + e.getMessage(), e);
        }
    }

    // Retrieve a donor by DonorID
    public Donor getDonorById(String donorID) throws DonorException {
        String query = "SELECT * FROM Donors WHERE DonorID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, donorID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Donor(
                        rs.getString("DonorID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("DonorPhone"),
                        rs.getString("DonorBloodType"),
                        rs.getInt("Age")
                );
            } else {
                throw new DonorException("Donor not found.");
            }

        } catch (SQLException e) {
            throw new DonorException("Error fetching donor: " + e.getMessage(), e);
        }
    }

    // Retrieve all donors
    public List<Donor> getAllDonors() throws DonorException {
        List<Donor> donorList = new ArrayList<>();
        String query = "SELECT * FROM Donors";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Donor donor = new Donor(
                        rs.getString("DonorID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("DonorPhone"),
                        rs.getString("DonorBloodType"),
                        rs.getInt("Age")
                );
                donorList.add(donor);
            }

        } catch (SQLException e) {
            throw new DonorException("Error retrieving donors: " + e.getMessage(), e);
        }

        return donorList;
    }
}
