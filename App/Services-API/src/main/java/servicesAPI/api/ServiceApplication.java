package servicesAPI.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Driver code to start RESTful API.
 */
@SpringBootApplication
public class ServiceApplication {
    public static boolean LOG_API_CALLS;
    public static void main(String[] args) {
        if (args.length == 0)
            LOG_API_CALLS = false;
        else
            LOG_API_CALLS = (args[0].equals("-l"));
        SpringApplication.run(ServiceApplication.class, args);
    }
}
