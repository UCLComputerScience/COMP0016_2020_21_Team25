import servicesAPI.serviceHandler.ApiResponse;
import servicesAPI.serviceHandler.RequestHandler;
import servicesAPI.serviceHandler.RequestHandlerFactory;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServiceDriver {
    final RequestHandler handler = RequestHandlerFactory.instance();
    private final HashMap<String, String> params = new HashMap<>();
    private final BlockingQueue<ApiResponse> appQueue = new LinkedBlockingQueue<>();

    public ServiceDriver() {
        new Thread(new ResponseListener(appQueue)).start();
    }

    public void start() {
        handler.setAppQueue(appQueue);
        testNewsService();
        testTransportService();
        testStocksService();
        testRecipeService();
        testJokeService();
        testWeatherService();
        testDictionaryService();
    }

    private void testNewsService() {
        newsServiceRequest("trump", "en");
    }

    private void testTransportService() {
        nearestTransportServiceRequest();
        transportSearchServiceRequest("euston", "train_station");
        transportSearchServiceRequest("studley road", "bus_stop");
    }

    private void testStocksService() {
        this.stocksServiceRequest("IBM", 1, "TIME_SERIES_INTRADAY");
    }

    private void testRecipeService() {
        randomRecipeRequest();
        ingredientRecipeRequest("apple,strawberry");
        specificRecipeRequest("vegetarian");
        specificRecipeRequest("pasta");
    }

    private void testJokeService() {
        jokeRequest("");
        jokeRequest("computers");
        jokeRequest("sport");
        jokeRequest("movie");
    }

    private void testWeatherService() {
        String[] cities = new String[]{"london", "beijing", "Shouguang",
                "Yanbu", "Barisal", "Nagpur", "Karachi", "Ulaanbaatar",
                "Kampala", "Bamenda"};
        for (String city : cities) {
            airQualityServiceRequest(city);
        }
        weatherForecastRequest();
        currentWeatherRequest();
    }

    private void testDictionaryService() {
        String[] languages = new String[]{"en"};
        for (String language : languages) {
            dictionaryRequest(false, true, "dictionary", language);
            dictionaryRequest(false, false, "example", language);
            dictionaryRequest(true, false, "accuracy", language);
            dictionaryRequest(true, true, "verisimilitude", language);
        }
    }

    private void newsServiceRequest(String query, String lang) {
        params.clear();
        params.put("QUERY", query);
        params.put("LANGUAGE", lang);
        handler.makeRequest("news", params);
    }

    private void airQualityServiceRequest(String city) {
        params.clear();
        params.put("CITY_NAME", city);
        handler.makeRequest("air quality", params);
    }

    private void nearestTransportServiceRequest() {
        params.clear();
        handler.makeRequest("nearest transport", params);
    }

    private void transportSearchServiceRequest(String query, String type) {
        params.clear();
        params.put("QUERY", query);
        params.put("TRANSPORT", type);
        handler.makeRequest("transport search", params);
    }

    private void stocksServiceRequest(String symbol, int interval, String function) {
        params.clear();
        params.put("SYMBOL", symbol);
        params.put("INTERVAL", Integer.toString(interval));
        params.put("FUNCTION", function);
        handler.makeRequest("stocks", params);
    }

    private void specificRecipeRequest(String query) {
        params.clear();
        params.put("QUERY", query);
        handler.makeRequest("recipe", params);
    }

    private void ingredientRecipeRequest(String listOfIngredients) {
        params.clear();
        params.put("INGREDIENTS", listOfIngredients);
        handler.makeRequest("ingredient", params);
    }

    private void randomRecipeRequest() {
        params.clear();
        handler.makeRequest("random recipe", params);
    }

    private void jokeRequest(String term) {
        params.clear();
        params.put("TERM", term);
        handler.makeRequest("joke", params);
    }

    private void weatherForecastRequest() {
        params.clear();
        handler.makeRequest("weather forecast", params);
    }

    private void currentWeatherRequest() {
        params.clear();
        handler.makeRequest("current weather", params);
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
