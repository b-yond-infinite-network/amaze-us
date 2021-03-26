# This is the Booster API

_You fill its tanks with Fuel...seem legit ! :)_

## Running the app

**Before running the app:**  
Make sure you have an instance of MySQL running locally.

To run the app, just use the following command:
```bash
go run main
```

In case you want to build the app:
```bash
go build
```

To run the tests (with coverage):
```bash
./test-coverage.sh
```

## App configurations

All the configurations are taken from this [yaml file](./booster.yml).  
Also, you can set environment variables.

| Variable | Configuration | Type | Default | Description |
| :------: | :-----------: | :-----: | :-----: | ----------- |
| BOOSTER_PORT | port | integer | 3000 | Application port |
| BOOSTER_DB_HOST | db.host | string | 127.0.0.1 | Database host (ip or name) |
| BOOSTER_DB_PORT | db.port | integer | 3306 | Database port |
| BOOSTER_DB_CONNECTION_TIMEOUT | db.connection-timeout | duration | 5s | Timeout to try to connect to the database. e.g., 1m, 60s, 60000ms |
| BOOSTER_DB_MAX_IDLE_CONNECTIONS | db.max-idle-connections | integer | 5 | Max number of unused connections |
| BOOSTER_DB_MAX_OPEN_CONNECTIONS | db.max-open-connections | integer | 10 | Maximum number of open connections |
| BOOSTER_DB_USERNAME | db.username | string | guest | Database username |
| BOOSTER_DB_PASSWORD | db.password | string | Guest0000! | Database password |
| BOOSTER_DB_NAME | db.name | booster | string | Database name |
| BOOSTER_DB_CHARSET | db.charset | string | utf8 | Database charset |

## Docker

To build a docker image with the booster app, you can run the following command, replacing the image name and version:
```bash
docker build -t <image_name>:<version> .
```

## API

_Just some endpoints_

Create a new tank:
```bash
curl -i -X POST -H 'Content-Type: application/json' -d '{"title":"Tank1", "archived": false, "fuel":[{"title":"Fuel 1", "priority": "0", "done":false}]}' 'http://localhost:3000/tanks'
```

Get the created tank:
```bash
curl -i 'http://localhost:3000/tanks/Tank1'
```

Set tank fuel as consumed:
```bash
curl -i -X PUT -H 'Content-Type: application/json' -d '{}' 'http://localhost:3000/tanks/Tank1/fuel/1/complete'
```
