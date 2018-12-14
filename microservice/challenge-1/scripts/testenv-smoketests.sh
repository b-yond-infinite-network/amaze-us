#! /usr/bin/env bash

PATH="$(pwd)/vendor/bats/bin:$PATH" bats tests/smoketests.bats
