# Backend API

Source code for the backend of the **IBM FISE Concierge**, providing support for both the app and the companion web app,
accessed via a restful API on `localhost`.

The backend can be run on a separate server-like device in a fully-federated manner. To accommodate this, the backend
sends out UDP broadcast messages to all devices in the local network allowing devices to discover the IP address of the
machine running the backend to connect to the RESTful API.

## Endpoints

Parameters are to be supplied in the URL in lowercase form.

- e.g., the `services` endpoint requires an `ID` parameter and is supplied (in lowercase) as:

<pre>/services?id=id_here</pre>

- Endpoints that require multiple parameters are supplied by an ampersand (&) separated list of strings.

### Web App Endpoints

Required endpoints for the Concierge web app with required parameters and expected responses:

<table>
<tr>
<th>Endpoint</th>
<th>Params</th>
<th>Method</th>
<th>Response</th>
<th>Description</th>
</tr>

<tr>
<td align="center">login</td>
<td align="center"><pre>username-or-email<br>password</pre></td>
<td align="center"><pre>GET</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "username" String
 "errors": {
     "field": String,
     "message": String,
 }
 "code": int
}
</pre>
</td>
<td>Checks the database to see if the login details are correct.</td>
</tr>

<tr>
<td align="center">register</td>
<td align="center">
<pre>username<br>first-name<br>last-name<br>email<br>phone-number<br>password</pre>
</td>
<td align="center"><pre>POST</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "errors": {
     "field": String,
     "message": String,
 }
 "code": int
}
</pre>
</td>
<td>Register a new admin account and store the details in the database.</td>
</tr>

<tr>
<td align="center">admin</td>
<td align="center"><pre>username</pre></td>
<td align="center"><pre>GET</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "data": {
     "first_name": String,
     "last_name": String,
     "email": String,
     "phone_number": String,
     "password": String,
     "profile_picture": String,
 }
 "code": int
}
</pre>
</td>
<td>Return the data stored for an admin's account.</td>
</tr>

<tr>
<td align="center">members</td>
<td align="center"><pre>username</pre></td>
<td align="center"><pre>GET</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "members": {
     user-id: {
         "first_name": String,
         "last_name": String,
         "phone_number": String,
         "prefix": String,
         "profile_picture": String
     } ...
 },
 "code": int
}
</pre>
</td>
<td>Return all users registered under the given admin username.</td>
</tr>

<tr>
<td align="center">member-services</td>
<td align="center"><pre>user-id</pre></td>
<td align="center"><pre>GET</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "services": [{
    "service_id": int,
    "service_name": String,
    "description": String,
    "icon": String,
    }, ... ]
 },
 "code": int
}
</pre>
</td>
<td>Return all services added to the given user's account.</td>
</tr>

<tr>
<td align="center">service-categories</td>
<td align="center"></td>
<td align="center"><pre>GET</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "categories": Array
 "code": int
}
</pre>
</td>
<td>Return all possible service categories currently stored in database. <b>Sorted alphabetically by category name.</b></td>
</tr>

<tr>
<td align="center">profile-pictures</td>
<td align="center"></td>
<td align="center"><pre>GET</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "profile-pictures": {
    profile_picture_id: String,
    ...
 }
 "code": int
}
</pre>
</td>
<td>Return all profile pictures.</td>
</tr>

<tr>
<td align="center">services-in-category</td>
<td align="center"><pre>category</pre></td>
<td align="center"><pre>GET</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "services": [{
         "service_id": int,
         "name": String,
         "category": String,
         "icon": String,
         "description": String,
     }, ... ],
 "code": int
}
</pre>
</td>
<td>Return all services in the given category. <b>Sorted alphabetically by service name.</b></td>
</tr>

<tr>
<td align="center">add-service-to-user</td>
<td align="center"><pre>user-id<br>service-id</pre></td>
<td align="center"><pre>POST</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "code": int
}
</pre>
</td>
<td>Add the given service to the user with the given user ID.</td>
</tr>

<tr>
<td align="center">remove-service-from-user</td>
<td align="center"><pre>user-id<br>service-id</pre></td>
<td align="center"><pre>POST</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "code": int
}
</pre>
</td>
<td>Remove the given service from the user's account with the given user ID.</td>
</tr>

