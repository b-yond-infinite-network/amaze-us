#start db
docker run -d --name timescaledb -p 5432:5432 -e POSTGRES_PASSWORD=password timescale/timescaledb:latest-pg11

#execute scripts
docker exec -it timescaledb psql -U postgres
then
create database cats
then scripts/createDB.sql