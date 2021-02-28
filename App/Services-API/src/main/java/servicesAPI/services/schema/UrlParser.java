package servicesAPI.services.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to read service schema from a JSON file to build the URL to the
 * external API.
 */
public final class UrlParser {
    /**
     * Private constructor to prevent instantiation
     */
    private UrlParser() {
    }

    /**
     * Helper function to build url when either testing or using in production.
     *
     * @param requestValues parameters to insert into URL.
     * @param serviceSchema the schema with parsing instructions.
     * @return the URL with values inserted.
     */
    private static String buildUrl(Map<String, Object> serviceSchema, Map<String, String> requestValues) {
        checkTopLevelKeys(serviceSchema);

        String baseUrl = (String) serviceSchema.get("url");
        // Remove any parameters just in case
        int paramIndex = baseUrl.indexOf("?");
        if (paramIndex != -1) {
            baseUrl = baseUrl.substring(0, paramIndex);
        }

        Map<String, String> apiKeyData = new HashMap<>();
        if (serviceSchema.containsKey("api-key")) {
            apiKeyData = (Map<String, String>) serviceSchema.get("api-key");
        }

        return buildUrl(baseUrl, serviceSchema, requestValues, apiKeyData);
    }

    /**
     * Build a service request object from the JSON schema defined in the value and
     * populate the url parameters with the request values.
     *
     * @param serviceSchema the JSON schema.
     * @param requestValues the parameters needed by the service.
     * @return a ServiceRequest object encapsulating the service name and its
     * formatted url to make the call.
     */
    public static String parse(Map<String, Object> serviceSchema, Map<String, String> requestValues) {
        return buildUrl(serviceSchema, requestValues);
    }

    /**
     * Ensure the top-level keys in the JSON file are of the correct schema.
     *
     * @param serviceSchema the Map representation of the JSON file.
     */
    private static void checkTopLevelKeys(Map<String, Object> serviceSchema) {
        String[] requiredParameters = new String[]{"url", "parameters", "message"};
        for (String parameter : requiredParameters) {
            if (!serviceSchema.containsKey(parameter)) {
                throw new RuntimeException("Missing required top-level parameter '" + parameter + "'");
            }
        }
    }

    /**
     * Populates the required parameters, specified in the JSON file, with the
     * values passed in and put it into the url.
     *
     * @param baseUrl       the url without any transformations applied, defined in
     *                      the JSON file.
     * @param schema        the JSON file as a map.
     * @param requestValues the values to insert into the url.
     * @param apiKeyData    information needed to add the API key (if required) to
     *                      the url, defined in the JSON file.
     * @return a formatted String used to make the API call.
     */
    private static String buildUrl(String baseUrl, Map<String, Object> schema, Map<String, String> requestValues,
                                   Map<String, String> apiKeyData) {
        ArrayList<Map<String, Object>> endpoints = (ArrayList<Map<String, Object>>) schema.getOrDefault("endpoints",
                new ArrayList<>());
        Map<String, Object> parameters = (Map<String, Object>) schema.get("parameters");

        // Remove trailing slashes
        if (endpoints.size() > 0) {
            while (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
            }
        }

        StringBuilder url = new StringBuilder(baseUrl);
        addEndpoints(url, endpoints, requestValues);
        int paramsAdded = addParameters(url, parameters, requestValues);
        addApiKey(url, paramsAdded, apiKeyData);
        return url.toString();
    }

    /**
     * Adds the parameters defined in requestValues to the URL based on information
     * from the JSON files.
     *
     * @param url           the URL to add parameters to.
     * @param parameters    defines how to add a parameter to the URL.
     * @param requestValues the parameters to add to the URL.
     * @return the number of parameters that were added to the URL.
     */
    private static int addParameters(StringBuilder url, Map<String, Object> parameters,
                                     Map<String, String> requestValues) {
        int size = parameters.size();
        int paramsAdded = 0;
        for (String key : parameters.keySet()) {
            String formattedParam = formatParameter(key, parameters, requestValues);
            if (formattedParam.length() > 0) {
                if (paramsAdded == 0) {
                    url.append("?");
                } else if (paramsAdded <= size - 1) {
                    url.append("&");
                }
                paramsAdded++;
            }
            url.append(formattedParam);
        }
        return paramsAdded;
    }

