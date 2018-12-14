FROM  tomcat:9-jre8

WORKDIR ${CATALINA_HOME}

# Setup script generating application's config
COPY ./scripts/ .

# Install the exploded war
RUN  rm -rf ./webapps/ROOT/
COPY ./target/App42PaaS-Java-MySQL-Sample-0.0.1-SNAPSHOT/ ./webapps/ROOT/

# Set the configuration & entrypoint
ENV MYSQL_IP_ADDR   db
ENV MYSQL_PORT      3306
ENV MYSQL_DATABASE  database42
ENV MYSQL_USER      user42
ENV MYSQL_PASSWORD_FILE /run/secrets/db_user_password

ENTRYPOINT ./entrypoint.sh
