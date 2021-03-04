package backend.web.controllers;

import backend.models.Database;
import backend.models.DatabaseFactory;
import backend.web.responses.LoginDataResponse;
import backend.web.responses.RegisterAdminResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller to manage endpoints for login/registration.
 */
@RestController
public class LoginController {

    private final Database database = DatabaseFactory.instance();

    @GetMapping("login")
    public LoginDataResponse getLoginData(@RequestParam String username, @RequestParam String password) {
        boolean success = true;
        String message = "Ok";
        int code = 200;

        Map<String, String> errors = new HashMap<>();
        String databaseUsername = "";
        String databasePassword = "";
        String query = "SELECT USERNAME, PASSWORD FROM ADMIN WHERE USERNAME='{USER}'";
        query = query.replace("{USER}", username);
        ResultSet results = database.query(query);

        try {
            if (results.next()) {
                databaseUsername = results.getString("USERNAME");
                databasePassword = results.getString("PASSWORD");
            }

        } catch (SQLException e) {
            code = 500;
            message = e.getMessage();
            success = false;
        }

        if (!username.equals(databaseUsername)) {
            success = false;
            message = "Incorrect Credentials";
            errors.put("USERNAME", "Username does not exist");
        } else if (username.equals(databaseUsername) && !password.equals(databasePassword)) {
            success = false;
            message = "Incorrect Credentials";
            errors.put("PASSWORD", "Incorrect Password");
        }


        return new LoginDataResponse(success, message, errors, code);
    }

    @PostMapping("register")
    public RegisterAdminResponse postAdminAccount(@RequestParam String username, @RequestParam String first_name, @RequestParam String last_name, @RequestParam String email, @RequestParam String phone_number, @RequestParam String password) {
        boolean success = true;
        String message = "Ok";
        int code = 200;

        Map<String, String> errors = new HashMap<>();
        String defaultPictureID = "37";
        String statement = "INSERT INTO ADMIN VALUES ('{USERNAME}', '{FIRST_NAME}', '{LAST_NAME}', '{PHONE_NUMBER}', '{EMAIL}', '{PICTURE_ID}', '{PASSWORD}')";
        statement = statement.replace("{USERNAME}", username);
        statement = statement.replace("{FIRST_NAME}", first_name);
        statement = statement.replace("{LAST_NAME}", last_name);
        statement = statement.replace("{PHONE_NUMBER}", phone_number);
        statement = statement.replace("{EMAIL}", email);
        statement = statement.replace("{PICTURE_ID}", defaultPictureID);
        statement = statement.replace("{PASSWORD}", password);


        switch (database.checkExisting("ADMIN", "USERNAME", "USERNAME =" + "'" + username + "'")) {
            case 0:
                success = false;
                message = "fail";
                errors.put("USER", "Username is already taken");
                break;
            case 2:
                success = false;
                code = 500;
        }
        switch (database.checkExisting("ADMIN", "EMAIL", "EMAIL =" + "'" + email + "'")) {
            case 0:
                success = false;
                message = "fail";
                errors.put("EMAIL", "Email is already being utilised");
                break;
            case 2:
                success = false;
                code = 500;
        }
        switch (database.checkExisting("ADMIN", "PHONE_NUMBER", "PHONE_NUMBER =" + "'" + phone_number + "'")) {
            case 0:
                success = false;
                message = "fail";
                errors.put("PHONE_NUMBER", "Phone number is already being utilised");
                break;
            case 2:
                success = false;
                code = 500;
        }

        if (!message.equals("fail")) {
            database.executeUpdate(statement);
            errors.put("STATUS", "OK");
        }


        return new RegisterAdminResponse(success, message, errors, code);

    }


}
