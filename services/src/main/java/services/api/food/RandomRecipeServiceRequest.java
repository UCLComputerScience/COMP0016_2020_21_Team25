package services.api.food;


import java.util.HashMap;

public class RandomRecipeServiceRequest extends AbstractRecipeServiceRequest {
    public RandomRecipeServiceRequest(HashMap<String, String> payload) {
        super("https://api.spoonacular.com/recipes/random?number=1", payload);
    }

    @Override
    protected HashMap<String, String> populatePayload() {
        return new HashMap<>();
    }
}