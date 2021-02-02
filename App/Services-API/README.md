# Service API

The service API package defines and handles the interaction between the app and external RESTful APIs over HTTP(S). It also performs its own error handling and output parsing.

## Interface

The `makeRequest` method takes the service name and required data as parameters and performs the API request, the output is a sentence for the speech synthesiser to speak aloud to the user (with the relevant data included).

-   This output is put onto an API response queue, which is then transferred to a queue maintained by the main controller of the app.

`String makeRequest(String serviceName, HashMap payload)`

The recognised service names are:

-   [*"air quality"*](#air-quality-api)
-   [*"current weather"*](#current-weather-api)
-   [*"dictionary"*](#dictionary-api)
-   [*"ingredient"*](#recipe-by-ingredient-api)
-   [*"joke"*](#joke-api)
-   [*"nearest transport"*](#nearest-transport-api)
-   [*"news"*](#news-api)
-   [*"random recipe"*](#random-recipe-api)
-   [*"recipe"*](#recipe-by-search-api)
-   [*"stocks"*](#stocks-api)
-   [*"transport search"*](#transport-by-search-api)
-   [*"weather forecast"*](#weather-forecast-api)

The following section defines the required parameters needed by each service. See the [*"Adding Services"*](#adding-services) section for more information on how to integrate new services.

## API Formats

The following set of tables define the required data needed by each service. The internal structure of a `REST`ful
service API request is a `HashMap` where the **Attribute** columns below represent the exact field names in
the hash map for a service.

### Current Weather API

A service allowing the user to retrieve information about the current weather in their (or explicitly specified) city.

|    Attribute   | Type   |   Default  | Description                                                                                                                                                                                             |
| :------------: | ------ | :--------: | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
|   `CITY_NAME`  | String | `"London"` | The name of the city the user's device is located in.                                                                                                                                                   |
| `COUNTRY_CODE` | String |   `"uk"`   | The two-character ISO country code of the city. The full list of possible country codes can be found [here](https://countrycode.org/).                                                                  |
|   `LANGUAGE`   | String |   `"en"`   | The two-character (or three) ISO code of the language to return the weather in. The full list of possible language codes can be found [here](https://www.loc.gov/standards/iso639-2/php/code_list.php). |

### Weather Forecast API

A service returning the predicted weather for the user's exact location.

|  Attribute | Type    |   Default   | Description                                                                                                                                                                                             |
| :--------: | ------- | :---------: | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
|    `LAT`   | Double  | `51.534121` | The latitude of the user's current (or most recent) location.                                                                                                                                           |
|    `LON`   | Double  |   `-0.006`  | The longitude of the user's current (or most recent location.                                                                                                                                           |
| `LANGUAGE` | String  |    `"en"`   | The two-character (or three) ISO code of the language to return the weather in. The full list of possible language codes can be found [here](https://www.loc.gov/standards/iso639-2/php/code_list.php). |
|   `DAYS`   | Integer |     `1`     | The number of days to forecast for. The minimum is 7 and the maximum is 7.                                                                                                                              |

### Air Quality API

A service returning the air quality index for a given city.

|  Attribute  | Type   |   Default  | Description                                           |
| :---------: | ------ | :--------: | ----------------------------------------------------- |
| `CITY_NAME` | String | `"London"` | The name of the city the user's device is located in. |

### Stocks API

A service allowing the user to retrieve stock information on an equity of their choice.

|  Attribute | Type    |          Default         | Description                                                                                                                           |
| :--------: | ------- | :----------------------: | ------------------------------------------------------------------------------------------------------------------------------------- |
| `FUNCTION` | String  | `"TIME_SERIES_INTRADAY"` | The time series of your choice.                                                                                                       |
|  `SYMBOL`  | String  |          `"IBM"`         | The name of the equity.                                                                                                               |
| `INTERVAL` | Integer |            `1`           | Time interval between two consecutive data points in the time series. The following values are supported: `1`, `5`, `15`, `30`, `60`. |

### Joke API

A service retrieving a random or specific joke.

| Attribute | Type   | Default | Description                           |
| :-------: | ------ | :-----: | ------------------------------------- |
|   `TERM`  | String |   `""`  | The search term to search for a joke. |

### Dictionary API

A service retrieving the definition, examples and synonyms for a given word.

|      Attribute     | Type    |  Default  | Description                                                                                                                                                                                                |
| :----------------: | ------- | :-------: | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
|       `WORD`       | String  | `"hello"` | The word to retrieve the definition of.                                                                                                                                                                    |
|     `LANGUAGE`     | String  |   `"en"`  | The two-character (or three) ISO code of the language to return the definition in. The full list of possible language codes can be found [here](https://www.loc.gov/standards/iso639-2/php/code_list.php). |
| `INCLUDE_SYNONYMS` | Boolean | `"false"` | Indicates whether to retrieve the synonyms of the word as well.                                                                                                                                            |
|   `SYNONYMS_ONLY`  | Boolean | `"false"` | Allows for thesaurus usage, retrieving only the synonyms of the word and not its definition and example sentence usage.                                                                                    |

### Nearest Transport API

This service returns either the nearest train stations or bus stops for a given location range.

|  Attribute  | Type   |      Default      | Description                                                                                       |
| :---------: | ------ | :---------------: | ------------------------------------------------------------------------------------------------- |
|  `MIN-LAT`  | Double |    `51.530121`    | The minimum latitude, representing one corner of the bounding box - must be less than `MAX-LAT`.  |
|  `MAX-LAT`  | Double |    `51.538121`    | The maximum latitude, representing one corner of the bounding box - must be more than `MIN-LAT`.  |
|  `MIN-LON`  | Double |     `"-0.009`     | The minimum longitude, representing one corner of the bounding box - must be less than `MAX-LON`. |
|  `MAX-LON`  | Double |      `-0.001`     | The maximum longitude, representing one corner of the bounding box - must be more than `MIN-LON`. |
| `TRANSPORT` | String | `"train_station"` | Indicates whether to search for a train station (`train_station`) or a bus stop (`bus_stop`).     |

### Transport By Search API

This service returns information on a bus stop or train station by its name.

|  Attribute  | Type   |      Default      | Description                                                                                   |
| :---------: | ------ | :---------------: | --------------------------------------------------------------------------------------------- |
|   `QUERY`   | String |     `"euston"`    | The name of the train station or bus stop to search for.                                      |
| `TRANSPORT` | String | `"train_station"` | Indicates whether to search for a train station (`train_station`) or a bus stop (`bus_stop`). |

### Random Recipe API

This service returns a random recipe.

This service requires no parameters - supplying them has no effect.

### Recipe By Search API

This service returns a set of recipes by searching via natural language.

| Attribute | Type   | Default | Description      |
| :-------: | ------ | :-----: | ---------------- |
|  `QUERY`  | String |   `""`  | The search term. |

### Recipe By Ingredient API

This service returns a set of recipes with certain ingredients, specified by the user.

|   Attribute   | Type   | Default | Description                                            |
| :-----------: | ------ | :-----: | ------------------------------------------------------ |
| `INGREDIENTS` | String |   `""`  | A comma separated string of ingredients to search for. |

### News API

This service returns a news article based on the user's search.
|   Attribute   | Type    | Default | Description                                            |
\| :-----------: \| ------- \| :-----: \| ------------------------------------------------------ \|
\| `QUERY` | String |   `""`  | The search term, in natural language. |
\| `LANGUAGE` | String |   `"en"`  | The language to return the result in. Uses the two-character IS0-639-1 code scheme. The full list of possible language codes can be found [here](https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes) \|

## Adding Services

The service interaction is highly extensible. A service must extend the abstract `ServiceRequest` class, providing the following attributes:

-   `URL` - The URL where the resource provided by the API is located.
-   If the URL requires any named parameters, they are to be added in the following format:
-   *e.g. the [Weather API](#current-weather-api) requires a `lang` attribute: `...lang={LANGUAGE}` as per the **Attribute** `LANGUAGE` in its API format.*
-   `name` - The name of the service - **must be unique**.
-   `category` - The category the service falls under.
-   `APIKey` - A unique string used to access the service's API - this is obtained by registering for one on the API's website. Pass an empty string if it is not required by the service.
-   `payload` - A `HashMap` containing the parameters required to perform the API request. The necessary keys (and values) for each service are defined in the section [above](#api-formats).

Note that the concrete service class must only take one parameter - the `payload`. The concrete constructor must be of the form:

    public NewServiceRequest(HashMap payload) {
        super("URL_HERE", "NAME_HERE", "CATEGORY_HERE", "API_Key_HERE", payload);
    }

It will then be called by the `ServiceFactory` as `new NewServiceRequest(payload);`

It must also implement the following methods:

-   `parseOutput(HashMap response);` - Defines how each service interprets its output from the API.

-   `String handleErrors(HashMap response);` - Defines how each service interprets error messages from the API.

-   `String getErrorCode(HashMap response);` - Defines how the HTTP error code is represented and retrieved for each service. Each service API will have a different way of representing HTTP error codes.

-   `HashMap populatePayload();` - Inserts default data into the payload if not given to avoid malformed requests. This method is what applies the attributes from the API format to a service.

The service can then be called by adding its name to the switch statement in the `ServiceFactory` by adding a new case for its `name` attribute in lowercase and returning a new object of the service (which takes the `payload` as its only parameter). No other code interaction needs to take place.

The service should be placed in the `services` package and its API format should be listed in the ["API Format"](#api-formats) section with its description.