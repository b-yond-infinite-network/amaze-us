# Cargo App

## Run the tests

_This app is tested with node 14._

**Before running the app:**  
Make sure you have run docker compose on the challenge-3 directory.

First, set the node version and install dependencies:
```bash
nvm use
npm install
```

Then, to run the app:
```bash
npm test
```

## App configurations

The app has the following environment variables:

| Variable | Default | Description |
| :------: | :-----: | ----------- |
| CYPRESS_BOOSTER_URL | http://localhost:3000 | Url to the booster API |
