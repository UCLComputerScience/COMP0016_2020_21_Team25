package servicesAPI.services.food;


import java.util.HashMap;

public class RecipeBySearchServiceRequest extends AbstractRecipeServiceRequest {

    public RecipeBySearchServiceRequest(HashMap<String, String> payload) {
        super("https://api.spoonacular.com/recipes/complexSearch?query={QUERY}", payload);
    }

    @Override
    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> servicePayload = new HashMap<>();
        servicePayload.put("QUERY", "");
        return servicePayload;
    }
}
