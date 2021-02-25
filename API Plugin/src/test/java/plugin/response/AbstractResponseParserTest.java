package plugin.response;

import org.junit.Before;
import plugin.ResponseParser;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static plugin.Driver.*;

public abstract class AbstractResponseParserTest {
    protected String serviceName = "";
    protected String structureSource = "";
    protected Map<String, Object> structure;
    protected Map<String, Object> metadata = new HashMap<>();

    @Before
    public abstract void setName();

    protected void setServiceName(String serviceName) {
        this.serviceName = serviceName;
        structureSource = readFile(serviceName, false);
        structure = JSONtoMap(structureSource);
        metadata = new HashMap<>();
    }

    protected void check(String serviceName, Map<String, Object> structure, String expected,
                         Map<String, Object> expectedMetadata, boolean metadataOnly) {
        System.out.println("Testing service " + serviceName);
        printMap(structure);
        String responseSource = readFile(serviceName, true);
        Map<String, Object> response = JSONtoMap(responseSource);
        ResponseParser responseParser = new ResponseParser(serviceName, structure);
        String output = responseParser.parse(response);
        Map<String, Object> metadata = responseParser.getMetadata();
        System.out.println("Result for " + responseParser.getServiceName());
        System.out.println(output);
        printMap(metadata);
        if (metadataOnly) {
            assertEquals(expectedMetadata, metadata);
        } else {
            assertEquals(expected.trim(), output.trim());
        }
    }
}
