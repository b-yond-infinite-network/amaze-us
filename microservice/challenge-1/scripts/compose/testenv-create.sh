#! /usr/bin/env bash

SECRETS_DIR='docker/secrets'

function generate_secret(){
  sec_name="$1"
  sec_fn="${SECRETS_DIR}/${sec_name}"
  test -f "${sec_fn}" || (echo "generating $sec_fn...";  od -An -N10 -x /dev/random | sed -e 's/ //g' > "${sec_fn}")
}

generate_secret mysql_root_password
generate_secret mysql_user_password

docker-compose up -d --build
