package services.api.food;


import java.util.ArrayList;
import java.util.HashMap;

public class RecipeByIngredientServiceRequest extends AbstractRecipeServiceRequest {

    public RecipeByIngredientServiceRequest(HashMap<String, String> payload) {
        super("https://api.spoonacular.com/recipes/findByIngredients?ingredients={INGREDIENTS}", payload);
    }

    @Override
    protected String parseOutput(HashMap<String, Object> response) {
        ArrayList<HashMap<String, Object>> recipes = getRecipes(response);
        if (recipes.size() > 0) {
            int randomIndex = randomiser.nextInt(recipes.size());
            HashMap<String, Object> randomRecipe = recipes.get(randomIndex);
            String output = "I've found a recipe called ";
            String title = (String) randomRecipe.get("title");
            int id = (int) randomRecipe.get("id");
            metadata.put("recipe-id", id);
            String image = (String) randomRecipe.get("image");
            metadata.put("image", image);
            return output + title + ". Would you like to me to retrieve the instructions?";
        }
        return "I'm sorry, I could not find a recipe with those ingredients.";
    }

    @Override
    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> servicePayload = new HashMap<>();
        servicePayload.put("INGREDIENTS", "");
        return servicePayload;
    }
}
