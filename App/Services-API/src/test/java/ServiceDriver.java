import servicesAPI.serviceHandler.RequestHandler;
import servicesAPI.serviceHandler.RequestHandlerFactory;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServiceDriver {
    final RequestHandler handler = RequestHandlerFactory.instance();
    private final HashMap<String, String> params = new HashMap<>();
    private final BlockingQueue<String> appQueue = new LinkedBlockingQueue<>();

    public ServiceDriver() {
        new Thread(new ResponseListener(appQueue)).start();
    }

    public void start() {
        handler.setAppQueue(appQueue);
        weatherRequest();
        dictionaryRequest(false, true, "dictionary", "en");
        dictionaryRequest(false, false, "example", "en");
        dictionaryRequest(true, false, "accuracy", "en");
        dictionaryRequest(true, true, "verisimilitude", "en");
    }

    private void weatherRequest() {
        params.clear();
        handler.makeRequest("weather", params);
    }

    private void dictionaryRequest(Boolean includeSynonyms, Boolean synonymsOnly,
                                   String word, String language) {
        params.clear();
        params.put("SYNONYMS-ONLY", synonymsOnly.toString());
        params.put("INCLUDE-SYNONYMS", includeSynonyms.toString());
        params.put("WORD", word);
        params.put("LANGUAGE", language);
        handler.makeRequest("dictionary", params);
    }

    public static void main(String[] args) {
        ServiceDriver serviceDriver = new ServiceDriver();
        serviceDriver.start();
    }
}
