package services.api.schema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Utility class providing common IO methods.
 */
public final class Util {
    private static final String resourcesFolder = "src" + File.separator + "{folder}" + File.separator + "resources"
            + File.separator;
    private static final String fileExtension = ".json";

    /**
     * Private constructor to prevent instantiation.
     */
    private Util() {
    }

    /**
     * Convert JSON string to Map object.
     *
     * @param source the JSON string.
     * @return the JSON string as a Map object.
     */
    public static Map<String, Object> JSONtoMap(String source) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(source, new TypeReference<>() {
            });
        } catch (JsonProcessingException ignored) {
            throw new RuntimeException(
                    "Malformed service data, please consult the docs on the correct schema structure.");
        }
    }

    /**
     * Read the contents of a file.
     *
     * @param serviceName the base name of the file to read
     * @param test        indicates whether to read from the main or test folder.
     * @return the contents of the file.
     */
    public static String readFile(String serviceName, boolean test) throws IOException {
        String base = resourcesFolder.replace("{folder}", test ? "test" : "main");
        return readFile(base + serviceName + fileExtension);
    }

    /**
     * Read the contents of the file.
     *
     * @param src the location of the file.
     * @return the string contents of the file.
     * @throws IOException if the file could not be located.
     */
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
}
