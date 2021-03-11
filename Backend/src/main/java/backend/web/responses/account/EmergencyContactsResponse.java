package backend.web.responses.account;

import backend.web.responses.StandardResponse;

import java.util.Map;

public class EmergencyContactsResponse extends StandardResponse {
    private final Map<String, String> emergencyContacts;

    public EmergencyContactsResponse(boolean success, String message, Map<String, String> emergencyContacts, int code) {
        super(success, message, code);
        this.emergencyContacts = emergencyContacts;
    }

    public Map<String, String> getEmergencyContacts() {
        return this.emergencyContacts;
    }

}