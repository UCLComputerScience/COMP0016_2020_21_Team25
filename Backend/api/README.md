# Backend API
The backend allows is to be accessed via a restful API on `localhost`.

## Endpoints

The following endpoints are available:


Parameters are to be supplied in the URL in lowercase form.

- e.g., the `services` endpoint requires an `ID` parameter  and is supplied (in lowercase) as:


    /services?id=id_here


- Endpoints that require multiple parameters are supplied by an ampersand (&) separated list.



## Responses


## Deployment

To start the application, ensuring no service is currently using port `8080`, run the following command in a terminal in this directory (where the `pom.xml` is located):

    mvn clean; mvn org.springframework.boot:spring-boot-maven-plugin:run

An executable `jar` file can be used instead, built and executed manually using:

    mvn clean package

You can also start the application using a Python script with the following command:

    python3 tools/run.py

And then perform your HTTP requests. For example, after the application is running, open a terminal and enter:

    curl "localhost:8080/services?id=1"

This will return something similar (depending on the user) to:

    {
        "services":["random-recipe"],
        "code":200
    }

Note that the URL must be quoted to escape the question marks and ampersands if performing HTTP requests from the command line.

### Command Line Arguments

There are different execution options for the API, summarised below.

Note that these can only be enabled by passing them as command line arguments to the Python `run` script or running the application as a `jar`. Command line arguments are unavailable with Java if not executing using a `jar` file.

The following arguments are all optional.

- A desired port can be specified using `-port=PORT` (or one of `--port`, `-p` and `--p`). Note that this must be an integer and is set to `8080` by default.
- Logging information to the console can be enabled with the `-l` (or `--l`) flag - logging is disabled by default.
- The application can be built into a single jar and executing using the `-jar` (or `--jar`) flag.
   - This option is only available using the Python run script.

When using the `jar` file:

- The `-l` flag is supplied **without** the leading `-` character(s) - i.e. as `l`.
- The `-port` flag is **always** supplied with a single `-` and the same goes for `-p`.

Running `python3 tools/run.py -h` gives you a similar breakdown of possible command line arguments and their aliases.

- The `-h` flag is not available outside of the Python script.