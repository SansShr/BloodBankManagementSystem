package dao;

import model.BloodRequest;
import db.DBConnection;
import exception.BloodRequestException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BloodRequestDAO {

    // Add a new blood request to the database
    public void addBloodRequest(BloodRequest bloodRequest) throws BloodRequestException {
        String query = "INSERT INTO BloodRequests (RequestID, HospitalID, BloodType, Quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, bloodRequest.getRequestID());
            stmt.setString(2, bloodRequest.getHospitalID());
            stmt.setString(3, bloodRequest.getBloodType());
            stmt.setInt(4, bloodRequest.getQuantity());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BloodRequestException("Failed to add blood request.");
            }

        } catch (SQLException e) {
            throw new BloodRequestException("Error adding blood request: " + e.getMessage(), e);
        }
    }

    // Update an existing blood request
    public void updateBloodRequest(BloodRequest bloodRequest) throws BloodRequestException {
        String updateQuery = "UPDATE BloodRequests SET HospitalID = ?, BloodType = ?, Quantity = ? WHERE RequestID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, bloodRequest.getHospitalID());
            stmt.setString(2, bloodRequest.getBloodType());
            stmt.setInt(3, bloodRequest.getQuantity());
            stmt.setString(4, bloodRequest.getRequestID());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BloodRequestException("Failed to update blood request.");
            }

            // Optional: Log or print to verify the update was successful
            System.out.println("Updated " + rowsAffected + " rows for RequestID: " + bloodRequest.getRequestID());

        } catch (SQLException e) {
            throw new BloodRequestException("Error updating blood request: " + e.getMessage(), e);
        }
    }


    // Delete a blood request by RequestID
    public void deleteBloodRequest(String requestID) throws BloodRequestException {
        String deleteQuery = "DELETE FROM BloodRequests WHERE RequestID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

            stmt.setString(1, requestID);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new BloodRequestException("Failed to delete blood request.");
            }

        } catch (SQLException e) {
            throw new BloodRequestException("Error deleting blood request: " + e.getMessage(), e);
        }
    }

    // Retrieve a blood request by RequestID
    public BloodRequest getBloodRequestById(String requestID) throws BloodRequestException {
        String query = "SELECT * FROM BloodRequests WHERE RequestID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, requestID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new BloodRequest(
                        rs.getString("RequestID"),
                        rs.getString("HospitalID"),
                        rs.getString("BloodType"),
                        rs.getInt("Quantity")
                );
            } else {
                throw new BloodRequestException("Blood request not found.");
            }

        } catch (SQLException e) {
            throw new BloodRequestException("Error fetching blood request: " + e.getMessage(), e);
        }
    }

    // Retrieve all blood requests
    public List<BloodRequest> getAllBloodRequests() throws BloodRequestException {
        List<BloodRequest> bloodRequestList = new ArrayList<>();
        String query = "SELECT * FROM BloodRequests";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                BloodRequest bloodRequest = new BloodRequest(
                        rs.getString("RequestID"),
                        rs.getString("HospitalID"),
                        rs.getString("BloodType"),
                        rs.getInt("Quantity")
                );
                bloodRequestList.add(bloodRequest);
            }

        } catch (SQLException e) {
            throw new BloodRequestException("Error retrieving blood requests: " + e.getMessage(), e);
        }

        return bloodRequestList;
    }
}
