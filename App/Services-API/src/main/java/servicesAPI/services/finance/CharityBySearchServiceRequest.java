package servicesAPI.services.finance;

import java.util.HashMap;

public class CharityBySearchServiceRequest extends AbstractCharityServiceRequest {

	public CharityBySearchServiceRequest(HashMap<String, String> payload) {
        super("http://data.orghunter.com/v1/charitysearch?searchTerm={QUERY}&eligible=1", payload);
	}
    
    @Override
    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> payload = super.populatePayload();
        payload.put("QUERY", "");
        return payload;
    }
}
