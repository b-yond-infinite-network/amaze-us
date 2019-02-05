# Challenge 1 - Karaoke needs words

Solution for the coding challenge.

## Tech Stack

- [React v16.8.0-alpha.1 (hooks)](https://reactjs.org/)
- [Reach Router](https://reach.tech/router)
- [Create React App](https://facebook.github.io/create-react-app/)
- [TypeScript](https://www.typescriptlang.org/)
- [Jest (Testing framework)](https://jestjs.io/)
- [react-testing-library (unit/integration tests)](https://github.com/kentcdodds/react-testing-library)
- [cypress.io (e2e tests)](https://www.cypress.io/)

## Build Process

In the project directory, you can run:

### `npm i`

Installs project dependencies

### `npm start`

Runs the app in the development mode.<br>
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

### `npm run build`

Builds the app for production to the `build` folder.<br>
It correctly bundles React in production mode and optimizes the build for the best performance.

### `npm test`

Launches the test runner in the interactive watch mode.

### `npm run test:cov`

Collects code coverage from unit tests.<br>
See the section about [Coverage Reporting](https://facebook.github.io/create-react-app/docs/running-tests#coverage-reporting) for more information.

### `npm test:e2e`

Launches a cypress instance using Electron<br>
After that, you will need to click the "Run all specs" button. This way, a new Chrome session is opened with the specs running into the browser.

---

## App Structure

```bash
|- src/
|	|- features
|	|	|- FeatureName
|   |	|	|- FeatureName.tsx (code)
|   |	|	|- FeatureName.module.css (css module)
|   |	|	|- FeatureName.test.tsx (unit test)
|	|- layouts
|   |	|	|- App.tsx (main layout)
|	|- pages
|   |	|	|- Page1.tsx
|   |	|	|- Page2.tsx
|	|- router (App router)
|	|- shared (common)
|   |	|	|- api (API connectors)
|   |	|	|- components (Reusable UI components)
|   |	|	|- hooks (Custom hooks)
|   |	|	|- types (Entities/types)
|	|- index.css (Global CSS styles)
|	|- index.tsx (entry point)
|- cypress/ (E2E tests)
|- package.json
```

The Web App is organized using the following structure/hierarchy:

```
Layout > Page > Feature(s) > Component(s)
```

- **Layout**: Web App layout (master layout)
- **Page**: Template composed by one or multiple features and loaded using a Router.
- **Feature**: Self-contained reusable complex building block. A feature is composed by N components.
- **Component**: Self-contained reusable buiding block.

---

Juan Andrade @ 2019

---

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).
