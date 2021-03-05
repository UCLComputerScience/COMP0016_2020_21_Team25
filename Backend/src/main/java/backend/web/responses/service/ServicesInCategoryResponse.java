package backend.web.responses.service;

import backend.web.responses.StandardResponse;

import java.util.ArrayList;
import java.util.Map;

public class ServicesInCategoryResponse extends StandardResponse {
    private final ArrayList<Map<String, String>> services;

    public ServicesInCategoryResponse(boolean success, String message, ArrayList<Map<String, String>> services, int code) {
        super(success, message, code);
        this.services = services;
    }

    public ArrayList<Map<String, String>> getServices() {
        return this.services;
    }

}
