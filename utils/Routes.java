package utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Routes {

    // Admin routes
    public static final Map<String, String> adminRoutes = new LinkedHashMap<>() {{
        put("Blood Banks", "/views/bloodbank.fxml");
        put("Blood Stock", "/views/bloodstock.fxml");
        put("Donors", "/views/donors.fxml");
        put("Donations", "/views/donations.fxml");
        put("Patients", "/views/patients.fxml");
        put("Emergency Services", "/views/emergencyservices.fxml");
        put("Eligibility Criteria", "/views/eligibilitycriteria.fxml");
        put("Blood Requests", "/views/bloodrequests.fxml");
        put("Hospitals", "/views/hospitals.fxml");
    }};

    // User routes
    public static final Map<String, String> userRoutes = new LinkedHashMap<>() {{
        put("Blood Request", "/views/bloodrequests.fxml");
        put("Check Eligibility", "/views/eligibilitycriteria.fxml");
        put("Become a Donor", "/views/donors.fxml");
        put("Emergency Request", "/views/emergencyservices.fxml");
    }};
}