    /**
     * Add dynamic endpoints to the URL.
     *
     * @param url            the URL to add the endpoints to.
     * @param endpoints      defines how to add a dynamic endpoint to the URL.
     * @param endpointValues contains the endpoints to add.
     */
    private static void addEndpoints(StringBuilder url, ArrayList<Map<String, Object>> endpoints,
                                     Map<String, String> endpointValues) {
        for (Map<String, Object> endpoint : endpoints) {
            if (!endpoint.containsKey("name")) {
                throw new RuntimeException("Missing required parameter 'name' for an endpoint");
            }
            String name = (String) endpoint.get("name");
            if (name.length() == 0) {
                throw new RuntimeException("Required parameter 'name' for an endpoint must not be blank");
            }
            if (!endpoint.containsKey("default") && !endpointValues.containsKey(name)) {
                throw new RuntimeException(
                        "No value was specified for the endpoint '" + name + "' and no default value was provided");
            }
            String defaultValue = (String) endpoint.getOrDefault("default", "");
            String endpointValue = endpointValues.getOrDefault(name, defaultValue);
            if (endpointValue.length() == 0) {
                if (endpointValue.equals(defaultValue)) {
                    throw new RuntimeException("Missing value for endpoint '" + name + "'");
                }
                endpointValue = defaultValue;
            }
            url.append("/");
            url.append(endpointValue.replace(" ", "%20"));
        }
    }

    /**
     * Adds the API key (if required) to the url based on information in the JSON
     * file.
     *
     * @param url        the url to insert the API key into.
     * @param params     how many parameters have already been added to the url.
     * @param apiKeyData data about how to insert the API key into the url, defined
     *                   in the JSON file.
     */
    private static void addApiKey(StringBuilder url, int params, Map<String, String> apiKeyData) {
        if (apiKeyData.size() > 0) {
            checkApiKeyData(apiKeyData);
            url.append((params == 0) ? "?" : "&");
            String apiKeyName = apiKeyData.getOrDefault("alias", "api-key");
            if (apiKeyName.length() == 0) {
                apiKeyName = "api-key";
            }
            String apikey = apiKeyData.get("value");
            url.append(apiKeyName).append("=").append(apikey);
        }
    }

    /**
     * Returns the formatted parameter with its value attached, if applicable.
     *
     * @param key           the name of the parameter.
     * @param parameters    the map containing the parameter.
     * @param requestValues the map where the corresponding value for the parameter
     *                      is stored.
     * @return the formatted parameter.
     */
    private static String formatParameter(String key, Map<String, Object> parameters,
                                          Map<String, String> requestValues) {
        String formattedParam;
        Object param = parameters.get(key);
        try {
            formattedParam = getParameterAndValue(key, (Map<String, Object>) param, requestValues);
        } catch (ClassCastException ignored) {
            // e.g. "city": "q", this syntax represents an alias.
            Map<String, Object> data = new HashMap<>();
            data.put("alias", param.toString());
            formattedParam = getParameterAndValue(key, data, requestValues);
        }
        return formattedParam;
    }

    /**
     * Pairs a parameter with its value in the request values, applying the alias is
     * required.
     *
     * @param paramName the name of the parameter.
     * @param paramData instructions on how to pair the parameter with its value,
     *                  defined in the json file.
     * @param values    the map where the corresponding value for the parameter is
     *                  stored.
     * @return a string representing the parameter and its value.
     */
    private static String getParameterAndValue(String paramName, Map<String, Object> paramData,
                                               Map<String, String> values) {
        String param = "";
        boolean required = false;
        if (paramData.containsKey("required")) {
            Object requiredBool = paramData.get("required");
            try {
                required = (boolean) requiredBool;
            } catch (ClassCastException ignored) {
                throw new RuntimeException("Field name 'required' must be of type boolean");
            }
        }
        boolean hasValue = values.containsKey(paramName);

        boolean hasDefault = paramData.containsKey("default");
        if (required && !hasValue && !hasDefault) {
            throw new RuntimeException("Missing required service parameter '" + paramName + "'");
        }

        String apiParamName = (String) paramData.getOrDefault("alias", paramName);
        // If value associated with 'alias' is blank.
        if (apiParamName.length() == 0) {
            apiParamName = paramName;
        }

        Object defaultValue = paramData.getOrDefault("default", "");
        Object value = values.getOrDefault(apiParamName, (String) defaultValue);
        if (value == defaultValue && defaultValue == "") {
            if (!required) {
                return "";
            }
            throw new RuntimeException("Missing value for required parameter '" + apiParamName + "'");
        }
        param += apiParamName + "=" + value;
        return param.replace(" ", "%20");
    }

    /**
     * Ensures the API key data defined in the JSON file is of the correct format.
     *
     * @param apiKeyData the data describing how to insert the API key into the url.
     */
    private static void checkApiKeyData(Map<String, String> apiKeyData) {
        if (apiKeyData.size() > 2) {
            throw new RuntimeException("The field 'api-key' must have either zero, one or two parameters");
        }
        if (!apiKeyData.containsKey("value")) {
            throw new RuntimeException("Missing required field 'value' for the API key.");
        }
    }
}
