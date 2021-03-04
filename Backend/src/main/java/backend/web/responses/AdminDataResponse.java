package backend.web.responses;

import java.util.Map;

public class AdminDataResponse extends StandardResponse {
    private final Map<String, String> data;

    public AdminDataResponse(boolean success, String message, Map<String, String> data, int code) {
        super(success, message, code);
        this.data = data;
    }

    public Map<String, String> getData() {
        return data;
    }
}
