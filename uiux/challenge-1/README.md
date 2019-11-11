# Intro

Hello fellow reviewers and welcome to my birthday!

This is my solution to the first UIUX challenge, Karaoke needs words, the solution is not complete to 100%, but it is fully functional.

Due to limitation of time, there is a couple of things I would've wanted to do, but I did not have the time for (Will mention them later)

## Live demo of the app
[http://mehdi-hamidi-challenge-1.tk/](http://mehdi-hamidi-challenge-1.tk/)  

DNS changes might not propagate quickly, if site not not available use direct IP:
[35.232.187.57](http://35.232.187.57)
# Tech Stack

## Developement 
- "react": "^16.11.0"
- "react-router-dom": "^5.1.2"
- "@progress/kendo-react-grid": "^3.6.0"
- "axios": "^0.19.0"
- "react-redux": "^7.1.1"
- "redux-saga": "^1.1.1"

## Testing
- "jest": "^24.9.0"
- "enzyme": "^3.10.0"
- "enzyme-adapter-react-16": "^1.15.1"
- "redux-mock-store": "^1.5.3"
- "redux-saga-test-plan": "^4.0.0-rc.3"

# Architecture

## Folder structure
```
|- src/
|	|- app
|	|- components
|	|- pages
|	|- router
|	|- redux
|	|- services
|	|- utils
|- package.json
```
- Components: shared and reusable react components
- Pages: app pages that use one or multiple components
- Router: logic and history for routing inside the app
- Redux: redux store and root reducer and saga
- Services: apis, actions, reducers, sagas per domain (services are not directly related to pages)
- Utils: modules or functions that can be used anywhere in the app

# Available Scripts

In the project directory, you can run:

## npm install

Installs the project's dependencies

## `npm start`

Runs the app in the development mode.<br />
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

## `npm test`

Launches the test runner in the interactive watch mode.<br />
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

## Deployment

I have deployed the version of the app to a cluster on GKE based on the latest image (tag:1.0)
The app can be accessed [HERE](http://mehdi-hamidi-challenge-1.tk) 

# Improvements and next steps

## Things I wish I did from the begining 

- Using typescript for writing the application
- Using hooks instead of the classes. I refactored the app mid developement, since I had no use case where classes were needed
- Using css-modules or a naming convention (BEM) for css to avoid the messy .css files
- Using scss instead of regular css

## Next steps
- General
    - Consistency for the developement  of the app: naming variable, file structure, etc. i.e: Setting some guidelines  and following them in all scenarios 
- Developement
    - Add default props to every component that uses them
    - Add maintenance page if the service is down
    - Error management for API calls and crashes
    - Use dynamic routing instead of hard-coded history.push("/location")
    - Use throttle or debounce with useEffect to git rid of onClick and launch search automatically  once the user is done typing
    - Make the design responsive for Mobile and small screens
    - Store the results of the API in the redux store to prevent calls for the same data (Clear store after a delay)
- Testing
    - Clear console from warnings
    - Improve coverage to at least 70%
    - Test react hooks state (useState)
    - Added test plans for redux-saga
    - Use mocked data for API calls to simulate real world scenarios
    - Add integration and E2E tests
- Scaling 
    - The version is deployed on GKE is easily scalable without issues, however if we add user session we might run into some issues (Sticky Sessions)

# Conclusions

Looking forward to your feedback!
