package servicesAPI.serviceHandler;

import servicesAPI.services.AbstractServiceRequest;
import servicesAPI.services.entertainment.BookSearchServiceRequest;
import servicesAPI.services.entertainment.JokeServiceRequest;
import servicesAPI.services.entertainment.NewsServiceRequest;
import servicesAPI.services.finance.CharityByCityServiceRequest;
import servicesAPI.services.finance.CharityBySearchServiceRequest;
import servicesAPI.services.finance.StocksServiceRequest;
import servicesAPI.services.food.RandomRecipeServiceRequest;
import servicesAPI.services.food.RecipeByIngredientServiceRequest;
import servicesAPI.services.food.RecipeBySearchServiceRequest;
import servicesAPI.services.food.RecipeInstructionsService;
import servicesAPI.services.schema.SchemaServiceFactory;
import servicesAPI.services.transport.BusOrTrainBySearchServiceRequest;
import servicesAPI.services.transport.NearestBusOrTrainServiceRequest;
import servicesAPI.services.utility.AirQualityServiceRequest;
import servicesAPI.services.utility.CurrentWeatherServiceRequest;
import servicesAPI.services.utility.DictionaryServiceRequest;
import servicesAPI.services.utility.WeatherForecastServiceRequest;

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
