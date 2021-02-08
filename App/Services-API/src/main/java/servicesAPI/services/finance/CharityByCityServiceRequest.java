package servicesAPI.services.finance;

import java.util.HashMap;

public class CharityByCityServiceRequest extends AbstractCharityServiceRequest {

	public CharityByCityServiceRequest(HashMap<String, String> payload) {
        super("http://data.orghunter.com/v1/charitysearch?city={CITY}&eligible=1", payload);
	}
    
    @Override
    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> payload = super.populatePayload();
        payload.put("CITY", "london");
        return payload;
    }
}
