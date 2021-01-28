package servicesAPI.services.food;

import servicesAPI.services.ServiceRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeInstructionsService extends ServiceRequest {
    public RecipeInstructionsService(HashMap<String, String> payload) {
        super("https://api.spoonacular.com/recipes/{ID}/analyzedInstructions?apiKey={API-Key}",
                "Recipe Instructions", "Food", "9728c23d120f4d1985ff2a7cc019bd96", payload);
    }

    @Override
    protected String parseOutput(HashMap<String, Object> response) {
        ArrayList<HashMap<String, Object>> results = (ArrayList<HashMap<String, Object>>) response.get("results");
        HashMap<String, Object> data = results.get(0);
        ArrayList<HashMap<String, Object>> stepData = (ArrayList<HashMap<String, Object>>) data.get("steps");
        ArrayList<String> steps = new ArrayList<>();
        int index = 1;
        for (HashMap<String, Object> step : stepData) {
            String stepNumber = "Step " + index + ": ";
            String stepInfo = (String) step.get("step");
            if (!stepInfo.endsWith(".")) {
                stepInfo += ".";
            }
            steps.add(stepNumber + stepInfo);
            index++;
        }
        metadata.put("steps", steps);
        return "Here's the recipe information.";
    }

    @Override
    protected String handleErrors(HashMap<String, Object> response) {
        return "I'm sorry, I could not retrieve any information on this recipe.";
    }

    @Override
    protected String getErrorCode(HashMap<String, Object> response) {
        return null;
    }

    @Override
    protected HashMap<String, String> populatePayload() {
        return new HashMap<>();
    }
}
