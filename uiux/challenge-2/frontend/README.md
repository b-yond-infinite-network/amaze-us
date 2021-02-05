# Kepler colony UI

![Frontend CI](https://github.com/sylhare/SpaceStack/workflows/Frontend%20CI/badge.svg)

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).
This frontend works with the [backend](../backend) and allow to:

- From the pioneer's page to see the population amount and request Baby
- From the Health and Survival page to approve or deny the baby requests.

## Usage details

There's a form validation on the baby request by the pioneer and the button is disabled while not correct,
with an explanation of what needs to be done.
A snackbar will show notifying of the success of the request.

In the managing page, the case where there are no requests is handled as well as when there is an issue.
When there is a decision that receive the correct response from the server, then it gets remove from the list of active requests.

## Available Scripts

In the project directory, you can run:

- `yarn start` to start the app running on [localhost:3000](http://localhost:3000)
- `yarn test` to run the test
- `yarn test:cov` to run the test with coverage
- `yarn test:e2e` to run the cypress tests (make sure the app is running)

So far the proxy in package.json is made to be working with docker-compose.
