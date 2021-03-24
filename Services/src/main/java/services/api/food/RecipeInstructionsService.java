package services.api.food;

import services.api.AbstractServiceRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeInstructionsService extends AbstractServiceRequest {
    public RecipeInstructionsService(HashMap<String, String> payload) {
        super("https://api.spoonacular.com/recipes/{ID}/analyzedInstructions?stepBreakdown={DETAILED}&apiKey={API-Key}",
                "Recipe Instructions", "Food", "9728c23d120f4d1985ff2a7cc019bd96", payload);
    }

    @Override
    protected String parseOutput(HashMap<String, Object> response) {
        ArrayList<HashMap<String, Object>> results = (ArrayList<HashMap<String, Object>>) response.get("results");
        if (results.size() == 0)
            return "I'm sorry, I couldn't retrieve the instructions for this recipe.";
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
        return "I'm sorry, I could not retrieve the instructions for this recipe.";
    }

    @Override
    protected String getErrorCode(HashMap<String, Object> response) {
        return null;
    }

    @Override
    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("ID", "");
        payload.put("DETAILED", "true");
        return payload;
    }
}
