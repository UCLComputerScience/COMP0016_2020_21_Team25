# Service API
The service API package defines and handles the interaction between the app and external RESTful APIs over HTTPS. It also performs its own error handling and output parsing.

## Interface

The `makeRequest` method takes the service name and required data as parameters and performs the API request, returning a string of output which is a sentence for the speech synthesiser to speak aloud to the user (with the relevant data included).

`String makeRequest(String serviceName, HashMap<String, String> payload)`

The `testRequest` method takes the same parameters as `makeRequest` but returns a `HashMap<String, Object>` representing the JSON response from the API (useful for debugging).

The recognised service names are:
- [_"Weather"_](#weather-api)
- [_"Stocks"_](#stocks-api)
- [_"Joke"_](#joke-api)

The following section defines the required parameters needed by each service. See the [_"Adding Services"_](#adding-services) section for more information on how to integrate new services.

## API Formats

The following set of tables define the required data needed by each service. The internal structure of a `REST`ful
service API request is a `HashMap` where the **Attribute** columns below represent the <ins>exact</ins> field names in
the hash map for a service.

### Weather API

A service allowing the user to retrieve information about the current weather in their (or explicitly specified) city.

| Attribute | Type   | Default | Description                                                         |
|:-----------:|--------|:---------:|------------------------------------------------------------|
| `CITY_NAME`  | String | `"London"`| The name of the city the user's device is located in.               |
| `COUNTRY_CODE`  | String |  `"uk"` |The two-character ISO country code of the city. The full list of possible country codes can be found [here](https://countrycode.org/).           |
| `LANGUAGE`  | String | `"en"` | The preferred language the user wants the output to be returned in. Uses the same two-character ISO country code format as `COUNTRY_CODE`.  |

### Stocks API

A service allowing the user to retrieve stock information on an equity of their choice.

| Attribute | Type   | Default | Description                                                         |
|:-----------:|--------|:---------:|------------------------------------------------------------|
| `FUNCTION`  | String | `"TIME_SERIES_INTRADAY"`| The time series of your choice.               |
| `SYMBOL`  | String |  `"IBM"` | The name of the equity.               |
| `INTERVAL`  | String | `"60"` | Time interval between two consecutive data points in the time series. The following values are supported: `1`, `5`, `15`, `30`, `60`. |

### Joke API

A service retrieving a random joke in a given category.

| Attribute | Type   | Default | Description                                                         |
|:-----------:|--------|:---------:|------------------------------------------------------------|
| `CATEGORY`  | String | `"ANY"`| The joke category of your choice.|

## Adding Services

The service interaction is highly extensible. A service must extend the abstract `ServiceRequest` class, providing the following attributes to the superclass:
- `URL` - The URL where the resource provided by the API is located.
  - If the URL requires any named parameters, they are to be added in the following format:
    - _e.g. the [Weather API](#weather-api) requires a `lang` attribute: `...lang={LANGUAGE}` as per the **Attribute** name `LANGUAGE` in its API format._
- `name` - The name of the service - **must be unique**.
- `category` - The category the service falls under.
- `APIKey` - A unique string used to access the service's API - this is obtained by registering for one on the API's website. Pass an empty string if it is not required by the service.
- `payload` - A `HashMap` containing the parameters required to perform the API request. The necessary keys (and values) for each service are defined in the section [above](#api-formats).

Note that the concrete service class must only take one parameter - the `payload`. The concrete constructor must be of the form:

```
public NewServiceRequest(HashMap<String, String> payload) {
    super("URL_HERE", "NAME_HERE", "CATEGORY_HERE", "API_Key_HERE", payload);
}
```

And will then be called by the `ServiceModel` as `new NewServiceRequest(payload);`

It must also define the following methods:
- `parseOutput(HashMap<String, Object> response);` - Defines how each service interprets its output from the API.
- `String handleErrors(HashMap<String, Object> response);` - Defines how each service interprets error messages from the API.

- `String getErrorCode(HashMap<String, Object> response);` - Defines how the HTTP error code is represented and retrieved for each service. Each service API will have a different way of representing HTTP error codes.

- `HashMap<String, String> populatePayload();` - Inserts default data into the payload if not given to avoid malformed requests. This method is what applies the attributes from the API format to a service.

The service can then be called by adding its name to the `ServiceMapper` switch statement by adding a new case for its `name` attribute in lowercase and returning a new object of the service (which takes the `payload` as its only parameter). No other code interaction needs to take place.    

The service should be placed in the `services` package and its API format should be listed in the ["API Format"](#api-formats) section with its description.