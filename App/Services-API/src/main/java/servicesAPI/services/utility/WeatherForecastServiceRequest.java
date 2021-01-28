package servicesAPI.services.utility;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;

public class WeatherForecastServiceRequest extends AbstractWeatherServiceRequest {
    private final String[] temperatureParams = new String[]{"day", "min", "max"};

    public WeatherForecastServiceRequest(HashMap<String, String> payload) {
        super("Weather Forecast",
                "https://api.openweathermap.org/data/2.5/onecall?lat={LAT}&lon={LON}&exclude=current,minutely,hourly&lang={LANGUAGE}", payload);
    }

    @Override
    protected String parseOutput(HashMap<String, Object> response) {
        ArrayList<HashMap<String, Object>> weatherData = (ArrayList<HashMap<String, Object>>) response.get("daily");
        int days = Integer.parseInt(payload.get("DAYS"));

        if (weatherData.size() == 0) {
            return "I'm sorry, I couldn't find any weather forecast information for you.";
        }

        StringBuilder output = new StringBuilder();
        for (int i = 1; i < days + 1; i++) {
            HashMap<String, Object> weather = weatherData.get(i);
            ArrayList<HashMap<String, Object>> mainData = (ArrayList<HashMap<String, Object>>) weather.get("weather");
            HashMap<String, Object> main = mainData.get(0);
            HashMap<String, Object> temperatureData = (HashMap<String, Object>) weather.get("temp");
            String sentence = "The weather this ";
            Integer epochTime = (Integer) weather.get("dt");
            sentence += getDate(epochTime);
            sentence += " will be {description} with the temperature being {day} degrees celsius but will probably feel like {feels_like} degrees celsius. The high will be {max} degrees celsius and the low, {min} degrees celsius.";
            sentence = replaceParameter(sentence, "description", main);
            HashMap<String, Object> feelsLikeData = (HashMap<String, Object>) weather.get("feels_like");
            double feelsLike = (double) feelsLikeData.get("day");
            sentence = sentence.replace("{feels_like}", kelvinToDegrees(feelsLike));
            for (String param : temperatureParams) {
                sentence = convertTemp(sentence, param, temperatureData);
            }
            String temperatureStr = kelvinToDegrees((Double) temperatureData.get("day"));
            int temperature = Integer.parseInt(temperatureStr);
            if (temperature < 11.0) {
                sentence += " Don't forget to dress warm!";
            } else if (temperature > 20.0) {
                sentence += " It's going to be quite warm, remember to stay hydrated!";
            }
            output.append(sentence);
            output.append("\n");
        }
        return output.toString().trim();
    }

    private String getDate(Integer epochTime) {
        long time = (long) epochTime;
        DayOfWeek dayOfWeek = Instant.ofEpochSecond(time).atOffset(ZoneOffset.UTC).getDayOfWeek();
        return dayOfWeek.name().toLowerCase();
    }

    @Override
    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> servicePayload = new HashMap<>();
        servicePayload.put("LAT", "51.534121");
        servicePayload.put("LON", "-0.006");
        servicePayload.put("LANGUAGE", "en");
        servicePayload.put("DAYS", "1");
        return servicePayload;
    }
}
