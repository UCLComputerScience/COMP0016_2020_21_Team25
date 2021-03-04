package backend.web.controllers;

import backend.web.responses.AdminDataResponse;
import backend.web.responses.StandardResponse;
import backend.web.responses.ProfilePictureResponse;
import backend.web.responses.MemberDataResponse;
import backend.web.responses.MemberHistoryResponse;
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
    //Could potentially utilise error array
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
    //Could benefit from errors array
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

    @GetMapping("update-admin")
    //Could benefit from errors array
    public StandardResponse postUpdateAdminData(@RequestParam String username, @RequestParam String first_name, @RequestParam String last_name, @RequestParam String email, @RequestParam String phone_number, @RequestParam String picture_id ,@RequestParam String password) {
        boolean success = true;
        String message = "Ok";
        int code = 200;

        String statement = "UPDATE ADMIN SET FIRST_NAME='{FIRST_NAME}',LAST_NAME='{LAST_NAME}',PHONE_NUMBER='{PHONE_NUMBER}',EMAIL='{EMAIL}',PICTURE_ID='{PICTURE_ID}',PASSWORD='{PASSWORD}' WHERE USERNAME='{USERNAME}'";
        statement = statement.replace("{USERNAME}", username);
        statement = statement.replace("{FIRST_NAME}", first_name);
        statement = statement.replace("{LAST_NAME}", last_name);
        statement = statement.replace("{PHONE_NUMBER}", phone_number);
        statement = statement.replace("{EMAIL}", email);
        statement = statement.replace("{PICTURE_ID}", picture_id);
        statement = statement.replace("{PASSWORD}", password);

         
        switch (database.checkExisting("ADMIN","USERNAME","USERNAME ="+"'"+username+"'")){
            case 1:
                success=false;
                message="fail";
                //errors.put("USER", "Username does not exist");
                break;
            case 2:
                success=false;
                code=500;
        }
        switch (database.checkExisting("ADMIN","EMAIL","EMAIL ="+"'"+email+"'"+ " AND NOT USERNAME="+"'"+username+"'")){
            case 0:
                success=false;
                message="fail";
                //errors.put("EMAIL", "Email is already being utilised");
                break;
            case 2:
                success=false;
                code=500;
        }
        switch (database.checkExisting("ADMIN","PHONE_NUMBER","PHONE_NUMBER ="+"'"+phone_number+"'"+ " AND NOT USERNAME="+"'"+username+"'")){
            case 0:
                success=false;
                message="fail";
                //errors.put("PHONE_NUMBER", "Phone number is already being utilised");
                break;
            case 2:
                success=false;
                code=500;
        }
        
        if (!message.equals("fail")){
            database.executeUpdate(statement);
            //errors.put("STATUS", "OK");
        }
            
        return new StandardResponse(success, message, code);
    }

    @GetMapping("update-member")
    //Could benefit from errors array
    public StandardResponse postUpdateMemberData(@RequestParam String user_id, @RequestParam String first_name, @RequestParam String last_name, @RequestParam String phone_number, @RequestParam String prefix, @RequestParam String picture_id ) {
        boolean success = true;
        String message = "Ok";
        int code = 200;

        String statement = "UPDATE USER SET FIRST_NAME='{FIRST_NAME}',LAST_NAME='{LAST_NAME}',PHONE_NUMBER='{PHONE_NUMBER}',PREFIX='{PREFIX}',PICTURE_ID='{PICTURE_ID}' WHERE USER_ID='{USER_ID}'";
        statement = statement.replace("{USER_ID}", user_id);
        statement = statement.replace("{FIRST_NAME}", first_name);
        statement = statement.replace("{LAST_NAME}", last_name);
        statement = statement.replace("{PHONE_NUMBER}", phone_number);
        statement = statement.replace("{PREFIX}", prefix);
        statement = statement.replace("{PICTURE_ID}", picture_id);
        

         
        switch (database.checkExisting("USER","USER_ID","USER_ID ="+"'"+user_id+"'")){
            case 1:
                success=false;
                message="fail";
                //errors.put("USER", "Username does not exist");
                break;
            case 2:
                success=false;
                code=500;
        }
        switch (database.checkExisting("USER","PHONE_NUMBER","PHONE_NUMBER ="+"'"+phone_number+"'"+ " AND NOT USER_ID="+"'"+user_id+"'")){
            case 0:
                success=false;
                message="fail";
                //errors.put("PHONE_NUMBER", "Phone number is already being utilised");
                break;
            case 2:
                success=false;
                code=500;
        }
        
        if (!message.equals("fail")){
            database.executeUpdate(statement);
            //errors.put("STATUS", "OK");
        }
            
        return new StandardResponse(success, message, code);
    }

    @GetMapping("member-history")
    public MemberHistoryResponse getMemberHistoryData(@RequestParam String user_id){
        boolean success = true;
        String message = "Ok";
        int code = 200;
        ArrayList<Map<String, String>> history = new ArrayList<>();
        
        String query = "SELECT SERVICE.NAME,  SERVICE_LOG.* FROM SERVICE_LOG INNER JOIN SERVICE ON SERVICE_LOG.SERVICE_ID=SERVICE.SERVICE_ID WHERE USER_ID='{USER_ID}'";
        ResultSet result = database.query(query.replace("{USER_ID}", user_id));
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
            if (history.size() == 0) {
                throw new RuntimeException("Could not find any history for this user");
            }
            history.sort(new MapComparator("value"));
        } catch (SQLException | RuntimeException e) {
            code = 500;
            message = e.getMessage();
            success = false;
        }

            
        return new MemberHistoryResponse(success, message, history, code);
    }

    
}
