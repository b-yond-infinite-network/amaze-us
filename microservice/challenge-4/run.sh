#!/bin/bash

sbt clean compile assembly
java -jar target/scala-2.13/clowder-assembly-1.0.jar
