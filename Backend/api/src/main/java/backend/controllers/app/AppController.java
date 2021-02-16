package backend.controllers.app;

import java.util.ArrayList;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.*;

import backend.models.Database;
import backend.models.DatabaseFactory;

/*
 * Endpoint logic for retrieving basic (app) user information.
*/

@RestController
public class AppController {
    private final Database database = DatabaseFactory.instance();

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
        String query = "SELECT * FROM SERVICE_LOG WHERE USER_ID={ID} ";
        query = query.replace("{ID}", id);
		ResultSet results = database.query(query);
        try {
            while (results.next()) {
                history.add(results.getString("SERVICE_ID"));
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
        int phoneNumber = 0;

        String query = "SELECT FIRST_NAME, LAST_NAME, PHONE_NUMBER, PREFIX FROM USER WHERE USER_ID={ID}";
        query = query.replace("{ID}", id);
        ResultSet results = database.query(query);

        try {
            firstName = results.getString("FIRST_NAME");
            lastName = results.getString("LAST_NAME");
            prefix = results.getString("PREFIX");
            phoneNumber = results.getInt("PHONE_NUMBER");
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
     * { "firstName": "", "lastName": "", "prefix": "", "phoneNumber": "", code: "" }
    */
    private static class UserResponse {
        private final String firstName;
        private final String lastName;
        private final String prefix;
        private final int phoneNumber;
        private final int code;

        public UserResponse(String firstName, String lastName, String prefix, int phoneNumber, int code) {
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

        public int getPhoneNumber() {
            return phoneNumber;
        }

        public int getCode() {
            return code;
        }
    }
}