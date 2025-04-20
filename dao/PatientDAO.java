package dao;

import model.patients;
import db.DBConnection;
import exception.PatientException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public void addPatient(patients patient) throws PatientException {
        String query = "INSERT INTO Patients (PatientID, FirstName, LastName, PatientBloodType, PatientAge, PatientGender, MedicalHistory, HospitalID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patient.getPatientID());
            stmt.setString(2, patient.getFirstName());
            stmt.setString(3, patient.getLastName());
            stmt.setString(4, patient.getPatientBloodType());
            stmt.setInt(5, patient.getPatientAge());
            stmt.setString(6, patient.getPatientGender());
            stmt.setString(7, patient.getMedicalHistory());
            stmt.setString(8, patient.getHospitalID());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new PatientException("Failed to add patient.");
            }

        } catch (SQLException e) {
            throw new PatientException("Error adding patient: " + e.getMessage(), e);
        }
    }

    public void updatePatient(patients patient) throws PatientException {
        String query = "UPDATE Patients SET FirstName = ?, LastName = ?, PatientBloodType = ?, PatientAge = ?, PatientGender = ?, MedicalHistory = ? WHERE PatientID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getLastName());
            stmt.setString(3, patient.getPatientBloodType());
            stmt.setInt(4, patient.getPatientAge());
            stmt.setString(5, patient.getPatientGender());
            stmt.setString(6, patient.getMedicalHistory());
            stmt.setString(7, patient.getPatientID());
            stmt.setString(8, patient.getHospitalID());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new PatientException("Failed to update patient.");
            }

        } catch (SQLException e) {
            throw new PatientException("Error updating patient: " + e.getMessage(), e);
        }
    }

    public void deletePatient(String patientID) throws PatientException {
        String query = "DELETE FROM Patients WHERE PatientID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patientID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new PatientException("Failed to delete patient.");
            }

        } catch (SQLException e) {
            throw new PatientException("Error deleting patient: " + e.getMessage(), e);
        }
    }
    public patients getPatientById(String patientID) throws PatientException {
        String query = "SELECT * FROM Patients WHERE PatientID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, patientID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new patients(
                        rs.getString("PatientID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("PatientBloodType"),
                        rs.getInt("PatientAge"),
                        rs.getString("PatientGender"),
                        rs.getString("MedicalHistory"),
                        rs.getString("HospitalID")
                        
                );
            } else {
                throw new PatientException("Patient not found.");
            }

        } catch (SQLException e) {
            throw new PatientException("Error fetching patient: " + e.getMessage(), e);
        }
    
    }
    public List<patients> getAllPatients() throws PatientException {
        List<patients> patientsList = new ArrayList<>();
        String query = "SELECT * FROM Patients";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                patients patient = new patients(
                        rs.getString("PatientID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("PatientBloodType"),
                        rs.getInt("PatientAge"),
                        rs.getString("PatientGender"),
                        rs.getString("MedicalHistory"),
                        rs.getString("HospitalID")
                );
                patientsList.add(patient);
            }

        } catch (SQLException e) {
            throw new PatientException("Error retrieving patients: " + e.getMessage(), e);
        }

        return patientsList;
    }

}
