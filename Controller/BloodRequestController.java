package Controller;

import model.BloodRequest;

import java.util.List;

import dao.BloodRequestDAO;
import exception.BloodRequestException;

public class BloodRequestController {

    private BloodRequestDAO bloodRequestDAO;

    // Constructor
    public BloodRequestController() {
        bloodRequestDAO = new BloodRequestDAO();  // Initializing the DAO object
    }

    // Correct the call to addBloodRequest by using bloodRequestDAO instance
    public void addBloodRequest(BloodRequest bloodRequest) throws BloodRequestException {
        bloodRequestDAO.addBloodRequest(bloodRequest); // Calling non-static method on instance
    }

    public void updateBloodRequest(BloodRequest bloodRequest) throws BloodRequestException {
        bloodRequestDAO.updateBloodRequest(bloodRequest);
    }

    public void deleteBloodRequest(String requestID) throws BloodRequestException {
        bloodRequestDAO.deleteBloodRequest(requestID);
    }

    public BloodRequest getBloodRequestById(String requestID) throws BloodRequestException {
        return bloodRequestDAO.getBloodRequestById(requestID);
    }

    public List<BloodRequest> getAllBloodRequests() throws BloodRequestException {
        return bloodRequestDAO.getAllBloodRequests();
    }
}
