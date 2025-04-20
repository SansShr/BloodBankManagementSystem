package dao;

import model.Hospital;
import db.DBConnection;
import exception.HospitalException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HospitalDAO {

    // Add a new hospital to the database
    public void addHospital(Hospital hospital) throws HospitalException {
        String query = "INSERT INTO Hospitals (HospitalID, HospitalName, HospitalLocation, HospitalContact, BloodBankID) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, hospital.getHospitalID());
            stmt.setString(2, hospital.getHospitalName());
            stmt.setString(3, hospital.getHospitalLocation());
            stmt.setString(4, hospital.getHospitalContact());
            stmt.setString(5, hospital.getBloodBankID());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new HospitalException("Failed to add hospital.");
            }

        } catch (SQLException e) {
            throw new HospitalException("Error adding hospital: " + e.getMessage(), e);
        }
    }

    // Update an existing hospital
    public void updateHospital(Hospital hospital) throws HospitalException {
        String updateQuery = "UPDATE Hospitals SET HospitalName = ?, HospitalLocation = ?, HospitalContact = ?, BloodBankID = ? WHERE HospitalID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {

            stmt.setString(1, hospital.getHospitalName());
            stmt.setString(2, hospital.getHospitalLocation());
            stmt.setString(3, hospital.getHospitalContact());
            stmt.setString(4, hospital.getBloodBankID());
            stmt.setString(5, hospital.getHospitalID());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new HospitalException("Failed to update hospital.");
            }

            // Optional: Log or print to verify the update was successful
            System.out.println("Updated " + rowsAffected + " rows for HospitalID: " + hospital.getHospitalID());

        } catch (SQLException e) {
            throw new HospitalException("Error updating hospital: " + e.getMessage(), e);
        }
    }

    // Delete a hospital by HospitalID
    public void deleteHospital(String hospitalID) throws HospitalException {
        String deleteQuery = "DELETE FROM Hospitals WHERE HospitalID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

            stmt.setString(1, hospitalID);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new HospitalException("Failed to delete hospital.");
            }

        } catch (SQLException e) {
            throw new HospitalException("Error deleting hospital: " + e.getMessage(), e);
        }
    }

    // Retrieve a hospital by HospitalID
    public Hospital getHospitalById(String hospitalID) throws HospitalException {
        String query = "SELECT * FROM Hospitals WHERE HospitalID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, hospitalID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Hospital(
                        rs.getString("HospitalID"),
                        rs.getString("HospitalName"),
                        rs.getString("HospitalLocation"),
                        rs.getString("HospitalContact"),
                        rs.getString("BloodBankID")
                );
            } else {
                throw new HospitalException("Hospital not found.");
            }

        } catch (SQLException e) {
            throw new HospitalException("Error fetching hospital: " + e.getMessage(), e);
        }
    }

    // Retrieve all hospitals
    public List<Hospital> getAllHospitals() throws HospitalException {
        List<Hospital> hospitalList = new ArrayList<>();
        String query = "SELECT * FROM Hospitals";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Hospital hospital = new Hospital(
                        rs.getString("HospitalID"),
                        rs.getString("HospitalName"),
                        rs.getString("HospitalLocation"),
                        rs.getString("HospitalContact"),
                        rs.getString("BloodBankID")
                );
                hospitalList.add(hospital);
            }

        } catch (SQLException e) {
            throw new HospitalException("Error retrieving hospitals: " + e.getMessage(), e);
        }

        return hospitalList;
    }
}
