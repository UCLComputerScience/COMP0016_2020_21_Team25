package services.api.schema;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory design pattern to retrieve services using the JSON schema.
 */
public final class SchemaServiceFactory {
    private SchemaServiceFactory() {

    }

    public static SchemaServiceRequest getService(String name, HashMap<String, String> payload) throws IOException {
        String contents = Util.readFile(name, false);
        Map<String, Object> schema = Util.JSONtoMap(contents);
        Util.validateJSONagainstSchema(contents);
        String URL = UrlParser.parse(schema, payload);
        return new SchemaServiceRequest(name, URL, schema, payload);
    }
}
