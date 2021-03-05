package backend.web.responses.login;

import backend.web.responses.StandardResponse;

import java.util.Map;

public class LoginResponse extends StandardResponse {
    private final Map<String, String> errors;

    public LoginResponse(boolean success, String message, Map<String, String> errors, int code) {
        super(success, message, code);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return this.errors;
    }
}