<tr>
<td align="center">update-admin</td>
<td align="center"><pre>username<br>first-name<br>last-name<br>email<br>phone-number<br>password<br>profile-picture</pre></td>
<td align="center"><pre>POST</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "code": int
}
</pre>
</td>
<td>Update the admin's profile data.</td>
</tr>

<tr>
<td align="center">add-member</td>
<td align="center"><pre>username<br>first-name<br>last-name<br>phone-number<br>prefix<br>profile-picture</pre></td>
<td align="center"><pre>POST</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "user_id": int,
 "registration_code": String,
 "code": int
}
</pre>
</td>
<td>Add a new user registered under the given admin. Note this should also generate the registration code for that user.</td>
</tr>

<tr>
<td align="center">remove-member</td>
<td align="center"><pre>user-id</pre></td>
<td align="center"><pre>DELETE</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "code": int
}
</pre>
</td>
<td>Delete a user's account from an admin.</td>
</tr>

<tr>
<td align="center">update-member</td>
<td align="center"><pre>user-id<br>first-name<br>last-name<br>phone-number<br>prefix<br>profile-picture</pre></td>
<td align="center"><pre>POST</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "code": int
}
</pre>
</td>
<td>Update the details for a given user.</td>
</tr>

<tr>
<td align="center">member-history</td>
<td align="center"><pre>user-id</pre></td>
<td align="center"><pre>GET</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "history": [{
         "service_id": int,
         "service_name": String,
         "timestamp": Integer,
     }],
 "code": int
}
</pre>
</td>
<td>Return (full) service usage history for the given user. <b>Sorted by the timestamp, with most recent entries first.</b></td>
</tr>

<tr>
<td align="center">member-service-data</td>
<td align="center"><pre>user-id<br>service-id</pre></td>
<td align="center"><pre>GET</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "fields": {
    "field_name": Any, 
    ...
    },
 "code": int
}
</pre>
</td>
<td>Return the fields required by the given service, for the given user, and the values they currently hold.</td>
</tr>

<tr>
<td align="center">update-member-service-data</td>
<td align="center"><pre>user-id<br>service-id<br>fields (Map)</pre></td>
    <td align="center"><pre>POST</pre></td>
<td>
<pre>
{
 "success": boolean,
 "message": String,
 "code": int
}
</pre>
</td>
<td>Update the required data for the given service, assigned to the given user.</td>
</tr>

</table>

## Deployment

To start the application in a Docker container, you must first build the image with:

    docker build --tag backend .

And then to start the application on port `8100`:

    docker run --rm backend backend/backend

Note that you must be running these docker commands from one level up, just outside this folder. You should navigate to
the containing folder before building or running the image:

    cd ..

To start the application, ensuring no service is currently using port `8100`, run the following command in a terminal in
this directory (where the `pom.xml` is located):

    mvn clean; mvn org.springframework.boot:spring-boot-maven-plugin:run

An executable `jar` file can be used instead, built and executed manually using:

    mvn clean package

You can also start the application using a Python script with the following command:

    python3 tools/run.py

And then perform your HTTP requests. For example, after the application is running (on port 8100), open a terminal and
enter:

    curl "localhost:8100/services?id=1"

This will return something similar (depending on the user) to:

    {
        "services": ["random-recipe"],
        "code": 200
    }

Note that the URL must be quoted to escape the question marks and ampersands if performing HTTP requests from the
command line.

### Command Line Arguments

There are different execution options for the API, summarised below.

Note that these can only be enabled by passing them as command line arguments to the Python `run` script or running the
application as a `jar`. Command line arguments are unavailable with Java if not executing using a `jar` file (using
the `java -jar ...` command).

The following arguments are all optional.

- A desired port can be specified using `-port=PORT` (or one of `--port`, `-p` and `--p`). Note that this must be an
  integer and is set to `8100` by default.
- Logging information to the console can be enabled with the `-l` (or `--l`) flag - logging is disabled by default.
- The application can be built into a single jar and executed using the `-jar` (or `--jar`) flag.
    - This option is only available using the Python `run` script.

When using the `jar` file (with `java -jar ...`):

- The `-l` flag is supplied **without** the leading `-` character(s) - i.e. as `l`.
- The `-port` flag is **always** supplied with a single `-` and the same goes for `-p`.

Running `python3 tools/run.py -h` gives you a similar breakdown of possible command line arguments and their aliases.

- The `-h` flag is not available outside of the Python script.
