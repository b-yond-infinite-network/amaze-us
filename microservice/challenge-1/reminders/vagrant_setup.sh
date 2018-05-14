#!/bin/sh

#fail outside, works with vagrant
cd /vagrant

#docker
sudo curl -fsSL https://get.docker.com/ | sudo sh
sudo service docker start

#nginx
sudo yum install -y epel-release
sudo yum install -y nginx

sudo rm /etc/nginx/nginx.conf
sudo cp /vagrant/etc/nginx/nginx.conf /etc/nginx/nginx.conf

sudo systemctl start nginx

#reminders-core
sudo docker build --no-cache -t reminders/reminders-core-api ./reminders-core-api
sudo docker run --name reminders-core-api -d -p 9000:9000 --add-host reminders.local:192.168.56.10 -t reminders/reminders-core-api

#scheduler
sudo docker build --no-cache -t reminders/scheduler-api ./scheduler-api
sudo docker run --name scheduler-api -d -p 8081:8080 --add-host reminders.local:192.168.56.10 -t reminders/scheduler-api

#email-sender
sudo docker build --no-cache -t reminders/email-sender-api ./email-sender-api
sudo docker run --name email-sender-api -d -p 8082:8080 --add-host reminders.local:192.168.56.10 -t reminders/email-sender-api

