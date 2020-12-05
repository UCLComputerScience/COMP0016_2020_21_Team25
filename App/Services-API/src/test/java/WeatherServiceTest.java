import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class WeatherServiceTest extends AbstractServiceTest {
    protected HashMap<String, String> inputData;
    private final String URL = "https://api.openweathermap.org/data/2.5/weather?q={CITY_NAME},{COUNTRY_CODE}&lang={LANGUAGE}&appid=a19ed1a7f194054f0458cb07ba18c0c3";

    protected void runValid() {
        defaultTest();
        cityTest();
        countryTest();
        languageTest();
        countryAndLanguageTest();
    }

    protected String defaultURL(String URL) {
        String testURL = URL;
        if (URL.contains("{CITY_NAME}")) {
            testURL = URL.replace("{CITY_NAME}", "London");
        }
        if (URL.contains("{COUNTRY_CODE}")) {
            testURL = testURL.replace("{COUNTRY_CODE}", "uk");
        }
        if (URL.contains("{LANGUAGE}")) {
            testURL = testURL.replace("{LANGUAGE}", "en");
        }
        return testURL;
    }

    @Before
    public void setupData() {
        inputData = new HashMap<>();
    }

    @Test
    // Run test with default data
    public void defaultTest() {
        test("Weather", inputData, URL);
    }

    @Test
    // Run test with a different city
    public void cityTest() {
        inputData.clear();
        String cityName = "Brighton";
        inputData.put("CITY_NAME", cityName);
        String testURL = URL.replace("{CITY_NAME}", cityName);
        test("Weather", inputData, testURL);
    }

    @Test
    // Run test with a different language
    public void languageTest() {
        inputData.clear();
        String language = "es";
        inputData.put("LANGUAGE", language);
        String testURL = URL.replace("{LANGUAGE}", language);
        test("Weather", inputData, testURL);
    }

    @Test
    // Run test with a different city and country
    public void countryTest() {
        inputData.clear();
        String cityName = "Madrid";
        String countryCode = "es";
        inputData.put("CITY_NAME", cityName);
        inputData.put("COUNTRY_CODE", countryCode);
        String testURL = URL.replace("{CITY_NAME}", cityName);
        testURL = testURL.replace("{COUNTRY_CODE}", countryCode);
        test("Weather", inputData, testURL);
    }

    @Test
    // Run test with a different city, country and language
    public void countryAndLanguageTest() {
        inputData.clear();
        String cityName = "Rome";
        String countryCode = "it";
        String language = "it";
        inputData.put("CITY_NAME", cityName);
        inputData.put("COUNTRY_CODE", countryCode);
        inputData.put("LANGUAGE", language);
        String testURL = URL.replace("{CITY_NAME}", cityName);
        testURL = testURL.replace("{COUNTRY_CODE}", countryCode);
        testURL = testURL.replace("{LANGUAGE}", language);
        test("Weather", inputData, testURL);
    }

    protected void runErroneous() {
        invalidCity();
        invalidCountry();
        invalidLanguage();
        invalidCityAndCountry();
        invalidCityAndLanguage();
        invalidCountryAndLanguage();
        invalidAll();
    }

    @Test
    // Make request with an invalid city name
    public void invalidCity() {
        inputData.clear();
        String cityName = "randomcity";
        inputData.put("CITY_NAME", cityName);
        String testURL = URL.replace("{CITY_NAME}", cityName);
        test("Weather", inputData, testURL);
    }

    @Test
    // Make request with an invalid country code
    public void invalidCountry() {
        inputData.clear();
        String countryCode = "aldskl";
        inputData.put("COUNTRY_CODE", countryCode);
        String testURL = URL.replace("{COUNTRY_CODE}", countryCode);
        test("Weather", inputData, testURL);
    }

    @Test
    // Make request with an invalid language code
    public void invalidLanguage() {
        inputData.clear();
        String language = "randomlanguage";
        inputData.put("LANGUAGE", language);
        String testURL = URL.replace("{LANGUAGE}", language);
        test("Weather", inputData, testURL);
    }

    @Test
    // Make request with an invalid city name and country code
    public void invalidCityAndCountry() {
        inputData.clear();
        String cityName = "randomcity";
        String countryCode = "randomcountry";
        inputData.put("CITY_NAME", cityName);
        inputData.put("COUNTRY_CODE", countryCode);
        String testURL = URL.replace("{CITY_NAME}", cityName);
        testURL = testURL.replace("{COUNTRY_CODE}", countryCode);
        test("Weather", inputData, testURL);
    }

    @Test
    // Make request with an invalid city name and language
    public void invalidCityAndLanguage() {
        inputData.clear();
        String cityName = "randomcity";
        String language = "randomlanguage";
        inputData.put("CITY_NAME", cityName);
        inputData.put("LANGUAGE", language);
        String testURL = URL.replace("{CITY_NAME}", cityName);
        testURL = testURL.replace("{LANGUAGE}", language);
        test("Weather", inputData, testURL);
    }

    @Test
    // Make request with an invalid country code and language
    public void invalidCountryAndLanguage() {
        inputData.clear();
        String countryCode = "randomcountry";
        String language = "randomlanguage";
        inputData.put("COUNTRY_CODE", countryCode);
        inputData.put("LANGUAGE", language);
        String testURL = URL.replace("{COUNTRY_CODE}", countryCode);
        testURL = testURL.replace("{LANGUAGE}", language);
        test("Weather", inputData, testURL);
    }

    @Test
    // Make request with an invalid city name, country code and language
    public void invalidAll() {
        inputData.clear();
        String cityName = "randomcity";
        String countryCode = "randomcountry";
        String language = "randomlanguage";
        inputData.put("CITY_NAME", cityName);
        inputData.put("COUNTRY_CODE", countryCode);
        inputData.put("LANGUAGE", language);
        String testURL = URL.replace("{CITY_NAME}", cityName);
        testURL = testURL.replace("{COUNTRY_CODE}", countryCode);
        testURL = testURL.replace("{LANGUAGE}", language);
        test("Weather", inputData, testURL);
    }
}
