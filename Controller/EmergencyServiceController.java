package Controller;

import model.EmergencyService;
import dao.EmergencyServicesDAO;
import exception.EmergencyServiceException;

import java.util.List;

public class EmergencyServiceController {

    private EmergencyServicesDAO emergencyServiceDAO;

    // Constructor
    public EmergencyServiceController() {
        emergencyServiceDAO = new EmergencyServicesDAO();  // Initializing the DAO object
    }

    // Add a new emergency service
    public void addEmergencyService(EmergencyService emergencyService) throws EmergencyServiceException {
        emergencyServiceDAO.addEmergencyService(emergencyService); // Calling non-static method on instance
    }

    // Update an existing emergency service
    public void updateEmergencyService(EmergencyService emergencyService) throws EmergencyServiceException {
        emergencyServiceDAO.updateEmergencyService(emergencyService);
    }

    // Delete an emergency service by ServiceID
    public void deleteEmergencyService(String serviceID, String requestID) throws EmergencyServiceException {
        emergencyServiceDAO.deleteEmergencyService(serviceID, requestID);
    }

    // Retrieve an emergency service by ServiceID
//    public EmergencyService getEmergencyServiceById(String serviceID) throws EmergencyServiceException {
//        return emergencyServiceDAO.getEmergencyServiceById(serviceID);
//    }

    // Retrieve all emergency services
    public List<EmergencyService> getAllEmergencyServices() throws EmergencyServiceException {
        return emergencyServiceDAO.getAllEmergencyServices();
    }
}
