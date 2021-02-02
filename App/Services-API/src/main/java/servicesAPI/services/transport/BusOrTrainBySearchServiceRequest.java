package servicesAPI.services.transport;

import servicesAPI.services.ServiceRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class BusOrTrainBySearchServiceRequest extends ServiceRequest {
    public BusOrTrainBySearchServiceRequest(HashMap<String, String> payload) {
        super("http://transportapi.com/v3/uk/places.json?query={QUERY}&type={TRANSPORT}&app_id=74da2f0e&app_key={API-Key}",
                "Transport Search", "Transport", "3ae3d6f10c454b1b3ee260e082435eab", payload);
    }

    @Override
    protected String parseOutput(HashMap<String, Object> response) {
        ArrayList<HashMap<String, Object>> transportData = (ArrayList<HashMap<String, Object>>) response.get("member");
        HashMap<String, Object> stationData = transportData.get(0);
        Double latitude = (Double) stationData.get("latitude");
        Double longitude = (Double) stationData.get("longitude");
        Double[] location = new Double[]{latitude, longitude};
        metadata.put("location", location);
        return "Here's what I've found about " + payload.get("QUERY") + ".";
    }

    @Override
    protected String handleErrors(HashMap<String, Object> response) {
        return "I'm sorry, I couldn't find any information on " + payload.get("QUERY") + ".";
    }

    @Override
    protected String getErrorCode(HashMap<String, Object> response) {
        return null;
    }

    @Override
    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> servicePayload = new HashMap<>();
        servicePayload.put("QUERY", "euston");
        servicePayload.put("TRANSPORT", "train_station");
        return servicePayload;
    }
}

