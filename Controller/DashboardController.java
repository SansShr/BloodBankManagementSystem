package Controller;

import model.User;
import java.util.Arrays;
import java.util.List;

public class DashboardController {

    public static List<String> getTablesForRole(User user) {
        if (user.getRole().equals("staff")) {
            return Arrays.asList("bloodbank", "bloodstock", "donors", "donations", "patients",
                                 "emergencyservices", "eligibilitycriteria", "bloodrequests", "hospitals");
        } else if (user.getRole().equals("patient")) {
            return Arrays.asList("bloodrequests", "eligibilitycriteria", "donors", "emergencyservices", "patients");
        } else {
            return Arrays.asList(); // or throw error
        }
    }
}
