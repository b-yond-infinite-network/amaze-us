#!/bin/sh

echo "[i] Building Docker image..."
sudo docker build -t challenge .
echo "[i] Docker Image Built."
echo "[i] running Image..."
sudo docker run -p 3000:3000 -d challenge
echo "[i] Image is running on port 3000..."
