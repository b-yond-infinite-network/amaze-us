#! /usr/bin/env bash

BACKEND="compose"
if [ -n "${USE_K8S_DIND+1}" ]; then
  kubectl=$(which kubectl)
  if [ $? -ne 0 ]; then
    echo "Cannot find kubectl"
    echo "Please install kubectl: https://kubernetes.io/docs/tasks/tools/install-kubectl"
    exit 1
  fi
  BACKEND="kubeadm-dind-cluster"
fi

SCRIPTS="./scripts/${BACKEND}"
create_env="${SCRIPTS}/testenv-create.sh"
destroy_env="${SCRIPTS}/testenv-destroy.sh"

if [ "$1" = "destroy" ]; then
  $destroy_env
  exit
fi

if [ "$1" = "test" ]; then
  PATH="$(pwd)/vendor/bats/bin:$PATH" bats "tests/${BACKEND}/smoketests.bats"
  exit $?
fi

if ! $create_env; then
  echo "Failed to create integration tests environment"
  exit 1
fi

echo ''
echo 'Waiting a bit for the cluster & apps to be up...'
sleep 7

echo ''
echo 'Running tests'
echo ''
PATH="$(pwd)/vendor/bats/bin:$PATH" bats "tests/${BACKEND}/smoketests.bats"
TEST_RESULT=$?

if [[ -z "${DEBUG}" ]]; then
  $destroy_env
else
  echo "DEBUG set, not destroying env"
  echo "when done you can:"
  echo " ./integration-tests.sh destroy"
fi

exit $TEST_RESULT

