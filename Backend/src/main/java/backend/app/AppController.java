package backend.app;

import backend.models.Database;
import backend.models.DatabaseFactory;
import backend.web.responses.StandardResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
 * Endpoint logic for retrieving basic (app) user information.
 */

@RestController
public class AppController {
    private final Database database = DatabaseFactory.instance();

    /**
     * Ping the backend to see if it is responding
     *
     * @return the message, HTTP status code and a success
     */
    @GetMapping("/")
    public StandardResponse ping() {
        String formattedDate = new SimpleDateFormat("dd/MM/yyyy @ HH:mm:ss").format(System.currentTimeMillis());
        System.out.println("[PING] " + formattedDate);
        System.out.println();
        return new StandardResponse(true, "Ok", 200);
    }

    /**
     * Return relevant information about the user's history
     *
     * @param id the user ID.
     * @return HistoryResponse a JSON object representing the data defined above.
     */
    @GetMapping("history")
    public HistoryResponse history(@RequestParam String id) {
        int code = 200;
        ArrayList<String> history = new ArrayList<>();
        String query = "SELECT * FROM SERVICE_LOG WHERE USER_ID={ID}";
        query = query.replace("{ID}", id);
        ResultSet results = database.query(query);
        try {
            while (results.next()) {
                history.add(results.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            code = 500;
        }
        return new HistoryResponse(history, code);
    }

    /**
     * Return all the service names added to the user's account.
     *
     * @param id the user ID.
     * @return ServiceResponse a JSON object representing the data defined above.
     */
    @GetMapping("services")
    public ServiceResponse services(@RequestParam String id) {
        int code = 200;
        ArrayList<String> services = new ArrayList<>();
        String query = "SELECT NAME FROM SERVICE INNER JOIN USER_SERVICE ON SERVICE.SERVICE_ID = USER_SERVICE.SERVICE_ID WHERE USER_SERVICE.USER_ID={ID}";
        query = query.replace("{ID}", id);
        ResultSet results = database.query(query);
        try {
            while (results.next()) {
                services.add(results.getString("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            code = 500;
        }
        return new ServiceResponse(services, code);
    }

    /**
     * Return information about a given service
     *
     * @param id the service id
     * @return ServiceData response a JSON object containing the data about the
     * given service
     */
    @GetMapping("servicedata")
    public serviceDataResponse serviceData(@RequestParam String id) {
        int code = 200;
        String name = "";
        String category = "";
        String description = "";
        String query = "SELECT NAME, CATEGORY, DESCRIPTION FROM SERVICE WHERE SERVICE_ID={ID}";
        query = query.replace("{ID}", id);
        ResultSet results = database.query(query);

        try {
            if (results.next()) {
                name = results.getString("NAME");
                category = results.getString("CATEGORY");
                description = results.getString("DESCRIPTION");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            code = 500;
        }

        return new serviceDataResponse(name, category, description, code);
    }

    /**
     * Return relevant information about the user (name(s), prefix and phone
     * number).
     *
     * @param id the user ID.
     * @return UserResponse a JSON object representing the data defined above.
     */
    @GetMapping("user")
    public UserResponse user(@RequestParam String id) {
        int code = 200;
        String firstName = "";
        String lastName = "";
        String prefix = "";
        String phoneNumber = "";

        String query = "SELECT FIRST_NAME, LAST_NAME, PHONE_NUMBER, PREFIX FROM USER WHERE USER_ID={ID}";
        query = query.replace("{ID}", id);
        ResultSet results = database.query(query);

        try {
            if (results.next()) {
                firstName = results.getString("FIRST_NAME");
                lastName = results.getString("LAST_NAME");
                prefix = results.getString("PREFIX");
                phoneNumber = results.getString("PHONE_NUMBER");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            code = 500;
        }

        return new UserResponse(firstName, lastName, prefix, phoneNumber, code);
    }

    /*
     * { "history": [], "code": 200 }
     */
    private static class HistoryResponse {
        private final ArrayList<String> history;
        private final int code;

        public HistoryResponse(ArrayList<String> history, int code) {
            this.history = history;
            this.code = code;
        }

        public ArrayList<String> getHistory() {
            return history;
        }

        public int getCode() {
            return code;
        }
    }

    /*
     * { "services": [], "code": 200 }
     */
    private static class ServiceResponse {
        private final ArrayList<String> services;
        private final int code;

        public ServiceResponse(ArrayList<String> services, int code) {
            this.services = services;
            this.code = code;
        }

        public ArrayList<String> getServices() {
            return services;
        }

        public int getCode() {
            return code;
        }
    }

    /*
     * { "name": "", "category": "", "description": "" }
     */
    private static class serviceDataResponse {
        private final String name;
        private final String category;
        private final String description;
        private final int code;

        public serviceDataResponse(String name, String category, String description, int code) {
            this.name = name;
            this.category = category;
            this.description = description;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }

        public String getDescription() {
            return description;
        }

        public int getCode() {
            return code;
        }

    }

    /*
     * { "firstName": "", "lastName": "", "prefix": "", "phoneNumber": "", code: ""
     * }
     */
    private static class UserResponse {
        private final String firstName;
        private final String lastName;
        private final String prefix;
        private final String phoneNumber;
        private final int code;

        public UserResponse(String firstName, String lastName, String prefix, String phoneNumber, int code) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.prefix = prefix;
            this.phoneNumber = phoneNumber;
            this.code = code;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public int getCode() {
            return code;
        }
    }
}