# Service API Interaction
This Java package allows the service API interaction to be accessed via a restful API.

## Endpoints
The following endpoints are available:

-   *"air-quality"*
-   *"current-weather"*
-   *"dictionary"*
-   *"ingredient"*
-   *"joke"*
-   *"nearest-transport"*
-   *"news"*
-   *"random-recipe"*
-   *"recipe"*
-   *"stocks"* 
-   *"transport-search"*
-   *"weather-forecast"*

As per the service APIs included except any spaces in the service names are replaced with hyphens.

Parameters are to be supplied in the URL in lowercase form.

- e.g., the dictionary service endpoint requires a `WORD` parameter (as per the outer README) and is supplied as:


    /dictionary?word=word_here

- Services that require multiple parameters are supplied by an ampersand (&) separated list.

Note that there are not `POST` requests available (only `GET`), setting the request method to `POST` in the request header 
will result in an error.


## Responses
All responses are of the form:


    {
       "service": "",
       "message": "",
       "metadata": {},
       "code": code
    }

Where

- **service** - the name of the service; this is either the endpoint, for a recognised service or `error` if a client HTTP error occurs.
- **message** - a natural language string representing the service response - useful for voice assistants.
- **metadata** - extra data that may be useful, but unsuitable for natural language e.g., image/web links.
- **code** - the HTTP code using [this](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status) standard.

## Deployment

To start the application, ensuring no service is currently using port `8080`, run the following command in a terminal in the "Services-API" directory (where the `pom.xml` is located):

    mvn clean; mvn org.springframework.boot:spring-boot-maven-plugin:run

Logging information can be enabled by passing the command line argument `-l`.

And then perform your HTTP requests. For example, after the application is running, open a terminal and enter:

    curl "localhost:8080/current-weather?city=london"

This will return something similar to:

    {
        "service":"current weather",
        "message":"The weather in London today is broken clouds with the temperature being 7 degrees celsius but will probably feel like 4 degrees celsius. The high will be 7 degrees celsius and the low, 6 degrees celsius. Don't forget to dress warm today!",
        "metadata":{},
        "code":200
    }

Note that the URL must be quoted to escape the question marks and ampersands if performing HTTP requests from the command line.