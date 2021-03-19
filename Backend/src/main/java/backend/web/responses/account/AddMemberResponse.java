package backend.web.responses.account;

import backend.web.responses.StandardResponse;

import java.util.ArrayList;


public class AddMemberResponse extends StandardResponse {
    private final String user_id;
    private final ArrayList<String> registration_code;

    public AddMemberResponse(boolean success, String message, String user_id, ArrayList<String> registration_code, int code) {
        super(success, message, code);
        this.user_id = user_id;
        this.registration_code = registration_code;
    }

    public String getUserId() {
        return user_id;
    }

    public ArrayList<String> getRegistrationCode() {
        return registration_code;
    }
}