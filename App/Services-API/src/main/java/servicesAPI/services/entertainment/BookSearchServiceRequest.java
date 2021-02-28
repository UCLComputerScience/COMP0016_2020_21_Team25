package servicesAPI.services.entertainment;

import java.util.ArrayList;
import java.util.HashMap;

import servicesAPI.services.AbstractServiceRequest;

public class BookSearchServiceRequest extends AbstractServiceRequest {

    public BookSearchServiceRequest(HashMap<String, String> payload) {
        super("https://gutendex.com/books?search={QUERY}&languages={LANGUAGES}&topic={TOPIC}", "Book", "Entertainment",
                "", payload);
    }

    @Override
    protected String parseOutput(HashMap<String, Object> response) {
        ArrayList<HashMap<String, Object>> books = (ArrayList<HashMap<String, Object>>) response.get("results");
        if (books.size() == 0)
            return "I'm sorry, I could not find any books matching the search criteria.";
        HashMap<String, Object> book = books.get(0);
        String output = "I've found a {CATEGORY} book called {NAME}, written by {AUTHOR}. Would you like me to read it to you?";
        ArrayList<String> categories = (ArrayList<String>) book.get("subjects");
        // Return the shortest category name (some returned by the API are extremely
        // verbose)
        String category = (categories.size() == 0) ? ""
                : categories.stream().min((e2, e1) -> e1.length() > e2.length() ? -1 : 1).get();
        output = output.replace("{CATEGORY}", category.toLowerCase());
        output = output.replace("{NAME}", (String) book.get("title"));
        ArrayList<HashMap<String, Object>> authors = (ArrayList<HashMap<String, Object>>) book.get("authors");
        StringBuilder authorStr = new StringBuilder();
        int length = authors.size();
        for (int i = 0; i < length; i++) {
            HashMap<String, Object> authorData = authors.get(i);
            String name = (String) authorData.get("name");
            authorStr.append(name);
            if (i == length - 1) {
            } else if (i == length - 2) {
                authorStr.append(" and ");
            } else {
                authorStr.append(", ");
            }
        }
        output = output.replace("{AUTHOR}", authorStr.toString());
        metadata.put("id", book.get("id"));

        HashMap<String, String> mimeTypes = (HashMap<String, String>) book.get("formats");
        addMimeTypeLink("image/jpeg", mimeTypes);
        addMimeTypeLink("text/html; charset=utf-8", mimeTypes);
        addMimeTypeLink("text/plain; charset=utf-8", mimeTypes);
        addMimeTypeLink("application/x-mobipocket-ebook", mimeTypes);
        addMimeTypeLink("application/epub+zip", mimeTypes);
        return output;
    }

    private void addMimeTypeLink(String key, HashMap<String, String> mimeTypes) {
        if (mimeTypes.containsKey(key)) {
            String imageUrl = mimeTypes.get(key);
            metadata.put(key, imageUrl);
        }
    }

    @Override
    protected String handleErrors(HashMap<String, Object> response) {
        return "I'm sorry, I could not find any books matching the search criteria.";
    }

    @Override
    protected String getErrorCode(HashMap<String, Object> response) {
        return null;
    }

    @Override
    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("QUERY", "");
        payload.put("LANGUAGES", "en");
        payload.put("TOPIC", "");
        return payload;
    }

}
