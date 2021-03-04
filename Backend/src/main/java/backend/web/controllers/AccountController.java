package backend.web.controllers;

import backend.web.responses.AdminDataResponse;
import backend.web.responses.StandardResponse;
import backend.web.responses.ProfilePictureResponse;
import backend.web.responses.MemberDataResponse;
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
                profilePicture = result.getString("PICTURE_ID");
            }
        } catch (SQLException e) {
            code = 500;
            message = e.getMessage();
            success = false;
        }
        return new AdminDataResponse(success, message, firstName, lastName, email, phoneNumber, password, profilePicture, code);
    }

    @GetMapping("members")
    public MemberDataResponse getMemberData(@RequestParam String username) {
        boolean success = true;
        String message = "Ok";
        int code = 200;
        String userID="";
        String firstName = "";
        String lastName = "";
        String phoneNumber = "";
        String prefix = "";
        String profilePicture = "";
        Map<String, Map<String, String>> members = new HashMap<>();
        String query = "SELECT USER.* FROM ADMIN_CIRCLE INNER JOIN USER ON ADMIN_CIRCLE.USER_ID=USER.USER_ID WHERE ADMIN_CIRCLE.USERNAME='{USERNAME}'";
        ResultSet result = database.query(query.replace("{USERNAME}", username));

        try {
            while (result.next()) {
                Map<String,String> user= new HashMap<>();
                userID= result.getString("USER_ID");
                firstName = result.getString("FIRST_NAME");
                lastName = result.getString("LAST_NAME");
                phoneNumber = result.getString("PHONE_NUMBER");
                prefix = result.getString("PREFIX");
                profilePicture = result.getString("PICTURE_ID");
                user.put("first_name",firstName);
                user.put("last_name",lastName);
                user.put("phone_number",phoneNumber);
                user.put("prefix",prefix);
                user.put("profile_picture",profilePicture);
                members.put(userID,user);
                
            }
        } catch (SQLException e) {
            code = 500;
            message = e.getMessage();
            success = false;
        }
        return new MemberDataResponse(success, message, members, code);
    }

    @GetMapping("add-service-to-user")
    public StandardResponse postAddServiceToUser(@RequestParam String user_id, @RequestParam String service_id) {
        boolean success = true;
        String message = "Ok";
        int code = 200;
        String statement = "INSERT INTO USER_SERVICE VALUES ('{SERVICE_ID}','{USER_ID}')";
        statement=statement.replace("{SERVICE_ID}",service_id);
        statement=statement.replace("{USER_ID}",user_id);
        
        
        switch (database.checkExisting("USER","USER_ID","USER_ID = "+"'"+user_id+"'")){
            case 1:
                success=false;
                message="User_ID does not exist";
                break;
            case 2:
                success=false;
                code=500;
        }
        switch (database.checkExisting("SERVICE","SERVICE_ID USER_ID","SERVICE_ID ="+"'"+service_id+"'")){
            case 1:
                success=false;
                message="Service_ID does not exist";
                break;
            case 2:
                success=false;
                code=500;
        }
        switch (database.checkExisting("USER_SERVICE","SERVICE_ID USER_ID","SERVICE_ID ="+"'"+service_id+"'"+" AND "+"USER_ID = "+"'"+user_id+"'")){
            case 0:
                success=false;
                message="User is already assigned this service";
                break;
            case 2:
                success=false;
                code=500;
        }


        if (success!=false){
            database.executeUpdate(statement);

        }
        

        return new StandardResponse(success, message, code);
    }

    @GetMapping("remove-service-from-user")
    public StandardResponse postRemoveServiceFromUser(@RequestParam String user_id, @RequestParam String service_id) {
        boolean success = true;
        String message = "Ok";
        int code = 200;
        String statement = "DELETE FROM USER_SERVICE WHERE SERVICE_ID='{SERVICE_ID}' AND USER_ID='{USER_ID}'";
        statement=statement.replace("{SERVICE_ID}",service_id);
        statement=statement.replace("{USER_ID}",user_id);
        
        
        switch (database.checkExisting("USER_SERVICE","SERVICE_ID USER_ID","SERVICE_ID ="+"'"+service_id+"'"+" AND "+"USER_ID = "+"'"+user_id+"'")){
            case 1:
                success=false;
                message="User is not assigned this service";
                break;
            case 2:
                success=false;
                code=500;
        }


        if (success!=false){
            database.executeUpdate(statement);

        }
        

        return new StandardResponse(success, message, code);
    }


    
}
