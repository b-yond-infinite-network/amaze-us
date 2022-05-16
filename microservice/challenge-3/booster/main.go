package main

import (
	"example.com/booster/app"
	"example.com/booster/config"
	"fmt"
)

func main() {
	config := config.GetConfig()

	app := &app.App{}
	app.Initialize(config)
	app.Run(":3000")
	fmt.Println("Hello world")
}
