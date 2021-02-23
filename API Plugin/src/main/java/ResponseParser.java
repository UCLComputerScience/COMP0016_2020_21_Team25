import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses output from the API based on the information defined in the JSON file.
 * <p>
 * Limitations: No support for multi-dimensional arrays. No support for looping
 * over an array. No support for custom nested metadata. No support for
 * parameter-based endpoints.
 * <p>
 * class.
 */
public class ResponseParser {
    private final String serviceName;
    private final Map<String, Object> structure;
    private final Map<String, Object> metadataFields;
    private final Map<String, Object> metadata = new HashMap<>();

    /**
     * Instantiate a new parser for the given service and JSON structure.
     *
     * @param serviceName the name of the service.
     * @param structure   Map representation of the JSON file defining the service.
     */
    public ResponseParser(String serviceName, Map<String, Object> structure) {
        this.serviceName = serviceName;
        this.structure = structure;
        this.metadataFields = (Map<String, Object>) structure.getOrDefault("metadata", new HashMap<>());
    }

    /**
     * Parse the API response into a single natural language string.
     *
     * @param response the API response JSON object.
     * @return a natural language string describing the response.
     */
    public String parse(Map<String, Object> response) {
        try {
            return parseResponse(response);
        } catch (NullPointerException | ClassCastException | IndexOutOfBoundsException ignored) {
            return parseErrorResponse(response);
        }
    }

    /**
     * Extracts the parameters defined in the 'message' field of the JSON file.
     *
     * @param message the message containing the parameters.
     * @return an array of all found parameters.
     */
    private ArrayList<String> extractParams(String message) {
        ArrayList<String> params = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\{(.*?)}").matcher(message);
        while (matcher.find()) {
            params.add(matcher.group(1).trim().replaceAll("\\s+", ""));
        }
        return params;
    }

    /**
     * Returns whether the string is using array notation (array[index]).
     *
     * @param param the string to test.
     * @return whether or not the string is using array notation.
     */
    private boolean isArrayNotation(String param) {
        Matcher matcher = Pattern.compile("\\[(\\d+)]").matcher(param);
        return matcher.find() && !isMapNotation(param);
    }

    /**
     * Returns whether the string is using map notation (map.key).
     *
     * @param param the string to test.
     * @return whether or not the string is using map notation.
     */
    private boolean isMapNotation(String param) {
        return param.contains(".") && !param.endsWith(".") && !param.startsWith(".");
    }

    /**
     * Parse the JSON response received from the API.
     *
     * @param response the JSON object as a Map.
     * @return a natural language string representing the response.
     */
    private String parseResponse(Map<String, Object> response) {
        String output = (String) structure.get("message");
        return parseMessage(output, response);
    }

    /**
     * Parse a message using the syntax defined above.
     *
     * @param message  the message to put response values into.
     * @param response the API response object.
     * @return a message with the API values inserted into the correct places.
     */
    private String parseMessage(String message, Map<String, Object> response) {
        ArrayList<String> params = extractParams(message);
        for (String param : params) {
            String value;
            if (isMapNotation(param)) {
                value = (extractStringFromMap(param, response));
            } else if (isArrayNotation(param)) {
                value = (extractStringFromArray(param, response));
            } else {
                value = (extractDefault(param, response));
            }
            message = message.replace("{" + param + "}", value);
        }
        populateMetadata(response);
        return message;
    }

    /**
     * Inserts all parameters specified in the JSON schema into the metadata of the
     * response.
     *
     * @param response the JSON response object.
     */
    private void populateMetadata(Map<String, Object> response) {
        for (String parameter : metadataFields.keySet()) {
            addToMetadata(parameter, response);
        }
    }

    /**
     * Adds the data at the location specified by param to the metadata of the
     * response.
     *
     * @param param    the parameter used to locate the data.
     * @param response the JSON response object.
     */
    private void addToMetadata(String param, Map<String, Object> response) {
        Object value;
        if (isMapNotation(param)) {
            value = extractObjectFromMap(param, response);
        } else {
            value = extractObjectFromArray(param, response);
        }
        String keyName;
        try {
            Map<String, String> fieldData = (Map<String, String>) metadataFields.get(param);
            keyName = fieldData.getOrDefault("alias", param);
        } catch (ClassCastException ignored) {
            keyName = (String) metadataFields.get(param);
        }
        metadata.put(keyName, value);
    }

