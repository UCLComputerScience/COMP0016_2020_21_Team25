package backend;

import java.text.SimpleDateFormat;

/**
 * Simple class to output logging info.
 */
public class ApiLogger {
    public static boolean LOG_API_CALLS;

    public static void log(String message) {
        if (LOG_API_CALLS) {
            String formattedDate = new SimpleDateFormat("dd/MM/yyyy @ HH:mm:ss").format(System.currentTimeMillis());
            System.out.println("[INFO] (" + formattedDate + ") " + message);
        }
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
        log("Backend API Started");
        log("Listening on port " + App.PORT);
        System.out.println();
    }
}