# Don't Let Them Grow 

### How
Since the [app42](https://github.com/shephertz/App42PaaS-Java-MySQL-Sample) 
is still a baby monolith and has not grown yet, Hero decided to break it down
to services below and manage them by Docker Compose.

### Services:
1. [Backend](./backend)
2. [Frontend](./frontend)
3. [Database](./db)

### Run Project
 Note: Requires node.js and npm to be installed.

``` bash
    cd [root directory of repository]
    
    chmod +x ./db/rundocker.sh
    ./db/rundocker.sh
    
    cd backend
    npm i
    npm run build
    npm start&
    
    curl -d '{"name":"Amir", "email":"email@domain.com", "description":"FullStack Developer"}' -H "Content-Type: application/json" -X POST http://localhost:3001/users
    
    cd ../frontend
    npm i
    npm start
```
Visit http://localhost:3000

**Note:** 'Add user' feature is not implemented in frontend.
Please send POST request to backend to add a new user.

### Improvements
1. Finish Dockerizing services.
2. Finish Docker-Compose to run and manage the entire solution.
3. Use Swagger to document the Backend API.
4. Use Selenium to automate Frontend testing.
5. Improve test coverage.
6. Use Typescript to implement backend tests.
7. Implement "add user" feature in frontend.

![Frontend](/microservice/challenge-1/app42/img/front.png?raw=true "Frontend")