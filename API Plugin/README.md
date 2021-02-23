# Concierge API Plugin
This Java package greatly simplifies the extension of Concierge's already rich API interaction ecosystem. It also allows for new services to be added in a language-independent manner, providing a simple and intuitive, yet powerful interface.

If you have a service, or would just like to extend Concierge to include some other services, the [schema structure](#schema-structure) section below will guide you on how to do so.

If extending the Concierge voice assistant, the service to be added **must** obtain **all** of its required parameters from user speech - there is (currently) no support for admin interaction with extended services.

## Schema Structure
New services can be defined using a `json` schema with the following possible sections:

* [url](#url) - **required**
* [api-key](#api-key)
* [parameters](#parameters) - **required**
* [message](#message) - **required**
* [error_code_name](#error_code_name)
* [error_messages](#error_messages)

### URL

The base URL of the endpoint is defined here with no query parameters.

For example, the [OpenWeatherMap](https://openweathermap.org/current#name) API for current weather information has an example URL endpoint:

<pre>api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}</pre>

With everything following the `?` character being parameters; these parameters are not to be included in the `url` section of the schema - they will be added internally (including the `?` character).

So, for the weather service, the `url` field will be defined as:

<pre>api.openweathermap.org/data/2.5/weather</pre>

### API Key

The API key to access the endpoint is defined here.

This field is not required, but, if the service requires an API key and this field is not filled out, an error message will be returned - prior knowledge of the service is required.

The `api-key` field has the following sub-fields:

<table>
<th align="center">Field</th>
<th align="center">Type</th>
<th align="center">Optional</th>
<th align="center">Description</th>

<tr>
<td align="center"><pre>alias</pre></td>
<td align="center">String</td>
<td align="center">Yes</td>
<td>If the API's endpoint has a different name for its API key parameter, it is defined here. If the API key parameter is also named "api-key", this field can be safely omitted. Naming the alias "api-key" has no effect.</td>
</tr>

<tr>
<td align="center"><pre>value</pre></td>
<td align="center">String</td>
<td align="center">No</td>
<td>If the API requires an API key, its value is specified here.</td>
</tr>
</table>

Note that if you include the `api-key` field in your schema, you **must** specify a value, otherwise a parsing error is thrown.

### Parameters

This section defines how to add custom parameters to the URL. It is required if you'd like to include any custom (or default) parameters.

The `parameters` field has the following subfields:

<table>
<th align="center">Field</th>
<th align="center">Type</th>
<th align="center">Optional</th>
<th align="center">Description</th>

<tr>
<td align="center"><pre>alias</pre></td>
<td align="center">String</td>
<td align="center">Yes</td>
<td>If the API's endpoint has a different name for the parameter, it is defined here. If you want the parameter to have the same name as the parameter in the API, this field can be safely omitted. Giving the `alias` the same value as the parameter has no effect. Note that this field is purely for semantics, should you want to name the parameter something different than the API-given name.</td>
</tr>

<tr>
<td align="center"><pre>default</pre></td>
<td align="center">String</td>
<td align="center">Yes</td>
<td>If you would like to set a default value for this parameter, you can do so here, specifying it as a string regardless of its type. If a value for this parameter is not included when making the API call, the default value will be substituted in. If this field is omitted and no value is supplied for the API call, the entire parameter is excluded from the URL.</td>
</tr>

<tr>
<td align="center"><pre>required</pre></td>
<td align="center">boolean</td>
<td align="center">Yes</td>
<td>Ensure this parameter is included in the URL, throwing an exception if is not. This setting defaults to false if omitted and explicitly setting this to false essentially has no effect. Note that setting a default value will still throw an error if this field is set to true a value for the parameter is not provided.</td>
</tr>
</table>

A shorthand can be used for the `alias` field:

<pre>"parameter": "alias"</pre>

This will transform `parameter` into `alias` when inserted into the URL - the parameter has no `default` value and is not required.

### Message

This section defines how to parse the `json` API response into a natural language string with useful, understandable information.

It uses a custom syntax defined as follows:

* To insert some top-level value defined in the `json` response object, the key name of the object is placed into the string as `{key_name}` i.e., surrounded with curly braces.

If the value is stored in some nested object in the response, there are two object notation forms which can be used to access it:

#### Map notation

Map notation can be used to access data stored in nested maps.

* Prefix the field name with the field name(s) of all outer maps, separated by `.` characters. For example, consider the following `json` response object:

<pre>
{
    "result": {
        "message": "Hello world"
    }
}
</pre>

To access the `message` field in our natural language string, we add `{result.message}` - this returns `"Hello World"`.

If it was nested further, e.g.,

<pre>
{
    "result": {
        "inner": {
            "message": "Hello World"
        }
    }
}
</pre>

We add all parent maps, separated by `.` characters: `{result.inner.message}` - this will also return `"Hello World"`.

#### Array Notation

If the data is stored in an array, rather than a map, **array notation** is used instead.

* Uses the same array-indexing notation in several programming languages.
* Given an array, named `array` in the response, to access the n<sup>th</sup> element, we use `array[n]` where `n` is some integer.

Consider the following `json` response object:

<pre>
{
    "result": ["Hello", "World"]
}
</pre>

`{result[0]}` returns `"Hello"` - **array indexing starts at 0**.

Note that indexing rules still apply - accessing an index outside of the range of the array will throw an exception.

Also note that if the array is nested in some map(s), prefix the array name with the names of the maps that contain it, using **map notation**.

#### Usage
These object notation forms can be chained together to access deeply nested objects (with some [limitations](#limitations)).

* Note that **map notation cannot be used inside the square braces of an array notation**.

*  Also note that the map notation takes precedence over the array notation and the top-level notation has the lowest precedence.

Whitespace inside the curly braces are removed.

An example can be found in the [example-schema](#example) section below.

### Error Code Name

Should you want to return custom error messages for a set of HTTP error codes, the `error_code_name` defines where in the `json` response, returned by the API, to look for the error code.

It uses the same syntax and notation as the `message` field so you can also locate nested error codes.

An example implementation:

<pre>
"error_code_name": "result.code"
</pre>

In this case, it is assuming the code is stored in `json` response as:

<pre>
{
    "result": {
        "code": ERROR_CODE
    }
}
</pre>

Note that the value of `error_code_name` must be of type string.

If the API does not return a HTTP status code, only a default catch-all error message can be defined in the secma and this field can safely be omitted.

### Error Messages

Should you want to return custom error messages for a set of HTTP error codes, you can define them in the `error_messages` section.

These messages follow the exact same syntax as the `message` field.

A `default` field can be used as a catch-all for other error codes.

An example implementation:

<pre>
"error_messages": {
    "404": "I could find not the endpoint.",
    "500": "Something bad happened on their side.",
    .... Other codes and messages
    "default": "I could not complete the service request."
}
</pre>

Note that the `error_code_name` field must also be set in order for these values to be used otherwise a generic error response is returned.

Also note that the error codes must be double-quoted (as per `json` standards) and are cast to strings internally.

If the API does not return any HTTP codes, any fields other than `"default"` are redundant.

## Example Schema

New services can then be added by uploading the `json` schema into the `resources` folder of the package.

<b align=center>Ensure that the filename of the schema is globally unique.</b>

An example `json` schema for a [weather service](https://openweathermap.org/current) is defined below (the `api-key` has been omitted):


<pre>
{
  "url": "https://api.openweathermap.org/data/2.5/weather",
  "api-key": {
    "name": "appid",
    "value": "OMITTED"
  },
  "parameters": {
    "city": "q",
    "language": {
      "alias": "lang",
      "default": "en",
      "required": false
    }
  },
  "message": "The weather in {name} today is {weather[0].description} with the temperature being {main.temp} degrees celsius but will probably feel like {main.feels_like} degrees celsius. The high will be {main.temp_max} degrees celsius and the low, {main.temp_min} degrees celsius.",
  "error_code_name": "cod",
  "error_messages": {
    "404": "Could not find ...",
    "default": "I'm sorry, I could not find any weather information."
  }
}
</pre>

The syntax in the `message` field was obtained by observing the [example response](https://openweathermap.org/current#current_JSON) for the endpoint.

## Limitations
Naturally, there is only so much that can be done with just string syntax parsing.

The following limitations currently exist:

* No support for multi-dimensional arrays in response parsing.
* No support for array-iteration in response parsing.
* No support for adding metadata from a response.
* No support for parameter-based API endpoints i.e., if the service has two endpoints which take the same parameters and return the same response, they **must** be defined in separate schemas under different names.


These limitations can be circumvented by implementing the service as a Java class, detailed [here](https://github.com/UCLComputerScience/COMP0016_2020_21_Team25/blob/main/App/Services-API/README.md).