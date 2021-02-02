package servicesAPI.serviceHandler;

import servicesAPI.services.ServiceRequest;
import servicesAPI.services.entertainment.JokeServiceRequest;
import servicesAPI.services.finance.StocksServiceRequest;
import servicesAPI.services.food.RandomRecipeServiceRequest;
import servicesAPI.services.food.RecipeByIngredientServiceRequest;
import servicesAPI.services.food.RecipeBySearchServiceRequest;
import servicesAPI.services.food.RecipeInstructionsService;
import servicesAPI.services.transport.BusOrTrainBySearchServiceRequest;
import servicesAPI.services.transport.NearestBusOrTrainServiceRequest;
import servicesAPI.services.utility.AirQualityServiceRequest;
import servicesAPI.services.utility.CurrentWeatherServiceRequest;
import servicesAPI.services.utility.DictionaryServiceRequest;
import servicesAPI.services.utility.WeatherForecastServiceRequest;

import java.util.HashMap;

public class ServiceFactory {
    /**
     * Employs the factory pattern to map a service name to its corresponding service request object.
     *
     * @param serviceName The name of the service.
     * @param payload     The payload - data required to complete the API call.
     * @return The ServiceRequest object.
     */
    public static ServiceRequest getServiceRequestByName(String serviceName,
                                                         HashMap<String, String> payload) {
        switch (serviceName.toLowerCase()) {
            case "air quality":
                return new AirQualityServiceRequest(payload);
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
                return null;
        }
    }
}
