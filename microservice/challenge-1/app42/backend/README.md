# Backend

Since the [app42](https://github.com/shephertz/App42PaaS-Java-MySQL-Sample) 
is still a baby monolith and has not grown yet, Hero decided to break it down
to services below and manage them by Docker Compose.

1. [Nodejs](https://nodejs.org/en/)
2. [TypeScript](http://www.typescriptlang.org/)
3. [Expressjs](https://expressjs.com/)
4. [Mocha](https://mochajs.org/)

#### Endpoints
     1. /users
        1.1 [GET] => Returns an array of users in JSON.
        1.2 [POST] => Adds a new user.
     2. /healthcheck
        2.1 [GET] => returns {ok: true} if service is up.

#### Run 
 Note: Requires node.js and npm to be installed.

    npm i
    npm start
    curl http://localhost:3000/healthcheck

### Test
#### Unit Tests
     cd [root directory of repository]
     npm run utest

#### Integration Tests
     cd [root directory of repository]
     npm start&
     npm run itest
     
##### Unit Tests Result
 
![Test Results](/microservice/challenge-1/app42/backend/test/utest.png?raw=true "Unit Test Results")

##### Integration Tests Result
 
![Test Results](/microservice/challenge-1/app42/backend/test/itest.png?raw=true "Integration Test Results")
