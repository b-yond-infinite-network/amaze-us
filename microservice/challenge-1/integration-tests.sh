#! /usr/bin/env bash

./scripts/testenv-create.sh && (echo 'waiting a bit...'; sleep 5) &&  ./scripts/testenv-smoketests.sh
res=$?
./scripts/testenv-destroy.sh
exit $?


