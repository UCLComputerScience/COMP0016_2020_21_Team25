package plugin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * For running manually, not for production
 */
public final class Driver {
    private static final String resourcesFolder = "src" + File.separator + "{folder}" + File.separator + "resources"
            + File.separator;
    private static final String fileExtension = ".json";

    private Driver() {
    }

    public static void printMap(Map<String, Object> map) {
        ObjectMapper mapper = new ObjectMapper();
        String output;
        try {
            output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
            System.out.println(output);
        } catch (JsonProcessingException ignored) {
        }
    }

    public static Map<String, Object> JSONtoMap(String source) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(source, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Malformed service data, please consult the docs on the correct structure.");
        }
    }

    public static String readFile(String serviceName, boolean test) {
        try {
            String base = resourcesFolder.replace("{folder}", test ? "test" : "main");
            return readFile(base + serviceName + fileExtension);
        } catch (IOException e) {
            throw new RuntimeException("Unknown service '" + serviceName + "'");
        }
    }

    private static String readFile(String src) throws IOException {
        File file = new File(src);
        InputStream inputStream = new FileInputStream(file);
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        inputStream.close();
        return result.toString(StandardCharsets.UTF_8.name());
    }

    public void runResponseParser() {
        String[] serviceNames = new String[]{"book", "current-weather", "joke", "news"};
        for (String serviceName : serviceNames) {
            String source = readFile(serviceName, false);
            Map<String, Object> structure = JSONtoMap(source);
            ResponseParser responseParser = new ResponseParser(serviceName, structure);
            String responseSrc = readFile(serviceName, true);
            Map<String, Object> response = JSONtoMap(responseSrc);
            System.out.println(serviceName);
            System.out.println(responseParser.parse(response));
            System.out.println(responseParser.getMetadata());
            System.out.println();
        }
    }

    public static void main(String[] args) {
        UrlParser urlParser = new UrlParser();
        String url = urlParser.parse("test", new HashMap<>());
        System.out.println(url);
    }
}
