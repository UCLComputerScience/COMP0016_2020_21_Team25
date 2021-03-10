package backend.web.controllers;

import backend.models.Database;
import backend.models.DatabaseFactory;
import backend.web.responses.login.LoginResponse;
import backend.web.responses.login.RegisterAdminResponse;
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
    public LoginResponse getLoginData(@RequestParam String username_or_email, @RequestParam String password) {
        boolean success = true;
        boolean isEmail = false;
        String message = "Ok";
        int code = 200;

        Map<String, String> errors = new HashMap<>();
        String databaseUsername = "";
        String databasePassword = "";

        // Check if param is actually email address
        String query = "SELECT USERNAME, PASSWORD FROM ADMIN WHERE EMAIL='{EMAIL}'";
        query = query.replace("{EMAIL}", username_or_email);
        ResultSet result = database.query(query);

        try {
            if (result.next()) {
                databaseUsername = result.getString("USERNAME");
                databasePassword = result.getString("PASSWORD");
                isEmail = true;
            }
        } catch (SQLException e) {
            code = 500;
            message = e.getMessage();
            success = false;
        }

        if (!isEmail) {
            String sqlStatement = "SELECT USERNAME, PASSWORD FROM ADMIN WHERE USERNAME='{USER}'";
            sqlStatement = sqlStatement.replace("{USER}", username_or_email);
            ResultSet results = database.query(sqlStatement);

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
        }

        if (!isEmail && !username_or_email.equals(databaseUsername)) {
            success = false;
            message = "Incorrect Credentials";
            errors.put("field", "username");
            errors.put("message", "The username " + username_or_email + " does not exist, did you mean to sign up?");
        } else if (username_or_email.equals(databaseUsername) && !password.equals(databasePassword)) {
            success = false;
            message = "Incorrect Credentials";
            errors.put("field", "password");
            errors.put("message", "Your password was incorrect, please try again.");
        }


        return new LoginResponse(success, message, databaseUsername, errors, code);
    }

    @PostMapping("register")
    public RegisterAdminResponse postAdminAccount(@RequestParam String username, @RequestParam String first_name, @RequestParam String last_name, @RequestParam String email, @RequestParam String phone_number, @RequestParam String password) {
        boolean success = true;
        String message = "Ok";
        int code = 200;

        Map<String, String> errors = new HashMap<>();
        String defaultPictureID = "37";

        String sqlStatement = "INSERT INTO ADMIN VALUES ('{USERNAME}', '{FIRST_NAME}', '{LAST_NAME}', '{PHONE_NUMBER}', '{EMAIL}', '{PICTURE_ID}', '{PASSWORD}')";
        sqlStatement = sqlStatement.replace("{USERNAME}", username);
        sqlStatement = sqlStatement.replace("{FIRST_NAME}", first_name);
        sqlStatement = sqlStatement.replace("{LAST_NAME}", last_name);
        sqlStatement = sqlStatement.replace("{PHONE_NUMBER}", phone_number);
        sqlStatement = sqlStatement.replace("{EMAIL}", email);
        sqlStatement = sqlStatement.replace("{PICTURE_ID}", defaultPictureID);
        sqlStatement = sqlStatement.replace("{PASSWORD}", password);


        switch (database.checkExisting("ADMIN", "USERNAME", "USERNAME =" + "'" + username + "'")) {
            case 0:
                success = false;
                message = "Error";
                errors.put("field", "username");
                errors.put("message", "The username " + username + " is already in use. Did you mean to log in?");
                break;
            case 2:
                success = false;
                message = "Server error";
                code = 500;
        }

        if (!message.equals("Error")) {
            switch (database.checkExisting("ADMIN", "EMAIL", "EMAIL =" + "'" + email + "'")) {
                case 0:
                    success = false;
                    message = "Error";
                    errors.put("field", "email");
                    errors.put("message", "The email address " + email + " is already associated with another account. Did you mean to log in?");
                    break;
                case 2:
                    success = false;
                    message = "Server error";
                    code = 500;
            }
        }

        if (!message.equals("Error")) {
            switch (database.checkExisting("ADMIN", "PHONE_NUMBER", "PHONE_NUMBER =" + "'" + phone_number + "'")) {
                case 0:
                    success = false;
                    message = "Error";
                    errors.put("field", "phone_number");
                    errors.put("message", "The phone number " + phone_number + " is already associated with another account.");
                    break;
                case 2:
                    success = false;
                    message = "Server error";
                    code = 500;
            }
        }

        if (!message.equals("Error")) {
            database.executeUpdate(sqlStatement);
        }


        return new RegisterAdminResponse(success, message, errors, code);

    }


}
