#! /usr/bin/env bash

mvn=$(which mvn)
if [ $? -ne 0 ]; then
  echo "Maven is required"
  echo "Please install Maven: https://maven.apache.org/install.html"
  exit 1
fi

pushd App42PaaS-Java-MySQL-Sample
mvn clean install
popd


