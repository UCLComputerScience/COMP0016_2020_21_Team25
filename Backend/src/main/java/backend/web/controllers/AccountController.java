package backend.web.controllers;

import backend.web.responses.AdminDataResponse;
import backend.web.responses.ProfilePictureResponse;
import backend.models.Database;
import backend.models.DatabaseFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller to manage endpoints for the admin account.
 */
@RestController
public class AccountController {
    private final Database database = DatabaseFactory.instance();

    @GetMapping("profile-pictures")
    public ProfilePictureResponse getProfilePictures() {
        boolean success = true;
        String message = "Ok";
        int code = 200;
        Map<String, String> profilePictures = new HashMap<>();
        String query = "SELECT * FROM PROFILE_PICTURE";
        ResultSet result = database.query(query);

        try {
            while (result.next()) {
                String id = result.getString("PICTURE_ID");
                String picture = result.getString("PICTURE");
                profilePictures.put(id, picture);
            }
        } catch (SQLException e) {
            code = 500;
            message = e.getMessage();
            success = false;
        }
        return new ProfilePictureResponse(success, message, profilePictures, code);
    }

    @GetMapping("admin")
    public AdminDataResponse getAdminData(@RequestParam String username) {
        boolean success = true;
        String message = "Ok";
        int code = 200;
        String firstName = "";
        String lastName = "";
        String email = "";
        String phoneNumber = "";
        String password = "";
        String profilePicture = "";

        String query = "SELECT * FROM ADMIN WHERE USERNAME='{USERNAME}'";
        ResultSet result = database.query(query.replace("{USERNAME}", username));

        try {
            if (result.next()) {
                firstName = result.getString("FIRST_NAME");
                lastName = result.getString("LAST_NAME");
                email = result.getString("EMAIL");
                phoneNumber = result.getString("PHONE_NUMBER");
                password = result.getString("PASSWORD");
                profilePicture = result.getString("PROFILE_PICTURE");
            }
        } catch (SQLException e) {
            code = 500;
            message = e.getMessage();
            success = false;
        }
        return new AdminDataResponse(success, message, firstName, lastName, email, phoneNumber, password, profilePicture, code);
    }
}
