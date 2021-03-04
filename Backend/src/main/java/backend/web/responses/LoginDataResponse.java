package backend.web.responses;

import java.util.Map;

public class LoginDataResponse extends StandardResponse {
    private final Map<String, String> errors;

    public LoginDataResponse(boolean success, String message, Map<String, String> errors, int code) {
        super(success, message, code);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return this.errors;
    }
}
