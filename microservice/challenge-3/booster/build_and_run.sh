#!/bin/bash

#Get Deps
go get -t -v ./...


#Test Booster
go test -v ./tests/...

#Build booster
go build -v ./

#Run booster
./booster
