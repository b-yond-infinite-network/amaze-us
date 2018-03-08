package main

import (
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app"
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/config"
)

func main() {
	//TODO: Process comand line arguments
	config := config.GetConfig()
	appServer := &app.App{}
	appServer.StartAppMain(config)
}
