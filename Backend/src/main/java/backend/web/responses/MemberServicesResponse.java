package backend.web.responses;

import java.util.ArrayList;
import java.util.Map;

public class MemberServicesResponse extends StandardResponse {
    private final ArrayList<Map<String, String>> services;

    public MemberServicesResponse(boolean success, String message, ArrayList<Map<String, String>> services, int code) {
        super(success, message, code);
        this.services = services;
    }

    public ArrayList<Map<String, String>> getServices() {
        return this.services;
    }

}
