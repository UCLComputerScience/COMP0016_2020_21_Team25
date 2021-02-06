package servicesAPI.services.food;

import servicesAPI.services.AbstractServiceRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public abstract class AbstractRecipeServiceRequest extends AbstractServiceRequest {
    protected static final Random randomiser = new Random();
    private static final int MAX_RECIPES = 3;

    /**
     * @param URL     The base URL to make the API call.
     * @param payload Data needed to fill out the API call parameters.
     */
    public AbstractRecipeServiceRequest(String URL, HashMap<String, String> payload) {
        super(URL + "&number=" + MAX_RECIPES + "&apiKey={API-Key}&addRecipeInformation=true",
                "Recipe", "Food", "9728c23d120f4d1985ff2a7cc019bd96", payload);
    }

    protected ArrayList<HashMap<String, Object>> getRecipes(HashMap<String, Object> response) {
        ArrayList<HashMap<String, Object>> recipes = (ArrayList<HashMap<String, Object>>) response.get("recipes");
        if (recipes == null)
            return (ArrayList<HashMap<String, Object>>) response.get("results");
        return recipes;
    }

    @Override
    protected String parseOutput(HashMap<String, Object> response) {
        ArrayList<HashMap<String, Object>> recipes = getRecipes(response);
        if (recipes.size() > 0) {
            int randomIndex = randomiser.nextInt(recipes.size());
            HashMap<String, Object> randomRecipe = recipes.get(randomIndex);
            int id = (int) randomRecipe.get("id");
            metadata.put("recipe-id", id);
            String image = (String) randomRecipe.get("image");
            metadata.put("image", image);
            String output = "I've found a recipe called ";
            String title = (String) randomRecipe.get("title");
            String servings = getServings(randomRecipe);
            String cookingTime = getCookingTime(randomRecipe);

            String dairyFree = getDairyFree(randomRecipe);
            String glutenFree = getGlutenFree(randomRecipe);
            String vegetarian = getVegetarian(randomRecipe);
            String vegan = getVegan(randomRecipe);

            output += title;
            output += ". ";
            output += cookingTime;
            output += servings;

            boolean isDairyFree = (boolean) randomRecipe.get("dairyFree");
            boolean isGlutenFree = (boolean) randomRecipe.get("glutenFree");
            output += dairyFree;
            output += (isDairyFree == isGlutenFree) ? "and " : "but ";
            output += glutenFree;

            boolean isVegetarian = (boolean) randomRecipe.get("vegetarian");
            boolean isVegan = (boolean) randomRecipe.get("vegan");
            output += vegetarian;
            output += (isVegan == isVegetarian) ? "and " : "but ";
            output += vegan;
            return output + " Would you like to me to retrieve the instructions?";
        }
        return "I'm sorry, I could not find a recipe.";
    }

    private String getCookingTime(HashMap<String, Object> recipeData) {
        int cookingTime = (int) recipeData.get("readyInMinutes");
        return "It should take around " + getTime(cookingTime) + " to prepare, ";
    }

    private String getTime(int time) {
        int hours = time / 60; //since both are ints, you get an int
        int minutes = time % 60;
        String output = "";
        if (hours > 0) {
            output += (hours > 1) ? "" : "an ";
            output += hours;
            output += " hour";
            output += (hours > 1) ? "s" : "";
        }
        if (minutes > 0) {
            output += (hours > 0) ? "and " : "";
            output += (minutes > 1) ? "" : "a ";
            output += minutes;
            output += " minute";
            output += (minutes > 1) ? "s" : "";
        }
        return output;
    }

    private String getServings(HashMap<String, Object> recipeData) {
        int servings = (int) recipeData.get("servings");
        String base = "serving " + servings + " ";
        return base + (servings == 1 ? "person" : "people") + ". ";
    }

    private String getDairyFree(HashMap<String, Object> recipeData) {
        boolean dairyFree = (boolean) recipeData.get("dairyFree");
        return "It is " + (dairyFree ? "" : "not ") + "dairy free ";
    }

    private String getGlutenFree(HashMap<String, Object> recipeData) {
        boolean glutenFree = (boolean) recipeData.get("glutenFree");
        return "it is " + (glutenFree ? "" : "not ") + "gluten free. ";
    }

    private String getVegetarian(HashMap<String, Object> recipeData) {
        boolean vegetarian = (boolean) recipeData.get("vegetarian");
        return "It is " + (vegetarian ? "" : "not ") + "a vegetarian option ";
    }

    private String getVegan(HashMap<String, Object> recipeData) {
        boolean vegan = (boolean) recipeData.get("vegan");
        return "It is " + (vegan ? "" : "not ") + "a vegan option. ";
    }

    @Override
    protected String handleErrors(HashMap<String, Object> response) {
        return "The requested action could not be performed. Please try again.";
    }

    @Override
    protected String getErrorCode(HashMap<String, Object> response) {
        return null;
    }

    protected abstract HashMap<String, String> populatePayload();
}
