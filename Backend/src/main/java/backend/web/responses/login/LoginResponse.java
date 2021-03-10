package backend.web.responses.login;

import backend.web.responses.StandardResponse;

import java.util.Map;

public class LoginResponse extends StandardResponse {
    private final String username;
    private final Map<String, String> errors;

    public LoginResponse(boolean success, String message, String username, Map<String, String> errors, int code) {
        super(success, message, code);
        this.username = username;
        this.errors = errors;
    }

    public String getUsername() {
        return this.username;
    }

    public Map<String, String> getErrors() {
        return this.errors;
    }
}