    /**
     * Extract a key from a map i.e., for param = 'key' and map = { param: value }
     * -> extractDefault returns map[key].
     *
     * @param param the key of the map.
     * @param map   the map where the key, value pair is held.
     * @return the value pointed to by the key.
     */
    private String extractDefault(String param, Map<String, Object> map) {
        return (String) map.get(param);
    }

    /**
     * Extract a string from a map using the notation in the rawParam.
     *
     * @param rawParam the parameter containing the index to return;
     * @param map      the map where the object is stored.
     * @return the object stored in the map.
     */
    private String extractStringFromMap(String rawParam, Map<String, Object> map) {
        Object string = extractObjectFromMap(rawParam, map);
        if (string == null) {
            return "";
        }
        return string.toString();
    }

    /**
     * Extract an object from a map using the notation in the rawParam.
     *
     * @param rawParam the parameter containing the index to return;
     * @param map      the map where the object is stored.
     * @return the object stored in the map.
     */
    private Object extractObjectFromMap(String rawParam, Map<String, Object> map) {
        String[] params = rawParam.split("\\.");
        Map<String, Object> mapCopy = map;
        int size = params.length;
        for (int i = 0; i < size; i++) {
            String parameter = params[i];
            if (isArrayNotation(parameter)) {
                mapCopy = (Map<String, Object>) extractObjectFromArray(parameter, mapCopy);
            } else {
                if (i < size - 1) {
                    mapCopy = (Map<String, Object>) mapCopy.get(parameter);
                } else {
                    Object value = mapCopy.get(parameter);
                    if (value != null) {
                        return value.toString();
                    }
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * Returns the value between a square bracket pair ([]).
     *
     * @param param the string containing the array index.
     * @return the array index.
     */
    private int getArrayIndex(String param) {
        Matcher matcher = Pattern.compile("\\[(.*?)]").matcher(param);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }

    /**
     * Returns an element, of any type, from an array, which is stored in a map.
     *
     * @param keyName specifies the name of the array and the index to retrieve.
     * @param map     the map containing the array.
     * @return the element in the array at the index specified by the keyName.
     */
    private Object extractObjectFromArray(String keyName, Map<String, Object> map) {
        int index = getArrayIndex(keyName);
        if (index == -1) {
            throw new RuntimeException("Invalid index specified in '" + keyName + "'");
        }
        int notationIndex = keyName.indexOf("[");
        String formattedKeyName = keyName.substring(0, notationIndex);
        ArrayList<Object> array = (ArrayList<Object>) map.get(formattedKeyName);
        return array.get(index);
    }

    /**
     * Returns an element, of type String only, from an array, which is stored in a
     * map
     *
     * @param param specifies the name of the array and the index to retrieve.
     * @param map   the map containing the array.
     * @return the string in the array at the index specified by the param.
     */
    private String extractStringFromArray(String param, Map<String, Object> map) {
        return (String) extractObjectFromArray(param, map);
    }

    /**
     * Parse the error response object from the API using error information defined
     * in the JSON file or return a default error message.
     *
     * @param response the API response object.
     * @return a natural language string describing the error that occurred.
     */
    private String parseErrorResponse(Map<String, Object> response) {
        String code = getErrorCode(response);
        if (structure.containsKey("error_messages")) {
            Map<String, String> allMessages = (Map<String, String>) structure.get("error_messages");
            if (allMessages.containsKey(code)) {
                String message = allMessages.get(code);
                return parseMessage(message, response);
            }
        }
        return "An error occurred while calling the " + serviceName + " service.";
    }

    /**
     * Retrieve the error code from the API response object using information
     * defined in the JSON file.
     *
     * @param response the API response object.
     * @return the error code contained inside it.
     */
    public String getErrorCode(Map<String, Object> response) {
        if (structure.containsKey("error_code_name")) {
            String key = (String) structure.get("error_code_name");
            Object code = extractObjectFromMap(key, response);
            if (code != null) {
                return code.toString();
            }
            return "default";
        }
        return "";
    }

    public String getServiceName() {
        return serviceName;
    }

    public Map<String, Object> getMetadata() {
        return this.metadata;
    }
}
