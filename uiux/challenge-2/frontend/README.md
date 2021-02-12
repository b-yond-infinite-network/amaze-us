# Kepler colony UI

![Frontend CI](https://github.com/sylhare/SpaceStack/workflows/Frontend%20CI/badge.svg)

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).
This frontend works with the [backend](../backend) and allow to manage the population growth of a spatial colony.

Made with react 17.0.1, axios 0.21.1, react-redux 7.2.2 and other linked dependencies.
Components are styled with the App.css.

## Usage details

Here are some details for each:
  - Login page: You need to login with any username/ password to get access of the rest of the app. the login page disappears once logged in.
  - Logout: The logout button appears once you are logged in so you can log out.
  - Pioneer's page: There's a form validation on the baby request by the pioneer and the button is disabled while not correct, a snackbar will show notifying of the success of the request.
      - You can also see the population amount that evolve with each approved babies.
  - Health and Survival Page: It will display the current new baby requests. The case where there are no requests is handled as well as when there is an issue.
      - When there is a decision gets received correctly from the server, then the request gets remove from the list of active requests.
  - Audit's page: Will allow you to filter through the approved requests

## Available Scripts

In the project directory, you can run:

- `yarn start` to start the app running on [localhost:3000](http://localhost:3000)
- `yarn test` to run the test
- `yarn test:cov` to run the test with coverage
- `yarn test:e2e` to run the cypress tests (make sure the app is running)

So far the proxy in package.json is made to be working with docker-compose.
