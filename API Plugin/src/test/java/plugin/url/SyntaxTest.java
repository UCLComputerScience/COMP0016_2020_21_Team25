package plugin.url;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Test the URL parser can parse JSON files and reject malformed schemas
 */
public class SyntaxTest extends AbstractUrlParserTest {

    @Override
    public void setServiceName() {
        setServiceName("joke");
    }

    @Test(expected = RuntimeException.class)
    public void unknownService() {
        checkIO("unknown", parameters, "");
    }

    @Test(expected = RuntimeException.class)
    public void malformedSchema() {
        checkIO("malformed", parameters, "");
    }

    @Test
    public void validService() {
        parameters.put("term", "something");
        checkIO("joke", parameters, "https://icanhazdadjoke.com/search?term=something&apiKey=" + jokeApiKey);
    }

    /**
     * URL is a required parameter field.
     */
    @Test(expected = RuntimeException.class)
    public void noUrl() {
        structure.remove("url");
        checkStructure(parameters, "");
    }

    @Test
    public void urlWithParamsAlreadyAdded() {
        parameters.put("term", "something");
        structure.put("url", "https://icanhazdadjoke.com/search?some_param=some_value");
        checkStructure(parameters, "https://icanhazdadjoke.com/search?term=something&apiKey=" + jokeApiKey);
    }

    /**
     * Parameters is a required parameter field.
     */
    @Test(expected = RuntimeException.class)
    public void noParamField() {
        structure.remove("parameters");
        checkStructure(parameters, "");
    }

    /**
     * Message is a required parameter field.
     */
    @Test(expected = RuntimeException.class)
    public void noMessage() {
        structure.remove("message");
        checkStructure(parameters, "");
    }

    @Test
    public void noApiKeyData() {
        parameters.put("term", "something");
        structure.remove("api-key");
        checkStructure(parameters, "https://icanhazdadjoke.com/search?term=something");
    }

    @Test
    public void emptyApiKeyData() {
        parameters.put("term", "something");
        Map<String, String> apiKeyData = new HashMap<>();
        structure.put("api-key", apiKeyData);
        checkStructure(parameters, "https://icanhazdadjoke.com/search?term=something");
    }

    @Test
    public void apiKeyNoAlias() {
        parameters.put("term", "something");
        Map<String, String> apiKeyData = new HashMap<>();
        apiKeyData.put("value", jokeApiKey);
        structure.put("api-key", apiKeyData);
        checkStructure(parameters, "https://icanhazdadjoke.com/search?term=something&api-key=" + jokeApiKey);
    }

    @Test
    public void apiKeyWithAlias() {
        parameters.put("term", "something");
        Map<String, String> apiKeyData = new HashMap<>();
        apiKeyData.put("value", jokeApiKey);
        apiKeyData.put("alias", "apiKey");
        structure.put("api-key", apiKeyData);
        checkStructure(parameters, "https://icanhazdadjoke.com/search?term=something&apiKey=" + jokeApiKey);
    }

    @Test
    public void apiKeyBlankAlias() {
        parameters.put("term", "something");
        Map<String, String> apiKeyData = new HashMap<>();
        apiKeyData.put("value", jokeApiKey);
        apiKeyData.put("alias", "");
        structure.put("api-key", apiKeyData);
        checkStructure(parameters, "https://icanhazdadjoke.com/search?term=something&api-key=" + jokeApiKey);
    }


    @Test(expected = RuntimeException.class)
    public void apiKeyNoValue() {
        parameters.put("term", "something");
        Map<String, String> apiKeyData = new HashMap<>();
        apiKeyData.put("alias", "apiKey");
        structure.put("api-key", apiKeyData);
        checkStructure(parameters, "");
    }

    @Test(expected = RuntimeException.class)
    public void tooManyApiKeyFields() {
        parameters.put("term", "something");
        Map<String, String> apiKeyData = new HashMap<>();
        apiKeyData.put("alias", "apiKey");
        apiKeyData.put("value", "some_value");
        apiKeyData.put("field", "value");
        structure.put("api-key", apiKeyData);
        checkStructure(parameters, "");
    }

