package services.handler;

import services.api.AbstractServiceRequest;
import services.api.entertainment.BookSearchServiceRequest;
import services.api.entertainment.JokeServiceRequest;
import services.api.entertainment.NewsServiceRequest;
import services.api.finance.CharityByCityServiceRequest;
import services.api.finance.CharityBySearchServiceRequest;
import services.api.finance.StocksServiceRequest;
import services.api.food.RandomRecipeServiceRequest;
import services.api.food.RecipeByIngredientServiceRequest;
import services.api.food.RecipeBySearchServiceRequest;
import services.api.food.RecipeInstructionsService;
import services.api.schema.SchemaServiceFactory;
import services.api.transport.BusOrTrainBySearchServiceRequest;
import services.api.transport.NearestBusOrTrainServiceRequest;
import services.api.utility.AirQualityServiceRequest;
import services.api.utility.CurrentWeatherServiceRequest;
import services.api.utility.DictionaryServiceRequest;
import services.api.utility.WeatherForecastServiceRequest;

import java.io.IOException;
import java.util.HashMap;

public final class ServiceFactory {
    private ServiceFactory() {

    }

    /**
     * Employs the factory pattern to map a service name to its corresponding
     * service request object.
     *
     * @param serviceName The name of the service.
     * @param payload     The payload - data required to complete the API call.
     * @return The ServiceRequest object.
     */
    public static AbstractServiceRequest getServiceRequestByName(String serviceName, HashMap<String, String> payload) {
        switch (serviceName.toLowerCase()) {
            case "air quality":
                return new AirQualityServiceRequest(payload);
            case "book":
                return new BookSearchServiceRequest(payload);
            case "charity search":
                return new CharityBySearchServiceRequest(payload);
            case "charity by city":
                return new CharityByCityServiceRequest(payload);
            case "current weather":
                return new CurrentWeatherServiceRequest(payload);
            case "dictionary":
                return new DictionaryServiceRequest(payload);
            case "ingredient":
                return new RecipeByIngredientServiceRequest(payload);
            case "joke":
                return new JokeServiceRequest(payload);
            case "nearest transport":
                return new NearestBusOrTrainServiceRequest(payload);
            case "news":
                return new NewsServiceRequest(payload);
            case "random recipe":
                return new RandomRecipeServiceRequest(payload);
            case "recipe":
                return new RecipeBySearchServiceRequest(payload);
            case "recipe instructions":
                return new RecipeInstructionsService(payload);
            case "stocks":
                return new StocksServiceRequest(payload);
            case "transport search":
                return new BusOrTrainBySearchServiceRequest(payload);
            case "weather forecast":
                return new WeatherForecastServiceRequest(payload);
            default:
                try {
                    return SchemaServiceFactory.getService(serviceName, payload);
                } catch (IOException ignored) {
                    return null;
                }
        }
    }
}
