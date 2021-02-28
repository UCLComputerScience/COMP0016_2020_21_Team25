package plugin.url;

import org.junit.Before;
import plugin.UrlParser;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static plugin.Driver.*;

public abstract class AbstractUrlParserTest {
    private final UrlParser urlParser = new UrlParser();
    protected Map<String, Object> structure;
    protected Map<String, Object> parameters;
    protected final String jokeApiKey = "9728c23d120f4d1985ff2a7cc019bd96";

    @Before
    public abstract void setServiceName();

    protected void setServiceName(String serviceName) {
        String structureSource = readFile(serviceName, false);
        structure = JSONtoMap(structureSource);
        parameters = new HashMap<>();
    }

    protected void checkIO(String serviceName, Map<String, Object> parameters, String expectedUrl) {
        String url = urlParser.parse(serviceName, parameters);
        assertEquals(expectedUrl, url);
    }


    protected void checkStructure(Map<String, Object> parameters, String expectedUrl) {
        printMap(structure);
        String url = urlParser.parseTest(parameters, structure);
        System.out.println(url);
        System.out.println(expectedUrl);
        assertEquals(expectedUrl, url);
    }
}
