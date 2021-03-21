package main

import (
	"fmt"
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/app"
	"github.com/b-yond-infinite-network/amaze-us/microservice/challenge-3/booster/config"
	"log"
)

func main() {
	config, err := config.GetConfig()
	if err != nil {
		log.Fatalln("Error loading booster configuration", err)
	}

	app := &app.App{}
	app.Initialize(config)

	log.Println("Running booster app on port", config.Port)

	app.Run( fmt.Sprintf(":%d", config.Port))
}
