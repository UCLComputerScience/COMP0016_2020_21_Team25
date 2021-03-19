package backend.web.controllers;

import backend.models.Database;
import backend.models.DatabaseFactory;
import backend.web.responses.service.CategoryResponse;
import backend.web.responses.service.MemberServicesResponse;
import backend.web.responses.service.ServicesInCategoryResponse;
import backend.web.util.MapComparator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Controller to manage endpoints for service information.
 */
@RestController
public class ServiceController {
    private final Database database = DatabaseFactory.instance();

    @GetMapping("service-categories")
    public CategoryResponse getCategories() {
        boolean success = true;
        String message = "Ok";
        int code = 200;

        ArrayList<String> categories = new ArrayList<>();

        String sqlStatement = "SELECT CATEGORY FROM SERVICE";
        ResultSet result = database.query(sqlStatement);

        try {
            while (result.next()) {
                String category = result.getString("CATEGORY");
                if (!categories.contains(category)) {
                    categories.add(category);
                }
            }
            categories.sort(String::compareToIgnoreCase);
        } catch (SQLException e) {
            code = 500;
            message = e.getMessage();
            success = false;
        }

        return new CategoryResponse(success, message, categories, code);
    }

    @GetMapping("services-in-category")
    public ServicesInCategoryResponse getServicesInCategory(@RequestParam String category) {
        boolean success = true;
        String message = "Ok";
        int code = 200;

        ArrayList<Map<String, String>> services = new ArrayList<>();

        String sqlStatement = "SELECT SERVICE_ID, NAME, ICON, DESCRIPTION FROM SERVICE WHERE CATEGORY='{CATEGORY}'";
        ResultSet result = database.query(sqlStatement.replace("{CATEGORY}", category));

        try {
            while (result.next()) {
                String serviceID = result.getString("SERVICE_ID");
                String name = result.getString("NAME");
                String icon = result.getString("ICON");
                String description = result.getString("DESCRIPTION");
                Map<String, String> service = new HashMap<>();
                service.put("service_id", serviceID);
                service.put("name", name);
                service.put("category", category);
                service.put("icon", icon);
                service.put("description", description);
                services.add(service);
            }
            if (services.size() > 1) {
                services.sort(new MapComparator("name"));
            }
        } catch (SQLException e) {
            code = 500;
            message = e.getMessage();
            success = false;
        }

        return new ServicesInCategoryResponse(success, message, services, code);
    }

    @GetMapping("member-services")
    public MemberServicesResponse getMemberServices(@RequestParam String user_id) {
        boolean success = true;
        ArrayList<Map<String, String>> services = new ArrayList<>();
        String message = "Ok";
        int code = 200;

        String sqlStatement = "SELECT SERVICE.* FROM USER_SERVICE INNER JOIN SERVICE ON USER_SERVICE.SERVICE_ID=SERVICE.SERVICE_ID WHERE USER_SERVICE.USER_ID='{USER_ID}'";

        ResultSet result = database.query(sqlStatement.replace("{USER_ID}", user_id));

        try {
            while (result.next()) {
                String serviceID = result.getString("SERVICE_ID");
                String name = result.getString("NAME");
                String category = result.getString("CATEGORY");
                String icon = result.getString("ICON");
                String description = result.getString("DESCRIPTION");
                Map<String, String> service = new HashMap<>();
                service.put("service_id", serviceID);
                service.put("name", name);
                service.put("category", category);
                service.put("icon", icon);
                service.put("description", description);
                services.add(service);
            }
            if (services.size() == 0) {
                message = "Could not find any services assigned to that given USER-ID";

            }
        } catch (SQLException | RuntimeException e) {
            code = 500;
            message = e.getMessage();
            success = false;
        }

        return new MemberServicesResponse(success, message, services, code);
    }
}
