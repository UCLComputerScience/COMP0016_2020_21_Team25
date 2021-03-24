package services.application;

import java.util.Map;

/**
 * Simple class to output logging info.
 */
public class ApiLogger {
    public static boolean LOG_API_CALLS;

    public static void welcome() {
        String title = "Concierge";
        String message = "© Calin Hadarean, Ernest Nkansah-Badu and Mohammad Syed, 2020. All Rights Reserved.";
        int dashes = message.length();
        for (int i = 0; i < (dashes - title.length()) / 2; i++) {
            System.out.print(" ");
        }
        System.out.print(title + "\n");
        for (int topDashes = 0; topDashes < dashes; topDashes++) {
            System.out.print("-");
        }
        System.out.print("\n");
        System.out.println(message);
        for (int bottomDashes = 0; bottomDashes < dashes; bottomDashes++) {
            System.out.print("-");
        }
        System.out.println();
    }

    public static void start() {
        System.out.println("\n[SYSTEM]: Service API Started");
        System.out.println("    > Listening on port " + ServiceApplication.PORT);
        System.out.println();
    }

    public static void logApiRequest(String service, Map<String, String> headers, Map<String, String> params) {
        if (LOG_API_CALLS) {
            System.out.println("[SYSTEM]: Service Request received");
            System.out.println("    " + service);
            System.out.println("    Request Header:");
            outputMap(headers);
            System.out.println("    Payload:");
            outputMap(params);
            System.out.println();
        }
    }

    private static void outputMap(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("        " + entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void logApiCall(ServiceApiRequest apiRequest, ServiceResponse response) {
        if (LOG_API_CALLS) {
            System.out.println("[SYSTEM]: Performing API Request");
            System.out.println("    - Service: " + response.getService());
            System.out.println("    - URL: " + apiRequest.getFormattedURL());
            System.out.println("    - Message: " + response.getMessage());
            System.out.println("    - Metadata: " + response.getMetadata());
            System.out.println("    - Code: " + response.getCode());
            System.out.println();
            System.out.println();
        }
    }

}
