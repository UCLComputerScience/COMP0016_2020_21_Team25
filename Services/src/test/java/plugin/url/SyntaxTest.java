package plugin.url;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Test the URL parser can parse JSON files and reject malformed schemas
 */
public class SyntaxTest extends AbstractUrlParserTest {

    @Test
    public void unknownService() throws IOException {
        FileNotFoundException thrown = assertThrows(FileNotFoundException.class,
                () -> checkIO("unknown", parameters, ""), "Expected FileNotFoundException");
    }

    @Test
    public void malformedSchema() throws IOException {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> checkIO("malformed", parameters, ""), "Expected FileNotFoundException");
    }

    @Test
    public void validService() throws IOException {
        parameters.put("term", "something");
        checkIO("joke", parameters, "https://icanhazdadjoke.com/search?term=something&apiKey=" + jokeApiKey);
    }

    /**
     * URL is a required parameter field.
     */
    @Test
    public void noUrl() {
        schema.remove("url");
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> checkSchema(parameters, ""), "Expected RuntimeException");
    }

    @Test
    public void urlWithParamsAlreadyAdded() {
        parameters.put("term", "something");
        String url = "https://icanhazdadjoke.com/search?term=something&apiKey=" + jokeApiKey;
        schema.put("url", "https://icanhazdadjoke.com/search?some_param=some_value");
        checkSchema(parameters, url);
    }

    /**
     * Parameters is a required parameter field.
     */
    @Test
    public void noParamField() {
        schema.remove("parameters");
        String url = "";
        schema.put("url", "https://icanhazdadjoke.com/search?some_param=some_value");
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> checkSchema(parameters, url),
                "Expected RuntimeException");
    }

    /**
     * Message is a required parameter field.
     */
    @Test
    public void noMessage() {
        schema.remove("message");
        String url = "";
        schema.put("url", "https://icanhazdadjoke.com/search?some_param=some_value");
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> checkSchema(parameters, url),
                "Expected RuntimeException");
    }

    @Test
    public void noApiKeyData() {
        parameters.put("term", "something");
        schema.remove("api-key");
        checkSchema(parameters, "https://icanhazdadjoke.com/search?term=something");
    }

    @Test
    public void emptyApiKeyData() {
        parameters.put("term", "something");
        Map<String, String> apiKeyData = new HashMap<>();
        schema.put("api-key", apiKeyData);
        checkSchema(parameters, "https://icanhazdadjoke.com/search?term=something");
    }

    @Test
    public void apiKeyNoAlias() {
        parameters.put("term", "something");
        Map<String, String> apiKeyData = new HashMap<>();
        apiKeyData.put("value", jokeApiKey);
        schema.put("api-key", apiKeyData);
        checkSchema(parameters, "https://icanhazdadjoke.com/search?term=something&api-key=" + jokeApiKey);
    }

    @Test
    public void apiKeyWithAlias() {
        parameters.put("term", "something");
        Map<String, String> apiKeyData = new HashMap<>();
        apiKeyData.put("value", jokeApiKey);
        apiKeyData.put("alias", "apiKey");
        schema.put("api-key", apiKeyData);
        checkSchema(parameters, "https://icanhazdadjoke.com/search?term=something&apiKey=" + jokeApiKey);
    }

    @Test
    public void apiKeyBlankAlias() {
        parameters.put("term", "something");
        Map<String, String> apiKeyData = new HashMap<>();
        apiKeyData.put("value", jokeApiKey);
        apiKeyData.put("alias", "");
        schema.put("api-key", apiKeyData);
        checkSchema(parameters, "https://icanhazdadjoke.com/search?term=something&api-key=" + jokeApiKey);
    }

    @Test
    public void apiKeyNoValue() {
        parameters.put("term", "something");
        Map<String, String> apiKeyData = new HashMap<>();
        apiKeyData.put("alias", "apiKey");
        schema.put("api-key", apiKeyData);
        String url = "";
        schema.put("url", "https://icanhazdadjoke.com/search?some_param=some_value");
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> checkSchema(parameters, url),
                "Expected RuntimeException");
        
    }

    @Test
    public void tooManyApiKeyFields() {
        parameters.put("term", "something");
        Map<String, String> apiKeyData = new HashMap<>();
        apiKeyData.put("alias", "apiKey");
        apiKeyData.put("value", "some_value");
        apiKeyData.put("field", "value");
        schema.put("api-key", apiKeyData);
        String url = "";
        schema.put("url", "https://icanhazdadjoke.com/search?some_param=some_value");
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> checkSchema(parameters, url),
                "Expected RuntimeException");
    }

    @Test
    public void emptyParams() {
        schema.put("parameters", new HashMap<>());
        checkSchema(parameters, "https://icanhazdadjoke.com/search?apiKey=" + jokeApiKey);
    }

    @Test
    public void paramWithAlias() {
        parameters.put("test", "something");
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("alias", "test");
        paramData.put("term", param);
        schema.put("parameters", paramData);
        checkSchema(parameters, "https://icanhazdadjoke.com/search?test=something&apiKey=" + jokeApiKey);
    }

    @Test
    public void paramBlankAlias() {
        parameters.put("term", "something");
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("alias", "");
        paramData.put("term", param);
        schema.put("parameters", paramData);
        checkSchema(parameters, "https://icanhazdadjoke.com/search?term=something&apiKey=" + jokeApiKey);
    }

    @Test
    public void paramAliasShorthand() {
        parameters.put("test", "something");
        Map<String, Object> paramData = new HashMap<>();
        paramData.put("term", "test");
        schema.put("parameters", paramData);
        checkSchema(parameters, "https://icanhazdadjoke.com/search?test=something&apiKey=" + jokeApiKey);
    }

    @Test
    public void paramWithDefaultAndNoValue() {
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("default", "something");
        paramData.put("term", param);
        schema.put("parameters", paramData);
        checkSchema(parameters, "https://icanhazdadjoke.com/search?term=something&apiKey=" + jokeApiKey);
    }

    /**
     * Non-required parameters are ignored if no default and no value supplied.
     */
    @Test
    public void paramWithNoDefaultAndNoValue() {
        Map<String, Object> paramData = new HashMap<>();
        paramData.put("term", "term");
        schema.put("parameters", paramData);
        checkSchema(parameters, "https://icanhazdadjoke.com/search?apiKey=9728c23d120f4d1985ff2a7cc019bd96");
    }

    @Test
    public void paramWithDefaultAndValue() {
        parameters.put("term", "something-else");
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("default", "something");
        paramData.put("term", param);
        schema.put("parameters", paramData);
        checkSchema(parameters, "https://icanhazdadjoke.com/search?term=something-else&apiKey=" + jokeApiKey);
    }

    @Test
    public void paramWithBlankDefaultAndValue() {
        parameters.put("term", "");
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("default", "");
        paramData.put("term", param);
        schema.put("parameters", paramData);
        checkSchema(parameters, "https://icanhazdadjoke.com/search?apiKey=" + jokeApiKey);
    }

    @Test
    public void paramWithBlankDefaultAndNoValue() {
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("default", "");
        paramData.put("term", param);
        schema.put("parameters", paramData);
        checkSchema(parameters, "https://icanhazdadjoke.com/search?apiKey=" + jokeApiKey);
    }

    @Test
    public void requiredParamWithBlankDefaultAndNoValue() {
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("default", "");
        param.put("required", true);
        paramData.put("term", param);
        schema.put("parameters", paramData);
        String url = "https://icanhazdadjoke.com/search?apiKey=" + jokeApiKey;
        schema.put("url", "https://icanhazdadjoke.com/search?some_param=some_value");
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> checkSchema(parameters, url),
                "Expected RuntimeException");
    }

    @Test
    public void requiredParamWithDefaultAndNoValue() {
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("default", "something");
        param.put("required", true);
        paramData.put("term", param);
        schema.put("parameters", paramData);
        checkSchema(parameters, "https://icanhazdadjoke.com/search?term=something&apiKey=" + jokeApiKey);
    }

    @Test
    public void requiredParamNoDefaultNoValue() {
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("required", true);
        paramData.put("term", param);
        schema.put("parameters", paramData);
        String url = "";
        schema.put("url", "https://icanhazdadjoke.com/search?some_param=some_value");
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> checkSchema(parameters, url),
                "Expected RuntimeException");
    }

    @Test
    public void requiredParamNotBoolean() {
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("required", "not a boolean!");
        paramData.put("term", param);
        schema.put("parameters", paramData);
        String url = "";
        schema.put("url", "https://icanhazdadjoke.com/search?some_param=some_value");
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> checkSchema(parameters, url),
                "Expected RuntimeException");
    }

    @Test
    public void twoParams() {
        parameters.put("test", "something");
        parameters.put("param", "value");
        Map<String, Object> paramData = new HashMap<>();
        paramData.put("term", "test");
        paramData.put("param", "param");
        schema.put("url", "api.testUrl");
        schema.put("parameters", paramData);
        schema.remove("api-key");
        checkSchema(parameters, "api.testUrl?param=value&test=something");
    }

    @Test
    public void multipleParams() {
        parameters.put("test", "something");
        parameters.put("param", "value");
        parameters.put("another", "test");
        Map<String, Object> paramData = new HashMap<>();
        paramData.put("term", "test");
        paramData.put("param", "param");
        paramData.put("another", "another");
        schema.put("url", "api.testUrl");
        schema.put("parameters", paramData);
        schema.remove("api-key");
        checkSchema(parameters, "api.testUrl?param=value&another=test&test=something");
    }

    @Test
    public void endpointWithValue() {
        parameters.put("id", "1234");
        parameters.put("term", "something");
        ArrayList<Map<String, Object>> endpoints = new ArrayList<>();
        Map<String, Object> idEndpoint = new HashMap<>();
        idEndpoint.put("name", "id");
        endpoints.add(idEndpoint);
        schema.put("endpoints", endpoints);
        schema.put("url", "api.testUrl");
        schema.remove("api-key");
        checkSchema(parameters, "api.testUrl/1234?term=something");
    }

    @Test
    public void endpointWithTrailingSlash() {
        parameters.put("id", "1234");
        parameters.put("term", "something");
        ArrayList<Map<String, Object>> endpoints = new ArrayList<>();
        Map<String, Object> idEndpoint = new HashMap<>();
        idEndpoint.put("name", "id");
        endpoints.add(idEndpoint);
        schema.put("endpoints", endpoints);
        schema.put("url", "api.testUrl/");
        schema.remove("api-key");
        checkSchema(parameters, "api.testUrl/1234?term=something");
    }

    @Test
    public void endpointNoDefaultNoValue() {
        parameters.put("term", "something");
        ArrayList<Map<String, Object>> endpoints = new ArrayList<>();
        Map<String, Object> idEndpoint = new HashMap<>();
        idEndpoint.put("name", "id");
        endpoints.add(idEndpoint);
        schema.put("endpoints", endpoints);
        schema.put("url", "api.testUrl");
        schema.remove("api-key");
        String url = "api.testUrl/1234?term=something";
        schema.put("url", "https://icanhazdadjoke.com/search?some_param=some_value");
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> checkSchema(parameters, url),
                "Expected RuntimeException");
    }

    @Test
    public void endpointNoName() {
        parameters.put("term", "something");
        ArrayList<Map<String, Object>> endpoints = new ArrayList<>();
        Map<String, Object> idEndpoint = new HashMap<>();
        endpoints.add(idEndpoint);
        schema.put("endpoints", endpoints);
        schema.put("url", "api.testUrl");
        schema.remove("api-key");
        String url = "api.testUrl/1234?term=something";
        schema.put("url", "https://icanhazdadjoke.com/search?some_param=some_value");
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> checkSchema(parameters, url),
                "Expected RuntimeException");
    }

    @Test
    public void endpointBlankName() {
        parameters.put("term", "something");
        ArrayList<Map<String, Object>> endpoints = new ArrayList<>();
        Map<String, Object> idEndpoint = new HashMap<>();
        idEndpoint.put("name", "");
        endpoints.add(idEndpoint);
        schema.put("endpoints", endpoints);
        schema.put("url", "api.testUrl");
        schema.remove("api-key");
        String url = "api.testUrl/1234?term=something";
        schema.put("url", "https://icanhazdadjoke.com/search?some_param=some_value");
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> checkSchema(parameters, url),
                "Expected RuntimeException");
    }

    @Test
    public void endpointDefaultNoValue() {
        parameters.put("term", "something");
        ArrayList<Map<String, Object>> endpoints = new ArrayList<>();
        Map<String, Object> idEndpoint = new HashMap<>();
        idEndpoint.put("default", "default");
        idEndpoint.put("name", "id");
        endpoints.add(idEndpoint);
        schema.put("endpoints", endpoints);
        schema.put("url", "api.testUrl");
        schema.remove("api-key");
        checkSchema(parameters, "api.testUrl/default?term=something");
    }

    @Test
    public void endpointBlankDefaultBlankValue() {
        parameters.put("id", "");
        parameters.put("term", "something");
        ArrayList<Map<String, Object>> endpoints = new ArrayList<>();
        Map<String, Object> idEndpoint = new HashMap<>();
        idEndpoint.put("name", "id");
        idEndpoint.put("default", "");
        endpoints.add(idEndpoint);
        schema.put("endpoints", endpoints);
        schema.put("url", "api.testUrl");
        schema.remove("api-key");
        String url = "api.testUrl/1234?term=something";
        schema.put("url", "https://icanhazdadjoke.com/search?some_param=some_value");
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> checkSchema(parameters, url),
                "Expected RuntimeException");
    }

    @Test
    public void endpointDefaultWithBlankValue() {
        parameters.put("id", "");
        parameters.put("term", "something");
        ArrayList<Map<String, Object>> endpoints = new ArrayList<>();
        Map<String, Object> idEndpoint = new HashMap<>();
        idEndpoint.put("name", "id");
        idEndpoint.put("default", "1234");
        endpoints.add(idEndpoint);
        schema.put("endpoints", endpoints);
        schema.put("url", "api.testUrl");
        schema.remove("api-key");
        checkSchema(parameters, "api.testUrl/1234?term=something");
    }
}
