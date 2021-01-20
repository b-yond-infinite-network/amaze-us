package main

import (
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app"
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/config"
)

func main() {
	startServer()
}

func startServer() {
	config := config.GetConfig()

	app := &app.App{}
	app.Start(config)
}
