import servicesAPI.serviceHandler.RequestHandlerFactory;

import java.util.HashMap;

public class ServiceDriver {
    public static void main(String[] args) {
        HashMap<String, String> params = new HashMap<>();
//        params.put("CITY_NAME", "aaaa");
        String response = RequestHandlerFactory.instance().makeRequest("Joke", params);
        System.out.println(response);
    }
}
