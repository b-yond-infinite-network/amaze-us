#!/bin/sh

echo "[i] running db..."
sudo docker run --name db -d -p 5431:5432 -e POSTGRES_USER="postgres" -e POSTGRES_PASSWORD="byond" -e POSTGRES_DB="byond" postgres
echo "[i] db is running on port 5431..."
