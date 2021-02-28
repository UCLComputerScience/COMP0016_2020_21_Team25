package plugin.url;

import org.junit.Before;
import plugin.UrlParser;
import plugin.Util;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public abstract class AbstractUrlParserTest {
    protected Map<String, Object> schema;
    protected Map<String, Object> parameters;
    protected final String jokeApiKey = "9728c23d120f4d1985ff2a7cc019bd96";

    @Before
    public void setServiceName() {
        String schemaSource = Util.readFile("joke", false);
        schema = Util.JSONtoMap(schemaSource);
        parameters = new HashMap<>();
    }

    protected void checkIO(String serviceName, Map<String, Object> parameters, String expectedUrl) {
        String url = UrlParser.parse(serviceName, parameters);
        assertEquals(expectedUrl, url);
    }

    protected void checkSchema(Map<String, Object> parameters, String expectedUrl) {
        String url = UrlParser.parseTest(parameters, schema);
        assertEquals(expectedUrl, url);
    }
}
