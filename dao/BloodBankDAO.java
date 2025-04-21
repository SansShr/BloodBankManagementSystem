package dao;

import model.BloodBank;
import db.DBConnection;
import exception.BloodBankException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BloodBankDAO {

    // Add a new blood bank to the database
    public void addBloodBank(BloodBank bloodBank) throws BloodBankException {
        String query = "INSERT INTO BloodBanks (BloodBankID, BloodBankName, BloodBankLocation, BloodBankContact, OperatingHours) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, bloodBank.getBloodBankID()); // BloodBankID passed explicitly
            stmt.setString(2, bloodBank.getBloodBankName());
            stmt.setString(3, bloodBank.getBloodBankLocation());
            stmt.setString(4, bloodBank.getBloodBankContact());
            stmt.setString(5, bloodBank.getOperatingHours());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BloodBankException("Failed to add blood bank.");
            }

        } catch (SQLException e) {
            throw new BloodBankException("Error adding blood bank: " + e.getMessage(), e);
        }
    }

    // Update an existing blood bank
    public void updateBloodBank(BloodBank bloodBank) throws BloodBankException {
        String updateQuery = "UPDATE BloodBanks SET BloodBankName = ?, BloodBankLocation = ?, BloodBankContact = ?, OperatingHours = ? WHERE BloodBankID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, bloodBank.getBloodBankName());
            stmt.setString(2, bloodBank.getBloodBankLocation());
            stmt.setString(3, bloodBank.getBloodBankContact());
            stmt.setString(4, bloodBank.getOperatingHours());
            stmt.setString(5, bloodBank.getBloodBankID()); // BloodBankID to identify the bank for update

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BloodBankException("Failed to update blood bank.");
            }

        } catch (SQLException e) {
            throw new BloodBankException("Error updating blood bank: " + e.getMessage(), e);
        }
    }

    // Delete a blood bank by BloodBankID
    public void deleteBloodBank(String bloodBankID) throws BloodBankException {
        String deleteQuery = "DELETE FROM BloodBanks WHERE BloodBankID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

            stmt.setString(1, bloodBankID);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BloodBankException("Failed to delete blood bank.");
            }

        } catch (SQLException e) {
            throw new BloodBankException("Error deleting blood bank: " + e.getMessage(), e);
        }
    }

    // Retrieve a blood bank by BloodBankID
    public BloodBank getBloodBankById(String bloodBankID) throws BloodBankException {
        String query = "SELECT * FROM BloodBanks WHERE BloodBankID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, bloodBankID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new BloodBank(
                        rs.getString("BloodBankID"),
                        rs.getString("BloodBankName"),
                        rs.getString("BloodBankLocation"),
                        rs.getString("BloodBankContact"),
                        rs.getString("OperatingHours")
                );
            } else {
                throw new BloodBankException("Blood bank not found.");
            }

        } catch (SQLException e) {
            throw new BloodBankException("Error fetching blood bank: " + e.getMessage(), e);
        }
    }

    // Retrieve all blood banks
    public List<BloodBank> getAllBloodBanks() throws BloodBankException {
        List<BloodBank> bloodBankList = new ArrayList<>();
        String query = "SELECT * FROM BloodBanks";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BloodBank bloodBank = new BloodBank(
                        rs.getString("BloodBankID"),
                        rs.getString("BloodBankName"),
                        rs.getString("BloodBankLocation"),
                        rs.getString("BloodBankContact"),
                        rs.getString("OperatingHours")
                );
                bloodBankList.add(bloodBank);
            }

        } catch (SQLException e) {
            throw new BloodBankException("Error retrieving blood banks: " + e.getMessage(), e);
        }

        return bloodBankList;
    }
}
