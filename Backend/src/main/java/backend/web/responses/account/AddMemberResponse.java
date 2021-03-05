package backend.web.responses.account;

import backend.web.responses.StandardResponse;


public class AddMemberResponse extends StandardResponse {
    private final String user_id;
    private final String registration_code;

    public AddMemberResponse(boolean success, String message, String user_id, String registration_code, int code) {
        super(success, message, code);
        this.user_id = user_id;
        this.registration_code= registration_code;
    }

    public String getUserId() {
        return user_id;
    }
    public String getRegistrationCode() {
        return registration_code;
    }
}