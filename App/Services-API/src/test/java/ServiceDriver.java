import servicesAPI.serviceHandler.ServiceFactory;

import java.util.HashMap;

public class ServiceDriver {
    public static void main(String[] args) {
        HashMap<String, String> params = new HashMap<>();
//        params.put("CITY_NAME", "aaaa");
        String response = ServiceFactory.instance().makeRequest("Joke", params);
        System.out.println(response);
    }
}
