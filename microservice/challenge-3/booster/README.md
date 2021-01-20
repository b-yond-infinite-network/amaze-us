# This is the Booster API
You fill its tanks with Fuel...seem legit ! :)

Before running API server, you should set the database config with yours or the rocket's...
```go
func GetConfig() *Config {
	return &Config{
		DB: &DBConfig{
			Dialect:  "mysql",
			Username: "guest",
			Password: "Guest0000!",
			Name:     "booster",
			Charset:  "utf8",
		},
	}
}
```

```bash
# Build and Run
cd go-todo-rest-api-example
go build
./go-todo-rest-api-example

# API Endpoint : http://127.0.0.1:3000
```


# Some examples

```bash
# Get all tanks
curl --location --request GET 'localhost:3000/tanks' 

# Create new tank
curl --location --request POST 'localhost:3000/tanks' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "title1",
    "archived": false,
    "fuel": [
        {
            "title": "fuel1",
            "done": false
        }
    ]
}'

# Get tank
curl --location --request GET 'localhost:3000/tanks/title2' 

# Update tank
curl --location --request PUT 'localhost:3000/tanks/title1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "title": "title1",
    "archived": false,
    "fuel": [
        {
            "title": "fuel1",
            "done": false
        },
        {
            "title": "fuel2",
            "done": false
        }
    ]
}'

# Delete tank
curl --location --request DELETE 'localhost:3000/tanks/title1' 

# Archive tank
curl --location --request PUT 'localhost:3000/tanks/title1/archive' 

# Restore tank
curl --location --request DELETE 'localhost:3000/tanks/title1/archive' 

# Get fuel parts for tank x
curl --location --request GET 'localhost:3000/tanks/title1/fuel' 

# Create fuel part for tank x
curl --location --request POST 'localhost:3000/tanks/title1/fuel' \
--header 'Content-Type: application/json' \
--data-raw '		{
            "title": "fuel1",
            "done": false
        }'

# Get fuel of tank x
curl --location --request GET 'localhost:3000/tanks/title1/fuel/0' \

# Update fuel of tank x
curl --location --request PUT 'localhost:3000/tanks/title1/fuel/0' \
--header 'Content-Type: application/json' \
--data-raw '		{
            "title": "fuel1",
            "done": true
        }'

```
