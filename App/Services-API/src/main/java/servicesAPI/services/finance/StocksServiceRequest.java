package servicesAPI.services.finance;

import servicesAPI.services.ServiceRequest;

import java.util.HashMap;

public class StocksServiceRequest extends ServiceRequest {

    public StocksServiceRequest(HashMap<String, String> requestData) {
        super("https://www.alphavantage.co/query?function={FUNCTION}&symbol={SYMBOL}&interval={INTERVAL}min&apikey={API-Key}",
                "Stocks", "Finance", "6NR4MAZEVM3PIYFT", requestData);
    }

    protected String parseOutput(HashMap<String, Object> response) {
        HashMap<String, Object> metadata = (HashMap<String, Object>) response.get("Meta Data");
        String time = (String) metadata.get("3. Last Refreshed");
        String key = "Time Series (" + payload.get("INTERVAL") + "min)";
        HashMap<String, Object> stockInfo = (HashMap<String, Object>) response.get(key);
        HashMap<String, Object> latestInfo = (HashMap<String, Object>) stockInfo.get(time);

        String output = "";
        output += "Here's some stock information on " +  payload.get("SYMBOL");
        output += ". ";

        String high = (String) latestInfo.get("2. high");
        String low = (String) latestInfo.get("3. low");
        String close = (String) latestInfo.get("4. close");

        output += "The most recent high is " + high + ". ";
        output += "The most recent low is " + low + " ";
        output += "and the most recent close value is " + close + ". ";
        return output;
    }

    // TODO - Implement
    protected String handleErrors(HashMap<String, Object> response) {
        return "Sorry, I could not find any stock information on " + payload.get("SYMBOL") + ". ";
    }

    // TODO - Implement
    protected String getErrorCode(HashMap<String, Object> response) {
        return null;
    }

    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> servicePayload = new HashMap<>();
        servicePayload.put("INTERVAL", "1");
        servicePayload.put("SYMBOL", "IBM");
        servicePayload.put("FUNCTION", "TIME_SERIES_INTRADAY");
        return servicePayload;
    }
}
