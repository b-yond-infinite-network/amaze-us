# Derived from official mysql image (our base image)
FROM mysql

# Add a database
ENV MYSQL_DATABASE user_management

# Add the content of the sql-scripts/ directory to your image
# All scripts in docker-entrypoint-initdb.d/ are automatically
# executed during container startup

COPY ./user_script.sql /docker-entrypoint-initdb.d/