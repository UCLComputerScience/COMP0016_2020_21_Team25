package backend;

import backend.models.DatabaseFactory;
import org.apache.commons.cli.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collections;

/**
 * Driver code to start RESTful API.
 */
@SpringBootApplication
public class App implements ApplicationRunner {
    public static String PORT;

    public static void main(String... args) {
        Options options = new Options();
        Option portOption = new Option("p", "port", true,
                "(optional) choose to run on a desired port - default is 8080");
        portOption.setRequired(false);
        options.addOption(portOption);

        Option logOption = new Option("l", false, "(optional) include logging information during execution");
        logOption.setRequired(false);
        options.addOption(logOption);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
            return;
        }

        String port = cmd.getOptionValue("port", "8080");
        ApiLogger.LOG_API_CALLS = Arrays.asList(cmd.getArgs()).contains("l");
        ApiLogger.welcome();
        SpringApplication application = new SpringApplication(App.class);

        try {
            // Just to ensure the port is a valid port.
            int ignored = Integer.parseInt(port);
            PORT = port;
            application.setDefaultProperties(Collections.singletonMap("server.port", port));
        } catch (NumberFormatException ignored) {
            System.out.println("error: invalid port \"" + port + "\" - must be a number");
            formatter.printHelp("utility-name", options);
            System.exit(1);
            return;
        }
        application.run(args);
    }

    @Override
    public void run(ApplicationArguments args) {
        ApiLogger.start();
        // Connect to database
        DatabaseFactory.instance();
    }
}
