package servicesAPI.services.finance;

import java.util.ArrayList;
import java.util.HashMap;

import servicesAPI.services.AbstractServiceRequest;

public class AbstractCharityServiceRequest extends AbstractServiceRequest {
    private final int MIN_VALUES = 1;
    private final int MAX_VALUES = 10;

    public AbstractCharityServiceRequest(String URL, HashMap<String, String> payload) {
        super(URL + "&rows={VALUES}&user_key={API-Key}", 
        "Charity", "Finance", "d1f127746fd5a2eed7de3aa66b6fadee", payload);
	}

	@Override
    protected String parseOutput(HashMap<String, Object> response) {
        ArrayList<HashMap<String, Object>> charities = (ArrayList<HashMap<String, Object>>) response.get("data");
        if (charities.size() == 0) 
            return "I'm sorry, I could not find any charity information.";
        int values = Integer.parseInt(payload.get("VALUES"));
        if (values == 0)
            values = MIN_VALUES;
        else if (values > MAX_VALUES)
            values = MAX_VALUES;
        ArrayList<HashMap<String, String>> allMetadata = new ArrayList<>();
        StringBuilder output = new StringBuilder("Here's the charity information I was able to find. ");
        for (int i = 0; i < values && i < charities.size(); i++) {
            HashMap<String, Object> charity = charities.get(i);
            String charityName = (String) charity.get("charityName");
            String category = (String) charity.get("category");
            String city = (String) charity.get("city");
            int donations = (int) charity.get("acceptingDonations");
            boolean acceptingDonations = donations == 1;

            output.append(charityName).append(" is a ").append(category).append(" charity based in ").append(city).append(".");
            if (acceptingDonations) {
                output.append(" ").append(charityName).append(" is accepting online donations.");
            } else {
                output.append(" ").append(charityName).append(" is currently not accepting online donations.");
            }

            HashMap<String, String> charityMetadata = new HashMap<>();
            String URL = (String) charity.get("url");
            String donationURL = (String) charity.get("donationUrl");
            String latitude = (String) charity.get("latitude");
            String longitude = (String) charity.get("longitude");
            charityMetadata.put("name", charityName);
            charityMetadata.put("URL", URL);
            charityMetadata.put("donationURL", donationURL);
            charityMetadata.put("latitude", latitude);
            charityMetadata.put("longitude", longitude);
            allMetadata.add(charityMetadata);
        }
        metadata.put("charities", allMetadata);
        return output.toString();
    }

    @Override
    protected String handleErrors(HashMap<String, Object> response) {
        return "I'm sorry, I could not find any charity information.";
    }

    @Override
    protected String getErrorCode(HashMap<String, Object> response) {
        return null;
    }

    @Override
    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> payload = new HashMap<>();
        payload.put("VALUES", Integer.toString(MIN_VALUES));
        return payload;
    }
}
