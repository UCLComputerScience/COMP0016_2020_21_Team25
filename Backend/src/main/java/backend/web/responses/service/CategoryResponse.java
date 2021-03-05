package backend.web.responses.service;

import backend.web.responses.StandardResponse;

import java.util.ArrayList;

public class CategoryResponse extends StandardResponse {
    private final ArrayList<String> categories;

    public CategoryResponse(boolean success, String message, ArrayList<String> categories, int code) {
        super(success, message, code);
        this.categories = categories;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }
}
