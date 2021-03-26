# Cargo App

## Run the app

_This app is tested with node 14._

**Before running the app:**  
Make sure you have an instance of MongoDB running locally.

First, set the node version and install dependencies:
```bash
nvm use
npm install
```

Then, to run the app:
```bash
node server.js
```

Finally, you can access the app here http://localhost:8080

## App configurations

The app has the following environment variables:

| Variable | Default | Description |
| :------: | :-----: | ----------- |
| PORT | 8080 | Application port |
| MONGODB_URL | mongodb://localhost:27017/cargo | MongoDB url |

## Docker

To build a docker image with the stage 2 app, you can run the following command, replacing the image name and version:
```bash
docker build -t <image_name>:<version> .
```

## API

Create a new cargo item:
```bash
curl -i -X POST -H 'Content-Type: application/json' -d '{"text": "some text", "loaded": false}' 'http://localhost:8080/api/cargo'
```

Get all cargo items:
```bash
curl -i 'http://localhost:8080/api/cargo'
```

Partial update a cargo item:
```bash
curl -i -X PATCH -H 'Content-Type: application/json' -d '{"loaded": true}' 'http://localhost:8080/api/cargo/{{cargp_id}}'
```

Delete a cargo item:
```bash
curl -i -X DELETE 'http://localhost:8080/api/cargo/{{cargp_id}}'
```
