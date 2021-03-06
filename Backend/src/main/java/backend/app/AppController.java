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
import java.util.HashMap;
import java.util.Map;

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
    @GetMapping("app-history")
    public AppHistoryResponse appHistory(@RequestParam String id) {
        int code = 200;

        ArrayList<Map<String, String>> history = new ArrayList<>();

        String sqlStatement = "SELECT SERVICE.NAME,  SERVICE_LOG.* FROM SERVICE_LOG INNER JOIN SERVICE ON SERVICE_LOG.SERVICE_ID=SERVICE.SERVICE_ID AND SERVICE_LOG.USER_ID='{USER_ID}' WHERE SERVICE_LOG.LOG_DATE BETWEEN CURRENT_DATE-7 AND CURRENT_DATE ORDER BY SERVICE_LOG.LOG_DATE, SERVICE_LOG.LOG_TIME";
        ResultSet result = database.query(sqlStatement.replace("{USER_ID}", id));
        try {
            while (result.next()) {
                String service_id = result.getString("SERVICE_ID");
                String service_name = result.getString("NAME");
                String date= result.getString("LOG_DATE");
                String time= result.getString("LOG_TIME");
                Map<String, String> serviceLog = new HashMap<>();
                serviceLog.put("service_id", service_id);
                serviceLog.put("service_name", service_name);
                serviceLog.put("timestamp", date+" "+time);
                history.add(serviceLog);
            }

        } catch (SQLException | RuntimeException e) {
            code = 500;
        }


        return new AppHistoryResponse(history, code);
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

    @GetMapping("add-history")
    public AddHistoryResponse addHistory(@RequestParam String service_name, @RequestParam String user_id){
        int code=200;
        String message="OK";
        String sqlStatement="INSERT INTO SERVICE_LOG VALUES((SELECT SERVICE_ID FROM SERVICE WHERE NAME='{SERVICE_NAME}'),'{USER_ID}',CURRENT_DATE,CURRENT_TIME)";
        sqlStatement = sqlStatement.replace("{SERVICE_NAME}", service_name);
        sqlStatement = sqlStatement.replace("{USER_ID}", user_id);

        switch (database.checkExisting("USER","USER_ID","USER_ID ="+"'"+user_id+"'")){
            case 1:
                message="Invalid USER_ID";
                break;
            case 2:
                message="Server error";
                code=500;
        }

        switch (database.checkExisting("SERVICE","NAME","NAME ="+"'"+service_name+"'")){
            case 1:
                message="Invalid SERVICE_ID";
                break;
            case 2:
                message="Server error";
                code=500;
        }

        if (message.equals("OK")){
            database.executeUpdate(sqlStatement);
        }

        return new AddHistoryResponse(message,code);

    }

    @GetMapping("login-user")
    public LoginUserResponse loginUser(@RequestParam String first_word, @RequestParam String second_word, @RequestParam String last_word){
        int code=200;
        String message="OK";
        String userId="";
        String sqlStatement="SELECT USER_ID FROM REGISTRATION_CODES WHERE FIRST_WORD='{FIRST_WORD}' AND SECOND_WORD='{SECOND_WORD}' AND LAST_WORD='{LAST_WORD}' ";
        sqlStatement = sqlStatement.replace("{FIRST_WORD}", first_word);
        sqlStatement = sqlStatement.replace("{SECOND_WORD}", second_word);
        sqlStatement = sqlStatement.replace("{LAST_WORD}", last_word);

        ResultSet results = database.query(sqlStatement);

        try {
            if (results.next()) {
                userId = results.getString("USER_ID");
            }
            else{
                message="Invalid Registration Codes";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            code = 500;
        }

        return new LoginUserResponse(message,userId,code);

    }

    /*
     * { "history": [], "code": 200 }
     */
    private static class AppHistoryResponse {
        private final ArrayList<Map<String, String>> history;
        private final int code;

        public AppHistoryResponse(ArrayList<Map<String, String>> history, int code) {
            this.history = history;
            this.code = code;
        }

        public ArrayList<Map<String, String>> getHistory() {
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

    private static class AddHistoryResponse {
        private final String message;
        private final int code;

        public AddHistoryResponse(String message, int code) {
            this.message = message;
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public int getCode() {
            return code;
        }
    }

    private static class LoginUserResponse {
        private final String message;
        private final String userId;
        private final int code;

        public LoginUserResponse(String message, String userId, int code) {
            this.message = message;
            this.userId=userId;
            this.code = code;
        }

        public String getMessage() {
            return message;
        }
        
        public String getUserId(){
            return userId;
        }

        public int getCode() {
            return code;
        }
    }
}