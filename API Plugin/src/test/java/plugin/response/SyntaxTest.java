package plugin.response;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Test the response parser with malformed/invalid syntax in the "message" field of the JSON file
 */
public class SyntaxTest extends AbstractResponseParserTest {
    private final String defaultOutput = "An error occurred while calling the book service.\n";

    @Override
    public void setName() {
        setServiceName("book");
    }

    /**
     * Array indices must only be integers.
     */
    @Test(expected = RuntimeException.class)
    public void nonIntegerArrayIndex() {
        String message = "test {results[test]}";
        structure.put("message", message);
        check(serviceName, structure, defaultOutput, metadata, false);
    }

    /**
     * Parser should be able to extract a top level parameter
     */
    @Test
    public void topLevelParameter() {
        String message = "{count}";
        structure.put("message", message);
        String expected = "64516";
        check(serviceName, structure, expected, metadata, false);
    }

    /**
     * Parser should be able to parse array notation.
     */
    @Test
    public void arrayNotation() {
        setServiceName("joke");
        String message = "{results[0]}";
        structure.put("message", message);
        String expected = "{id=M7wPC5wPKBd, joke=Did you hear the one about the guy with the broken hearing aid? Neither did he.}";
        check("joke", structure, expected, metadata, false);
    }

    /**
     * The parser should ignore unknown fields.
     */
    @Test
    public void unknownMap() {
        setServiceName("joke");
        String message = "{results[0].foo}";
        structure.put("message", message);
        String expected = "";
        check("joke", structure, expected, metadata, false);
    }

    /**
     * Invalid syntax should result in an error.
     */
    @Test
    public void invalidMapNotation() {
        setServiceName("joke");
        String message = "{.}";
        structure.put("message", message);
        String expected = "An error occurred while calling the joke service.";
        check("joke", structure, expected, metadata, false);
    }

    /**
     * Invalid syntax should result in an error.
     */
    @Test
    public void MapNotationNoChild() {
        setServiceName("joke");
        String message = "{result[0].}";
        structure.put("message", message);
        String expected = "An error occurred while calling the joke service.";
        check("joke", structure, expected, metadata, false);
    }

    /**
     * Invalid syntax should result in an error.
     */
    @Test
    public void MapNotationNoParent() {
        setServiceName("current-weather");
        String message = "{.lon}";
        structure.put("message", message);
        String expected = "An error occurred while calling the current-weather service.";
        check("current-weather", structure, expected, metadata, false);
    }

    /**
     * Parser should be able to add arrays to metadata.
     */
    @Test
    public void AddArrayToMetadata() {
        setServiceName("current-weather");
        Map<String, Object> metadataStructure = new HashMap<>();
        metadataStructure.put("weather[0]", "weather[0]");
        structure.put("metadata", metadataStructure);
        Map<String, Object> weather = new HashMap<>();
        weather.put("id", 800);
        weather.put("main", "Clear");
        weather.put("description", "clear sky");
        weather.put("icon", "01d");
        metadata.put("weather[0]", weather);
        check("current-weather", structure, defaultOutput, metadata, true);
    }

    /**
     * Just for code coverage, parser should return its own default error message
     */
    @Test
    public void errorResponseNoErrorCodeName() {
        String message = "{invalid}";
        structure.put("message", message);
        check(serviceName, structure, "An error occurred while calling the book service.",
                metadata, false);
    }

    /**
     * Returning the default error message for an error response.
     */
    @Test
    public void errorResponseDefaultCodeOnly() {
        setServiceName("current-weather");
        String message = "{invalid}";
        structure.put("message", message);
        HashMap<String, Object> errorMessages = new HashMap<>();
        errorMessages.put("default", "error");
        structure.put("error_messages", errorMessages);
        structure.remove("error_code_name");
        check(serviceName, structure, "error",
                metadata, false);
    }

    /**
     * If the error_code_name field does not point to a value in the JSON response object, the error message should default to the generic one in the parser.
     */
    @Test
    public void invalidErrorCodeName() {
        setServiceName("current-weather");
        String message = "{invalid}";
        structure.put("message", message);
        structure.put("error_code_name", "code");
        structure.remove("error_messages");
        check(serviceName, structure, "An error occurred while calling the current-weather service.",
                metadata, false);
    }

    /**
     * Ensure that the parser can use the service name specified in the JSON file.
     */
    @Test
    public void customServiceNameTest() {
        setServiceName("current-weather");
        structure.put("name", "CUSTOM SERVICE");
        String message = "{invalid}";
        structure.put("message", message);
        structure.put("error_code_name", "code");
        structure.remove("error_messages");
        check(serviceName, structure, "An error occurred while calling the CUSTOM SERVICE service.",
                metadata, false);
    }

    /**
     * Providing a "name" attribute but leaving the value as the empty string should default the service name to the filename.
     */
    @Test
    public void blankServiceNameTest() {
        setServiceName("current-weather");
        structure.put("name", "");
        String message = "{invalid}";
        structure.put("message", message);
        structure.put("error_code_name", "code");
        structure.remove("error_messages");
        check(serviceName, structure, "An error occurred while calling the current-weather service.",
                metadata, false);
    }
}
