# Concierge API Plugin

This Java package greatly simplifies the extension of Concierge's already rich API interaction ecosystem. It also allows
for new services to be added in a language-independent manner, providing a simple and intuitive, yet powerful interface.

If you have a service, or would just like to extend Concierge to include some other services,
the [schema structure](#schema-structure) section below will guide you on how to do so.

If extending the Concierge voice assistant, the service to be added **must** obtain **all** of its required parameters
from user speech - there is (currently) no support for admin interaction with extended services.

Adding a new service assumes, and requires, prior knowledge of the API, its endpoints, required URL parameters and
response structure. Prior knowledge about HTTP status codes (and what they mean) used by the API are required for custom
exception reporting.

## Schema Structure

New services can be defined using a `JSON` schema with the following possible sections:

- [name](#name)
- [description](#description)
- [url](#url) - **required**
- [endpoints](#endpoints)
- [api-key](#api-key)
- [parameters](#parameters) - **required**
- [message](#message) - **required** (If a natural language output is required)
- [error_code_name](#error-code-name)
- [error_messages](#error-messages)
- [metadata](#metadata)
- [error_metadata](#error-metadata)

### Name

If you want to give your service a specific name, you can do so using the `name` field. If this field is blank or not
specified, the service name defaults to the name of the `JSON` schema.

* Note that this is only used when reporting error responses from the API - the filename of the schema is still used to
  resolve the schema.

### Description

You can provide a description of this service for others looking over your schema - this has no effect on service
interaction.

### URL

The base URL of the endpoint is defined here with no query parameters.

For example, the [OpenWeatherMap](https://openweathermap.org/current#name) API for current weather information has an
example URL endpoint:

<pre>api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}</pre>

With everything following the `?` character being parameters; these parameters are not to be included in the `url`
section of the schema - they will be added internally (including the `?` character).

So, for the weather service, the `url` field will be defined as:

<pre>api.openweathermap.org/data/2.5/weather</pre>

### Endpoints

Some APIs have parameter-based endpoints
e.g., `api.someApi/someFixedEndpoint/{parameter}/{anotherParameter}?{queryParameter}={someValue}`.

* These parameters cannot be included in the [`parameters`](#parameters) as these are added as query parameters,
  following a `?` character.

To get around this, the `endpoints` section allows you to implement services with parameter-based endpoint.

The `endpoints` section is an array of mappings, defining how to resolve each dynamic endpoint with the following
fields:

<table>
<th align="center">Field</th>
<th align="center">Type</th>
<th align="center">Optional</th>
<th align="center">Description</th>

<tr>
<td align="center"><pre>name</pre></td>
<td align="center">String</td>
<td align="center">No</td>
<td>The name given to the endpoint. It is important that this name is <b>unique to both the endpoints and <a href="#parameters">parameters</a> sections</b> as endpoint and parameter values are taken from the same map when making an API call.</td>
</tr>

<tr>
<td align="center"><pre>default</pre></td>
<td align="center">String</td>
<td align="center">Yes</td>
<td>If you would like to set a default value for the endpoint, you can do so here, specifying it as a string regardless of its type. If a value for this endpoint is not included when making the API call, the default value will be substituted in. If this field is omitted and no value is supplied for the API call, an exception is thrown.</td>
</tr>
</table>

You do not include these endpoint names in the `url` section.

* e.g., with the URL above, `api.someApi/someFixedEndpoint/{parameter}/{anotherParameter}?{queryParameter}={someValue}`,
  the `url` section is defined as everything before the dynamic endpoints i.e.:

<pre>api.someApi/someFixedEndpoint</pre>

* Notice the omission of the trailing slash, it is not required but is recommended to do so.
* As a result, URLs can only have parameter-based endpoints after _all_ fixed endpoints.

If you include any dynamic endpoints, any trailing slashes in the `url` are removed beforehand.

**It is important to note that the endpoints are inserted in the same order in which they are defined in the `endpoints`
array.**

* Changing this order results in a different, potentially invalid, URL.

Note that if the `default` value is the empty string and no value is provided for the API call at runtime, an exception
is thrown.

### API Key

The API key to access the endpoint is defined here.

This field is not required, but, if the service requires an API key and this field is not filled out, an error message
will be returned - prior knowledge of the service is required.

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

Note that if you include the `api-key` field in your schema, you **must** specify a value, otherwise, a parsing error is
thrown.

### Parameters

This section defines how to add custom parameters to the URL. It is required if you'd like to include any custom (or
default) parameters.

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

This will transform `parameter` into `alias` when inserted into the URL - the parameter has no `default` value and is
not required.

### Message

This section defines how to parse the `JSON` API response into a natural language string with useful, understandable
information.

It uses a custom syntax defined as follows:

- To insert some top-level value defined in the `JSON` response object, the key name of the object is placed into the
  string as `{key_name}` i.e., surrounded with curly braces.

If the value is stored in some nested object in the response, two object notation forms can be used to access it:

#### Map notation

Map notation can be used to access data stored in nested maps.

- Prefix the field name with the field name(s) of all outer maps, separated by `.` characters. For example, consider the
  following `JSON` response object:

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

- Uses the same array-indexing notation in several programming languages.
- Given an array, named `array` in the response, to access the n<sup>th</sup> element, we use `array[n]` where `n` is
  some integer.

Consider the following `JSON` response object:

<pre>
{
    "result": ["Hello", "World"]
}
</pre>

`{result[0]}` returns `"Hello"` - **array indexing starts at 0**.

Multidimensional arrays can also be accessed e.g., `{result[0][1]}`.

Note that indexing rules still apply - accessing an index outside the range of the array will throw an exception.

Also note that if the array is nested in some map(s), prefix the array name with the names of the maps that contain it,
using **map notation**.

#### Usage

These object notation forms can be chained together to access deeply nested objects (with
some [limitations](#limitations)).

- e.g., `{map.array[0]}` or `{array[0].map}`.

- Note that **map notation cannot be used inside the square braces of an array notation**.

- Also note that the map notation takes precedence over the array notation, and the top-level notation
  i.e., `{parameter}` - not stored in any nested structure, has the lowest precedence.

The name of the parameter can include spaces i.e. if a parameter is named `parameter name` we can still reference it
as `{parameter name}` in the message.

- This includes map notation: `{map.parameter name}` is valid syntax.

- Note that any fields with leading and trailing whitespace e.g., `{map . parameter name}`, will be trimmed
  to `{map.parameter name}` internally. Whitespace within the parameter is preserved.

An example can be found in the [example](#example-schema) section below.

Technically speaking, the `message` attribute does not _need_ to be a natural language string.

- In theory, you could structure it to represent a stringified `JSON` object instead (with indentation if needed) - this
  could be useful when transferring data between two APIs.

- However, typing information may be lost using this method; a better approach is to define the structure using
  the [`metadata`](#metadata) field defined below.

### Error Code Name

Should you want to return custom error messages for a set of HTTP error codes, the `error_code_name` defines where in
the `JSON` response, returned by the API, to look for the error code.

It uses the same syntax and notation as the `message` field, so, you can also locate nested error codes.

An example implementation:

<pre>
"error_code_name": "result.code"
</pre>

In this case, it is assuming the code is stored in the `JSON` response as:

<pre>
{
    "result": {
        "code": ERROR_CODE
    }
}
</pre>

Note that the value of `error_code_name` must be of type string.

If the API does not return an HTTP status code, only a default catch-all error message should be defined in the schema
and this field can safely be omitted.

### Error Messages

Should you want to return custom error messages for a set of HTTP error codes, you can define them in
the `error_messages` section.

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

Note that the `error_code_name` field must also be set for these values to be used otherwise a generic error response is
returned.

Also, note that the error codes must be double-quoted (as per `JSON` standards) and are all cast to strings internally.

If the API does not return any HTTP codes, any fields other than `"default"` are redundant.

### Metadata

Some information may be unsuitable for speech synthesis but may be needed elsewhere - this is referred to as `metadata`
and specified in the `metadata` section of the schema.

However, if the required parameter is nested inside a map or an array, the key name can become quite complicated.
Instead, an `alias` sub-field can be used to rename the parameter in the response metadata map:

<pre>
"metadata": {
      "parameter_name": {
        "alias": "alias_name"
      }
}
</pre>

Leaving the `alias` sub-field blank sets the key name in the response metadata map to the `parameter_name`.

As with the [`parameters`](#parameters) section, the following shorthand also exists here for the `alias` sub-field:

<pre>
"metadata": {
      "parameter_name": "alias"
}
</pre>

The value specified by `parameter_name` does not have to be a single data point - it can also be an object.

#### Custom Metadata Structure

Consider the [example response](https://openweathermap.org/current#current_JSON) from the OpenWeatherMap API.

What if we wanted to store several attributes together e.g., the coordinates and wind speed?

<pre>
{
    "data": {
        "latitude": some_value,
        "longitude": some_value,
        "wind_speed": some_value
    }
}
</pre>

In the example response, the data is not structured in this manner, but, we can use the `custom` flag in the `metadata`
section of the schema to define a custom mapping.

We can define a custom metadata field to pull in these values, regardless of where they are located in the actual
response e.g.,

To achieve the above structure, using the example response:

<pre>
  "metadata": {
    "data": {
      "custom": true,
      "children": {
        "coord.lat": "latitude",
        "coord.lon": "longitude",
        "wind.speed": "wind_speed"
      }
    },
  }
</pre>

When using the `custom` flag, the parameter name (e.g., `data` in this case) is not searched for in the response,
instead, its `children` are.

- Note that children can also be marked as custom.
- Children can be nested as much as required, ensuring the containing parameter is marked as `custom` otherwise they
  will be ignored.
- Setting `custom` to false yields the default behaviour - the parameter will be treated as a field in the JSON response
  object returned by the API and will be searched for.

Note that the parameter will be skipped if it is marked as `custom` but no `children` attribute is present -
if `children` is empty, the metadata returned for the parameter will also be empty.

### Error Metadata

Metadata parsing can also be performed with the error responses, using the exact same structure as
the [metadata](#metadata) section.

The only difference is that this is defined under a separate `erorr_metadata` section in the schema.

## Example Schema

New services can then be added by uploading the `JSON` schema into the `resources` folder of the package.

<b align=center>Ensure that the filename of the schema is globally unique.</b>

An example `JSON` schema for a [weather service](https://openweathermap.org/current) is defined below (the `api-key` has
been omitted):

<pre>
{
  "name": "Current Weather",
  "description": "Return the current weather",
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
    "404": "Could not find that city",
    "default": "I'm sorry, I could not find any weather information."
  },
  "metadata": {
    "data": {
      "custom": true,
      "children": {
        "wind.speed": "wind_speed",
        "coord.lat": "latitude",
        "coord.lon": "longitude"
      }
    },
    "weather[0]": "basic_weather_data"
  }
}
</pre>

The syntax in the `message` field was obtained by observing
the [example response](https://openweathermap.org/current#current_JSON) for the endpoint.

## Limitations

Naturally, there is only so much that can be done with just string syntax parsing.

The following limitations currently exist:

- No support for array iteration in response parsing.
- No support for adding custom arrays into response metadata.
- URLs can only have parameter-based endpoints after _all_ fixed endpoints.
- No support for map iteration.

Array iteration can be achieved by duplicating the desired section of the `message` n times, increasing the array index
in the duplicated section by the desired increment. Prior knowledge about the size of the array returned is required.

These limitations can be circumvented by implementing the service as a Java class,
detailed [here](https://github.com/UCLComputerScience/COMP0016_2020_21_Team25/blob/main/App/Services-API/README.md);
this gives you much more flexibility in URL building and response parsing.
