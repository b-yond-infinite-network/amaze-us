#!/usr/bin/env sh

function progress() {
    local GREEN CLEAN
    GREEN='\033[0;32m'
    CLEAN='\033[0m'
    printf "\n${GREEN}$@  ${CLEAN}\n" >&2
}

set -e

progress "Building cassandra docker image ..."
docker build ./docker-environment/cassandra -t suitecrm/cassandra

progress "Building base jre docker image ..."
docker build ./docker-environment/java-base-image -t suitecrm/jre-base

progress "Dowloading confluentinc-kafka-connect-cassandra-1.0.2.zip ..."
mkdir -p $PWD/Tmp
wget https://d1i4a15mxbxib1.cloudfront.net/api/plugins/confluentinc/kafka-connect-cassandra/versions/1.0.2/confluentinc-kafka-connect-cassandra-1.0.2.zip -O $PWD/Tmp/kafka-connect-cassandra.zip \
      && unzip $PWD/Tmp/kafka-connect-cassandra.zip -d $PWD/Tmp \
      && rm -rf $PWD/Tmp/kafka-connect-cassandra.zip

