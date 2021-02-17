package backend.web.controllers;

import backend.web.responses.CategoryResponse;
import backend.web.responses.ServicesInCategoryResponse;
import backend.web.util.MapComparator;
import backend.models.Database;
import backend.models.DatabaseFactory;
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
        ArrayList<String> categories = new ArrayList<>();
        String message = "Ok";
        int code = 200;

        String query = "SELECT CATEGORY FROM SERVICE";
        ResultSet result = database.query(query);
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
        ArrayList<Map<String, String>> services = new ArrayList<>();
        String message = "Ok";
        int code = 200;

        String query = "SELECT NAME, ICON, DESCRIPTION FROM SERVICE WHERE CATEGORY='{CATEGORY}'";
        ResultSet result = database.query(query.replace("{CATEGORY}", category.toLowerCase()));
        try {
            while (result.next()) {
                String name = result.getString("NAME");
                String icon = result.getString("ICON");
                String description = result.getString("DESCRIPTION");
                Map<String, String> service = new HashMap<>();
                service.put("name", name);
                service.put("category", category);
                service.put("icon", icon);
                service.put("description", description);
                services.add(service);
            }
            if (services.size() == 0) {
                throw new RuntimeException("Could not find any services in the given category.");
            }
            services.sort(new MapComparator("value"));
        } catch (SQLException | RuntimeException e) {
            code = 500;
            message = e.getMessage();
            success = false;
        }
        return new ServicesInCategoryResponse(success, message, services, code);
    }
}
