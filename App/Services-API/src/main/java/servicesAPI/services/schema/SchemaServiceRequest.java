package servicesAPI.services.schema;

import servicesAPI.services.AbstractServiceRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper for services added using the JSON schema format.
 */
public class SchemaServiceRequest extends AbstractServiceRequest {
    private final Map<String, Object> schema;

    public SchemaServiceRequest(String name, String URL, Map<String, Object> schema, HashMap<String, String> payload) {
        super(URL, name, "", "", payload);
        this.schema = schema;
    }

    @Override
    protected String parseOutput(HashMap<String, Object> response) {
        String name = getName();
        ResponseParser responseParser = new ResponseParser(name, schema);
        return responseParser.parse(response);
    }

    @Override
    protected String handleErrors(HashMap<String, Object> response) {
        return null;
    }

    @Override
    protected String getErrorCode(HashMap<String, Object> response) {
        return null;
    }

    @Override
    protected HashMap<String, String> populatePayload() {
        return new HashMap<>();
    }
}
