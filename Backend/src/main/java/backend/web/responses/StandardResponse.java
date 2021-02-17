package backend.web.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StandardResponse {
    private final boolean success;
    private final String message;
    private final int code;

    public StandardResponse(boolean success, String message, int code) {
        this.success = success;
        this.message = message;
        this.code = code;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public boolean getSuccess() {
        return this.success;
    }


    public String getMessage() {
        return this.message;
    }


    public int getCode() {
        return this.code;
    }
}
