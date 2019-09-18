# Cargo App-Server

__environments variables are needed to run the project.__
- `mkdir config` , inside that add `test-integration.env` and `docker.env` with variables for `HOST` and `DB_NAME`.

In the server directory :

## To run the server:
- `npm i`
- `npm run start`

## To run the Tests:
- `npm run test:integration`
- `npm run test`

## To check the API documentation :
`-  docker run -d -p 80:8080 swaggerapi/swagger-editor`
