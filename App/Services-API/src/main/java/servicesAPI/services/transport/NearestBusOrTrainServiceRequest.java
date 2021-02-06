package servicesAPI.services.transport;

import servicesAPI.services.AbstractServiceRequest;

import java.util.ArrayList;
import java.util.HashMap;

public class NearestBusOrTrainServiceRequest extends AbstractServiceRequest {
    public NearestBusOrTrainServiceRequest(HashMap<String, String> payload) {
        super("http://transportapi.com/v3/uk/places.json?min_lat={MIN-LAT}&min_lon={MIN-LON}&max_lat={MAX-LAT}&max_lon={MAX-LON}&type={TRANSPORT}&app_id=74da2f0e&app_key={API-Key}",
                "Nearest Transport", "Transport", "3ae3d6f10c454b1b3ee260e082435eab", payload);
    }

    @Override
    protected String parseOutput(HashMap<String, Object> response) {
        ArrayList<HashMap<String, Object>> transportData = (ArrayList<HashMap<String, Object>>) response.get("member");
        ArrayList<Double[]> locations = new ArrayList<>();
        for (HashMap<String, Object> stationData : transportData) {
            Double latitude = (Double) stationData.get("latitude");
            Double longitude = (Double) stationData.get("longitude");
            Double[] location = new Double[]{latitude, longitude};
            locations.add(location);
        }
        metadata.put("locations", locations);
        return "Here's what I've found in your area.";
    }

    @Override
    protected String handleErrors(HashMap<String, Object> response) {
        return "I'm sorry, I couldn't find anything in your area.";
    }

    @Override
    protected String getErrorCode(HashMap<String, Object> response) {
        return null;
    }

    @Override
    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> servicePayload = new HashMap<>();
        servicePayload.put("MIN-LAT", "51.530121");
        servicePayload.put("MAX-LAT", "51.538121");
        servicePayload.put("MIN-LON", "-0.009");
        servicePayload.put("MAX-LON", "-0.001");
        servicePayload.put("TRANSPORT", "bus_stop");
        return servicePayload;
    }
}