    @Test
    public void emptyParams() {
        structure.put("parameters", new HashMap<>());
        checkStructure(parameters, "https://icanhazdadjoke.com/search?apiKey=" + jokeApiKey);
    }

    @Test
    public void paramWithAlias() {
        parameters.put("test", "something");
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("alias", "test");
        paramData.put("term", param);
        structure.put("parameters", paramData);
        checkStructure(parameters, "https://icanhazdadjoke.com/search?test=something&apiKey=" + jokeApiKey);
    }

    @Test
    public void paramBlankAlias() {
        parameters.put("term", "something");
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("alias", "");
        paramData.put("term", param);
        structure.put("parameters", paramData);
        checkStructure(parameters, "https://icanhazdadjoke.com/search?term=something&apiKey=" + jokeApiKey);
    }

    @Test
    public void paramAliasShorthand() {
        parameters.put("test", "something");
        Map<String, Object> paramData = new HashMap<>();
        paramData.put("term", "test");
        structure.put("parameters", paramData);
        checkStructure(parameters, "https://icanhazdadjoke.com/search?test=something&apiKey=" + jokeApiKey);
    }

    @Test
    public void paramWithDefaultAndNoValue() {
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("default", "something");
        paramData.put("term", param);
        structure.put("parameters", paramData);
        checkStructure(parameters, "https://icanhazdadjoke.com/search?term=something&apiKey=" + jokeApiKey);
    }

    /**
     * Non-required parameters are ignored if no default and no value supplied.
     */
    @Test
    public void paramWithNoDefaultAndNoValue() {
        Map<String, Object> paramData = new HashMap<>();
        paramData.put("term", "term");
        structure.put("parameters", paramData);
        checkStructure(parameters, "https://icanhazdadjoke.com/search?apiKey=9728c23d120f4d1985ff2a7cc019bd96");
    }

    @Test
    public void paramWithDefaultAndValue() {
        parameters.put("term", "something-else");
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("default", "something");
        paramData.put("term", param);
        structure.put("parameters", paramData);
        checkStructure(parameters, "https://icanhazdadjoke.com/search?term=something-else&apiKey=" + jokeApiKey);
    }

    @Test
    public void paramWithBlankDefaultAndValue() {
        parameters.put("term", "");
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("default", "");
        paramData.put("term", param);
        structure.put("parameters", paramData);
        checkStructure(parameters, "https://icanhazdadjoke.com/search?apiKey=" + jokeApiKey);
    }

    @Test
    public void paramWithBlankDefaultAndNoValue() {
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("default", "");
        paramData.put("term", param);
        structure.put("parameters", paramData);
        checkStructure(parameters, "https://icanhazdadjoke.com/search?apiKey=" + jokeApiKey);
    }

    @Test(expected = RuntimeException.class)
    public void requiredParamWithBlankDefaultAndNoValue() {
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("default", "");
        param.put("required", true);
        paramData.put("term", param);
        structure.put("parameters", paramData);
        checkStructure(parameters, "https://icanhazdadjoke.com/search?apiKey=" + jokeApiKey);
    }

    @Test
    public void requiredParamWithDefaultAndNoValue() {
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("default", "something");
        param.put("required", true);
        paramData.put("term", param);
        structure.put("parameters", paramData);
        checkStructure(parameters, "https://icanhazdadjoke.com/search?term=something&apiKey=" + jokeApiKey);
    }

    @Test(expected = RuntimeException.class)
    public void requiredParamNoDefaultNoValue() {
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("required", true);
        paramData.put("term", param);
        structure.put("parameters", paramData);
        checkStructure(parameters, "");
    }

    @Test(expected = RuntimeException.class)
    public void requiredParamNotBoolean() {
        Map<String, Object> paramData = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        param.put("required", "not a boolean!");
        paramData.put("term", param);
        structure.put("parameters", paramData);
        checkStructure(parameters, "");
    }

    @Test
    public void twoParams() {
        parameters.put("test", "something");
        parameters.put("param", "value");
        Map<String, Object> paramData = new HashMap<>();
        paramData.put("term", "test");
        paramData.put("param", "param");
        structure.put("url", "api.testUrl");
        structure.put("parameters", paramData);
        structure.remove("api-key");
        checkStructure(parameters, "api.testUrl?param=value&test=something");
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
        structure.put("url", "api.testUrl");
        structure.put("parameters", paramData);
        structure.remove("api-key");
        checkStructure(parameters, "api.testUrl?param=value&another=test&test=something");
    }

