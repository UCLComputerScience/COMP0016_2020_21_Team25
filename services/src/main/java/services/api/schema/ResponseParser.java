package services.api.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO - Custom Exceptions

/**
 * Parses output from the API based on the information defined in the JSON file.
 * <p>
 * Limitations: No support for array iteration.
 * <p>
 */
public class ResponseParser {
    private final String serviceName;
    private final Map<String, Object> schema;
    private final Map<String, Object> metadata = new HashMap<>();

    /**
     * Instantiate a new parser for the given service and JSON schema.
     *
     * @param serviceName the name of the service.
     * @param schema      Map representation of the JSON file defining the service.
     */
    public ResponseParser(String serviceName, Map<String, Object> schema) {
        String name = (String) schema.getOrDefault("name", serviceName);
        this.serviceName = name.length() == 0 ? serviceName : name;
        this.schema = schema;
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
            params.add(matcher.group(1).trim());
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
        Matcher matcher = Pattern.compile("\\[(.*?)]").matcher(param);
        return matcher.find();
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
        String output = (String) schema.get("message");
        Map<String, Object> metadataFields = (Map<String, Object>) schema.getOrDefault("metadata", new HashMap<>());
        return parseMessage(output, response, metadataFields);
    }

    /**
     * Parse a message using the syntax defined above.
     *
     * @param message        the message to put response values into.
     * @param response       the API response object.
     * @param metadataFields defines how to locate the required metadata fields.
     * @return a message with the API values inserted into the correct places.
     */
    private String parseMessage(String message, Map<String, Object> response, Map<String, Object> metadataFields) {
        ArrayList<String> params = extractParams(message);
        for (String param : params) {
            String value;
            if (isMapNotation(param)) {
                value = extractStringFromMap(param, response);
            } else if (isArrayNotation(param)) {
                value = extractStringFromArray(param, response);
            } else {
                value = extractDefault(param, response);
            }
            message = message.replace("{" + param + "}", value);
        }
        metadata.clear();
        populateMetadata(metadataFields, metadata, response);
        return message;
    }

    /**
     * Inserts all parameters specified in the JSON schema into the metadata of the
     * response.
     *
     * @param metadataFields defines how to locate the values.
     * @param metadata       the metadata to add the values to, allowing for custom
     *                       nested metadata.
     * @param response       the JSON response object.
     */
    private void populateMetadata(Map<String, Object> metadataFields, Map<String, Object> metadata,
            Map<String, Object> response) {
        for (String parameter : metadataFields.keySet()) {
            try {
                Map<String, Object> fieldData = (Map<String, Object>) metadataFields.get(parameter);
                boolean isCustomParameter = (boolean) fieldData.getOrDefault("custom", false);
                if (isCustomParameter) {
                    if (fieldData.containsKey("children")) {
                        Map<String, Object> childrenMetadataFields = (Map<String, Object>) fieldData.get("children");
                        Map<String, Object> childrenMetadata = new HashMap<>();
                        populateMetadata(childrenMetadataFields, childrenMetadata, response);
                        metadata.put(parameter, childrenMetadata);
                    }
                    continue;
                }
            } catch (ClassCastException ignored) {
            }
            addToMetadata(parameter, response, metadata, metadataFields);
        }
    }

    /**
     * Adds the data at the location specified by param to the metadata of the
     * response.
     *
     * @param param          the parameter used to locate the data.
     * @param response       the JSON response object.
     * @param metadata       the metadata to add the value to.
     * @param metadataFields defines how to locate the data.
     */
    private void addToMetadata(String param, Map<String, Object> response, Map<String, Object> metadata,
            Map<String, Object> metadataFields) {
        Object value;
        if (isMapNotation(param)) {
            value = extractObjectFromMap(param, response);
        } else if (isArrayNotation(param)) {
            value = extractObjectFromArray(param, response);
        } else {
            value = response.get(param);
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
        return map.get(param).toString();
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
        return (string == null) ? "" : string.toString();
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
                Object value = extractObjectFromArray(parameter, mapCopy);
                try {
                    mapCopy = (Map<String, Object>) value;
                } catch (ClassCastException ignored) {
                    return value;
                }
            } else {
                if (i < size - 1) {
                    mapCopy = (Map<String, Object>) mapCopy.get(parameter);
                } else {
                    return mapCopy.get(parameter);
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
        Matcher matcher = Pattern.compile("\\[(\\d*?)]").matcher(param);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        throw new RuntimeException("Invalid index specified in '" + param + "'");
    }

    /**
     * Returns an element, of any type, from an array, which is stored in a map.
     *
     * @param keyName specifies the name of the array and the index to retrieve.
     * @param map     the map containing the array.
     * @return the element in the array at the index specified by the keyName.
     */
    private Object extractObjectFromArray(String keyName, Map<String, Object> map) {
        int notationIndex = keyName.indexOf("[");
        String formattedKeyName = keyName.substring(0, notationIndex);
        ArrayList<Object> array = (ArrayList<Object>) map.get(formattedKeyName);
        if (array == null) {
            throw new RuntimeException(
                    "Could find not a value associated with the key '" + formattedKeyName + "' in the response.");
        }
        int index = getArrayIndex(keyName);
        Object objectInArray = array.get(index);

        String indices = keyName.substring(notationIndex);
        while (isMultidimensionalNotation(indices)) {
            int firstClosingBrace = indices.indexOf("]");
            indices = indices.substring(firstClosingBrace + 1);
            int nestedIndex = getArrayIndex(indices);
            ArrayList<Object> objectAsArray = (ArrayList<Object>) objectInArray;
            objectInArray = objectAsArray.get(nestedIndex);
            if (objectInArray == null) {
                throw new RuntimeException(
                        "Could find not a value associated with the key '" + formattedKeyName + "' in the response.");
            }
        }

        return objectInArray;
    }

    /**
     * Returns whether or not the parameter is using multidimensional array notation
     * e.g., arr[0][1].
     *
     * @param param the parameter to test.
     * @return whether or not the parameter is using multidimensional array notation
     *         e.g., arr[0][1].
     */
    private boolean isMultidimensionalNotation(String param) {
        Matcher matcher = Pattern.compile("(\\[(.*?)]){2,}").matcher(param);
        return matcher.find();
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
        return extractObjectFromArray(param, map).toString();
    }

    /**
     * Parse the error response object from the API using error information defined
     * in the JSON file or return a default error message.
     *
     * @param response the API response object.
     * @return a natural language string describing the error that occurred.
     */
    private String parseErrorResponse(Map<String, Object> response) {
        if (schema.containsKey("error_messages")) {
            Map<String, String> allMessages = (Map<String, String>) schema.get("error_messages");
            String code = getErrorCode(response);
            if (allMessages.containsKey(code)) {
                String message = allMessages.get(code);
                Map<String, Object> metadataFields = (Map<String, Object>) schema.getOrDefault("error_metadata",
                        new HashMap<>());
                return parseMessage(message, response, metadataFields);
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
        if (schema.containsKey("error_code_name")) {
            String key = (String) schema.get("error_code_name");
            Object code = extractObjectFromMap(key, response);
            if (code != null) {
                return code.toString();
            }
        }
        return "default";
    }

    public String getServiceName() {
        return serviceName;
    }

    public Map<String, Object> getMetadata() {
        return this.metadata;
    }
}
