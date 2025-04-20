package Controller;

import model.patients;
import dao.PatientDAO;
import exception.PatientException;

public class PatientController {

    private PatientDAO patientDAO;

    // Constructor
    public PatientController() {
        patientDAO = new PatientDAO();  // Initializing the DAO object
    }

    // Correct the call to addPatient by using patientDAO instance
    public void addPatient(patients patient) throws PatientException {
        patientDAO.addPatient(patient); // Calling non-static method on instance
    }

    public void updatePatient(patients patient) throws PatientException {
        patientDAO.updatePatient(patient);
    }

    public void deletePatient(String patientID) throws PatientException {
        patientDAO.deletePatient(patientID);
    }

    public patients getPatientById(String patientID) throws PatientException {
        return patientDAO.getPatientById(patientID);
    }
}
