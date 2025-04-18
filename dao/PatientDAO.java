package dao;

import model.patients;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    private Connection connection;

    // Constructor to initialize the connection
    public PatientDAO(Connection connection) {
        this.connection = connection;
    }

    // Insert a new Patient
    public void insertPatient(patients patient) throws SQLException {
        String sql = "INSERT INTO patients (PatientID, FirstName, LastName, PatientBloodType, PatientAge, PatientGender, MedicalHistory, HospitalID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patient.getPatientId());
            stmt.setString(2, patient.getFirstName());
            stmt.setString(3, patient.getLastName());
            stmt.setString(4, patient.getPatientBloodType());
            stmt.setInt(5, patient.getPatientAge());
            stmt.setString(6, patient.getPatientGender());
            stmt.setString(7, patient.getMedicalHistory());
            stmt.setString(8, patient.getHospitalId());
            stmt.executeUpdate();
        }
    }

    // Update an existing Patient
    public void updatePatient(patients patient) throws SQLException {
        String sql = "UPDATE patients SET FirstName = ?, LastName = ?, PatientBloodType = ?, PatientAge = ?, PatientGender = ?, MedicalHistory = ?, HospitalID = ? WHERE PatientID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getLastName());
            stmt.setString(3, patient.getPatientBloodType());
            stmt.setInt(4, patient.getPatientAge());
            stmt.setString(5, patient.getPatientGender());
            stmt.setString(6, patient.getMedicalHistory());
            stmt.setString(7, patient.getHospitalId());
            stmt.setString(8, patient.getPatientId());
            stmt.executeUpdate();
        }
    }

    // Delete a Patient by PatientID
    public void deletePatient(String patientId) throws SQLException {
        String sql = "DELETE FROM patients WHERE PatientID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patientId);
            stmt.executeUpdate();
        }
    }

    // Get a Patient by PatientID
    public patients getPatientById(String patientId) throws SQLException {
        String sql = "SELECT * FROM patients WHERE PatientID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
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
                }
            }
        }
        return null;
    }

    // Get all Patients
    public List<patients> getAllPatients() throws SQLException {
        List<patients> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                patients.add(new patients(
                    rs.getString("PatientID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("PatientBloodType"),
                    rs.getInt("PatientAge"),
                    rs.getString("PatientGender"),
                    rs.getString("MedicalHistory"),
                    rs.getString("HospitalID")
                ));
            }
        }
        return patients;
    }
}
