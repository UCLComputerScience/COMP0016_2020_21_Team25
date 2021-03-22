package plugin.response;

import services.api.schema.ResponseParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static services.api.schema.Util.JSONtoMap;
import static services.api.schema.Util.readFile;

public abstract class AbstractResponseParserTest {
    protected String serviceName = "";
    protected String schemaSource = "";
    protected Map<String, Object> schema;
    protected Map<String, Object> metadata = new HashMap<>();

    @BeforeEach
    public abstract void setName();

    protected void setServiceName(String serviceName) {
        this.serviceName = serviceName;
        try {
            schemaSource = readFile(serviceName, false);
            schema = JSONtoMap(schemaSource);
            metadata = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void check(String serviceName, Map<String, Object> schema, String expected,
                         Map<String, Object> expectedMetadata, boolean metadataOnly) {
        try {
            String responseSource = readFile(serviceName, true);
            Map<String, Object> response = JSONtoMap(responseSource);
            ResponseParser responseParser = new ResponseParser(serviceName, schema);
            String output = responseParser.parse(response);
            Map<String, Object> metadata = responseParser.getMetadata();
            if (metadataOnly) {
                assertEquals(expectedMetadata, metadata);
            } else {
                assertEquals(expected.trim(), output.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
