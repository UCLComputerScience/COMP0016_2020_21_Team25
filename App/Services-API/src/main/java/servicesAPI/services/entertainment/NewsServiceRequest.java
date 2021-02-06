package servicesAPI.services.entertainment;

import servicesAPI.services.AbstractServiceRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class NewsServiceRequest extends AbstractServiceRequest {
    public NewsServiceRequest(HashMap<String, String> payload) {
        super("https://newsapi.org/v2/everything?q={QUERY}&page=1&pageSize=1&language={LANGUAGE}&apiKey={API-Key}",
                "News", "Entertainment", "ee68362d0c8d492b9f525e5bc0d3989b", payload);
    }

    @Override
    protected String parseOutput(HashMap<String, Object> response) {
        ArrayList<HashMap<String, Object>> articles = (ArrayList<HashMap<String, Object>>) response.get("articles");
        HashMap<String, Object> article = articles.get(0);
        String output = "Here's the closest match I could find. ";

        HashMap<String, String> publisherInfo = (HashMap<String, String>) article.get("source");
        String publisher = publisherInfo.get("name");
        String title = (String) article.get("title");
        output += "The article, published by " + publisher + ", is titled \"" + title + "\" and written by ";
        String author = (String) article.get("author");
        output += author + ". ";
        String description = (String) article.get("description");
        output += description;
        if (!output.endsWith(".")) {
            output += ".";
        }
        output += " The full article can be found on the " + publisher + " website. Would you like to go there now?";

        String url = (String) article.get("url");
        metadata.put("url", url);
        String imageURL = (String) article.get("urlToImage");
        metadata.put("image", imageURL);
        return output;
    }

    @Override
    protected String handleErrors(HashMap<String, Object> response) {
        return "I'm sorry could find not find news information";
    }

    @Override
    protected String getErrorCode(HashMap<String, Object> response) {
        return null;
    }

    @Override
    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> servicePayload = new HashMap<>();
        servicePayload.put("QUERY", "");
        servicePayload.put("LANGUAGE", "en");
        return servicePayload;
    }
}
