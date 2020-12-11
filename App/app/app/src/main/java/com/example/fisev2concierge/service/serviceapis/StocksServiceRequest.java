package com.example.fisev2concierge.service.serviceapis;

import java.util.HashMap;

public class StocksServiceRequest extends ServiceRequest {

    public StocksServiceRequest(HashMap<String, String> requestData) {
        super("https://www.alphavantage.co/query?function={FUNCTION}&symbol={SYMBOL}&interval={INTERVAL}min&apikey={API-Key}",
                "Stocks", "Finance", "6NR4MAZEVM3PIYFT", requestData);
    }

    // TODO - Implement
    protected String parseOutput(HashMap<String, Object> response) {
        return response.toString();
    }

    // TODO - Implement
    protected String handleErrors(HashMap<String, Object> response) {
        return null;
    }

    // TODO - Implement
    protected String getErrorCode(HashMap<String, Object> response) {
        return null;
    }

    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> servicePayload = new HashMap<>();
        servicePayload.put("INTERVAL", "60");
        servicePayload.put("SYMBOL", "IBM");
        servicePayload.put("FUNCTION", "TIME_SERIES_INTRADAY");
        return servicePayload;
    }
}
