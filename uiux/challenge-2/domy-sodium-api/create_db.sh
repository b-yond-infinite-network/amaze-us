#!/usr/bin/env bash
echo ">>> CREATE DATABASE"
createdb -h 127.0.0.1 --port=5432 -U postgres domy_sodium

exit 0