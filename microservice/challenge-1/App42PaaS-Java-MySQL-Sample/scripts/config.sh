#! /usr/bin/env bash

APP42_MYSQL_PROPERTY_DIR="${CATALINA_HOME}/webapps/ROOT"
APP42_MYSQL_PROPERTY_FILE="${APP42_MYSQL_PROPERTY_DIR}/Config.properties"

mkdir -p "${APP42_MYSQL_PROPERTY_DIR}"

if [[ -z "${MYSQL_PASSWORD}" ]]; then
  MYSQL_PASSWORD=$(cat "${MYSQL_PASSWORD_FILE}")
fi

cat > "${APP42_MYSQL_PROPERTY_FILE}" <<EOD
app42.paas.db.ip = ${MYSQL_IP_ADDR}
app42.paas.db.port = ${MYSQL_PORT}
app42.paas.db.username = ${MYSQL_USER}
app42.paas.db.password = ${MYSQL_PASSWORD}
app42.paas.db.name = ${MYSQL_DATABASE}
EOD

