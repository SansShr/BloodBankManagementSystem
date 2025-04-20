package Controller;

import model.Hospital;
import dao.HospitalDAO;
import exception.HospitalException;

import java.util.List;

public class HospitalController {

    private HospitalDAO hospitalDAO;

    // Constructor
    public HospitalController() {
        hospitalDAO = new HospitalDAO();  // Initializing the DAO object
    }

    // Method to add a hospital
    public void addHospital(Hospital hospital) throws HospitalException {
        hospitalDAO.addHospital(hospital);  // Calling non-static method on instance
    }

    // Method to update a hospital
    public void updateHospital(Hospital hospital) throws HospitalException {
        hospitalDAO.updateHospital(hospital);
    }

    // Method to delete a hospital by ID
    public void deleteHospital(String hospitalID) throws HospitalException {
        hospitalDAO.deleteHospital(hospitalID);
    }

    // Method to get a hospital by ID
    public Hospital getHospitalById(String hospitalID) throws HospitalException {
        return hospitalDAO.getHospitalById(hospitalID);
    }

    // Method to get all hospitals
    public List<Hospital> getAllHospitals() throws HospitalException {
        return hospitalDAO.getAllHospitals();
    }
}
