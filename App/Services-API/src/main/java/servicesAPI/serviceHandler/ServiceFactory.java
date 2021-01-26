package servicesAPI.serviceHandler;

import servicesAPI.services.ServiceRequest;
import servicesAPI.services.entertainment.JokeServiceRequest;
import servicesAPI.services.finance.StocksServiceRequest;
import servicesAPI.services.food.RandomRecipeServiceRequest;
import servicesAPI.services.food.RecipeByIngredientServiceRequest;
import servicesAPI.services.food.RecipeBySearchServiceRequest;
import servicesAPI.services.transport.BusOrTrainBySearchServiceRequest;
import servicesAPI.services.transport.NearestBusOrTrainServiceRequest;
import servicesAPI.services.utility.AirQualityServiceRequest;
import servicesAPI.services.utility.DictionaryServiceRequest;
import servicesAPI.services.utility.WeatherForecastServiceRequest;
import servicesAPI.services.utility.CurrentWeatherServiceRequest;

import java.util.HashMap;

public class ServiceFactory {
    /**
     * Employs the factory pattern to map a service name to its corresponding service request object.
     *
     * @param serviceName The name of the service.
     * @param data        The payload - data required to complete the API call.
     * @return The ServiceRequest object.
     */
    public static ServiceRequest getServiceRequestByName(String serviceName,
                                                   HashMap<String, String> data) {
        switch (serviceName.toLowerCase()) {
            case "air quality":
                return new AirQualityServiceRequest(data);
            case "current weather":
                return new CurrentWeatherServiceRequest(data);
            case "dictionary":
                return new DictionaryServiceRequest(data);
            case "ingredient":
                return new RecipeByIngredientServiceRequest(data);
            case "joke":
                return new JokeServiceRequest(data);
            case "nearest transport":
                return new NearestBusOrTrainServiceRequest(data);
            case "random recipe":
                return new RandomRecipeServiceRequest(data);
            case "recipe":
                return new RecipeBySearchServiceRequest(data);
            case "stocks":
                return new StocksServiceRequest(data);
            case "transport search":
                return new BusOrTrainBySearchServiceRequest(data);
            case "weather forecast":
                return new WeatherForecastServiceRequest(data);
            default:
                return null;
        }
    }
}
