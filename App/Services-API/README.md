# API Formats

The following set of tables define the required data needed by each service. The internal structure of a `REST`ful
service API request is a `HashMap` where the **Attribute** columns below represent the <ins>exact</ins> field names in
the hash map for a service.

## Weather API

A service allowing the user to retrieve information about the current weather in their (or explicitly specified) city.

| Attribute | Type   | Default | Description                                                         |
|:-----------:|--------|:---------:|------------------------------------------------------------|
| `CITY_NAME`  | String | `"London"`| The name of the city the user's device is located in.               |
| `COUNTRY_CODE`  | String |  `"uk"` |The two-character ISO country code of the city. The full list of possible country codes can be found [here](https://countrycode.org/)            |
| `LANGUAGE`  | String | `"en"` | The preferred language the user wants the output to be returned in. Uses the same two-character ISO country code format as `COUNTRY_CODE`  |

## Stocks API

A service allowing the user to retrieve stock information on an equity of their choice.

| Attribute | Type   | Default | Description                                                         |
|:-----------:|--------|:---------:|------------------------------------------------------------|
| `FUNCTION`  | String | `"TIME_SERIES_INTRADAY"`| The time series of your choice.               |
| `SYMBOL`  | String |  `"IBM"` | The name of the equity.               |
| `INTERVAL`  | String | `"60"` | Time interval between two consecutive data points in the time series. The following values are supported: `1`, `5`, `15`, `30`, `60`. |
