package backend;

import java.util.Map;

/**
 * Simple class to output logging info.
 */
public class ApiLogger {
    public static boolean LOG_API_CALLS;

    public static void log(String message) {
        System.out.println("[SYSTEM]: " + message);
    }

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
        System.out.println("\n[SYSTEM]: Backend API Started");
        System.out.println("    > Listening on port " + App.PORT);
        System.out.println();
    }

    public static void logApiRequest(String endpoint) {
        if (LOG_API_CALLS) {
            System.out.println("[SYSTEM]: Request received");
            System.out.println("    at endpoint: " + endpoint);
            System.out.println();
        }
    }

    private static void outputMap(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("        " + entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void logApiCall() {
        if (LOG_API_CALLS) {
            System.out.println("[SYSTEM]: Performing API Request");
            System.out.println();
            System.out.println();
        }
    }

}