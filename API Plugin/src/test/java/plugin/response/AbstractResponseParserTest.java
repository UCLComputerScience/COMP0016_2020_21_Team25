package plugin.response;

import org.junit.Before;
import plugin.ResponseParser;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static plugin.Util.*;

public abstract class AbstractResponseParserTest {
    protected String serviceName = "";
    protected String schemaSource = "";
    protected Map<String, Object> schema;
    protected Map<String, Object> metadata = new HashMap<>();

    @Before
    public abstract void setName();

    protected void setServiceName(String serviceName) {
        this.serviceName = serviceName;
        schemaSource = readFile(serviceName, false);
        schema = JSONtoMap(schemaSource);
        metadata = new HashMap<>();
    }

    protected void check(String serviceName, Map<String, Object> schema, String expected,
            Map<String, Object> expectedMetadata, boolean metadataOnly) {
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
    }
}
