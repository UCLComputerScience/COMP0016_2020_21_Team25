# Admin Site

The companion web app to the **IBM FISE Concierge** allowing friends of (elderly)
users to set up accounts and manage the details of all users in their circle. Admins
have the ability to add and remove services from user accounts and view service
usage history for each user.

## Prerequisites

To get started with development, you will need a browser that runs JavaScript and
some extra prerequisites:

-   [Node.js](https://nodejs.org) - Package manager
-   [npm](https://www.npmjs.com/get-npm) - This should come with your Node installation.
-   A (relatively recent) browser that allows JavaScript - more information on supported browsers can be found in the [`package.json`](https://github.com/UCLComputerScience/COMP0016_2020_21_Team25/blob/9d2ed43ad6e29286b3ac329b5479949a2092c5c2/Admin%20Site/package.json#L62) file.

## Installation

To install the necessary packages, run the following command:

    npm install

## Deployment

Before running the application, ensure the [server](https://github.com/UCLComputerScience/COMP0016_2020_21_Team25/tree/main/Backend) is running on `localhost` on port `8080`.

-   Should you want to run the server on a different port, you must change the `PORT` value in the [`src/backend/api.js`](https://github.com/UCLComputerScience/COMP0016_2020_21_Team25/tree/main/Admin%20Site/src/backend/api.js#L3) script to match before running.

To view the website in a development server run the following command:

    npm run-script dev-start

To view the website in production mode (with all necessary optimisations performed) run the following command:

    npm start

## Testing

Testing, with a coverage report, can be performed using the following command:

    npm test

The coverage report can be viewed in your default browser using:

    npm run-script coverage

The [`tests/unit/components`](https://github.com/UCLComputerScience/COMP0016_2020_21_Team25/tree/main/Admin%20Site/tests/unit/components) directory contains tests for the `.vue` single file components with the working store and backend interaction, meaning the backend server **must** be running before running these tests.

The [`tests/unit/store`](https://github.com/UCLComputerScience/COMP0016_2020_21_Team25/tree/main/Admin%20Site/tests/unit/components) directory contains tests to ensure the backend is functioning correctly, requiring no `.vue` components.

## Publish

To publish an update to the website, run the following command:

    npm run-script deploy

A live version of the website can be found [here](https://fise-concierge.web.app/) should you not want to serve it locally

-   Note that the backend server **must** still be manually started.
