#!/usr/bin/env sh

set -e

LOG_INFO="[INFO]"
LOG_ERROR="[ERROR]"
LOG_WARN="[WARN]"

SUITECRM_USER=crmuser

[ -z ${UID_USER} ] && UID_USER=1000
[ -z ${GID_USER} ] && GID_USER=${UID_USER}
echo "$LOG_INFO Create user ${SUITECRM_USER} with UID: ${UID_USER} and GID: ${GID_USER}"

id -g ${SUITECRM_USER} &> /dev/null || addgroup -g ${GID_USER} -S ${SUITECRM_USER}
id -u ${SUITECRM_USER} &> /dev/null || adduser -u ${UID_USER} -D -S -G ${SUITECRM_USER} ${SUITECRM_USER}

chown ${SUITECRM_USER}:${SUITECRM_USER} -R /usr/share/suitecrm

echo "$LOG_INFO Start process with user ${SUITECRM_USER}"
exec su-exec ${SUITECRM_USER} ${JAVA_PROCESS} $@