    @Test
    public void endpointWithValue() {
        parameters.put("id", "1234");
        parameters.put("term", "something");
        ArrayList<Map<String, Object>> endpoints = new ArrayList<>();
        Map<String, Object> idEndpoint = new HashMap<>();
        idEndpoint.put("name", "id");
        endpoints.add(idEndpoint);
        structure.put("endpoints", endpoints);
        structure.put("url", "api.testUrl");
        structure.remove("api-key");
        checkStructure(parameters, "api.testUrl/1234?term=something");
    }

    @Test
    public void endpointWithTrailingSlash() {
        parameters.put("id", "1234");
        parameters.put("term", "something");
        ArrayList<Map<String, Object>> endpoints = new ArrayList<>();
        Map<String, Object> idEndpoint = new HashMap<>();
        idEndpoint.put("name", "id");
        endpoints.add(idEndpoint);
        structure.put("endpoints", endpoints);
        structure.put("url", "api.testUrl/");
        structure.remove("api-key");
        checkStructure(parameters, "api.testUrl/1234?term=something");
    }

    @Test(expected = RuntimeException.class)
    public void endpointNoDefaultNoValue() {
        parameters.put("term", "something");
        ArrayList<Map<String, Object>> endpoints = new ArrayList<>();
        Map<String, Object> idEndpoint = new HashMap<>();
        idEndpoint.put("name", "id");
        endpoints.add(idEndpoint);
        structure.put("endpoints", endpoints);
        structure.put("url", "api.testUrl");
        structure.remove("api-key");
        checkStructure(parameters, "api.testUrl/1234?term=something");
    }
    @Test(expected = RuntimeException.class)
    public void endpointNoName() {
        parameters.put("term", "something");
        ArrayList<Map<String, Object>> endpoints = new ArrayList<>();
        Map<String, Object> idEndpoint = new HashMap<>();
        endpoints.add(idEndpoint);
        structure.put("endpoints", endpoints);
        structure.put("url", "api.testUrl");
        structure.remove("api-key");
        checkStructure(parameters, "api.testUrl/1234?term=something");
    }

    @Test(expected = RuntimeException.class)
    public void endpointBlankName() {
        parameters.put("term", "something");
        ArrayList<Map<String, Object>> endpoints = new ArrayList<>();
        Map<String, Object> idEndpoint = new HashMap<>();
        idEndpoint.put("name", "");
        endpoints.add(idEndpoint);
        structure.put("endpoints", endpoints);
        structure.put("url", "api.testUrl");
        structure.remove("api-key");
        checkStructure(parameters, "api.testUrl/1234?term=something");
    }

    @Test
    public void endpointDefaultNoValue() {
        parameters.put("term", "something");
        ArrayList<Map<String, Object>> endpoints = new ArrayList<>();
        Map<String, Object> idEndpoint = new HashMap<>();
        idEndpoint.put("default", "default");
        idEndpoint.put("name", "id");
        endpoints.add(idEndpoint);
        structure.put("endpoints", endpoints);
        structure.put("url", "api.testUrl");
        structure.remove("api-key");
        checkStructure(parameters, "api.testUrl/default?term=something");
    }

    @Test(expected = RuntimeException.class)
    public void endpointBlankDefaultBlankValue() {
        parameters.put("id", "");
        parameters.put("term", "something");
        ArrayList<Map<String, Object>> endpoints = new ArrayList<>();
        Map<String, Object> idEndpoint = new HashMap<>();
        idEndpoint.put("name", "id");
        idEndpoint.put("default", "");
        endpoints.add(idEndpoint);
        structure.put("endpoints", endpoints);
        structure.put("url", "api.testUrl");
        structure.remove("api-key");
        checkStructure(parameters, "api.testUrl/1234?term=something");
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
        structure.put("endpoints", endpoints);
        structure.put("url", "api.testUrl");
        structure.remove("api-key");
        checkStructure(parameters, "api.testUrl/1234?term=something");
    }

    @Test
    public void multipleEndpoints() {

    }
}
