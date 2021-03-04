package services.api.schema;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * Utility class providing common IO methods.
 */
public final class Util {
    private static final String resourcesFolder = "src" + File.separator + "{folder}" + File.separator + "resources"
            + File.separator;
    private static final String fileExtension = ".json";
    private static final String schema = readFileSafe("schema", false);
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Private constructor to prevent instantiation.
     */
    private Util() {
    }

    /**
     * Catch IOException thrown by reading a file.
     * @param serviceName the base name of the file to read
     * @param test        indicates whether to read from the main or test folder.
     * @return the contents of the file.
     */
    public static String readFileSafe(String serviceName, boolean test) {
        try {
            return readFile(serviceName, test);
        } catch (IOException ignored) {

        }
        return "";
    }

    /**
     * Ensure the source is in compliance with the schema.
     *
     * @param source the input JSON content.
     */
    public static void validateJSONagainstSchema(String source) throws JsonProcessingException {
        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
        JsonNode schemaNode = mapper.readTree(schema);
        JsonNode subject = mapper.readTree(source);
        JsonSchema jsonSchema = jsonSchemaFactory.getSchema(schemaNode);
        Set<ValidationMessage> errorMessages = jsonSchema.validate(subject);
        if (errorMessages.size() > 0) {
            for (ValidationMessage error : errorMessages) {
                System.err.println(error.getMessage());
            }
            throw new RuntimeException(
                    "Malformed service schema, please consult the docs on the correct schema structure. The error report is shown above.");
        }
    }

    /**
     * Convert JSON string to Map object.
     *
     * @param source the JSON string.
     * @return the JSON string as a Map object.
     */
    public static Map<String, Object> JSONtoMap(String source) {
        // Prevent duplicate keys
        mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        try {
            return mapper.readValue(source, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(
                    "Malformed JSON syntax: " + e.getMessage());
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
