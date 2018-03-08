# This is the Booster API
You fill its tanks with Fuel...seem legit ! :)

Before running API server, you should set the database config with yours or the rocket's..., depends on mysql
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
cd /microservice/challenge-3/booster 
go build -v .
./booster

# API Endpoint : http://127.0.0.1:3000
```
